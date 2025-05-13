package it.fornacecalandra.thip.vendite.uds;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.softre.thip.vendite.uds.YEvasioneUdsVenRiga;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.vendite.ordineVE.OrdineVendita;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;

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

public class YEvasioneUdsVendita extends it.softre.thip.vendite.uds.YEvasioneUdsVendita {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List estraiRigheOrdine() {
		List righeEstratte = super.estraiRigheOrdine();
		Iterator iterator = righeEstratte.iterator();
		List newRigheEstratte = new ArrayList();
		Map<String,String> righeOrdineEstratte = new HashMap<String, String>();
		while(iterator.hasNext()) {
			YEvasioneUdsVenRiga riga = (YEvasioneUdsVenRiga) iterator.next();
			if(riga.getRigaOrdine() != null) {
				righeOrdineEstratte.put(riga.getRigaOrdine().getKey(), riga.getRigaOrdine().getKey());
			}
		}
		iterator = righeEstratte.iterator();
		while(iterator.hasNext()) {
			YEvasioneUdsVenRiga riga = (YEvasioneUdsVenRiga) iterator.next();
			if(riga.getTipoRiga() == YEvasioneUdsVenRiga.RIGA_UDS) {
				OrdineVendita ordVenTes = (OrdineVendita) riga.getRigaOrdine().getTestata();
				Iterator iterRighe = ordVenTes.getRighe().iterator();
				while(iterRighe.hasNext()) {
					OrdineVenditaRigaPrm ordVenRig = (OrdineVenditaRigaPrm) iterRighe.next();
					if(ordVenRig.getNumeroRigaDocumento().compareTo(riga.getRigaOrdine().getNumeroRigaDocumento()) != 0
							&& !isArticoloGestitoCataste(ordVenRig.getArticolo())) { //.Inserisco solo articoli NON gestiti a cataste
						if(!righeOrdineEstratte.containsKey(ordVenRig.getKey()) && ordVenRig.getQuantitaResiduo().getQuantitaInUMRif().compareTo(BigDecimal.ZERO) > 0) {
							YEvasioneUdsVenRiga newRiga = creaRiga();
							newRiga.setTipoRiga(YEvasioneUdsVenRiga.RIGA_ORDINE);
							assegnaDatiRiga(newRiga, null, ordVenRig);
							newRiga.setQtaDaSpedireInUMRif(ordVenRig.getQuantitaResiduo().getQuantitaInUMRif());
							newRigheEstratte.add(newRiga);
							righeOrdineEstratte.put(ordVenRig.getKey(), ordVenRig.getKey());
						}
					}
				}
			}
		}
		righeEstratte.addAll(newRigheEstratte);
		return righeEstratte;
	}

	protected static boolean isArticoloGestitoCataste(Articolo articolo) {
		if(articolo != null && articolo.isGestioneLogisticaLight()) {
			return true;
		}
		return false;
	}
}
