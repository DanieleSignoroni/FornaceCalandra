package it.fornacecalandra.thip.base.generale;

import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.Child;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.thera.thip.base.articolo.ClasseD;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.EntitaAzienda;
import it.thera.thip.magazzino.documenti.CausaleRigaDocVersDist;

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
 * 72XXX    24/08/2026  DSSOF3   Prima stesura
 */

public abstract class YCauRigDocPrdStrutturePO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Child, Conflictable {

	private static YCauRigDocPrdStrutture cInstance;

	protected char iTipoMq = '-';

	protected Proxy iClassed = new Proxy(it.thera.thip.base.articolo.ClasseD.class);

	protected Proxy iCausalerigadocvrsdist = new Proxy(it.thera.thip.magazzino.documenti.CausaleRigaDocVersDist.class);

	protected Proxy iParent = new Proxy(it.fornacecalandra.thip.base.generale.YPsnDatiImpMovPrd.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YCauRigDocPrdStrutture) Factory.createObject(YCauRigDocPrdStrutture.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YCauRigDocPrdStrutture elementWithKey(String key, int lockType) throws SQLException {
		return (YCauRigDocPrdStrutture) PersistentObject.elementWithKey(YCauRigDocPrdStrutture.class, key, lockType);
	}

	public YCauRigDocPrdStrutturePO() {
		setTipoMq('-');
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setTipoMq(char tipoMq) {
		this.iTipoMq = tipoMq;
		setDirty();
	}

	public char getTipoMq() {
		return iTipoMq;
	}

	public void setClassed(ClasseD classed) {
		String idAzienda = getIdAzienda();
		if (classed != null) {
			idAzienda = KeyHelper.getTokenObjectKey(classed.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iClassed.setObject(classed);
		setDirty();
		setOnDB(false);
	}

	public ClasseD getClassed() {
		return (ClasseD) iClassed.getObject();
	}

	public void setClassedKey(String key) {
		iClassed.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getClassedKey() {
		return iClassed.getKey();
	}

	public void setIdClasseD(String idClasseD) {
		String key = iClassed.getKey();
		iClassed.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idClasseD));
		setDirty();
		setOnDB(false);
	}

	public String getIdClasseD() {
		String key = iClassed.getKey();
		String objIdClasseD = KeyHelper.getTokenObjectKey(key, 2);
		return objIdClasseD;
	}

	public void setCausalerigadocvrsdist(CausaleRigaDocVersDist causalerigadocvrsdist) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (causalerigadocvrsdist != null) {
			idAzienda = KeyHelper.getTokenObjectKey(causalerigadocvrsdist.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iCausalerigadocvrsdist.setObject(causalerigadocvrsdist);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public CausaleRigaDocVersDist getCausalerigadocvrsdist() {
		return (CausaleRigaDocVersDist) iCausalerigadocvrsdist.getObject();
	}

	public void setCausalerigadocvrsdistKey(String key) {
		String oldObjectKey = getKey();
		iCausalerigadocvrsdist.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getCausalerigadocvrsdistKey() {
		return iCausalerigadocvrsdist.getKey();
	}

	public void setIdCausaleRigaDocVrs(String idCausaleRigaDocVrs) {
		String key = iCausalerigadocvrsdist.getKey();
		iCausalerigadocvrsdist.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idCausaleRigaDocVrs));
		setDirty();
	}

	public String getIdCausaleRigaDocVrs() {
		String key = iCausalerigadocvrsdist.getKey();
		String objIdCausaleRigaDocVrs = KeyHelper.getTokenObjectKey(key, 2);
		return objIdCausaleRigaDocVrs;
	}

	public void setParent(YPsnDatiImpMovPrd parent) {
		setIdAziendaInternal(parent.getKey());
		this.iParent.setObject(parent);
		setDirty();
		setOnDB(false);
	}

	public YPsnDatiImpMovPrd getParent() {
		return (YPsnDatiImpMovPrd) iParent.getObject();
	}

	public void setParentKey(String key) {
		iParent.setKey(key);
		setIdAziendaInternal(key);
		setDirty();
		setOnDB(false);
	}

	public String getParentKey() {
		return iParent.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YCauRigDocPrdStrutturePO yCauRigDocPrdStrutturePO = (YCauRigDocPrdStrutturePO) obj;
		iClassed.setEqual(yCauRigDocPrdStrutturePO.iClassed);
		iCausalerigadocvrsdist.setEqual(yCauRigDocPrdStrutturePO.iCausalerigadocvrsdist);
		iParent.setEqual(yCauRigDocPrdStrutturePO.iParent);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setIdClasseD(KeyHelper.getTokenObjectKey(key, 2));
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idClasseD = getIdClasseD();
		Object[] keyParts = { idAzienda, idClasseD };
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String getFatherKey() {
		return getParentKey();
	}

	public void setFatherKey(String key) {
		setParentKey(key);
	}

	public void setFather(PersistentObject father) {
		iParent.setObject(father);
	}

	public String getOrderByClause() {
		return "";
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YCauRigDocPrdStruttureTM.getInstance();
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iClassed.getKey();
		iClassed.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iCausalerigadocvrsdist.getKey();
		iCausalerigadocvrsdist.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		iParent.setKey(idAzienda);
	}

}
