package it.softre.thip.vendite.uds;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.DB2NetDatabase;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.SQLServerJTDSNoUnicodeDatabase;
import com.thera.thermfw.persist.TableManager;

import it.softre.thip.vendite.uds.web.YUdsVenditaGridActionAdapter;
import it.thera.thip.base.articolo.ArticoloVersione;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.azienda.Magazzino;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.comuniVenAcq.GestoreCalcoloCosti;
import it.thera.thip.base.comuniVenAcq.OrdineTestata;
import it.thera.thip.base.comuniVenAcq.TipoCostoRiferimento;
import it.thera.thip.base.comuniVenAcq.TipoRiga;
import it.thera.thip.base.documenti.DocumentoBase;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.generale.Numeratore;
import it.thera.thip.base.generale.Serie;
import it.thera.thip.base.generale.UnitaMisura;
import it.thera.thip.base.prezziExtra.DocOrdRigaPrezziExtra;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.cs.ThipException;
import it.thera.thip.magazzino.generalemag.PersDatiMagazzino;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaLottoPrm;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaPrm;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.documentoVE.web.DocumentoVenditaDataCollector;
import it.thera.thip.vendite.generaleVE.CausaleDocumentoVendita;
import it.thera.thip.vendite.generaleVE.CausaleRigaDocVen;
import it.thera.thip.vendite.generaleVE.CausaleRigaOrdVen;
import it.thera.thip.vendite.generaleVE.PersDatiVen;
import it.thera.thip.vendite.ordineVE.GestoreEvasioneVendita;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaLottoPrm;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;
import it.thera.thip.vendite.ordineVE.OrdineVenditaTestata;
import it.thera.thip.vendite.ordineVE.ParamRigaPrmDocEvaVen;
import it.thera.thip.vendite.prezziExtra.DocRigaPrezziExtraVendita;
import it.thera.thip.vendite.prezziExtra.OrdineRigaPrezziExtraVendita;

/**
 * <h1>Softre Solutions</h1>
 * 
 * @author Daniele Signoroni 04/10/2022 <br>
 *         </br>
 *         <b>70687 DSSOF3 04/10/2022</b>
 *         <p>
 *         Classe per l'evasione di un uds vendita, contiene i parametri della
 *         form.<br>
 *         E contiene inoltre i metodi per eseguire l'evasione.
 *         </p>
 *         <b>71469 DSSOF3 28/03/2024</b>
 *         <p>
 *         Introdurre recupero righe uds figlie da altre uds tramite uds
 *         padre.<br>
 *         Rimuovere checkBloccoImmissione su cliente.<br>
 *         </p>
 *         <b>71512 DSSOF3 19/04/2024</b>
 *         <p>
 *         Aggiungere check su qta uds rig > 0.<br>
 *         Aggiungere scarico dei lotti dall'ordine di vendita.<br>
 *         </p>
 *         <b>71512 DSSOF3 26/04/2024</b>
 *         <p>
 *         Ridisegnazione completa dell'evasione.<br>
 *         A parita' di riga ordine le righe uds vengono accorpate, ora in
 *         maniera corretta.<br>
 *         </p>
 */

public class YEvasioneUdsVendita extends DocumentoBase {

	protected String iIdAzienda;

	protected String iRSerie;

	protected Date iDataDocumento;

	protected String iRCauDocTes;

	protected String iRCliente;

	protected Date iDataRifIntestatario;

	protected String iNumeroRifIntestatario;

	protected String iRIdNumeratoreSerie;

	protected Proxy iRelRCauDocTes = new Proxy(it.thera.thip.vendite.generaleVE.CausaleDocumentoVendita.class);

	protected Proxy iRelSerie = new Proxy(it.thera.thip.base.generale.Serie.class);

	protected Proxy iRelCliente = new Proxy(it.thera.thip.base.cliente.ClienteVendita.class);

	protected Proxy iRelNumeratore = new Proxy(Numeratore.class);

	protected Proxy iMagazzinoTrasferimento = new Proxy(it.thera.thip.base.azienda.Magazzino.class);

	protected String iAzione;

	protected boolean iGeneraDocumento = false;

	@SuppressWarnings("rawtypes")
	protected List iRigheEstratte;
	protected String[] iChiaviSelezionate = null;

	public YEvasioneUdsVendita() {
		setIdAzienda(Azienda.getAziendaCorrente());
		setRIdNumeratoreSerie("DOC_VEN");
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
		setIdAziendaInternal(iIdAzienda);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		String key1 = iRelCliente.getKey();
		iRelCliente.setKey(KeyHelper.replaceTokenObjectKey(key1, 1, idAzienda));
		String key2 = iRelRCauDocTes.getKey();
		iRelRCauDocTes.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
	}

	public String getRSerie() {
		return KeyHelper.getTokenObjectKey(iRelSerie.getKey(), 3);
	}

	public Date getDataRifIntestatario() {
		return iDataRifIntestatario;
	}

	public void setDataRifIntestatario(Date iDataRifIntestatario) {
		this.iDataRifIntestatario = iDataRifIntestatario;
	}

	public String getNumeroRifIntestatario() {
		return iNumeroRifIntestatario;
	}

	public void setNumeroRifIntestatario(String iNumeroRifIntestatario) {
		this.iNumeroRifIntestatario = iNumeroRifIntestatario;
	}

	public String getRIdNumeratoreSerie() {
		return KeyHelper.getTokenObjectKey(iRelNumeratore.getKey(), 2);
	}

	public void setRIdNumeratoreSerie(String iRIdNumeratoreSerie) {
		iRelSerie.setKey(KeyHelper.replaceTokenObjectKey(iRelSerie.getKey(), 2, iRIdNumeratoreSerie));
		iRelSerie.setKey(KeyHelper.replaceTokenObjectKey(iRelSerie.getKey(), 1, Azienda.getAziendaCorrente()));
		iRelNumeratore.setKey(KeyHelper.replaceTokenObjectKey(iRelNumeratore.getKey(), 2, iRIdNumeratoreSerie));
		iRelNumeratore.setKey(KeyHelper.replaceTokenObjectKey(iRelNumeratore.getKey(), 1, Azienda.getAziendaCorrente()));
	}

	public void setNumeratoreKey(String key) {
		iRelNumeratore.setKey(key);
	}

	public String getNumeratoreKey() {
		return iRelNumeratore.getKey();
	}

	public void setNumeratore(Numeratore numRcvFsc) {
		iRelNumeratore.setObject(numRcvFsc);
	}

	public Numeratore getNumeratore() {
		return (Numeratore) iRelNumeratore.getObject();
	}

	public void setRSerie(String iIdSerie) {
		iRelSerie.setKey(KeyHelper.replaceTokenObjectKey(iRelSerie.getKey(), 3, iIdSerie));
	}

	public Date getDataDocumento() {
		return iDataDocumento;
	}

	public void setDataDocumento(Date iDataDocumento) {
		this.iDataDocumento = iDataDocumento;
	}

	public String getRCauDocTes() {
		String key = iRelRCauDocTes.getKey();
		String rCausale = KeyHelper.getTokenObjectKey(key,2);
		return rCausale;
	}

