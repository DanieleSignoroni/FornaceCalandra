package it.fornacecalandra.thip.base.generale;

import java.sql.SQLException;
import java.util.Hashtable;

import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.Cacheable;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.PersistentObjectCache;

import it.thera.thip.base.azienda.Azienda;

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

public class YPsnDatiImpMovPrd extends YPsnDatiImpMovPrdPO implements Cacheable{

	@SuppressWarnings("rawtypes")
	protected static Hashtable iHistory_YPsnDatiImpMovPrd = new Hashtable();

	public static YPsnDatiImpMovPrd getCurrentYPsnDatiImpMovPrd(){
		YPsnDatiImpMovPrd persDatiGen = getYPsnDatiImpMovPrd(Azienda.getAziendaCorrente());
		if (persDatiGen == null)
			return persDatiGen;
		return persDatiGen;
	}

	@SuppressWarnings("unchecked")
	public static YPsnDatiImpMovPrd getYPsnDatiImpMovPrd(String iIdAzienda)
	{
		if (iIdAzienda == null)
			return null;

		YPsnDatiImpMovPrd iYPsnDatiImpMovPrd = null;

		try
		{
			if(PersistentObjectCache.isEnabled())
			{
				return (YPsnDatiImpMovPrd)PersistentObject.readOnlyElementWithKey(YPsnDatiImpMovPrd.class, iIdAzienda);
			}
			else
			{
				if(iHistory_YPsnDatiImpMovPrd.containsKey(iIdAzienda))
					return (YPsnDatiImpMovPrd)iHistory_YPsnDatiImpMovPrd.get(iIdAzienda);
				else
				{
					iYPsnDatiImpMovPrd=YPsnDatiImpMovPrd.elementWithKey(iIdAzienda, PersistentObject.OPTIMISTIC_LOCK);
					if(iYPsnDatiImpMovPrd != null)
						iHistory_YPsnDatiImpMovPrd.put(iIdAzienda,iYPsnDatiImpMovPrd);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}

		return iYPsnDatiImpMovPrd;
	}

	@SuppressWarnings("unchecked")
	public int saveOwnedObjects(int rc) throws SQLException
	{
		rc += super.saveOwnedObjects(rc);
		if(rc >= 0)
			iHistory_YPsnDatiImpMovPrd.put(this.getIdAzienda(),this);
		return rc;
	}


	public ErrorMessage checkDelete() {

		return null;
	}

}
