<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.1.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<html>
<!-- WIZGEN Therm 2.0.0 as Form - multiBrowserGen = true -->
<%=WebGenerator.writeRuntimeInfo()%>
<head>
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
  BODataCollector YPsnDatiImpMovPrdBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YPsnDatiImpMovPrdForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YPsnDatiImpMovPrdForm", "YPsnDatiImpMovPrd", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/fornacecalandra/thip/base/generale/YPsnDatiImpMovPrd.js"); 
  YPsnDatiImpMovPrdForm.setServletEnvironment(se); 
  YPsnDatiImpMovPrdForm.setJSTypeList(jsList); 
  YPsnDatiImpMovPrdForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YPsnDatiImpMovPrdForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YPsnDatiImpMovPrdForm.setWebFormModifierClass("it.thera.thip.cs.web.DatiPersWebFormModifier"); 
  YPsnDatiImpMovPrdForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YPsnDatiImpMovPrdForm.getMode(); 
  String key = YPsnDatiImpMovPrdForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  boolean conflitPresent = false; 
  String leftClass = ""; 
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        YPsnDatiImpMovPrdForm.outTraceInfo(getClass().getName()); 
        String collectorName = YPsnDatiImpMovPrdForm.findBODataCollectorName(); 
                YPsnDatiImpMovPrdBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YPsnDatiImpMovPrdBODC instanceof WebDataCollector) 
            ((WebDataCollector)YPsnDatiImpMovPrdBODC).setServletEnvironment(se); 
        YPsnDatiImpMovPrdBODC.initialize("YPsnDatiImpMovPrd", true, 0); 
        YPsnDatiImpMovPrdForm.setBODataCollector(YPsnDatiImpMovPrdBODC); 
        int rcBODC = YPsnDatiImpMovPrdForm.initSecurityServices(); 
        mode = YPsnDatiImpMovPrdForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YPsnDatiImpMovPrdForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YPsnDatiImpMovPrdBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YPsnDatiImpMovPrdForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YPsnDatiImpMovPrdForm); 
   request.setAttribute("menuBar", menuBar); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="menuBar"/> 
</jsp:include> 
<% 
  menuBar.write(out); 
  menuBar.writeChildren(out); 
%> 
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YPsnDatiImpMovPrdForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
<body onbeforeunload="<%=YPsnDatiImpMovPrdForm.getBodyOnBeforeUnload()%>" onload="<%=YPsnDatiImpMovPrdForm.getBodyOnLoad()%>" onunload="<%=YPsnDatiImpMovPrdForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YPsnDatiImpMovPrdForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YPsnDatiImpMovPrdForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YPsnDatiImpMovPrdBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YPsnDatiImpMovPrdForm.getServlet()%>" method="post" name="YPsnDatiImpMovPrdForm" style="height:100%"><%
  YPsnDatiImpMovPrdForm.writeFormStartElements(out); 
%>

		<table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" width="100%">
			<tr>
				<td style="height: 0"><% menuBar.writeElements(out); %> 
</td>
			</tr>
			<tr>
				<td style="height: 0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<tr>
				<td height="100%"><!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(YPsnDatiImpMovPrdForm); 
 mytabbed.addTab("tab1", "it.fornacecalandra.thip.base.generale.resources.YPsnDatiImpMovPrd", "tab1", "YPsnDatiImpMovPrd", null, null, null, null); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;"> <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab1")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab1"); %>
							<table style="width: 100%;">
								<tr>
									<td>
										<fieldset style="width: fit-content;">
											<legend>Prelievo libero materiali</legend>
											<table>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiImpMovPrd", "IdSerieDocPrlLibero", null); 
   label.setParent(YPsnDatiImpMovPrdForm); 
%><label class="<%=label.getClassType()%>" for="SerieDocGenPrlLibero"><%label.write(out);%></label><%}%>
													</td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiImpMovPrdSerieDocGenPrlLibero =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiImpMovPrd", "SerieDocGenPrlLibero", false, false, true, 2, null, null); 
  YPsnDatiImpMovPrdSerieDocGenPrlLibero.setParent(YPsnDatiImpMovPrdForm); 
  YPsnDatiImpMovPrdSerieDocGenPrlLibero.write(out); 
%>
<!--<span class="multisearchform" id="SerieDocGenPrlLibero"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiImpMovPrd", "IdCauDocGenPrlLibero", null); 
   label.setParent(YPsnDatiImpMovPrdForm); 
%><label class="<%=label.getClassType()%>" for="CauDocGenPrlLibero"><%label.write(out);%></label><%}%>
													</td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiImpMovPrdCauDocGenPrlLibero =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiImpMovPrd", "CauDocGenPrlLibero", false, false, true, 1, null, null); 
  YPsnDatiImpMovPrdCauDocGenPrlLibero.setParent(YPsnDatiImpMovPrdForm); 
  YPsnDatiImpMovPrdCauDocGenPrlLibero.write(out); 
%>
<!--<span class="multisearchform" id="CauDocGenPrlLibero"></span>--></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
								<tr>
									<td>
										<fieldset style="width: fit-content;">
											<legend>Produzione strutture</legend>
											<table>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiImpMovPrd", "IdSerieDocProdStrutture", null); 
   label.setParent(YPsnDatiImpMovPrdForm); 
