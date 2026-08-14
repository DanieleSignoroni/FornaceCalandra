package it.fornacecalandra.thip.base.generale.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.json.JSONObject;

import com.thera.thermfw.rs.BaseResource;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 14/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    14/08/2026  DSSOF3   Prima stesura
 */

@Path("/softre/produzione")
public class YProduzioneCalandraResource extends BaseResource {

	private YProduzioneCalandraService service = YProduzioneCalandraService.getService(); 

	@Path("/prelievoLiberoMateriali")
	@POST
	public Response registraPrelievoLiberoMateriali(String payload) {
		JSONObject response = service.prelievoLiberoMateriali(new JSONObject(payload));
		return buildResponse((StatusType) response.get("status"),response.get("response"));
	}
	
	@Path("/produzioneStruttura")
	@POST
	public Response registraProduzioneStruttura(String payload) {
		JSONObject response = service.produzioneStruttura(new JSONObject(payload));
		return buildResponse((StatusType) response.get("status"),response.get("response"));
	}
}