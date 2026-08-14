package it.fornacecalandra.thip.base.generale.api;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.collector.ApiInfo;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.gui.cnr.OpenType;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.rs.errors.ErrorUtils;
import com.thera.thermfw.rs.errors.PantheraApiException;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.magazzino.documenti.DocMagGenerico;
import it.thera.thip.magazzino.documenti.DocMagGenericoRiga;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 14/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    14/08/2026  DSSOF3   Prima stesura
 */

public class YProduzioneCalandraService {

	static YProduzioneCalandraService service;

	public static YProduzioneCalandraService getService() {
		if (service == null) {
			service = Factory.newObject(YProduzioneCalandraService.class); 
		}
		return service;
	}

	@SuppressWarnings("unchecked")
	public JSONObject prelievoLiberoMateriali(JSONObject payload) {
		JSONObject response = new JSONObject();
		JSONObject result = new JSONObject();
		Status status = Status.OK;
		Collection<ErrorMessage> errors = new ArrayList<>();

		if(!payload.has("righe")) {
			errors.add(new ErrorMessage("BAS0000078","Il JSON non ha un oggetto 'righe'"));
			status = Status.BAD_REQUEST;
		}else {
			BODataCollector docBODC = creaDocGenTestata(payload);
			DocMagGenerico testata = (DocMagGenerico) docBODC.getBo();
			if(testata != null) {
				JSONArray righe = payload.getJSONArray("righe");

				for(int i = 0; i < righe.length(); i ++) {
					JSONObject riga = righe.getJSONObject(i);

					DocMagGenericoRiga docMagGenRig = creaDocGenRiga(testata, riga);
					if(docMagGenRig != null) {
						testata.getRighe().add(docMagGenRig);
					}
				}

				docBODC.setAutoCommit(true);
				int res = docBODC.save();
				if(res == BODataCollector.ERROR) {
					throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
				}else {
					result.put("message", "Creato correttamente il documento generico : "+KeyHelper.formatKeyString(docBODC.getBo().getKey()));
				}

			}
		}

		result.put("errors", ErrorUtils.getInstance().toJSON(errors));
		response.put("response", result);
		response.put("status", status);
		return response;
	}

	@SuppressWarnings("unchecked")
	protected BODataCollector creaDocGenTestata(JSONObject payload) throws PantheraApiException {
		DocumentoDataCollector docBODC = (DocumentoDataCollector) createDataCollector("DocMagGenerico");

		int rcSS = docBODC.initSecurityServices(OpenType.NEW, true, true, true);
		if(rcSS == BODataCollector.ERROR) {
			return docBODC;
		}

		DocMagGenerico doc = (DocMagGenerico) docBODC.getBo();

		doc.setIdAzienda(Azienda.getAziendaCorrente());

		//Parametri da prendere da causale
		doc.getNumeratoreHandler().setDataDocumento(TimeUtils.getCurrentDate());
		doc.getNumeratoreHandler().setIdSerie("DG");
		doc.setIdCau("SCP");
		doc.setIdMagazzino(doc.getCausale().getIdMagazzino());

		//..Leggo eventuali dati extra
		JSONObject payloadTestata = new JSONObject(payload.toString());
		payloadTestata.remove("righe");

		readExtraData(docBODC, payloadTestata);

		//..In questo modo carico sul bo i dati messi nei component manager sopra
		docBODC.setOnBORecursive();

		doc.setStatoAvanzamento(StatoAvanzamento.DEFINITIVO);

		docBODC.setBo(doc);
		docBODC.setAutoCommit(false);
		int res = docBODC.save();
		if(res == BODataCollector.ERROR) {
			throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
		}
		return docBODC;
	}

	@SuppressWarnings("unchecked")
	protected DocMagGenericoRiga creaDocGenRiga(DocMagGenerico testata,JSONObject payload) throws PantheraApiException {
		DocumentoDataCollector docBODC = (DocumentoDataCollector) createDataCollector("DocMagGenericoRiga");
		DocMagGenericoRiga riga = (DocMagGenericoRiga) docBODC.getBo();

		int rcSS = docBODC.initSecurityServices(OpenType.NEW, true, true, true);
		if(rcSS == BODataCollector.ERROR) {
			throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
		}

		riga.setIdAzienda(Azienda.getAziendaCorrente());
		riga.setTestata(testata);
		riga.completaBO();

		//..Leggo eventuali dati extra
		readExtraData(docBODC, payload);

		//..In questo modo carico sul bo i dati messi nei component manager sopra
		docBODC.setOnBORecursive();

		if(riga.getArticolo() != null) {
			riga.setOperatoreConversioneUM(riga.getArticolo().getOperConverUM());
			riga.setIdUMPrm(riga.getArticolo().getIdUMPrmMag());
		}

		riga.getQuantita().setQuantitaInUMPrm(riga.getQtaInUMPrm());

		riga.setStatoAvanzamento(testata.getStatoAvanzamento());

		docBODC.setBo(riga);
		int res = docBODC.check();
		if(res == BODataCollector.ERROR) {
			throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
		}
		return riga;
	}

	public void readExtraData(BODataCollector boDC, JSONObject payload) throws PantheraApiException{
		for (String name : payload.keySet()) {
			JSONObject data = new JSONObject();
			data.put(name, payload.get(name));
			try {
				boDC.read(name, data);
			}catch (Exception e) {
				ErrorMessage em = new ErrorMessage("BAS0000078","Componente di base "+name+", errore nel settaggio dati");
				em.addComponent(name, boDC.getClassADCollection().getAttribute(name).getAttributeName(), 
						boDC.getComponent(name));
				throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR,em);
			}
		}
	}

	protected BODataCollector createDataCollector(String classname) {
		try {
			ClassADCollection hdr = ClassADCollectionManager.collectionWithName(classname);
			return createDataCollector(hdr);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected BODataCollector createDataCollector(ClassADCollection classDescriptor) {
		BODataCollector dataCollector = null;
		String collectorName = classDescriptor.getBODataCollector();
		if (collectorName != null) {
			dataCollector = (BODataCollector) Factory.createObject(collectorName);
		} else {
			dataCollector = new BODataCollector();
		}
		ApiInfo info = new ApiInfo();
		info.doNotAddNullComponentsToGroup = true;
		dataCollector.setApiInfo(info);
		dataCollector.initialize(classDescriptor.getClassName(), true);
		return dataCollector;
	}
}
