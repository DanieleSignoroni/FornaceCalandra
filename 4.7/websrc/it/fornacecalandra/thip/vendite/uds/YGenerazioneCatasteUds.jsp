<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.1.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<html>
<!-- WIZGEN Therm 2.0.0 as Batch form - multiBrowserGen = true -->
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
  BODataCollector YGenCatasteUdsBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YGenCatasteUdsForm =  
     new com.thera.thermfw.web.WebFormForBatchForm(request, response, "YGenCatasteUdsForm", "YGenCatasteUds", "Arial,10", "com.thera.thermfw.batch.web.BatchFormActionAdapter", false, false, false, true, true, true, null, 0, false, null); 
  YGenCatasteUdsForm.setServletEnvironment(se); 
  YGenCatasteUdsForm.setJSTypeList(jsList); 
  YGenCatasteUdsForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YGenCatasteUdsForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  ((WebFormForBatchForm)  YGenCatasteUdsForm).setGenerateThReportId(true); 
  ((WebFormForBatchForm)  YGenCatasteUdsForm).setGenerateSSDEnabled(true); 
  YGenCatasteUdsForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YGenCatasteUdsForm.getMode(); 
  String key = YGenCatasteUdsForm.getKey(); 
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
        YGenCatasteUdsForm.outTraceInfo(getClass().getName()); 
        String collectorName = YGenCatasteUdsForm.findBODataCollectorName(); 
				 YGenCatasteUdsBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YGenCatasteUdsBODC instanceof WebDataCollector) 
            ((WebDataCollector)YGenCatasteUdsBODC).setServletEnvironment(se); 
        YGenCatasteUdsBODC.initialize("YGenCatasteUds", true, 0); 
        int rcBODC; 
        if (YGenCatasteUdsBODC.getBo() instanceof BatchRunnable) 
          rcBODC = YGenCatasteUdsBODC.initSecurityServices("RUN", mode, true, false, true); 
        else 
          rcBODC = YGenCatasteUdsBODC.initSecurityServices(mode, true, true, true); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YGenCatasteUdsForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YGenCatasteUdsBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YGenCatasteUdsForm.setBODataCollector(YGenCatasteUdsBODC); 
              YGenCatasteUdsForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<% 
  WebLink link_0 =  
   new com.thera.thermfw.web.WebLink(); 
 link_0.setHttpServletRequest(request); 
 link_0.setHRefAttribute("thermweb/css/thermGrid.css"); 
 link_0.setRelAttribute("STYLESHEET"); 
 link_0.setTypeAttribute("text/css"); 
  link_0.write(out); 
%>
<!--<link href="thermweb/css/thermGrid.css" rel="STYLESHEET" type="text/css">-->
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YGenCatasteUdsForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/com/thera/thermfw/batch/BatchRunnableMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YGenCatasteUdsForm.getBodyOnBeforeUnload()%>" onload="<%=YGenCatasteUdsForm.getBodyOnLoad()%>" onunload="<%=YGenCatasteUdsForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YGenCatasteUdsForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YGenCatasteUdsForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YGenCatasteUdsBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YGenCatasteUdsForm.getServlet()%>" method="post" name="YGenCatasteUdsForm" style="height:100%"><%
  YGenCatasteUdsForm.writeFormStartElements(out); 
%>

		<table cellpadding="2" cellspacing="2" width="100%">
			<tr>
				<td style="height: 0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<tr>
				<td>
					<table style="width: 100%;">
						<tr>
							<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YGenCatasteUds", "IdTipoUds", null); 
   label.setParent(YGenCatasteUdsForm); 
%><label class="<%=label.getClassType()%>" for="TipoUds"><%label.write(out);%></label><%}%></td>
							<td valign="top"><% 
  WebMultiSearchForm YGenCatasteUdsTipoUds =  
     new com.thera.thermfw.web.WebMultiSearchForm("YGenCatasteUds", "TipoUds", false, false, true, 1, null, null); 
  YGenCatasteUdsTipoUds.setParent(YGenCatasteUdsForm); 
  YGenCatasteUdsTipoUds.write(out); 
%>
<!--<span class="multisearchform" id="TipoUds"></span>--></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YGenCatasteUdsForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YGenCatasteUdsForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YGenCatasteUdsForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YGenCatasteUdsBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YGenCatasteUdsForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YGenCatasteUdsBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YGenCatasteUdsBODC.getErrorList().getErrors()); 
           if(YGenCatasteUdsBODC.getConflict() != null) 
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
     if(YGenCatasteUdsBODC != null && !YGenCatasteUdsBODC.close(false)) 
        errors.addAll(0, YGenCatasteUdsBODC.getErrorList().getErrors()); 
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
     String errorPage = YGenCatasteUdsForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YGenCatasteUdsBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YGenCatasteUdsForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
