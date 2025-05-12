<%@ page contentType="text/html; charset=Cp1252"%>
<%@ page import= "it.thera.thip.base.documenti.web.*"%>
<%@ page import= "it.thera.thip.vendite.ordineVE.*"%>
<%@ page import= "it.thera.thip.base.comuniVenAcq.web.*"%>
<%@ page import= "com.thera.thermfw.base.*"%>
<%@ page import= "com.thera.thermfw.common.*"%>
<%@ page import= "java.net.URLEncoder"%>
<%@ page import= "java.util.*"%>
<%@ page import= "com.thera.thermfw.web.servlet.BaseServlet"%>

<%@ page import= "it.thera.thip.vendite.ordineVE.web.*"%>
<%@ page import= "com.thera.thermfw.web.*"%>
<%@ page import= "com.thera.thermfw.persist.*"%>
<%@ page import= "com.thera.thermfw.collector.*"%>


<html>
<!-- HANDGEN Thip 1.1.6 -->
<!-- FIX 3294 - Migr.Therm 1.3.0 -->
<!-- FIX 5238 - EP - Gestione Warning Fido-->

<head>
<title>
     Dispatcher
</title>
</head>
<script>
/* var debug = false; */ // Fix 28387

function createWin(url, width, height) {
  var winName = "newWind" + Math.round(Math.random() * 1000000);
  var winFeature = "width="+width+", height="+height+", scrollbars=yes";
  winFeature += ", resizable=yes, toolbar=no, status=yes, menubar=no";
  if (navigator.appName=="Microsoft Internet Explorer") {
    winFeature += ", left=0, top=0";
  }
  else {
    winFeature += ", screenX=0, screenY=0";
  }
  return open(url, winName, winFeature);
}

function redirect() {
	//PJ BEGIN
	var pw = window.parent.progressWindow;
	if (pw != undefined)
		pw.close();
	//PJ END
		
    var pLocation = window.parent.location;
    var baseUrl = pLocation.protocol + "//" + pLocation.hostname + ":" + pLocation.port +
                  "<%=request.getContextPath()%>/";
    var thAction = "<%=com.thera.thermfw.web.servlet.BaseServlet.getStringParameter(request,"thAction")%>";
    var closeParent = "<%=BaseServlet.getStringParameter(request, "thCloseParent")%>";
    var newUrl = baseUrl + "<%=BaseServlet.getStringParameter(request, "thNewUrl")%>";
    var ritornoDaRiga = "<%=BaseServlet.getStringParameter(request, "ritornoDaRiga")%>";
    var thActionForm = "<%=BaseServlet.getStringParameter(request, "thActionForm")%>";
    var thClassName = "<%=BaseServlet.getStringParameter(request, "thClassName")%>";
    var thKey = "<%=BaseServlet.getStringParameter(request, "thKey")%>";
	// Inizio 3566
    var errBloccoEvasione = "<%=BaseServlet.getStringParameter(request, "ErrBloccoEvasione")%>";
    <%
    ErrorMessage erroreBloccoEvasione = (ErrorMessage) request.getAttribute("ErroreBloccoEvasione");
    String msgBlocco = null;
    if (erroreBloccoEvasione != null){
      msgBlocco = erroreBloccoEvasione.getLongText();
    }
    %>
  // Fine 3566
  
  //Fix 28387 Inizio
	<%
    String resFile = "it.thera.thip.vendite.ordineVE.resources.EvasioneOrdine";
    String messageClose = "Si vuole passare alla manutenzione del documento ?";
    try {
      messageClose = ResourceLoader.getString(resFile, "dispatcher.messageClose");
    }
    catch (Throwable t) {
      t.printStackTrace(Trace.excStream);
    }
    %>
    if (typeof closeParent != "undefined" && closeParent != "null") {
    	newUrl = baseUrl + closeParent;
	   }
	  <%
	  List warningOB = (List)request.getAttribute("Warning");
	  if (warningOB != null && !warningOB.isEmpty()) {
	  %>
	  if (thAction == "<%=EvasioneOrdiniConst.AZIONE_EVADI_DOCUMENTO%>") {
		  <%
		  String messageCloseWarning = "";
		  Iterator iter = warningOB.iterator();
		  while (iter.hasNext()) {
			  ErrorMessage errmsg = (ErrorMessage)iter.next();
			  messageCloseWarning = errmsg.getId() + " - " + errmsg.getLongText();
		  }
	
		  %>
		  closeWin = !alert("<%=messageCloseWarning%>");
		}
	   <%}%>
	      var msgBlocco="<%=msgBlocco%>";
	      var messageClose="<%=messageClose%>";
  
	        if ((window.parent == window) || (thAction!='CONFERMA_CHIUDI_GRIGLIA')) {
				  // Fix 32868 inizio
	        	  if(thAction=='CONFERMA_GUIDA') {
					createWin(newUrl, "1366", "768");
					window.parent.close();
				  }
				  else if(thAction=='CONDIZIONI_GRIGLIA') {
					createWin(newUrl, "800", "600");
					window.parent.close();
				  }
				  else				  
				// Fix 32868 fine
					window.parent.document.location.href = newUrl;
	        }
	        else{
	        	  parent.redirectRighe(msgBlocco,messageClose,pLocation,baseUrl,thAction,closeParent,newUrl,ritornoDaRiga,thActionForm,thClassName,thKey,errBloccoEvasione);
	        }
}

</script>

<body onLoad="javascript:redirect()">
</body>
</html>