package it.fornacecalandra.thip.vendite.uds;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.softre.thip.base.uds.YTipoUds;
import it.softre.thip.vendite.uds.YCatastaRiga;
import it.softre.thip.vendite.uds.YCatasteObject;
import it.softre.thip.vendite.uds.YCatasteObjectCatastaComparator;
import it.softre.thip.vendite.uds.YCatasteObjectRigaComparator;
import it.softre.thip.vendite.uds.YCatasteResultSetIterator;
import it.softre.thip.vendite.uds.YUdsVenRig;
import it.softre.thip.vendite.uds.YUdsVendita;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.ThipException;
import it.thera.thip.vendite.ordineVE.OrdineVendita;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrmTM;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 17/04/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 71936    17/04/2025  DSSOF3   Prima stesura
 */

public class YGenerazioneCatasteUds extends BatchRunnable implements Authorizable{

	protected String iIdAzienda;

	protected Proxy iTipoUds = new Proxy(YTipoUds.class);


	public YGenerazioneCatasteUds() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
		setIdAziendaInternal(iIdAzienda);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		String key6 = iTipoUds.getKey();
		iTipoUds.setKey(KeyHelper.replaceTokenObjectKey(key6, 1, idAzienda));
	}

	public YTipoUds getTipoUds() {
		return (YTipoUds) iTipoUds.getObject();
	}

	public void setTipoUds(YTipoUds iTipoUds) {
		this.iTipoUds.setObject(iTipoUds);
	}

	public String getTipoUdsKey() {
		return iTipoUds.getKey();
	}

	public void setTipoUdsKey(String key) {
		iTipoUds.setKey(key);
	}

	public String getIdTipoUds() {
		String key = iTipoUds.getKey();
		String rTipoUds = KeyHelper.getTokenObjectKey(key, 2);
		return rTipoUds;
	}

	public void setIdTipoUds(String rTipoUds) {
		String key = iTipoUds.getKey();
		iTipoUds.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rTipoUds));
	}

	@Override
	protected boolean run() {
		boolean isOk = true;
		try {
			List<YCatasteObject> cataste = recuperaCataste();
			Collections.sort(cataste, new YCatasteObjectRigaComparator());

			String previousRiga = null;
			List<YCatasteObject> currentGroup = new ArrayList<YCatasteObject>();

			for (YCatasteObject catasta : cataste) {
				String currentRiga = catasta.getRIGA();

				if (previousRiga == null || !currentRiga.equals(previousRiga)) {
					if (!currentGroup.isEmpty()) {
						int rcUds = processaCatasta(previousRiga, currentGroup);
						if (rcUds < 0) {

						}
						currentGroup.clear();
					}

					previousRiga = currentRiga;
				}

				currentGroup.add(catasta);
			}
		}catch (Exception e) {
			isOk = false;
			output.println("exc non gestita"+e.getMessage());
			e.printStackTrace(Trace.excStream);
		}
		return isOk;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected int processaCatasta(String idRiga, List<YCatasteObject> children) throws SQLException {
		String previousCatasta = null;
		List<YCatasteObject> currentCatastaGroup = new ArrayList();

		Collections.sort(children, new YCatasteObjectCatastaComparator());

		for (YCatasteObject catasta : children) {
			String currentCatasta = catasta.getCATASTA();

			// Check if CATASTA is different from the previous one
			if (previousCatasta == null || !currentCatasta.equals(previousCatasta)) {
				if (!currentCatastaGroup.isEmpty()) {
					// Create the CATASTA head and associate with the currentCatastaGroup
					creaUdsVendita(idRiga, previousCatasta, currentCatastaGroup);
					currentCatastaGroup.clear();
				}

				previousCatasta = currentCatasta;
			}

			// Add the current object to the CATASTA group
			currentCatastaGroup.add(catasta);
		}

		// Handle the last CATASTA group after the loop
		if (!currentCatastaGroup.isEmpty()) {
			creaUdsVendita(idRiga, previousCatasta, currentCatastaGroup);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	protected int creaUdsVendita(String idRiga, String idCatasta, List<YCatasteObject> children) throws SQLException {
		YUdsVendita testata = (YUdsVendita) Factory.createObject(YUdsVendita.class);

		testata.setIdAzienda(getIdAzienda());
		testata.setRTipoUds(getTipoUds().getIdTipoUds());
		testata.setDataUds(TimeUtils.getCurrentDate());
		testata.setAssegnaNumeratoreAutomatico(false);
		YCatasteObject children0 = children.get(0);
		testata.setIdUds(children0.getCATASTA());
		testata.setDaCatasta(true); // Flag per capire che ho creato l'uds da questo flusso

		String cOrdVenTes = getIdAzienda() + KeyHelper.KEY_SEPARATOR + children0.getORDINE().substring(0, 4)
				+ KeyHelper.KEY_SEPARATOR + children0.getORDINE().substring(4, children0.getORDINE().length());
		OrdineVendita ordVenTes = (OrdineVendita) OrdineVendita.elementWithKey(OrdineVendita.class, cOrdVenTes,
				PersistentObject.NO_LOCK);
		if (ordVenTes != null) {
			testata.setRCliente(ordVenTes.getIdCliente());
		}

		int rc = testata.save();
		if (rc > 0) {

			YUdsVenRig riga = (YUdsVenRig) Factory.createObject(YUdsVenRig.class);
			riga.setParent(testata);

			String cOrdVenRig = getIdAzienda() + KeyHelper.KEY_SEPARATOR + children0.getORDINE().substring(0, 4)
					+ KeyHelper.KEY_SEPARATOR + children0.getORDINE().substring(4, children0.getORDINE().length());
			cOrdVenRig += KeyHelper.KEY_SEPARATOR + children0.getRIGA();

			OrdineVenditaRigaPrm rigaOrdVen = ricercaOrdineVenditaRigaPrimaria(children0);
			if (rigaOrdVen != null) {
				riga.setRelarticolo(rigaOrdVen.getArticolo());

			} else {
				throw new ThipException("Impossibile trovare la riga ordine vendita con chiave :" + cOrdVenRig
						+ ", per il seguente motivo non e' possibile recuperare l'articolo");
			}

			riga.setCliente(((OrdineVendita) rigaOrdVen.getTestata()).getCliente());
			riga.setOrdineVenditaRiga(rigaOrdVen);

			testata.getRigheUDSVendita().add(riga);

			BigDecimal totSuperficieFatt = BigDecimal.ZERO;

			for (YCatasteObject catasta : children) {

				YCatastaRiga rigaCatasta = (YCatastaRiga) Factory.createObject(YCatastaRiga.class);
				rigaCatasta.setCatasta(Integer.valueOf(catasta.getCATASTA()));
				rigaCatasta.setSigla(catasta.getSIGLA());
				rigaCatasta.setNumeroLastre(catasta.getNUMERO_LASTRE());
				rigaCatasta.setSuperficieFatturata(catasta.getSUPERFICIE_FATTURATA());
				rigaCatasta.setSuperficieReale(catasta.getSUPERFICIE_REALE());
				rigaCatasta.setPesoTot(BigDecimal.valueOf(catasta.getPESO_TOT()));
				rigaCatasta.setLunghezza(catasta.getLUNGHEZZA());
				rigaCatasta.setLarghezza(catasta.getLARGHEZZA());

				riga.getRigheCataste().add(rigaCatasta);

				totSuperficieFatt = totSuperficieFatt.add(rigaCatasta.getSuperficieFatturata());

			}

			BigDecimal qtaUmPrm = totSuperficieFatt;
			qtaUmPrm = rigaOrdVen.getArticolo().convertiUM(totSuperficieFatt, rigaOrdVen.getUMPrm(),
					rigaOrdVen.getUMPrm(), null);
			BigDecimal qtaUmSec = BigDecimal.ZERO;
			if (rigaOrdVen.getUMSec() != null) {
				qtaUmSec = rigaOrdVen.getArticolo().convertiUM(totSuperficieFatt, rigaOrdVen.getUMPrm(),
						rigaOrdVen.getUMSec(), null);
			}
			BigDecimal qtaUmVen = rigaOrdVen.getArticolo().convertiUM(totSuperficieFatt, rigaOrdVen.getUMPrm(),
					rigaOrdVen.getUMRif(), null);

			riga.setQtaPrm(qtaUmPrm);
			riga.setQtaSec(qtaUmSec);
			riga.setQtaVen(qtaUmVen);

		}
		rc = testata.save();
		return rc;
	}

	@SuppressWarnings("rawtypes")
	public OrdineVenditaRigaPrm ricercaOrdineVenditaRigaPrimaria(YCatasteObject catasta) {
		OrdineVenditaRigaPrm riga = null;
		String anno = catasta.getORDINE().substring(0, 4);
		String numero = catasta.getORDINE().substring(4, catasta.getORDINE().length());
		String sequenza = catasta.getRIGA();
		String where = " " + OrdineVenditaRigaPrmTM.ID_AZIENDA + " = '" + getIdAzienda() + "' ";
		where += " AND " + OrdineVenditaRigaPrmTM.ID_ANNO_ORD + " = '" + anno + "'  ";
		where += " AND " + OrdineVenditaRigaPrmTM.ID_NUMERO_ORD + " = '" + numero + "'  ";
		where += " AND " + OrdineVenditaRigaPrmTM.SEQUENZA_RIGA + " = '" + sequenza + "'  ";
		try {
			Vector rows = OrdineVenditaRigaPrm.retrieveList(OrdineVenditaRigaPrm.class, where, "", false);
			if (rows.size() > 0) {
				riga = (OrdineVenditaRigaPrm) rows.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return riga;
	}

	protected List<YCatasteObject> recuperaCataste() {
		List<YCatasteObject> cataste = new ArrayList<YCatasteObject>();
		String sql = "SELECT * "
				+ "FROM OPENQUERY([CALSRVPROD],  "
				+ "' "
				+ "    SELECT *  "
				+ "    FROM [AppSimon].[dbo].[YCATASTE_V01] "
				+ "') AS C "
				+ "WHERE NOT EXISTS ( "
				+ "    SELECT 1 "
				+ "    FROM SOFTRE.YUDS_VEN_TES U "
				+ "    WHERE C.CATASTA = U.ID_UDS "
				+ ")";
		ResultSet rs = null;
		YCatasteResultSetIterator iterator = null;
		PreparedStatement ps = null;
		try {
			ps = ConnectionManager.getCurrentConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			iterator = new YCatasteResultSetIterator(rs);
			while (iterator.hasNext()) {
				cataste.add((YCatasteObject) iterator.next());
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		} finally {
			try {
				if (iterator != null)
					iterator.closeCursor();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return cataste;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YGenCatasteUds";
	}

}