%><label class="<%=label.getClassType()%>" for="SerieDocProdStrutture"><%label.write(out);%></label><%}%>
													</td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiImpMovPrdSerieDocProdStrutture =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiImpMovPrd", "SerieDocProdStrutture", false, false, true, 2, null, null); 
  YPsnDatiImpMovPrdSerieDocProdStrutture.setParent(YPsnDatiImpMovPrdForm); 
  YPsnDatiImpMovPrdSerieDocProdStrutture.write(out); 
%>
<!--<span class="multisearchform" id="SerieDocProdStrutture"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiImpMovPrd", "IdCauDocProdStrutture", null); 
   label.setParent(YPsnDatiImpMovPrdForm); 
%><label class="<%=label.getClassType()%>" for="CauDocPrdStrutture"><%label.write(out);%></label><%}%>
													</td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiImpMovPrdCauDocPrdStrutture =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiImpMovPrd", "CauDocPrdStrutture", false, false, true, 1, null, null); 
  YPsnDatiImpMovPrdCauDocPrdStrutture.setParent(YPsnDatiImpMovPrdForm); 
  YPsnDatiImpMovPrdCauDocPrdStrutture.write(out); 
%>
<!--<span class="multisearchform" id="CauDocPrdStrutture"></span>--></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
								<tr>
									<td colspan="2" valign="top"><!--<span class="editgrid" id="CausaliRigaDocStrutture">--><% 
  WebEditGrid YPsnDatiImpMovPrdCausaliRigaDocStrutture =  
     new com.thera.thermfw.web.WebEditGrid("YPsnDatiImpMovPrd", "CausaliRigaDocStrutture", 8, new String[]{"IdClasseD", "IdAzienda", "ClasseD.Descrizione.Descrizione", "IdCausaleRigaDocVrs", "CausaleRigaDocVrsDist.Descrizione.Descrizione", "TipoMq", "Azienda.Descrizione"}, 1, null, null,false,"com.thera.thermfw.web.servlet.GridActionAdapterForIndependentRow"); 
 YPsnDatiImpMovPrdCausaliRigaDocStrutture.setParent(YPsnDatiImpMovPrdForm); 
 YPsnDatiImpMovPrdCausaliRigaDocStrutture.setNoControlRowKeys(false); 
 YPsnDatiImpMovPrdCausaliRigaDocStrutture.addHideAsDefault("Azienda.Descrizione"); 
 YPsnDatiImpMovPrdCausaliRigaDocStrutture.write(out); 
%>
<BR><% 
   request.setAttribute("parentForm", YPsnDatiImpMovPrdForm); 
   String CDForCausaliRigaDocStrutture = "CausaliRigaDocStrutture"; 
%>
<jsp:include page="/it/fornacecalandra/thip/base/generale/YCauRigDocPrdStrutture.jsp" flush="true"> 
<jsp:param name="EditGridCDName" value="<%=CDForCausaliRigaDocStrutture%>"/> 
<jsp:param name="Mode" value="NEW"/> 
</jsp:include> 
<!--</span>--></td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>

												<td valign="top"><% 
   request.setAttribute("parentForm", YPsnDatiImpMovPrdForm); 
   String CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp = "DatiComuniEstesi"; 
%>
<jsp:include page="/it/thera/thip/cs/DatiComuniEstesi.jsp" flush="true"> 
<jsp:param name="CDName" value="<%=CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp%>"/> 
</jsp:include> 
<!--<span class="subform" id="DatiComuniEstesi"></span>--></td>
												<td valign="top"></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
					<% mytabbed.endTab(); %> 
</div>
				</div><% mytabbed.endTabbed();%> 

     </td>
   </tr>
</table><!--</span>--></td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YPsnDatiImpMovPrdForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YPsnDatiImpMovPrdForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YPsnDatiImpMovPrdForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YPsnDatiImpMovPrdBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YPsnDatiImpMovPrdForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YPsnDatiImpMovPrdBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YPsnDatiImpMovPrdBODC.getErrorList().getErrors()); 
           if(YPsnDatiImpMovPrdBODC.getConflict() != null) 
                conflitPresent = true; 
     } 
     else 
        errors.add(new ErrorMessage("BAS0000010")); 
  } 
  catch(NamingException e) { 
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("CBS000025", errorMessage));  } 
  catch(SQLException e) {
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("BAS0000071", errorMessage));  } 
  catch(Throwable e) {
     e.printStackTrace(Trace.excStream);
  }
  finally 
  {
     if(YPsnDatiImpMovPrdBODC != null && !YPsnDatiImpMovPrdBODC.close(false)) 
        errors.addAll(0, YPsnDatiImpMovPrdBODC.getErrorList().getErrors()); 
     try 
     { 
        se.end(); 
     }
     catch(IllegalArgumentException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
     catch(SQLException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
  } 
  if(!errors.isEmpty())
  { 
      if(!conflitPresent)
  { 
     request.setAttribute("ErrorMessages", errors); 
     String errorPage = YPsnDatiImpMovPrdForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YPsnDatiImpMovPrdBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YPsnDatiImpMovPrdForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
