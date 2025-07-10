package it.softre.thip.vendite.uds.web;

import java.io.IOException;
//import java.net.URLEncoder;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.web.WebJSTypeList;
import com.thera.thermfw.web.WebPullDownButton;
import com.thera.thermfw.web.WebPullDownButtonAction;

//import com.thera.thermfw.web.WebElement;
//import com.thera.thermfw.web.WebFormModifier;

import it.softre.thip.vendite.uds.YUdsVendita;
import it.thera.thip.base.documenti.web.DocumentoEstrattoFormModifier;

/**
 * 
 * @author daniele.signoroni
 *	70687	06/05/2022	All'apertura della form lancio la Grid delle righe
 */

public class YUdsVenditaFormModifier extends DocumentoEstrattoFormModifier {

	public static final String RES_PERS = "it/softre/thip/vendite/uds/resources/YUdsVendita";

	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		out.println("<script language='JavaScript' type='text/javascript'>");
		//out.println("window.resizeTo(940,640)");
		out.println("</script>");
	}

	@Override
	public void writePulsantiBarraAzioniPers(JspWriter out) throws IOException {
		super.writePulsantiBarraAzioniPers(out);

		YUdsVendita uds = (YUdsVendita) getBODataCollector().getBo();

		//		out.println("<td nowrap=\"true\" height=\"0\">");
		//		out.println("<button name=\"Convalida\" id=\"Convalida\" onclick=\"convalidaYUdsVendita()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES_PERS, "btnConvalida") + "\">");
		//		out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"" + getIconaBarraAzioniStandard("it/thera/thip/base/documenti/images/Convalida.gif") + "\" >");
		//		out.println("</button>");
		//		out.println("</td>");
		if(uds.getDocumentoVendita() != null) {
			out.println("<td nowrap=\"true\" height=\"0\">");
			out.println("<button name=\"Regressione\" id=\"Regressione\" onclick=\"regressioneYUdsVendita()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES_PERS, "btnRegressione") + "\">");
			out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"" + getIconaBarraAzioniStandard("it/thera/thip/base/documenti/images/Regressione.gif") + "\" >");
			out.println("</button>");
			out.println("</td>");
		}

		//		out.println("<td nowrap=\"true\" height=\"0\">");
		//		out.println("<button name=\"Definitivo\" id=\"Definitivo\" onclick=\"azioneApriTestata()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES, "btnDefinitivo") + "\">");
		//		out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"" + getIconaBarraAzioniStandard("it/thera/thip/base/documenti/images/Definitivo.gif") + "\" >");
		//		out.println("</button>");
		//		out.println("</td>");

		//		out.println("<td nowrap=\"true\" height=\"0\">");
		//		out.println("<button name=\"Evasione\" id=\"Evasione\" onclick=\"evasioneUds()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES_PERS, "btnEvasione") + "\">");
		//		out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"" + getIconaBarraAzioniStandard("it/thera/thip/vendite/ordineVE/images/EvaOrdVenDir_24.gif") + "\" >");
		//		out.println("</button>");
		//		out.println("</td>");

		out.println("<td nowrap=\"true\" height=\"30px\">\n");
		WebPullDownButton pdb = new WebPullDownButton("thEvasioneDiretta", null, null, getIconaBarraAzioniStandard("it/thera/thip/vendite/ordineVE/images/EvaOrdVenDir_24.gif"), "evasioneUds()()", null);
		pdb.setParent(getWebForm());
		pdb.setWidth(widthBtnBarraAzioniStandard);
		pdb.setHeight("30px");
		pdb.setImageWidth(widthImgBarraAzioniStandard);
		pdb.setImageHeight("24px");     
		pdb.setText(ResourceLoader.getString(RES_PERS, "btnEvasione"));
		WebPullDownButtonAction actionEvasDiretta = new WebPullDownButtonAction("thEvasioneDiretta", null, null, null, "evasioneUds()", null);
		pdb.addAction(actionEvasDiretta);
		actionEvasDiretta.setText(ResourceLoader.getString(RES_PERS, "btnEvasione"));
		WebPullDownButtonAction actionEvasGuidata = new WebPullDownButtonAction("thEvasioneTrasferimento", null, null, null, "trasferisciUds()", null);
		actionEvasGuidata.setText(ResourceLoader.getString(RES_PERS, "TrasferisciUds"));
		pdb.addAction(actionEvasGuidata);
		pdb.write(out);

		out.println("<td nowrap=\"true\" height=\"0\">");
		out.println("<button name=\"StampaEtichetta\" id=\"StampaEtichetta\" onclick=\"stampaEtichetta()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES_PERS, "btnStampaEtichetta") + "\">");
		out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"it/softre/thip/vendite/uds/img/StampaEtic.gif\" >");
		out.println("</button>");
		out.println("</td>");
	}

	public void writeBodyStartElements(JspWriter out) throws IOException {
		super.writeBodyStartElements(out);
		//impostaURLGrigliaRighe(out);
	}

	public void writeFormStartElements(JspWriter out) throws IOException {
		super.writeFormStartElements(out);
		//da verificare
		//getBODataCollector().setOnBORecursive();
	}

	public void writeFormEndElements(JspWriter out) throws IOException {
		super.writeFormEndElements(out);
		out.println("<script language='JavaScript' type='text/javascript'>");
		//		YUdsVendita udsVE = (YUdsVendita) getBODataCollector().getBo();
		//		if(udsVE != null) {
		//			setBottoniGrid(udsVE, out);
		//	}
		//out.println("document.body.style.backgroundColor='#E8E8E8'");
		//		out.println("var grigliaRighe = document.getElementById('GrigliaRighe');");
		//		out.println("if (grigliaRighe != null) {");
		//		out.println("	grigliaRighe.src = URLGrigliaRighe;");
		//		out.println("}");
		out.println("setReadOnly(document.getElementById('RCliente'))");
		out.println("setReadOnly(document.getElementById('RelCliente$RagioneSociale'))");
		out.println("setReadOnly(document.getElementById('Note'))");
		out.println("document.getElementById('thRelClienteSearchBut').disabled = true");
		//out.println("document.getElementById('thRelClienteEditBut').disabled = true");
		out.println("</script>");
	}

	public void writeBodyEndElements(JspWriter out) throws IOException {
		out.println(
				"<script language=\"JavaScript1.2\" type=\"text/javascript\">" +
						// "parent.document.title = document.forms[0].document.title;" // Commented on fix 12090
						"parent.document.title = document.title;" +
						"</script>"
				);
		//Fix 12807 inizio
		out.println(WebJSTypeList.getImportForJSLibrary("it/thera/thip/base/documenti/DocumentoEstrattoEnd.js", getServletEnvironment().getRequest()));
		//Fix 12807 fine
		//Fix 21346 inizio
		initLabelSaldoRiapri(out);//Fix 21346
		//Fix 21346 fine

		//Fix 40412 - inizio
		// out.println("<script language=\"JavaScript1.2\" type=\"text/javascript\">");
		//out.println("getScadenze()");	     //45281
		//Fix 41181 - inizio
		//if (PersDatiGen.getCurrentPersDatiGen().getGesDocDgt()) 
		//	 out.println("getDocDgt()");
		//Fix 41181 - fine
		// out.println("</script>");
	}

	/*
	@SuppressWarnings("deprecation")
	private void impostaURLGrigliaRighe(JspWriter out) throws java.io.IOException {
		String servletPath = getServletEnvironment().getServletPath();
		if (servletPath.startsWith("/")) {
			servletPath = servletPath.substring(1);
		}

		String filtri = 
				"IdAzienda=" + getBODataCollector().get("IdAzienda").toString().trim() + ";" +
						"IdUds=" + getBODataCollector().get("IdUds").toString().trim() + ";";
		String urlGrigliaRighe = servletPath + "com.thera.thermfw.web.servlet.ShowGrid" + 
				"?thGridType=list" +
				"&thRestrictConditions=" + URLEncoder.encode(filtri) +
				"&ClassName=YUdsVenRig";

		out.println("<script language='JavaScript' type='text/javascript'>");
		out.println("var URLGrigliaRighe = '" + WebElement.formatStringForHTML(urlGrigliaRighe) + "'");
		out.println("</script>");
	}

	public void setBottoniGrid(YUdsVendita udsVE, JspWriter out) {
		try {
			out.println("document.getElementById('Definitivo').parentNode.style.display = 'none'");
			out.println("document.getElementById('Convalida').parentNode.style.display = 'none'");
			if(udsVE.getStatoEvasione() == '0') {
				out.println("document.getElementById('Regressione').parentNode.style.display = 'none'");
			}else if(udsVE.getStatoEvasione() == '3') {
				out.println("document.getElementById('FieldsetDoc').parentNode.style.visibility = 'unset'");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	 */
}
