package it.softre.thip.vendite.uds.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.collector.BaseBOComponentManager;

import it.thera.thip.base.documenti.web.DocumentoAbsFormModifier;

public class YEvasioneUdsVenditaFormModifier extends DocumentoAbsFormModifier{

	@Override
	public int getTipoJSP() {
		return 0;
	}

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		it.softre.thip.vendite.uds.YEvasioneUdsVendita eva = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) this.iDatiSessione.getDocumentoBO();
		YEvasioneUdsVenditaDataCollector bodc = (YEvasioneUdsVenditaDataCollector) this.getBODataCollector();
		it.softre.thip.vendite.uds.YEvasioneUdsVendita bo = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) bodc.getBo();
		bo.setRCliente(eva.getRCliente());
		bodc.loadAttValue();
		bodc.updateHandlingModeOnComponentManagers();
		BaseBOComponentManager cm = bodc.getComponentManager("Cliente");
		if (cm != null) {
			cm.setReadOnly(true);
		}
	}
	
	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {
		super.writeFormEndElements(out);
		it.softre.thip.vendite.uds.YEvasioneUdsVendita eva = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) this.iDatiSessione.getDocumentoBO();
		out.println("<script>");
		out.println("document.getElementById('RCliente').value = '"+eva.getRCliente()+"';");
		out.println("</script>");
	}

}
