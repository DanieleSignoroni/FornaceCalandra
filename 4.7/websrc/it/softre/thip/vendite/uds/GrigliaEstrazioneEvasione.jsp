<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.1.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<%@page import="it.thera.thip.base.documenti.web.DocumentoDatiSessione"%>
<%@page import="it.softre.thip.vendite.uds.web.YDatiSessioneEvasioneUdsVendita"%>
<html>
<!-- WIZGEN Therm 2.0.0 as Form - multiBrowserGen = true -->
<%=WebGenerator.writeRuntimeInfo()%>
<title>Griglia estrazione UDS</title>

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
  BODataCollector YEvasioneUdsVenditaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YEvasioneUdsVenditaForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YEvasioneUdsVenditaForm", "YEvasioneUdsVendita", null, "it.softre.thip.vendite.uds.web.YEvasioneUdsGrigliaFormActionAdapter", false, false, true, true, true, true, null, 1, true, "it/softre/thip/vendite/uds/GrigliaEstrazioneEvasione.js"); 
  YEvasioneUdsVenditaForm.setServletEnvironment(se); 
  YEvasioneUdsVenditaForm.setJSTypeList(jsList); 
  YEvasioneUdsVenditaForm.setHeader("it.thera.thip.cs.Header.jsp"); 
  YEvasioneUdsVenditaForm.setFooter("it.thera.thip.cs.Footer.jsp"); 
  YEvasioneUdsVenditaForm.setWebFormModifierClass("it.softre.thip.vendite.uds.web.YEvasioneUdsGrigliaFormModifier"); 
  YEvasioneUdsVenditaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YEvasioneUdsVenditaForm.getMode(); 
  String key = YEvasioneUdsVenditaForm.getKey(); 
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
        YEvasioneUdsVenditaForm.outTraceInfo(getClass().getName()); 
        String collectorName = YEvasioneUdsVenditaForm.findBODataCollectorName(); 
                YEvasioneUdsVenditaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YEvasioneUdsVenditaBODC instanceof WebDataCollector) 
            ((WebDataCollector)YEvasioneUdsVenditaBODC).setServletEnvironment(se); 
        YEvasioneUdsVenditaBODC.initialize("YEvasioneUdsVendita", true, 1); 
        YEvasioneUdsVenditaForm.setBODataCollector(YEvasioneUdsVenditaBODC); 
        int rcBODC = YEvasioneUdsVenditaForm.initSecurityServices(); 
        mode = YEvasioneUdsVenditaForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YEvasioneUdsVenditaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YEvasioneUdsVenditaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YEvasioneUdsVenditaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<% 
  WebToolBar GrigliaToolBarTB = new com.thera.thermfw.web.WebToolBar("GrigliaToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  GrigliaToolBarTB.setParent(YEvasioneUdsVenditaForm); 
   request.setAttribute("toolBar", GrigliaToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenuGrigliaEvasione.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   GrigliaToolBarTB.write(out); 
%> 
</head>
<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YEvasioneUdsVenditaForm.getBodyOnBeforeUnload()%>" onload="<%=YEvasioneUdsVenditaForm.getBodyOnLoad()%>" onunload="<%=YEvasioneUdsVenditaForm.getBodyOnUnload()%>" rightmargin="0" style="overflow: hidden" topmargin="0"><%
   YEvasioneUdsVenditaForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YEvasioneUdsVenditaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YEvasioneUdsVenditaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YEvasioneUdsVenditaForm.getServlet()%>" method="post" name="YEvasioneUdsGrigliaForm" style="height:100%"><%
  YEvasioneUdsVenditaForm.writeFormStartElements(out); 
%>

		<table align="center" border="0" height="100%" width="100%">
			<tr valign="top">
				<td style="height: 0"><% GrigliaToolBarTB.writeChildren(out); %> 

				</td>
			</tr>
			<tr valign="top">
    <td>
      <fieldset>
        <table align="center" width="100%">
          <tr>
           <th align="left">
              <label for="DataDocField">Data Doc.</label>
            </th>
            <th align="left">
              <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YEvasioneUdsVendita", "RCliente", null); 
   label.setParent(YEvasioneUdsVenditaForm); 
%><label class="<%=label.getClassType()%>" for="ClienteField"><%label.write(out);%></label><%}%>
            </th>
            <th align="left">
            &nbsp;
            </th>
            <th align="left">
              <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YEvasioneUdsVendita", "RCauDocTes", null); 
   label.setParent(YEvasioneUdsVenditaForm); 
%><label class="<%=label.getClassType()%>" for="CausaleField"><%label.write(out);%></label><%}%>
            </th>
            <th align="left">
            &nbsp;
            </th>
          </tr>
          <tr>
          <td align="left" width="5">
              <% 
  WebTextInput YEvasioneUdsVenditaDataDocumento =  
     new com.thera.thermfw.web.WebTextInput("YEvasioneUdsVendita", "DataDocumento"); 
  YEvasioneUdsVenditaDataDocumento.setShowCalendarBtn(false); 
  YEvasioneUdsVenditaDataDocumento.setParent(YEvasioneUdsVenditaForm); 
