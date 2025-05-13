var oldCloseForm = closeForm;

closeForm = function () {
  var res = runActionDirect("CLOSE_FORM", "", "", "", "", "");
  close();
}

function removeComponentFromError(s) {
}

function runActionDirect(action, type, classhdr, key, target, toolbar, form)
{

  var currentForm = otherAction(action, form); // fix 12686
  if (typeof currentForm == "undefined") {
  	return false;
  }

  if(action == "")
    return false;

  if((action == "SAVE_SCREEN_DATA" || action == "OPEN_SCREEN_DATA") && !window.mdvPresent)
  {
    alert(mdvMsg);
      return;
  }

  // get the servlet name from the form action
  servlet = currentForm.action;

  // manage target
  if(target == "new")
  {
    if (currentForm.thTarget) { // 12639
    	currentForm.thTarget.value="new";
    }	
    createTargetWin("yes",toolbar,"800","600");
  }
  else if(target == "" || target == "same")
  {
	 if (currentForm.thTarget) { // 12639
		 currentForm.thTarget.value="";
	 }
     currentForm.target="";
  }
  else if(target == "errorsFrame")
  {
   // Inizio 3673
   if (noConcurrentActions && isActionsDisabled()){
     return;
   }
   // Fine 3673
     var errorsView = eval(errorsViewName);
     if(errorsView)
     {
       errorsView.clearDisplay();
       errorsView.setMessage(operMsg);
     }
	 if (currentForm.thTarget) { // 12639
		 currentForm.thTarget.value=target;
	 }
     currentForm.target= errorsFrameName;
  }
  else
  {
     if (currentForm.thTarget) { // 12639
		currentForm.thTarget.value=target;
     }	
     currentForm.target=target;
  }

  if(action == "REFRESH")// || action == "CHECK_ALL")
    window.conflictClosed = true;
  
  if (currentForm.thAction) { // 12686
	  currentForm.thAction.value = action;
  }

  // Teststa-riga
  // Fix 1464
  var undefined;
  if (editGridName!=undefined){
	  if(editGridName.length>0)
	    window.editGridSubmit();
  }
  // Fine fix 1464

  // Doppia Lista
  // ini FIX 1684
  if(arrCD != undefined && arrCD.length)
    dlsubmit();
  // fine FIX 1684
  // exec
  if(type == "action_submit")
     currentForm.submit();
  else{
    //window.location = servlet+'?Classhdr='+classhdr+'&Key='+key+'&Action='+action;//12574
    setLocationOnWindow(window, servlet+'?Classhdr='+classhdr+'&Key='+key+'&Action='+action);//12574
  }
  // Fix 19167 inizio
  if(isIE) {
	  if(window.parent.opener != undefined && 
			  window.parent.opener.top.frames.item(0) != undefined && 
			  window.parent.opener.top.frames.item(0).document.forms[0].thClassName != undefined) {
		  
		  if(action == "CLOSE") {
			  for(var ind = 0; ind < window.parent.opener.top.frames.item(1).editAjaxGridId.length; ind++) {
				  window.parent.opener.top.frames.item(1).refreshTestataRowsRequest(window.parent.opener.top.frames.item(1).editAjaxGridId[ind]);
			  }
		  }

	  }
  }
  else  {
	  if(window.parent.opener != undefined && 
			  window.parent.opener.top.frames[0] != undefined && 
			  window.parent.opener.top.frames[0].thClassName != undefined) {
		  if(action == "CLOSE") {
			  for(var ind = 0; ind < window.parent.opener.top.frames[1].editAjaxGridId.length; ind++) {
				  window.parent.opener.top.frames[1].refreshTestataRowsRequest(window.parent.opener.top.frames[1].editAjaxGridId[ind]);
			  }
		  }

	  }
  }
  // Fix 19167 fine
}

