package it.softre.thip.vendite.uds.web;

import it.softre.thip.vendite.uds.YEvasioneUdsVendita;
import it.thera.thip.base.documenti.web.DocumentoDatiSessione;

public class YDatiSessioneEvasioneUdsVendita extends DocumentoDatiSessione{
	
	protected YEvasioneUdsVendita bo = null;
	
	protected String idCliente;
	
	protected boolean iDaEstratto;
	
	protected String[] chiaviSel;

	public String[] getChiaviSel() {
		return chiaviSel;
	}

	public void setChiaviSel(String[] chiaviSel) {
		this.chiaviSel = chiaviSel;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public boolean isDaEstratto() {
		return iDaEstratto;
	}

	public void setDaEstratto(boolean iDaEstratto) {
		this.iDaEstratto = iDaEstratto;
	}

	public YEvasioneUdsVendita getBo() {
		return bo;
	}

	public void setBo(YEvasioneUdsVendita bo) {
		this.bo = bo;
	}
}
