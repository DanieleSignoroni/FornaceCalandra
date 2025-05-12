package it.softre.thip.vendite.uds.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.collector.BaseBOComponentManager;

import it.thera.thip.base.documenti.web.DocumentoAbsFormModifier;
import it.thera.thip.base.documenti.web.DocumentoDatiSessione;

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

public class YEvasioneUdsGrigliaFormModifier extends DocumentoAbsFormModifier {

	@Override
	public int getTipoJSP() {
		return 0;
	}

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		BODataCollector bodc = super.getBODataCollector();
		YDatiSessioneEvasioneUdsVendita dev = (YDatiSessioneEvasioneUdsVendita) DocumentoDatiSessione.
				getDocumentoDatiSessione(super.getServletEnvironment());
		if (dev.getDocumentoBO() != null) {
			bodc.setBo(dev.getBo());
		}
		try {
			BaseBOComponentManager cm = bodc.getComponentManager("RCliente");
			if (cm != null) {
				cm.setReadOnly(true);
			}
			cm = bodc.getComponentManager("RagioneSocialeCli");
			if (cm != null) {
				cm.setReadOnly(true);
			}
			cm = bodc.getComponentManager("RCauDocTes");
			if (cm != null) {
				cm.setReadOnly(true);
			}
			cm = bodc.getComponentManager("CausaleDescrizione");
			if (cm != null) {
				cm.setReadOnly(true);
			}
			cm = bodc.getComponentManager("DataDocumento");
			if (cm != null) {
				cm.setReadOnly(true);
			}
		}
		catch (Throwable ex) {
			ex.printStackTrace(Trace.excStream);
		}
	}

}
