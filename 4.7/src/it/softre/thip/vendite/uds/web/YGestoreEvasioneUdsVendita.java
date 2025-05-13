package it.softre.thip.vendite.uds.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.servlet.BaseServlet;

import it.thera.thip.base.comuniVenAcq.GestoreEvasione;
import it.thera.thip.base.comuniVenAcq.ParamRigaBase;
import it.thera.thip.base.comuniVenAcq.web.EvasioneOrdiniConst;
import it.thera.thip.vendite.ordineVE.ParamRigaPrmDocEvaVen;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 12/05/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 71XXX    12/05/2025  DSSOF3   Prima stesura
 */

public class YGestoreEvasioneUdsVendita extends GestoreEvasione {

	public static YGestoreEvasioneUdsVendita get() {
		YGestoreEvasioneUdsVendita instance = (YGestoreEvasioneUdsVendita)GestoreEvasione.getCurrentGestoreEvasione(YGestoreEvasioneUdsVendita.class);
		if (instance == null) {
			instance = (YGestoreEvasioneUdsVendita) Factory.createObject(YGestoreEvasioneUdsVendita.class);
			addGestoreEvasione(instance);
		}
		return instance;
	}

	@SuppressWarnings("rawtypes")
	public List getRigheSelezionate(ServletEnvironment se) {
		List lista = new ArrayList();
		ParamRigaPrmDocEvaVen pr = ParamRigaPrmDocEvaVen.create();
		int i = -1;
		String forzaStatoAvanzamento = BaseServlet.getStringParameter(se.getRequest() , EvasioneOrdiniConst.P_FORZA_STATO_AVANZAMENTO);
		Enumeration params = se.getRequest().getParameterNames();
		while (params.hasMoreElements()) {
			String p = (String) params.nextElement();
			String value = BaseServlet.getStringParameter(se.getRequest(),p);
			if (p.startsWith(EvasioneOrdiniConst.P_RIGA_DOCUMENTO)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_RIGA_DOCUMENTO);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_RIGA_ESTRATTA)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_RIGA_ESTRATTA);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iEstratta = true;
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_RIGA_SALDATA)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_RIGA_SALDATA);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iSaldo = true;
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_STATO_DEFINITIVO)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_STATO_DEFINITIVO);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iDefinitivo = true;
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_DESCRIZIONE_ARTICOLO)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_DESCRIZIONE_ARTICOLO);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iDescrizioneArticolo = value;
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_RIGA_FORZATA)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_RIGA_FORZATA);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iForzaEvasione = true;
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_DASPEDIRE_V)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_DASPEDIRE_V);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iDaSpedireV = ParamRigaBase.getQta(value);
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_DASPEDIRE_M)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_DASPEDIRE_M);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iDaSpedireM = ParamRigaBase.getQta(value);
			}

			else if (p.startsWith(EvasioneOrdiniConst.P_DASPEDIRE_SEC_M)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_DASPEDIRE_SEC_M);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iDaSpedireSecM = ParamRigaBase.getQta(value);
			}
			else if (p.startsWith(EvasioneOrdiniConst.P_RICALCOLA)) {
				i = getIndiceRiga(p, EvasioneOrdiniConst.P_RICALCOLA);
				pr = (ParamRigaPrmDocEvaVen)pr.getFromLista(i, lista);
				pr.iRicalcola = true;
			}

			if (i >= 0) {
				if (forzaStatoAvanzamento == null || forzaStatoAvanzamento.trim().equals("")) {
					pr.iForzaStatoAvanzamento = false;
				}
				else {
					if (BaseServlet.getStringParameter(se.getRequest() , EvasioneOrdiniConst.AZIONE_CAMBIA_STATO) != null) {
						pr.iForzaStatoAvanzamento = true;
					}
				}
				pr.addToLista(lista);
			}
			i = -1;
		}
		return lista;
	}

	protected int getIndiceRiga(String p, String name) {
		int i = 0;
		if (p.startsWith(name)) {
			String sI = p.substring(name.length(), p.length());
			try {
				i = Integer.parseInt(sI);
			}
			catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return i;
	}
}
