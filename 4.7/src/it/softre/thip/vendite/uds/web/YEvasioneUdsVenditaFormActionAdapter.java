package it.softre.thip.vendite.uds.web;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebElement;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;
import com.thera.thermfw.web.servlet.FormActionAdapter;

public class YEvasioneUdsVenditaFormActionAdapter extends FormActionAdapter{

	private static final long serialVersionUID = 1L;

	public static final String EVADI_UDS = "EVADI_UDS";
	
	public static final String CONFERMA_EVASIONE = "CONFERMA_EVASIONE";
	
	protected static String EVADI_UDS_IMG = "it/thera/thip/base/comuniVenAcq/image/AggiornaGriglia.gif";
	
	protected static String EVADI_UDS_RES= "it/softre/thip/vendite/uds/resources/YUdsVendita";
	
	@SuppressWarnings("rawtypes")
	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		Iterator iterBtn = toolBar.getButtons().iterator();
		WebToolBarButton saveDat = null;
		while(iterBtn.hasNext()) {
			WebElement btn = (WebElement) iterBtn.next();
			if(btn.getName().equals("SaveScreenData"))
				saveDat = ((WebToolBarButton)btn);
		}
		toolBar.getButtons().clear();
		WebToolBarButton evasioneUds = new WebToolBarButton(EVADI_UDS, "action_submit", "errorsFrame"
				, "no", EVADI_UDS_RES, EVADI_UDS, EVADI_UDS_IMG, EVADI_UDS, "single", false);
		toolBar.addButton(evasioneUds);
		toolBar.addButton(saveDat);
	}

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String azione = getAzione(se);
		if(azione.equals(EVADI_UDS)) {
			String data = getStringParameter(se.getRequest(), "DataDocumento");
			String idCliente = getStringParameter(se.getRequest(), "RCliente");
			String idSerie = getStringParameter(se.getRequest(), "RSerie");
			String idCausale = getStringParameter(se.getRequest(), "RCauDocTes");
			String dataRifIntestatario = getStringParameter(se.getRequest(), "DataRifIntestatario");
			String numeroRifIntestatario = getStringParameter(se.getRequest(), "NumeroRifIntestatario");
			String url = se.getServletPath() + "it.softre.thip.vendite.uds.web.YEvasioneUdsVendita?thAction="+CONFERMA_EVASIONE;
			url += "&Data="+data+"&IdCliente="+idCliente+"&IdSerie="+idSerie+"&IdCausale="+idCausale+"&DataRifIntestatario="+dataRifIntestatario;
			url += "&NumeroRifIntestatario="+numeroRifIntestatario;
			url += "&thClassName="+cadc.getClassName();
			se.sendRequest(getServletContext(), url, false);
		}else {
			super.otherActions(cadc, se);
		}
	}

	protected String getAzione(ServletEnvironment se){
		return getStringParameter(se.getRequest(), "thAction").toUpperCase();
	}
}
