package it.softre.thip.vendite.uds.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebForm;

import it.softre.thip.vendite.uds.YUdsVendita;
import it.thera.thip.base.comuniVenAcq.web.EvasioneOrdiniConst;
import it.thera.thip.base.documenti.web.DocumentoCambiaJSP;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.documenti.web.DocumentoDatiSessione;
import it.thera.thip.base.documenti.web.DocumentoGridActionAdapter;
import it.thera.thip.base.documenti.web.DocumentoNavigazioneWeb;
import it.thera.thip.cs.ThipException;
import it.thera.thip.vendite.ordineVE.web.DocEvaVenNavigazioneWeb;

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

public class YEvasioneUdsVendita extends DocumentoCambiaJSP implements EvasioneOrdiniConst {

	private static final long serialVersionUID = 1L;

	public static final String EVADI_UDS = "EVADI_UDS";

	public static final String CONFERMA_EVASIONE = "CONFERMA_EVASIONE";

	public static final String REFRESH_GRID = "REFRESH_GRID"; 

	@Override
	public void eseguiAzioneSpecifica(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException, SQLException {
		String azione = getAzione(se);
		if(azione.equals(EVADI_UDS) || azione.equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) {
			evadiUds(se,cadc,docBODC,datiSessione);
		}else if(azione.equals(CONFERMA_EVASIONE)) {
			confermaEvasione(se,cadc,docBODC,datiSessione);
		}else if(azione.equals(AZIONE_EVADI_DOCUMENTO)) {
			evadiDocumentoGriglia(se,cadc,docBODC,datiSessione);
		}else {
			super.eseguiAzioneSpecifica(se, cadc, docBODC, datiSessione);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void evadiDocumentoGriglia(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC,
			DocumentoDatiSessione datiSessione) throws ServletException, IOException, SQLException {
		boolean erroriPresenti = false;
		List errors = new ArrayList();
		String action = super.getAzione(se);
		it.softre.thip.vendite.uds.YEvasioneUdsVendita docInUso = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) ( (YDatiSessioneEvasioneUdsVendita) datiSessione).getBo();
		//docBODC = (DocumentoDataCollector) Factory.createObject(DocumentoDataCollector.class);
		docBODC.initialize("YEvasioneUdsVendita", true, getCurrentLockType(se));
		docBODC.setBo(docInUso);
		List righeSel = YGestoreEvasioneUdsVendita.get().getRigheSelezionate(se);
		try {
			docInUso.aggiornaRigheDocumento(righeSel);
		} catch (ThipException e) {
			e.printStackTrace(Trace.excStream);
		}
		docInUso.setGeneraDocumento(true);
		if (docBODC.save(true) != BODataCollector.OK) {
			errors.addAll(docBODC.getErrorList().getErrors());
			erroriPresenti = true;
		}
		se.addErrorMessages(errors);
		se.getRequest().setAttribute(CHIAVE_DOC_SALVATO, docBODC.getBo().getKey());
		closeAction(docBODC, se);
		se.getRequest().setAttribute(ERRORI_PRESENTI, new Boolean(erroriPresenti));
		se.getRequest().setAttribute(AZIONE_RICHIESTA, getAzioneDopoCambio(se, erroriPresenti));
		se.getRequest().setAttribute(DocumentoDatiSessione.CHIAVE_DATI_SESSIONE, datiSessione.getChiaveDatiSessione());
		String url = null;
		boolean isInclude = false;
		String dispatcher = DocEvaVenNavigazioneWeb.cJspDispatcherEstrazione;
		String newUrl = DocEvaVenNavigazioneWeb.cJspGestoreGriglia;
		String params = "?" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" +
				(String) se.getRequest().getAttribute(DocumentoDatiSessione.CHIAVE_DATI_SESSIONE);
		if (docInUso.isOnDB()) {
			params += "&Mode=" + WebForm.UPDATE + "&Key=" + docInUso.getKey();
		}
		if (erroriPresenti) {
			String errorHandler = DocEvaVenNavigazioneWeb.cJspErrorListHandler;
			String parAdd = "&" + DocumentoGridActionAdapter.NON_PASSA_DA_NUOVO + "=true";
			se.getRequest().setAttribute(DocumentoCambiaJSP.PARAMETRI_ADDIZIONALI, parAdd);
			params = "thClassName" + "=" + "YEvasioneUdsVendita";
			errorHandler += "?" + params;
			url = errorHandler;
		}
		else {
			newUrl = DocEvaVenNavigazioneWeb.cJspGestoreGriglia;
			newUrl += params;
			params = "thNewUrl=" + URLEncoder.encode(newUrl,"UTF-8");
			if (action.equals(EvasioneOrdiniConst.AZIONE_CONFERMA_CHIUDI_GRIGLIA) ||
					action.equals(EvasioneOrdiniConst.AZIONE_EVADI_DOCUMENTO)) {
				String initialActionAdapter = Factory.getName("it.thera.thip.vendite.documentoVE.web.DocumentoVenditaGridActionAdapter",Factory.CLASS);

				String className = "DocumentoVendita";
				String key = docInUso.getKey();
				String servletPath = se.getServletPath();
				if (servletPath.startsWith("/")) {
					servletPath = servletPath.substring(1);
				}
				if (servletPath.endsWith("/")) {
					servletPath = servletPath.substring(0, servletPath.length() - 1);
				}
				String thAction = DocumentoGridActionAdapter.UPDATE_RIGHE;
				String urlEstrattoDoc = servletPath + "/" + initialActionAdapter +
						"?thAction=" + thAction +
						"&thClassName=" + className +
						"&ObjectKey=" + URLEncoder.encode(key,"UTF-8") +
						"&" + DocumentoNavigazioneWeb.TIPO_FORM + "=" +
						DocumentoNavigazioneWeb.TF_DETTAGLI +
						"&" + DocumentoNavigazioneWeb.MODO_FORM + "=" +
						DocumentoNavigazioneWeb.MF_COMPLETA;
				params = "thNewUrl=" + java.net.URLEncoder.encode(newUrl,"UTF-8") +
						"&thCloseParent=" + java.net.URLEncoder.encode(urlEstrattoDoc,"UTF-8");
				String ritornoDaRiga = getStringParameter(se.getRequest(),"ritornoDaRiga");
				if (ritornoDaRiga != null && ritornoDaRiga.equalsIgnoreCase("true")) {
					params += "&ritornoDaRiga=" + ritornoDaRiga;
				}
			}
			params += "&thAction=" + action;
			url = dispatcher + "?" + params;
		}
		se.sendRequest(getServletContext(), url, isInclude);
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
		List<YUdsVendita> list = recuperaUdsVenditaDaChiavi(chiaviSel);
		String cliente = list.get(0).getRCliente();
		if(chiaviSel != null) {
			ErrorMessage em = checkCongruenzaCliente(list);
			if(em != null) {
				se.addErrorMessage(em);
			}
			em = checkUdsEvase(list,getAzione(se));
			if(em != null) {
				se.addErrorMessage(em);
			}
			em = checkPresenzaRigheUds(list);
			if(em != null) {
				se.addErrorMessage(em);
			}
			if(se.isErrorListEmpity()) {
				it.softre.thip.vendite.uds.YEvasioneUdsVendita udsVenBO = null;
				udsVenBO = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) docBODC.getBo();
				completaEvasione(udsVenBO,se);
				udsVenBO.setAzione(getAzione(se));
				docBODC.setBo(udsVenBO);
				YDatiSessioneEvasioneUdsVendita datiSessioneEvasione = (YDatiSessioneEvasioneUdsVendita) Factory.createObject(YDatiSessioneEvasioneUdsVendita.class);
				datiSessioneEvasione.setDocumentoBO(docBODC.getBo());
				datiSessioneEvasione.setNavigatore(docBODC.getNavigatore());
				datiSessioneEvasione.setIdCliente(cliente);
				datiSessioneEvasione.setChiaviSel(chiaviSel);
				datiSessioneEvasione.setBo(udsVenBO);
				boolean daEstratto = (Boolean) se.getRequest().getAttribute("DaEstratto");
				datiSessioneEvasione.setDaEstratto(daEstratto);
				datiSessioneEvasione.salvaInSessione(se);
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

	public ErrorMessage checkPresenzaRigheUds(List<YUdsVendita> list) {
		for(YUdsVendita uds : list) {
			if(uds.getRigheUDSVendita().size() == 0
					&& !uds.esistonoUdsFiglieCollegate()) {
				return  new ErrorMessage("YSOFTRE001","L'UDS " + uds.getKey() + " non ha righe, non puo' essere evasa");
			}
		}
		return null;
	}

	public ErrorMessage checkUdsEvase(List<YUdsVendita> list, String azione) {
		for(YUdsVendita uds : list) {
			//.Se sto lanciando l'azione di trasferimento devo controllare che l'uds
			//.non sia stata gia' evasa o trasferita
			if(azione.equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) {
				if((uds.getRAnnoDocVen() != null && uds.getRNumDocVen() != null) || uds.getDocumentoVenditaTrasf() != null) {
					return new ErrorMessage("YSOFTRE001","Non e' possibile evadere un UDS gia' evasa");
				}
			}else {
				//				if(uds.getStatoEvasione() == YUdsVendita.GENERATO_DOCUMENTO || uds.getStatoEvasione() == YUdsVendita.VERSATO_A_MAGAZZINO) {
				//					return new ErrorMessage("YSOFTRE001","Non e' possibile evadere un UDS gia' evasa");
				//				}
				if(uds.getRAnnoDocVen() != null && uds.getRNumDocVen() != null) {
					return new ErrorMessage("YSOFTRE001","Non e' possibile evadere un UDS gia' evasa");
				}
			}
		}
		return null;
	}

	public ErrorMessage checkCongruenzaCliente(List<YUdsVendita> list) {
		if(list.size() == 0)
			return new ErrorMessage("BAS0000000");
		String firstCliente = list.get(0).getRCliente();
		if(firstCliente == null)
			return new ErrorMessage("BAS0000000");
		for(YUdsVendita uds : list) {
			if(uds.getRCliente() == null) {
				return new ErrorMessage("BAS0000000");
			}else if(!uds.getRCliente().equals(firstCliente)){
				return new ErrorMessage("YSOFTRE001","Non e' possibile evadere UDS con clienti diversi");
			}
		}
		return null;
	}

	public List<YUdsVendita> recuperaUdsVenditaDaChiavi(String[] keys){
		List<YUdsVendita> list = new ArrayList<YUdsVendita>();
		for(String key : keys) {
			try {
				list.add((YUdsVendita) YUdsVendita.elementWithKey(YUdsVendita.class, key, PersistentObject.NO_LOCK));
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return list;
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
