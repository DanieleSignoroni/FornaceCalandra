<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///D:/3part/websrcThip3.0.6+Therm2.0/dtd/xhtml1-transitional.dtd">
<%@page import="it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm"%>
<%@page import="it.softre.thip.vendite.uds.YEvasioneUdsVenRiga"%>
<%@page import="it.softre.thip.vendite.uds.YEvasioneUdsVendita"%>
<%@page import="it.thera.thip.base.documenti.web.DocumentoDatiSessione"%>
<%@page import="it.softre.thip.vendite.uds.web.YDatiSessioneEvasioneUdsVendita"%>
<%@page import="it.thera.thip.base.generale.PersDatiGen"%>
<html>
<%=WebGenerator.writeRuntimeInfo()%>
<%
int idx = 0;
%>
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
  BODataCollector YEvasioneUdsVenRigaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YEvasioneUdsVenRigaForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YEvasioneUdsVenRigaForm", "YEvasioneUdsVenRiga", null, "it.thera.thip.vendite.ordineVE.servlet.GestoreEstrazioneEvasioneForm", false, false, false, false, true, true, null, 1, false, "it/thera/thip/vendite/ordineVE/EvasioneOrdini.js"); 
  YEvasioneUdsVenRigaForm.setServletEnvironment(se); 
  YEvasioneUdsVenRigaForm.setJSTypeList(jsList); 
  YEvasioneUdsVenRigaForm.setHeader(null); 
  YEvasioneUdsVenRigaForm.setFooter(null); 
  YEvasioneUdsVenRigaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YEvasioneUdsVenRigaForm.getMode(); 
  String key = YEvasioneUdsVenRigaForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  boolean conflitPresent = false; 
  String leftClass = ""; 
  List righeNonCompatibili = new ArrayList();  // Fix 14445
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        YEvasioneUdsVenRigaForm.outTraceInfo(getClass().getName()); 
        String collectorName = YEvasioneUdsVenRigaForm.findBODataCollectorName(); 
                YEvasioneUdsVenRigaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YEvasioneUdsVenRigaBODC instanceof WebDataCollector) 
            ((WebDataCollector)YEvasioneUdsVenRigaBODC).setServletEnvironment(se); 
        YEvasioneUdsVenRigaBODC.initialize("YEvasioneUdsVenRiga", true, 1); 
        YEvasioneUdsVenRigaForm.setBODataCollector(YEvasioneUdsVenRigaBODC); 
        int rcBODC = YEvasioneUdsVenRigaForm.initSecurityServices(); 
        mode = YEvasioneUdsVenRigaForm.getMode(); 

        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YEvasioneUdsVenRigaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YEvasioneUdsVenRigaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YEvasioneUdsVenRigaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

	<title>Griglia estrazione UDS vendita</title>
</head>
 <%@ page import= "it.thera.thip.vendite.ordineVE.*"%> 
 <%@ page import= "it.thera.thip.vendite.ordineVE.web.*"%> 
 <%@ page import= "it.thera.thip.base.documenti.web.*"%> 
 <%@ page import= "com.thera.thermfw.web.*"%> 
 <%@ page import= "it.thera.thip.base.comuniVenAcq.*"%> 
 <%@ page import= "it.thera.thip.base.documenti.*"%> 
 <%@ page import= "it.thera.thip.base.articolo.*"%> 
 <%@ page import= "java.util.*"%> 

<% 
  WebScript script_0 =  
   new com.thera.thermfw.web.WebScript(); 
 script_0.setRequest(request); 
 script_0.setSrcAttribute("it/thera/thip/vendite/ordineVE/EvasioneOrdini.js"); 
 script_0.setLanguageAttribute("JavaScript1.2"); 
  script_0.write(out); 
%>

<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YEvasioneUdsVenRigaForm.getBodyOnBeforeUnload()%>" onload="<%=YEvasioneUdsVenRigaForm.getBodyOnLoad()%>" onunload="<%=YEvasioneUdsVenRigaForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0" style="overflow: auto;" ><%
   YEvasioneUdsVenRigaForm.writeBodyStartElements(out); 
%> 

