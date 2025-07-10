<%@page import="it.fornacecalandra.thip.vendite.uds.YEvasioneUdsVenRiga"%>
<%
YEvasioneUdsVenRiga riga = (YEvasioneUdsVenRiga) request.getAttribute(request.getParameter("thDocVenRigaPrm"));
String idx = request.getParameter("thDocVenRigaPrmIdx");
%>
<td class="cellSerie">
    <input size="20" type="text" id="Serie<%=idx%>" name="Serie<%=idx%>" value="<%=riga.getSerie()%>" disabled=""/>
</td>
<td class="cellIdCommessa">
    	<input size="20" type="text" id="Commessa<%=idx%>" name="Commessa<%=idx%>" value="<%=riga.getIdCommessa()%>" disabled=""/>
</td>