/**
* In base alla action esegue e restituisce un form o null se deve finire l'azione
*/
function otherAction(action, form) { // fix 12686
  var undefined;
  var currentForm = document.forms[0];
  if (typeof form != "undefined") {
    	currentForm = eval(form);
  }
  else {
        if (action == "MANUTENZIONE_RIGA" ||
        	action == "DETTAGLIO_RIGA") {

        }
	if (action == "CONFERMA_GUIDA") {
		apriProgressWindow();
	}
  	if (action == "CONFERMA_GRIGLIA" ||
 		action == "CONFERMA_CHIUDI_GRIGLIA" ||
        	action == "EVADI_DOCUMENTO" ||
		action == "AGGIORNA_GRIGLIA" ||
		action == "CAMBIA_STATO") {
		apriProgressWindow();
  		//form = 'window.DettaglioGrigliaSingolaRigaEvasione.DettaglioGrigliaSingolaRigaEvasioneForm';
			form = document.getElementById('DettaglioGrigliaSingolaRigaEvasione').contentWindow.document.forms[0];
  	}
  	if (action == "SELEZIONA_TUTTO"){
  		//selezionaTutto('window.DettaglioGrigliaSingolaRigaEvasione.DettaglioGrigliaSingolaRigaEvasioneForm', 'RigaEstratta')//12091
			selezionaTutto(document.getElementById('DettaglioGrigliaSingolaRigaEvasione').contentWindow.document.forms[0], 'RigaEstratta');//12091
  		currentForm = undefined;
  	}
  	else if (action == "SALDO_AUTOMATICO"){
  		//saldoAutomatico('window.DettaglioGrigliaSingolaRigaEvasione.DettaglioGrigliaSingolaRigaEvasioneForm', 'RigaEstratta', 'RigaSaldata')//12091
			saldoAutomatico(document.getElementById('DettaglioGrigliaSingolaRigaEvasione').contentWindow.document.forms[0], 'RigaEstratta', 'RigaSaldata');//12091
  		currentForm = undefined;
  	}
	else if (typeof form != "undefined") {
    	//currentForm = eval(form);//12091
			currentForm = form;//12091
		if (action == "CAMBIA_STATO") {
			//var selObj = eval(form + ".ForzaStatoAvanzamento");//12091
			var selObj = form.ForzaStatoAvanzamento;//12091
    		selObj.value = "true";
		}
	}
  }
  if (action == "CLOSE_FORM") {
    // ini FIX 1684
    if (typeof currentForm != "undefined") {
    	var obj = currentForm.ChiusuraForm;
    	if (typeof obj != "undefined") {
          obj.value = "true";
        }
    }
    // fine FIX 1684
    oldCloseForm();
    window.opener.focus();
    currentForm = undefined;
  }
  return currentForm;
}

/*********/

function positionWindow(x, y, w, h, type) {
    if (typeof type == "undefined") {
        type = "N";
    }
    if (type == "C") {
        centerWindow(w, h);
    }
    else {
        resizeTo(w, h);
    }
}

function centerWindow(w, h) {
  resizeTo(w, h);
  var winl = (screen.width-w)/2;
  var wint = (screen.height-h)/2;
  moveTo(winl, wint);
  if(parseInt(navigator.appVersion) >= 4){window.focus();}
}

function saldoAutomatico(form, rigaSel, rigaSaldo) {
	var theForm = form;//eval(form);12091
	var totRighe = totaleCheckbox(form, rigaSel);
	for (i = 0; i < totRighe; i++) {
		//12091 var rigaSelObj = eval(form + "." + rigaSel + i);
		var rigaSelObj = form.ownerDocument.getElementById(rigaSel+ i);//12091
		if (rigaSelObj.checked) {
			//12091 var obj = eval(form + "." + rigaSaldo + i);
			var obj = form.ownerDocument.getElementById(rigaSaldo+ i);//12091
			//if (typeof obj != "undefined") {//12574
      if (obj != null) {//12574
				obj.checked = true;
			}
		}
	}
}

function selezionaTutto(form, rigaSel) {
	selezionaCheckbox(form, rigaSel);
}

function totaleCheckbox(form, name) {
	var tot = 0;
	var theForm = form;//12091 eval(form);
	for (i = 0; i < theForm.elements.length; i++) {
		if (theForm.elements[i].type == "checkbox") {
			var cname = theForm.elements[i].name;
			var cnameS = cname.substring(0, name.length);
			if ((name) == cnameS) {
				tot++;
			}
		}
	}
	return tot;
}

