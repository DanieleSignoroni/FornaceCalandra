package it.fornacecalandra.thip.vendite.uds;

import it.thera.thip.base.articolo.Articolo;

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

public class YEvasioneUdsVenRiga extends it.softre.thip.vendite.uds.YEvasioneUdsVenRiga {

	/**
	 * Se l'articolo e' gestito a cataste  allora devo bloccare la riga perche' non puo' essere modificata
	 */
	@Override
	public boolean isSelezionabile() {
		boolean ret = super.isSelezionabile();
		Articolo articolo = null;
		if(getTipoRiga() == RIGA_UDS) {
			articolo = getRigaUdsVendita().getRelarticolo();
		}else if(getTipoRiga() == RIGA_ORDINE) {
			articolo = getRigaOrdine().getArticolo();
		}
		if(articolo != null) {
			return !YEvasioneUdsVendita.isArticoloGestitoCataste(articolo);
		}
		return ret;
	}
}
