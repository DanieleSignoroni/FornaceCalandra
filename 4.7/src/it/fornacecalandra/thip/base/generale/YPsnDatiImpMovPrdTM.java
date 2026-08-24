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

public class YPsnDatiImpMovPrdTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String R_NUM_DOC_PRL_LIBERO = "R_NUM_DOC_PRL_LIBERO";

	public static final String R_NUM_DOC_PRD_STRUTTURE = "R_NUM_DOC_PRD_STRUTTURE";

	public static final String R_SERIE_DOC_PRL_LIBERO = "R_SERIE_DOC_PRL_LIBERO";

	public static final String R_SERIE_DOC_PRD_STRUTTURE = "R_SERIE_DOC_PRD_STRUTTURE";

	public static final String R_CAU_DOC_GEN_PRL_LIBERO = "R_CAU_DOC_GEN_PRL_LIBERO";

	public static final String R_CAU_DOC_PRD_STRUTTURE = "R_CAU_DOC_PRD_STRUTTURE";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YPSN_DATI_IMP_MOV_PRD";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.fornacecalandra.thip.base.generale.YPsnDatiImpMovPrd.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YPsnDatiImpMovPrdTM.class);
		}
		return cInstance;
	}

	public YPsnDatiImpMovPrdTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("IdNumeratorePrelLibero", R_NUM_DOC_PRL_LIBERO);
		addAttribute("IdNumeratoreProdStrutture", R_NUM_DOC_PRD_STRUTTURE);
		addAttribute("IdSerieDocPrlLibero", R_SERIE_DOC_PRL_LIBERO);
		addAttribute("IdSerieDocProdStrutture", R_SERIE_DOC_PRD_STRUTTURE);
		addAttribute("IdCauDocGenPrlLibero", R_CAU_DOC_GEN_PRL_LIBERO);
		addAttribute("IdCauDocProdStrutture", R_CAU_DOC_PRD_STRUTTURE);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure(ID_AZIENDA + ", " + R_NUM_DOC_PRL_LIBERO + ", " + R_NUM_DOC_PRD_STRUTTURE + ", "
				+ R_SERIE_DOC_PRL_LIBERO + ", " + R_SERIE_DOC_PRD_STRUTTURE + ", " + R_CAU_DOC_GEN_PRL_LIBERO + ", "
				+ R_CAU_DOC_PRD_STRUTTURE + ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", "
				+ R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
	}

}
