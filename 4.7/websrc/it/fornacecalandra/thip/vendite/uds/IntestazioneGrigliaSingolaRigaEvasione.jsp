<%@page import="com.thera.thermfw.web.WebForm"%>
<%@page import="com.thera.thermfw.web.WebLabelSimple"%>
<%
WebForm YEvasioneUdsVenRigaForm = (WebForm) request.getAttribute("YEvasioneUdsVenRigaForm");
%>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaSerieLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.fornacecalandra.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.Serie", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaCommessaLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.Commessa", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>