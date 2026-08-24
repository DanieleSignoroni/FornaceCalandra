<!-- WIZGEN Therm 2.0.0 as Form riga interna - multiBrowserGen = true -->
<% 
  if(false) 
  { 
%> 
<head><% 
  } 
%> 

<%@ page contentType="text/html; charset=Cp1252"%>
<%@ page import= " 
  java.sql.*, 
  java.util.*, 
  java.lang.reflect.*, 
  javax.naming.*, 
  com.thera.thermfw.common.*, 
  com.thera.thermfw.type.*, 
  com.thera.thermfw.web.*, 
  com.thera.thermfw.security.*, 
  com.thera.thermfw.base.*, 
  com.thera.thermfw.ad.*, 
  com.thera.thermfw.persist.*, 
  com.thera.thermfw.gui.cnr.*, 
  com.thera.thermfw.setting.*, 
  com.thera.thermfw.collector.*, 
  com.thera.thermfw.batch.web.*, 
  com.thera.thermfw.batch.*, 
  com.thera.thermfw.pref.* 
"%> 
<%
  ServletEnvironment se = (ServletEnvironment)Factory.createObject("com.thera.thermfw.web.ServletEnvironment"); 
  BODataCollector YCauRigDocPrdStruttureBODC = null; 
  WebFormForInternalRowForm YCauRigDocPrdStruttureForm =  
     new com.thera.thermfw.web.WebFormForInternalRowForm(request, response, "YCauRigDocPrdStruttureForm", "YCauRigDocPrdStrutture", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, false, false, true, true, null, 1); 
  int mode = YCauRigDocPrdStruttureForm.getMode(); 
  String key = YCauRigDocPrdStruttureForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  String leftClass = ""; 
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        YCauRigDocPrdStruttureForm.outTraceInfo(getClass().getName()); 
        ClassADCollection globCadc = YCauRigDocPrdStruttureForm.getClassADCollection(); 
        requestIsValid = true; 
        YCauRigDocPrdStruttureForm.write(out); 
        String collectorName = YCauRigDocPrdStruttureForm.findBODataCollectorName(); 
				 YCauRigDocPrdStruttureBODC = (BODataCollector)Factory.createObject(collectorName); 
        YCauRigDocPrdStruttureBODC.initialize("YCauRigDocPrdStrutture", true, 1); 
        YCauRigDocPrdStruttureForm.setBODataCollector(YCauRigDocPrdStruttureBODC); 
        WebForm parentForm = (WebForm)request.getAttribute("parentForm"); 
        YCauRigDocPrdStruttureForm.setJSTypeList(parentForm.getOwnerForm().getJSTypeList()); 
        YCauRigDocPrdStruttureForm.setParent(parentForm); 
        YCauRigDocPrdStruttureForm.writeHeadElements(out); 
     }
  }
  catch(NamingException e) { 
    errorMessage = e.getMessage(); 
  } 
  catch(SQLException e) { 
     errorMessage = e.getMessage(); 
  } 
  finally 
  { 
     try 
     { 
        se.end(); 
     } 
     catch(IllegalArgumentException e) { 
        e.printStackTrace(); 
     } 
     catch(SQLException e) { 
        e.printStackTrace(); 
     } 
  } 
%> 
<% 
  if(false) 
  { 
%> 
</head><% 
  } 
%> 


<% 
  if(false) 
  { 
%> 
<body style="margin: 0px; overflow: hidden;"><% 
  } 
%> 
<%
   YCauRigDocPrdStruttureForm.writeBodyStartElements(out); 
%> 

	<% 
  if(false) 
  { 
%> 
<form name="YCauRigDocPrdStruttureForm"><% 
  } 
%> 
<%
   YCauRigDocPrdStruttureForm.writeFormStartElements(out); 
%> 

		<table id="emptyborder">
			<tr>
				<td><% 
  WebTextInput YCauRigDocPrdStruttureIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YCauRigDocPrdStrutture", "IdAzienda"); 
  YCauRigDocPrdStruttureIdAzienda.setParent(YCauRigDocPrdStruttureForm); 
%>
<input class="<%=YCauRigDocPrdStruttureIdAzienda.getClassType()%>" id="<%=YCauRigDocPrdStruttureIdAzienda.getId()%>" maxlength="<%=YCauRigDocPrdStruttureIdAzienda.getMaxLength()%>" name="<%=YCauRigDocPrdStruttureIdAzienda.getName()%>" size="<%=YCauRigDocPrdStruttureIdAzienda.getSize()%>" type="hidden"><% 
  YCauRigDocPrdStruttureIdAzienda.write(out); 
%>
</td>
			</tr>
			<tr>
				<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCauRigDocPrdStrutture", "IdClasseD", null); 
   label.setParent(YCauRigDocPrdStruttureForm); 
%><label class="<%=label.getClassType()%>" for="ClasseD"><%label.write(out);%></label><%}%></td>
				<td valign="top"><% 
  WebMultiSearchForm YCauRigDocPrdStruttureClasseD =  
     new com.thera.thermfw.web.WebMultiSearchForm("YCauRigDocPrdStrutture", "ClasseD", false, false, true, 1, null, null); 
  YCauRigDocPrdStruttureClasseD.setParent(YCauRigDocPrdStruttureForm); 
  YCauRigDocPrdStruttureClasseD.write(out); 
%>
<!--<span class="multisearchform" id="ClasseD"></span>--></td>
			</tr>
			<tr>
				<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCauRigDocPrdStrutture", "IdCausaleRigaDocVrs", null); 
   label.setParent(YCauRigDocPrdStruttureForm); 
%><label class="<%=label.getClassType()%>" for="CausaleRigaDocVrsDist"><%label.write(out);%></label><%}%></td>
				<td valign="top"><% 
  WebMultiSearchForm YCauRigDocPrdStruttureCausaleRigaDocVrsDist =  
     new com.thera.thermfw.web.WebMultiSearchForm("YCauRigDocPrdStrutture", "CausaleRigaDocVrsDist", false, false, true, 1, null, null); 
  YCauRigDocPrdStruttureCausaleRigaDocVrsDist.setParent(YCauRigDocPrdStruttureForm); 
  YCauRigDocPrdStruttureCausaleRigaDocVrsDist.write(out); 
%>
<!--<span class="multisearchform" id="CausaleRigaDocVrsDist"></span>--></td>
			</tr>
			<tr>
				<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCauRigDocPrdStrutture", "TipoMq", null); 
   label.setParent(YCauRigDocPrdStruttureForm); 
%><label class="<%=label.getClassType()%>" for="TipoMq"><%label.write(out);%></label><%}%></td>
				<td valign="top"><% 
  WebComboBox YCauRigDocPrdStruttureTipoMq =  
     new com.thera.thermfw.web.WebComboBox("YCauRigDocPrdStrutture", "TipoMq", null); 
  YCauRigDocPrdStruttureTipoMq.setParent(YCauRigDocPrdStruttureForm); 
%>
<select id="<%=YCauRigDocPrdStruttureTipoMq.getId()%>" name="<%=YCauRigDocPrdStruttureTipoMq.getName()%>"><% 
  YCauRigDocPrdStruttureTipoMq.write(out); 
%> 
</select></td>
			</tr>
		</table>
	<%
  YCauRigDocPrdStruttureForm.writeFormEndElements(out); 
%>
<% 
  if(false) 
  { 
%> 
</form><% 
  } 
%> 

<%
   YCauRigDocPrdStruttureForm.writeBodyEndElements(out); 
%> 
<% 
  if(false) 
  { 
%> 
</body><% 
  } 
%> 

