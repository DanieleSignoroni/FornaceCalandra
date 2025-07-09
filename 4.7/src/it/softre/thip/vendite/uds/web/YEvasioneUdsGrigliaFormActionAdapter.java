package it.softre.thip.vendite.uds.web;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebElement;
import com.thera.thermfw.web.WebForm;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;

import it.thera.thip.base.comuniVenAcq.web.EvasioneOrdiniConst;
import it.thera.thip.base.documenti.web.DocumentoDatiSessione;
import it.thera.thip.base.documenti.web.DocumentoNuovoFormActionAdapter;

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

public class YEvasioneUdsGrigliaFormActionAdapter extends DocumentoNuovoFormActionAdapter implements EvasioneOrdiniConst {

	private static final long serialVersionUID = 1L;

	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws
	ServletException,
	IOException {
		String action = super.getAzione(se);

		YDatiSessioneEvasioneUdsVendita datiSessione = (YDatiSessioneEvasioneUdsVendita) DocumentoDatiSessione.getDocumentoDatiSessione(se);
		String modoIniziale = DocumentoDatiSessione.NEW;
		datiSessione.setModoIniziale(modoIniziale);
		datiSessione.salvaInSessione(se);

		if (action.equals(AZIONE_DETTAGLIO_RIGA) ||
				action.equals(AZIONE_DETTAGLIO_DISPONIBILITA) ||
				action.equals(AZIONE_MANUTENZIONE_RIGA) ||
				action.equals(AZIONE_VISUALIZZAZIONE_RIGA) ||
				action.equals(AZIONE_CONFERMA_RIGA_PRM) ||
				action.equals(AZIONE_ELIMINA_RIGA)||
				action.equals(AZIONE_AGGIORNA_GIADISP) ||  // fix 11473           
				action.equals(AZIONE_CONFIGURATORE_RIGA)) {  // fix 11415
			sendToRiga(cadc, se);
		}
		else if (action.equals(AZIONE_CONFERMA_GRIGLIA) ||
				action.equals(AZIONE_CONFERMA_CHIUDI_GRIGLIA) ||
				action.equals(AZIONE_AGGIORNA_GRIGLIA) ||
				action.equals(AZIONE_RICARICA_GRIGLIA) ||
				action.equals(AZIONE_CAMBIA_STATO) ||
				action.equals(AZIONE_EVADI_DOCUMENTO)) {
			sendToGriglia(cadc, se);
		}
		else if (action.equals(AZIONE_CONDIZIONI_GRIGLIA) ||
				action.equals(AZIONE_ANNULLA_GRIGLIA) ||
				action.equals(AZIONE_CONFERMA_GUIDA)) {
			sendToGuida(cadc, se);
		}
		else {
			super.otherActions(cadc, se);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		Iterator iter = toolBar.getButtons().iterator();
		while(iter.hasNext()) {
			WebElement elem = (WebElement) iter.next();
			if(elem instanceof WebToolBarButton
					&& ((((WebToolBarButton) elem).getAction().equals(EvasioneOrdiniConst.AZIONE_EVADI_DOCUMENTO))
							|| ((WebToolBarButton) elem).getAction().equals("SELEZIONA_TUTTO"))) {
			}else {
				iter.remove();
			}
		}
	}

	protected void sendToGuida(ClassADCollection cadc, ServletEnvironment se) throws
	ServletException,
	IOException {
		se.sendRequest(getServletContext(),
				se.getServletPath() + getServletNameGestoreGuida(), false);
	}

	protected void sendToGriglia(ClassADCollection cadc, ServletEnvironment se) throws
	ServletException,
	IOException {
		se.sendRequest(getServletContext(),
				se.getServletPath() + getServletNameGestoreGriglia(), false);
	}

	protected void sendToRiga(ClassADCollection cadc, ServletEnvironment se) throws
	javax.servlet.ServletException,
	IOException {
		se.sendRequest(getServletContext(),
				se.getServletPath() + getServletNameGestoreRiga(), false);
	}

	public static YDatiSessioneEvasioneUdsVendita getDatiSessioneEvasione(WebForm form) {
		YDatiSessioneEvasioneUdsVendita datiInSessione = null;
		datiInSessione = (YDatiSessioneEvasioneUdsVendita)DocumentoDatiSessione.getDocumentoDatiSessione(form.getServletEnvironment());
		return datiInSessione;
	}

	protected String getServletNameGestoreRiga() {
		return null;
	}

	protected String getServletNameGestoreGriglia() {
		return "it.softre.thip.vendite.uds.web.YEvasioneUdsVendita";
	}

	protected String getServletNameGestoreGuida() {
		return null;
	}
}
