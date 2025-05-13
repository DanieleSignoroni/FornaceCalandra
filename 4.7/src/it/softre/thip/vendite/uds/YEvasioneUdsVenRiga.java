package it.softre.thip.vendite.uds;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.Proxy;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.RigaPrimaria;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRiga;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;
import it.thera.thip.vendite.ordineVE.ParamRigaPrmDocEvaVen;

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

public class YEvasioneUdsVenRiga extends BusinessObjectAdapter {

	public static final char RIGA_UDS = '0';
	public static final char RIGA_ORDINE = '1';

	protected String iIdAzienda;

	protected Proxy iUdsVendita = new Proxy(it.softre.thip.vendite.uds.YUdsVendita.class);
	protected Proxy iRigaUdsVendita = new Proxy(it.softre.thip.vendite.uds.YUdsVenRig.class);

	@SuppressWarnings("rawtypes")
	protected List iRigheUdsAccorpate = new ArrayList();

	protected OrdineVenditaRigaPrm rigaOrdine = null;
	private boolean isRigaEstratta = false;
	private boolean iRigaSaldata       = false ;
	private BigDecimal iQtaDaSpedireInUMRif = null;

	protected char iTipoRiga = RIGA_UDS;

	public YEvasioneUdsVenRiga() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
		setIdAziendaInternal(iIdAzienda);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		String key1 = iUdsVendita.getKey();
		iUdsVendita.setKey(KeyHelper.replaceTokenObjectKey(key1, 1, idAzienda));
		String key2 = iRigaUdsVendita.getKey();
		iRigaUdsVendita.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
	}

	public char getTipoRiga() {
		return iTipoRiga;
	}

	public void setTipoRiga(char iTipoRiga) {
		this.iTipoRiga = iTipoRiga;
	}

	public void setUdsVendita(YUdsVendita parent) {
		String idAzienda = getIdAzienda();
		if (parent != null) {
			idAzienda = KeyHelper.getTokenObjectKey(parent.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iUdsVendita.setObject(parent);
		setOnDB(false);
	}

	public YUdsVendita getUdsVendita() {
		return (YUdsVendita)iUdsVendita.getObject();
	}

	public void setUdsVenditaKey(String key) {
		iUdsVendita.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setOnDB(false);
	}

	public String getUdsVenditaKey() {
		return iUdsVendita.getKey();
	}

	public String getIdUdsVendita() {
		String key = iUdsVendita.getKey();
		String objRIdUdsPadre = KeyHelper.getTokenObjectKey(key,2);
		return objRIdUdsPadre;
	}

	public void setIdUdsVendita(String iRIdUdsPadre) {
		String key = iUdsVendita.getKey();
		iUdsVendita.setKey(KeyHelper.replaceTokenObjectKey(key , 2, iRIdUdsPadre));
		iRigaUdsVendita.setKey(KeyHelper.replaceTokenObjectKey(key, 2, iRIdUdsPadre));
	}

	public void setRigaUdsVendita(YUdsVenRig parent) {
		String idAzienda = getIdAzienda();
		if (parent != null) {
			idAzienda = KeyHelper.getTokenObjectKey(parent.getKey(), 3);
		}
		setIdAziendaInternal(idAzienda);
		this.iRigaUdsVendita.setObject(parent);
		setOnDB(false);
	}

	public YUdsVenRig getRigaUdsVendita() {
		return (YUdsVenRig)iRigaUdsVendita.getObject();
	}

	public void setRigaUdsVenditaKey(String key) {
		iRigaUdsVendita.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 3);
		setIdAziendaInternal(idAzienda);
		setOnDB(false);
	}

	public String getRigaUdsVenditaKey() {
		return iRigaUdsVendita.getKey();
	}

	public Integer getIdRigaUdsVendita() {
		String key = iRigaUdsVendita.getKey();
		String objRIdUdsPadre = KeyHelper.getTokenObjectKey(key,3);
		return Integer.valueOf(objRIdUdsPadre != null ? objRIdUdsPadre : "0");
	}

	public void setIdRigaUdsVendita(Integer iRIdUdsPadre) {
		String key = iRigaUdsVendita.getKey();
		iRigaUdsVendita.setKey(KeyHelper.replaceTokenObjectKey(key , 3, iRIdUdsPadre));
	}

	public OrdineVenditaRigaPrm getRigaOrdine() {
		return rigaOrdine;
	}

	public void setRigaOrdine(OrdineVenditaRigaPrm rigaOrdine) {
		this.rigaOrdine = rigaOrdine;
	}

	@SuppressWarnings("rawtypes")
	public List getRigheUdsAccorpate() {
		return iRigheUdsAccorpate;
	}

	@SuppressWarnings("rawtypes")
	public void setRigheUdsAccorpate(List iRigheUdsAccorpate) {
		this.iRigheUdsAccorpate = iRigheUdsAccorpate;
	}

	public String getIdArticolo() {
		if(getTipoRiga() == RIGA_UDS) {
			if(getRigaUdsVendita() != null) 
				return getRigaUdsVendita().getRArticolo();
		}else if(getTipoRiga() == RIGA_ORDINE) {
			if(getRigaOrdine() != null)
				return getRigaOrdine().getIdArticolo();
		}
		return "";
	}

	public String getIdMagazzino() {
		if(getRigaOrdine() != null)
			return getRigaOrdine().getIdMagazzino();
		return "";
	}

	public String getIdLotto() {
		if(getRigaUdsVendita() != null) {
			return getRigaUdsVendita().getRLotto();
		}
		return "";
	}

	public Date getDataConsegnaConfermata() {
		if(getRigaOrdine() != null) {
			//SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
			return getRigaOrdine().getDataConsegnaConfermata();
		}
		return null;
	}

	public BigDecimal getQtaDaSpedireInUMRif() {
		return iQtaDaSpedireInUMRif;
	}

	public void setQtaDaSpedireInUMRif(BigDecimal iQtaDaSpedireInUMRif) {
		this.iQtaDaSpedireInUMRif = iQtaDaSpedireInUMRif;
	}

	public String getRifRigaOrdineFormattato() {
		String s = "";
		OrdineVenditaRiga rigaOrdine = (OrdineVenditaRiga)getRigaOrdine();
		if (rigaOrdine != null) {
			s = getRigaOrdine().getTestata().getNumeroDocumentoFormattato() + " - " +
					rigaOrdine.getNumeroRigaDocumento().intValue();
			if (! (rigaOrdine instanceof RigaPrimaria)) {
				s = s + "/" + rigaOrdine.getDettaglioRigaDocumento().intValue();
			}
		}
		return s;
	}

	public String getIdUMRif() {
		if(getTipoRiga() == RIGA_UDS) {
			if(getRigaUdsVendita() != null) 
				return getRigaUdsVendita().getRelarticolo().getUMDefaultVendita().getIdUnitaMisura();
		}else if(getTipoRiga() == RIGA_ORDINE) {
			if(getRigaOrdine() != null)
				return getRigaOrdine().getArticolo().getUMDefaultVendita().getIdUnitaMisura();
		}
		return "";
	}

	public boolean isCambioMagazzinoAbilitato() {
		return false;
	}

	public boolean isSelezionabile() {
		return true;
	}

	public boolean isForzabile() {
		return true;
	}

	public BigDecimal getQtaResiduaInUMRif() {
		if(getRigaOrdine() != null) {
			return getRigaOrdine().getQuantitaResiduo().getQuantitaInUMRif();
		}
		return BigDecimal.ZERO;
	}

	public boolean isSaldoAutomatico() {
		return false;
	}

	public boolean isRigaSaldata() {
		return iRigaSaldata;
	}

	/**
	 * Imposta il saldo manuale
	 * @param isOk true saldo manuale, false altrimenti
	 */
	public void setRigaSaldata(boolean isOk) {
		iRigaSaldata = isOk;
	}

	public boolean isRigaForzata() {
		return false;
	}

	public String getDescrizioneArticolo() {
		if(getTipoRiga() == RIGA_UDS) {
			if(getRigaUdsVendita() != null) 
				return getRigaUdsVendita().getRelarticolo().getDescrizioneArticoloNLS().getDescrizioneEstesa();
		}else if(getTipoRiga() == RIGA_ORDINE) {
			if(getRigaOrdine() != null)
				return getRigaOrdine().getArticolo().getDescrizioneArticoloNLS().getDescrizioneEstesa();
		}
		return "";
	}

	public boolean isRigaEstratta() {
		return isRigaEstratta;
	}

	public void setRigaEstratta(boolean isOk) {
		isRigaEstratta = isOk;
	}

	public String getIdCommessa() {
		if(getTipoRiga() == RIGA_UDS) {
			if(getRigaUdsVendita() != null) 
				return getRigaUdsVendita().getIdCommessa();
		}else if(getTipoRiga() == RIGA_ORDINE) {
			if(getRigaOrdine() != null)
				return getRigaOrdine().getIdCommessa();
		}
		return "";
	}

	public String getSequenza() {
		if(getRigaOrdine() != null)
			return String.valueOf(getRigaOrdine().getSequenzaRiga());
		return "";
	}

	/**
	 * Aggiorna la riga di evasione con i parametri scelti nella form di evasione
	 * @param pr
	 */
	protected void aggiornaRiga(ParamRigaPrmDocEvaVen pr) {
		this.setRigaEstratta(pr.iEstratta);
		this.setRigaSaldata(pr.iSaldo || isSaldoAutomatico());
	}

}