<% 
  WebLink link_0 =  
   new com.thera.thermfw.web.WebLink(); 
 link_0.setHttpServletRequest(request); 
 link_0.setHRefAttribute("it/thera/thip/vendite/ordineVE/EvasioneOrdini.css"); 
 link_0.setRelAttribute("STYLESHEET"); 
 link_0.setTypeAttribute("text/css"); 
  link_0.write(out); 
%>
 <script> var ewf = eval("parent." + eval("parent.errorsViewName")); </script> 
<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YEvasioneUdsVenRigaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YEvasioneUdsVenRigaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YEvasioneUdsVenRigaForm.getServlet()%>" method="post" name="DettaglioGrigliaSingolaRigaEvasioneForm" style="height:100%"><%
  YEvasioneUdsVenRigaForm.writeFormStartElements(out); 
%>

<table border="0" cellspacing="0" style="height:100%;vertical-align:top;" width="100%">
    <% YDatiSessioneEvasioneUdsVendita dev = (YDatiSessioneEvasioneUdsVendita)DocumentoDatiSessione.getDocumentoDatiSessione(se);
	YEvasioneUdsVendita docEvaVen = dev.getBo(); 
	List righe = new ArrayList(); 
	String jspToolbarPaginazioneReload = "/it/thera/thip/base/documenti/ToolbarPaginazioneReload.jsp"; 
	if (dev != null) {  
		docEvaVen.setChiaviSelezionate(dev.getChiaviSel());
		righe = docEvaVen.getRigheEstratte();
	} 
	int colonne = 12; 
	%>
	<% if (!righe.isEmpty()) { %>
  <tr valign="top">
    <td height="100%">
<table border="0" cellspacing="0" width="100%">
  <tr>
    <td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaSelezioneLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.Selezione", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaNumeroOrdineFormattatoLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.NumeroOrdineFormattato", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaCommessaLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.Commessa", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaSequenzaRigaOrdLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.SequenzaRigaOrd", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaIdArticoloLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.IdArticolo", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaDataConsegnaConfermataLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.DataConsegnaConfermata", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaQtaResInUMVenLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.QtaResInUMVen", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaQtaSpedireLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.QtaResInUMVen", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaIdUMVenLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.IdUMVen", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>
<td class="cssIntestazioneRighe"><label class="thLabel" id="grigliaColonnaSaldoRigaLabel">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.colonna.SaldoRiga", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label></td>

  </tr>
<%     
String prefixDettaglioDisp = "DettaglioDisp";
String strDettaglioDisp = "";
String prefixDettaglioRiga = "DettaglioRiga";
String strDettaglioRiga = "";
String prefixEliminaRiga = "EliminaRiga"; 
String strEliminaRiga = "";
String prefixManutenzioneRiga = "ManutenzioneRiga";
String strManutenzioneRiga = "";
int nLinea = 0; 
String cssTRrigaEstrazione = null;
Iterator myRighe = righe.iterator(); 
int totRighe = righe.size();
while (myRighe.hasNext()) {
	YEvasioneUdsVenRiga riga = (YEvasioneUdsVenRiga) myRighe.next();
	OrdineVenditaRigaPrm rigaOrd = riga.getRigaOrdine();
	cssTRrigaEstrazione = ((idx % 2) == 0) ? "cssRigaEstrazioneOrdine1" : "cssRigaEstrazioneOrdine2";
	strDettaglioRiga = prefixDettaglioRiga + idx;
	strDettaglioDisp = prefixDettaglioDisp + idx;
	strManutenzioneRiga = prefixManutenzioneRiga + idx;
	strEliminaRiga = prefixEliminaRiga + idx;
	String strDisabled = ""; 
	String strForzable = "";
	boolean isDisabled = !riga.isSelezionabile();
	boolean isForzabile = riga.isForzabile();
	if (isDisabled) {
		strDisabled = "disabled=\"\"";
		strForzable = "disabled=\"\"";
	}else {          
		if (!isForzabile) {
			strForzable = "disabled=\"\"";
		} 
	}
%>
  <tr class="<%=cssTRrigaEstrazione%>">
    <td colspan="<%=colonne%>" height="5"></td>
  </tr>
  <tr class="<%=cssTRrigaEstrazione%>">
  	<input type="hidden" name="IdArticolo<%=idx%>" value="<%=riga.getIdArticolo()%>"/> 
 	<input type="hidden" name="isSelezionabile<%=idx%>" value="<%=riga.isSelezionabile()%>"/>
 	<input type="hidden" name="isForzabile<%=idx%>" value="<%=riga.isForzabile()%>"/> 

    <td class="cellRigaEstratta" rowspan="2">
    	<input <%=strDisabled%> id="RigaEstratta<%=idx%>" name="RigaEstratta<%=idx%>" onclick="deselezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'RigaEstratta')" type="checkbox" <%=(riga.isRigaEstratta()?"checked":"")%>>
    </td>
    <td class="cellArticolo">
    <%
    String urlArticolo = "";
    if (urlArticolo != null) {
    	urlArticolo = request.getContextPath() + urlArticolo;
    }
    String labelArticolo = riga.getIdArticolo();
    %>
      <label onclick="javascript:dettaglioArticolo('<%=urlArticolo%>')">
        <%=labelArticolo%>
      </label>
    </td>
    <td class="cellMagazzino">
	<%
	if (riga.getIdMagazzino() == null) {  
	%>
    &nbsp;
	<%  
	}else if (!riga.isCambioMagazzinoAbilitato() ) {  
	%>
	<% 
		WebTextInputIndexed YEvasioneUdsVenRigaIdMagazzino =  
		   new com.thera.thermfw.web.WebTextInputIndexed(idx,"YEvasioneUdsVenRiga", "IdMagazzino"); 
		YEvasioneUdsVenRigaIdMagazzino.setOnChange("selezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'IdMagazzino', 'RigaEstratta');CambioMagEvasione(this,'IdMagazzino')"); 
		YEvasioneUdsVenRigaIdMagazzino.setParent(YEvasioneUdsVenRigaForm); 
		%>
		<input class="<%=YEvasioneUdsVenRigaIdMagazzino.getClassType()%>" disabled id="<%=YEvasioneUdsVenRigaIdMagazzino.getId()%>" maxlength="<%=YEvasioneUdsVenRigaIdMagazzino.getMaxLength()%>" name="<%=YEvasioneUdsVenRigaIdMagazzino.getName()%>" readonly size="2" style="width:100%;background-color:#E8E8E8" type="text" value="Magazzino1"><% 
		YEvasioneUdsVenRigaIdMagazzino.write(out); 
		%>

	<%
	}else { 
	%>
       <input type="text" size="2" id="Magazzino1" name="Magazzino1" value="Magazzino1" disabled="disabled"/>
	  <% 
	  WebTextInputIndexed YEvasioneUdsVenRigaIdMagazzino =  
	     new com.thera.thermfw.web.WebTextInputIndexed(idx,"YEvasioneUdsVenRiga", "IdMagazzino"); 
	  YEvasioneUdsVenRigaIdMagazzino.setOnChange("selezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'IdMagazzino', 'RigaEstratta');CambioMagEvasione(this,'IdMagazzino')"); 
	  YEvasioneUdsVenRigaIdMagazzino.setParent(YEvasioneUdsVenRigaForm); 
	  %>
	  <input type="text" size="2" value="Magazzino1" id="<%=YEvasioneUdsVenRigaIdMagazzino.getId()%>" name="<%=YEvasioneUdsVenRigaIdMagazzino.getName()%>" maxlength="<%=YEvasioneUdsVenRigaIdMagazzino.getMaxLength()%>" class="<%=YEvasioneUdsVenRigaIdMagazzino.getClassType()%>" style="width:100%" ><% 
	  YEvasioneUdsVenRigaIdMagazzino.write(out); 
	  %>
	<%
	}
	%>
    </td>
    <td class="cellDataConsegna">
      <% 
  	WebTextInputIndexed YEvasioneUdsVenRigaDataConsegnaConfermataRO =  
	     new com.thera.thermfw.web.WebTextInputIndexed(idx, "YEvasioneUdsVenRiga", "DataConsegnaConfermata"); 
	  YEvasioneUdsVenRigaDataConsegnaConfermataRO.setParent(YEvasioneUdsVenRigaForm); 
	%>
	<input class="<%=YEvasioneUdsVenRigaDataConsegnaConfermataRO.getClassType()%>" disabled id="<%=YEvasioneUdsVenRigaDataConsegnaConfermataRO.getId()%>" maxlength="<%=YEvasioneUdsVenRigaDataConsegnaConfermataRO.getMaxLength()%>" name="<%=YEvasioneUdsVenRigaDataConsegnaConfermataRO.getName()%>" size="8" type="text" style="width:100%"><% 
 	 YEvasioneUdsVenRigaDataConsegnaConfermataRO.write(out); 
	%>
    </td>
 	<% 
 	String UMV = riga.getIdUMRif(); 
	%>
		<td colspan="4" rowspan="2" valign="top">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr class="<%=cssTRrigaEstrazione%>">
          <td class="cellIdUM">
            <input disabled id="UMVendita<%=idx%>" name="UMVendita<%=idx%>" size="3" type="text" value="<%=riga.getIdUMRif()%> " style="width:100%">
          </td>
          <td class="cellResiduo">
            <input disabled id="ResiduoV<%=idx%>" name="ResiduoV<%=idx%>" size="8" type="text" value="<%=riga.getQtaResiduaInUMRif()%>" style="width:100%">
          </td>
          <td class="cellDaSpedire">
            <% 
  WebTextInputIndexed YEvasioneUdsVenRigaQtaDaSpedireInUMRif =  
     new com.thera.thermfw.web.WebTextInputIndexed(idx, "YEvasioneUdsVenRiga", "QtaDaSpedireInUMRif"); 
  YEvasioneUdsVenRigaQtaDaSpedireInUMRif.setOnChange("selezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'QtaDaSpedireInUMRif', 'RigaEstratta');variazioneQta(this,'QtaDaSpedireInUMRif','RIF')"); 
  YEvasioneUdsVenRigaQtaDaSpedireInUMRif.setParent(YEvasioneUdsVenRigaForm); 
%>
<input style="width:100%" <%=strDisabled%> class="<%=YEvasioneUdsVenRigaQtaDaSpedireInUMRif.getClassType()%>" id="<%=YEvasioneUdsVenRigaQtaDaSpedireInUMRif.getId()%>" maxlength="<%=YEvasioneUdsVenRigaQtaDaSpedireInUMRif.getMaxLength()%>" name="<%=YEvasioneUdsVenRigaQtaDaSpedireInUMRif.getName()%>" size="8" type="text"><% 
  YEvasioneUdsVenRigaQtaDaSpedireInUMRif.write(out); 
%>
    <td class="cellRigaSaldata" rowspan="2">
      <%       if (riga.isSaldoAutomatico()) {       %>
      <input disabled id="RigaSaldata<%=idx%>" name="RigaSaldata<%=idx%>" type="checkbox" <%=(riga.isRigaSaldata()?"checked":"")%>>
      <%       }       else {       %>
      <input <%=strDisabled%> id="RigaSaldata<%=idx%>" name="RigaSaldata<%=idx%>" onclick="selezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'RigaSaldata', 'RigaEstratta')" type="checkbox" <%=(riga.isRigaSaldata()?"checked":"")%>>
      <%       }       %>
    </td>
    <td class="cellRifRigaOrdine">
    <%
    if (rigaOrd != null) {       
    	String urlRigaOrdine = "";
    	if (urlRigaOrdine != null) {
    		urlRigaOrdine = request.getContextPath() + urlRigaOrdine;
    	}         %>
        <input style="width:100%" id="RifRigaOrdine<%=idx%>" name="RifRigaOrdine<%=idx%>" onclick="javascript:dettaglioRigaOrdine('<%=urlRigaOrdine%>')" readonly size="20" type="text" value="<%=riga.getRifRigaOrdineFormattato()%>">
        <label style="text-decoration: underline; color: Blue; cursor: hand;" onclick="javascript:dettaglioRigaOrdine('<%=urlRigaOrdine%>')">           <input size="20" type="text" id="RifRigaOrdine<%=idx%>" name="RifRigaOrdine<%=idx%>" value="<%=riga.getRifRigaOrdineFormattato()%>" disabled=""/>         </label>
      <%     } %>
    </td>
    <td class="cellRigaForzata" rowspan="2">
      <input <%=strForzable%> id="RigaForzata<%=idx%>" name="RigaForzata<%=idx%>" onclick="selezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'RigaForzata', 'RigaEstratta')" type="checkbox" <%=(riga.isRigaForzata()?"checked":"")%>>
    </td>
  </tr>
  <tr class="<%=cssTRrigaEstrazione%>">
    <% String cellDescrArticoloClass = "cellDescrArticolo";%>
    <td class="<%=cellDescrArticoloClass%>" colspan="3">
      <% 
  WebTextInputIndexed YEvasioneUdsVenRigaDescrizioneArticolo =  
     new com.thera.thermfw.web.WebTextInputIndexed(idx, "YEvasioneUdsVenRiga", "DescrizioneArticolo"); 
  YEvasioneUdsVenRigaDescrizioneArticolo.setOnChange("selezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'DescrizioneArticolo', 'RigaEstratta')"); 
  YEvasioneUdsVenRigaDescrizioneArticolo.setParent(YEvasioneUdsVenRigaForm); 
%>
<input style="width:100%" <%=strDisabled%> class="<%=YEvasioneUdsVenRigaDescrizioneArticolo.getClassType()%>" id="<%=YEvasioneUdsVenRigaDescrizioneArticolo.getId()%>" maxlength="<%=YEvasioneUdsVenRigaDescrizioneArticolo.getMaxLength()%>" name="<%=YEvasioneUdsVenRigaDescrizioneArticolo.getName()%>" onclick="selezionaRiga(this, 'document.DettaglioGrigliaSingolaRigaEvasioneForm', 'DescrizioneArticolo', 'RigaEstratta')" size="35" type="text" value='<%=WebElement.formatStringForHTML(riga.getDescrizioneArticolo())%>'><% 
  YEvasioneUdsVenRigaDescrizioneArticolo.write(out); 
%>
    </td>
  </tr>


  <tr class="<%=cssTRrigaEstrazione%>">
    <td height="5">
      <input id="StatoAvanzamento1" name="StatoAvanzamento1" size="3" type="hidden">
    </td>
    <td colspan="<%=(colonne - 1)%>" height="0">
    </td>
  </tr>
  <%   idx++;   %> 
<%   } %> 
</table>
</td>
</tr>
 <% } else { %> 
  <tr valign="top">
    <td class="cellMessage">
      <label class="thLabel" id="grigliaMessage0Label">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.softre.thip.vendite.uds.resources.YEvasioneUdsVendita", "griglia.Message0", null, null, null, null); 
 label.setParent(YEvasioneUdsVenRigaForm); 
label.write(out); }%> 
</label>
    </td>
  </tr>

 <% } %>

