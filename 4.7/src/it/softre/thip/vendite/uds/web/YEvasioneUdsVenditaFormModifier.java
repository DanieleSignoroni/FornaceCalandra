package it.softre.thip.vendite.uds.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BaseBOComponentManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.web.servlet.GridActionAdapter;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.documenti.web.DocumentoAbsFormModifier;
import it.thera.thip.vendite.generaleVE.CausaleDocumentoVendita;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 09/07/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    09/07/2025  DSSOF3   Prima stesura
 */

public class YEvasioneUdsVenditaFormModifier extends DocumentoAbsFormModifier{

	@Override
	public int getTipoJSP() {
		return 0;
	}

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		String azione = getServletEnvironment().getRequest().getParameter(GridActionAdapter.ACTION);
		super.writeHeadElements(out);
		YEvasioneUdsVenditaDataCollector bodc = (YEvasioneUdsVenditaDataCollector) this.getBODataCollector();
		bodc.updateHandlingModeOnComponentManagers();
		BaseBOComponentManager cm = bodc.getComponentManager("Cliente");
		if (cm != null) {
			cm.setReadOnly(true);
		}
		it.softre.thip.vendite.uds.YEvasioneUdsVendita evas = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) getBODataCollector().getBo();
		it.softre.thip.vendite.uds.YEvasioneUdsVendita eva = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) this.iDatiSessione.getDocumentoBO();
		String k = Azienda.getAziendaCorrente()+KeyHelper.KEY_SEPARATOR+eva.getRCliente();
		ClienteVendita cliente = (ClienteVendita) Factory.createObject(ClienteVendita.class);
		cliente.setKey(k);
		boolean isOk;
		try {
			isOk = cliente.retrieve();
			if(isOk) {
				CausaleDocumentoVendita cauDef = cliente.getCausaleDocumentoVendita();
				if(cauDef != null && azione.equals(YUdsVenditaGridActionAdapter.EVADI_UDS)) {
					evas.setRCauDocTes(cauDef.getIdCausaleDocumentoVen());
				}
				evas.setRCliente(cliente.getIdCliente());
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		evas.setAzione(eva.getAzione());
		getBODataCollector().setBo(evas);
		getBODataCollector().setOnBORecursive();
	}

	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {
		super.writeFormEndElements(out);
		out.println("<script language=\"JavaScript1.2\">");
		it.softre.thip.vendite.uds.YEvasioneUdsVendita bo = (it.softre.thip.vendite.uds.YEvasioneUdsVendita) getBODataCollector().getBo();
		if(bo.getAzione().equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) {
			 out.println("document.forms[0].IdMagazzinoTrasferimento.parentNode.parentNode.style.display = 'revert';");
		}
		out.println("</script>");
	}

}