%>
<input class="<%=YEvasioneUdsVenditaDataDocumento.getClassType()%>" id="<%=YEvasioneUdsVenditaDataDocumento.getId()%>" maxlength="<%=YEvasioneUdsVenditaDataDocumento.getMaxLength()%>" name="<%=YEvasioneUdsVenditaDataDocumento.getName()%>" size="8" type="text"><% 
  YEvasioneUdsVenditaDataDocumento.write(out); 
%>

            </td>
            <td align="left" width="5">
              <% 
  WebTextInput YEvasioneUdsVenditaRCliente =  
     new com.thera.thermfw.web.WebTextInput("YEvasioneUdsVendita", "RCliente"); 
  YEvasioneUdsVenditaRCliente.setParent(YEvasioneUdsVenditaForm); 
%>
<input class="<%=YEvasioneUdsVenditaRCliente.getClassType()%>" id="<%=YEvasioneUdsVenditaRCliente.getId()%>" maxlength="<%=YEvasioneUdsVenditaRCliente.getMaxLength()%>" name="<%=YEvasioneUdsVenditaRCliente.getName()%>" size="8" type="text"><% 
  YEvasioneUdsVenditaRCliente.write(out); 
%>

            </td>
            <td align="left">
              <% 
  WebTextInput YEvasioneUdsVenditaRagioneSocialeCli =  
     new com.thera.thermfw.web.WebTextInput("YEvasioneUdsVendita", "RagioneSocialeCli"); 
  YEvasioneUdsVenditaRagioneSocialeCli.setParent(YEvasioneUdsVenditaForm); 
%>
<input class="<%=YEvasioneUdsVenditaRagioneSocialeCli.getClassType()%>" id="<%=YEvasioneUdsVenditaRagioneSocialeCli.getId()%>" maxlength="<%=YEvasioneUdsVenditaRagioneSocialeCli.getMaxLength()%>" name="<%=YEvasioneUdsVenditaRagioneSocialeCli.getName()%>" size="20" type="text"><% 
  YEvasioneUdsVenditaRagioneSocialeCli.write(out); 
%>

            </td>
            <td align="left" width="5">
              <% 
  WebTextInput YEvasioneUdsVenditaRCauDocTes =  
     new com.thera.thermfw.web.WebTextInput("YEvasioneUdsVendita", "RCauDocTes"); 
  YEvasioneUdsVenditaRCauDocTes.setParent(YEvasioneUdsVenditaForm); 
%>
<input class="<%=YEvasioneUdsVenditaRCauDocTes.getClassType()%>" id="<%=YEvasioneUdsVenditaRCauDocTes.getId()%>" maxlength="<%=YEvasioneUdsVenditaRCauDocTes.getMaxLength()%>" name="<%=YEvasioneUdsVenditaRCauDocTes.getName()%>" size="8" type="text"><% 
  YEvasioneUdsVenditaRCauDocTes.write(out); 
%>

            </td>
            <td align="left">
              <% 
  WebTextInput YEvasioneUdsVenditaCausaleDescrizione =  
     new com.thera.thermfw.web.WebTextInput("YEvasioneUdsVendita", "CausaleDescrizione"); 
  YEvasioneUdsVenditaCausaleDescrizione.setParent(YEvasioneUdsVenditaForm); 
%>
<input class="<%=YEvasioneUdsVenditaCausaleDescrizione.getClassType()%>" id="<%=YEvasioneUdsVenditaCausaleDescrizione.getId()%>" maxlength="<%=YEvasioneUdsVenditaCausaleDescrizione.getMaxLength()%>" name="<%=YEvasioneUdsVenditaCausaleDescrizione.getName()%>" size="20" type="text"><% 
  YEvasioneUdsVenditaCausaleDescrizione.write(out); 
%>

            </td>
          </tr>
        </table>
      </fieldset>
			  <%      YDatiSessioneEvasioneUdsVendita dev = (YDatiSessioneEvasioneUdsVendita)DocumentoDatiSessione.getDocumentoDatiSessione(se);      %>
    </td>
  </tr>
			<tr valign="top">
    <td height="80%">
    <%     String src_iframe = "it/softre/thip/vendite/uds/DettaglioGrigliaSingolaRigaEvasione.jsp" + "?" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" + dev.getChiaveDatiSessione();     %>
    <iframe height="100%" id="DettaglioGrigliaSingolaRigaEvasione" name="DettaglioGrigliaSingolaRigaEvasione" src="<%=src_iframe %>" width="100%"></iframe>
    </td>
  </tr>
			<tr valign="top">
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YEvasioneUdsVenditaForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
			<tr style="display: none">
				<td><input name="ChiusuraForm" type="hidden" value></td>
			</tr>
		</table>
	<%
  YEvasioneUdsVenditaForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YEvasioneUdsVenditaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YEvasioneUdsVenditaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YEvasioneUdsVenditaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YEvasioneUdsVenditaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YEvasioneUdsVenditaBODC.getErrorList().getErrors()); 
           if(YEvasioneUdsVenditaBODC.getConflict() != null) 
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
     if(YEvasioneUdsVenditaBODC != null && !YEvasioneUdsVenditaBODC.close(false)) 
        errors.addAll(0, YEvasioneUdsVenditaBODC.getErrorList().getErrors()); 
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
     String errorPage = YEvasioneUdsVenditaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YEvasioneUdsVenditaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YEvasioneUdsVenditaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