function selezionaRiga(obj, form, prefix, name) {
	var s = name + obj.name.substring(prefix.length, obj.name.length);
        var formVera = eval(form); //Fix 12091
	//12091 var selObj = eval(form + "." + s);
	var selObj = formVera.ownerDocument.getElementById(s);//Fix 1209
	selObj.checked = true;
}

function dettaglioRiga(obj, form, prefix, name) {
    var theForm = eval(form);
    var s = obj.name.substring(prefix.length, obj.name.length);
	  selezionaRiga(obj, form, prefix, name);
    var selObj = eval(form + ".RigaInDettaglio");
		selObj.value = s;
    //var target = window.parent.name;
    var target = "new";
	var res = runActionDirect("DETTAGLIO_RIGA", "action_submit", "DocEvaVen", "", target, "no")
    return res;
}

function manutenzioneRiga(obj, form, prefix, name) {
    var theForm = eval(form);
    var s = obj.name.substring(prefix.length, obj.name.length);
    selezionaRiga(obj, form, prefix, name);
    var selObj = eval(form + ".RigaInDettaglio");
    selObj.value = s;
    //var target = "errorsFrame";
	var target = window.parent.errorsFrameName;

    //var target = "new";

    // Disabilito tutti i bottoni di Manutenzione della form
/*
    enableFields(form,'QtaDaSpedireInUMRif',false);
    enableFields(form,'RigaSaldata',false),
    enableFields(form,'StatoAvanzamentoDef',false);
    enableFields(form,'RigaForzata',false);
    enableFields(form,'EliminaRiga',false);
    enableFields(form,'DescrizioneArticolo',false);
    enableFields(form,'ManutenzioneRiga',false);
*/
    var res = runActionDirect("MANUTENZIONE_RIGA", "action_submit", "DocEvaVen", "", target, "no")
    return res;
}

function eliminaRiga(obj, form, prefix, name) {
    var theForm = eval(form);
    var s = obj.name.substring(prefix.length, obj.name.length);
	selezionaRiga(obj, form, prefix, name);
    var selObj = eval(form + ".RigaInDettaglio");
    selObj.value = s;
    var target = window.parent.name;
    var res = runActionDirect("ELIMINA_RIGA", "action_submit", "DocEvaVen", "", target, "no")
    return res;
}

function dettaglioRigaOrdine(url) {
	var win = createTargetWin("yes", "", "800", "600");
  //Fix 12574 inizio
  /*
	var pLocation = win.opener.location;
  var baseUrl = pLocation.protocol + "//" + pLocation.hostname + ":" + pLocation.port;
  var newUrl = baseUrl + url;
  win.location = newUrl;
  */
  setLocationOnWindow(win, url);
  //Fix 12574 fine
	win.focus();
}

function dettaglioArticolo(url) {
	var win = createTargetWin("yes", "", "800", "600");
  //Fix 12574 inizio
  /*
	var pLocation = win.opener.location;
  var baseUrl = pLocation.protocol + "//" + pLocation.hostname + ":" + pLocation.port;
  var newUrl = baseUrl + url;
  win.location = newUrl;
  */
  setLocationOnWindow(win, url);
  //Fix 12574 fine
	win.focus();
}

function dettaglioLogRighe(url) {
	var win = createTargetWin("yes", "", "800", "400");
  //Fix 12574 inizio
  /*
	var pLocation = win.opener.location;
  var baseUrl = pLocation.protocol + "//" + pLocation.hostname + ":" + pLocation.port;
  var newUrl = baseUrl + url;
  win.location = newUrl;
  */
  setLocationOnWindow(win, url);
  //Fix 12574 fine
	win.focus();
}

function dettaglioSaldoMagArticolo(url) {
	var win = createTargetWin("yes", "", "800", "600");
  //Fix 12574 inizio
  /*
	var pLocation = win.opener.location;
  var baseUrl = pLocation.protocol + "//" + pLocation.hostname + ":" + pLocation.port;
  var newUrl = baseUrl + url;
  win.location = newUrl;
  */
  setLocationOnWindow(win, url);
  //Fix 12574 fine
	win.focus();
}