	public void setRCauDocTes(String rCausale) {
		String key = iRelRCauDocTes.getKey();
		iRelRCauDocTes.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rCausale));
	}

	public String getRCliente() {
		String key = iRelCliente.getKey();
		String objRCliente = KeyHelper.getTokenObjectKey(key,2);
		return objRCliente;
	}

	public void setRCliente(String iRCliente) {
		String key = iRelCliente.getKey();
		iRelCliente.setKey(KeyHelper.replaceTokenObjectKey(key , 2, iRCliente));
	}

	public CausaleDocumentoVendita getCausale() {
		return (CausaleDocumentoVendita) iRelRCauDocTes.getObject();
	}

	public void setCausale(CausaleDocumentoVendita iCausale) {
		this.iRelRCauDocTes.setObject(iCausale);
	}

	public void setCausaleKey(String key) {
		iRelRCauDocTes.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAzienda(idAzienda);
	}

	public String getCausaleKey() {
		return iRelRCauDocTes.getKey();
	}

	public void setSerie(Serie serie) {
		this.iRelSerie.setObject(serie);
		setOnDB(false);
	}

	public Serie getSerie() {
		return (Serie) iRelSerie.getObject();
	}

	public void setSerieKey(String key) {
		iRelSerie.setKey(key);
		setOnDB(false);
	}

	public String getSerieKey() {
		return iRelSerie.getKey();
	}

	public void setRelcliente(ClienteVendita relcliente) {
		String idAzienda = getIdAzienda();
		if (relcliente != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relcliente.getKey(), 1);
		}
		setIdAzienda(idAzienda);
		this.iRelCliente.setObject(relcliente);
	}

	public ClienteVendita getRelcliente() {
		return (ClienteVendita) iRelCliente.getObject();
	}

	public void setRelclienteKey(String key) {
		iRelCliente.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAzienda(idAzienda);
	}

	public String getRelclienteKey() {
		return iRelCliente.getKey();
	}

	public boolean isGeneraDocumento() {
		return iGeneraDocumento;
	}

	public void setGeneraDocumento(boolean iGeneraDocumento) {
		this.iGeneraDocumento = iGeneraDocumento;
	}

	public Magazzino getMagazzinoTrasferimento() {
		return (Magazzino)iMagazzinoTrasferimento.getObject();
	}

	public void setMagazzinoTrasferimento(Magazzino iMagazzino) {
		this.iMagazzinoTrasferimento.setObject(iMagazzino);
	}

	public String getMagazzinoTrasferimentoKey() {
		return iMagazzinoTrasferimento.getKey();
	}

	public void setMagazzinoTrasferimentoKey(String key) {
		iMagazzinoTrasferimento.setKey(key);
	}

	public String getIdMagazzinoTrasferimento() {
		String key = iMagazzinoTrasferimento.getKey();
		String rMagazzino = KeyHelper.getTokenObjectKey(key,2);
		return rMagazzino;
	}

	public void setIdMagazzinoTrasferimento(String rMagazzino) {
		String key = iMagazzinoTrasferimento.getKey();
		iMagazzinoTrasferimento.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rMagazzino));
	}

	public String getAzione() {
		return iAzione;
	}
	public void setAzione(String iAzione) {
		this.iAzione = iAzione;
	}

	@SuppressWarnings("rawtypes")
	public List getRigheEstratte() throws ThipException {
		if (iRigheEstratte == null || this.iRigheEstratte.isEmpty()) {
			iRigheEstratte = this.estraiRigheOrdine();
		}
		return iRigheEstratte;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List ordinaRigheEstratte() {
		Collections.sort(iRigheEstratte, new Comparator() {
			public int compare(Object o1, Object o2) {
				YEvasioneUdsVenRiga r1 = (YEvasioneUdsVenRiga) o1;
				YEvasioneUdsVenRiga r2 = (YEvasioneUdsVenRiga) o2;

				OrdineVenditaRigaPrm ord1 = r1.getRigaOrdine();
				OrdineVenditaRigaPrm ord2 = r2.getRigaOrdine();

				// Se uno dei due è null, lo metti in fondo
				if (ord1 == null && ord2 == null) return 0;
				if (ord1 == null) return 1;
				if (ord2 == null) return -1;

				// Compara per anno
				int cmp = ord1.getAnnoDocumento().compareTo(ord2.getAnnoDocumento());
				if (cmp != 0) return cmp;

				// Compara per numero
				cmp = ord1.getNumeroDocumento().compareTo(ord2.getNumeroDocumento());
				if (cmp != 0) return cmp;

				// Compara per sequenza
				return Integer.valueOf(ord1.getSequenzaRiga()).compareTo(Integer.valueOf(ord2.getSequenzaRiga()));
			}
		});
		return iRigheEstratte;
	}

	public String[] getChiaviSelezionate() {
		return iChiaviSelezionate;
	}

	public void setChiaviSelezionate(String[] iChiaviSelezionate) {
		this.iChiaviSelezionate = iChiaviSelezionate;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List estraiRigheOrdine() {
		List righeEstratte = new ArrayList();
		List<YUdsVendita> testate = recuperaListaUdsVenditaDaSelezionate(getChiaviSelezionate());
		List<YUdsVenRig> righe = recuperaListaUdsVenditaRigheDaUdsVendita(testate);
		Collections.sort(righe, new YOrdinamentoUdsVenRigComparator().orderByIdArticolo);
		List<YOggettinoRigheAccorpate> righeAccorpate = recuperaRigheAccorpate(righe);
		Iterator<YOggettinoRigheAccorpate> iter = righeAccorpate.iterator();
		while (iter.hasNext()) {
			YEvasioneUdsVenRiga riga = creaRiga();
			YOggettinoRigheAccorpate rigaUdsAccorpata = (YOggettinoRigheAccorpate) iter.next();
			YUdsVenRig rigaUds = rigaUdsAccorpata.riga;
			if(rigaUds.getDocumentoVenditaRiga() == null) {
				if (rigaUds.getQtaPrm().compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				try {
					OrdineVenditaRigaPrm rigaOrdine = rigaUds.getOrdineVenditaRigaObj();
					assegnaDatiRiga(riga, rigaUds, rigaOrdine);
					riga.setQtaDaSpedireInUMRif(rigaUdsAccorpata.getQuantita());
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
				riga.getRigheUdsAccorpate().addAll(rigaUdsAccorpata.righeAccorpate);
				righeEstratte.add(riga);
			}
		}
		return righeEstratte;
	}

	public static YEvasioneUdsVenRiga creaRiga() {
		return (YEvasioneUdsVenRiga) Factory.createObject(YEvasioneUdsVenRiga.class);
	}

	protected void assegnaDatiRiga(YEvasioneUdsVenRiga riga, YUdsVenRig udsVenRig, OrdineVenditaRigaPrm ordVenRig) {
		if(udsVenRig != null) {
			riga.setRigaUdsVendita(udsVenRig);
			riga.setUdsVendita(udsVenRig.getParent());
		}
		if(ordVenRig != null)
			riga.setRigaOrdine(ordVenRig);
	}

	public String getRagioneSocialeCli() {
		if(getRelcliente() != null) {
			return getRelcliente().getRagioneSociale();
		}
		return "";
	}

	public String getCausaleDescrizione() {
		if(getCausale() != null) {
			return getCausale().getDescrizione().getDescrizione();
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		java.util.Vector errors = new java.util.Vector();
		components.runAllChecks(errors);
		ErrorMessage em = checkSerieCausale();
		if (em != null)
			errors.add(em);
		if(isGeneraDocumento()) {
			Iterator iterRigheEstratte;
			try {
				iterRigheEstratte = getRigheEstratte().iterator();
				while (iterRigheEstratte.hasNext()) {
					YEvasioneUdsVenRiga riga = (YEvasioneUdsVenRiga) iterRigheEstratte.next();
					if(riga.isRigaEstratta()
							&& (riga.getQtaDaSpedireInUMRif() == null
							|| riga.getQtaDaSpedireInUMRif().compareTo(BigDecimal.ZERO) == 0)) {
						errors.add(new ErrorMessage("YSOFTRE001", "Riga "+riga.getSequenza()+" qta' immessa non valida"));
					}
				}
			} catch (ThipException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return errors;
	}

	protected ErrorMessage checkSerieCausale() {
		ErrorMessage em = null;
		try {
			CausaleDocumentoVendita cau = (CausaleDocumentoVendita) CausaleDocumentoVendita.elementWithKey(
					CausaleDocumentoVendita.class,
					KeyHelper.buildObjectKey(new String[] { Azienda.getAziendaCorrente(), this.getRCauDocTes() }), 0);
			if (cau != null) {
				if (cau.getTipiGestione().getTPGestioneRiferimentoCli().getTipoGestione() == '1'
						&& (this.getDataRifIntestatario() == null || this.getNumeroRifIntestatario() == null)) {
					em = new ErrorMessage("YSOFTRE003", "Data e numero riferimento sono obbligatori");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return em;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int save() throws SQLException {
		Map<DocumentoVenRigaPrm, YEvasioneUdsVenRiga> righeUdsDaAggiornare = new HashMap<DocumentoVenRigaPrm, YEvasioneUdsVenRiga>();
		List<YUdsVendita> testate = recuperaListaUdsVenditaDaSelezionate(getChiaviSelezionate());
		DocumentoVendita documentoVendita = creaDocumentoVenditaTestata(getDataDocumento(), getRCauDocTes(), getRSerie(), getRCliente(),getDataRifIntestatario(), getNumeroRifIntestatario());
		assegnaDatiTestata(documentoVendita,testate);
		DocumentoDataCollector docBODC = creaDocumentoVenditaTestataBODC(documentoVendita);
		int rc = docBODC.save();
		if (rc == DocumentoVenditaDataCollector.OK) {
			Iterator iterRigheEstratte = getRigheEstratte().iterator();
			if(!iterRigheEstratte.hasNext()) {
				throw new ThipException(new ErrorMessage("YSOFTRE001", "Nessuna riga estratta"));
			}else {
				while (iterRigheEstratte.hasNext()) {
					YEvasioneUdsVenRiga riga = (YEvasioneUdsVenRiga) iterRigheEstratte.next();
					if(riga.isRigaEstratta()
							|| (riga.getUdsVendita() != null)) {
						DocumentoVenRigaPrm rigaDocumentoVE = creaDocumentoVenditaRigaPrm(documentoVendita, riga);

						if(riga.isRigaSaldata()) {
							rigaDocumentoVE.setRigaSaldata(true);
						}
						rigaDocumentoVE.setSalvaTestata(false);

						aggiornaAttributiDaRigaOrdine(rigaDocumentoVE, riga);

						DocumentoDataCollector docBODCRigaPrm = creaDocumentoVenditaRigaPrmBODC(rigaDocumentoVE);
						int rcRig = docBODCRigaPrm.save();
						if(rcRig == BODataCollector.OK) {
							righeUdsDaAggiornare.put(rigaDocumentoVE, riga);
						}else{
							throw new ThipException(docBODCRigaPrm.getErrorList().getErrors());
						}

					}
				}
				if(rc == BODataCollector.OK) {
					setKey(docBODC.getBo().getKey()); //.Setto la chiave del documento salvato
					// Aggiorno i riferimento delle testate che sono state selezionate
					for (YUdsVendita testata : testate) {
						aggiornaRiferimentiDocumentoVenditaTestataUds(testata, documentoVendita);
						testata.rendiDefinitivaUdsVendita();
						testata.save();
					}
					for (Map.Entry<DocumentoVenRigaPrm, YEvasioneUdsVenRiga> entry : righeUdsDaAggiornare.entrySet()) {
						DocumentoVenRigaPrm rigaDocumentoVE = entry.getKey();
						YEvasioneUdsVenRiga riga = entry.getValue();
						YUdsVenRig rigaUds = riga.getRigaUdsVendita();
						//.Aggiorno i riferimenti del documento sull'uds se presente
						if(rigaUds != null) {
							aggiornaRiferimentiDocumentoVenditaRigaUds(rigaUds, rigaDocumentoVE);
							rigaUds.rendiDefinitivaRigaUdsVendita();
							rigaUds.save();
						}

						// ora aggiorniamo eventuali riferimenti righe accorpate deep
						Iterator<YUdsVenRig> righeAccorpateDeep = riga.getRigheUdsAccorpate().iterator();
						while (righeAccorpateDeep.hasNext()) {
							YUdsVenRig rigaUdsAccorpata = righeAccorpateDeep.next();
							aggiornaRiferimentiDocumentoVenditaRigaUds(rigaUdsAccorpata, rigaDocumentoVE);
							rigaUdsAccorpata.rendiDefinitivaRigaUdsVendita();
							rigaUdsAccorpata.save();
						}
					}
				}else {
					throw new ThipException(docBODC.getErrorList().getErrors());
				}
			}
		} else {
			throw new ThipException(docBODC.getErrorList().getErrors());
		}
		return 1;
	}

	protected void assegnaDatiTestata(DocumentoVendita documentoVendita, List<YUdsVendita> testate) {
		if(getAzione().equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) { //.Se e' un trasferimento setto da dove viene
			documentoVendita.setMagazzinoTrasferimento(getMagazzinoTrasferimento());
		}
		String idMagazzino = null;
		for (Iterator<YUdsVendita> iterator = testate.iterator(); iterator.hasNext();) {
			YUdsVendita udsVen = (YUdsVendita) iterator.next();
			if(udsVen.getRMagazzino() != null) {
				idMagazzino = udsVen.getRMagazzino();
			}
		}
		documentoVendita.setIdMagazzino(idMagazzino);
		if(getAzione().equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) { 
			documentoVendita.setAbilitaCheckBloccoImmissione(false);
		}
		documentoVendita.setAbilitaCheckBloccoImmissione(false);
		BigDecimal[] totalePesi = recuperaTotalePesiDaTestate(testate);
		BigDecimal totPesoNetto = totalePesi[1];
		BigDecimal totPesoLordo = totalePesi[0];
		documentoVendita.setPesoLordo(totPesoLordo);
		documentoVendita.setPesoNetto(totPesoNetto);
		documentoVendita.setRicalcolaPesiEVolume(false);
		documentoVendita.setNumeroColli(testate.size()); // aggiungere numero colli...sono le chiavi selezionate
		documentoVendita.setSalvaRighe(false);
	}

	public DocumentoDataCollector creaDocumentoVenditaTestataBODC(DocumentoVendita dv) {
		try {
			String className = "DocumentoVendita";
			ClassADCollection cadc = ClassADCollectionManager.collectionWithName(className);
			String collectorName = cadc.getBODataCollector();
			if (collectorName == null) {
				collectorName = DocumentoDataCollector.class.getName();
			}
			DocumentoDataCollector bo = (DocumentoDataCollector) Factory.createObject(collectorName);
			bo.initialize(className);
			bo.setAutoCommit(false);
			bo.setBo(dv);
			bo.loadAttValue();
			bo.impostaSecondoCausale();
			return bo;
		}catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	public DocumentoDataCollector creaDocumentoVenditaRigaPrmBODC(DocumentoVenRigaPrm dvr) {
		try {
			String className = "DocumentoVenditaRigaPrm";
			ClassADCollection cadc = ClassADCollectionManager.collectionWithName(className);
			String collectorName = cadc.getBODataCollector();
			if (collectorName == null) {
				collectorName = DocumentoDataCollector.class.getName();
			}
			DocumentoDataCollector bo = (DocumentoDataCollector) Factory.createObject(collectorName);
			bo.initialize(className);
			bo.setAutoCommit(false);
			bo.setBo(dvr);
			bo.loadAttValue();
			bo.impostaSecondoCausale();
			return bo;
		}catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	@Override
	public boolean retrieve(int lockType) throws SQLException {
		return false;
	}

	@Override
	public void setOnDB(boolean onDB) {
	}

	@Override
	public void unlock() throws SQLException {
	}

	@Override
	public void setObjQueryTimeout(int seconds) {
	}

	/**
	 * DSSOF3 Generazione documento di vendita:
	 * 
	 * 1.Creazione di un documento di vendita contenente tutte le righe di tutte le
	 * testate UDS selezionate. 2.Le righe UDS vengono ordinate tramite un
	 * Comparator in base all'IdArticolo, cosi da matchare le righe che provengono
	 * dagli stessi ordini vendita. 3.Se vi sono righe che provengono dallo stesso
	 * ordine vendita vado ad accorparle, ovvero sommo la qtaPrm() dell'UDS e la
	 * aggiungo alle qta della riga vendita. 4.In seguito viene lanciato il metodo
	 * ricalcoloQta(), questo metodo sistema le qta in base all'UM. 5.Aggiorno i
	 * riferimenti al Documento di Vendita sia in riga UDS che in testata. 6.Commit
	 * solo se tutto è andato a buon fine, o tutto o niente.
	 * 
	 */
	//	@SuppressWarnings({ "unchecked" })
	//	public Object generaDocumentoVendita(String[] chiaviSel, Date data, String idCausale, String idSerie,
	//			String idCliente, Date dataRifClienteDocVe, String numeroRifCliente) {
	//		ErrorList errori = new ErrorList();
	//		String chiave = "";
	//		Object ogg = new Object[] { errori, chiave };
	//		boolean commit = false;
	//		try {
	//			DocumentoVenditaDataCollector docBODC = (DocumentoVenditaDataCollector) Factory
	//					.createObject(DocumentoVenditaDataCollector.class);
	//			docBODC.setAutoCommit(false);
	//			docBODC.initialize(Factory.getName("DocumentoVendita", Factory.CLASS_HDR), true,
	//					PersistentObject.OPTIMISTIC_LOCK);
	//			DocumentoVendita documentoVendita = creaDocumentoVenditaTestata(data, idCausale, idSerie, idCliente,
	//					dataRifClienteDocVe, numeroRifCliente);
	//			documentoVendita.setAbilitaCheckBloccoImmissione(false); // non considerare controllo blocco cliente
	//			docBODC.setBo(documentoVendita);
	//			docBODC.loadAttValue();
	//			docBODC.setAutoCommit(false);
	//			int rc = docBODC.save();
	//			if (rc == DocumentoVenditaDataCollector.OK) {
	//				commit = true;
	//			} else {
	//				errori.getErrors().addAll(docBODC.getErrorList().getErrors());
	//			}
	//			documentoVendita.retrieve();
	//			chiave = documentoVendita.getKey();
	//			// lista uds vendita rige (da dove creo e accorpo le righe)
	//			// lista uds vendita testate (da dove prendo il peso) e i numeri dei colli
	//			List<YUdsVendita> testate = recuperaListaUdsVenditaDaSelezionate(chiaviSel);
	//			List<YUdsVenRig> righe = recuperaListaUdsVenditaRigheDaUdsVendita(testate);
	//			BigDecimal[] totalePesi = recuperaTotalePesiDaTestate(testate);
	//			BigDecimal totPesoNetto = totalePesi[1];
	//			BigDecimal totPesoLordo = totalePesi[0];
	//			Collections.sort(righe, new YOrdinamentoUdsVenRigComparator().orderByIdArticolo);
	//			YUdsVenRig rigaUds = null;
	//			List<YOggettinoRigheAccorpate> righeAccorpate = recuperaRigheAccorpate(righe);
	//			if (righeAccorpate.size() == 0) {
	//				commit = false;
	//				errori.addError(new ErrorMessage("YSOFTRE001", "Nessuna riga estratta"));
	//				return new Object[] { errori, chiave };
	//			}
	//
	//			// Aggiorno i riferimento delle testate che sono state selezionate
	//			for (YUdsVendita testata : testate) {
	//				aggiornaRiferimentiDocumentoVenditaTestataUds(testata, documentoVendita);
	//				testata.rendiDefinitivaUdsVendita();
	//				commit = testata.save() >= 0 ? true : false;
	//			}
	//
	//			Iterator<YOggettinoRigheAccorpate> iter = righeAccorpate.iterator();
	//			while (iter.hasNext()) {
	//				YOggettinoRigheAccorpate oggino = iter.next();
	//				rigaUds = (YUdsVenRig) oggino.getRiga();
	//				if (rigaUds.getQtaPrm().compareTo(BigDecimal.ZERO) == 0) {
	//					errori.addError(
	//							new ErrorMessage("YSOFTRE001", "Non e' possibile evadere una riga uds con quantita 0"));
	//					return new Object[] { errori, chiave };
	//				}
	//				OrdineVenditaRigaPrm rigaOrdine = rigaUds.getOrdineVenditaRigaObj();
	//				DocumentoVenRigaPrm rigaDocumentoVE = creaDocumentoVenditaRigaPrm(documentoVendita, rigaUds, oggino,
	//						rigaOrdine);
	//				aggiornaAttributiDaRigaOrdine(rigaDocumentoVE, rigaUds, oggino);
	//				ricalcolaQta(rigaDocumentoVE);
	//				commit = rigaDocumentoVE.save() >= 0 ? true : false;
	//				aggiornaRiferimentiDocumentoVenditaRigaUds(rigaUds, rigaDocumentoVE);
	//				rigaUds.rendiDefinitivaRigaUdsVendita();
	//				commit = rigaUds.save() >= 0 ? true : false;
	//
	//				// ora aggiorniamo eventuali riferimenti righe accorpate deep
	//				Iterator<YUdsVenRig> righeAccorpateDeep = oggino.righeAccorpate.iterator();
	//				while (righeAccorpateDeep.hasNext()) {
	//					YUdsVenRig riga = righeAccorpateDeep.next();
	//					aggiornaRiferimentiDocumentoVenditaRigaUds(riga, rigaDocumentoVE);
	//					riga.rendiDefinitivaRigaUdsVendita();
	//					riga.save();
	//				}
	//			}
	//			// ora iteriamo tutto per aggiornare i riferimenti
	//			if (commit) {
	//				// spostare fuori dal for in modo da avere il totale
	//				documentoVendita.setPesoLordo(totPesoLordo);
	//				documentoVendita.setPesoNetto(totPesoNetto);
	//				documentoVendita.setRicalcolaPesiEVolume(false);
	//				documentoVendita.setNumeroColli(testate.size()); // aggiungere numero colli...sono le chiavi selezionate
	//				commit = documentoVendita.save() > 0 ? true : false;
	//			}
	//			// }
	//			if (commit) {
	//				ConnectionManager.commit();
	//			} else {
	//				ConnectionManager.rollback();
	//			}
	//		} catch (Exception e) {
	//			errori.addError(new ErrorMessage("YSOFTRE001", e.getMessage()));
	//			e.printStackTrace(Trace.excStream);
	//		}
	//		ogg = new Object[] { errori, chiave };
	//		return ogg;
	//	}

	/**
	 * @author Daniele Signoroni 26/04/2024
	 *         <p>
	 *         Si occupa di recuperare le righe accorpate.<br>
	 *         Data una lista di righe uds vendita, queste vengono accorpate , la
	 *         rottura e' la riga ordine vendita.<br>
	 *         Viene poi ritornata una lista di oggettini di comodo che contengono
	 *         la quantita' sommata, la riga uds e le righe uds che sono state
	 *         accorpate in modo che l'utente poi abbia una lista completa di quali
	 *         righe sono accorpate, cosi che poi possa aggiornare i riferimenti del
	 *         documento vendita su suddette righe uds.<br>
	 *         </p>
	 * @param righe
	 * @return
	 */
	protected List<YOggettinoRigheAccorpate> recuperaRigheAccorpate(List<YUdsVenRig> righe) {
		Collections.sort(righe, new YComparatorRigheOrdineUds()); // le sorto per riga ordine key
		List<YOggettinoRigheAccorpate> righeAccorpate = new ArrayList<YEvasioneUdsVendita.YOggettinoRigheAccorpate>();
		YUdsVenRig rigaPrec = null;
		HashMap<String, YOggettinoRigheAccorpate> oggettini = new HashMap<String, YEvasioneUdsVendita.YOggettinoRigheAccorpate>();
		for (YUdsVenRig riga : righe) {
			if (rigaPrec != null) {
				if (isAccorpamento(rigaPrec, riga)) {
					if (oggettini.containsKey(riga.getOrdineVenditaRigaKey())) {
						YOggettinoRigheAccorpate ogg = oggettini.get(riga.getOrdineVenditaRigaKey());
						ogg.setQuantita(ogg.getQuantita().add(riga.getQtaPrm()));
						ogg.righeAccorpate.add(riga);
						oggettini.put(riga.getOrdineVenditaRigaKey(), ogg);
					} else {
						YEvasioneUdsVendita inst = new YEvasioneUdsVendita();
						YOggettinoRigheAccorpate ogg = inst.new YOggettinoRigheAccorpate();
						ogg.setQuantita(riga.getQtaPrm().add(rigaPrec.getQtaPrm()));
						ogg.setRiga(riga);
						ogg.setKey(riga.getKey());
						ogg.righeAccorpate.add(rigaPrec);
						oggettini.put(riga.getOrdineVenditaRigaKey(), ogg);
					}
				} else {
					YEvasioneUdsVendita inst = new YEvasioneUdsVendita();
					YOggettinoRigheAccorpate ogg = inst.new YOggettinoRigheAccorpate();
					ogg.setQuantita(riga.getQtaPrm());
					ogg.setRiga(riga);
					ogg.setKey(riga.getKey());
					// ogg.righeAccorpate.add(rigaPrec);
					oggettini.put(riga.getOrdineVenditaRigaKey(), ogg);
				}
			} else {
				YEvasioneUdsVendita inst = new YEvasioneUdsVendita();
				YOggettinoRigheAccorpate ogg = inst.new YOggettinoRigheAccorpate();
				ogg.setQuantita(riga.getQtaPrm());
				ogg.setRiga(riga);
				ogg.setKey(riga.getKey());
				oggettini.put(riga.getOrdineVenditaRigaKey(), ogg);
			}
			rigaPrec = riga;
		}
		for (Map.Entry<String, YOggettinoRigheAccorpate> entry : oggettini.entrySet()) {
			// String key = entry.getKey();
			YOggettinoRigheAccorpate val = entry.getValue();
			righeAccorpate.add(val);
		}
		return righeAccorpate;
	}

	protected static List<YOggettinoRigaLottoAccorpata> recuperaRigheLottoAccorpate(List<YUdsVenRig> righe) {
		Collections.sort(righe, new YComparatorRigheLottoUds()); // le sorto per id lotto
		List<YOggettinoRigaLottoAccorpata> righeAccorpate = new ArrayList<YEvasioneUdsVendita.YOggettinoRigaLottoAccorpata>();
		YUdsVenRig rigaPrec = null;
		HashMap<String, YOggettinoRigaLottoAccorpata> oggettini = new HashMap<String, YEvasioneUdsVendita.YOggettinoRigaLottoAccorpata>();
		for (YUdsVenRig riga : righe) {
			if (rigaPrec != null) {
				if (rigaPrec.getRLotto() != null && riga.getRLotto() != null
						&& (rigaPrec.getRLotto().equals(riga.getRLotto()))) {
					if (oggettini.containsKey(riga.getRLotto())) {
						YOggettinoRigaLottoAccorpata ogg = oggettini.get(riga.getRLotto());
						ogg.setQuantita(ogg.getQuantita().add(riga.getQtaPrm()));
						ogg.setIdLotto(riga.getRLotto());
						oggettini.put(riga.getRLotto(), ogg);
					} else {
						YEvasioneUdsVendita inst = new YEvasioneUdsVendita();
						YOggettinoRigaLottoAccorpata ogg = inst.new YOggettinoRigaLottoAccorpata();
						ogg.setQuantita(riga.getQtaPrm().add(rigaPrec.getQtaPrm()));
						ogg.setIdLotto(riga.getRLotto());
						oggettini.put(riga.getRLotto(), ogg);
					}
				} else {
					YEvasioneUdsVendita inst = new YEvasioneUdsVendita();
					YOggettinoRigaLottoAccorpata ogg = inst.new YOggettinoRigaLottoAccorpata();
					ogg.setQuantita(riga.getQtaPrm());
					ogg.setIdLotto(riga.getRLotto());
					oggettini.put(riga.getRLotto(), ogg);
				}
			} else {
				YEvasioneUdsVendita inst = new YEvasioneUdsVendita();
				YOggettinoRigaLottoAccorpata ogg = inst.new YOggettinoRigaLottoAccorpata();
				ogg.setQuantita(riga.getQtaPrm());
				ogg.setIdLotto(riga.getRLotto());
				oggettini.put(riga.getRLotto(), ogg);
			}
			rigaPrec = riga;
		}
		if (oggettini.size() == 0 && righe.size() == 1) {
			// ne ho solo 1 la inserisco
			YUdsVenRig riga0 = righe.get(0);
			YEvasioneUdsVendita inst = new YEvasioneUdsVendita();
			YOggettinoRigaLottoAccorpata ogg = inst.new YOggettinoRigaLottoAccorpata();
			ogg.setQuantita(riga0.getQtaPrm());
			ogg.setIdLotto(riga0.getRLotto());
			oggettini.put(riga0.getRLotto(), ogg);
		}
		for (Map.Entry<String, YOggettinoRigaLottoAccorpata> entry : oggettini.entrySet()) {
			// String key = entry.getKey();
			YOggettinoRigaLottoAccorpata val = entry.getValue();
			righeAccorpate.add(val);
		}
		return righeAccorpate;
	}

	/**
	 * @author Daniele Signoroni 26/04/2024
	 *         <p>
	 *         Data una lista di testate ritorna i pesi sommati.<br>
	 *         </p>
	 * @param testate
	 * @return
	 */
	protected static BigDecimal[] recuperaTotalePesiDaTestate(List<YUdsVendita> testate) {
		BigDecimal[] pesi = new BigDecimal[2];
		BigDecimal totPesoNetto = BigDecimal.ZERO;
		BigDecimal totPesoLordo = BigDecimal.ZERO;
		for (YUdsVendita testata : testate) {
			if (testata.getPesoLordo() != null) {
				totPesoLordo = totPesoLordo.add(testata.getPesoLordo());
			}
			if (testata.getPesoNetto() != null) {
				totPesoNetto = totPesoNetto.add(testata.getPesoNetto());
			}
		}
		pesi[0] = totPesoLordo;
		pesi[1] = totPesoNetto;
		return pesi;
	}

	/**
	 * @author Daniele Signoroni 26/04/2024
	 *         <p>
	 *         Data una lista di testate, recupera tutte le uds figlie.<br>
	 *         </p>
	 * @param testate
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	protected static List<YUdsVenRig> recuperaListaUdsVenditaRigheDaUdsVendita(List<YUdsVendita> testate) {
		List<YUdsVenRig> list = new ArrayList<YUdsVenRig>();
		for (YUdsVendita testata : testate) {
			try {
				list.addAll(testata.getRigheUDSVendita());
				list.addAll(testata.getRigheUDSVenditaDaFigli());
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return list;
	}

	/**
	 * @author Daniele Signoroni 26/04/2024
	 *         <p>
	 *         Data una lista di chiavi impaccate selezionate ritorna la lista degli
	 *         oggetti scaricati dal database.<br>
	 *         </p>
	 * @param chiaviSelezionate
	 * @return
	 */
	protected static List<YUdsVendita> recuperaListaUdsVenditaDaSelezionate(String[] chiaviSelezionate) {
		List<YUdsVendita> list = new ArrayList<YUdsVendita>();
		for (String key : chiaviSelezionate) {
			try {
				list.add((YUdsVendita) YUdsVendita.elementWithKey(YUdsVendita.class, key, PersistentObject.NO_LOCK));
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return list;
	}

	/**
	 * DSSOF3 Metodo per accorpare le righe uds in una riga vendita. Se ho trovato
	 * una riga UDS con gli stessi riferimenti ordine vendita della precedente,
	 * accorpo le qtà.
	 * 
	 * @param docVenRig
	 * @param udsCorrente
	 */
	public void accorpaQtaDocumentoVenditaRigaPrm(DocumentoVenRigaPrm docVenRig, YUdsVenRig udsCorrente) {
		if (docVenRig.getQtaInUMVen() != null && udsCorrente.getQtaPrm() != null)
			docVenRig.setQtaInUMVen(docVenRig.getQtaInUMVen().add(udsCorrente.getQtaPrm()));

		if (docVenRig.getQtaInUMPrm() != null && udsCorrente.getQtaPrm() != null)
			docVenRig.setQtaInUMPrm(docVenRig.getQtaInUMPrm().add(udsCorrente.getQtaPrm()));
	}

	/**
	 * @author Daniele Signoroni
	 *         <p>
	 *         DSSOF3 Metodo per verificare se gli ordini di vendita di due righe
	 *         UDS sono uguali.
	 *         </p>
	 *         <b>71XXX DSSOF3 26/04/2024</b>
	 *         <p>
	 *         Sistemare alleggerendo.<br>
	 *         </p>
	 * @return true se sono da accorpare
	 * @return false se non sono da accorpare
	 */
	public boolean isAccorpamento(YUdsVenRig uds1, YUdsVenRig uds2) {
		boolean isDaRaggruppare = false;
		if (uds2 == null) {
			return false;
		}
		String c1 = uds1.getOrdineVenditaRigaKey();
		String c2 = uds2.getOrdineVenditaRigaKey();
		if (c1.equals(c2)) {
			isDaRaggruppare = true;
		}
		return isDaRaggruppare;
	}

	/**
	 * DSSOF3 Metodo per la creazione della testata Documento di Vendita.
	 * 
	 * @param data
	 * @param idCausale
	 * @param idSerie
	 * @param idCliente
	 * @param dataRifIntestatario
	 * @param numeroRifCliente
	 * @return
	 */
	public DocumentoVendita creaDocumentoVenditaTestata(Date data, String idCausale, String idSerie,
			String idCliente, Date dataRifIntestatario, String numeroRifCliente) {
		DocumentoVendita docVenTes = (DocumentoVendita) Factory.createObject(DocumentoVendita.class);
		docVenTes.setIdAzienda(Azienda.getAziendaCorrente());
		docVenTes.setIdCau(idCausale);
		docVenTes.getNumeratoreHandler().setIdSerie(idSerie);
		docVenTes.getNumeratoreHandler().setDataDocumento(data);
		docVenTes.setIdCliente(idCliente);
		docVenTes.setDataRifIntestatario(dataRifIntestatario);
		docVenTes.setNumeroRifIntestatario(numeroRifCliente);
		docVenTes.completaBO();
		return docVenTes;

	}

	/**
	 * DSSOF3 Metodo per la creazione di una riga Documento di Vendita.
	 * 
	 * @param docVenTes
	 * @param udsVeRig
	 * @param oggino
	 * @param rigaOrdine
	 * @return
	 */
	public DocumentoVenRigaPrm creaDocumentoVenditaRigaPrm(DocumentoVendita docVenTes, YEvasioneUdsVenRiga riga) {
		DocumentoVenRigaPrm docVenRig = (DocumentoVenRigaPrm) Factory.createObject(DocumentoVenRigaPrm.class);
		docVenRig.setIdAzienda(Azienda.getAziendaCorrente());
		docVenRig.setTestata(docVenTes);
		YUdsVenRig udsVenRig = riga.getRigaUdsVendita();
		if(udsVenRig != null) {
			docVenRig.setRAnnoOrd(udsVenRig.getRAnnoOrdVen());
			docVenRig.setRNumeroOrd(udsVenRig.getRNumOrdVen());
			docVenRig.setRRigaOrd(udsVenRig.getRRigaOrdVen());
			docVenRig.setRDetRigaOrd(udsVenRig.getRRigaDetOrdVen() != null ? udsVenRig.getRRigaDetOrdVen() : 0);
		}else {
			docVenRig.setRAnnoOrd(riga.getRigaOrdine().getAnnoDocumento());
			docVenRig.setRNumeroOrd(riga.getRigaOrdine().getNumeroDocumento());
			docVenRig.setRRigaOrd(riga.getRigaOrdine().getNumeroRigaDocumento());
			docVenRig.setRDetRigaOrd(riga.getRigaOrdine().getDettaglioRigaDocumento());
		}
		if (docVenRig.getRigaOrdine() != null) {
			CausaleRigaDocVen cau = trovaCausaleRigaDocVen((OrdineVenditaRigaPrm) docVenRig.getRigaOrdine());
			if (cau != null) {
				docVenRig.setCausaleRiga(cau);
			}
		} else {
			docVenRig.setIdCauRig(docVenTes.getCausale().getCausaleRigaDocumVenKey());
		}
		docVenRig.completaBO();
		docVenRig.setIdArticolo(udsVenRig != null ? udsVenRig.getRArticolo() : docVenRig.getRigaOrdine().getIdArticolo());
		docVenRig.cambiaArticolo(docVenRig.getArticolo(), docVenRig.getConfigurazione(), false);
		UnitaMisura um = docVenRig.getArticolo().getUMDefaultVendita();
		docVenRig.setUMRif(um);
		docVenRig.setQtaInUMVen(riga.getQtaDaSpedireInUMRif());
		BigDecimal qtaInUmPrm = docVenRig.getArticolo().convertiUM(riga.getQtaDaSpedireInUMRif(), um, docVenRig.getUMPrm(), docVenRig.getArticoloVersRichiesta());
		if(qtaInUmPrm != null)
			docVenRig.setQtaInUMPrm(qtaInUmPrm);
		docVenRig.setIdEsternoConfig(udsVenRig != null ? udsVenRig.getRConfig() : docVenRig.getRigaOrdine().getIdEsternoConfig());
		docVenRig.setIdVersioneRcs(udsVenRig != null ? udsVenRig.getRVersione() : docVenRig.getRigaOrdine().getIdVersioneRcs());
		return docVenRig;
	}

	/**
	 * DSSOF3 Metodo per agggiornare gli attributi tramite la riga ordine presente
	 * nei riferimenti dell'uds.
	 * 
	 * @param docVenRig
	 * @param udsVenRig
	 * @param oggino
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void aggiornaAttributiDaRigaOrdine(DocumentoVenRigaPrm docVenRig, YEvasioneUdsVenRiga riga) {
		try {
			OrdineVenditaRigaPrm rigaOrdine = (OrdineVenditaRigaPrm) docVenRig.getRigaOrdine();
			if (rigaOrdine != null) {
				CausaleRigaDocVen cauRigaDoc = trovaCausaleRigaDocVen((OrdineVenditaRigaPrm) docVenRig.getRigaOrdine());
				if(cauRigaDoc != null)
					docVenRig.setCausaleRiga(cauRigaDoc);
				docVenRig.setRRigaOrd(rigaOrdine.getNumeroRigaDocumento());
				docVenRig.setSpecializzazioneRiga(rigaOrdine.getSpecializzazioneRiga());
				docVenRig.setSequenzaRiga(rigaOrdine.getSequenzaRiga());
				docVenRig.setTipoRiga(rigaOrdine.getTipoRiga());
				if (rigaOrdine.getTipoRiga() == TipoRiga.OMAGGIO) {
					docVenRig.setServizioCalcDatiVendita(false);
				}
				YUdsVendita udsVendita = riga.getUdsVendita();
				if(udsVendita != null && udsVendita.getRMagazzino() != null)
					docVenRig.setIdMagazzino(udsVendita.getRMagazzino()); //.Prendo il magazzino dall'UDS se c'è
				else
					docVenRig.setMagazzino(rigaOrdine.getMagazzino());
				docVenRig.setArticolo(rigaOrdine.getArticolo());
				docVenRig.setArticoloVersSaldi(rigaOrdine.getArticoloVersSaldi());
				docVenRig.setArticoloVersRichiesta(rigaOrdine.getArticoloVersRichiesta());
				docVenRig.setConfigurazione(rigaOrdine.getConfigurazione());
				docVenRig.setDescrizioneArticolo(rigaOrdine.getDescrizioneArticolo());
				docVenRig.setDescrizioneExtArticolo(rigaOrdine.getDescrizioneExtArticolo());
				docVenRig.setNota(rigaOrdine.getNota());
				String nota = docVenRig.getNota();
				if (nota != null && !nota.equals("")) {
					if (rigaOrdine.getNota() != null && !rigaOrdine.getNota().equals("")) {
						nota = rigaOrdine.getNota() + " " + nota;
					}
				} else {
					nota = rigaOrdine.getNota();
				}
				if (nota != null && nota.length() > 250) {
					nota = nota.substring(0, 250);
				}
				docVenRig.setNota(nota);
				docVenRig.setDocumentoMM(rigaOrdine.getDocumentoMM());
				docVenRig.setSpesa(rigaOrdine.getSpesa());
				docVenRig.setImportoPercentualeSpesa(rigaOrdine.getImportoPercentualeSpesa());
				docVenRig.setSpesaPercentuale(rigaOrdine.isSpesaPercentuale());
				docVenRig.setUMRifKey(rigaOrdine.getUMRifKey());
				docVenRig.setUMPrmKey(rigaOrdine.getUMPrmKey());
				docVenRig.setUMSecKey(rigaOrdine.getUMSecKey());
				docVenRig.setRicalcoloQtaFattoreConv(rigaOrdine.isRicalcoloQtaFattoreConv());
				docVenRig.setCoefficienteImpiego(rigaOrdine.getCoefficienteImpiego());
				docVenRig.setBloccoRicalcoloQtaComp(rigaOrdine.isBloccoRicalcoloQtaComp());
				docVenRig.setTipoCostoRiferimento(rigaOrdine.getTipoCostoRiferimento());
				docVenRig.setDataConsegnaRichiesta(rigaOrdine.getDataConsegnaRichiesta());
				docVenRig.setDataConsegnaConfermata(rigaOrdine.getDataConsegnaConfermata());
				docVenRig.setSettConsegnaRichiesta(rigaOrdine.getSettConsegnaRichiesta());
				docVenRig.setSettConsegnaConfermata(rigaOrdine.getSettConsegnaConfermata());
				docVenRig.setStatoConfermaATP(rigaOrdine.getStatoConfermaATP());
				docVenRig.setDataPrevistaATP(rigaOrdine.getDataPrevistaATP());
				docVenRig.setDataTollerataATP(rigaOrdine.getDataTollerataATP());
				docVenRig.setIdListino(rigaOrdine.getIdListino());
				BigDecimal bd = GestoreEvasioneVendita.getBigDecimalZero();
				bd = rigaOrdine.getPrezzo() == null ? GestoreEvasioneVendita.getBigDecimalZero()
						: rigaOrdine.getPrezzo();
				docVenRig.setPrezzo(bd);
				bd = rigaOrdine.getPrezzoExtra() == null ? GestoreEvasioneVendita.getBigDecimalZero()
						: rigaOrdine.getPrezzoExtra();
				docVenRig.setPrezzoExtra(bd);
				bd = rigaOrdine.getPrezzoListino() == null ? GestoreEvasioneVendita.getBigDecimalZero()
						: rigaOrdine.getPrezzoListino();
				docVenRig.setPrezzoListino(bd);
				bd = rigaOrdine.getPrezzoExtraListino() == null ? GestoreEvasioneVendita.getBigDecimalZero()
						: rigaOrdine.getPrezzoExtraListino();
				docVenRig.setPrezzoExtraListino(bd);
				docVenRig.setRiferimentoUMPrezzo(rigaOrdine.getRiferimentoUMPrezzo());
				docVenRig.setTipoPrezzo(rigaOrdine.getTipoPrezzo());
				docVenRig.setBloccoRclPrzScnFatt(rigaOrdine.isBloccoRclPrzScnFatt());
				docVenRig.setProvenienzaPrezzo(rigaOrdine.getProvenienzaPrezzo());
				docVenRig.setTipoRigaListino(rigaOrdine.getTipoRigaListino());
				docVenRig.setAssoggettamentoIVA(rigaOrdine.getAssoggettamentoIVA());
				docVenRig.setResponsabileVendite(rigaOrdine.getResponsabileVendite());
				docVenRig.setScontoArticolo1(rigaOrdine.getScontoArticolo1());
				docVenRig.setScontoArticolo2(rigaOrdine.getScontoArticolo2());
				docVenRig.setMaggiorazione(rigaOrdine.getMaggiorazione());
				docVenRig.setSconto(rigaOrdine.getSconto());
				docVenRig.setPrcScontoIntestatario(rigaOrdine.getPrcScontoIntestatario());
				docVenRig.setPrcScontoModalita(rigaOrdine.getPrcScontoModalita());
				docVenRig.setScontoModalita(rigaOrdine.getScontoModalita());
				docVenRig.setAgente(rigaOrdine.getAgente());
				docVenRig.setProvvigione1Agente(rigaOrdine.getProvvigione1Agente());
				docVenRig.setProvvigione2Agente(rigaOrdine.getProvvigione2Agente());
				docVenRig.setSubagente(rigaOrdine.getSubagente());
				docVenRig.setProvvigione1Subagente(rigaOrdine.getProvvigione1Subagente());
				docVenRig.setProvvigione2Subagente(rigaOrdine.getProvvigione2Subagente());
				docVenRig.setDifferenzaPrezzoAgente(rigaOrdine.hasDifferenzaPrezzoAgente());
				docVenRig.setDifferenzaPrezzoSubagente(rigaOrdine.hasDifferenzaPrezzoSubagente());
				docVenRig.setCommessa(rigaOrdine.getCommessa());
				docVenRig.setCentroCosto(rigaOrdine.getCentroCosto());
				if (rigaOrdine.getDatiCA() != null) {
					docVenRig.getDatiCA().setVoceSpesaCA(rigaOrdine.getDatiCA().getVoceSpesaCA());
					docVenRig.getDatiCA().setVoceCA4(rigaOrdine.getDatiCA().getVoceCA4());
					docVenRig.getDatiCA().setVoceCA5(rigaOrdine.getDatiCA().getVoceCA5());
					docVenRig.getDatiCA().setVoceCA6(rigaOrdine.getDatiCA().getVoceCA6());
					docVenRig.getDatiCA().setVoceCA7(rigaOrdine.getDatiCA().getVoceCA7());
					docVenRig.getDatiCA().setVoceCA8(rigaOrdine.getDatiCA().getVoceCA8());
				}
				docVenRig.setGruppoContiAnalitica(rigaOrdine.getGruppoContiAnalitica());
				docVenRig.setFornitore(rigaOrdine.getFornitore());
				docVenRig.setStatoAccantonamentoPrenot(rigaOrdine.getStatoAccantonamentoPrenot());
				docVenRig.setPriorita(rigaOrdine.getPriorita());
				docVenRig.setStatoInvioEDI(rigaOrdine.getStatoInvioEDI());
				docVenRig.setStatoInvioDatawarehouse(rigaOrdine.getStatoInvioDatawarehouse());
				docVenRig.setStatoInvioLogistica(rigaOrdine.getStatoInvioLogistica());
				docVenRig.setStatoInvioContAnalitica(rigaOrdine.getStatoInvioContAnalitica());
				docVenRig.setNonFatturare(rigaOrdine.isNonFatturare());
				docVenRig.setFlagRiservatoUtente1(rigaOrdine.getFlagRiservatoUtente1());
				docVenRig.setFlagRiservatoUtente2(rigaOrdine.getFlagRiservatoUtente2());
				docVenRig.setFlagRiservatoUtente3(rigaOrdine.getFlagRiservatoUtente3());
				docVenRig.setFlagRiservatoUtente4(rigaOrdine.getFlagRiservatoUtente4());
				docVenRig.setFlagRiservatoUtente5(rigaOrdine.getFlagRiservatoUtente5());
				docVenRig.setAlfanumRiservatoUtente1(rigaOrdine.getAlfanumRiservatoUtente1());
				docVenRig.setAlfanumRiservatoUtente2(rigaOrdine.getAlfanumRiservatoUtente2());
				docVenRig.setNumeroRiservatoUtente1(rigaOrdine.getNumeroRiservatoUtente1());
				docVenRig.setNumeroRiservatoUtente2(rigaOrdine.getNumeroRiservatoUtente2());
				docVenRig.setCostoUnitario(rigaOrdine.getCostoUnitario());
				PersDatiVen pdv = PersDatiVen.getCurrentPersDatiVen();
				if (pdv.getTipoCostoRiferimento() == TipoCostoRiferimento.COSTO_ULTIMO) {
					GestoreCalcoloCosti gesCalcoloCosti = (GestoreCalcoloCosti) Factory
							.createObject(GestoreCalcoloCosti.class);
					gesCalcoloCosti.initialize(rigaOrdine.getIdAzienda(), rigaOrdine.getIdArticolo(),
							rigaOrdine.getIdVersioneSal(), rigaOrdine.getIdConfigurazione(),
							rigaOrdine.getIdMagazzino());
					gesCalcoloCosti.impostaCostoUnitario();
					docVenRig.setCostoUnitario(gesCalcoloCosti.getCostoUnitario());
				}
				if (rigaOrdine.getRigaPrezziExtra() != null) {
					docVenRig.istanziaRigaPrezziExtra();
					if (docVenRig.getRigaPrezziExtra() != null) {
						DocRigaPrezziExtraVendita rigaPrezzi = (DocRigaPrezziExtraVendita) docVenRig
								.getRigaPrezziExtra();
						DocOrdRigaPrezziExtra rigaPrezziOrd = rigaOrdine.getRigaPrezziExtra();
						rigaPrezzi.setAnnoContrattoMateriaPrima(rigaPrezziOrd.getAnnoContrattoMateriaPrima());
						rigaPrezzi.setIdAzienda(rigaPrezziOrd.getIdAzienda());
						rigaPrezzi.setIdRigaCondizione(rigaPrezziOrd.getIdRigaCondizione());
						rigaPrezzi.setIdSchemaPrezzo(rigaPrezziOrd.getIdSchemaPrezzo());
						rigaPrezzi.setNumContrattoMateriaPrima(rigaPrezziOrd.getNumContrattoMateriaPrima());
						rigaPrezzi.setRAnnoCantiere(((OrdineRigaPrezziExtraVendita) rigaPrezziOrd).getRAnnoCantiere());
						rigaPrezzi.setRNumeroCantiere(
								((OrdineRigaPrezziExtraVendita) rigaPrezziOrd).getRNumeroCantiere());
						rigaPrezzi.setRRigaCantiere(((OrdineRigaPrezziExtraVendita) rigaPrezziOrd).getRRigaCantiere());
						rigaPrezzi.setPrezzoRiferimento(
								((OrdineRigaPrezziExtraVendita) rigaPrezziOrd).getPrezzoRiferimento());
						try {
							rigaPrezzi.getPrezziExtra().setEqual(rigaPrezziOrd.getPrezziExtra());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						rigaPrezzi.setRigaOrdine(rigaOrdine);
						rigaPrezzi.setIdDetRigaOrdine(rigaOrdine.getDettaglioRigaDocumento());
					}
				}
				if (docVenRig.getRigaOrdine() != null) {
					OrdineVenditaTestata ordVen = (OrdineVenditaTestata) docVenRig.getRigaOrdine().getTestata();
					if (ordVen.getTipoEvasioneOrdine() == OrdineTestata.SALDO_AUTOMATICO) {
						docVenRig.setRigaSaldata(true);
					}
				}
				docVenRig.setRifCommessaCli(rigaOrdine.getRifCommessaCli());
				docVenRig.setDifferenzaPrezzoAgente(rigaOrdine.hasDifferenzaPrezzoAgente());
				docVenRig.setDifferenzaPrezzoSubagente(rigaOrdine.hasDifferenzaPrezzoSubagente());
				docVenRig.setPrezzoNetto(rigaOrdine.getPrezzoNetto());
			}
			if (PersDatiMagazzino.getCurrentPersDatiMagazzino().getGesLotti()) {
				// if(rigaOrdine != null) {
				// qui dovrei accorpare i lotti e creare un nuovo oggettino con la somma delle
				// quantita' accorpate per lotto
				List<YOggettinoRigaLottoAccorpata> righeLottoAccorpate = recuperaRigheLottoAccorpate(riga.getRigheUdsAccorpate());
				Iterator iterRigheTot = righeLottoAccorpate.iterator();
				while (iterRigheTot.hasNext()) {
					YOggettinoRigaLottoAccorpata lotto = (YOggettinoRigaLottoAccorpata) iterRigheTot.next();
					DocumentoVenRigaLottoPrm rigaLotto = (DocumentoVenRigaLottoPrm) Factory
							.createObject(DocumentoVenRigaLottoPrm.class);
					rigaLotto.setFather(docVenRig);
					rigaLotto.setIdArticolo(docVenRig.getIdArticolo());
					rigaLotto.setIdLotto(lotto.getIdLotto()); // 24-04-24 cambiare a lotto della riga uds
					rigaLotto.getQtaAttesaEvasione().setQuantitaInUMRif(lotto.getQuantita());
					rigaLotto.getQtaAttesaEvasione().setQuantitaInUMPrm(lotto.getQuantita());

					docVenRig.getRigheLotto().add(rigaLotto);
				}

				//.Vuol dire che sto evadendo una riga ordine e non UDS quindi genero i lotti a partire da quelli dell'ordine
				if(riga.getRigheUdsAccorpate().isEmpty()) { 
					Iterator iterLotti = rigaOrdine.getRigheLotto().iterator();
					while(iterLotti.hasNext()) {
						OrdineVenditaRigaLottoPrm rigaLottoOrdVenRig = (OrdineVenditaRigaLottoPrm) iterLotti.next();

						DocumentoVenRigaLottoPrm rigaLottoDocVenRig = (DocumentoVenRigaLottoPrm) Factory
								.createObject(DocumentoVenRigaLottoPrm.class);
						rigaLottoDocVenRig.setFather(docVenRig);
						rigaLottoDocVenRig.setIdArticolo(docVenRig.getIdArticolo());
						rigaLottoDocVenRig.setIdLotto(rigaLottoOrdVenRig.getIdLotto());
						rigaLottoDocVenRig.getQtaAttesaEvasione().setQuantitaInUMRif(rigaLottoOrdVenRig.getQtaInUMRif());
						rigaLottoDocVenRig.getQtaAttesaEvasione().setQuantitaInUMPrm(rigaLottoOrdVenRig.getQtaInUMPrmMag());

						docVenRig.getRigheLotto().add(rigaLottoDocVenRig);
					}
				}
			}
			// }
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	/**
	 * DSSOF3 Metodo copiato dallo standard per prendere la versione dell'articolo.
	 * 
	 * @param idAzienda
	 * @param idArticolo
	 * @param idVersione
	 * @return
	 */
	public ArticoloVersione getArticoloVersione(String idAzienda, String idArticolo, String idVersione) {
		ArticoloVersione av = null;
		String azienda = Azienda.getAziendaCorrente();
		if (idAzienda != null) {
			azienda = idAzienda;
		}
		String key = KeyHelper.buildObjectKey(new String[] { azienda, idArticolo, idVersione });
		try {
			av = ArticoloVersione.elementWithKey(key, PersistentObject.NO_LOCK);
		} catch (Exception ex) {
			ex.printStackTrace(Trace.excStream);
		}
		return av;
	}

	/**
	 * DSSOF3 Metodo copiato dallo standard per prendere l'unità di misura.
	 * 
	 * @param idUM
	 * @return
	 */
	public UnitaMisura getUnitaMisura(String idUM) {
		UnitaMisura um = null;
		if (idUM == null || idUM.equals(""))
			return um;
		try {
			um = UnitaMisura.getUM(idUM);
		} catch (Exception ex) {
			ex.printStackTrace(Trace.excStream);
		}
		return um;
	}

	/**
	 * DSSOF3	Metodo per aggiornare i riferimenti al Documento di Vendita sulla riga uds.
	 * @param udsVeRig
	 * @param docVeRig
	 */
	public void aggiornaRiferimentiDocumentoVenditaRigaUds(YUdsVenRig udsVeRig, DocumentoVenRigaPrm docVeRig) {
		if(getAzione().equals(YUdsVenditaGridActionAdapter.EVADI_UDS)) {
			udsVeRig.setRAnnoDocVen(docVeRig.getTestata().getAnnoDocumento());
			udsVeRig.setRNumDocVen(docVeRig.getTestata().getNumeroDocumento());
			udsVeRig.setRRigaDocVen(docVeRig.getNumeroRigaDocumento());
			if(docVeRig.getDettaglioRigaDocumento() == null) {
				udsVeRig.setRRigaDetDocVen(docVeRig.getDettaglioRigaDocumento());
			}
		}else if(getAzione().equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) {
			udsVeRig.setIdAnnoDocumentoTrasf(docVeRig.getTestata().getAnnoDocumento());
			udsVeRig.setIdNumeroDocumentoTrasf(docVeRig.getTestata().getNumeroDocumento());
			udsVeRig.setIdRigaDocumentoTrasf(docVeRig.getNumeroRigaDocumento());
		}
	}

	/**
	 * DSSOF3	Metodo per aggiornare i riferimenti al Documento di Vendita sulla testata uds.
	 * @param udsVeTes
	 * @param docVenTes
	 */
	public void aggiornaRiferimentiDocumentoVenditaTestataUds(YUdsVendita udsVeTes, DocumentoVendita docVenTes) {
		if(getAzione().equals(YUdsVenditaGridActionAdapter.EVADI_UDS)) {
			udsVeTes.setRAnnoDocVen(docVenTes.getAnnoDocumento());
			udsVeTes.setRNumDocVen(docVenTes.getNumeroDocumento());
		}else if(getAzione().equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) {
			udsVeTes.setIdAnnoDocumentoTrasf(docVenTes.getAnnoDocumento());
			udsVeTes.setIdNumeroDocumentoTrasf(docVenTes.getNumeroDocumento());
		}
	}

	/**
	 * DSSOF3 Metodo per formattare la data da stringa java.sql.Date.
	 * 
	 * @param data
	 * @return
	 */
	public static Date getDataFormattata(String data) {
		java.sql.Date date = null;
		try {
			SimpleDateFormat formatoWeb = new SimpleDateFormat("dd/MM/yyyy");
			// SimpleDateFormat formatoSql = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatoDB2 = new SimpleDateFormat("yyyy-MM-dd");
			String dataOk = "";
			if (ConnectionManager.getCurrentDatabase() instanceof SQLServerJTDSNoUnicodeDatabase)
				dataOk = formatoDB2.format(formatoWeb.parse(data));
			else if (ConnectionManager.getCurrentDatabase() instanceof DB2NetDatabase)
				dataOk = formatoDB2.format(formatoWeb.parse(data));
			date = java.sql.Date.valueOf(dataOk);
		} catch (ParseException e) {
			e.printStackTrace(Trace.excStream);
		}
		return date;
	}

	/**
	 * <h1>Softre Solutions</h1> <br>
	 * 
	 * @author Daniele Signoroni 26/04/2024 <br>
	 *         <br>
	 *         <b>71XXX DSSOF3 26/04/2024</b>
	 *         <p>
	 *         Prima stesura.<br>
	 *         E' una classe di comodo per tenere le righe uds accorpate.<br>
	 *         {@link #riga} e' la riga uds principale.<br>
	 *         {@link #righeAccorpate} e' la lista che contiene le righe che sono
	 *         state accorpate a quelle principali.<br>
	 *         {@link #quantita} la quantita' totale sommata.<br>
	 *         {@link #key} la chiave della riga uds principale.<br>
	 *         </p>
	 */
	public class YOggettinoRigheAccorpate {

		protected BigDecimal quantita = BigDecimal.ZERO;
		protected String key = null;
		protected YUdsVenRig riga = null;

		List<YUdsVenRig> righeAccorpate = new ArrayList<YUdsVenRig>();

		public BigDecimal getQuantita() {
			return quantita;
		}

		public void setQuantita(BigDecimal quantita) {
			this.quantita = quantita;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public YUdsVenRig getRiga() {
			return riga;
		}

		public void setRiga(YUdsVenRig riga) {
			this.riga = riga;
		}
	}

	public class YOggettinoRigaLottoAccorpata {

		protected BigDecimal quantita = BigDecimal.ZERO;
		protected String idLotto = null;

		public BigDecimal getQuantita() {
			return quantita;
		}

		public void setQuantita(BigDecimal quantita) {
			this.quantita = quantita;
		}

		public String getIdLotto() {
			return idLotto;
		}

		public void setIdLotto(String idLotto) {
			this.idLotto = idLotto;
		}

	}

	@SuppressWarnings("rawtypes")
	public void aggiornaRigheDocumento(List righeParamRiga) throws ThipException {
		List righeDoc = this.getRigheEstratte();
		Iterator iter = righeParamRiga.iterator();
		while (iter.hasNext()) {
			try {
				ParamRigaPrmDocEvaVen pr = (ParamRigaPrmDocEvaVen) iter.next();
				YEvasioneUdsVenRiga rigaPrm = (YEvasioneUdsVenRiga) righeDoc.get(pr.iPos);
				if(rigaPrm == null)
					righeDoc.remove(pr.iPos);
				rigaPrm.aggiornaRiga(pr);
			}
			catch (IndexOutOfBoundsException ex) {
				ex.printStackTrace(Trace.excStream);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CausaleRigaDocVen trovaCausaleRigaDocVen(OrdineVenditaRigaPrm
			rigaOrdine) {
		List causaliRigaDoc = new ArrayList();
		// pongo come prina causale quella eventualmente selezionata come default
		List causaliRigaDocTemp = getCausale().getCausaliRiga();
		CausaleRigaDocVen cauRigaDocDefault = getCausale().getCausaleRigaDocumVen();
		int idx = 0;
		String cauRigaDocDefaultKey = "";

		if (cauRigaDocDefault != null &&
				cauRigaDocDefault.getDatiComuniEstesi().getStato() ==
				DatiComuniEstesi.VALIDO) {
			causaliRigaDoc.add(idx, cauRigaDocDefault);
			cauRigaDocDefaultKey = cauRigaDocDefault.getKey();
			idx++;
		}
		Iterator iterDocTemp = causaliRigaDocTemp.iterator();
		while (iterDocTemp.hasNext()) {
			CausaleRigaDocVen cauObj = (CausaleRigaDocVen) iterDocTemp.next();
			if (!cauObj.getKey().equals(cauRigaDocDefaultKey) &&
					cauObj.getDatiComuniEstesi().getStato() == DatiComuniEstesi.VALIDO) {
				causaliRigaDoc.add(idx, cauObj);
				idx++;
			}
		}
		// cerca la causale appropriata
		CausaleRigaDocVen cauRigaDoc = null;
		boolean isTrovata = false;
		cauRigaDoc = trovaCausaleRigaDocVen(rigaOrdine, causaliRigaDoc, true);
		if (cauRigaDoc == null)
			cauRigaDoc = trovaCausaleRigaDocVen(rigaOrdine, causaliRigaDoc, false);
		isTrovata = (cauRigaDoc != null) ? true : false;
		//Fix 16542 fine
		if (!isTrovata) {
			cauRigaDoc = null;
		}
		return cauRigaDoc;
	}

	@SuppressWarnings("rawtypes")
	public CausaleRigaDocVen trovaCausaleRigaDocVen(OrdineVenditaRigaPrm rigaOrdine, List causaliRigaDoc, boolean conTestSuGestioneCatalogo) {
		CausaleRigaDocVen cauRigaDoc = null;
		boolean isTrovata = false;
		boolean isForzaControllo = false;
		CausaleRigaOrdVen cauRigaOrd = (CausaleRigaOrdVen) rigaOrdine.getCausaleRiga();
		Iterator iterDoc = causaliRigaDoc.iterator();
		if (cauRigaOrd != null) {
			if (rigaOrdine.getTipoRiga() == TipoRiga.SPESE_MOV_VALORE) {
				CausaleRigaDocVen cauDaSpesa = null;
				if (rigaOrdine.getSpesa() != null) {
					cauDaSpesa = (CausaleRigaDocVen) rigaOrdine.getSpesa().
							getCausaleRigaDocVen();
				}
				if (cauDaSpesa != null) {
					while (iterDoc.hasNext() && !isTrovata) {
						CausaleRigaDocVen cau = (CausaleRigaDocVen) iterDoc.next();
						if (cauDaSpesa.getKey().equals(cau.getKey())) {
							cauRigaDoc = cau;
							isTrovata = true;
						}
					}
					if (!isTrovata) {
						isForzaControllo = true;
					}
				}
				else {
					isForzaControllo = true;
				}
			}
			iterDoc = causaliRigaDoc.iterator();
			if (rigaOrdine.getTipoRiga() == TipoRiga.OMAGGIO) {
				boolean isROOmaggioSA = rigaOrdine.getCausaleRiga().isOmaggioScontoArticolo();
				while (iterDoc.hasNext() && !isTrovata) {
					CausaleRigaDocVen cau = (CausaleRigaDocVen) iterDoc.next();
					if (cau.getTipoRiga() == TipoRiga.OMAGGIO) {
						if (isROOmaggioSA == cau.isOmaggioScontoArticolo()) {
							Iterator i = cau.getCausaliRigaOrdine().iterator();
							while (i.hasNext() && !isTrovata) {
								CausaleRigaOrdVen cro = (CausaleRigaOrdVen) i.next();
								if (cro.getKey().equals(cauRigaOrd.getKey())) {
									if (!conTestSuGestioneCatalogo || (cro.isCatalogoEstPerOrdineAbl() == cau.isCatalogoEstPerOrdineAbl()) )
									{
										isTrovata = true;
										cauRigaDoc = cau;
									}
								}
							}
						}
					}
				}
			}
			else if (rigaOrdine.getTipoRiga() == TipoRiga.MERCE ||
					rigaOrdine.getTipoRiga() == TipoRiga.SERVIZIO ||
					isForzaControllo) {
				while (iterDoc.hasNext() && !isTrovata) {
					cauRigaDoc = (CausaleRigaDocVen) iterDoc.next();
					List causaliRigaOrd = cauRigaDoc.getCausaliRigaOrdine();
					Iterator iterOrd = causaliRigaOrd.iterator();
					while (iterOrd.hasNext() && !isTrovata) {
						CausaleRigaOrdVen cau = (CausaleRigaOrdVen) iterOrd.next();
						if (cau.getKey().equals(cauRigaOrd.getKey())) {
							if (!conTestSuGestioneCatalogo || (cau.isCatalogoEstPerOrdineAbl() == cauRigaDoc.isCatalogoEstPerOrdineAbl()))
							{
								isTrovata = true;
							}
						}
					}
				}
			}
		}
		if (!isTrovata && isForzaControllo) {
			Iterator iterDocUltima = causaliRigaDoc.iterator();
			while (iterDocUltima.hasNext()) {
				cauRigaDoc = (CausaleRigaDocVen) iterDocUltima.next();
				if (cauRigaDoc.getTipoRiga() == rigaOrdine.getTipoRiga()) {
					isTrovata = true;
					break;
				}
			}
		}
		return (isTrovata) ? cauRigaDoc : null;
	}

	@Override
	protected TableManager getTableManager() throws SQLException {
		return null;
	}

}
