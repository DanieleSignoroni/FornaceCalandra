package it.fornacecalandra.thip.base.generale.api;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.ApiInfo;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.gui.cnr.OpenType;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.rs.errors.ErrorUtils;
import com.thera.thermfw.rs.errors.PantheraApiException;

import it.fornacecalandra.thip.base.generale.YCauRigDocPrdStrutture;
import it.fornacecalandra.thip.base.generale.YCauRigDocPrdStruttureTM;
import it.fornacecalandra.thip.base.generale.YPsnDatiImpMovPrd;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.generale.PersDatiGen;
import it.thera.thip.datiTecnici.modpro.ModelloProduttivo;
import it.thera.thip.datiTecnici.modpro.ModproEsplosione;
import it.thera.thip.magazzino.documenti.CausaleRigaDocVersDist;
import it.thera.thip.magazzino.documenti.DocMagBase;
import it.thera.thip.magazzino.documenti.DocMagBaseRiga;
import it.thera.thip.magazzino.documenti.DocMagGenerico;
import it.thera.thip.magazzino.documenti.DocMagGenericoRiga;
import it.thera.thip.magazzino.documenti.DocMagVersDistinta;
import it.thera.thip.magazzino.documenti.DocMagVersDistintaRigaPrm;

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
 * 72616    14/08/2026  DSSOF3   Prima stesura
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
			BODataCollector docBODC = creaDocMagBase("DocMagGenerico", payload);
			DocMagGenerico testata = (DocMagGenerico) docBODC.getBo();
			if(testata != null) {
				JSONArray righe = payload.getJSONArray("righe");

				for(int i = 0; i < righe.length(); i ++) {
					JSONObject riga = righe.getJSONObject(i);

					DocMagGenericoRiga docMagGenRig = (DocMagGenericoRiga) creaDocMagBaseRiga("DocMagGenericoRiga", testata, riga, null, YCauRigDocPrdStrutture.NON_SIGNIFICATIVO);
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
	public JSONObject produzioneStruttura(JSONObject payload) {
		JSONObject response = new JSONObject();
		JSONObject result = new JSONObject();
		Status status = Status.OK;
		Collection<ErrorMessage> errors = new ArrayList<>();

		if(!payload.has("righe")) {
			errors.add(new ErrorMessage("BAS0000078","Il JSON non ha un oggetto 'righe'"));
			status = Status.BAD_REQUEST;
		}else {
			BODataCollector docBODC = creaDocMagBase("DocMagVersDistinta", payload);
			DocMagVersDistinta testata = (DocMagVersDistinta) docBODC.getBo();
			if(testata != null) {
				JSONArray righe = payload.getJSONArray("righe");

				for(int i = 0; i < righe.length(); i ++) {
					JSONObject riga = righe.getJSONObject(i);

					BigDecimal qta = null;
					if(riga.has("QtaMqFatturabili") && riga.has("QtaMqProdotti")) {

						qta = riga.getBigDecimal("QtaMqFatturabili");
						DocMagVersDistintaRigaPrm rigaQtaFatturabile = (DocMagVersDistintaRigaPrm) creaDocMagBaseRiga("DocMagVersDistintaRigaPrm", testata, riga, qta, YCauRigDocPrdStrutture.FATTURABILI);
						if(rigaQtaFatturabile != null) {
							testata.getRighe().add(rigaQtaFatturabile);
						}

						qta = riga.getBigDecimal("QtaMqProdotti");
						DocMagVersDistintaRigaPrm rigaQtaProdotta = (DocMagVersDistintaRigaPrm) creaDocMagBaseRiga("DocMagVersDistintaRigaPrm", testata, riga, qta, YCauRigDocPrdStrutture.PRODOTTI);
						if(rigaQtaProdotta != null) {
							testata.getRighe().add(rigaQtaProdotta);
						}

					}else {
						DocMagVersDistintaRigaPrm docVersDistintaRig = (DocMagVersDistintaRigaPrm) creaDocMagBaseRiga("DocMagVersDistintaRigaPrm", testata, riga, qta, YCauRigDocPrdStrutture.NON_SIGNIFICATIVO);
						if(docVersDistintaRig != null) {
							testata.getRighe().add(docVersDistintaRig);
						}
					}
				}

				docBODC.setAutoCommit(true);
				int res = docBODC.save();
				if(res == BODataCollector.ERROR) {
					throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
				}else {
					result.put("message", "Creato correttamente il documento versamento distinta: "+KeyHelper.formatKeyString(docBODC.getBo().getKey()));
				}

			}
		}

		result.put("errors", ErrorUtils.getInstance().toJSON(errors));
		response.put("response", result);
		response.put("status", status);
		return response;
	}

	@SuppressWarnings("unchecked")
	protected BODataCollector creaDocMagBase(String className, JSONObject payload) {
		DocumentoDataCollector docBODC = (DocumentoDataCollector) createDataCollector(className);

		int rcSS = docBODC.initSecurityServices(OpenType.NEW, true, true, true);
		if(rcSS == BODataCollector.ERROR) {
			return docBODC;
		}

		DocMagBase doc = (DocMagBase) docBODC.getBo();

		doc.setIdAzienda(Azienda.getAziendaCorrente());

		assegnaDatiDocMagPre(doc, payload);

		//..Leggo eventuali dati extra
		JSONObject payloadTestata = new JSONObject(payload.toString());
		payloadTestata.remove("righe");

		readExtraData(docBODC, payloadTestata);

		//..In questo modo carico sul bo i dati messi nei component manager sopra
		docBODC.setOnBORecursive();

		if(doc.getDataDocumento() == null) {
			doc.getNumeratoreHandler().setDataDocumento(TimeUtils.getCurrentDate());
		}

		//..Qui posso gestire l'oggetto con eventuali altre logiche
		doc.setStatoAvanzamento(StatoAvanzamento.DEFINITIVO);

		assegnaDatiDocMagPre(doc, payloadTestata);

		docBODC.loadAttValue(); //per caricare sui component manager i valori messi sul bo (serve per le check)
		docBODC.setAutoCommit(false);
		int res = docBODC.save();
		if(res == BODataCollector.ERROR) {
			throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
		}
		return docBODC;
	}

	@SuppressWarnings("unchecked")
	protected DocMagBaseRiga creaDocMagBaseRiga(String className, DocMagBase testata,JSONObject payload, BigDecimal qta, char tipoMq) throws PantheraApiException {
		DocumentoDataCollector docBODC = (DocumentoDataCollector) createDataCollector(className);
		DocMagBaseRiga riga = (DocMagBaseRiga) docBODC.getBo();

		int rcSS = docBODC.initSecurityServices(OpenType.NEW, true, true, true);
		if(rcSS == BODataCollector.ERROR) {
			throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
		}

		riga.setIdAzienda(Azienda.getAziendaCorrente());
		riga.setTestata(testata);
		riga.completaBO();

		assegnaDatiDocMagBaseRigaPre(riga, testata, payload, tipoMq);

		//..Leggo eventuali dati extra
		readExtraData(docBODC, payload);

		//..In questo modo carico sul bo i dati messi nei component manager sopra
		docBODC.setOnBORecursive();

		if(qta != null) {
			riga.setQtaInUMPrm(qta);
		}

		assegnaDatiDocMagBaseRigaPost(riga, testata, payload);

		riga.setStatoAvanzamento(testata.getStatoAvanzamento());

		docBODC.loadAttValue(); //per caricare sui component manager i valori messi sul bo (serve per le check)
		int res = docBODC.check();
		if(res == BODataCollector.ERROR) {
			throw new PantheraApiException(Status.INTERNAL_SERVER_ERROR, docBODC.getErrorList().getErrors());
		}
		return riga;
	}

	public void assegnaDatiDocMagPre(DocMagBase doc, JSONObject payload) {
		if(doc instanceof DocMagGenerico) {
			doc.getNumeratoreHandler().setDataDocumento(TimeUtils.getCurrentDate());
			doc.getNumeratoreHandler().setIdSerie(YPsnDatiImpMovPrd.getCurrentYPsnDatiImpMovPrd().getIdSerieDocPrlLibero());
			doc.setIdCau(YPsnDatiImpMovPrd.getCurrentYPsnDatiImpMovPrd().getIdCauDocGenPrlLibero());
			doc.setIdMagazzino(doc.getCausale().getIdMagazzino());
		}else if(doc instanceof DocMagVersDistinta) {
			doc.getNumeratoreHandler().setDataDocumento(TimeUtils.getCurrentDate());
			doc.getNumeratoreHandler().setIdSerie(YPsnDatiImpMovPrd.getCurrentYPsnDatiImpMovPrd().getIdSerieDocProdStrutture());
			doc.setIdCau(YPsnDatiImpMovPrd.getCurrentYPsnDatiImpMovPrd().getIdCauDocProdStrutture());
			doc.setIdMagazzino(doc.getCausale().getIdMagazzino());
		}
	}

	public void assegnaDatiDocMagPost(DocMagBase doc, JSONObject payload) {
		if(doc instanceof DocMagGenerico) {

		}else if(doc instanceof DocMagVersDistinta) {

		}
	}

	public void assegnaDatiDocMagBaseRigaPre(DocMagBaseRiga riga, DocMagBase testata, JSONObject payload, char tipoMq) {
		if(riga instanceof DocMagGenericoRiga) {

		}else if(riga instanceof DocMagVersDistintaRigaPrm) {
			//..Con elementWithKey perche' non lo ho ancora sull'oggetto in questo metodo
			Articolo articolo;
			try {
				articolo = (Articolo) Articolo.elementWithKey(Articolo.class, KeyHelper.buildObjectKey(new String[] {
						Azienda.getAziendaCorrente(), (String) payload.get("IdArticolo")
				}), PersistentObject.NO_LOCK);
				if(articolo != null)
					riga.setCausaleRiga(trovaCausaleRigaVersamento(articolo, tipoMq));
			} catch (JSONException | SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
	}

	public void assegnaDatiDocMagBaseRigaPost(DocMagBaseRiga riga, DocMagBase testata, JSONObject payload) {
		if(riga.getArticolo() != null) {
			riga.setOperatoreConversioneUM(riga.getArticolo().getOperConverUM());
			riga.setIdUMPrm(riga.getArticolo().getIdUMPrmMag());
		}

		riga.getQuantita().setQuantitaInUMPrm(riga.getQtaInUMPrm());

		if(riga instanceof DocMagGenericoRiga) {

		}else if(riga instanceof DocMagVersDistintaRigaPrm) {
			((DocMagVersDistintaRigaPrm) riga).setTipoEmissione(DocMagVersDistintaRigaPrm.MODELLO_PRD);

			ModelloProduttivo modPro = null;

			((DocMagVersDistintaRigaPrm) riga).setDominio(ModelloProduttivo.GENERICO);
			((DocMagVersDistintaRigaPrm) riga).setIdStabilimento(PersDatiGen.getCurrentPersDatiGen().getIdStabilimento());

			try {
				modPro = ModproEsplosione.trovaModelloProduttivo(riga.getIdAzienda(), riga.getIdArticolo(), ((DocMagVersDistintaRigaPrm) riga).getIdStabilimento(),
						testata.getDataDocumento(), riga.getIdCommessa(), ((DocMagVersDistintaRigaPrm) riga).getDominio());
				if(modPro != null) {
					((DocMagVersDistintaRigaPrm) riga).setIdModello(modPro.getIdModello());
				}
			} 
			catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public CausaleRigaDocVersDist trovaCausaleRigaVersamento(Articolo articolo, char tipoMq) {
		CausaleRigaDocVersDist causale = null;
		if(articolo != null
				&& articolo.getIdClasseD() != null) {
			String where = " "+YCauRigDocPrdStruttureTM.ID_AZIENDA+" = '"+Azienda.getAziendaCorrente()+"'";
			where += " AND "+YCauRigDocPrdStruttureTM.R_CLASSE_D+" = '"+articolo.getIdClasseD()+"' ";
			where += " AND "+YCauRigDocPrdStruttureTM.TIPO_MQ+" = '"+tipoMq+"' ";
			Vector causali;
			try {
				causali = YCauRigDocPrdStrutture.retrieveList(YCauRigDocPrdStrutture.class, where, "", false);
				if(causali.size() > 0) {
					causale = ((YCauRigDocPrdStrutture) causali.get(0)).getCausalerigadocvrsdist();
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
			//Fallback su non significativo ??
			/*if(causale == null)
				causale = trovaCausaleRigaVersamento(articolo, YCauRigDocPrdStrutture.NON_SIGNIFICATIVO);*/
		}
		return causale;
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