function dettaglioSaldoMagArticoloKit(name, form, prefix, isKit) {
    dettaglioDispMagArticolo(name, form, prefix, isKit);
}

function dettaglioDispMagArticolo(name, form, prefix, isKit) {
    var theForm = form;//eval(form);
    var s = name.substring(prefix.length, name.length);
    var selObj = eval(form + ".RigaInDettaglio");
    selObj.value = s;
    // ini Fix 1684
    var target = "new";
    /*
    if (isKit && isKit == "true") {
      target = "new";
    }
    */
    // fine Fix 1684
    var res = runActionDirect("DETTAGLIO_DISPONIBILITA", "action_submit", "DocEvaVen", "", target, "no")
    return res;
}

function confermaGuida(form) {
	var theForm = eval(form);
    var target = "errorsFrame";
    var res = runActionDirect("CONFERMA_GUIDA", "action_submit", "DocEvaVen", "", target, "no")
    return res;
}

function confermaGriglia(form) {
	var theForm = eval(form);
    var target = "errorsFrame";
    var res = runActionDirect("CONFERMA_GRIGLIA", "action_submit", "DocEvaVen", "", target, "no", form);
    return res;
}

function confermaChiudiGriglia(form) {
	var theForm = eval(form);
    var target = "errorsFrame";
    var res = runActionDirect("CONFERMA_CHIUDI_GRIGLIA", "action_submit", "DocEvaVen", "", target, "no", form);
    return res;
}

function evadiDocumento(form) {
	var theForm = eval(form);
    var target = "errorsFrame";
    var res = runActionDirect("EVADI_DOCUMENTO", "action_submit", "DocEvaVen", "", target, "no", form);
    return res;
}

function confermaRigaPrm(form) {
	var theForm = eval(form);
	var target = "errorsFrame";
    var res = runActionDirect("CONFERMA_RIGA_PRM", "action_submit", "DocEvaVenRigaPrm", "", target, "no")
    return res;
}

function annullaRigaPrm() {
    closeForm();
    window.opener.focus();
}

function confermaRigaSec() {
    confirmEditXPanel('CONFIRM_AND_CLOSE');
}

function annullaRigaSec() {
    closeForm();
    window.opener.focus();
}

function aggiornaGriglia() {
    var target = "errorsFrame";
    var res = runActionDirect("AGGIORNA_GRIGLIA", "action_submit", "DocEvaVen", "", target, "no")
    return res;
}

function ricaricaGriglia(form) {
	var theForm = eval(form);
    var target = "errorsFrame";
    var res = runActionDirect("RICARICA_GRIGLIA", "action_submit", "DocEvaVen", "", target, "no", form)
    return res;
}

function forzaCambiaStato(form) {
	var theForm = eval(form);
    var selObj = eval(form + ".ForzaStatoAvanzamento");
    selObj.value = "true";
    var target = "errorsFrame";
    var res = runActionDirect("CAMBIA_STATO", "action_submit", "DocEvaVen", "", target, "no", form)
    return res;
}

function annullaGriglia(form) {
	var theForm = eval(form);
    var res = runActionDirect("ANNULLA_GRIGLIA", "action_submit", "DocEvaVen", "", "same", "no")
	return res;
}

function condizioniGriglia(form) {
	var theForm = eval(form);
    var res = runActionDirect("CONDIZIONI_GRIGLIA", "action_submit", "DocEvaVen", "", "same", "no")
	return res;
}

function deselezionaRiga(obj, form, prefix, arrayObj) {
/* Fix 1510 GSCARTA CORREGGERE da togliere
	if (obj.checked) {
		return;
	}
	var sI = obj.name.substring(prefix.length, obj.name.length);
	if (typeof arrayObj == "undefined") {
		var formObj = eval(form);
		arrayObj = new Array(formObj.length)
		for (k = 0; k < formObj.length; k++) {
			if (formObj.elements[k].type == "checkbox" || formObj.elements[k].type == "text") {
				var name = formObj.elements[k].name;
				var sname = name.substring(0, name.length - sI.length);
				arrayObj[k] = sname;
			}
		}
	}
Fine fix 1510 */
    /* se voglio cancellare i valori ...
	for (i = 0; i < arrayObj.length; i++) {
		var name = arrayObj[i];
		var s = name + obj.name.substring(prefix.length, obj.name.length);
		var selObj = eval(form + "." + s);
		if (typeof selObj != "undefined") {
			if (selObj.type == "checkbox") {
				selObj.checked = false;
			}
			else if (selObj.type == "text") {
				selObj.value = "";
			}
		}
	}
    */
}


