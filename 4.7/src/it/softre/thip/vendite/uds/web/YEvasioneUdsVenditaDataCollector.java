package it.softre.thip.vendite.uds.web;

import com.thera.thermfw.collector.EnhBOComponentManager;

import it.thera.thip.base.documenti.TipoGestione;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.documenti.web.DocumentoNavigazioneWeb;

public class YEvasioneUdsVenditaDataCollector extends DocumentoDataCollector {

	@Override
	protected String getNavigatoreName() {
		return "it.softre.thip.vendite.uds.web.YEvasioneUdsVenditaNavigazioneWeb";
	}
	
	@Override
	public DocumentoNavigazioneWeb getNavigatore() {
		return super.getNavigatore();
	}

	@Override
	public void impostaSecondoCausale() {
		
	}
	
	@Override
	public void updateHandlingModeOnComponentManagers() {
		super.updateHandlingModeOnComponentManagers();
		EnhBOComponentManager p0 = (EnhBOComponentManager)getComponentManager("RCliente");
		impostaHandlingModeOnComponentManagers(p0, TipoGestione.SOLO_VISUALIZZ);
	}
	

}
