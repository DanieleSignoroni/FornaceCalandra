package it.softre.thip.vendite.uds.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.web.ServletEnvironment;

import it.softre.thip.vendite.uds.YAlertUtilsVendite;
import it.softre.thip.vendite.uds.YUdsVendita;
import it.thera.thip.base.comuniVenAcq.web.EvasioneOrdiniConst;
import it.thera.thip.base.documenti.web.DocumentoCambiaJSP;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.documenti.web.DocumentoDatiSessione;

public class YEvasioneUdsVendita extends DocumentoCambiaJSP {

	private static final long serialVersionUID = 1L;

	public static final String EVADI_UDS = "EVADI_UDS";

	public static final String CONFERMA_EVASIONE = "CONFERMA_EVASIONE";

	public static final String REFRESH_GRID = "REFRESH_GRID"; 

	@Override
	public void eseguiAzioneSpecifica(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException, SQLException {
		String azione = getAzione(se);
		if(azione.equals(EVADI_UDS)) {
			evadiUds(se,cadc,docBODC,datiSessione);
		}else if(azione.equals(CONFERMA_EVASIONE)) {
			confermaEvasione(se,cadc,docBODC,datiSessione);
		}else {
			super.eseguiAzioneSpecifica(se, cadc, docBODC, datiSessione);
		}
	}

	private void evadiUds(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException {
		String className = se.getRequest().getParameter(CLASS_NAME);
		docBODC.initialize(className, true, getCurrentLockType(se));
		String chiaviSel[] = (String[]) se.getRequest().getAttribute("ChiaviSelEvasioneUdsVendita");
		if(chiaviSel == null) {
			ErrorMessage em = new ErrorMessage("YSOFTRE001","Non sono state selezionate UDS");
			se.addErrorMessage(em);
			se.sendRequest(getServletContext(), "com/thera/thermfw/common/InfoAreaHandler.jsp", false);
			return;
		}
		String cliente = getCliente(chiaviSel[0]);
		if(chiaviSel != null) {
			boolean isOk = YAlertUtilsVendite.checkCliente(chiaviSel);
			if(isOk) {
				ArrayList<String> chiaviUdsEvase = YAlertUtilsVendite.chiaviUdsEvase(chiaviSel);
				if(!chiaviUdsEvase.isEmpty()) {
					ErrorMessage em = new ErrorMessage("YSOFTRE001","Le seguenti UDS sono già state evase " + getMsgEvasione(chiaviUdsEvase));
					se.addErrorMessage(em);
				}
			}else {
				ErrorMessage em = new ErrorMessage("YSOFTRE001","Non e' possibile evadere UDS con clienti diversi");
				se.addErrorMessage(em);
			}
			if(cliente == null) {
				ErrorMessage em = new ErrorMessage("YSOFTRE001","Non e' possibile evadere UDS senza cliente");
				se.addErrorMessage(em);
			}
			checkChiavi(chiaviSel,se);
			if(se.isErrorListEmpity()) {
				it.softre.thip.vendite.uds.YEvasioneUdsVendita udsVenBO = null;
				udsVenBO = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) docBODC.getBo();
				completaEvasione(udsVenBO,se);
				docBODC.setBo(udsVenBO);
				YDatiSessioneEvasioneUdsVendita datiSessioneEvasione = (YDatiSessioneEvasioneUdsVendita) Factory.createObject(YDatiSessioneEvasioneUdsVendita.class);
				datiSessioneEvasione.setDocumentoBO(docBODC.getBo());
				datiSessioneEvasione.setNavigatore(docBODC.getNavigatore());
				datiSessioneEvasione.salvaInSessione(se);
				datiSessioneEvasione.setIdCliente(cliente);
				datiSessioneEvasione.setChiaviSel(chiaviSel);
				datiSessioneEvasione.setBo(udsVenBO);
				boolean daEstratto = (Boolean) se.getRequest().getAttribute("DaEstratto");
				datiSessioneEvasione.setDaEstratto(daEstratto);
				se.getRequest().setAttribute(DocumentoDataCollector.CARICA_DA_SESSIONE, "true");
				String url = "it/softre/thip/vendite/uds/YEvasioneUdsVendita.jsp?Cliente="+cliente;
				String params = "&" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" +(String) se.getRequest().getAttribute(DocumentoDatiSessione.CHIAVE_DATI_SESSIONE);
				params += "&thAction=" + EvasioneOrdiniConst.AZIONE_CONFERMA_GUIDA;
				url += params;
				executeJSOpenAction(se, url, docBODC);
			}else {
				se.sendRequest(getServletContext(), "com/thera/thermfw/common/InfoAreaHandler.jsp", false);
			}
		}
	}

	private void completaEvasione(it.softre.thip.vendite.uds.YEvasioneUdsVendita udsVenBO, ServletEnvironment se) {
		String chiaviSel[] = (String[]) se.getRequest().getAttribute("ChiaviSelEvasioneUdsVendita");
		String cliente = getCliente(chiaviSel[0]);
		udsVenBO.setRCliente(cliente);
	}

	public void confermaEvasione(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException {
		String className = se.getRequest().getParameter(CLASS_NAME);
		docBODC.initialize(className, true, getCurrentLockType(se));
		setValues(cadc, docBODC, se);
		docBODC.setOnBORecursive();
		YDatiSessioneEvasioneUdsVendita datiSessioneEvasione = (YDatiSessioneEvasioneUdsVendita) DocumentoDatiSessione.getDocumentoDatiSessione(se);
		//String[] chiaviSel = datiSessioneEvasione.getChiaviSel();
		if(docBODC.check() == DocumentoDataCollector.OK) {
			//			it.softre.thip.vendite.uds.YEvasioneUdsVendita bo = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) docBODC.getBo();
			//			if(chiaviSel != null && bo.getRCliente() != null && bo.getRCauDocTes() != null && bo.getRSerie() != null) {
			//				se.getSession().removeAttribute("chiaviUdsVenditaEvasione");
			//				Object[] ogg = (Object[]) it.softre.thip.vendite.uds.YEvasioneUdsVendita.generaDocumentoVendita(
			//						chiaviSel,
			//						bo.getDataDocumento(),
			//						bo.getRCauDocTes(), 
			//						bo.getRSerie(),
			//						bo.getRCliente(),
			//						bo.getDataRifIntestatario(),
			//						bo.getNumeroRifIntestatario());
			//				ErrorList errori = (ErrorList)ogg[0];
			//				String key = (String) ogg[1];
			//				if(errori.getErrors().isEmpty()) {
			//					boolean daEstratto = datiSessioneEvasione.isDaEstratto();
			//					String url = "it/softre/thip/vendite/uds/YAperturaDocVe.jsp?ChiaveDocVe="+key;
			//					url += "&DaEstratto="+(daEstratto == true ? "true" : "false")+"";
			//					se.sendRequest(getServletContext(), url, false);
			//				}else {
			//					se.addErrorMessages(errori.getErrors());
			//					se.sendRequest(getServletContext(), "com/thera/thermfw/common/ErrorListHandler.jsp", false);
			//				}
			//			}
			it.softre.thip.vendite.uds.YEvasioneUdsVendita bo = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) docBODC.getBo();
			datiSessioneEvasione.setBo(bo);
			datiSessioneEvasione.getBo().setRelclienteKey(KeyHelper.buildObjectKey(new String[] {datiSessioneEvasione.getBo().getIdAzienda(),datiSessioneEvasione.getBo().getRCliente()}));
			datiSessioneEvasione.getBo().setCausaleKey(KeyHelper.buildObjectKey(new String[] {datiSessioneEvasione.getBo().getIdAzienda(),datiSessioneEvasione.getBo().getRCauDocTes()}));
			se.getRequest().setAttribute(DocumentoDataCollector.CARICA_DA_SESSIONE, "true");
			se.getRequest().setAttribute(DocumentoDatiSessione.CHIAVE_DATI_SESSIONE,
					datiSessioneEvasione.getChiaveDatiSessione());
			String newUrl = YEvasioneUdsVenditaNavigazioneWeb.cJspGestoreGriglia;
			String url =  "it/softre/thip/vendite/uds/DispatcherEstrazioneEvasione.jsp";
			String params = "?" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" +datiSessioneEvasione.getChiaveDatiSessione();
			newUrl = "?thNewUrl=" + URLEncoder.encode(newUrl, "UTF-8");
			newUrl += params;
			url += newUrl;
			se.sendRequest(getServletContext(),url, false);
		}else {
			se.addErrorMessages(docBODC.getErrorList().getErrors());
			se.sendRequest(getServletContext(), "com/thera/thermfw/common/ErrorListHandler.jsp", false);
		}
	}

	private boolean checkChiavi(String[] chiaviSel, ServletEnvironment se) {
		try {
			for(int i = 0 ; i < chiaviSel.length; i++) {
				YUdsVendita udsVen = (YUdsVendita)
						YUdsVendita.elementWithKey(YUdsVendita.class, chiaviSel[i], 0);
				if(udsVen.getRigheUDSVendita().size() == 0
						&& !udsVen.esistonoUdsFiglieCollegate()) {
					ErrorMessage em = new ErrorMessage("YSOFTRE001","L'UDS " + chiaviSel[i] + " non ha righe, non puo' essere evasa");
					se.addErrorMessage(em);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return false;
	}

	public void executeJSOpenAction(ServletEnvironment se, String url, DocumentoDataCollector docBODC) {
		try {
			PrintWriter out = se.getResponse().getWriter();
			out.println("  <script language=\'JavaScript1.2\'>");
			String initialActionAdapter = getStringParameter(se.getRequest(), "thInitialActionAdapter");
			if(initialActionAdapter != null) {
				out.println("    var errViewObj = window.parent.eval(window.parent.errorsViewName);");
				out.println("    errViewObj.setMessage(null);");
				out.println("    parent.enableFormActions();");
			}
			else {
				out.println("window.parent.ErVwinfoarea.clearDisplay();");
				out.println("window.parent.enableGridActions();");
			}
			if (url.startsWith("/"))
				url = url.substring(1);
			out.println("    var url = '" + se.getWebApplicationPath() + url + "'");
			out.println("    var winFeature = 'width=800, height=700, resizable=yes';");
			out.println("    var winName = '" + String.valueOf(System.currentTimeMillis()) + "';");
			out.println("    var winrUrl = window.open(url, winName, winFeature);");
			//			if(( (Boolean) se.getRequest().getAttribute("DaEstratto") != null &&
			//					((Boolean) se.getRequest().getAttribute("DaEstratto")).booleanValue() == false) && se.isErrorListEmpity()) {
			//				out.println("parent.runAction('" + REFRESH_GRID + "','none','same','no');");
			//			}
			out.println("  </script>");
		}
		catch (Exception ex) {
			ex.printStackTrace(Trace.excStream);
		}
	}

	public String getMsgEvasione(ArrayList<String> chiaviGiaEvase) {
		String ret = "";
		for(int i = 0; i < chiaviGiaEvase.size(); i++) {
			ret += " - " + chiaviGiaEvase.get(i) + " \n";
		}
		return ret;
	}

	public String getCliente(String chiave) {
		try {
			YUdsVendita uds = (YUdsVendita) YUdsVendita.elementWithKey(YUdsVendita.class, chiave,0);
			if(uds != null) {
				return uds.getRCliente();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