//fixed 3741 (PJ)
function selezionaCheckbox(form, name) {

	for (var i = 0; true; i++) {
		//12094 var obj = eval(form + "." + name + i);
		var obj = form.ownerDocument.getElementById(name + i);//12091
		if (obj == undefined)
			break;
		obj.checked = true;
	}

}


//#######################################################################################
// Mario Nicodemo
//#######################################################################################

var oldRigaForzataDisable;
// MN 29-05-03: Disabilita i componenti della riga selezionata.
function disableFields(obj,form,prefix,name) {
	var s = name + obj.name.substring(prefix.length, obj.name.length);
//	alert('s '+s)
	//12091 var selObj = eval(form + "." + s);
	var selObj = form.ownerDocument.getElementById(name + s);//12091
	if (selObj.type == 'button'){
		selObj.style.visibility = "hidden";
	}
	else if (selObj.type == 'checkbox'){
		if (name=='RigaForzata'){
			oldRigaForzataDisable = selObj.disabled;
		}
		selObj.disabled = true;
	}
	else{
		//selObj.disabled = true;
		selObj.readOnly = -1;
		selObj.style.background = bCo;//12091 document.forms[0].style.background;
	}
}


// Abilita/Disabilita i campi della form.
function enableFields(form,name,enable){
	var theForm = eval(form);
	for (i = 0; i < theForm.elements.length; i++) {
//		alert('Tipo '+theForm.elements[i].type+' nome '+theForm.elements[i].name);
		var cname = theForm.elements[i].name;
		var cnameS = cname.substring(0, name.length);

		if (theForm.elements[i].type == "button") {
			if ( ((name) == cnameS) && enable ) {
					theForm.elements[i].style.visibility = "visible";
			}
			else if (((name) == cnameS) && !enable ){
					theForm.elements[i].style.visibility = "hidden";
			}
		}
		if (theForm.elements[i].type == "checkbox") {
			if (((name) == cnameS) && enable && name=='RigaForzata'){
//				alert('Campo '+theForm.elements[i].name+' Setto RigaForzata '+oldRigaForzataDisable)
					theForm.elements[i].disabled = oldRigaForzataDisable;
			}
			else if (((name) == cnameS) && !enable && name=='RigaForzata'){
//				alert('Campo '+theForm.elements[i].name+' Setto RigaForzata '+oldRigaForzataDisable)
					oldRigaForzataDisable = theForm.elements[i].disabled;
			}
			else if ( ((name) == cnameS) && enable ) {
				theForm.elements[i].disabled = false;
			}
			else if (((name) == cnameS) && !enable ){
					theForm.elements[i].disabled = true;
			}
		}
		if (theForm.elements[i].type == "text") {
			if ( ((name) == cnameS) && enable ) {
					//theForm.elements[i].disabled = false;
//					alert('Campo '+theForm.elements[i].name+' readOnly '+theForm.elements[i].readOnly);
					theForm.elements[i].readOnly = 0;
					theForm.elements[i].style.background = sCo
			}
			else if (((name) == cnameS) && !enable ){
					theForm.elements[i].readOnly = -1;
					theForm.elements[i].style.background = bCo;//12091 document.forms[0].style.background;

			}
		}
	}
}

//fix 3741 begin (PJJ)

function apriProgressWindow() {
  var url = "/" + webAppPath + "/it/thera/thip/base/comuniVenAcq/ProgressWindow.jsp";
  progressWindow = null;
  progressWindow = buildProgressWindow();
  //progressWindow.location = url;//Fix 12574
  setLocationOnWindow(progressWindow, url);//Fix 12574
}

