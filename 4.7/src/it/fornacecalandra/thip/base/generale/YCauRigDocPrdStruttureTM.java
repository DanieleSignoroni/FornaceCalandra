package it.fornacecalandra.thip.base.generale;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

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

public class YCauRigDocPrdStruttureTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String R_CLASSE_D = "R_CLASSE_D";

	public static final String R_CAU_RIGA_DOC_VRS = "R_CAU_RIGA_DOC_VRS";

	public static final String TIPO_MQ = "TIPO_MQ";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YCAU_RIG_DOC_PRD_STRUTTURE";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.fornacecalandra.thip.base.generale.YCauRigDocPrdStrutture.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YCauRigDocPrdStruttureTM.class);
		}
		return cInstance;
	}

	public YCauRigDocPrdStruttureTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("TipoMq", TIPO_MQ);
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("IdClasseD", R_CLASSE_D);
		addAttribute("IdCausaleRigaDocVrs", R_CAU_RIGA_DOC_VRS);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + R_CLASSE_D);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure(TIPO_MQ + ", " + ID_AZIENDA + ", " + R_CLASSE_D + ", " + R_CAU_RIGA_DOC_VRS + ", " + STATO + ", "
				+ R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
	}

}
