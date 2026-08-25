package it.fornacecalandra.thip.base.generale;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.OneToMany;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.generale.Numeratore;
import it.thera.thip.base.generale.Serie;
import it.thera.thip.cs.EntitaAzienda;
import it.thera.thip.magazzino.documenti.CausaleDocumentoGen;
import it.thera.thip.magazzino.documenti.CausaleDocumentoVersDist;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 24/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72616    24/08/2026  DSSOF3   Prima stesura
 */

public abstract class YPsnDatiImpMovPrdPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static YPsnDatiImpMovPrd cInstance;

	protected Proxy iNumeratoredocgenprllibero = new Proxy(it.thera.thip.base.generale.Numeratore.class);

	protected Proxy iNumeratoredocprdstrutture = new Proxy(it.thera.thip.base.generale.Numeratore.class);

	protected Proxy iSeriedocgenprllibero = new Proxy(it.thera.thip.base.generale.Serie.class);

	protected Proxy iSeriedocprodstrutture = new Proxy(it.thera.thip.base.generale.Serie.class);

	protected Proxy iCaudocgenprllibero = new Proxy(it.thera.thip.magazzino.documenti.CausaleDocumentoGen.class);

	protected Proxy iCaudocprdstrutture = new Proxy(it.thera.thip.magazzino.documenti.CausaleDocumentoVersDist.class);

	protected OneToMany iCausaliRigaDocStrutture = new OneToMany(it.fornacecalandra.thip.base.generale.YCauRigDocPrdStrutture.class, this, 1, false);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YPsnDatiImpMovPrd) Factory.createObject(YPsnDatiImpMovPrd.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YPsnDatiImpMovPrd elementWithKey(String key, int lockType) throws SQLException {
		return (YPsnDatiImpMovPrd) PersistentObject.elementWithKey(YPsnDatiImpMovPrd.class, key, lockType);
	}

	public YPsnDatiImpMovPrdPO() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setNumeratoredocgenprllibero(Numeratore numeratoredocgenprllibero) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (numeratoredocgenprllibero != null) {
			idAzienda = KeyHelper.getTokenObjectKey(numeratoredocgenprllibero.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idNumeratorePrelLibero = getIdNumeratorePrelLibero();
		if (numeratoredocgenprllibero != null) {
			idNumeratorePrelLibero = KeyHelper.getTokenObjectKey(numeratoredocgenprllibero.getKey(), 2);
		}
		setIdNumeratorePrelLiberoInternal(idNumeratorePrelLibero);
		this.iNumeratoredocgenprllibero.setObject(numeratoredocgenprllibero);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public Numeratore getNumeratoredocgenprllibero() {
		return (Numeratore) iNumeratoredocgenprllibero.getObject();
	}

	public void setNumeratoredocgenprlliberoKey(String key) {
		String oldObjectKey = getKey();
		iNumeratoredocgenprllibero.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idNumeratorePrelLibero = KeyHelper.getTokenObjectKey(key, 2);
		setIdNumeratorePrelLiberoInternal(idNumeratorePrelLibero);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public String getNumeratoredocgenprlliberoKey() {
		return iNumeratoredocgenprllibero.getKey();
	}

	public void setNumeratoredocprdstrutture(Numeratore numeratoredocprdstrutture) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (numeratoredocprdstrutture != null) {
			idAzienda = KeyHelper.getTokenObjectKey(numeratoredocprdstrutture.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idNumeratoreProdStrutture = getIdNumeratoreProdStrutture();
		if (numeratoredocprdstrutture != null) {
			idNumeratoreProdStrutture = KeyHelper.getTokenObjectKey(numeratoredocprdstrutture.getKey(), 2);
		}
		setIdNumeratoreProdStruttureInternal(idNumeratoreProdStrutture);
		this.iNumeratoredocprdstrutture.setObject(numeratoredocprdstrutture);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public Numeratore getNumeratoredocprdstrutture() {
		return (Numeratore) iNumeratoredocprdstrutture.getObject();
	}

	public void setNumeratoredocprdstruttureKey(String key) {
		String oldObjectKey = getKey();
		iNumeratoredocprdstrutture.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idNumeratoreProdStrutture = KeyHelper.getTokenObjectKey(key, 2);
		setIdNumeratoreProdStruttureInternal(idNumeratoreProdStrutture);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public String getNumeratoredocprdstruttureKey() {
		return iNumeratoredocprdstrutture.getKey();
	}

	public void setSeriedocgenprllibero(Serie seriedocgenprllibero) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (seriedocgenprllibero != null) {
			idAzienda = KeyHelper.getTokenObjectKey(seriedocgenprllibero.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idNumeratorePrelLibero = getIdNumeratorePrelLibero();
		if (seriedocgenprllibero != null) {
			idNumeratorePrelLibero = KeyHelper.getTokenObjectKey(seriedocgenprllibero.getKey(), 2);
		}
		setIdNumeratorePrelLiberoInternal(idNumeratorePrelLibero);
		this.iSeriedocgenprllibero.setObject(seriedocgenprllibero);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public Serie getSeriedocgenprllibero() {
		return (Serie) iSeriedocgenprllibero.getObject();
	}

	public void setSeriedocgenprlliberoKey(String key) {
		String oldObjectKey = getKey();
		iSeriedocgenprllibero.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idNumeratorePrelLibero = KeyHelper.getTokenObjectKey(key, 2);
		setIdNumeratorePrelLiberoInternal(idNumeratorePrelLibero);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public String getSeriedocgenprlliberoKey() {
		return iSeriedocgenprllibero.getKey();
	}

	public void setIdNumeratorePrelLibero(String idNumeratorePrelLibero) {
		setIdNumeratorePrelLiberoInternal(idNumeratorePrelLibero);
		setDirty();
	}

	public String getIdNumeratorePrelLibero() {
		String key = iNumeratoredocgenprllibero.getKey();
		String objIdNumeratorePrelLibero = KeyHelper.getTokenObjectKey(key, 2);
		return objIdNumeratorePrelLibero;
	}

	public void setIdSerieDocPrlLibero(String idSerieDocPrlLibero) {
		String key = iSeriedocgenprllibero.getKey();
		iSeriedocgenprllibero.setKey(KeyHelper.replaceTokenObjectKey(key, 3, idSerieDocPrlLibero));
		setDirty();
	}

	public String getIdSerieDocPrlLibero() {
		String key = iSeriedocgenprllibero.getKey();
		String objIdSerieDocPrlLibero = KeyHelper.getTokenObjectKey(key, 3);
		return objIdSerieDocPrlLibero;
	}

	public void setSeriedocprodstrutture(Serie seriedocprodstrutture) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (seriedocprodstrutture != null) {
			idAzienda = KeyHelper.getTokenObjectKey(seriedocprodstrutture.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idNumeratoreProdStrutture = getIdNumeratoreProdStrutture();
		if (seriedocprodstrutture != null) {
			idNumeratoreProdStrutture = KeyHelper.getTokenObjectKey(seriedocprodstrutture.getKey(), 2);
		}
		setIdNumeratoreProdStruttureInternal(idNumeratoreProdStrutture);
		this.iSeriedocprodstrutture.setObject(seriedocprodstrutture);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public Serie getSeriedocprodstrutture() {
		return (Serie) iSeriedocprodstrutture.getObject();
	}

	public void setSeriedocprodstruttureKey(String key) {
		String oldObjectKey = getKey();
		iSeriedocprodstrutture.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idNumeratoreProdStrutture = KeyHelper.getTokenObjectKey(key, 2);
		setIdNumeratoreProdStruttureInternal(idNumeratoreProdStrutture);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public String getSeriedocprodstruttureKey() {
		return iSeriedocprodstrutture.getKey();
	}

	public void setIdNumeratoreProdStrutture(String idNumeratoreProdStrutture) {
		setIdNumeratoreProdStruttureInternal(idNumeratoreProdStrutture);
		setDirty();
	}

	public String getIdNumeratoreProdStrutture() {
		String key = iNumeratoredocprdstrutture.getKey();
		String objIdNumeratoreProdStrutture = KeyHelper.getTokenObjectKey(key, 2);
		return objIdNumeratoreProdStrutture;
	}

	public void setIdSerieDocProdStrutture(String idSerieDocProdStrutture) {
		String key = iSeriedocprodstrutture.getKey();
		iSeriedocprodstrutture.setKey(KeyHelper.replaceTokenObjectKey(key, 3, idSerieDocProdStrutture));
		setDirty();
	}

	public String getIdSerieDocProdStrutture() {
		String key = iSeriedocprodstrutture.getKey();
		String objIdSerieDocProdStrutture = KeyHelper.getTokenObjectKey(key, 3);
		return objIdSerieDocProdStrutture;
	}

	public void setCaudocgenprllibero(CausaleDocumentoGen caudocgenprllibero) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (caudocgenprllibero != null) {
			idAzienda = KeyHelper.getTokenObjectKey(caudocgenprllibero.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iCaudocgenprllibero.setObject(caudocgenprllibero);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public CausaleDocumentoGen getCaudocgenprllibero() {
		return (CausaleDocumentoGen) iCaudocgenprllibero.getObject();
	}

	public void setCaudocgenprlliberoKey(String key) {
		String oldObjectKey = getKey();
		iCaudocgenprllibero.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public String getCaudocgenprlliberoKey() {
		return iCaudocgenprllibero.getKey();
	}

	public void setIdCauDocGenPrlLibero(String idCauDocGenPrlLibero) {
		String key = iCaudocgenprllibero.getKey();
		iCaudocgenprllibero.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idCauDocGenPrlLibero));
		setDirty();
	}

	public String getIdCauDocGenPrlLibero() {
		String key = iCaudocgenprllibero.getKey();
		String objIdCauDocGenPrlLibero = KeyHelper.getTokenObjectKey(key, 2);
		return objIdCauDocGenPrlLibero;
	}

	public void setCaudocprdstrutture(CausaleDocumentoVersDist caudocprdstrutture) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (caudocprdstrutture != null) {
			idAzienda = KeyHelper.getTokenObjectKey(caudocprdstrutture.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iCaudocprdstrutture.setObject(caudocprdstrutture);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public CausaleDocumentoVersDist getCaudocprdstrutture() {
		return (CausaleDocumentoVersDist) iCaudocprdstrutture.getObject();
	}

	public void setCaudocprdstruttureKey(String key) {
		String oldObjectKey = getKey();
		iCaudocprdstrutture.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iCausaliRigaDocStrutture.setFatherKeyChanged();
		}
	}

	public String getCaudocprdstruttureKey() {
		return iCaudocprdstrutture.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
		iCausaliRigaDocStrutture.setFatherKeyChanged();
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	public void setIdCauDocProdStrutture(String idCauDocProdStrutture) {
		String key = iCaudocprdstrutture.getKey();
		iCaudocprdstrutture.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idCauDocProdStrutture));
		setDirty();
	}

	public String getIdCauDocProdStrutture() {
		String key = iCaudocprdstrutture.getKey();
		String objIdCauDocProdStrutture = KeyHelper.getTokenObjectKey(key, 2);
		return objIdCauDocProdStrutture;
	}

	@SuppressWarnings("rawtypes")
	public List getCausaliRigaDocStrutture() {
		return getCausaliRigaDocStruttureInternal();
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YPsnDatiImpMovPrdPO yPsnDatiImpMovPrdPO = (YPsnDatiImpMovPrdPO) obj;
		iNumeratoredocgenprllibero.setEqual(yPsnDatiImpMovPrdPO.iNumeratoredocgenprllibero);
		iNumeratoredocprdstrutture.setEqual(yPsnDatiImpMovPrdPO.iNumeratoredocprdstrutture);
		iSeriedocgenprllibero.setEqual(yPsnDatiImpMovPrdPO.iSeriedocgenprllibero);
		iSeriedocprodstrutture.setEqual(yPsnDatiImpMovPrdPO.iSeriedocprodstrutture);
		iCaudocgenprllibero.setEqual(yPsnDatiImpMovPrdPO.iCaudocgenprllibero);
		iCaudocprdstrutture.setEqual(yPsnDatiImpMovPrdPO.iCaudocprdstrutture);
		iCausaliRigaDocStrutture.setEqual(yPsnDatiImpMovPrdPO.iCausaliRigaDocStrutture);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(key);
	}

	public String getKey() {
		return getIdAzienda();
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public int saveOwnedObjects(int rc) throws SQLException {
		rc = iCausaliRigaDocStrutture.save(rc);
		return rc;
	}

	public int deleteOwnedObjects() throws SQLException {
		return getCausaliRigaDocStruttureInternal().delete();
	}

	public boolean initializeOwnedObjects(boolean result) {
		result = iCausaliRigaDocStrutture.initialize(result);
		return result;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YPsnDatiImpMovPrdTM.getInstance();
	}

	protected OneToMany getCausaliRigaDocStruttureInternal() {
		if (iCausaliRigaDocStrutture.isNew())
			iCausaliRigaDocStrutture.retrieve();
		return iCausaliRigaDocStrutture;
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iNumeratoredocgenprllibero.getKey();
		iNumeratoredocgenprllibero.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iNumeratoredocprdstrutture.getKey();
		iNumeratoredocprdstrutture.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		String key4 = iSeriedocgenprllibero.getKey();
		iSeriedocgenprllibero.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		String key5 = iSeriedocprodstrutture.getKey();
		iSeriedocprodstrutture.setKey(KeyHelper.replaceTokenObjectKey(key5, 1, idAzienda));
		String key6 = iCaudocgenprllibero.getKey();
		iCaudocgenprllibero.setKey(KeyHelper.replaceTokenObjectKey(key6, 1, idAzienda));
		String key7 = iCaudocprdstrutture.getKey();
		iCaudocprdstrutture.setKey(KeyHelper.replaceTokenObjectKey(key7, 1, idAzienda));
	}

	protected void setIdNumeratorePrelLiberoInternal(String idNumeratorePrelLibero) {
		String key1 = iNumeratoredocgenprllibero.getKey();
		iNumeratoredocgenprllibero.setKey(KeyHelper.replaceTokenObjectKey(key1, 2, idNumeratorePrelLibero));
		String key2 = iSeriedocgenprllibero.getKey();
		iSeriedocgenprllibero.setKey(KeyHelper.replaceTokenObjectKey(key2, 2, idNumeratorePrelLibero));
	}

	protected void setIdNumeratoreProdStruttureInternal(String idNumeratoreProdStrutture) {
		String key1 = iNumeratoredocprdstrutture.getKey();
		iNumeratoredocprdstrutture.setKey(KeyHelper.replaceTokenObjectKey(key1, 2, idNumeratoreProdStrutture));
		String key2 = iSeriedocprodstrutture.getKey();
		iSeriedocprodstrutture.setKey(KeyHelper.replaceTokenObjectKey(key2, 2, idNumeratoreProdStrutture));
	}

}