function buildProgressWindow() {
  winName = "newWind" + Math.round(Math.random() * 1000000);
  winFeature = "width=150, height=150, scrollbars=no, toolbar=no, status=no, menubar=no";
  var leftPosition = window.screen.width/2 - 70;
  var topPosition = window.screen.height/2 - 80;
  if(navigator.appName=="Microsoft Internet Explorer")
    winFeature += ", left="+leftPosition+", top="+topPosition;
  else
    winFeature += ", screenX="+leftPosition+", screenY="+topPosition;
  return window.open("", winName, winFeature);
}

//fix 3741 end


// Inizio 3942
function variazioneQta(obj, elem, tipoUM){
  var undefined;
  form = 'document.DettaglioGrigliaSingolaRigaEvasioneForm';
  var s = elem + obj.name.substring(elem.length, obj.name.length);
  
  var idx = obj.name.substring(elem.length, obj.name.length);
  var qtaSelectedField = eval(form + "." + s);

  //fix 4090
  if (qtaSelectedField.value == "")
  	return;

  var qtaCambiata = qtaSelectedField.typeNameJS.format(qtaSelectedField.value);
  var idArticolo = eval(form + ".IdArticolo"+idx );
  var idMagazzino = eval(form + ".IdMagazzino"+idx );  //33903  
  var idVersione = eval(form + ".IdVersione"+idx ); // fix 10955
  var ricalcoloQta = eval(form + ".Ricalcola"+idx );
  //if (ricalcoloQta == undefined || !ricalcoloQta.checked)  //33903  
    //return; //33903  
	

  var UMRif = eval(form + ".UMVendita"+idx );
  var UMMagPrm = eval(form + ".UMMagazzino"+idx );
  var UMMagSec = eval(form + ".UMMagazzinoSec"+idx );

  var idUMRif = UMRif.value;
  var idUMMagPrm = '';
  var idUMMagSec = '';
  if (UMMagPrm != undefined)
    idUMMagPrm = UMMagPrm.value;
  if (UMMagSec != undefined)
    idUMMagSec = UMMagSec.value;

  if (ricalcoloQta == undefined || !ricalcoloQta.checked) //33903  
	ricalcoloDisponibilita(idMagazzino.value, idArticolo.value, idVersione.value, qtaCambiata, idUMRif, idUMMagPrm,idUMMagSec, tipoUM,idx); //33903  
  else 
	ricalcoloQuantita(idArticolo.value, idVersione.value, qtaCambiata, idUMRif, idUMMagPrm,idUMMagSec, tipoUM, idx);
}


function setQtaToField(field, value){
  // Inizio 5063
  //field.value = field.typeNameJS.format(value);
  // fix 11840 >
  if (typeof field != "undefined") {
	field.value = field.typeNameJS.unformat(value);
  }
  // fix 11840 <
  // Fine 5063
}

// fix 12825 >
function recuperaRigaEstrattaField(idxRiga) {
   var form = 'document.DettaglioGrigliaSingolaRigaEvasioneForm';
   var field = eval(form + ".RigaEstratta"+idxRiga );
   return field;
}
// fix 12825 <

// Fine 3942

//Fix 26324 inizio
function CambioMagEvasione(obj, elem){
	var form = 'document.DettaglioGrigliaSingolaRigaEvasioneForm';
	var idxRiga = obj.name.substring(elem.length, obj.name.length);
	var nuovoMagazzino = eval(form + ".IdMagazzino"+idxRiga ).value;
	var className =  eval("document.forms[0].thClassName").value;
	var chiaveDatiSessione =  eval("document.forms[0].thChiaveDatiSessione").value; 

	var url = "/" + webAppPath  + "/" + servletPath + "/it.thera.thip.base.comuniVenAcq.web.CambioMagEvasione?";
	var params = "IdxRiga=" + idxRiga + "&IdNuovoMag=" + nuovoMagazzino + "&thClassName=" + className + "&thChiaveDatiSessione=" + chiaveDatiSessione;

	setLocationOnWindow(document.getElementById("RicalcoloQtaFrame").contentWindow, url + params);
}
//Fix 26324 fine