<tr style="display:none" valign="top">
  <td height="100%">
	 <iframe frameborder="0" height="0" id="RicalcoloQtaFrame" name="RicalcoloQtaFrame" src style="visibility:hidden;" width="0">
	   </iframe>
  </td>
</tr>
<tr style="display:none" valign="top">
  <td>
    <input name="RigaInDettaglio" type="hidden" value>
    <input name="ForzaStatoAvanzamento" type="hidden" value>
    <input name="ChiusuraForm" type="hidden" value>
    <input name="TimestampDoc" type="hidden" value>
  </td>
</tr>
<tr style="display:none" valign="top">
  <td>
    <% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YEvasioneUdsVenRigaForm); 
  errorList.write(out); 
%>
<span class="errorlist" id="errorList"></span>
  </td>
</tr>
</table>

 <table border="0"><tr><td> 

<%
  YEvasioneUdsVenRigaForm.writeFormEndElements(out); 

  
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YEvasioneUdsVenRigaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YEvasioneUdsVenRigaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<jsp:include flush="true" page="<%=jspToolbarPaginazioneReload%>"></jsp:include>
<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YEvasioneUdsVenRigaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YEvasioneUdsVenRigaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YEvasioneUdsVenRigaBODC.getErrorList().getErrors()); 
           if(YEvasioneUdsVenRigaBODC.getConflict() != null) 
                conflitPresent = true; 
  }else 
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
     if(YEvasioneUdsVenRigaBODC != null && !YEvasioneUdsVenRigaBODC.close(false)) 
        errors.addAll(0, YEvasioneUdsVenRigaBODC.getErrorList().getErrors()); 
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
     String errorPage = YEvasioneUdsVenRigaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YEvasioneUdsVenRigaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YEvasioneUdsVenRigaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
