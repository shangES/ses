/*****************************************************************

	grid 1.0
	Copyright (C) 2005-2009 Sigma Soft Ltd. All Rights Reserved. 
	http://www.sigmawidgets.com/

	WARNING: This software program is protected by copyright law 
	and international treaties. Unauthorized reproduction or
	distribution of this program, or any portion of it, may result
	in severe civil and criminal penalties, and will be prosecuted
	to the maximum extent possible under the law.

	You can use this softwareunder LGPL license, or you need to buy 
  a commercial license for a better tech support or non-LGPL usage.

*****************************************************************/

if(!window.Sigma){
  window.Sigma={};
}
Sigma.Const=Sigma.Const||{};
SigmaConst=Sigma.Const;
Sigma.Const.Grid={COL_CLASS_PREFIX:"td.",DEFAULT_ECG_ID:"gt",SHADOW_ROW:"_shadowRow",HIDE_HEADER_ROW:"_hideListRow",COL_T_CLASSNAME:"gt-col-",SKIN_CLASSNAME_PREFIX:"gt-skin-",SCROLLBAR_WIDTH:18,MIN_COLWIDTH:40,AJAX_HEADER:["isAjaxRequest","true"]};
Sigma.Const.Key={BACKSPACE:8,TAB:9,ENTER:13,SHIFT:16,CTRL:17,PAUSE:19,CAPSLOCK:20,ESC:27,SPACE:33,PAGEUP:33,PAGEDOWN:34,END:35,HOME:36,LEFT:37,UP:38,RIGHT:39,DOWN:40,INSERT:45,DELETE:46,WIN:91,WIN_R:92,MENU:93,F1:112,F2:113,F3:114,F4:115,F5:116,F6:117,F7:118,F8:119,F9:120,F10:121,F11:122,F12:123,NUMLOCK:144,SCROLLLOCK:145};
if(!window.Sigma){
  window.Sigma={};
}
Sigma.loaded=false;
Sigma.init=function(_){
  _=_||window;
  Sigma.doc=document;
  var $=_.navigator.userAgent.toLowerCase();
  Sigma.isIE=$.indexOf("msie")>-1;
  Sigma.isIE7=$.indexOf("msie 7")>-1;
  Sigma.isIE8=$.indexOf("msie 8")>-1;
  Sigma.isIE9=$.indexOf("msie 9")>-1;
  Sigma.isFF=$.indexOf("firefox")>-1;
  Sigma.isFF1=$.indexOf("firefox/1")>-1;
  Sigma.isFF2=$.indexOf("firefox/2")>-1;
  Sigma.isFF3=$.indexOf("firefox/3")>-1;
  Sigma.isOpera=$.indexOf("opera")>-1;
  Sigma.isWebkit=(/webkit|khtml/).test($);
  Sigma.isSafari=$.indexOf("safari")>-1||Sigma.isWebkit;
  Sigma.isChrome=$.indexOf("chrome")>-1||Sigma.isWebkit;
  Sigma.isGecko=Sigma.isMoz=!Sigma.isSafari&&$.indexOf("gecko")>-1;
  Sigma.isStrict=Sigma.doc.compatMode=="CSS1Compat"||Sigma.isSafari;
  Sigma.isBoxModel=Sigma.isIE&&!Sigma.isIE8&&!Sigma.isIE9&&!Sigma.isStrict;
  Sigma.isNotStrictIE=Sigma.isBoxModel;
  Sigma.isSecure=_.location.href.toLowerCase().indexOf("https")===0;
  Sigma.isWindows=($.indexOf("windows")!=-1||$.indexOf("win32")!=-1);
  Sigma.isMac=($.indexOf("macintosh")!=-1||$.indexOf("mac os x")!=-1);
  Sigma.isLinux=($.indexOf("linux")!=-1);
};
Sigma.init();
Sigma.$extend=function(_,A,C){
  if(arguments.length<2){
    A=_;
    _=this;
  }
  for(var B in A){
    var $=A[B];
    if(C&&$&&Sigma.$type($,"object","array")){
      $=Sigma.$clone($,C);
    }
    if($!==undefined){
      _[B]=$;
    }
  }
  return _;
};
Sigma.$extend(Sigma,{$empty:function(){
  },$chk:function($){
    return !!($||$===0||$==="");
  },$type:function($){
    var A=arguments.length;
    if(A>1){
      for(var B=1;B<A;B++){
        if(Sigma.$type($)==arguments[B]){
          return true;
        }
      }
      return false;
    }
    var _=typeof $;
    if($===null){
      return "object";
    }
    if(_=="undefined"){
      return "undefined";
    }
    if($.htmlElement){
      return "element";
    }
    if(_=="object"&&$.nodeType&&$.nodeName){
      switch($.nodeType){
      case 1:
        return "element";
      case 3:
        return (/\S/).test($.nodeValue)?"textnode":"whitespace";
      }
    }
    if(Sigma.U.isArray($)){
      return "array";
    }
    if(_=="object"&&typeof $.length=="number"){
      return ($.callee)?"arguments":"collection";
    }else {
      if(_=="function"&&typeof $.length=="number"&&$[0]!==undefined){
        return "collection";
      }
    }
    return _;
  },$merge:function(){
    var B={};
    for(var C=0;C<arguments.length;C++){
      for(var A in arguments[C]){
        var _=arguments[C][A],$=B[A];
        if($&&Sigma.$type(_,"object")&&Sigma.$type($,"object")){
          B[A]=Sigma.$merge($,_);
        }else {
          B[A]=_;
        }
      }
    }
    return B;
  },$indexOf:function(_,A,$){
    if(_){
      $=$||0;
      for(var C=$,B=_.length;C<B;C++){
        if(_[C]===A){
          return C;
        }
      }
    }
    return -1;
  },$array:function(B,_,D,A){
    var $=[];
    if(B){
      if(!Sigma.$chk(_)){
        _=0;
      }
      if(!Sigma.$chk(D)){
        D=B.length;
      }
      if(Sigma.$type(B,"arguments","collection")||Sigma.$type(B,"array")&&(_>0||D<B.length)){
        for(var E=_;E<D;E++){
          $.push(B[E]);
        }
      }else {
        if(Sigma.$type(B,"array")){
          $=$.concat(B);
        }else {
          for(var C in B){
            if(B.hasOwnProperty(C)){
              $.push(B[C]);
            }
          }
        }
      }
    }
    return $;
  },$clone:function(_,A){
    var $;
    if(!_){
      $=_;
    }else {
      if(Sigma.$type(_,"array","arguments","collection")){
        $=Sigma.$array(_,0,_.length,A);
      }else {
        $=Sigma.$extend({},_,A);
      }
    }
    return $;
  },$msg:function($,_){
    for(var A=1;A<arguments.length;A++){
      $=Sigma.U.replaceAll($,"#{"+A+"}",arguments[A]);
    }
    return $;
  },$clear:function($){
    window.clearTimeout($);
    window.clearInterval($);
    if(CollectGarbage){
      CollectGarbage();
    }
    return null;
  },$thread:function(_,A){
    var $=_;
    window.setTimeout($,A||20);
  },$each:function(C,E,D,$){
    var A=[];
    if(Sigma.$type(C,"array","arguments","collection")||C&&!Sigma.$type(C,"string")&&Sigma.$type(C.length,"number")){
      for(var F=0,B=C.length;F<B;F++){
        A.push(E.call(D||C,C[F],F,C,$));
      }
    }else {
      for(var _ in C){
        A.push(E.call(D||C,C[_],_,C,$));
      }
    }
    return A;
  },$getText:function($){
    return $.innerText===undefined?$.textContent:$.innerText;
  },$element:function($,B){
    if(Sigma.$type($,"string")){
      if(Sigma.isIE&&B&&(B.name||B.type)){
        var _=(B.name)?" name=\""+B.name+"\"":"",A=(B.type)?" type=\""+B.type+"\"":"";
        delete B.name;
        delete B.type;
        $="<"+$+_+A+">";
      }
      $=Sigma.doc.createElement($);
    }
    if(B){
      if(B.style){
        Sigma.$extend($.style,B.style);
        delete B.style;
      }
      Sigma.$extend($,B);
    }
    return $;
  }});
Sigma.Class=function(_){
  _=_||{};
  var $=function(){
    var _=this.in5;
    if(Sigma.$type(_,"function")){
      _=_.apply(this,arguments);
    }
    if(Sigma.$type(_,"object")){
      Sigma.$extend(this,_);
    }
    var $=this.abstractMethods;
    Sigma.$each(this.abstractMethods,function($){
      this[$]=Sigma.$empty;
    },this);
    return (arguments[0]!==Sigma.$empty&&Sigma.$type(this.Tb3,"function"))?this.Tb3.apply(this,arguments):this;
  };
  Sigma.$extend($,this);
  $.constructor=Sigma.Class;
  $.prototype=_;
  return $;
};
Sigma.Class.prototype={extend:function(){
    var C=new this(Sigma.$empty);
    for(var D=0,_=arguments.length;D<_;D++){
      var A=arguments[D];
      for(var $ in A){
        var B=C[$];
        C[$]=Sigma.Class.merge(B,A[$]);
      }
    }
    return new Sigma.Class(C);
  }};
Sigma.Class.merge=function($,_){
  if($&&$!=_){
    var A=Sigma.$type(_);
    if(!Sigma.$type($,A)){
      return _;
    }
    switch(A){
    case "function":
      var B=function(){
        this.Oo3=arguments.callee.Oo3;
        return _.apply(this,arguments);
      };
      B.Oo3=$;
      return B;
    case "object":
      return Sigma.$merge($,_);
    }
  }
  return _;
};
Sigma.$class=function($){
  return new Sigma.Class($);
};
Sigma.$e=Sigma.$element;
Sigma.$A=Sigma.$array;
Sigma.$byId=function($,A){
  if(!Sigma.$chk($)){
    return null;
  }
  var _=Sigma.$type($);
  if(_=="element"){
    return Sigma.$e($,A);
  }
  if(_=="string"||_=="number"){
    $=Sigma.doc.getElementById(""+$);
  }
  if(!$){
    return null;
  }
  if(Sigma.U.contains(["object","embed"],!$.tagName?$.tagName.toLowerCase():"")){
    return $;
  }
  return Sigma.$e($);
};
Sigma.vP3=function($){
  if(!$||!document){
    return null;
  }
  return $.dom?$.dom:(typeof $=="string"?document.getElementById($):$);
};
Sigma.$byName=function(_){
  var A=[];
  if(!Sigma.$chk(_)){
    return A;
  }
  var $=Sigma.doc.getElementsByName(""+_);
  if(!$||$.length<1){
    return A;
  }
  for(var B=0;B<$.length;B++){
    _=$[B];
    A.push(Sigma.U.contains(["object","embed"],_.tagName.toLowerCase())?_:Sigma.$e(_));
  }
  return A;
};
Sigma.$=function($){
  var _=Sigma.$byName($);
  if(_&&_.length>0){
    return _[0];
  }
  return (!_||_.length<1)?Sigma.$byId($):_;
};
Sigma.Utils={P_START:"@{",P_END:"}",P_VAR_NAME:"obj_in",parseExpression:function(ex,pName,argNames,pStart,pEnd){
    pStart=pStart||Sigma.U.P_START;
    pEnd=pEnd||Sigma.U.P_END;
    pName=pName||Sigma.U.P_VAR_NAME;
    argNames=argNames||pName;
    var startLength=pStart.length,endLength=pEnd.length,templateC=[],current=0;
    while(true){
      var start=ex.indexOf(pStart,current),sBegin=start+startLength,sEnd=ex.indexOf(pEnd,sBegin),str=null,val=null;
      if(sBegin>=startLength&&sEnd>sBegin){
        str=ex.substring(current,start);
        val=ex.substring(sBegin,sEnd);
      }else {
        str=ex.substring(current);
      }
      str=Sigma.U.escapeString(str);
      templateC.push(str);
      if(val===null){
        break ;
      }
      if(!Sigma.U.isNumber(val)){
        val=(pName?(pName+"."):"")+val;
      }else {
        val=(pName?(pName+"["):"")+val+(pName?"]":"");
      }
      templateC.push(val);
      current=sEnd+endLength;
    }
    var t="function("+argNames+"){ return "+templateC.join("+")+" }";
    eval("t="+t);
    return t;
  },isArray:function($){
    return Object.prototype.toString.apply($)==="[object Array]";
  },isNumber:function($,_){
    return $===0||(_?Sigma.$type($,"number"):($&&!isNaN($)));
  },parseInt:function($,A){
    var _=parseInt($);
    return isNaN(parseInt($))?A||0:_;
  },add2Map:function(A,_,$){
    $=$||{};
    if($[A]===undefined){
      $[A]=_;
    }else {
      $[A]=[].concat($[A]);
      $[A].push(_);
    }
    return $;
  },moveItem:function(_,$,B){
    if($==B){
      return _;
    }
    var A=_[$],C=_[B];
    _.splice(B,1,A,C);
    if($<B){
      _.splice($,1);
    }else {
      _.splice($+1,1);
    }
    return _;
  },convert:function(_,$){
    switch($){
    case "int":
      return parseInt(_);
    case "float":
      return parseFloat(_);
    case "date":
      return _;
    default:
      return _;
    }
    return _;
  },getTagName:function($){
    return $&&$.tagName?String($.tagName).toUpperCase():null;
  },Lu8:function(A,$,_,B){
    if(!$){
      _=Sigma.$event(_);
      $=Sigma.U.getEventTarget(_);
    }
    B=B||6;
    if(!$){
      return null;
    }
    A=A.toLowerCase();
    while($&&(B--)>0){
      if($.tagName&&$.tagName.toLowerCase()==A){
        return $;
      }
      if(Sigma.U.hasClass($.className,"gt-grid")&&A!="div"){
        break ;
      }
      $=$.parentNode;
    }
    return null;
  },focus:function(_){
    if(_){
      try{
        _.focus();
        _.select&&_.select();
      }
      catch($){
      }
    }
  },hasClass:function($,_){
    return $?Sigma.U.hasSubString($.className,_," "):false;
  },addClass:function($,_){
    if($&&!Sigma.U.hasClass($,_)){
      $.className=Sigma.U.Ae5($.className+" "+_);
    }
    return $;
  },removeClass:function($,_){
    if($){
      $.className=Sigma.U.Ae5($.className.replace(new RegExp("(^|\\s)"+_+"(?:\\s|$)"),"$1"));
    }
    return $;
  },toggleClass:function($,_){
    return Sigma.U.hasClass($,_)?Sigma.U.removeClass($,_):Sigma.U.addClass($,_);
  },hasSubString:function(_,A,$){
    return ($)?($+_+$).indexOf($+A+$)>-1:_.indexOf(A)>-1;
  },childElement:function(_,$){
    var B=0,A=_?_.firstChild:null;
    while(A){
      if(A.nodeType==1){
        if(++B==$){
          return A;
        }
      }
      A=A.nextSibling;
    }
    return null;
  },firstChildElement:function($){
    return Sigma.U.childElement($,1);
  },yo9:function($){
    var _=$.childNodes[$.childNodes.length-1];
    return _.nodeType==1?_:Sigma.U.prevElement(_);
  },nextElement:function($){
    while(($=$.nextSibling)&&$.nodeType!=1){
    }
    return $;
  },prevElement:function($){
    while(($=$.previousSibling)&&$.nodeType!=1){
    }
    return $;
  },getCellIndex:function(_){
    if(Sigma.isIE){
      var $=_.parentNode.cells;
      for(var B=0,A=$.length;B<A;B++){
        if($[B]===_){
          return B;
        }
      }
    }
    return _.cellIndex;
  },insertNodeBefore:function($,_){
    if(!$||!_||!_.parentNode){
      return null;
    }
    _.parentNode.insertBefore($,_);
    return $;
  },insertNodeAfter:function($,_){
    _.parentNode.insertBefore($,_.nextSibling);
    return $;
  },listToMap:function(_){
    var $={};
    for(var A=0;A<_.length;A++){
      $[_[A]]=_[A];
    }
    return $;
  },createSelect:function(A,B,C,_){
    _=_||Sigma.$e("select",C||{});
    var $=Sigma.doc.createDocumentFragment();
    Sigma.$each(A,function(_,C){
      var A=Sigma.$e("option",{"value":C,"text":""+_,innerHTML:_});
      if(Sigma.$chk(B)&&C==B){
        A.selected=true;
      }
      $.appendChild(A);
    });
    _.appendChild($);
    return _;
  },createSelectHTML:function(B,D,G){
    G=G||{};
    var $=G.id?(" id=\""+G.id+"\" "):" ",_=G.className||"",C=G.style?(" style=\""+G.style+"\" "):" ",F=["<select"+$+C+"class=\"gt-input-select "+_+"\">"];
    for(var E in B){
      var A="";
      if((D||D===0)&&E==D){
        A=" selected=\"selected\" ";
      }
      F.push("<option value=\""+E+"\" "+A+">"+B[E]+"</option>");
    }
    F.push("</select>");
    return F.join("");
  },getEventTarget:function(A){
    var _=null;
    try{
      _=A.target||A.srcElement;
    }
    catch($){
      return null;
    }
    return !_?null:(_.nodeType==3?_.parentNode:_);
  },stopEvent:function($){
    $=$||window.event;
    if($){
      if($.stopPropagation){
        $.stopPropagation();
        $.preventDefault();
      }else {
        $.cancelBubble=true;
        $.returnValue=false;
      }
    }
  },addEvent:function(_,B,D,C,A){
    if(!D||!_||!B){
      return false;
    }
    if(arguments.length>3){
      D=Sigma.U.bindAsEventListener(D,C,A);
    }
    if(_.addEventListener){
      _.addEventListener(B,D,false);
    }else {
      var $=B=="selectstart"?B:"on"+B;
      _.attachEvent($,D);
    }
    Sigma.EventCache.add(_,B,D,false);
    return _;
  },removeEvent:function(_,B,D,C,A){
    if(!D||!_||!B){
      return false;
    }
    if(arguments.length>3){
      D=Sigma.U.bindAsEventListener(D,C,A);
    }
    if(_.addEventListener){
      _.removeEventListener(B,D,false);
    }else {
      var $=B=="selectstart"?B:"on"+B;
      _.detachEvent($,D);
    }
    Sigma.EventCache.remove(_,B,D,false);
    return _;
  },ue2:[],au3:function(){
    for(var _=0;_<Sigma.U.ue2.length;_++){
      var $=Sigma.U.ue2[_];
      $.apply(this,arguments);
    }
    Sigma.loaded=true;
  },onLoad:function(_,$){
    $=$||window;
    Sigma.U.ue2.push(_);
    if(!Sigma.U.au3.hasAdd){
      Sigma.U.addEvent($,"load",Sigma.U.au3);
      Sigma.U.au3.hasAdd=true;
    }
  },YC2:function(){
    var $=Sigma.doc.createElement("div");
    $.className="gt-orphan-div";
    return $;
  }(),createElementFromHTML:function(_,A){
    Sigma.U.YC2.innerHTML=_;
    var $=Sigma.U.firstChildElement(Sigma.U.YC2);
    A.appendChild($);
    Sigma.U.YC2.innerHTML="";
    return $;
  },createTrFromHTML:function(_,A){
    Sigma.U.YC2.innerHTML="<table><tbody>"+_+"</tbody></table>";
    var $=Sigma.U.YC2.getElementsByTagName("tr")[0];
    A.appendChild($);
    Sigma.U.YC2.innerHTML="";
    return $;
  },removeNode:function(_){
    for(var A=0;A<arguments.length;A++){
      var $=arguments[A];
      if(!$||!$.parentNode||$.tagName=="BODY"){
        return null;
      }
      Sigma.EventCache.remove($);
      if(Sigma.isIE){
        Sigma.U.YC2.appendChild($);
        Sigma.U.YC2.innerHTML="";
      }else {
        $.parentNode.removeChild($);
      }
    }
  },removeNodeTree:function($){
    if($){
      var _=$.getElementsByTagName("*");
      for(var A=0;A<_.length;A++){
        Sigma.U.removeNodeTree(_[A]);
      }
      Sigma.U.removeNode($);
    }
  },getLastChild:function($){
    return $.childNodes[$.childNodes.length-1];
  },getPosLeftTop:function($,_){
    _=_||window;
    var B=$.offsetTop,A=$.offsetLeft;
    $=$.offsetParent;
    while($&&$!=_){
      B+=($.offsetTop-$.scrollTop);
      A+=($.offsetLeft-$.scrollLeft);
      $=$.offsetParent;
    }
    return [A,B];
  },getPosRight:function($){
    return Sigma.U.getPosLeftTop($)[0]+$.offsetWidth;
  },getPosBottom:function($){
    return Sigma.U.getPosLeftTop($)[1]+$.offsetHeight;
  },getHeight:function(A,$){
    var C=A.offsetHeight||0;
    if($!==true){
      return C;
    }
    var B=Sigma.U.getBorderWidths(A),_=Sigma.U.getPaddings(A);
    return C-B[0]-B[2]-_[0]-_[2];
  },getWidth:function(B,_){
    var $=B.offsetWidth||0;
    if(_!==true){
      return $;
    }
    var C=Sigma.U.getBorderWidths(B),A=Sigma.U.getPaddings(B);
    return $-C[1]-C[3]-A[1]-A[3];
  },getBorderWidths:function($){
    return [Sigma.U.parseInt($.style.borderTopWidth),Sigma.U.parseInt($.style.borderRightWidth),Sigma.U.parseInt($.style.borderBottomWidth),Sigma.U.parseInt($.style.borderLeftWidth)];
  },getPaddings:function($){
    return [Sigma.U.parseInt($.style.paddingTop),Sigma.U.parseInt($.style.paddingRight),Sigma.U.parseInt($.style.paddingBottom),Sigma.U.parseInt($.style.paddingLeft)];
  },getPageX:function($){
    $=$||window.event;
    var _=$.pageX;
    if(!_&&0!==_){
      _=$.clientX||0;
      if(Sigma.isIE){
        _+=Sigma.U.getPageScroll()[0];
      }
    }
    return _;
  },getPageY:function($){
    $=$||window.event;
    var _=$.pageY;
    if(!_&&0!==_){
      _=$.clientY||0;
      if(Sigma.isIE){
        _+=Sigma.U.getPageScroll()[1];
      }
    }
    return _;
  },getPageScroll:function(){
    var _=Sigma.doc.documentElement,$=Sigma.doc.body;
    if(_&&(_.scrollLeft||_.scrollTop)){
      return [_.scrollLeft,_.scrollTop];
    }else {
      if($){
        return [$.scrollLeft,_.scrollTop];
      }else {
        return [0,0];
      }
    }
  },getScroll:function(A){
    var $=A,B=Sigma.doc;
    if($==B||$==B.body){
      var C=window.pageXOffset||B.documentElement.scrollLeft||B.body.scrollLeft||0,_=window.pageYOffset||B.documentElement.scrollTop||B.body.scrollTop||0;
      return [C,_];
    }else {
      return [$.scrollLeft,$.scrollTop];
    }
  },getXY:function(F,A){
    var H,E,_,B,I=Sigma.doc.body;
    if(F.getBoundingClientRect){
      _=F.getBoundingClientRect();
      B=Sigma.U.getScroll(Sigma.doc);
      return [_.left+B[0],_.top+B[1]];
    }
    var K=0,J=0;
    H=F;
    A=A||I;
    var D=F.style.position=="absolute";
    while(H){
      K+=H.offsetLeft;
      J+=H.offsetTop;
      if(!D&&H.style.position=="absolute"){
        D=true;
      }
      if(Sigma.isGecko){
        E=H;
        var C=parseInt(E.style.borderTopWidth,10)||0,G=parseInt(E.style.borderLeftWidth,10)||0;
        K+=G;
        J+=C;
        if(H!=F&&E.style.overflow!="visible"){
          K+=G;
          J+=C;
        }
      }
      H=H.offsetParent;
    }
    if(Sigma.isSafari&&D){
      K-=I.offsetLeft;
      J-=I.offsetTop;
    }
    if(Sigma.isGecko&&!D){
      var $=I;
      K+=parseInt($.style.borderTopWidth,10)||0;
      J+=parseInt($.style.borderTopWidth,10)||0;
    }
    H=F.parentNode;
    while(H&&H!=I){
      if(!Sigma.isOpera||(H.tagName.toUpperCase()!="TR"&&H.style.display!="inline")){
        K-=H.scrollLeft;
        J-=H.scrollTop;
      }
      H=H.parentNode;
    }
    return [K,J];
  },setXY:function($,A){
    if($.style.position=="static"){
      $.style.position="relative";
    }
    var _=Sigma.U.iK0($,A);
    if(A[0]!==false){
      $.style.left=_.left+"px";
    }
    if(A[1]!==false){
      $.style.top=_.top+"px";
    }
  },iK0:function(_,E,D){
    if(typeof E=="object"||E instanceof Array){
      D=E[1];
      E=E[0];
    }
    var A=_.style.position,B=Sigma.U.getXY(_),C=parseInt(_.style.left,10),$=parseInt(_.style.top,10);
    if(isNaN(C)){
      C=(A=="relative")?0:_.offsetLeft;
    }
    if(isNaN($)){
      $=(A=="relative")?0:_.offsetTop;
    }
    return {left:(E-B[0]+C),top:(D-B[1]+$)};
  },getContentWidthHeight:function(A){
    var _=Sigma.U.parseInt(A.style.marginLeft),B=Sigma.U.parseInt(A.style.marginRight),D=Sigma.U.parseInt(A.style.paddingLeft),C=Sigma.U.parseInt(A.style.paddingRight),$=A.clientWidth-D-C,E=A.clientHeight;
    return [$,E];
  },getPixelValue:function(A,_){
    if(Sigma.$type(A,"number")){
      return A;
    }
    A=""+A;
    var $=Sigma.U.parseInt(A);
    if(A.indexOf("%")>1){
      return _*$/100;
    }
    return $;
  },setValue:function(_,C){
    _=Sigma.$(_);
    if(!_){
      return ;
    }
    var A=_.tagName;
    A=(""+A).toUpperCase();
    switch(A){
    case "SELECT":
      var $=[].concat(C),B=null;
      Sigma.$each(_.options,function(C,A){
        if(A===0){
          B=C;
        }
        C.selected=false;
        if(_.multiple){
          Sigma.$each($,function($){
            C.selected=C.value==$;
          });
        }else {
          if(C.value==$[0]){
            C.selected=true;
            B=false;
          }
        }
      });
      if(!_.multiple&&B){
        B.selected=true;
      }
      return (_.multiple)?$:$[0];
    case "INPUT":
      if(_.type=="checkbox"||_.type=="radio"){
        _.checked=_.value==C;
        break ;
      }
    case "TEXTAREA":
      _.value=C;
    }
    return null;
  },getValue:function(_){
    _=Sigma.$(_);
    if(!_){
      return ;
    }
    var A=_.tagName;
    switch(A){
    case "SELECT":
      var $=[];
      Sigma.$each(_.options,function(_){
        if(_.selected){
          $.push(_.value);
        }
      });
      $=(_.multiple)?$:$[0];
      if(($===null||$===undefined)&&_.options[0]){
        $=_.options[0].value;
      }
      return $;
    case "INPUT":
      if((_.type=="checkbox"||_.type=="radio")&&!_.checked){
        break ;
      }
    case "TEXTAREA":
      return _.value;
    }
    return null;
  },setOpacity:function($,_){
    _=_>1?1:(_<0?0:_);
    if(!$.currentStyle||!$.currentStyle.hasLayout){
      $.style.zoom=1;
    }
    if(Sigma.isIE){
      $.style.filter=(_==1)?"":"alpha(opacity="+_*100+")";
    }
    $.style.opacity=_;
    if(_===0){
      if($.style.visibility!="hidden"){
        $.style.visibility="hidden";
      }
    }else {
      if($.style.visibility!="visible"){
        $.style.visibility="visible";
      }
    }
    return $;
  },replaceAll:function(exstr,ov,value){
    var gc=Sigma.U.escapeRegExp(ov);
    if(!Sigma.$chk(gc)||gc===""){
      return exstr;
    }
    var rep="/"+gc+"/gm",r=null,cmd="r=exstr.replace("+rep+","+Sigma.U.escapeString(value)+")";
    eval(cmd);
    return r;
  },trim:function(_,$){
    if(!_||!_.replace||!_.length){
      return _;
    }
    var A=($>0)?(/^\s+/):($<0)?(/\s+$/):(/^\s+|\s+$/g);
    return _.replace(A,"");
  },escapeRegExp:function($){
    return !$?""+$:(""+$).replace(/\\/gm,"\\\\").replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm,"\\$1");
  },escapeString:function($){
    return $===""?"\"\"":(!$?""+$:("\""+(""+$).replace(/(["\\])/g,"\\$1")+"\"").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r"));
  },bind:function(A,_,$){
    $=[].concat($);
    return function(){
      return A.apply(_||A,Sigma.U.merge(Sigma.$A(arguments),$));
    };
  },bindAsEventListener:function(A,_,$){
    return function(B){
      B=B||window.event;
      return A.apply(_||A,[Sigma.$event(B)].concat($));
    };
  },Ae5:function($){
    return Sigma.U.trim($.replace(/\s{2,}/g," "));
  },contains:function($,_,A){
    return Sigma.U.indexOf($,_,A)!=-1;
  },merge:function(A,C,$){
    var _=A.length<C.length?A.length:C.length,D,B;
    if($){
      for(D=0,B=_;D<B;D++){
        A[D]=C[D];
      }
    }
    for(D=_,B=C.length;D<B;D++){
      A[D]=C[D];
    }
    return A;
  },each:function($,A,_){
    return Sigma.$each($,A,_);
  },indexOf:function($,_,B){
    var A=$.length;
    for(var C=(B<0)?Math.max(0,A+B):B||0;C<A;C++){
      if($[C]===_){
        return C;
      }
    }
    return -1;
  },remove:function($,_,B){
    var C=0,A=$.length;
    while(C<A){
      if($[C]===_){
        $.splice(C,1);
        if(!B){
          return $;
        }
        A--;
      }else {
        C++;
      }
    }
    return $;
  },next:function(_,A){
    var $=Sigma.U.indexOf(_,A);
    if($<0){
      return null;
    }
    return _[$+1];
  },previous:function(_,A){
    var $=Sigma.U.indexOf(_,A);
    if($<1){
      return null;
    }
    return _[$-1];
  },Um1:function($,_){
    _=_||Sigma.doc;
    var A=_.createElement("style");
    A.id=$;
    var B=_.getElementsByTagName("head")[0];
    B&&B.appendChild(A);
    return A;
  },getCheckboxState:function($,_){
    var A={};
    for(var B=0;B<$.length;B++){
      if($[B].name==_&&$[B].checked){
        A[$[B].value]=$[B].checked;
      }
    }
    return A;
  }};
Sigma.Util=Sigma.Utils;
Sigma.U=Sigma.Utils;
Sigma.Utils.CSS=function(){
  var $=null;
  return {Um1:function(C,$,A){
      var D;
      A=A||Sigma.doc;
      var B=A.getElementsByTagName("head");
      if(!B||B.length<1){
        B=A.createElement("head");
        if(A.documentElement){
          A.documentElement.insertBefore(B,A.body);
        }else {
          A.appendChild(B);
        }
        B=A.getElementsByTagName("head");
      }
      var E=B[0],F=A.createElement("style");
      F.setAttribute("type","text/css");
      if($){
        F.setAttribute("id",$);
      }
      if(Sigma.isIE){
        E.appendChild(F);
        D=F.styleSheet;
        D.cssText=C;
      }else {
        try{
          F.appendChild(A.createTextNode(C));
        }
        catch(_){
          F.cssText=C;
        }
        E.appendChild(F);
        D=F.styleSheet?F.styleSheet:(F.sheet||A.styleSheets[A.styleSheets.length-1]);
      }
      this.cacheStyleSheet(D);
      return D;
    },getRules:function(_,A){
      A=A||Sigma.doc;
      if(!$||_){
        $={};
        var C=A.styleSheets;
        for(var D=0,B=C.length;D<B;D++){
          this.cacheStyleSheet(C[D]);
        }
      }
      return $;
    },getRule:function(_,$){
      var A=this.getRules($);
      return A[_.toLowerCase()];
    },updateRule:function($,B,A){
      var _=this.getRule($);
      if(_){
        _.style[B]=A;
      }
    },cacheStyleSheet:function(A){
      $=$||{};
      try{
        var B=A.cssRules||A.rules;
        for(var C=B.length-1;C>=0;--C){
          $[B[C].selectorText.toLowerCase()]=B[C];
        }
      }
      catch(_){
      }
    }};
}();
Sigma.$event=function($){
  $=$||window.event;
  return $;
};
Sigma.EventCache=(function(){
  var A=[],_=[],B={};
  function $($){
    return ""+$+"_"+$.id;
  }
  return {add:function($,D,E){
      if(!$){
        return ;
      }
      if(!Sigma.U.contains(A,arguments)){
        A.push(arguments);
      }
      var C=Sigma.U.indexOf(_,$),F=C+"_"+$+"_"+$.id;
      if(C<0){
        F=_.length+"_"+$+"_"+$.id;
        _.push($);
        B[F]={};
      }
      B[F][D]=B[F][D]||[];
      if(!Sigma.U.contains(B[F][D],E)){
        B[F][D].push(E);
      }
    },remove:function($,C,D){
      if(!$){
        return ;
      }
      var A=Sigma.U.indexOf(_,$),E=A+"_"+$+"_"+$.id;
      if(A<0||!B[E]){
        return ;
      }
      if(!C){
        B[E]=null;
        _[A]=null;
        return ;
      }
      if(!D&&B[E][C]){
        B[E][C]=null;
        delete B[E][C];
      }
      if(B[E][C]){
        B[E][C].remove(D);
      }
    },HN8:function(){
      Sigma.destroyGrids&&Sigma.destroyGrids();
      Sigma.destroyWidgets&&Sigma.destroyWidgets();
      var _,$;
      for(_=A.length-1;_>=0;_=_-1){
        $=A[_];
        Sigma.EventCache.remove($[0]);
        if($[0].removeEventListener){
          $[0].removeEventListener($[1],$[2],$[3]);
        }
        if($[1].substring(0,2)!="on"){
          $[1]="on"+$[1];
        }
        if($[0].detachEvent){
          $[0].detachEvent($[1],$[2]);
        }
        $[0][$[1]]=null;
        delete A[_];
      }
      window.CollectGarbage&&CollectGarbage();
    }};
})();
Sigma.toQueryString=function(A){
  if(!A||Sigma.$type(A,"string","number")){
    return A;
  }
  var _=[];
  for(var C in A){
    var B=A[C];
    if(B!==undefined){
      B=[].concat(B);
    }
    for(var D=0;D<B.length;D++){
      var $=B[D];
      if(Sigma.$type($,"object")){
        $=Sigma.$json($);
      }
      _.push(encodeURIComponent(C)+"="+encodeURIComponent($));
    }
  }
  return _.join("&");
};
Sigma.toJSONString=function($,_){
  return Sigma.JSON.encode($,"__gt_",_);
};
Sigma.$json=Sigma.toJSONString;
Sigma.FunctionCache={};
Sigma.$invoke=function(B,_,$){
  B=B||window;
  var A=B[_]||Sigma.$getFunction(_);
  if(typeof (A)=="function"){
    return A.apply(B,$||[]);
  }
};
Sigma.$getFunction=function($){
  return Sigma.FunctionCache[$];
};
Sigma.$callFunction=function($,_){
  Sigma.$invoke(null,$,_);
};
Sigma.$putFunction=function($,_){
  Sigma.FunctionCache[$]=_;
};
Sigma.$removeFunction=function($){
  Sigma.FunctionCache[$]=null;
  delete Sigma.FunctionCache[$];
};
Sigma.U.onLoad(function(){
  Sigma.U.addEvent(window,"unload",Sigma.EventCache.HN8);
});
Sigma.AjaxDefault={paramName:"_gt_json"};
Sigma.Ajax=Sigma.$class({in5:function(){
    return {method:"post",jsonParamName:Sigma.AjaxDefault.paramName,async:true,urlEncoded:true,encoding:null,mimeType:null,beforeSend:Sigma.$empty,onComplete:Sigma.$empty,onSuccess:Sigma.$empty,onFailure:Sigma.$empty,onCancel:Sigma.$empty,xhr:"",url:"",data:"",paramType:"jsonString",headers:{"X-Requested-With":"XMLHttpRequest","Accept":"text/javascript, text/html, application/xml,application/json, text/xml, */*"},autoCancel:false,evalScripts:false,evalResponse:false,responseContentType:"",dataUrl:false,queryParameters:null};
  },setQueryParameters:function($){
    this.queryParameters=$;
  },Tb3:function(_){
    _=_||{};
    if(Sigma.$type(_,"string")){
      _={url:_};
    }
    if(!(this.xhr=this.getXHR())){
      return ;
    }
    var $=Sigma.$extend(this.headers,_.headers);
    Sigma.$extend(this,_);
    if(this.mimeType){
      $["X-Response-MimeType"]=this.mimeType;
    }
    this.headers=$;
  },send:function(I){
    this.running=true;
    if(Sigma.$type(I,"string")){
      I={data:I};
    }
    I=Sigma.$extend({data:this.data,url:this.url,method:this.method},I);
    var C=I.data,H=I.url,E=String(I.method).toLowerCase();
    if(Sigma.$invoke(this,"beforeSend",[this.xhr,C])===false){
      return this;
    }
    if(this.urlEncoded&&E=="post"){
      var B=(this.encoding)?"; charset="+this.encoding:"";
      this.setHeader("Content-type","application/x-www-form-urlencoded"+B);
    }
    switch(Sigma.$type(C)){
    case "object":
      if(this.paramType=="jsonString"){
        var F=Sigma.$json(C);
        C={};
        C[this.jsonParamName]=F;
      }
      C=Sigma.toQueryString(C);
      break ;
    default:
    }
    var D;
    if(this.queryParameters&&Sigma.$type(this.queryParameters,"object")){
      D=Sigma.toQueryString(this.queryParameters);
    }else {
      if(Sigma.$type(this.queryParameters,"string")){
        D=this.queryParameters;
      }
    }
    if(D&&Sigma.$type(C,"string")){
      C=C+"&"+D;
    }
    if(E=="post"){
      var _=H.indexOf("?");
      if(_>=0){
        C=H.substring(_+1)+"&"+C;
        H=H.substring(0,_);
      }
    }else {
      if(C&&(E=="get"||this.dataUrl)){
        H=H+(H.indexOf("?")>=0?"&":"?")+C;
        C=null;
      }
    }
    var A=this;
    this.xhr.open(E.toUpperCase(),H,this.async);
    this.xhr.onreadystatechange=function(){
      return A.onStateChange.apply(A,arguments);
    };
    for(var G in this.headers){
      try{
        this.xhr.setRequestHeader(G,this.headers[G]);
      }
      catch($){
      }
    }
    this.xhr.send(C);
    if(!this.async){
      this.onStateChange();
    }
    return this;
  },onStateChange:function(){
    if(this.xhr.readyState!=4||!this.running){
      return ;
    }
    this.running=false;
    this.status=0;
    try{
      this.status=this.xhr.status;
    }
    catch($){
    }
    this.onComplete();
    if(this.isSuccess()){
      this._onSuccess();
    }else {
      this._onFailure();
    }
    this.xhr.onreadystatechange=Sigma.$empty;
  },isScript:function(){
    return (/(ecma|java)script/).test(this.getHeader("Content-type"));
  },isSuccess:function(){
    var $=this.xhr.status;
    return (($>=200)&&($<300));
  },_onSuccess:function(){
    this.response={"text":this.xhr.responseText,"xml":this.xhr.responseXML};
    this.onSuccess(this.response);
  },_onFailure:function($){
    this.onFailure(this.xhr,$);
  },setHeader:function($,_){
    this.headers[$]=_;
    return this;
  },getHeader:function(_){
    try{
      return this.xhr.getResponseHeader(_);
    }
    catch($){
      return null;
    }
  },getXHR:function(){
    return (window.XMLHttpRequest)?new XMLHttpRequest():((window.ActiveXObject)?new ActiveXObject("Microsoft.XMLHTTP"):false);
  },cancel:function(){
    if(!this.running){
      return this;
    }
    this.running=false;
    this.xhr.abort();
    this.xhr.onreadystatechange=Sigma.$empty;
    this.xhr=this.getXHR();
    this.onCancel();
    return this;
  }});
Sigma.JSON={encode:function(A,$,B){
    var _,C=B?"\n":"";
    switch(Sigma.$type(A)){
    case "string":
      return "\""+A.replace(/[\x00-\x1f\\"]/g,Sigma.JSON.Vf6)+"\"";
    case "array":
      _=[];
      Sigma.$each(A,function(D,A){
        var C=Sigma.JSON.encode(D,$,B);
        if(C||C===0){
          _.push(C);
        }
      });
      return "["+C+(B?_.join(","+C):_)+"]"+C;
    case "object":
      if(A===null){
        return "null";
      }
      _=[];
      Sigma.$each(A,function(C,D){
        if(!$||D.indexOf($)!==0){
          var A=Sigma.JSON.encode(C,$,B);
          if(A){
            _.push(Sigma.JSON.encode(D,$,B)+":"+A);
          }
        }
      },null,$);
      return "{"+C+(B?_.join(","+C):_)+C+"}"+C;
    case "number":
    case "boolean":
      return String(A);
    }
    return null;
  },decode:function(string,secure){
    if(!Sigma.$type(string,"string")||!string.length){
      return null;
    }
    if(secure&&!(/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(string.replace(/\\./g,"@").replace(/"[^"\\\n\r]*"/g,""))){
      return null;
    }
    return eval("("+string+")");
  },Ca1:{"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r","\"":"\\\"","\\":"\\\\"},Vf6:function($){
    return Sigma.JSON.Ca1[$]||"\\u00"+Math.floor($.charCodeAt()/16).toString(16)+($.charCodeAt()%16).toString(16);
  }};
Sigma.Const.DataSet={KEY:"__gt_ds_key__",INDEX:"__gt_ds_index__",ROW_KEY:"__gt_row_key__",NOT_VAILD:"__gt_no_valid__",SN_FIELD:"__gt_sn__",SORT_VALUE:"__gt_sort_value__",SORT_S:"__gt_"};
Sigma.DataSetDefault={SEQUENCE:0,uniqueField:Sigma.Const.DataSet.SN_FIELD,recordType:"object",recordXpath:null,dataXML:null,currentBegin:0,cursor:0,startRecordNo:0,cacheData:false,cacheModifiedData:true,modified:false,in5:function(){
    return {fields:[],fieldsName:[],fieldsMap:{},fieldsInfo:{},data:null,vS5:[],Ac6:null,additional:[],sortInfo:[],queryInfo:[],reocrdIndex:{},updatedRecords:{},nl8:{},updatedFields:{},insertedRecords:{},deletedRecords:{},qd2:Sigma.$empty,ht4:Sigma.$empty};
  },Tb3:function($){
    Sigma.$extend(this,$);
    this.recordType=this.recordType||"object";
    this.fields&&this.XB3(this.fields);
    this.data&&this.HF1(this.data);
    this.unfieldIdx=this.uniqueField;
  },initValues:Sigma.$empty,isEqualRecord:function(_,A){
    for(var $ in this.fieldsInfo){
      if(_[$]!==A[$]){
        return false;
      }
    }
    return true;
  },Ae5:function($){
    if(!this.cacheData||$===true){
      this.data=null;
      this.currentBegin=0;
      this.vS5=[];
    }
    this.cleanModifiedData();
  },cleanModifiedData:function($){
    if(!this.cacheModifiedData||$){
      this.updatedRecords={};
      this.nl8={};
      this.updatedFields={};
      this.insertedRecords={};
      this.deletedRecords={};
    }
  },HF1:function($){
    if(!$){
      return false;
    }
    this.Ae5();
    return this.ce4($);
  },XB3:function(C){
    this.fields=C;
    this.fieldsName=[];
    var A=null;
    for(var B=0,_=this.fields.length;B<_;B++){
      var $=this.fields[B]||{};
      if(Sigma.$type($,"string")){
        $={name:$};
      }
      $.name=$.name||String(B);
      $.type=$.type||"string";
      $.index=$.index||(this.getRecordType()=="array"?B:$.name);
      if($.initValue){
        A=A||{};
        A[$.index]=$.initValue;
      }
      this.fieldsMap[$.name]=$;
      this.fieldsInfo[$.index]=$;
      this.fieldsName[B]=$.name;
    }
    if(A){
      this.initValues=(function($){
        return function(C,A,B){
          for(var _ in $){
            C[_]=$[_](C,A,B);
          }
        };
      })(A);
    }else {
      this.initValues=Sigma.$empty;
    }
  },ce4:function(A){
    if(!A){
      return false;
    }
    this.data=this.data||[];
    var $=this,D=Sigma.Const.DataSet.SN_FIELD;
    for(var C=0,B=A.length;C<B;C++){
      var _=A[C];
      _[D]=_[D]||this.SEQUENCE++;
      this.data.push(_);
      this.vS5.push(this.currentBegin++);
      this.initValues(_,C,this);
    }
    return true;
  },getDataProxySize:function(){
    return this.vS5.length;
  },resetDataProxy:function(_){
    this.vS5=[];
    _=_||this.getSize();
    for(var $=0;$<_;$++){
      this.vS5[$]=$;
    }
  },loadData:function($){
    if($){
      return this.HF1($.load());
    }
  },oh6:function($){
    if($&&this.recordType!=$){
      this.recordType=$;
      this.XB3(this.fields);
    }
  },getRecord:function($){
    return this.data?this.data[this.vS5[$]]:null;
  },getDataRecord:function($){
    return this.dataset.data[$];
  },setValueByName:function($,B,A){
    var _=this.fieldsMap[B].index;
    if(Sigma.$type($,"number")){
      $=this.getRecord($);
    }
    $[_]=A;
  },getValueByName:function($,A){
    var _=this.fieldsMap[A].index;
    if(Sigma.$type($,"number")){
      $=this.getRecord($);
    }
    return $[_];
  },getFields:function(){
  },getRecordType:function(_,$){
    this.recordType=_||this.recordType;
    if(!Sigma.$type(this.recordType,"string")&&(this.data&&this.getSize()>0)){
      $=this.data[0];
      if(Sigma.$type($,"array")){
        this.recordType="array";
      }else {
        this.recordType="object";
      }
    }
    return this.recordType;
  },filterCheck:{equal:function($,_){
      return $==_;
    },notEqual:function($,_){
      return $!=_;
    },less:function($,_){
      return $<_;
    },great:function($,_){
      return $>_;
    },lessEqual:function($,_){
      return $<=_;
    },greatEqual:function($,_){
      return $>=_;
    },like:function($,_){
      return (""+$).indexOf(_+"")>=0;
    },startWith:function($,_){
      return (""+$).indexOf(_+"")===0;
    },endWith:function($,_){
      $=$+"";
      _=_+"";
      return $.indexOf(_)==$.length-_.length;
    }},filterData:function(_){
    var $=this,A=[];
    _=[].concat(_);
    Sigma.$each(this.data,function(C,E){
      var F=true;
      for(var J=0,H=_.length;J<H;J++){
        var D=$.fieldsMap[_[J].fieldName].index,G=_[J].value,I=_[J].logic,B=C[D];
        F=$.filterCheck[I](B,G);
        if(!F){
          break ;
        }
      }
      if(F){
        A.push(E);
      }
    });
    return A;
  },Qh3:function($){
    var _=(this.SEQUENCE++);
    $[Sigma.Const.DataSet.SN_FIELD]=_;
    this.insertedRecords[_]=$;
    Sigma.$invoke(this,"qd2",[$]);
    this.modified=true;
  },updateRecord:function($,F,C){
    if(Sigma.$type($,"number")){
      $=this.data[$];
    }
    //桑志豪在这里加了判断
    //新加的列$是空
    if(!$)
    	return;
    var B=$[Sigma.Const.DataSet.SN_FIELD],A=$[this.unfieldIdx],E=this.fieldsMap[F].type,_=this.fieldsMap[F].index,D;
    if(!this.insertedRecords[B]){
      this.nl8[A]=this.nl8[A]||{};
      this.nl8[A][_]=$[_];
      this.nl8[A][this.unfieldIdx]=A;
      this.updatedRecords[A]=$;
    }
    if(this.insertedRecords[B]||Sigma.$invoke(this,"ht4",[$,F,C])!==false){
      if(E=="int"){
        C=parseInt(C);
        C=isNaN(C)?"":C;
      }else {
        if(E=="float"){
          C=parseFloat(C);
          C=isNaN(C)?"":C;
        }else {
          C=Sigma.$chk(C)?String(C):"";
        }
      }
      this.updatedFields[A]=this.updatedFields[A]||{};
      this.updatedFields[A][_]=C;
      this.updatedFields[A][this.unfieldIdx]=A;
      $[_]=C;
      this.modified=true;
    }
  },undeleteRecord:function(D){
    var B=-1,$,C;
    if(Sigma.$type(D,"number")){
      B=D;
      if(B>=0){
        C=this.vS5[B];
        $=this.data[C];
      }
    }else {
      if(D&&(Sigma.$type(D,"object")||Sigma.$type(D,"array"))){
        $=D;
      }
    }
    if($){
      var A=$[Sigma.Const.DataSet.SN_FIELD],_=$[this.unfieldIdx];
      this.deletedRecords[_]=null;
      delete this.deletedRecords[_];
    }
  },Ie6:function(D){
    var B=-1,$,C;
    if(Sigma.$type(D,"number")){
      B=D;
      if(B>=0){
        C=this.vS5[B];
        $=this.data[C];
      }
    }else {
      if(D&&(Sigma.$type(D,"object")||Sigma.$type(D,"array"))){
        $=D;
      }
    }
    if($){
      var A=$[Sigma.Const.DataSet.SN_FIELD],_=$[this.unfieldIdx];
      if(this.insertedRecords[A]){
        delete this.insertedRecords[A];
      }else {
        if(this.updatedRecords[_]){
          delete this.updatedRecords[_];
          delete this.nl8[_];
        }
        this.deletedRecords[_]=$;
        this.modified=true;
      }
    }
  },addUniqueKey:function($){
  },isInsertedRecord:function($){
    return $&&this.insertedRecords[$[Sigma.Const.DataSet.SN_FIELD]]==$;
  },Yr3:function($){
    return $&&this.deletedRecords[$[this.unfieldIdx]]==$;
  },isUpdatedRecord:function($){
    return $&&this.updatedRecords[$[this.unfieldIdx]]==$;
  },sortFunction:null,negative:function($){
    return function(A,_){
      return 0-$(A,_);
    };
  },sort:function(J){
    var E=[].concat(J),C=[];
    for(var D=0;D<E.length;D++){
      var H=E[D];
      if(H){
        var F,B,$,I=H.sortOrder.indexOf("def")===0;
        if(!H.sortOrder||I){
          $=Sigma.Const.DataSet.SN_FIELD;
          B="int";
        }else {
          F=this.fieldsMap[H.fieldName];
          if(F){
            $=F.index;
            B=F.type;
          }
        }
        C.push(!I&&H.sortFn?H.sortFn:this.getSortFuns($,H.sortOrder,B,H.getSortValue));
      }
    }
    var G=this,_=C.length,A=function(B,A){
      var D=G.data[B],F=G.data[A];
      for(var H=0;H<_;H++){
        var $=C[H](D,F,E[H].sortOrder);
        if($!==0){
          return $;
        }
      }
      return 0;
    };
    this.vS5.sort(A);
  },getSortFuns:function(D,B,E,G){
    var $=this,A=Sigma.Const.DataSet.SORT_VALUE,C={},F=this.sortFunction;
    if(!F){
      var _=G&&B.indexOf("def")!==0?function(_){
        var A=_[D],$=G(A,_);
        C[_[Sigma.Const.DataSet.SN_FIELD]]=$;
        return $;
      }:function(_){
        var A=_[D],$=Sigma.U.convert(A,E);
        C[_[Sigma.Const.DataSet.SN_FIELD]]=$;
        return $;
      };
      F=B=="desc"?function(B,D){
        var $=C[B]||_(B),A=C[D]||_(D);
        return $<A?1:($>A?-1:0);
      }:function(B,D){
        var $=C[B]||_(B),A=C[D]||_(D);
        return $<A?-1:($>A?1:0);
      };
    }
    return F;
  },query:function($,_,B,A){
  },getSize:function(){
    return !this.data?-1:this.data.length;
  },getFieldsNum:function(){
    return this.fields.length;
  },sum:function($){
  },avg:function($){
  }};
Sigma.DataSet=Sigma.$class(Sigma.DataSetDefault);
if(!Sigma.Template){
  Sigma.Template={};
}
Sigma.$extend(Sigma.Template,{Grid:{main:function(_){
      var $=_.id,A=[_.toolbarPosition=="top"||_.toolbarPosition=="t"?"<div id=\""+$+"_toolBarBox\" class=\"gt-toolbar-box gt-toolbar-box-top\" ></div>":"","<div id=\""+$+"_viewport\" class=\"gt-viewport"+(_.simpleScrollbar?" gt-simple-scrollbar":"")+"\" >","<div id=\""+$+"_headDiv\" class=\"gt-head-div\"><div class=\"gt-head-wrap\" ></div>","<div id=\""+$+"_columnMoveS\" class=\"gt-column-moveflag\"></div>","<div id=\""+$+"_headerGhost\" class=\"gt-head-ghost\"></div>","</div>","<div id=\""+$+"_bodyDiv\" class=\"gt-body-div\"></div>","<div id=\""+$+"_freeze_headDiv\" class=\"gt-freeze-div\" ></div>","<div id=\""+$+"_freeze_bodyDiv\" class=\"gt-freeze-div\" ></div>","</div>",_.toolbarPosition=="bottom"||_.toolbarPosition=="b"?"<div id=\""+$+"_toolBarBox\" class=\"gt-toolbar-box\" ></div>":"","<div id=\""+$+"_separateLine\" class=\"gt-split-line\"></div>","<div id=\""+$+"_mask\" class=\"gt-grid-mask\" >","<div  id=\""+$+"_waiting\" class=\"gt-grid-waiting ui-state-default ui-corner-all\">","<div class=\"gt-grid-waiting-icon\"></div><div class=\"gt-grid-waiting-text\">"+_.getMsg("WAITING_MSG")+"</div>","</div>","<div class=\"gt-grid-mask-bg\">","</div>","</div>"];
      return A.join("\n");
    },formIFrame:function(_){
      var $=_.id,A=["<div class=\"gt-hidden\" >","<form id=\""+$+"_export_form\" target=\""+$+"_export_iframe\" style=\"width:0px;height:0px;margin:0px;padding:0xp\" method=\"post\" width=\"0\" height=\"0\" >","<input type=\"hidden\" id=\""+$+"_export_filename\" name=\"exportFileName\"  value=\"\" />","<input type=\"hidden\" id=\""+$+"_export_exporttype\" name=\"exportType\" value=\"\" />","<textarea id=\""+$+"_export_form_textarea\" name=\"\" style=\"width:0px;height:0px;display:none;\" ></textarea>","</form>","<iframe id=\""+$+"_export_iframe\"  name=\""+$+"_export_iframe\" scrolling=\"no\" style=\"width:0px;height:0px;\" width=\"0\" height=\"0\" border=\"0\" frameborder=\"0\" >","</iframe>","</div>"];
      return A.join("\n");
    },Do0:function(B,$,_){
      var A=Sigma.$e("td",{className:$.styleClass,columnId:$.id}),C=$.hdRenderer($.header,$,B);
      $.title=$.title||$.header||"";
      C=(!C||Sigma.U.trim(C)==="")?"&#160;":C;
      if(_){
        A.style.height="0px";
      }
      A.innerHTML=["<div class=\"gt-inner"+($.headAlign?" gt-inner-"+$.headAlign:"")+"\" ",_?"style=\"padding-top:0px;padding-bottom:0px;height:1px;\" ":"","unselectable=\"on\" title=\""+$.title+"\" >","<span>",C,"</span>",_?"":Sigma.T_G.hdToolHTML,"</div>"].join("");
      return A;
    },hdToolHTML:"<div class=\"gt-hd-tool\" ><span class=\"gt-hd-icon\"></span><span class=\"gt-hd-button\"></span><span class=\"gt-hd-split\"></span></div>",bodyTableStart:function($,_){
      return ["<table ",$?"id=\""+$+"\" ":"","class=\"gt-table\" cellspacing=\"0\"  cellpadding=\"0\" border=\"0\" >",_===false?"":"<tbody>"].join("");
    },tableStartHTML:"<table class=\"gt-table\" style=\"margin-left:0px\" cellspacing=\"0\"  cellpadding=\"0\" border=\"0\" ><tbody>",tableEndHTML:"</tbody></table>",rowStart:function($,A,_){
      return Sigma.T_G.rowStartS($,A)+">\n";
    },rowStartS:function(_,A,$){
      return ["<tr class=\"gt-row",(A%2===0?_.evenRowCss:""),"\" ",Sigma.Const.DataSet.INDEX,"=\"",A,"\" ",$].join("");
    },rowEndHTML:"</tr>\n",innerStart:function($){
      return ["<div class=\"gt-inner "+($.align?" gt-inner-"+$.align+" ":"")+"","\" >"].join("");
    },cellStartHTML:"<td ><div class=\"gt-inner\" >",cellEndHTML:"</div></td>",cell:function($,A,_){
      return ["<td ",_||$.cellAttributes," class=\""+$.styleClass+"\" >",$.innerStartHTML,A,"</div></td>"].join("");
    },Go9:function($,_){
      return (Sigma.Const.Grid.COL_T_CLASSNAME+$.id+"-"+_).toLowerCase();
    },freezeBodyCell:function(D,C,_,A){
    	//桑志豪这里修改过IE8下有问题
        var F=C+D.kR2,$=C+D.oc8,E="style=\"width:"+$+"px;\"";
		_=_||"&#160;";
		var B=Sigma.$e("td",{style:{width:F+"px"},innerHTML:"<div class=\"gt-inner\" "+E+">"+_+"</div>"});
		return B;
    },oo5:function(A,_,$){
      return this.freezeBodyCell(A,_,$,true);
    }},Dialog:{create:function(C){
      var $=C.domId,A=C.gridId,B=Sigma.$chk(C.Am5)?C.Am5:true,_=C.title||"Dialog";
      return ["<div class=\"gt-dialog-head\" >","<div class=\"gt-dialog-head-icon\">&#160;</div>","<div id=\""+$+"_dialog_title\"  class=\"gt-dialog-head-text\" >"+_+"</div>","<div class=\"gt-dialog-head-button\"  >",B?"<a href=\"#\" onclick=\"Sigma.$grid('"+A+"').closeDialog();return false;\">&#160;</a>":"","</div>","</div><div id=\""+$+"_dialog_body\" class=\"gt-dialog-body\"></div>"].join("");
    },Hm4:["<select class=\"gt-input-select\">","<option value=\"equal\">=</option>","<option value=\"notEqual\">!=</option>","<option value=\"less\">&lt;</option>","<option value=\"great\">></option>","<option value=\"lessEqual\">&lt;=</option>","<option value=\"greatEqual\" >>=</option>","<option value=\"like\" >like</option>","<option value=\"startWith\">startWith</option>","<option value=\"endWith\">endWith</option>","</select>"].join("")}});
Sigma.T_G=Sigma.Template.Grid;
Sigma.T_C=Sigma.Template.Column;
Sigma.T_D=Sigma.Template.Dialog;
if(!window.Sigma){
  window.Sigma={};
}
Sigma_GRID_VER="grid 1.0";
Sigma.WidgetCache={};
Sigma.GridCache={};
Sigma.GridNum=0;
Sigma.activeGrid=null;
Sigma.$widget=function($){
  return Sigma.$type($,"string")?Sigma.WidgetCache[$]:$;
};
Sigma.$grid=function($){
  $=$||Sigma.Const.Grid.DEFAULT_ECG_ID;
  return Sigma.$type($,"string")?Sigma.GridCache[$]:$;
};
Sigma.destroyGrids=function(){
  Sigma.eachGrid(function(_){
    try{
      _.destroy();
    }
    catch($){
    }
  });
};
Sigma.destroyWidgets=function(){
  for(var A in Sigma.WidgetCache){
    var _=Sigma.WidgetCache[A];
    if(_&&_.destroy){
      try{
        _.destroy();
      }
      catch($){
      }
    }
  }
};
Sigma.eachGrid=function(_){
  for(var A in Sigma.GridCache){
    var $=Sigma.GridCache[A];
    _($);
  }
};
Sigma.GridDefault={id:Sigma.Const.Grid.DEFAULT_ECG_ID,defaultColumnWidth:70,domList:["gridWaiting","separateLine","gridMask","gridGhost","gridFormTextarea","gridFormFileName","gridFormExportType","gridForm","gridIFrame","activeDialog","gridDialog","gridDialogTitle","gridDialogBody","gridFilterRSCache","titleBar","toolTipDiv","freezeHeadTable","freezeHeadDiv","freezeBodyDiv","freezeView","resizeButton","pageSizeSelect","pageStateBar","toolBar","KN2","columnMoveS","headerGhost","headFirstRow","bodyFirstRow","headTable","headDiv","bodyDiv","gridDiv","viewport","container"],defaultConst:["action","recordType","exportType","exportFileName","exception","parameters","queryParameters","data","pageInfo","filterInfo","sortInfo","columnInfo","fieldsName","insertedRecords","updatedRecords","updatedFields","deletedRecords","success","succeedData","failedData","remotePaging","remoteSort","remoteFilter","remoteGroup"],language:"default",skin:"default",dataPageInfo:null,dataException:null,formid:null,isNative:false,loadURL:null,saveURL:null,exportURL:null,exportType:null,exportFileName:null,sortInfo:null,editable:true,resizable:false,allowCustomSkin:false,allowFreeze:false,allowHide:false,allowGroup:false,allowResizeColumn:true,simpleScrollbar:true,scrollbarClass:null,monitorResize:false,readOnly:false,stripeRows:true,lightOverRow:true,evenRowCss:"gt-row-even",clickStartEdit:true,remotePaging:true,remoteSort:false,remoteFilter:false,remoteGroup:false,autoLoad:true,submitColumnInfo:true,autoUpdateSortState:true,autoUpdateEditState:true,autoUpdateGroupState:true,autoUpdateFreezeState:true,autoSelectFirstRow:true,autoEditNext:false,submitUpdatedFields:false,autoSaveOnNav:false,reloadAfterSave:true,recountAfterSave:true,recount:false,showGridMenu:false,showEditTool:true,showAddTool:true,showDelTool:true,showSaveTool:true,showReloadTool:true,showPrintTool:true,showFilterTool:true,showChartTool:true,showPageState:true,showIndexColumn:false,renderHiddenColumn:true,transparentMask:false,justShowFiltered:true,toolbarPosition:"bottom",toolbarContent:"nav | goto | pagesize | reload | add del save | print | filter chart | state",width:"100%",height:"100%",minWidth:50,minHeight:50,dataRoot:"data",custom2Cookie:true,multiSort:false,multiGroup:false,multiSelect:true,selectRowByCheck:false,html2pdf:true,SigmaGridPath:"../../gt-grid",in5:function(){
    return {skinList:[{text:this.getMsg("STYLE_NAME_DEFAULT"),value:"default"},{text:this.getMsg("STYLE_NAME_CHINA"),value:"china"},{text:this.getMsg("STYLE_NAME_VISTA"),value:"vista"},{text:this.getMsg("STYLE_NAME_MAC"),value:"mac"}],encoding:null,mimeType:null,jsonParamName:null,title:null,lastAction:null,ajax:null,autoExpandColumn:null,autoColumnWidth:false,cellWidthPadding:Sigma.isBoxModel?0:4,cellHeightPadding:Sigma.isBoxModel?0:2,kR2:Sigma.isBoxModel?0:0,oc8:Sigma.isBoxModel?0:(Sigma.isIE8?-1:-4),freezeFixH:Sigma.isBoxModel?0:0,freezeFixW:Sigma.isIE?-1:-2,toolbarHeight:24,Kt9:0,iB3:0,freezeColumns:0,ye8:0,defaultRecord:null,isWaiting:false,Ad6:false,requesting:false,hasGridDivTemp:false,ut9:-1,moveColumnDelay:800,mouseDown:false,footDiv:null,footTable:null,footFirstRow:null,freezeBodyTable:null,titleBar:null,nearPageBar:null,lastOverHdCell:null,toolTipDiv:null,gridTable:null,overRow:null,overRowF:null,activeCell:null,activeRow:null,activeRecord:null,activeEditor:null,activeDialog:null,scrollLeft:0,scrollTop:0,Hs5:0,onComplete:Sigma.$empty,onResize:Sigma.$empty,beforeSelectRow:Sigma.$empty,afterSelectRow:Sigma.$empty,onClickHead:Sigma.$empty,onClickCell:Sigma.$empty,onDblClickCell:Sigma.$empty,beforeEdit:Sigma.$empty,afterEdit:Sigma.$empty,beforeRefresh:Sigma.$empty,beforeExport:Sigma.$empty,beforeSave:Sigma.$empty,beforeLoad:Sigma.$empty,Qc6:Sigma.$empty,beforeUpdateDataset:Sigma.$empty,ht4:Sigma.$empty,beforeInsert:Sigma.$empty,afterInsert:Sigma.$empty,beforeUpdate:Sigma.$empty,afterUpdate:Sigma.$empty,beforeDelete:Sigma.$empty,afterDelete:Sigma.$empty,customRowAttribute:Sigma.$empty,editing:false,rendered:false,isFirstLoad:true,customPrintCss:null,gridTbodyList:[],gridRowList:[],gridFreezeRowList:[],checkedRows:{},rowBegin:0,rowNum:0,rowEnd:0,currentRowNum:0,filterDataProxy:null,Ac6:null,cacheData:[],dataset:null,selectedRows:[],cacheBodyList:[],frozenColumnList:[],sortedColumnList:[],countTotal:true,pageSizeList:[],tools:{},toolCreator:{},columns:[],columnList:[],columnMap:{},CONST:null,queryParameters:{},parameters:{}};
  },lH8:function(){
    Sigma.activeGrid=this;
  },clearCheckedRows:function($){
    this.checkedRows={};
    if($){
      this.refresh();
    }
  },Tb3:function(_,H){
    Sigma.GridNum++;
    var F={},E=this;
    Sigma.$each(this.domList,function($,_){
      E[$]=null;
    });
    Sigma.$each(this.defaultConst,function($,_){
      F[$]=$;
    });
    this.id=""+_;
    H=H||{};
    if(Sigma.$type(_,"object")){
      H=_;
      this.id="gtgrid_"+Sigma.GridNum;
    }
    Sigma.$extend(F,H.CONST);
    this.CONST=F;
    Sigma.$extend(this,H);
    this.gridId=this.id;
    this.rowKey="__gt_"+this.id+"_r_";
    Sigma.GridCache[this.id]=this;
    this.legacy();
    if(!this.dataset&&this.columns){
      var D={fields:[]};
      for(var C=0;C<this.columns.length;C++){
        var A=this.columns[C],$={name:A.name||A.fieldName||A.id,type:A.type,index:(A.fieldIndex||A.fieldIndex===0)?A.fieldIndex:null};
        D.fields.push($);
      }
      this.dataset=D;
    }
    if(this.dataset&&!(this.dataset instanceof Sigma.DataSet)){
      this.dataset.recordType=this.dataset.recordType||this.recordType;
      this.dataset=new Sigma.DataSet(this.dataset);
    }
    this.loadURL=this.loadURL||this.dataset.loadURL;
    this.saveURL=this.saveURL||this.dataset.saveURL;
    this.evenRowCss=" "+this.evenRowCss;
    this.toolbarContent=this.toolbarContent===false?false:this.toolbarContent;
    if(this.toolCreator){
      for(var G in this.toolCreator){
        Sigma.ToolFactroy.register(G,this.toolCreator[G]);
      }
    }
    var B=H.pageInfo||(this.dataset?this.dataset.pageInfo:null)||{};
    B.pageSize=B.pageSize||H.pageSize||0;
    if(B.pageSize===0){
      delete B.pageSize;
    }
    delete H.pageInfo;
    delete H.pageSize;
    delete this.pageInfo;
    delete this.pageSize;
    this.navigator=new Sigma.Navigator({gridId:this.id,pageInfo:B});
  },legacy:function(){
    var $=this;
    $.beforeRowSelect=$.beforeRowSelect||$.beforeSelectRow;
    $.afterRowSelect=$.afterRowSelect||$.afterSelectRow;
    $.onCellClick=$.onCellClick||$.onClickCell;
    $.onRowClick=$.onRowClick||$.onClickRow;
    $.onCellDblClick=$.onCellDblClick||$.onDblClickCell;
    $.onRowDblClick=$.onRowDblClick||$.onDblClickRow;
  },TP2:function(E){
    this.columns=E||this.columns;
    if(!this.columns){
      return ;
    }
    this.gridEditorCache=this.gridEditorCache||Sigma.$e("div",{className:"gt-editor-cache"});
    var H=this.columns.length,F=0,D=true,C={};
    function B(A,_,$,B,D,C){
      return this.editor.getDisplayValue(A);
    }
    for(var G=0;G<H;G++){
      var A=this.columns[G];
      A.grid=this;
      A.gridId=this.id;
      if(A.isCheckColumn){
        A=Sigma.Grid.yt8(this,A);
      }
      var _=new Sigma.Column(A);
      this.columnMap[_.id]=_;
      this.columnList.push(_);
      _.colIndex=G;
      this.checkColumn=_.isCheckColumn?_:this.checkColumn;
      if(_.frozen){
        this.frozenColumnList.push(_.id);
      }
      var $=this.dataset.fieldsMap[_.fieldName];
      if($){
        _.fieldIndex=$.index;
      }
      if(_.editable!==true&&_.editable!==false){
        _.editable=this.editable;
      }
      _.editor=Sigma.Editor?Sigma.Editor.vn4(_.editor,this):null;
      if(_.editor&&_.editor.vP3()){
        this.gridEditorCache.appendChild(_.editor.vP3());
      }
      if(_.renderer=="by editor"&&_.editor){
        _.renderer=B;
      }else {
        if(Sigma.$type(_.renderer,"string")){
          _.renderer=Sigma.U.parseExpression(_.renderer,"record","value,record,col,grid,colNo,rowNo");
        }
      }
      C[_.fieldIndex]=_.newValue||"";
      _.styleClass=Sigma.T_G.Go9(this,_.id);
      _.innerStyleClass=_.styleClass+" .gt-inner";
      _.minWidth=_.minWidth+this.kR2;
      _.innerStartHTML=Sigma.T_G.innerStart(_);
      if(_.sortOrder){
        this.addSortInfo(this.Dy8(_));
      }
      if(_.separator){
        _.separator.gridId=_.gridId;
      }
      if(_.hidden){
      }
    }
    this.defaultRecord=this.defaultRecord||C;
    return this;
  },getMsg:function($){
    var _=Sigma.Msg.Grid[this.language][$];
    return _;
  },NY2:function(){
    if(this.autoSelectFirstRow&&!this.selectRowByCheck){
      this.selectFirstRow();
    }
    this.kn9();
    Sigma.$invoke(this,"onComplete",[this]);
    this.hideWaiting();
    this.Hs5++;
  },render:function($){
    if(!this.rendered){
      $=Sigma.vP3($);
      this.container=$||this.container;
      this.TP2();
      this.YY7();
      this.kn7();
      this.Yd4();
      this.YO9();
      this.Nd4();
      this.DT2();
      this.ol6();
      this.rendered=true;
    }
    return this;
  },getEl:function(){
    return this.gridDiv;
  },fo9:function(){
    var $=this;
    this.headDivHeight=this.headDiv.clientHeight;
    if(this.customHead){
      this.headDiv.style.height=this.headDivHeight-2+"px";
      Sigma.$thread(function(){
        $.headDiv.scrollTop=2;
      });
      this.headDivHeight-=2;
    }
    this.freezeHeadDiv.style.height=this.headDivHeight+"px";
    this.freezeHeadDiv.style.top=0+this.freezeFixH+"px";
    this.freezeBodyDiv.style.top=this.headDivHeight+this.freezeFixH+0+"px";
  },YY7:function(){
    var $="display:none;";
    Sigma.Const.Grid.SCROLLBAR_WIDTH=20;
    var A=this;
    A.evenRowCss=A.stripeRows?A.evenRowCss:"";
    var _=[];
    Sigma.$each(this.columnList,function(B,D){
      B.width=B.width||A.defaultColumnWidth;
      var C=""+B.width;
      if(C.indexOf("px")<1&&C.indexOf("%")<1){
        C=parseInt(C);
      }else {
        if(C.indexOf("%")>0){
        }
      }
      _[D]=[B.CLASS_PREFIX+B.styleClass+" { width:"+(C+A.kR2)+"px;"+(B.hidden?$:"")+" } ",B.CLASS_PREFIX+B.innerStyleClass+" { width:"+(C+A.oc8)+"px; } "].join("\n");
    });
    Sigma.U.CSS.Um1(_.join("\n"));
  },kn7:function(){
    var A=this;
    this.pageStateBar=Sigma.$(this.pageStateBar);
    if(this.isNative){
      this.gridDiv=Sigma.$(this.id+"_div");
    }else {
      var B=[(Sigma.isBoxModel?"gt-b-ie ":(Sigma.isSafari?"gt-b-safari ":(Sigma.isOpera?"gt-b-opera ":(Sigma.isStrict?"gt-b-strict":"")))),"gt-grid","gt-skin-"+this.skin];
      this.gridDiv=Sigma.$e("div",{id:this.id+"_div",className:B.join(" ")});
      this.container=Sigma.$(this.container);
      if(!this.container||!this.container.appendChild||!this.container.tagName||Sigma.U.getTagName(this.container)=="BODY"){
        Sigma.doc.body.appendChild(this.gridDiv);
      }else {
        if(this.replaceContainer===true){
          this.container.parentNode.insertBefore(this.gridDiv,this.container);
          Sigma.U.removeNode(this.container);
          this.container=null;
        }else {
          this.container.appendChild(this.gridDiv);
        }
      }
      this.gridDiv.innerHTML=Sigma.T_G.main(this);
    }
    this.gridDiv.hideFocus=true;
    this.gridMask=Sigma.$byId(this.id+"_mask");
    this.gridWaiting=Sigma.$(this.id+"_waiting");
    this.gridDialog=Sigma.$(this.id+"_dialog");
    this.gridDialogTitle=Sigma.$(this.id+"_dialog_title");
    this.gridDialogBody=Sigma.$(this.id+"_dialog_body");
    this.gridDiv.appendChild(this.gridEditorCache);
    this.gridFilterRSCache=this.gridFilterRSCache||Sigma.$e("table",{className:"gt-filter-rs-cache"});
    this.gridDiv.appendChild(this.gridFilterRSCache);
    this.showMask();
    this.viewport=Sigma.$(this.id+"_viewport");
    this.KN2=Sigma.$(this.id+"_toolBarBox");
    this.headDiv=Sigma.$(this.id+"_headDiv");
    this.bodyDiv=Sigma.$(this.id+"_bodyDiv");
    this.freezeView=Sigma.$(this.id+"_freezeView");
    this.freezeHeadDiv=Sigma.$(this.id+"_freeze_headDiv");
    this.freezeBodyDiv=Sigma.$(this.id+"_freeze_bodyDiv");
    this.ai8();
    this.separateLine=Sigma.$(this.id+"_separateLine");
    this.Kt9=this.toolbarPosition=="top"||this.toolbarPosition=="t"?this.toolbarHeight+(Sigma.isBoxModel?0:1):0;
    if(this.separateLine){
      this.separateLine.style.top=this.Kt9+"px";
    }
    this.columnMoveS=Sigma.$(this.id+"_columnMoveS");
    this.headerGhost=Sigma.$(this.id+"_headerGhost");
    if(this.KN2){
      this.toolBar=Sigma.$e("div",{id:this.id+"_toolBar",className:"gt-toolbar"});
      this.KN2.appendChild(this.toolBar);
    }
    var _=""+this.width,$=""+this.height;
    this.setSize(_,$,true);
    this.showWaiting();
    this.syncLeftTop();
  },syncLeftTop:function(){
    this.left=Sigma.U.getPosLeftTop(this.gridDiv);
    this.top=this.left[1];
    this.left=this.left[0];
    this.iz8=Sigma.U.getXY(this.viewport);
  },setSize:function(A,$,D){
    var C=this,B=[this.gridDiv.offsetWidth,this.gridDiv.offsetHeight];
    A=""+A;
    $=""+$;
    this.width=A;
    this.height=$;
    if(A.toLowerCase()=="auto"){
      this.width=B[0]+"px";
    }else {
      if(A.indexOf("%")<1&&A.indexOf("px")<1){
        this.width=Sigma.U.parseInt(A)+"px";
      }
    }
    if($.toLowerCase()=="auto"){
      this.height=B[1]+"px";
    }else {
      if($.indexOf("%")<1&&$.indexOf("px")<1){
        this.height=Sigma.U.parseInt($)+"px";
      }
    }
    var _=false;
    if(($.indexOf("%")>1||A.indexOf("%")>1)&&this.monitorResize){
      _=true;
    }
    if(_){
      if(Sigma.isIE){
        this.gridDiv.style.overflowY="hidden";
      }else {
        if(Sigma.isOpera){
          this.gridDiv.style.overflow="hidden";
        }
      }
    }
    C.Ao9(D);
  },Ao9:function(A){
    this.gridMask.style.width=this.width;
    this.gridMask.style.height=this.height;
    this.gridDiv.style.width=this.width;
    this.gridDiv.style.height=this.height;
    var B=(""+this.height).indexOf("%")>0?this.gridDiv.clientHeight:parseInt(this.height),_=0-(Sigma.isBoxModel?2:3);
    this.bodyDiv.style.height=B-(this.headDivHeight+this.toolbarHeight)+_+"px";
    if(Sigma.isOpera){
      var $=this.gridDiv.clientWidth+_+"px";
      this.viewport.style.width=$;
      if(this.KN2){
        this.KN2.style.width=$;
      }
    }
    if(this.freezeBodyDiv){
      this.freezeBodyDiv.style.height=this.bodyDiv.clientHeight+"px";
    }
    if(A!==true){
      this.onResize();
    }
  },Yd4:function(){
    Sigma.U.createElementFromHTML(Sigma.T_G.formIFrame(this),Sigma.doc.body);
    this.gridForm=Sigma.$(this.id+"_export_form");
    this.gridFormTextarea=Sigma.$(this.id+"_export_form_textarea");
    this.gridFormFileName=Sigma.$(this.id+"_export_filename");
    this.gridFormExportType=Sigma.$(this.id+"_export_exporttype");
    this.gridIFrame=Sigma.$(this.id+"_export_iframe");
  },YO9:function(){
    this.gridGhost=Sigma.$e("div",{id:this.id+"_gridGhost",className:"gt-grid-ghost-rect"});
    this.gridGhost.style.top=this.top+"px";
    this.gridGhost.style.left=this.left+"px";
    this.gridGhost.style.width=this.gridMask.style.width;
    this.gridGhost.style.height=this.gridMask.style.height;
    Sigma.doc.body.appendChild(this.gridGhost);
  },orphanTr:function(){
    if(!this.renderHiddenColumn){
      var $=Sigma.doc.createElement("tr");
      $.className="gt-orphan-tr";
      return $;
    }
  }(),Ke3:function(){
    var $=this,_=$.headTbody.rows[0];
    if(!this.renderHiddenColumn&&_){
      Sigma.$each(this.columnList,function(A,B){
        var C=A.headCell&&A.headCell.parentNode==$.orphanTr;
        if(A.hidden&&C){
          $.orphanTr.appendChild(A.headCell);
        }else {
          if(C){
            var D=_.cell[B];
            if(D){
              _.insertBefore(A.headCell,D);
            }
          }
        }
      },this);
    }
  },ai8:function(){
    var _=this,A;
    if(this.customHead){
      this.renderHiddenColumn=true;
      Sigma.U.removeNode(this.orphanTr);
      this.orphanTr=null;
      this.Me3();
    }else {
      this.headTable=Sigma.$e("table",{id:this.id+"_headTable",className:"gt-head-table",cellSpacing:"0",cellPadding:"0",border:"0"});
      this.headTbody=Sigma.$e("tbody");
      this.headTable.appendChild(this.headTbody);
      A=Sigma.$e("tr",{className:"gt-hd-row"});
      this.headTbody.appendChild(A);
      Sigma.$each(this.columnList,function($,B){
        var C=Sigma.T_G.Do0(_,$);
        A.appendChild(C);
        $.headCell=C;
        Sigma.Grid.TV9(_,$);
      },this);
      this.Ke3();
    }
    this.headTable.style.marginRight=100+"px";
    var $=this.headDiv.firstChild?String(Sigma.U.getTagName(this.headDiv.firstChild)):null;
    if($=="DIV"||$=="SPAN"){
      this.headDiv.firstChild.appendChild(this.headTable);
    }else {
      this.headDiv.appendChild(this.headTable);
    }
    this.headFirstRow=this.headTbody.rows[0];
    this.freezeHeadTable=Sigma.$e("table",{id:this.headTable.id+"_freeze",className:"gt-head-table",cellSpacing:"0",cellPadding:"0",border:"0"});
    this.freezeHeadTable.appendChild(Sigma.$e("tbody"));
    this.freezeHeadTable.style.height="100%";
    this.freezeHeadDiv.appendChild(this.freezeHeadTable);
    this.fo9();
  },ol6:function(){
    var $=Sigma.$(this.id+"_bodyTable");
    if($){
      this.gridTable=$;
      this.gridTbodyList.push($.tBodies[0]);
      this.bodyFirstRow=this.Ih5();
    }else {
      this.ei7();
    }
  },Me3:function(){
    var A=this,D;
    if(Sigma.$type(this.customHead,"string")){
      if(this.customHead.indexOf("<table")===0){
        Sigma.U.YC2.innerHTML=this.customHead;
        this.customHead=Sigma.U.YC2.firstChild;
      }else {
        this.customHead=Sigma.$(this.customHead);
      }
    }
    this.customHead.style.display="";
    this.headTable=Sigma.$e(this.customHead,{id:this.id+"_headTable",className:"gt-head-table",cellSpacing:"0",cellPadding:"0",border:"0"});
    this.headTbody=this.headTable.tBodies[0];
    for(var G=0;G<this.headTbody.rows.length;G++){
      var F=this.headTbody.rows[G];
      F.className="gt-hd-row";
      for(var C=0;C<F.cells.length;C++){
        var _=F.cells[C],E=_.innerHTML,B=_.getAttribute("columnId");
        if(B){
          _.columnId=B;
          var $=this.columnMap[B];
          if(String(_.getAttribute("resizable")).toLowerCase()=="false"){
            $.resizable=false;
          }
          if(String(_.getAttribute("sortable")).toLowerCase()=="false"){
            $.sortable=false;
          }
          if(_.colSpan<2){
            _.className=$.styleClass;
          }
          $.headCell=_;
        }
        _.innerHTML=["<div class=\"gt-inner",_.rowSpan<2?"":" gt-inner-tall2","\" unselectable=\"on\" >","<span>",E,"</span>",B?Sigma.T_G.hdToolHTML:"","</div>"].join("");
      }
    }
    D=Sigma.$e("tr",{className:"gt-hd-row"+(this.customHead?" gt-hd-hidden":"")});
    Sigma.$each(this.columnList,function($,_){
      var B=Sigma.T_G.Do0(A,$,true);
      D.appendChild(B);
      Sigma.Grid.TV9(A,$);
    });
    this.headTbody.insertBefore(D,this.headTbody.rows[0]);
  },getColumn:function($){
    if(Sigma.U.isNumber($)&&$>=0){
      return this.columnList[$];
    }else {
      if(Sigma.U.getTagName($)=="TD"){
        return this.columnList[Sigma.U.getCellIndex($)];
      }else {
        if(Sigma.$type($)=="object"){
          return $;
        }else {
          return this.columnMap[$];
        }
      }
    }
  },getDisplayColumns:function(A){
    var B=[];
    for(var _=0;_<this.columnList.length;_++){
      var $=this.columnList[_];
      if($.hidden!==(A!==false)){
        B.push($);
      }
    }
    return B;
  },or8:function(C,_){
    C=this.Oq6(C);
    _=_||this.getRecordByRow(C);
    var B=C.getAttribute(Sigma.Const.DataSet.INDEX)/1;
    this.dataset.initValues(_,B,this.dataset);
    for(var E=0;E<C.cells.length;E++){
      var $=this.getColumn(E);
      if($!=this.activeColumn&&$.syncRefresh===false){
        continue ;
      }
      var A=C.cells[E],D=false;
      A.firstChild.innerHTML=$.renderer(_[$.fieldIndex],_,$,this,E,B);
    }
  },scrollGrid:function(I,H){
    var C=this.iB3,_=this.freezeBodyDiv.clientWidth,$=this.activeCell.offsetLeft+((Sigma.isFF2||Sigma.isFF1)?0:C),B=$+this.activeCell.offsetWidth,F=this.activeCell.offsetTop,D=F+this.activeCell.offsetHeight,A=this.bodyDiv.scrollLeft,E=this.bodyDiv.scrollTop,J=A+this.bodyDiv.clientWidth+C,G=E+this.bodyDiv.clientHeight;
    if(Sigma.$chk(I)){
      this.bodyDiv.scrollLeft=I;
    }else {
      if($<=A+_){
        this.bodyDiv.scrollLeft=$-_-(_>0?1:0);
      }else {
        if(B>=J){
          this.bodyDiv.scrollLeft=A+B-J+C;
        }
      }
    }
    if(Sigma.$chk(H)){
      this.bodyDiv.scrollTop=H;
    }else {
      if(F<=E){
        this.bodyDiv.scrollTop=F;
      }else {
        if(D>=G){
          this.bodyDiv.scrollTop=E+D-G;
        }
      }
    }
  },ko9:function($){
    this.activeCell=$=($||this.activeCell);
    this.activeRow=$?$.parentNode:null;
    this.activeColumn=this.getColumn($);
    this.activeEditor=this.activeColumn?this.activeColumn.editor:null;
    this.activeRecord=this.getRecordByRow(this.activeRow);
  },cd1:function(_){
    var $=Sigma.U.prevElement(_);
    while($&&$.offsetWidth<1){
      $=Sigma.U.prevElement($);
    }
    if(!$){
      $=Sigma.U.prevElement(_.parentNode);
      if($){
        $=$.cells[$.cells.length-1];
      }
    }
    while($&&$.offsetWidth<1){
      $=Sigma.U.prevElement($);
    }
    return $;
  },ce9:function(_){
    var $=Sigma.U.nextElement(_);
    while($&&$.offsetWidth<1){
      $=Sigma.U.nextElement($);
    }
    if(!$){
      $=Sigma.U.nextElement(_.parentNode);
      if($){
        $=$.cells[0];
      }
    }
    while($&&$.offsetWidth<1){
      $=Sigma.U.nextElement($);
    }
    return $;
  },ra8:function(D){
    var A=this.activeCell,$=null,F=D.keyCode,E=this;
    function B($){
      if($){
        E.endEdit();
        Sigma.U.stopEvent(D);
        Sigma.Grid.ii3(D,E,$.parentNode);
        E.fl6(D,$);
      }
    }
    if(F==Sigma.Const.Key.ESC){
      if(this.endEdit()===false){
        return ;
      }else {
        Sigma.U.stopEvent(D);
      }
    }else {
      if(F==Sigma.Const.Key.ENTER){
        var G=Sigma.U.getEventTarget(D);
        if(this.editing&&Sigma.U.getTagName(G)=="TEXTAREA"){
          return ;
        }
        Sigma.U.stopEvent(D);
        if(this.editing){
          if(!this.autoEditNext){
            this.endEdit();
            return ;
          }
          $=this.ce9(A);
          B($);
        }else {
          this.ko9($);
          this.startEdit();
        }
      }else {
        if(this.editing&&F==Sigma.Const.Key.TAB&&D.shiftKey){
          $=this.cd1(A);
          B($);
        }else {
          if(this.editing&&F==Sigma.Const.Key.TAB){
            $=this.ce9(A);
            B($);
          }else {
            if(A&&!this.editing){
              switch(F){
              case Sigma.Const.Key.LEFT:
              case Sigma.Const.Key.TAB:
                if(F==Sigma.Const.Key.LEFT||D.shiftKey){
                  $=this.cd1(A);
                  while(this.isGroupRow($)){
                    $=this.cd1($);
                  }
                  break ;
                }
              case Sigma.Const.Key.RIGHT:
                $=this.ce9(A);
                while(this.isGroupRow($)){
                  $=this.ce9($);
                }
                break ;
              case Sigma.Const.Key.DOWN:
                $=Sigma.U.nextElement(A.parentNode);
                while(this.isGroupRow(null,$)){
                  $=Sigma.U.nextElement($);
                }
                if($){
                  $=$.cells[Sigma.U.getCellIndex(A)];
                }
                break ;
              case Sigma.Const.Key.UP:
                $=Sigma.U.prevElement(A.parentNode);
                while(this.isGroupRow(null,$)){
                  $=Sigma.U.prevElement($);
                }
                if($){
                  $=$.cells[Sigma.U.getCellIndex(A)];
                }
                break ;
              }
              if($){
                Sigma.U.stopEvent(D);
                var C=A.parentNode,_=$.parentNode;
                this.Td2(_,D);
                Sigma.Grid.ii3(D,this,_);
                this.Kr5(D,$);
              }
            }
          }
        }
      }
    }
  },startEdit:function(){
    if(this.activeCell&&this.activeEditor&&(this.activeColumn.editable||this.isInsertRow(this.activeRow))&&Sigma.$invoke(this.activeColumn,"beforeEdit",[this.activeValue,this.activeRecord,this.activeColumn,this])!==false&&Sigma.$invoke(this,"beforeEdit",[this.activeValue,this.activeRecord,this.activeColumn,this])!==false&&!this.isDelRow(this.activeRow)&&!this.readOnly){
      this.editing=true;
      this.VM2(this.activeValue,this.activeRecord);
    }
  },endEdit:function(){
    if(this.activeEditor&&this.activeEditor.locked===true||(this.activeDialog!=this.activeEditor)&&this.activeDialog&&!this.activeDialog.hidden){
      return false;
    }
    if(this.activeCell&&this.activeEditor&&(this.activeColumn.editable||this.isInsertRow(this.activeRow))){
      this.EB2();
      this.editing=false;
      this.Tp0(null,this.activeCell);
    }
  },VM2:function(B,$){
    var A=this.activeCell,_=this.bodyDiv,C=this.iB3;
    if(this.activeColumn.frozen){
      A=this.iy7(this.activeCell)[1];
      _=this.freezeBodyDiv;
      C=0;
    }
    if(this.activeEditor&&this.activeEditor instanceof Sigma.Dialog){
    }else {
      _.appendChild(this.activeEditor.vP3());
    }
    this.activeEditor.show();
    this.activeEditor.setValue(B,$);
    if(this.activeEditor!==this.activeDialog){
    	//桑志豪修改
    	//this.activeEditor.vk2(((Sigma.isFF2||Sigma.isFF1)?0:C)+A.offsetLeft,A.offsetTop);
      this.activeEditor.vk2(((Sigma.isFF2||Sigma.isFF1)?C:C)+A.offsetLeft,A.offsetTop);
      this.activeEditor.setSize(A.offsetWidth,A.offsetHeight);
    }
    this.activeEditor.active();
  },validValue:function($,B,_,A){
    if($.editor){
      var C=$.editor.yb4(B,_,$,this);
      if(C!==true){
        this.Vc5(B,C,A,$);
      }
      return C;
    }
    return true;
  },EB2:function(){
    if(this.editing){
      var B=this.activeRow,_=this.activeValue,$=this.activeEditor.parseValue(this.activeEditor.getValue()),A=this.validValue(this.activeColumn,$,this.activeRecord,this.activeCell);
      if(A===true&&String(this.activeValue)!==String($)){
        this.updateRecordField(this.activeCell,$);
        this.or8(B,this.activeRecord);
        this.dirty(this.activeCell);
        this.activeValue=$;
      }
      return;
      Sigma.$invoke(this.activeColumn,"afterEdit",[$,_,this.activeRecord,this.activeColumn,this]);
      Sigma.$invoke(this,"afterEdit",[$,_,this.activeRecord,this.activeColumn,this]);
    }
    if(this.activeEditor&&this.activeEditor instanceof Sigma.Dialog){
    }else {
      this.gridEditorCache.appendChild(this.activeEditor.vP3());
    }
    this.activeEditor.hide();
  },Vc5:function(B,C,A,$){
    var _=this.iy7(A);
    Sigma.U.addClass(_[0],"gt-cell-vaildfailed");
    Sigma.U.addClass(_[1],"gt-cell-vaildfailed");
    C=[].concat(C);
  // alert(C.join("\n")+"\n\n"+B);
    Sigma.$thread(function(){
      Sigma.U.removeClass(_[0],"gt-cell-vaildfailed");
      Sigma.U.removeClass(_[1],"gt-cell-vaildfailed");
    },1500);
  },Kl8:function(C){
    var _=C,B=C.rowIndex,$=!_.id?this.gridTbodyList[0]:this.freezeBodyTable.tBodies[0],A=$?$.rows[B]:null;
    if(!A&&$&&$.parentNode.tFoot){
      A=$.parentNode.tFoot.rows[B-$.rows.length];
    }
    return _.id?[_,A,B]:[A,_,B];
  },iy7:function(_,E){
    var A=_,D=Sigma.U.getCellIndex(_),$=null,B=0,C=_.parentNode;
    E=E||this.Kl8(C);
    if(E[1]==C){
      B=D-(this.showIndexColumn?1:1);
      return [E[0]?E[0].cells[B]:null,_,D];
    }
    B=D+(this.showIndexColumn?1:1);
    return [_,E[1]?E[1].cells[B]:null,D];
  },Tp0:function(C,A){
    if(!C&&!A){
      return ;
    }
    C=C||A.parentNode;
    var D=this.Kl8(C),B=D[1];
    C=D[0];
    if(B&&C){
      B.className=C.className;
      Sigma.U.removeClass(B,"gt-row-over");
    }
    if(A){
      var $=this.iy7(A,D),_=$[1];
      A=$[0];
      if(_&&A){
        _.className=A.className;
        if(_.getElementsByTagName("input")[0]){
          A.innerHTML=_.innerHTML;
        }else {
          _.innerHTML=A.innerHTML;
        }
      }
    }
  },Kr5:function(_,A){
    A=A||Sigma.U.Lu8("td",null,_);
    if(A&&!this.isGroupRow(A)){
      this.closeGridMenu();
      var $=A!=this.activeCell;
      if($){
        Sigma.U.removeClass(this.activeCell,"gt-cell-actived"+(this.activeEditor?"-editable":""));
        this.Tp0(null,this.activeCell);
      }
      this.ko9(A);
      $&&Sigma.U.addClass(this.activeCell,"gt-cell-actived"+(this.activeEditor?"-editable":""));
      if(this.activeColumn&&this.activeRecord){
        this.activeValue=this.activeRecord[this.activeColumn.fieldIndex];
      }
      this.scrollGrid();
      this.Tp0(null,this.activeCell);
    }else {
      this.lR7();
    }
    return A;
  },fl6:function(_,$,A){
    if(this.rowNum<1){
      return ;
    }
    var B=this.activeCell;
    this.Kr5(_,$);
    if(this.activeEditor&&(this.clickStartEdit||B&&B==this.activeCell)){
      Sigma.U.stopEvent(_);
      this.ko9($);
      this.startEdit();
    }
  },getLastSelectRow:function(){
    return this.selectedRows[this.selectedRows.length-1];
  },eH5:function($){
    Sigma.$invoke(this,"onCellSelect",[this.activeValue,this.activeRecord,$.cell,$.row,$.colNo,$.rowNo,$.column,this]);
  },Td2:function(C,A){
    if(!C||Sigma.U.hasClass(C.cells[0],"gt-nodata-cell")){
      return ;
    }
    if(this.multiSelect&&A.shiftKey){
      var $=this.getLastSelectRow();
      if($&&$.parentNode==C.parentNode){
        this.unselectAllRow();
        var _=$.rowIndex>C.rowIndex?"prevElement":"nextElement";
        while($&&$!=C){
          this.selectRow($);
          $=Sigma.U[_]($);
        }
        this.selectRow(C);
        return C;
      }
    }
    var B=A.ctrlKey;
    if(Sigma.Grid.isSelectedRow(C)){
      if(this.multiSelect&&B!==true&&this.selectedRows.length>1){
        this.unselectAllRow();
        this.selectRow(C);
      }else {
        if(B){
          this.unselectRow(C);
        }
      }
    }else {
      if(!this.multiSelect||B!==true){
        this.unselectAllRow();
      }
      this.selectRow(C);
    }
    return C;
  },ud3:function(_,A,$){
    Sigma.$invoke(this,"onCellClick",[$.value,$.record,$.cell,$.row,$.colNo,$.rowNo,$.column,this,_]);
  },Fb9:function(_,A,$){
    Sigma.$invoke(this,"onCellDblClick",[$.value,$.record,$.cell,$.row,$.colNo,$.rowNo,$.column,this,_]);
  },am8:function(_,A,$){
    Sigma.$invoke(this,"onRowClick",[$.value,$.record,$.cell,$.row,$.colNo,$.rowNo,$.column,this,_]);
  },bt8:function(_,A,$){
    Sigma.$invoke(this,"onRowDblClick",[$.value,$.record,$.cell,$.row,$.colNo,$.rowNo,$.column,this,_]);
  },Mn1:function(A,D,_,$){
    this.endEdit();
    this.lH8();
    var C=$.cell,F;
    if(!C||C==_){
      return ;
    }
    C=this.iy7(C)[0];
    if(C){
      F=C.parentNode;
    }else {
      F=this.Kl8($.row)[0];
    }
    var E=$.eventTarget,B=(Sigma.U.getTagName(E)=="INPUT"&&E.className=="gt-f-check");
    if(B){
      Sigma.Tc5(E,this);
    }
    this.eH5($);
    if(!this.selectRowByCheck){
      this.Td2(F,A);
    }
    if(D){
      this.Fb9(A,C,$);
      this.bt8(A,F,$);
    }else {
      this.ud3(A,C,$);
      this.am8(A,F,$);
    }
    if(!Sigma.U.hasClass(C,"gt-index-col")){
      this.fl6(A,C,D);
    }
    this.Tp0(null,C);
  },clickCount:0,clickInterval:500,Do9:function(A,B,_){
    var C=this;
    if(_==C.bodyDiv){
      if(B=="scroll"){
        C.lH8();
        C.closeGridMenu();
        C.syncScroll();
        return ;
      }
    }else {
      if(_==C.freezeBodyDiv){
      }
    }
    var $=this.getEventTargets(A);
    if(C.lightOverRow&&B=="mousemove"){
      Sigma.Grid.Hi5(A,C,_);
    }
    switch(B){
    case "contextmenu":
    	 $.lc4(_,$);
    case "dblclick":
      this.clickCount=0;
      return C.Mn1(A,true,_,$);
    case "click":
      Sigma.$thread(function(){
        C.clickCount=0;
      },C.clickInterval);
      this.clickCount++;
      return C.Mn1(A,false,_,$);
    case "mousemove":
    default:
      B=(B.indexOf("on")!==0?"on"+B:B).toLowerCase();
      return Sigma.$invoke(C,B,[A,C,$,B,_]);
    }
  },Gs5:function($,_){
    var A=this;
    Sigma.U.addEvent($,_,function(B){
      A.Do9(B,_,$);
    });
  },getEventTargets:function(A,_){
    _=_||Sigma.U.getEventTarget(A);
    var F=null,G=-1,E=-1,D=null,$=null,C=null,B=Sigma.U.Lu8("td",_,A);
    if(B){
      F=B.parentNode;
      G=Sigma.U.getCellIndex(B);
      E=F.rowIndex;
      D=this.columnList[G];
      $=this.getRecordByRow(F)||{};
      C=$[D.fieldIndex];
    }
    return {cell:B,row:F,colNo:G,rowNo:E,column:D,record:$,value:C,eventTarget:_};
  },DT2:function(){
    var _=this;
    Sigma.ev7();
    if(_.monitorResize){
      Sigma.U.addEvent(window,"resize",function($){
        _.Ao9();
      });
      _.hasResizeListener=true;
    }
    Sigma.U.addEvent(_.gridDiv,"mousedown",function($){
      _.lH8();
    });
    _.Gs5(_.bodyDiv,"scroll");
    _.Gs5(_.bodyDiv,"click");
    _.Gs5(_.bodyDiv,"dblclick");
    _.Gs5(_.bodyDiv,"contextmenu");
    _.Gs5(_.freezeBodyDiv,"click");
    _.Gs5(_.freezeBodyDiv,"dblclick");
    Sigma.U.addEvent(_.headDiv,"selectstart",function($){
      Sigma.U.stopEvent($);
      return false;
    });
    _.Gs5(_.bodyDiv,"mouseover");
    _.Gs5(_.bodyDiv,"mouseout");
    _.Gs5(_.bodyDiv,"mousemove");
    _.Gs5(_.freezeBodyDiv,"mousemove");
    function $($){
      $=$||window.event;
      var A=Sigma.U.Lu8("td",null,$);
      if(A){
        Sigma.U.addClass(A,"gt-hd-row-over");
      }
      if(_.lastOverHdCell!=A){
        Sigma.U.removeClass(_.lastOverHdCell,"gt-hd-row-over");
      }
      _.lastOverHdCell=A;
    }
    Sigma.U.addEvent(_.headTable,"mousemove",$);
    Sigma.U.addEvent(_.freezeHeadTable,"mousemove",$);
  },showCellToolTip:function(_,$){
    if(!this.toolTipDiv){
      this.toolTipDiv=Sigma.$e("div",{className:"gt-cell-tooltip gt-breakline"});
      this.toolTipDiv.style.display="none";
    }
    this.toolTipDiv.innerHTML=Sigma.$getText(_);
    this.gridDiv.appendChild(this.toolTipDiv);
    this.toolTipDiv.style.left=_.offsetLeft+this.bodyDiv.offsetLeft-this.bodyDiv.scrollLeft+((Sigma.isFF2||Sigma.isFF1)?0:this.iB3)+"px";
    this.toolTipDiv.style.top=_.offsetTop+_.offsetHeight+this.bodyDiv.offsetTop-this.bodyDiv.scrollTop+this.Kt9+(Sigma.isFF?1:0)+"px";
    $&&(this.toolTipDiv.style.width=$+"px");
    this.toolTipDiv.style.display="block";
  },hideCellToolTip:function(){
    if(this.toolTipDiv){
      this.toolTipDiv.style.display="none";
      this.gridEditorCache.appendChild(this.toolTipDiv);
      this.toolTipDiv.innerHTML="";
    }
  },addParameters:function(_,$){
    this.parameters=Sigma.U.add2Map(_,$,this.parameters);
  },setParameters:function($){
    this.parameters=$;
  },cleanParameters:function(){
    this.parameters={};
  },setQueryParameters:function($){
    this.queryParameters=$;
  },cleanQueryParameters:function(){
    this.queryParameters={};
  },addQueryParameter:function(_,$){
    this.queryParameters=Sigma.U.add2Map(_,$,this.queryParameters);
  },removeQueryParameter:function(_){
    var $=this.queryParameters[_];
    this.queryParameters[_]=undefined;
    delete this.queryParameters[_];
    return $;
  },exceptionHandler:function(_,$){
    alert($+"\n\n"+_);
  },getInsertedRecords:function(){
    return Sigma.$array(this.dataset.insertedRecords);
  },setInsertedRecords:function($){//这个方法桑志豪加入的
	  this.dataset.insertedRecords=$;
  },getUpdatedRecords:function(){
    return Sigma.$array(this.dataset.updatedRecords);
  },getUpdatedFields:function(){
    return Sigma.$array(this.dataset.updatedFields);
  },getDeletedRecords:function(){
    return Sigma.$array(this.dataset.deletedRecords);
  },getColumnInfo:function(){
    var $=[];
    for(var B=0;B<this.columnList.length;B++){
    	//桑志豪:数据导出扩展数字number:A.number
      var A=this.columnList[B],_={id:A.id,header:A.header||A.title,fieldName:A.fieldName,fieldIndex:A.fieldIndex,sortOrder:A.sortOrder,hidden:A.hidden,exportable:A.exportable,printable:A.printable,number:A.number};
      $.push(_);
    }
    return $;
  },getSaveParam:function($){
    $=$||{};
    $[this.CONST.fieldsName]=this.dataset.fieldsName;
    $[this.CONST.recordType]=this.dataset.getRecordType();
    $[this.CONST.parameters]=this.parameters;
    this.submitUpdatedFields&&($[this.CONST.updatedFields]=this.getUpdatedFields());
    return $;
  },getLoadParam:function($){
    $=$||{};
    $[this.CONST.recordType]=this.dataset.getRecordType();
    $[this.CONST.pageInfo]=this.getPageInfo(true);
    this.submitColumnInfo&&($[this.CONST.columnInfo]=this.getColumnInfo());
    $[this.CONST.sortInfo]=this.Md2();
    $[this.CONST.filterInfo]=this.Yx0();
    $[this.CONST.remotePaging]=this.remotePaging;
    $[this.CONST.parameters]=this.parameters;
    if(this.recount){
      $[this.CONST.pageInfo].totalRowNum=-1;
    }
    return $;
  },La6:function(F,_,E,D,B){
    var C=this;
    C.requesting=true;
    var A=_[C.CONST.action];
    if(F){
      try{
        C.ajax=new Sigma.Ajax(F);
        C.ajax.encoding=C.encoding||C.ajax.encoding;
        C.ajax.method=C.ajaxMethod||C.ajax.method;
        C.ajax.mimeType=C.mimeType||C.ajax.mimeType;
        C.ajax.jsonParamName=C.jsonParamName||C.ajax.jsonParamName;
        C.ajax.onSuccess=D||Sigma.$empty;
        C.ajax.onFailure=B||Sigma.$empty;
        C.ajax.setQueryParameters(C.queryParameters);
        C.ajax.send({data:_});
      }
      catch($){
        B({status:"Exception "+$.message},$);
      }
    }else {
      B({status:"url is null"});
    }
  },save:function(N){
    if(this.endEdit()===false){
      return ;
    }
    var E=this.saveURL,I=this.getInsertedRecords(),J=this.getUpdatedRecords(),$=this.getDeletedRecords(),A=(I&&I.length>0||J&&J.length>0||$&&$.length>0);
    if(!N&&!A){
     // alert(this.getMsg("NO_MODIFIED"));
    }else {
      var F=this.gridTable.tFoot?this.gridTable.tFoot.rows:[];
      for(var G=0,D=I.length;G<D;G++){
        var _=I[G];
        for(var K=0;K<this.columnList.length;K++){
          var H=this.columnList[K];
          if(H.editor){
            var L=_[H.fieldIndex],C=F[G]?F[G].cells[H.colIndex]:null;
            if(this.validValue(H,L,_,C)!==true){
              return false;
            }
          }
        }
      }
      var B=this.getSaveParam();
      B[this.CONST.action]="save";
      B[this.CONST.insertedRecords]=I;
      B[this.CONST.updatedRecords]=J;
      B[this.CONST.deletedRecords]=$;
      if(Sigma.$invoke(this,"beforeSave",[B,this])!==false){
        this.showWaiting();
        var M=this;
        return this.La6(E,B,"json",function($){
          return function(_){
            M.sa1(_,B,$);
          };
        }(N),function(_,$){
          var A={};
          A[M.CONST.exception]=" XMLHttpRequest Status : "+_.status;
          M.saveFailure(A,$);
          M.hideWaiting();
        });
      }
    }
    if(N===true){
      this.load();
    }
    return false;
  },load:function(B,D){
    var C=this,E=this.loadURL,A=(!this.autoLoad&&!this.rendered);
    if(A){
      C.hideWaiting();
      C.hideFreezeZone();
      return ;
    }
    this.remotePaging=this.remotePaging===false?false:!!E;
    var _=this.getLoadParam();
    if(B===true){
      _[this.CONST.pageInfo].totalRowNum=-1;
    }
    _[this.CONST.action]="load";
    if(Sigma.$invoke(this,"beforeLoad",[_,this])!==false){
      if(!E||(D!==true&&this.remotePaging===false&&!this.isFirstLoad)){
        C.requesting=true;
        C.ir1(function(){
          var _={};
          _[C.dataRoot]=C.dataset.data||[];
          var A=C.getPageInfo(),$=C.dataset.getSize();
          A.totalRowNum=$>0?$:0;
          _[C.CONST.pageInfo]=A;
          return _;
        }(C),_);
        return ;
      }
      this.showWaiting();
      var $=this.La6(E,_,"json",function($){
        C.ir1($,_);
      },function(_,$){
        var A={};
        A[C.CONST.exception]=" XMLHttpRequest Status : "+_.status;
        C.loadFailure(A,$);
        C.hideWaiting();
      });
      this.isFirstLoad=false;
      return $;
    }else {
      C.hideWaiting();
    }
    return false;
  },query:function($){
    this.setQueryParameters($);
    this.lastAction="query";
    this.reload(true,true);
  },ei7:function(_,$){
    Sigma.$chk(_)&&(this.getPageInfo().pageNum=_);
    if(this.autoSaveOnNav){
      this.save(true);
    }else {
      this.load();
    }
  },reload:function($,_){
    if(_!==false||!this.dataset||this.dataset.getSize()<0){
      this.load($,true);
    }else {
      this.refresh();
    }
  },refresh:function(_){
    if(this.dataset&&_){
      this.dataset.HF1(_);
    }
    var A=this,B=A.scrollLeft,$=A.scrollTop;
    if(A.remotePaging===false){
      A.dataset.startRecordNo=(A.getPageInfo().startRowNum||1)-1;
    }
    function C(){
      if(Sigma.$invoke(A,"beforeRefresh",[A])!==false){
        A.ga5();
        !A.remoteSort&&A.qP4();
        A.mm8();
        A.autoUpdateSortState&&A.updateSortState();
        A.sorting=false;
        A.autoUpdateEditState&&A.updateEditState();
        A.updateCheckState();
        A.autoUpdateFreezeState&&A.qb8();
        A.refreshToolBar();
        A.syncScroll(B,$);
        Sigma.$invoke(A,"afterRefresh",[A]);
        A.NY2();
      }
    }
    Sigma.$thread(C);
  },sa1:function($,_,D){
    var B=this.CA2($,_);
    if(this.requesting){
      var A=Sigma.$extend({},B);
      this.requesting=false;
      var C=!(A[this.CONST.success]===false||A[this.CONST.success]==="false");
      if(C){
        this.saveSuccess(A,D);
      }else {
        this.saveFailure(A);
      }
      this.hideWaiting();
      Sigma.$invoke(this,"afterSave",[A,C,this]);
    }
  },saveSuccess:function($,_){
    this.dataset.cleanModifiedData(true);
    if(this.reloadAfterSave||this.autoSaveOnNav&&_){
      if(this.recountAfterSave){
        this.getPageInfo().totalRowNum=-1;
      }else {
        if($[this.CONST.pageInfo]){
          this.getPageInfo().totalRowNum=$[$.CONST.pageInfo].totalRowNum||this.getPageInfo().totalRowNum;
        }
      }
      this.reload();
    }
  },loadSuccess:function($){
    this.setContent($);
  },cleanContent:function(){
    this.setContent({data:[],pageInfo:{pageNum:1,totalPageNum:1,totalRowNum:0,startRowNum:0}});
  },setContent:function(_){
    var $=this.getPageInfo();
    if(Sigma.$type(_,"array")){
      _[this.dataRoot]=_;
    }else {
      _[this.CONST.pageInfo]=_[this.dataPageInfo||this.CONST.pageInfo];
      if(_[this.CONST.recordType]){
        this.dataset.oh6(_[this.CONST.recordType]);
      }
      if(_[this.CONST.pageInfo]){
        Sigma.$extend($,_[this.CONST.pageInfo]);
      }
      $.totalRowNum=_.totalRowNum||$.totalRowNum;
    }
    if(_[this.dataRoot]&&Sigma.$invoke(this,"beforeDatasetUpdate",[_[this.dataRoot]])!==false){
      $.totalRowNum=$.totalRowNum||_[this.dataRoot].length;
      this.refresh(_[this.dataRoot]);
    }else {
      this.refresh();
    }
  },ir1:function($,_){
    var B=this.CA2($,_);
    if(this.requesting){
      var A=Sigma.$extend({},B);
      if(A[this.CONST.success]===false||A[this.CONST.success]==="false"){
        this.loadFailure(A);
        this.hideWaiting();
      }else {
        this.loadSuccess(A);
      }
      this.requesting=false;
    }
  },CA2:function(response,reqParam,action){
    action=action||reqParam[this.CONST.action];
    response=Sigma.$invoke(this,action+"ResponseHandler",[response,reqParam])||response;
    if(!response||Sigma.$type(response,"string","number")){
      response={text:response};
    }
    var respT=null;
    try{
      respT=response.text?eval("("+response.text+")"):response;
    }
    catch(e){
      respT={text:response.text};
      respT[this.CONST.exception]=respT.text;
    }
    if(respT[this.CONST.exception]){
      respT[this.CONST.success]=false;
    }
    return respT;
  },exportGrid:function(F,C,G,A,B){
    var D=this;
    if(Sigma.$invoke(D,"beforeExport",[F,C,G,A,B,D])!==false){
      try{
        F=F||this.exportType;
        C=C||this.exportFileName;
        G=G||this.exportURL;
        A=A||this.jsonParamName||(this.ajax?this.ajax.jsonParamName:Sigma.AjaxDefault.paramName);
        B="export";
        if(this.html2pdf&&F=="pdf"){
          this.gridFormTextarea.name="__gt_html";
          var E=["<style type=\"text/css\">",this.rg5("exportable"),"</style>"];
          this.gridFormTextarea.value=E.join("\n")+"\n"+this.Nv0(true);
        }else {
          var _=this.getLoadParam();
          _[this.CONST.action]=B;
          _[this.CONST.exportType]=F;
          _[this.CONST.exportFileName]=C;
          this.gridFormTextarea.name=A;
          this.gridFormTextarea.value=Sigma.$json(_);
        }
        this.gridFormFileName.value=C;
        this.gridFormExportType.value=F;
        this.gridForm.action=G;
        G&&(this.gridForm.submit());
        this.gridFormTextarea.value="";
      }
      catch($){
        this.exportFailure({type:F,fileName:C},$);
      }
    }
  },loadFailure:function(_,$){
    var A=_[this.CONST.exception]||($?$.message:undefined);
    alert(" LOAD Failed! "+"\n Exception : \n"+A);
  },saveFailure:function(_,$){
    //var A=_[this.CONST.exception]||($?$.message:undefined);
    //alert(" SAVE Failed! "+"\n Exception : \n"+A);
  },exportFailure:function(_,$){
    var A=_[this.CONST.exception]||($?($.message||""):"");
    alert(" Export "+_.type+" ( "+_.fileName+" ) Failed! "+"\n Exception : \n"+A);
  },updateRecordField:function(B,A){
    var $=this.getColumn(B);
    if($){
      var _=this.getRecordByRow(B.parentNode);
      return this.update(_,$.fieldName,A);
    }
    return false;
  },update:function($,A,_){
    if(Sigma.$invoke(this,"beforeUpdate",[$,A,_])!==false){
      this.dataset.updateRecord($,A,_);
      return true;
    }
  },cloneDefaultRecord:function(){
    var $=this.defaultRecord;
    if(Sigma.$type($,"function")){
      $=$(this,this.dataset,this.dataset.getSize());
    }
    return Sigma.$clone($);
  },add:function($,_){
    if(this.readOnly){
      return ;
    }
    this.Nm9($,_);
  },Nm9:function(_,F){
    _=_||this.cloneDefaultRecord()||(this.dataset.getRecordType()=="array"?[]:{});
    if(Sigma.$invoke(this,"beforeInsert",[_])!==false){
      this.dataset.Qh3(_);
      _[Sigma.Const.DataSet.NOT_VAILD]=true;
      var D=this.rowKey+this.getUniqueField(_);
      _[Sigma.Const.DataSet.ROW_KEY]=D;
      var B=this.qh9(_);
      this.bodyDiv.scrollTop=this.bodyDiv.scrollHeight;
      this.rowNum++;
      if(F!==false){
        var C=0,A=-1;
        for(C=0;C<this.columnList.length;C++){
          var $=this.columnList[C];
          if(A<0&&!$.hidden&&$.editor){
            A=C;
          }
          if($.frozen&&B[1]){
            var G=B[0].cells[C].cloneNode(true);
            B[1].appendChild(G);
          }
        }
        var E=B[0].cells[A];
      }
      this.syncScroll();
    }
    this.kn9();
  },del:function($){
    if(this.readOnly||this.endEdit()===false){
      return ;
    }
    this.delRow($);
  },delRow:function(B){
    var D=[].concat(B||this.selectedRows);
    for(var C=0;C<D.length;C++){
      B=D[C];
      var _=this.getRecordByRow(B);
      if(B!=this.activeRow){
        this.unselectRow(B);
      }
      if(!_){
        continue ;
      }
      if(this.isInsertRow(B)){
        if(this.activeCell&&this.activeRow==B){
          this.lR7();
        }
        var $=this.Kl8(B);
        Sigma.U.removeNode($[0],$[1]);
        this.dataset.Ie6(_);
        this.kn9();
        continue ;
      }
      if(Sigma.$invoke(this,"beforeDelete",[_,B,this])!==false){
        var A=this.dataset.Yr3(_);
        if(!A){
          this.dataset.Ie6(_);
          Sigma.U.addClass(B,"gt-row-del");
        }else {
          this.dataset.undeleteRecord(_);
          Sigma.U.removeClass(B,"gt-row-del");
        }
        this.Tp0(B);
      }
    }
  },qh9:function($){
    var A,C,D=this.colNum,_=Sigma.T_G.rowStart(this,this.rowNum);
    if(!this.gridTable.tFoot){
      this.gridTable.appendChild(Sigma.$e("tfoot"));
    }
    if(!this.freezeBodyTable.tFoot){
      this.freezeBodyTable.appendChild(Sigma.$e("tfoot"));
    }
    A=Sigma.U.createTrFromHTML(this.eX0(_,$,this.rowNum,D),this.gridTable.tFoot);
    if(this.showIndexColumn){
      C=Sigma.U.createTrFromHTML(this.sx4(_,$,this.rowNum,D),this.freezeBodyTable.tFoot);
    }else {
      C=Sigma.U.createTrFromHTML(_+"</tr>",this.freezeBodyTable.tFoot);
      C.appendChild(Sigma.T_G.freezeBodyCell(this,10,null));
    }
    Sigma.U.addClass(A,"gt-row-new");
    Sigma.U.addClass(C,"gt-row-new");
    var B=$[Sigma.Const.DataSet.ROW_KEY];
    A.id=B;
    A.setAttribute(Sigma.Const.DataSet.INDEX,$[Sigma.Const.DataSet.SN_FIELD]);
    return [A,C];
  },R_6:function(){
    var $=this.ew1().length>0,_=!!(this.gridTable.tFoot&&this.gridTable.tFoot.rows.length>0);
    return $||_;
  },kn9:function(){
    if(!this.R_6()){
      var _=["<tr class=\"gt-row gt-row-empty\" >"];
      for(var $=0;$<this.colNum;$++){
        _.push(Sigma.T_G.cell(this.columnList[$],"&#160;"));
      }
      _.push(Sigma.T_G.rowEndHTML);
      Sigma.U.createTrFromHTML(_.join(""),this.gridTbodyList[0]);
    }else {
      var A=this.Ih5();
      if(this.isEmptyRow(A)){
        Sigma.U.removeNode(A);
      }
    }
  },mm8:function(D){
    D=D||this;
    var F=D.dataset.getSize(),E=D.getPageInfo();
    if(!D.remotePaging&&!E.pageSize){
      E.pageSize=F;
    }
    F=F>E.pageSize?E.pageSize:F;
    var C=D.dataset.startRecordNo;
    D.rowNum=F;
    D.rowBegin=C;
    D.rowEnd=C+F;
    D.colNum=D.columnList.length;
    D.iB3=0;
    var G=[],A=[];
    D.eb3(D,G,A);
    D.bodyDiv.innerHTML=G.join("");
    D.freezeBodyDiv.innerHTML=A.join("");
    var _=Sigma.U.firstChildElement(D.bodyDiv);
    if(_){
      if(Sigma.U.getTagName(_)!="TABLE"){
        _=Sigma.U.nextElement(_);
      }
      if(Sigma.U.getTagName(_)=="TABLE"){
        D.gridTable=_;
        D.gridTbodyList.push(_.tBodies[0]);
      }
    }
    _=Sigma.U.firstChildElement(D.freezeBodyDiv);
    if(_){
      if(Sigma.U.getTagName(_)!="TABLE"){
        _=Sigma.U.nextElement(_);
      }
      D.freezeBodyTable=_;
    }
    D.bodyFirstRow=D.Ih5();
    if(D.rowNum<1){
      for(var B=0;B<D.colNum;B++){
        var $=D.columnList[B];
        if(D.bodyFirstRow){
          $.firstCell=D.bodyFirstRow.cells[B];
          $.firstCell.style.height="0px";
          $.firstCell.style.borderBottomWidth="0px";
        }
      }
    }
    D.Eo3=D.showIndexColumn;
    D.YF1=true;
    Sigma.$thread(function(){
      D.freezeBodyDiv.style.height=D.bodyDiv.clientHeight+"px";
      D.syncScroll();
    });
  },hideFreezeZone:function(){
    this.freezeHeadDiv&&(this.freezeHeadDiv.style.display="none");
    this.freezeBodyDiv&&(this.freezeBodyDiv.style.display="none");
  },cleanFreezeHead:function(){
	  //桑志豪加判斷
	if(this.freezeHeadTable!=null){
		var _=this.freezeHeadTable.tBodies[0];
	    for(var $=_.rows.length-1;$>=0;$--){
	      Sigma.U.removeNodeTree(_.rows[$]);
	    }
	}
    
  },eb3:function(E,I,_){
    var A=(""+E.rowEnd).length;
    A=(A<2?2:A)*10+5;
    var G=A+this.kR2,$=A+this.oc8,J="style=\"width:"+G+"px;\"",F="style=\"width:"+$+"px;\"";
    this.indexColumnCell=["<td class=\"gt-index-col\" "+J+" ><div class=\"gt-inner\" "+F+" >","</div></td>"];
    if(E.showIndexColumn){
      E.iB3=A;
      E.cleanFreezeHead();
      //这里桑志豪修改过
      //className:"gt-hd-row"
      var B=Sigma.$e("tr",{className:"gt-hd-row gt-row"}),H=Sigma.T_G.oo5(E,A,null);
      B.appendChild(H);
      E.freezeHeadTable.tBodies[0].appendChild(B);
      E.freezeHeadDiv.style.left=E.freezeBodyDiv.style.left=this.freezeFixW+"px";
      E.headTable.style.marginLeft=E.iB3+"px";
      E.freezeHeadDiv.style.display=E.freezeBodyDiv.style.display="block";
      E.freezeBodyDiv.style.height=parseInt(E.bodyDiv.style.height)+"px";
    }else {
      E.freezeHeadDiv.style.display=E.freezeBodyDiv.style.display="none";
    }
    _.push(Sigma.T_G.tableStartHTML);
    I.push(Sigma.U.replaceAll(Sigma.T_G.tableStartHTML,"margin-left:0px","margin-left:"+E.iB3+"px"));
    var D=E.rowBegin,C=E.rowEnd;
    E.currentRowNum=D;
    E.Yt8(D,C,-1,I,_);
    _.push(Sigma.T_G.tableEndHTML);
  },isNextGroup:function($,A,_){
  },isGroupRow:function($,_){
    $=$||(_?_.cells[0]:null);
    return Sigma.U.hasClass($,"gt-group-row");
  },isEmptyRow:function($){
    return !$||Sigma.U.hasClass($,"gt-row-empty");
  },isInsertRow:function($){
    return Sigma.U.hasClass($,"gt-row-new");
  },isDelRow:function($){
    return Sigma.U.hasClass($,"gt-row-del");
  },sx4:function(_,$,A,C){
    var B=[_];
    B.push(this.indexColumnCell[0]);
    B.push(A+1+this.indexColumnCell[1]);
    B.push(Sigma.T_G.rowEndHTML);
    return B.join("");
  },eX0:function(A,$,F,H,C){
    var E=[A],G=0;
    for(var B=0;B<H;B++){
      var _=this.columnList[B];
      if(_.hidden&&!this.renderHiddenColumn){
        continue ;
      }
      var D=C&&C[_.id]?C[_.id].attr:null;
      E.push(Sigma.T_G.cell(_,_.renderer($[_.fieldIndex],$,_,this,G,F),D));
      G++;
    }
    E.push(Sigma.T_G.rowEndHTML);
    return E.join("");
  },tn8:function(_,$,C,D){
    var B=[_],A="<td colspan=\""+D+"\" class=\"gt-group-row\" > + "+C+" -------------</td>";
    B.push(A);
    B.push(Sigma.T_G.rowEndHTML);
    return B.join("");
  },resetFreeze:function($){
  },qb8:function(){
    if(this.frozenColumnList){
      var _,$;
      for(_=0;_<this.frozenColumnList.length;_++){
        $=this.columnMap[this.frozenColumnList[_]];
        if($){
          this.Ei9($.colIndex,_,true);
        }
      }
      for(_=0;_<this.frozenColumnList.length;_++){
        $=this.columnMap[this.frozenColumnList[_]];
        if($){
          $.freeze(true);
        }
      }
    }
  },getGroupInfo:function($,_){
    return this.getMergeGroupInfo($,_);
  },getSeparateGroupInfo:function(G,B){
    var C=this.colNum,$=null;
    for(var H=0;H<C;H++){
      var _=this.columnList[H];
      if(_.grouped){
        $=_;
        break ;
      }
    }
    var D={};
    if($){
      var I=B-G,A=G;
      for(var E=0;E<I;E++){
        var F=this.dataset.getRecord(A++);
        if(!F){
          continue ;
        }
        var J=this.getUniqueField(F);
      }
    }
  },getMergeGroupInfo:function(J,C){
    var F=this.colNum,M=C-J,D,B,N=1,A={},_=null,G=[];
    for(var K=0;K<F;K++){
      var H=this.columnList[K],$=J;
      for(var E=0;E<M;E++){
        var I=this.dataset.getRecord($++);
        if(!I){
          continue ;
        }
        D=G[E]=G[E]||{};
        var L=D["__gt_group_s_"];
        if(H.grouped){
          B=D[H.id]=D[H.id]||{};
          if(_==I[H.fieldIndex]&&(!L&&L!==0||L>K)){
            B.attr=" style=\"display:none;\" ";
            N++;
          }else {
            A.attr=" rowspan=\""+N+"\" ";
            N=1;
            A=B;
            _=I[H.fieldIndex];
            D["__gt_group_s_"]=K;
          }
        }
      }
      A.attr=" rowspan=\""+N+"\" ";
    }
    return G;
  },Dy8:function($){
    return {columnId:$.id,fieldName:$.fieldName,sortOrder:$.sortOrder,getSortValue:$.getSortValue,sortFn:$.sortFn};
  },qP4:function(){
    if(!this.sortInfo||this.sortInfo.length<1){
      return ;
    }
    this.dataset.sort(this.sortInfo);
  },Ec3:function($){
    this.sortInfo=$||this.sortInfo;
    if(this.remoteSort){
      this.reload();
    }else {
      this.refresh();
    }
  },addSortInfo:function(G,E){
    E=E||E===false?E:this.multiSort;
    var D=[],C=false;
    for(var B=0;B<this.columnList.length;B++){
      var _=this.columnList[B];
      if(_.grouped){
        if(!C&&_.id==G.columnId){
          _.sortOrder=G.sortOrder;
          C=true;
        }else {
          var A=_.sortOrder;
          A=A=="asc"||A=="desc"?A:"asc";
          _.sortOrder=A;
        }
        D.push(this.Dy8(_));
      }
    }
    if(!C&&E!==true){
      this.sortInfo=D.concat(G);
      return ;
    }
    this.sortInfo=this.sortInfo||[];
    var $=G.columnId,H=false,F,I;
    for(I=0;I<this.sortInfo.length;I++){
      F=this.sortInfo[I];
      if(F&&F.columnId===$){
        F.sortOrder=G.sortOrder;
        H=true;
        break ;
      }
    }
    !H&&(this.sortInfo.push(G));
    for(I=0;I<this.sortInfo.length;I++){
      F=this.sortInfo[I];
      if(!F||(!F.sortOrder||F.sortOrder=="defaultsort")){
        this.sortInfo.splice(I,1);
        I--;
      }
    }
  },updateSortState:function(){
    var $,_;
    for(_=0;_<this.colNum;_++){
      $=this.columnList[_];
      $.sortIcon&&($.sortIcon.className="gt-hd-icon");
      $.frozenSortIcon&&($.frozenSortIcon.className="gt-hd-icon");
      $.sortOrder=null;
    }
    if(!this.sortInfo||this.sortInfo.length<1){
      return ;
    }
    for(var F=0;F<this.sortInfo.length;F++){
      var B=this.sortInfo[F];
      if(B){
        $=this.columnMap[B.columnId];
        var A=B.sortOrder||"defaultsort";
        $.sortOrder=A;
        Sigma.U.addClass($.sortIcon,"gt-hd-"+A);
        Sigma.U.addClass($.frozenSortIcon,"gt-hd-"+A);
      }
    }
    var C=this.Ih5();
    if(C&&!this.isEmptyRow(C)){
      this.bodyFirstRow=C;
      var E=0;
      for(_=0;_<this.colNum;_++){
        var D=this.columnList[_];
        if(!D.hidden||this.renderHiddenColumn){
          D.firstCell=this.bodyFirstRow.cells[E];
          D.firstCell.className=D.styleClass;
          E++;
        }
      }
    }
  },getRecord:function($){
    var _;
    if(Sigma.U.isNumber($)){
      _=$;
    }else {
      if(Sigma.U.getTagName($)=="TD"){
        return this.getRecordByRow($.parentNode);
      }else {
        if(Sigma.U.getTagName($)=="TR"){
          return this.getRecordByRow($);
        }else {
          if(Sigma.$type($,"object")&&!$.tagName){
            return $;
          }else {
            if(this.selectedRows.length>0){
              _=this.selectedRows[this.selectedRows.length-1].getAttribute(Sigma.Const.DataSet.INDEX)/1;
            }else {
              _=0;
            }
          }
        }
      }
    }
    return this.dataset.getRecord(_);
  },getRecordByRow:function(A){
    if(!A){
      return null;
    }
    if(this.isInsertRow(A)){
      var _=A.getAttribute(Sigma.Const.DataSet.INDEX);
      return this.dataset.insertedRecords[_];
    }
    var $=A.getAttribute(Sigma.Const.DataSet.INDEX)/1;
    return $||$===0?this.dataset.getRecord($):null;
  },getRowByRecord:function($){
    var _=$[Sigma.Const.DataSet.ROW_KEY];
    return Sigma.doc.getElementById(_);
  },getUniqueField:function($){
    return $[this.dataset.uniqueField];
  },forEachRow:function(A){
    var D=this.ew1();
    for(var C=0,_=D.length;C<_;C++){
      var B=D[C],$=this.getRecordByRow(B);
      A(B,$,C,this);
    }
  },updateCheckState:function(){
    var $=this.checkColumn;
    if($){
      var _=$.colIndex,F=this.ew1();
      for(var E=0,C=F.length;E<C;E++){
        var D=F[E],B=D.cells[_],A=B?B.getElementsByTagName("input"):null;
        A=A?A[0]:A;
        if(A&&A.checked){
          this.selectRow(D);
        }
      }
    }
  },updateEditState:function(){
    var B=this.getInsertedRecords(),F,A,H,D,E;
    for(H=0;H<B.length;H++){
      this.qh9(B[H]);
    }
    for(E in this.dataset.updatedRecords){
      A=this.dataset.updatedRecords[E];
      var G=this.dataset.nl8[E];
      F=this.getRowByRecord(A);
      if(F){
        var C=this.getRecordByRow(F);
        if(C){
          for(var _ in G){
            C[_]=A[_];
            for(H=0,D=this.columnList.length;H<D;H++){
              var $=this.columnList[H];
              if(_==$.fieldIndex&&F.cells){
                this.dirty(F.cells[H]);
                F.cells[H].firstChild.innerHTML=$.renderer(C[$.fieldIndex],C,$,this,H,F.rowIndex);
              }
            }
          }
        }
        this.dataset.updatedRecords[E]=C;
      }
    }
    for(E in this.dataset.deletedRecords){
      A=this.dataset.deletedRecords[E];
      F=this.getRowByRecord(A);
      this.del(F);
    }
  },filterGrid:function($){
    this.filterInfo=$||this.filterInfo||[];
    if(this.tools.filterTool){
      Sigma.U.addClass(this.tools.filterTool.itemIcon,"gt-tool-filtered");
    }
    if(this.remoteFilter){
      this.reload();
      return ;
    }
    this.Ac6=this.dataset.vS5;
    this.filterDataProxy=this.dataset.filterData(this.filterInfo);
    if(!this.remoteFilter&&this.justShowFiltered){
      this.dataset.vS5=this.filterDataProxy;
      this.refresh();
    }
    if(this.afterFilter){
      this.afterFilter(this.filterDataProxy);
    }
    return this.filterDataProxy;
  },unfilterGrid:function($){
    this.filterGrid([]);
    if(this.tools.filterTool){
      Sigma.U.removeClass(this.tools.filterTool.itemIcon,"gt-tool-filtered");
    }
    return null;
  },syncScroll:function($,_){
    if(Sigma.$chk($)){
      this.bodyDiv.scrollLeft=$;
    }
    if(Sigma.$chk(_)){
      this.bodyDiv.scrollTop=_;
    }
    this.headDiv.scrollLeft=this.bodyDiv.scrollLeft;
    this.freezeBodyDiv.scrollTop=this.bodyDiv.scrollTop;
    this.scrollLeft=this.bodyDiv.scrollLeft;
    this.scrollTop=this.bodyDiv.scrollTop;
  },Nd4:function(){
    if(this.resizable&&(this.toolbarPosition=="bottom"||this.toolbarPosition=="b")&&this.KN2){
      this.resizeButton=Sigma.$e("div",{id:this.id+"_resizeButton",className:"gt-tool-resize",innerHTML:"&#160;"});
      this.resizeButton.setAttribute("unselectable","on");
      this.KN2.appendChild(this.resizeButton);
      var D=this;
      Sigma.U.addEvent(this.resizeButton,"mousedown",function($){
        Sigma.Grid.startGridResize($,D);
      });
    }
    this.Ui3();
    if(this.toolbarContent&&this.toolbarPosition&&this.toolbarPosition!="none"){
      this.toolbarContent=this.toolbarContent.toLowerCase();
      var C=this.toolbarContent.split(" "),$=null;
      for(var E=0;E<C.length;E++){
        var A=C[E];
        if(A=="|"){
          var _=Sigma.ToolFactroy.create(this,"separator",true);
          if($){
            $.separator=_;
          }
        }else {
          if(A=="state"||A=="info"||A=="pagestate"){
            if(!this.pageStateBar){
              this.pageStateBar=Sigma.ToolFactroy.create(this,"pagestate",this.showPageState);
            }
            if(E!=C.length-1){
              this.pageStateBar.className+=" gt-page-state-left";
            }
            $=this.pageStateBar;
          }else {
            if(A=="nav"){
              this.navigator.Qa5(this);
              $=this.navigator;
            }else {
              var B=A.charAt(0).toUpperCase()+A.substring(1);
              $=this.tools[A+"Tool"]=Sigma.ToolFactroy.create(this,A,this["show"+B+"Tool"]);
            }
          }
        }
      }
    }
    this.expendMenu={};
    this.over_initToolbar=true;
  },refreshToolBar:function($,A){
    $&&(this.setPageInfo($));
    if(this.over_initToolbar){
      this.navigator.Ap1($,A);
      this.navigator.gd3();
      var _=this.navigator.pageInput;
      if(this.pageStateBar){
        $=this.getPageInfo();
        Sigma.U.removeNode(this.pageStateBar.firstChild);
        this.pageStateBar.innerHTML="<div>"+Sigma.$msg(this.getMsg(_?"PAGE_STATE":"PAGE_STATE_FULL"),$.startRowNum,$.endRowNum,$.totalPageNum,$.totalRowNum,$.pageNum)+"</div>";
      }
    }
  },Ui3:function(){
    if(!this.showGridMenu||!this.KN2||!this.toolBar){
      return ;
    }
    var F=this,_=F.id;
    this.gridMenuButton=new Sigma.Button({gridId:this.id,parentItem:this,container:this.toolBar,cls:"gt-tool-gridmenu",iD3:true});
    var C=!this.allowGroup?null:Sigma.ad2("group",{gridId:_,checkValid:function($){
        return $.grouped;
      },checkFn:"group",uncheckFn:"ungroup",checkType:F.multiGroup?"checkbox":"radio",canCheck:function($){
        return !$.hidden;
      }}),E=!this.allowFreeze?null:Sigma.ad2("freeze",{gridId:_,checkValid:function($){
        return $.frozen;
      },checkFn:"freeze",uncheckFn:"unfreeze",canCheck:function($){
        return !$.hidden;
      }}),H=!this.allowHide?null:Sigma.ad2("show",{gridId:_,checkValid:function($){
        return !$.hidden;
      },checkFn:"show",uncheckFn:"hide",canCheck:function($){
        return !$.frozen;
      }});
    function B(_,$){
      if(!_){
        return ;
      }
      _.show();
      _.setTitle($);
      F.gridMenuButton.ti1();
    }
    var D=this.toolbarPosition!="bottom"?"B":"T";
    function A($){
      return function(){
        Sigma.Grid.av9(F,$);
      };
    }
    var $=null;
    if(this.allowCustomSkin){
      $=new Sigma.MenuItem({gridId:this.id,type:"",text:this.getMsg("CHANGE_SKIN"),cls:"gt-icon-skin"});
      var G=[];
      for(var I=0;I<this.skinList.length;I++){
        G.push(new Sigma.MenuItem({gridId:this.id,type:"radiobox",text:this.skinList[I].text,checked:I===0,onclick:A(this.skinList[I].value)}));
      }
      $.FN4(G,"R");
    }
    this.gridMenuButton.FN4([$,C?new Sigma.MenuItem({gridId:F.id,type:"",text:F.getMsg("MENU_GROUP_COL"),cls:"gt-icon-groupcol",onclick:function(){
        B(C,F.getMsg("MENU_GROUP_COL"));
      }}):null,E?new Sigma.MenuItem({gridId:F.id,type:"",text:F.getMsg("MENU_FREEZE_COL"),cls:"gt-icon-freeze",onclick:function(){
        B(E,F.getMsg("MENU_FREEZE_COL"));
      }}):null,H?new Sigma.MenuItem({gridId:F.id,type:"",text:F.getMsg("MENU_SHOW_COL"),cls:"gt-icon-hidecol",onclick:function(){
        B(H,F.getMsg("MENU_SHOW_COL"));
      }}):null,new Sigma.MenuItem({gridId:this.id,type:"",text:Sigma_GRID_VER})],D);
  },showMask:function($){
    if($||this.transparentMask){
      Sigma.U.addClass(this.gridMask,"gt-transparent");
    }else {
      Sigma.U.removeClass(this.gridMask,"gt-transparent");
    }
    this.gridMask&&(this.gridMask.style.display="block");
    this.pageSizeSelect&&(this.pageSizeSelect.style.visibility="hidden");
  },hideMask:function(){
    if(this.gridMask){
      this.gridMask.style.cursor="auto";
      this.gridMask.style.display="none";
    }
    this.pageSizeSelect&&(this.pageSizeSelect.style.visibility="inherit");
  },showWaiting:function(){
    this.showMask();
    if(this.gridWaiting&&!this.transparentMask){
      this.gridWaiting.style.display="block";
    }
    this.isWaiting=true;
  },hideWaiting:function(){
    if(this.gridWaiting){
      this.gridWaiting.style.display="none";
    }
    this.hideMask();
    this.isWaiting=false;
  },showDialog:function(D){
    var A=this;
    switch(D){
    case "filter":
      A.filterDialog=A.filterDialog||Sigma.fo0({title:A.getMsg("DIAG_TITLE_FILTER"),gridId:A.id});
      A.filterDialog.show();
      break ;
    case "chart":
      var _=A.activeCell?A.getRecordByRow(A.activeRow):A.getRecord();
      if(!_){
        break ;
      }
      var C=[],B="",$=300,E=300;
      if(_){
        A.charDialog=A.charDialog||new Sigma.Dialog({gridId:A.id,container:A.gridMask,id:"charDialog",width:$,height:E,autoRerender:true,title:A.getMsg("DIAG_TITLE_CHART")});
        A.charDialog.show();
        A.chart=new Sigma.Chart({swfPath:A.SigmaGridPath+"/flashchart/fusioncharts/charts/",width:$-3,height:E-23,container:A.charDialog.bodyDiv});
        Sigma.$each(A.columnList,function($,A){
          if($.chartCaption){
            B=$.chartCaption.replace("{@}",_[$.fieldIndex]);
          }
          if($.inChart){
            C.push([$.header||$.title,_[$.fieldIndex],$.chartColor||"66bbff"]);
          }
        });
        A.chart.caption=A.chartCaption;
        A.chart.subCaption=B;
        A.chart.data=C;
        A.chart.qo7();
      }
      break ;
    }
  },closeDialog:function(){
    this.activeDialog&&this.activeDialog.close();
    this.activeDialog=null;
  },hideDialog:function(){
    this.activeDialog&&this.activeDialog.hide();
    this.activeDialog=null;
  },closeGridMenu:function(){
    if(this.gridMenuButton){
      this.gridMenuButton.ti1();
    }
  },Yx0:function(){
    return this.filterInfo||[];
  },Md2:function(){
    return this.sortInfo||[];
  },getPageInfo:function($){
    return $?this.navigator.Ap1():this.navigator.pageInfo;
  },setPageInfo:function($){
    Sigma.$extend(this.getPageInfo(),$);
  },setTotalRowNum:function($){
    this.getPageInfo().totalRowNum=$;
  },getTotalRowNum:function($){
    return this.getPageInfo($).totalRowNum;
  },addSkin:function($){
    if(Sigma.$type($,"string")){
      $={text:this.getMsg("STYLE_NAME_"+$.toUpperCase()),value:$.toLowerCase()};
    }
    this.skinList.push($);
  },nB1:function(_){
    for(var $=0;$<_.length;$++){
      this.gridRowList.push(_[$]);
    }
  },Ih5:function(){
    return this.gridTbodyList[0]?this.gridTbodyList[0].rows[0]:null;
  },ew1:function(){
    return this.gridTbodyList[0].rows;
  },Oq6:function($){
    if(Sigma.U.isNumber($)){
      return this.ew1()[$];
    }
    return $;
  },getRowNumber:function(){
    return this.rowNum;
  },hasData:function(){
    return this.rowNum>0;
  },getColumnValue:function(B,C){
    var _=this.getColumn(B),A=this.getRecord(C),$=A?A[_.fieldIndex]:null;
    return $;
  },setColumnValue:function(B,A,_){
    var $=A;
    if(Sigma.U.isNumber($)){
      $=this.dataset.getRecord($);
    }
    this.update($,this.columnMap[B].fieldName,_);
  },dirty:function($){
    Sigma.U.addClass($,"gt-cell-updated");
  },selectFirstRow:function(){
    var $=this.Oq6(0);
    if($){
      this.selectRow($);
    }
  },selectRow:function(B){
    var D=[].concat(B);
    for(var C=0,_=D.length;C<_;C++){
      B=this.Oq6(D[C]);
      if(!this.isEmptyRow(B)){
        var A=B.rowIndex,$=this.getRecordByRow(B);
        if(Sigma.$invoke(this,"beforeRowSelect",[$,B,A,this])!==false){
          Sigma.U.addClass(B,"gt-row-selected");
          this.Tp0(B);
          this.selectedRows.push(B);
          this.activeRow=B;
          Sigma.$invoke(this,"afterRowSelect",[$,B,A,this]);
        }
      }
    }
  },unselectRow:function($){
    if($){
      Sigma.U.removeClass($,"gt-row-selected");
      this.Tp0($);
      Sigma.U.remove(this.selectedRows,$);
    }
  },unselectAllRow:function(){
    var $=this;
    Sigma.$each(this.selectedRows,function(_){
      Sigma.U.removeClass(_,"gt-row-selected");
      $.Tp0(_);
    });
    this.selectedRows=[];
    this.activeRow=null;
  },getSelectedRecords:function(){
    var $=[];
    for(var _=0;_<this.selectedRows.length;_++){
      $.push(this.getRecordByRow(this.selectedRows[_]));
    }
    return $;
  },getSelectedRecord:function(){
    return this.getRecordByRow(this.selectedRows[this.selectedRows.length-1])||this.activeRecord;
  },Nv0:function(_,C){
    var B=this.Yk7();
    C=C||[Sigma.T_G.bodyTableStart(this.id,false),B,"<tbody>"];
    var $=0,D=this.dataset.getSize(),A=_?this.getPageInfo().pageSize:-1;
    C.push(this.Yt8($,D,A,C,null,true));
    return C.join("");
  },Yk7:function(){
    var _=this.headTable.innerHTML,$=_.toLowerCase().indexOf("<tr"),A=_.toLowerCase().lastIndexOf("</tr>");
    if(this.customHead){
      $=_.toLowerCase().indexOf("<tr",$+3);
    }
    _=_.substring($,A+"</tr>".length);
    return "<!-- gt : head start  -->"+_+"<!-- gt : head end  -->";
  },Yt8:function(K,G,D,P,C,F){
    var N=this,H=N.colNum,M=N.getGroupInfo(K,G),_=" >",J=null,O=null,I=0,E="";
    for(var B=K;B<G;B++){
      J=N.dataset.getRecord(B);
      if(!J){
        break ;
      }
      var $=N.customRowAttribute(J,B,N)||"",A=Sigma.T_G.rowStartS(N,B,$);
      if(!F){
        E=N.rowKey+N.getUniqueField(J);
        J[Sigma.Const.DataSet.ROW_KEY]=E;
        N.currentRowNum++;
        if(N.showIndexColumn){
          C.push(N.sx4(A+_,J,B,H));
        }
      }else {
        if(B>0&&D>0&&(B%D)===0){
          P.push("\n<!-- gt : page separator  -->\n");
        }
      }
      if(N.isNextGroup(J,O,B)){
        P.push(N.tn8(A+_,J,B,H));
      }
      var L=M[I++];
      P.push(N.eX0(A+" id=\""+E+"\""+_,J,B,H,L));
      O=J;
    }
    P.push(Sigma.T_G.tableEndHTML);
  },rg5:function(A){
    var _=this,$=[];
    Sigma.$each(_.columnList,function(_,B){
      if(_.hidden===true||(A&&_[A]===false)){
        $.push(_.CLASS_PREFIX+_.styleClass+" { display:none;width:0px; }");
        $.push(_.CLASS_PREFIX+_.innerStyleClass+" { display:none;width:0px; }");
      }
    });
    var B=$.join("\n");
    return Sigma.U.replaceAll(B,".gt-grid ","");
  },printGrid:function(){
    var A=this,$;
    A.closeGridMenu();
    A.showWaiting();
    var E=[" body { margin :5px;padding:0px;}",".gt-table { width:100%;border-left:1px solid #000000 ; border-top:1px solid #000000; }",".gt-table TD { font-size:12px;padding:2px; border-right:1px solid #000000 ; border-bottom:1px solid #000000; }",".gt-hd-row TD { padding:3px; border-bottom:2px solid #000000 ;background-color:#e3e3e3; white-space:nowrap;word-break:keep-all;word-wrap:normal; }",".gt-hd-hidden { }",".gt-row-even {\tbackground-color:#f6f6f6; }"];
    E.push(this.rg5("printable"));
    A.customPrintCss&&E.push(A.customPrintCss);
    E=E.join("\n");
    var D=A.Nv0(),_=Sigma.doc.activeElement;
    function B($){
      $.writeln("<style>");
      $.writeln(E);
      $.writeln("</style>");
      $.writeln(D);
      $.close();
    }
    if(Sigma.isIE||Sigma.isGecko||Sigma.isSafari){
      $=A.gridIFrame.contentWindow.document;
      B($);
      A.gridIFrame.contentWindow.focus();
      A.gridIFrame.contentWindow.print();
    }else {
      if(Sigma.isOpera){
        var C=window.open("");
        $=C.document;
        B($);
        C.focus();
        Sigma.$thread(function(){
          C.print();
          Sigma.$thread(function(){
            C.close();
          },2000);
        });
      }
    }
    Sigma.$thread(function(){
      A.hideWaiting();
    },1000);
  },getAllRows:function(){
    var $=this;
    if($.gridRowList.length===0){
      Sigma.$each($.gridTbodyList,function(_){
        $.nB1(_.rows);
      });
    }
    return $.gridRowList;
  },getAllFreezeRows:function(){
    var $=this;
    if($.gridFreezeRowList.length===0){
      var A=$.freezeBodyTable.tBodies[0].rows;
      for(var _=0;_<A.length;_++){
        $.gridFreezeRowList.push(A[_]);
      }
    }
    return $.gridFreezeRowList;
  },filterHandler:{hide:function($){
      $=$||this.filterInfo;
      var _=Sigma.Grid.filterCheck[$.checkType]($.value,$.checkValue);
      if(_){
      }
    }},Ei9:function(_,G,A){
    if(_==G){
      return ;
    }
    var B=this,F=this.columnList.length,C=A!==true&&this.freezeHeadDiv.style.display=="block"?this.frozenColumnList.length:0;
    G=G<C?C:G;
    var $=false,E;
    if(G>=F){
      G=F-1;
      G^=_;
      _^=G;
      G^=_;
      $=true;
    }
    var D=this.getAllRows();
    Sigma.U.insertNodeBefore(this.columnList[_].headCell,this.columnList[G].headCell);
    for(E=0;E<D.length;E++){
      Sigma.U.insertNodeBefore(D[E].cells[_],D[E].cells[G]);
    }
    Sigma.U.moveItem(this.columnList,_,G);
    for(E=0;E<F;E++){
      this.columnList[E].colIndex=E;
    }
  },lR7:function(){
    this.activeCell=null;
    this.activeRow=null;
    this.activeColumn=null;
    this.activeEditor=null;
    this.activeRecord=null;
    this.activeValue=null;
  },ga5:function(B){
    this.closeGridMenu();
    if(this.endEdit()===false){
      return ;
    }
    this.lastOverHdCell=null;
    if(B!==false){
      this.lR7();
      for(var C=0;C<this.selectedRows.length;C++){
        this.selectedRows[C]=null;
      }
      this.selectedRows=[];
      this.overRow=null;
      this.overRowF=null;
    }
    this.gridRowList=[];
    this.bodyFirstRow=null;
    for(var _=0;_<this.colNum;_++){
      var $=this.columnList[_];
      $.firstCell=null;
      $.frozenSortIcon=null;
      $.frozenHdTool=null;
      $.frozenHeadCell=null;
    }
    var A=this;
    Sigma.$each(this.gridTbodyList,function(_,$){
      Sigma.U.removeNode(_);
      A.gridTbodyList[$]=null;
    });
    Sigma.U.removeNode(this.gridTable);
    this.gridTable=null;
    this.gridTbodyList=[];
    this.cleanFreezeHead();
    if(this.freezeBodyTable){
      Sigma.U.removeNode(this.freezeBodyTable.tBodies[0],this.freezeBodyTable);
      this.freezeBodyTable=null;
    }
  },destroy:function(){
    var B=this;
    Sigma.$invoke(this,"Qc6");
    this.ga5();
    var D=["gridMenuButton","filterDialog","charDialog","navigator"];
    Sigma.$each(D,function($,_){
      if(B[$]&&B[$].destroy){
        B[$].destroy();
      }
      B[$]=null;
    });
    for(var C in this.tools){
      var _=this.tools[C];
      if(_&&_.destroy){
        _.destroy();
      }
      this.tools[C]=null;
    }
    Sigma.U.removeNodeTree(this.gridDialogTitle);
    Sigma.U.removeNodeTree(this.gridDialogBody);
    Sigma.U.removeNodeTree(this.gridDialog);
    this.gridDialog=this.gridDialogBody=this.gridDialogTitle=null;
    for(var A=0;A<this.colNum;A++){
      var $=this.columnList[A];
      $.destroy();
    }
    Sigma.$each(this.domList,function($,_){
      Sigma.U.removeNode(B[$]);
      B[$]=null;
    });
    this.freezeBodyTable=this.gridTable=this.bodyFirstRow=this.lastOverHdCell=this.overRowF=this.overRow=null;
    this.gridFreezeRowList=[];
    this.selectedRows=[];
    this.cacheBodyList=[];
    this.frozenColumnList=[];
    this.sortedColumnList=[];
    this.checkedRows={};
    this.gridTbodyList=[];
    this.gridRowList=[];
    if(Sigma.activeGrid==this){
      Sigma.activeGrid=null;
    }
    Sigma.GridCache[this.id]=null;
    delete Sigma.GridCache[this.id];
  }};
Sigma.Grid=Sigma.$class(Sigma.GridDefault);
Sigma.$extend(Sigma.Grid,{isSelectedRow:function($){
    return Sigma.U.hasClass($,"gt-row-selected");
  },ii3:function($,_,A){
    if(!A||_.overRow==A){
      _.aI2=false;
      return ;
    }
    _.aI2=true;
    if(_.overRow){
      _.overRow.className=_.overRow.className.replace(" gt-row-over","");
    }
    A.className+=" gt-row-over";
    _.overRow=A;
    _.aI2=false;
    return A;
  },Hi5:function(_,B,$){
    _=_||window.event;
    var C=Sigma.U.Lu8("tr",null,_);
    if(B.isEmptyRow(C)){
      return ;
    }
    if($==B.freezeBodyDiv&&C){
      var A=B.Oq6(C.rowIndex);
      Sigma.Grid.ii3(_,B,A);
      B.overRowF=C;
    }else {
      Sigma.Grid.ii3(_,B,C);
    }
  },startGridResize:function($,_){
    $=$||window.event;
    _=Sigma.$grid(_);
    _.closeGridMenu();
    _.Ll0=true;
    _.resizeButton.style.cursor=_.gridGhost.style.cursor="se-resize";
    _.syncLeftTop();
    _.gridGhost.style.top=_.top+Sigma.doc.body.scrollTop+"px";
    _.gridGhost.style.left=_.left+Sigma.doc.body.scrollLeft+"px";
    var A=_.gridDiv.offsetWidth,B=_.gridDiv.offsetHeight;
    _.gridGhost.cx=$.clientX-A;
    _.gridGhost.cy=$.clientY-B;
    _.gridGhost.style.width=A+"px";
    _.gridGhost.style.height=B+"px";
    _.gridGhost.style.display="block";
  },iT7:function(_,B){
    var A=Sigma.U.parseInt(B.gridGhost.style.width)+"px",$=Sigma.U.parseInt(B.gridGhost.style.height)+"px";
    B.gridGhost.style.cursor="auto";
    B.gridMask.style.display=B.gridGhost.style.display="none";
    B.Ll0=false;
    B.setSize(A,$);
  },Nt7:function(_,$){
    _=_||window.event;
    if($.resizable===false){
      return ;
    }
    var B=$.grid;
    B.mouseDown=true;
    if(_.ctrlKey){
      return ;
    }
    B.Ad6=true;
    B.closeGridMenu();
    B.showMask(true);
    B.headDiv.style.cursor=B.gridMask.style.cursor="e-resize";
    B.ut9=$.id;
    var A=Sigma.U.getPageX(_);
    $.pn8=A-B.iz8[0];
    B.ye8=$.pn8+$.minWidth-$.headCell.offsetWidth;
    if(B.separateLine){
      B.separateLine.style.left=$.pn8+"px";
      B.separateLine.style.height=B.viewport.offsetHeight-2+"px";
      B.separateLine.style.display="block";
    }
    if(B.columnMoveS){
      B.columnMoveS.style.left=$.headCell.offsetLeft+((Sigma.isFF2||Sigma.isFF1)?0:B.iB3)+"px";
      B.columnMoveS.style.display="block";
    }
  },RR7:function(A,$){
    A=A||window.event;
    var C=$.grid;
    C.mouseDown=true;
    if(!A.ctrlKey||C.frozenColumnList[$.getColumnNo()]){
      return ;
    }
    C.closeGridMenu();
    var B=Sigma.U.getPageX(A),D=$.headCell.offsetLeft;
    C.columnMoveS.setAttribute("newColIndex",null);
    var _=C.headerGhost;
    _.setAttribute("colIndex",$.getColumnNo());
    _.setAttribute("offsetX2",D-B);
    _.style.left=D+((Sigma.isFF2||Sigma.isFF1)?0:C.iB3)+"px";
    _.style.width=$.headCell.offsetWidth-1+"px";
    _.style.display="block";
    _.innerHTML="<span style=\"padding-left:2px;\" >"+Sigma.$getText($.headCell)+"</span>";
  },Ct6:function(C,G){
    C=C||window.event;
    var F=Sigma.U.getPageX(C);
    if(G.separateLine&&G.separateLine.style.display=="block"){
      var E=F-G.iz8[0];
      E=E>G.ye8?E:G.ye8;
      G.separateLine.style.left=E+"px";
    }else {
      if(!G.customHead&&G.headerGhost&&G.headerGhost.style.display=="block"){
        var H=F-G.iz8[0]+G.headDiv.scrollLeft;
        G.headerGhost.style.left=F+((Sigma.isFF2||Sigma.isFF1)?0:G.iB3)+G.headerGhost.getAttribute("offsetX2")/1+"px";
        var _=-1,A=-1;
        for(var I=0;I<G.headFirstRow.cells.length;I++){
          var B=G.headFirstRow.cells[I];
          if(H>B.offsetLeft&&H<B.offsetLeft+B.offsetWidth){
            _=B.offsetLeft;
            A=I;
            break ;
          }
        }
        if(H<=B.offsetLeft){
          I=0;
        }
        if(_>=0){
          G.columnMoveS.style.left=_+((Sigma.isFF2||Sigma.isFF1)?0:G.iB3)+"px";
          G.columnMoveS.style.display="block";
        }else {
          G.columnMoveS.style.display="none";
        }
        G.columnMoveS.setAttribute("newColIndex",A);
      }else {
        if(G.Ll0){
          var D=C.clientX-G.gridGhost.cx,$=C.clientY-G.gridGhost.cy;
          D=D<G.minWidth?G.minWidth:D;
          $=$<G.minHeight?G.minHeight:$;
          G.gridGhost.style.width=D+"px";
          G.gridGhost.style.height=$+"px";
        }
      }
    }
  },Ni4:function(A,E){
    A=A||window.event;
    E=Sigma.$grid(E);
    var D=Sigma.U.getPageX(A);
    E.mouseDown=false;
    if(E.separateLine&&E.separateLine.style.display=="block"){
      var $=E.columnMap[E.ut9];
      $.Cq3=D-E.iz8[0];
      var C=$.Cq3-$.pn8,B=C+parseInt($.width);
      $.setWidth(B);
      E.ut9=-1;
      E.separateLine.style.display=E.columnMoveS.style.display="none";
      E.headDiv.style.cursor="auto";
      E.hideMask();
      E.syncScroll();
      if(!Sigma.isOpera){
        E.Ad6=false;
      }
      Sigma.$invoke(E,"afterColumnResize",[$,B,E]);
    }else {
      if(!E.customHead&&E.headerGhost&&E.headerGhost.style.display=="block"){
        var F=Sigma.isIE?A.x:A.pageX,G=E.columnMoveS.getAttribute("newColIndex"),_=E.headerGhost.getAttribute("colIndex");
        if(G!==null&&(G+"").length>0&&_!==null&&(_+"").length>0){
          G=G/1;
          if(G<0){
            G=E.columnList.length;
          }
          E.Ei9(_/1,G/1);
          E.syncScroll();
        }
        E.columnMoveS.style.display="none";
        E.columnMoveS.setAttribute("newColIndex",null);
        E.headerGhost.style.display="none";
        E.headerGhost.setAttribute("colIndex",null);
        E.headerGhost.style.cursor="auto";
      }else {
        if(E.Ll0){
          Sigma.Grid.iT7(A,E);
        }
      }
    }
  },av9:function(_,$){
    _=Sigma.$grid(_);
    var A=_.gridDiv.className.split(" ");
    for(var B=0;B<A.length;B++){
      if(A[B].indexOf(Sigma.Const.Grid.SKIN_CLASSNAME_PREFIX)===0){
        A[B]="";
      }
    }
    A.push(Sigma.Const.Grid.SKIN_CLASSNAME_PREFIX+$);
    _.gridDiv.className=A.join(" ");
  },yt8:function(C,E){
    var $=E.id;
    C=Sigma.$grid(C);
    var B=C.id,D=E.checkValid,_=E.checkValue,F=E.checkType||"checkbox";
    if(!_){
      _=Sigma.$chk(E.fieldIndex)?"record[\""+E.fieldIndex+"\"];":"grid.getUniqueField(record);";
    }
    if(typeof _=="string"){
      _=new Function("value","record","col","grid","colNo","rowNo",["return ",_].join(""));
    }
    if(!D){
      D=function(B,C,A,$,_,E,D){
        return _.checkedRows[B];
      };
    }
    E.header="";
    E.title=E.title||C.getMsg("CHECK_ALL");
    E.width=30;
    E.resizable=false;
    E.printable=false;
    E.sortable=false;
    var A="gt_"+B+"_chk_"+$;
    E.hdRenderer=function(B,_,$){
      return "<input type=\""+F+"\" class=\"gt-f-totalcheck\" name=\""+A+"\" />";
    };
    E.renderer=function(G,C,$,B,J,H){
      var E=_(G,C,$,B,J,H),I=D(E,G,C,$,B,J,H)?"checked=\"checked\"":"";
      return "<input type=\""+F+"\" class=\"gt-f-check\" value=\""+E+"\" "+I+" name=\""+A+"\" />";
    };
    return E;
  }});
Sigma.Grid.prototype.initGrid=Sigma.Grid.prototype.render;
Sigma.$extend(Sigma.Grid,{render:function($){
    $=Sigma.$grid($);
    return function(){
      $.render();
    };
  },TV9:function(B,$,C,A){
    C=C||$.headCell;
    if(!C){
      return ;
    }
    A=A||Sigma.Grid.Le4($,C);
    var _=Sigma.U.nextElement(A),D=Sigma.U.nextElement(_);
    $.hdTool=$.hdTool||Sigma.Grid.getHdTool($,C);
    $.sortIcon=$.sortIcon||A;
    $.menuButton=$.menuButton||_;
    $.separator=$.separator||D;
    if($.separator&&$.resizable===false){
      $.separator.style.display="none";
    }
    Sigma.U.addEvent(C,"mousedown",function(_){
      B.lH8();
      if(B.endEdit()===false){
        return ;
      }
      B.closeGridMenu();
      if(!B.customHead){
        Sigma.U.stopEvent(_);
        Sigma.Grid.RR7(_,$);
      }
    });
    Sigma.U.addEvent(C,"click",function(A){
      var D=Sigma.U.getEventTarget(A);
      if(!B.Ad6){
        Sigma.$invoke(B,"onHeadClick",[A,C,$,B]);
        if(Sigma.U.getTagName(D)=="INPUT"&&D.type=="checkbox"&&Sigma.U.hasClass(D,"gt-f-totalcheck")){
          Sigma.Ye2(D,B,$);
        }else {
          if($.sortable&&D.className!="gt-hd-button"){
            B.lastAction="sort";
            B.sorting=true;
            var _=$.sortOrder=="asc"?"desc":($.sortOrder=="desc"?"defaultsort":"asc"),E=B.Dy8($);
            E.sortOrder=_;
            B.addSortInfo(E);
            B.Ec3();
          }
        }
      }
      if(Sigma.isOpera){
        B.Ad6=false;
      }
    });
    if($.resizable){
      D.colID=$.id;
      D.style.cursor="e-resize";
      Sigma.U.addEvent(D,"mousedown",function(_){
        B.lH8();
        Sigma.U.stopEvent(_);
        Sigma.Grid.Nt7(_,$);
      });
    }
    if(!$.sortable&&!$.resizable&&$.hdTool){
      $.hdTool.style.display="none";
    }
  },getHdTool:function($,_){
    var A=Sigma.U.firstChildElement(_||$.headCell);
    return Sigma.U.yo9(A);
  },Le4:function($,_){
    var A=Sigma.Grid.getHdTool($,_);
    return Sigma.U.firstChildElement(A);
  },mappingRenderer:function(_,$){
    return function(A){
      return _[A]||($===undefined||$===null?A:$);
    };
  },findGridByElement:function(B){
    var _="DIV",A="gt-grid",$="";
    while((B=B.parentNode)){
      if(Sigma.U.getTagName(B)==_&&Sigma.U.hasClass(B,A)){
        $=B.id;
        break ;
      }
    }
    if($.indexOf("_div")===$.length-4){
      $=$.substring(0,$.length-4);
    }
    return Sigma.$grid($);
  }});
var Ext=Ext||null;
(Ext&&Ext.reg)&&(Ext.reg("gtgrid",Sigma.Grid));
Sigma.Tc5=function(_,C,$){
  C=Sigma.$grid(C);
  _=Sigma.$(_);
  if(_.checked==$){
    return $;
  }
  var B=Sigma.U.Lu8("td",_),D=B.parentNode,A=C.Kl8(D)[0];
  if($===true||$===false){
    _.checked=$;
  }
  if(_.checked){
    C.checkedRows[_.value]=true;
    if(C.selectRowByCheck){
      C.selectRow(A);
    }
  }else {
    delete C.checkedRows[_.value];
    if(C.selectRowByCheck){
      C.unselectRow(A);
    }
  }
  return !!_.checked;
};
Sigma.Ye2=function(H,J,G,I){
  J=Sigma.$grid(J);
  H=Sigma.$(H);
  if(I!==null&&I!==undefined){
    H.checked=I;
  }
  var _=Sigma.U.Lu8("td",H),$=Sigma.U.getCellIndex(_),K=H.checked,E=G.frozen?J.getAllFreezeRows():J.getAllRows();
  for(var F=0,C=E.length;F<C;F++){
    var D=E[F],B=D.cells[$];
    if(B){
      var A=B.getElementsByTagName("input")[0];
      Sigma.Tc5(A,J,K);
    }
  }
};
Sigma.ev7=function(){
  if(Sigma.ev7.inited){
    return ;
  }
  var $=Sigma.isIE?Sigma.doc.body:Sigma.doc;
  Sigma.U.addEvent($,"mousemove",function($){
    Sigma.activeGrid&&Sigma.Grid.Ct6($,Sigma.activeGrid);
  });
  Sigma.U.addEvent($,"mouseup",function($){
    Sigma.activeGrid&&Sigma.Grid.Ni4($,Sigma.activeGrid);
  });
  Sigma.U.addEvent($,"click",function($){
   //桑志豪修改
   // Sigma.activeGrid&&(Sigma.activeGrid.endEdit()||Sigma.activeGrid.closeGridMenu());
    Sigma.activeGrid&&Sigma.activeGrid.closeGridMenu();
  });
  Sigma.U.addEvent($,"keydown",function($){
    Sigma.activeGrid&&Sigma.activeGrid.ra8($);
  });
  Sigma.ev7.inited=true;
};

//桑志豪:数据导出扩展数字number:A.number
Sigma.ColumnDefault={CLASS_PREFIX:".",destroyList:["sortIcon","hdTool","menuButton","separator","frozenSortIcon","frozenHdTool","frozenHeadCell","headCell","firstCell"],id:0,fieldName:null,width:120,minWidth:45,header:null,styleClass:null,align:"left",headAlign:"left",emptyText:"",sortable:true,resizable:true,moveable:true,editable:true,hideable:true,frozenable:true,groupable:true,filterable:true,printable:true,number:false,exportable:true,sortOrder:null,hidden:false,frozen:false,toolTip:false,beforEdit:null,afterEdit:null,renderer:function(A,$,_,B,D,C){
    return A!==null&&A!==undefined?A:_.emptyText;
  },hdRenderer:function(_,$){
    return _;
  },editor:null,fieldIndex:0,gridId:null,filterField:null,newValue:null,cellAttributes:"",getSortValue:null,sortFn:null,format:null,syncRefresh:true,expression:null,isExpend:false,Tb3:function(A,_){
    var $=this;
    if(Sigma.$type(A,"string")){
      this.id=A;
    }else {
      Sigma.$extend(this,A);
    }
    this.id=this.id||encodeURIComponent(this.header);
    this.header=this.header||this.id;
    this.fieldName=this.fieldName||this.fieldIndex||this.id;
    this.fieldIndex=this.fieldIndex||this.fieldName||this.id;
    this.CLASS_PREFIX=".gt-grid "+this.CLASS_PREFIX;
  },destroy:function(){
    if(this.editor&&this.editor.destroy){
      this.editor.destroy();
    }
    this.editor=null;
    Sigma.$each(this.destroyList,function($,_){
      Sigma.U.removeNode(this[$]);
      this[$]=null;
    });
  },getColumnNo:function(){
    return this.colIndex;
  },setWidth:function($){
    var _=this.grid;
    $=$<this.minWidth?this.minWidth:$;
    this.width=$+"px";
    Sigma.U.CSS.updateRule(this.CLASS_PREFIX+this.styleClass,"width",($+_.kR2)+"px");
    Sigma.U.CSS.updateRule(this.CLASS_PREFIX+this.innerStyleClass,"width",($+_.oc8)+"px");
  },setHeader:function(_){
    this.header=_;
    var $=this.headCell.getElementsByTagName("div")[0];
    if($){
      var A=$.getElementsByTagName("span")[0]||$;
      A.innerHTML=_;
    }
  },Xl4:function(){
    if(!this.grid.renderHiddenColumn){
      this.grid.Ke3();
      this.grid.refresh();
    }
  },hide:function(){
    if(this.frozen){
      return false;
    }
    Sigma.U.CSS.updateRule(this.CLASS_PREFIX+this.styleClass,"display","none");
    this.hidden=true;
    this.Xl4();
  },show:function(){
    if(this.frozen){
      return false;
    }
    Sigma.U.CSS.updateRule(this.CLASS_PREFIX+this.styleClass,"display","");
    this.hidden=false;
    this.Xl4();
  },toggle:function(){
    return this.hidden?this.show():this.hide();
  },group:function($,_){
    this.grouped=true;
    this.grid.refresh();
  },ungroup:function($,_){
    this.grouped=false;
    this.grid.refresh();
  },pc7:function(F,D,$,C,G,B,A,_){
    if(!A.Eo3){
      $=F.cloneNode(false);
      $.id="";
      $.appendChild(B.cloneNode(true));
      D.appendChild($);
    }
    var E=F.cells[G].cloneNode(true);
    $.appendChild(E);
    if(_&&C===0){
      this.frozenHeadCell=E;
      this.frozenSortIcon=Sigma.Grid.Le4(this,this.frozenHeadCell);
      this.frozenHdTool=Sigma.Grid.getHdTool(this,this.frozenHeadCell);
      if(!Sigma.isIE){
        Sigma.Grid.TV9(A,this,this.frozenHeadCell,this.frozenSortIcon);
      }
    }
  },freeze:function(B){
    var A=this.grid,G=this.getColumnNo();
    if(!B&&G<A.frozenColumnList.length){
      return false;
    }
    var F=A.headTable.tBodies[0].rows,_=A.freezeHeadTable.tBodies[0].rows,$,D,E,C=10;
    if(!A.Eo3){
      $=Sigma.T_G.oo5(A,C,null);
      D=Sigma.T_G.freezeBodyCell(A,C,null);
    }
    for(E=0;E<F.length;E++){
      this.pc7(F[E],A.freezeHeadTable.tBodies[0],_[E],E,G,$,A,true);
    }
    if(A.rowNum<1){
    }
    A.YF1=false;
    if(A.overRow){
      A.overRow.className=A.overRow.className.replace(" gt-row-over","");
    }
    F=A.getAllRows();
    _=A.freezeBodyTable.tBodies[0].rows;
    for(E=0;E<F.length;E++){
      this.pc7(F[E],A.freezeBodyTable.tBodies[0],_[E],E,G,D,A);
    }
    if(!B){
      A.Ei9(G,A.frozenColumnList.length);
      A.frozenColumnList.push(this.id);
    }
    this.frozen=true;
    A.freezeHeadDiv.style.display=A.freezeBodyDiv.style.display="block";
    A.freezeHeadDiv.style.height=A.headDiv.offsetHeight+"px";
    A.freezeBodyDiv.style.height=A.bodyDiv.clientHeight+"px";
    if(!A.Eo3){
      A.freezeHeadDiv.style.left=A.freezeBodyDiv.style.left=0-(C+A.kR2)+A.freezeFixW+"px";
    }
    A.Eo3=true;
    A.syncScroll();
    Sigma.U.removeNode($,D);
  },En9:function(_,$){
    for(var A=0;A<_.length;A++){
      Sigma.U.removeNodeTree(_[A].cells[$]);
    }
  },unfreeze:function(){
    var _=this.grid,A=this.getColumnNo();
    if(!_.frozenColumnList||A>=_.frozenColumnList.length){
      return false;
    }
    this.frozenHeadCell=this.frozenHdTool=this.frozenSortIcon=null;
    _.Ei9(A,_.frozenColumnList.length-1);
    _.frozenColumnList.splice(A,1);
    var $=_.freezeHeadTable.tBodies[0].rows;
    this.En9($,A+1);
    if(_.rowNum<1){
    }
    $=_.freezeBodyTable.tBodies[0].rows;
    this.En9($,A+1);
    this.frozen=false;
    if(_.frozenColumnList.length<1){
      if(!_.showIndexColumn){
        _.freezeHeadDiv.style.display=_.freezeBodyDiv.style.display="none";
      }
    }
    _.syncScroll();
  }};
Sigma.Column=Sigma.$class(Sigma.ColumnDefault);
Sigma.Navigator=Sigma.$class({in5:function(){
    return {pageInfo:{pageSize:20,pageNum:1,totalRowNum:0,totalPageNum:1,startRowNum:0,endRowNum:0}};
  },inited:false,Tb3:function(_){
    var $=_.pageInfo||{};
    delete _.pageInfo;
    Sigma.$extend(this,_);
    Sigma.$extend(this.pageInfo,$);
  },destroy:function(){
    var $=this,_=["firstPageButton","prevPageButton","nextPageButton","tK6","gotoPageButton"];
    Sigma.$each(_,function(_,A){
      if($[_]&&$[_].destroy){
        $[_].destroy();
      }
      $[_]=null;
    });
    Sigma.U.removeNode(this.pageInput);
    this.pageInput=null;
  },Qa5:function(){
    var $=Sigma.$grid(this.gridId);
    this.firstPageButton=new Sigma.Button({container:$.toolBar,cls:"gt-first-page",onclick:this.aY5,onclickArgs:[this]});
    this.prevPageButton=new Sigma.Button({container:$.toolBar,cls:"gt-prev-page",onclick:this.oc1,onclickArgs:[this]});
    this.nextPageButton=new Sigma.Button({container:$.toolBar,cls:"gt-next-page",onclick:this.vi6,onclickArgs:[this]});
    this.tK6=new Sigma.Button({container:$.toolBar,cls:"gt-last-page",onclick:this.XI5,onclickArgs:[this]});
    this.inited=true;
    if(!$.loading){
      this.Ap1();
    }
  },createGotoPage:function(){
    var B=Sigma.$grid(this.gridId);
    this.gotoPageButton=new Sigma.Button({container:B.toolBar,cls:"gt-goto-page",onclick:this.Dv6,onclickArgs:[this],text:B.getMsg("GOTOPAGE_BUTTON_TEXT")});
    if(B.toolBar){
      var _,A;
      this.pageInput=Sigma.$e("input",{className:"gt-page-input"});
      var $=this;
      Sigma.U.addEvent(this.pageInput,"keydown",function(_){
        var A=_.keyCode;
        if(A==Sigma.Const.Key.ENTER){
          Sigma.U.stopEvent(_);
          $.Dv6(_,$);
        }
      });
      _=Sigma.$e("div",{innerHTML:B.getMsg("PAGE_BEFORE"),className:"gt-toolbar-text"});
      A=Sigma.$e("div",{innerHTML:B.getMsg("PAGE_AFTER"),className:"gt-toolbar-text"});
      B.toolBar.appendChild(_);
      B.toolBar.appendChild(Sigma.Button.FM8(this.pageInput));
      B.toolBar.appendChild(A);
    }
  },Ap1:function($,B){
    this.pageInfo=($||this.pageInfo);
    var A=this.pageInfo;
    if(B!==false){
      if(A.totalRowNum<1){
        var _=Sigma.$grid(this.gridId);
        A.totalRowNum=_.dataset.getSize();
      }
      A.totalPageNum=Math.ceil(A.totalRowNum/A.pageSize);
      A.pageNum=A.pageNum>A.totalPageNum?A.totalPageNum:A.pageNum;
      A.pageNum=A.pageNum<1?1:A.pageNum;
      A.startRowNum=(A.pageNum-1)*A.pageSize+1;
      A.startRowNum=isNaN(A.startRowNum)?1:A.startRowNum;
      A.endRowNum=A.startRowNum/1+A.pageSize/1-1;
      A.endRowNum=A.endRowNum>A.totalRowNum?A.totalRowNum:A.endRowNum;
    }
    return A;
  },gd3:function($){
    this.pageInfo=($||this.pageInfo);
    var A=this.pageInfo,_=Sigma.$grid(this.gridId);
    if(this.inited){
      if(this.pageInput){
        this.pageInput.value=A.pageNum;
        this.pageInput.maxLength=(""+A.totalPageNum).length;
      }
      if(A.pageNum==1){
        this.firstPageButton.disable();
        this.prevPageButton.disable();
      }else {
        this.firstPageButton.enable();
        this.prevPageButton.enable();
      }
      if(A.pageNum==A.totalPageNum){
        this.nextPageButton.disable();
        this.tK6.disable();
      }else {
        this.nextPageButton.enable();
        this.tK6.enable();
      }
    }
    if(_&&_.pageSizeSelect){
      _.pageSizeList=!_.pageSizeList||_.pageSizeList.length<1?[_.pageSize]:_.pageSizeList;
      _.pageSizeSelect.innerHTML="";
      Sigma.U.createSelect(Sigma.U.listToMap(_.pageSizeList),this.pageInfo.pageSize,{},_.pageSizeSelect);
    }
  },ei7:function(A,C,$){
    A=A||this;
    var B=A.pageInfo.pageNum,_=Sigma.$grid(A.gridId);
    C=(!C||C<1)?1:(C>A.pageInfo.totalPageNum?A.pageInfo.totalPageNum:C);
    if(Sigma.$invoke(_,"beforeGotoPage",[C,B,A,_])!==false){
      _.lastAction=$;
      _.ei7(C,B);
    }
  },Dv6:function($,_){
    _.ei7(_,Sigma.U.parseInt(_.pageInput.value,_.pageInfo.pageNum),"ei7");
  },aY5:function($,_){
    _.ei7(_,1,"firstPage");
  },oc1:function($,_){
    _.ei7(_,_.pageInfo.pageNum-1,"prevPage");
  },vi6:function($,_){
    _.ei7(_,_.pageInfo.pageNum+1,"nextPage");
  },XI5:function($,_){
    _.ei7(_,_.pageInfo.totalPageNum,"lastPage");
  },refreshPage:function($,_){
    _.ei7(_,_.pageInfo.pageNum,"refreshPage");
  }});
Sigma.BaseMenuItem=Sigma.$class({id:null,gridId:null,cls:null,type:null,onclickArgs:null,parentItem:null,reference:null,container:null,text:null,toolTip:null,itemBox:null,itemIcon:null,itemText:null,itemAfterIcon:null,subMenu:null,Tb3:function($){
    this.disabled=false;
    this.iD3=false;
    this.overShowSubMenu=true;
    this.onclick=Sigma.$empty;
    Sigma.$extend(this,$);
    this.toolTip=this.toolTip||this.text||"";
  },destroy:function(){
    if(this.subMenu){
      this.subMenu.destroy();
    }
    this.container=null;
    this.parentItem=null;
    if(this.separator){
      Sigma.U.removeNode(this.separator);
      this.separator=null;
    }
    Sigma.U.removeNode(this.itemIcon);
    this.itemIcon=null;
    Sigma.U.removeNode(this.itemText);
    this.itemText=null;
    Sigma.U.removeNode(this.itemAfterIcon);
    this.itemAfterIcon=null;
    Sigma.U.removeNode(this.itemBox);
    this.itemBox=null;
  },lc4:function(B,$){
    Sigma.activeGrid&&Sigma.activeGrid.endEdit();
    var A=$.subMenu?$.subMenu.hidden:false;
    if($.parentItem){
      ($.parentItem.Re8)&&$.parentItem.Re8(B);
      if($.parentItem.currenItem){
        Sigma.U.removeClass($.parentItem.currenItem.itemBox,"gt-menu-activemenu");
      }
      $.parentItem.currenItem=$;
      Sigma.U.addClass($.itemBox,"gt-menu-activemenu");
    }
    if($.disabled||$.onclick.apply($,[B].concat($.onclickArgs||$))===false){
      Sigma.U.stopEvent(B);
      return ;
    }
    Sigma.U.stopEvent(B);
    if($.type=="checkbox"){
      $.toggleCheck();
    }else {
      if($.type=="radiobox"){
        var _=$.parentItem.itemList;
        for(var C=0;C<_.length;C++){
          if(_[C].type=="radiobox"&&_[C]!=$){
            _[C].uncheckMe();
          }
        }
        $.QM1();
      }
    }
    if($.subMenu){
      if(A){
        $.Ga9(B);
      }else {
        $.ti1(B);
      }
    }
  },Re8:Sigma.$empty,QM1:function(){
    Sigma.U.removeClass(this.itemIcon,"gt-icon-unchecked");
    Sigma.U.addClass(this.itemIcon,"gt-icon-"+this.type);
    this.checked=true;
  },uncheckMe:function(){
    Sigma.U.removeClass(this.itemIcon,"gt-icon-"+this.type);
    Sigma.U.addClass(this.itemIcon,"gt-icon-unchecked");
    this.checked=false;
  },toggleCheck:function(){
    if(this.checked===true){
      this.uncheckMe();
    }else {
      this.QM1();
    }
  },disable:function(){
    Sigma.U.addClass(this.itemBox,"gt-button-disable");
    this.disabled=true;
  },enable:function(){
    Sigma.U.removeClass(this.itemBox,"gt-button-disable");
    this.disabled=false;
  },bP6:function(){
    if(this.subMenu){
      return this.subMenu.position;
    }
    return null;
  },BX2:function($){
    if(this.subMenu&&$){
      this.subMenu.position=$;
    }
  },Ga9:function($){
    if(this.subMenu){
      if(!this.bP6()){
        this.BX2("R");
      }
      this.subMenu.show($);
    }
  },toggleMenu:function($){
    if(this.subMenu){
      if(!this.bP6()){
        this.BX2("R");
      }
      this.subMenu.toggle($);
    }
  },ti1:function($){
    var _=this;
    while((_=_.subMenu)){
      _.close($);
    }
  },FN4:function(_,$){
    if(_){
      if(!this.subMenu){
        this.subMenu=new Sigma.GridMenu({gridId:this.gridId,parentItem:this,reference:this.itemBox});
        this.itemAfterIcon&&Sigma.U.addClass(this.itemAfterIcon,"gt-menu-parent");
      }
      _.gridId=this.gridId;
      this.BX2($);
      this.subMenu.FN4(_);
    }
    return this;
  }});
Sigma.Button=Sigma.BaseMenuItem.extend({Tb3:function(_){
    this.className="gt-image-button";
    this.clickClassName="gt-image-button-down";
    this.Oo3(_);
    if(!this.container){
      return ;
    }
    this.itemBox=Sigma.$e("a",{href:"javascript:void(0);return false;",className:this.className,title:this.toolTip});
    this.itemIcon=Sigma.$e("div",{className:this.cls});
    this.itemBox.appendChild(this.itemIcon);
    this.container.appendChild(this.itemBox);
    if(this.iD3){
      Sigma.Button.rQ6(this.container);
    }
    var $=this;
    Sigma.U.addEvent($.itemBox,"mousedown",function(_){
      if(!$.disabled){
        Sigma.U.addClass($.itemBox,$.clickClassName);
      }
    });
    Sigma.U.addEvent($.itemBox,"mouseup",function(_){
      if(!$.disabled){
        Sigma.U.removeClass($.itemBox,$.clickClassName);
      }
    });
    Sigma.U.addEvent($.itemBox,"click",function(_){
      $.lc4(_,$);
    });
    Sigma.U.addEvent($.itemBox,"dblclick",function(_){
      $.lc4(_,$);
    });
  }});
Sigma.$extend(Sigma.Button,{rQ6:function(_){
    var $=Sigma.$e("div",{className:"gt-image-button gt-button-split"});
    if(_){
      _.appendChild($);
    }
    return $;
  },createCommonButton:function(D,_,A,$,C,B){
    return new Sigma.Button({id:D,container:C,cls:_,onclick:A,onclickArgs:$,iD3:B});
  },FM8:function(_){
    var $=Sigma.$e("div",{className:"gt-toolbar-comp"});
    if(_){
      if(Sigma.$type(_,"string","number")){
        $.innerHTML=_;
      }else {
        $.appendChild(_);
      }
    }
    return $;
  }});
Sigma.MenuItem=Sigma.BaseMenuItem.extend({Tb3:function(_){
    this.Oo3(_);
    if(this.type=="checkbox"||this.type=="radiobox"){
      this.cls=this.checked?("gt-icon-"+this.type):"gt-icon-unchecked";
    }
    this.itemBox=Sigma.$e("a",{href:"javascript:void(0);return false;",className:"gt-menuitem"});
    this.itemIcon=Sigma.$e("div",{className:"gt-menu-icon "+this.cls});
    this.itemText=Sigma.$e("div",{className:"gt-checkboxtext",innerHTML:this.text,title:this.toolTip});
    this.itemAfterIcon=Sigma.$e("div",{className:"gt-aftericon "+this.afterIconClassName});
    this.itemBox.appendChild(this.itemIcon);
    this.itemBox.appendChild(this.itemText);
    this.itemBox.appendChild(this.itemAfterIcon);
    var $=this;
    Sigma.U.addEvent($.itemBox,"click",function(_){
      $.lc4(_,$);
    });
  }});
Sigma.$extend(Sigma.MenuItem,{rQ6:function(_){
    var $=Sigma.$e("div",{className:"gt-image-button gt-button-split"});
    if(_){
      _.appendChild($);
    }
    return $;
  }});
Sigma.GridMenu=Sigma.$class({gridId:null,parentItem:null,container:null,Ro4:null,pt5:null,destroy:function(){
    this.container=null;
    this.parentItem=null;
    Sigma.$each(this.itemList,function(_,A,$){
      Sigma.U.removeNode(_);
      $[A]=null;
    });
    this.itemList=null;
  },Tb3:function(_){
    this.itemList=[];
    this.refreshOnShow=false;
    this.currenItem=null;
    this.hidden=true;
    this.className="gt-popmenu";
    this.position="";
    Sigma.$extend(this,_);
    this.menuBox=Sigma.$e("div",{className:this.className,style:{display:"none",left:"10px",top:"10px"}});
    var $=Sigma.$grid(this.gridId)||{};
    this.container=this.container||$.gridDiv||Sigma.doc.body;
    this.container.appendChild(this.menuBox);
  },refresh:function(){
  },onshow:function(){
  },HN8:function(){
  },FN4:function($){
    $=[].concat($);
    for(var _=0;_<$.length;_++){
      if($[_]){
        $[_].gridId=this.gridId;
        $[_].parentItem=this;
        $[_].container=this.menuBox;
        this.itemList.push($[_]);
        this.menuBox.appendChild($[_].itemBox);
      }
    }
    return this;
  },show:function($){
    if(this.container&&this.container.parentNode&&this.container.parentNode.className.indexOf("menu")>1){
    }
    this.menuBox.style.display="block";
    var B,_,A=Sigma.U.getXY(this.reference,this.container);
    B=A[0];
    _=A[1];
    this.Ro4=this.Ro4||0;
    this.pt5=this.pt5||0;
    switch(this.position.toUpperCase()){
    case "L":
      B-=this.menuBox.offsetWidth;
      break ;
    case "T":
      _-=this.menuBox.offsetHeight;
      break ;
    case "R":
      B+=this.reference.offsetWidth;
      break ;
    case "B":
      _+=this.reference.offsetHeight;
      break ;
    case "LT":
      B-=this.menuBox.offsetWidth;
      _-=this.menuBox.offsetHeight-this.reference.offsetHeight;
      break ;
    case "RT":
      B+=this.reference.offsetWidth;
      _-=this.menuBox.offsetHeight-this.reference.offsetHeight;
      break ;
    case "RB":
      B+=this.reference.offsetWidth;
      _+=this.reference.offsetHeight;
      break ;
    case "LB":
      B-=this.reference.offsetWidth;
      _+=this.menuBox.offsetHeight;
      break ;
    case "M":
      B=$.pageX||($.clientX-$.x);
      _=$.pageY||($.clientY-$.y);
      break ;
    default:
      _+=this.reference.offsetHeight;
    }
    Sigma.U.setXY(this.menuBox,[B+this.Ro4,_+this.pt5]);
    this.hidden=false;
  },close:function($){
    this.Re8($);
    this.menuBox.style.display="none";
    this.hidden=true;
  },Re8:function($){
    for(var _=0;_<this.itemList.length;_++){
      this.itemList[_].ti1($);
    }
  },toggle:function($){
    Sigma.U.stopEvent($);
    var _=Sigma.$grid(this.gridId);
    if(this.hidden===true){
      this.show($);
    }else {
      this.close($);
    }
  }});
Sigma.ToolFactroy={register:function(_,$){
    Sigma.ToolFactroy.tools[_]=$;
  },create:function(D,E,_){
    if(_===false){
      return false;
    }
    D=Sigma.$grid(D);
    var $=D;
    if(E=="info"||E=="pagestate"){
      E="state";
    }
    var C=Sigma.ToolFactroy.tools[E];
    if(C&&Sigma.$type(C,"function")){
      C=C(D,E,_);
    }else {
      if(C){
        var A=C.name||E,B=C.onclick||C.action;
        C=new Sigma.Button({container:C.container||D.toolBar,cls:C.cls||"gt-tool-"+A,toolTip:C.toolTip||D.getMsg("TOOL_"+A.toUpperCase()),onclick:function($){
            B($,D);
          }});
      }
    }
    return C;
  },tools:{"goto":function($){
      return $.navigator.createGotoPage();
    },"pagesize":function(C){
      var _=Sigma.U.createSelect({});
      _.className="gt-pagesize-select";
      C.pageSizeSelect=_;
      function $(A){
        C.setPageInfo({pageSize:_.value/1});
        C.navigator.aY5(A,C.navigator);
        C.pageSizeSelect.blur();
        try{
          C.bodyDiv.focus();
        }
        catch($){
        }
      }
      Sigma.U.addEvent(C.pageSizeSelect,"change",$);
      var A=Sigma.$e("div",{innerHTML:C.getMsg("PAGESIZE_BEFORE"),className:"gt-toolbar-text"}),B=Sigma.$e("div",{innerHTML:C.getMsg("PAGESIZE_AFTER"),className:"gt-toolbar-text"});
      C.toolBar.appendChild(A);
      C.toolBar.appendChild(Sigma.Button.FM8(C.pageSizeSelect));
      C.toolBar.appendChild(B);
      return _;
    },"add":{onclick:function($,_){
        _.add();
      }},"del":{onclick:function($,_){
        _.del();
      }},"save":{onclick:function($,_){
        _.lastAction="save";
        _.save();
      }},"reload":{onclick:function($,_){
        _.lastAction="reload";
        _.reload();
      }},"print":{onclick:function($,_){
        _.lastAction="print";
        _.printGrid();
      }},"xls":{onclick:function($,_){
        _.lastAction="export";
        _.exportGrid("xls");
      }},"pdf":{onclick:function($,_){
        _.lastAction="export";
        _.exportGrid("pdf");
      }},"csv":{onclick:function($,_){
        _.lastAction="export";
        _.exportGrid("csv");
      }},"xml":{onclick:function($,_){
        _.lastAction="export";
        _.exportGrid("xml");
      }},"filter":{onclick:function($,_){
        _.lastAction="filter";
        _.showDialog("filter");
      }},"chart":{onclick:function($,_){
        _.showDialog("chart");
      }},"state":function(_){
      var $=Sigma.$e("div",{innerHTML:"&#160;",className:"gt-page-state"});
      _.toolBar.appendChild($);
      return $;
    },"separator":function(_){
      var $=Sigma.Button.rQ6(_.toolBar);
      return $;
    },"fill":function(_){
      var $="";
      return $;
    }}};
Sigma.Widget=Sigma.$class({id:null,dom:null,setDom:function($){
    this.dom=$;
  },vP3:function(){
    return this.dom;
  },show:function(){
    this.dom&&(this.dom.style.display="block");
  },hide:function(){
    this.dom&&(this.dom.style.display="none");
  },close:function(){
    this.hide();
  },vk2:function(_,$){
    if(_||_===0){
      this.left=_;
      this.dom&&(this.dom.style.left=this.left+"px");
    }
    if($||$===0){
      this.top=$;
      this.dom&&(this.dom.style.top=this.top+"px");
    }
  },setSize:function($,_){
    this.width=$||this.width;
    this.height=_||this.height;
    if(!this.dom){
      return ;
    }
    if(this.width/1&&this.width>0){
      this.dom.style.width=(this.width-1)+"px";
    }
    if(this.height/1&&this.height>0){
      this.dom.style.height=(this.height-1)+"px";
    }
  },destroy:function(){
    Sigma.$invoke(this,"Qc6");
    Sigma.U.removeNode(this.dom);
    this.dom=null;
  }});
Sigma.DialogDefault={Am5:true,autoRerender:true,title:null,body:null,buttonZone:null,headHeight:20,hidden:false,Tb3:function($){
    if($){
      Sigma.$extend(this,$);
    }
    this.domId=(this.gridId?this.gridId+"_":"")+this.id;
    this.buttonLayout=this.buttonLayout||"h";
    this.dialogId=this.id;
    Sigma.WidgetCache[this.id]=this;
  },destroy:function(){
    this.container=null;
    Sigma.U.removeNode(this.bodyDiv);
    this.bodyDiv=null;
    Sigma.U.removeNode(this.dom);
    this.dom=null;
    Sigma.WidgetCache[this.id]=null;
    delete Sigma.WidgetCache[this.id];
  },titleRender:function($){
    this.title=$||this.title;
    return this.title;
  },show:function(){
    var $=Sigma.$grid(this.gridId);
    $.closeGridMenu();
    if(Sigma.$invoke(this,"beforeShow",[$])!==false){
      if(Sigma.isIE){
        Sigma.eachGrid(function(_){
          if(_!=$){
            _.gridDiv.style.position=_.viewport.style.position="static";
            _.KN2&&(_.KN2.style.position="static");
          }
        });
      }
      this.locked=true;
      $.showMask();
      this.autoRerender&&this.render($.gridMask);
      $.gridMask.appendChild(this.dom);
      if(this.width/1&&this.width>0){
        this.dom.style.marginLeft=(0-this.width/2)+"px";
      }
      this.dom.style.marginTop="0px";
      this.dom.style.top="25px";
      this.dom.style.display="block";
      $.activeDialog=this;
      this.hidden=false;
      Sigma.$invoke(this,"afterShow",[$]);
    }
  },hide:function(){
    var $=Sigma.$grid(this.gridId);
    if(Sigma.$invoke(this,"beforeHide",[$])!==false){
      this.locked=false;
      $.hideMask();
      if(this.dom){
        this.dom.style.display="none";
        $.gridEditorCache.appendChild(this.dom);
      }
      $.activeDialog=null;
      this.hidden=true;
      if(Sigma.isIE){
        Sigma.eachGrid(function(_){
          if(_!=$){
            _.gridDiv.style.position=_.viewport.style.position="relative";
            _.KN2&&(_.KN2.style.position="relative");
          }
        });
      }
      Sigma.$invoke(this,"afterHide",[$]);
    }
  },close:function(){
    var $=Sigma.$grid(this.gridId);
    this.hide();
  },confirm:function(){
    var $=Sigma.$grid(this.gridId);
    if($.activeEditor==this){
      this.locked=false;
      $.endEdit();
      $.activeEditor=this;
    }
  },render:function($){
    this.container=$||this.container;
    if(!this.rendered){
      this.dom=this.dom||Sigma.$e("div",{className:"gt-grid-dialog"});
      this.dom.id=this.domId+"_dialog";
      this.container=this.container||Sigma.doc.body;
      this.container.appendChild(this.dom);
      this.dom.innerHTML=Sigma.T_D.create(this);
      this.titleDiv=Sigma.$(this.domId+"_dialog_title");
      this.bodyDiv=Sigma.$(this.domId+"_dialog_body");
      if(this.height){
        this.bodyDiv.style.height=this.height-(this.headHeight||0)+"px";
      }
      this.setBody();
      this.setButtons();
      this.setTitle();
      Sigma.$invoke(this,"afterRender",[this]);
    }
    this.setSize();
    this.vk2();
    if(Sigma.$type(this.valueDom,"function")){
      this.valueDom=this.valueDom();
    }
    this.valueDom=Sigma.$(this.valueDom);
    this.rendered=true;
  },setBody:function($){
    var _=Sigma.$grid(this.gridId);
    this.body=$||this.body;
    this.bodyDiv.innerHTML="";
    if(Sigma.$type(this.body,"function")){
      this.body=this.body(_);
    }
    if(!this.body){
    }else {
      if(Sigma.$type(this.body,"string")){
        this.bodyDiv.innerHTML=this.body;
      }else {
        this.bodyDiv.appendChild(this.body);
      }
    }
  },setButtons:function(_){
    this.buttons=_||this.buttons;
    if(!this.buttons){
      return ;
    }
    _=[].concat(this.buttons);
    if(_.length>0){
      this.buttonZone=this.buttonZone||Sigma.$e("div",{className:"gt-dialog-buttonzone-"+this.buttonLayout,id:this.domId+"_div"});
      if(this.buttonLayout=="h"){
        this.buttonZone.style.width=this.width-12+"px";
      }
      for(var A=0;A<_.length;A++){
        var $=null;
        if(_[A].breakline){
        	$=Sigma.$e("br");
        }else {
          if(_[A].html){
        	$=Sigma.$e("span",{innerHTML:_[A].html});
          }else {
    		  $=Sigma.$e("button",{id:this.domId+"_"+_[A].id,className:"gt-input-button",innerHTML:_[A].text});
              Sigma.U.addEvent($,"click",_[A].onclick);
          }
        }
        this.buttonZone.appendChild($);
      }
    }
    this.bodyDiv.appendChild(this.buttonZone);
  },setTitle:function($){
    this.titleDiv.innerHTML=this.titleRender($);
  }};
Sigma.Dialog=Sigma.Widget.extend(Sigma.DialogDefault);
Sigma.dd4=function(C,$){
  C=Sigma.$grid(C);
  $=$||{};
  $.checkType=$.checkType||"checkbox";
  $.canCheck=$.canCheck||function($){
    return !$.hidden;
  };
  function A($,_){
    var A=$.canCheck===true||$.canCheck(_)!==false;
    return "<input type=\""+$.checkType+"\" name=\""+$.name+"\" value=\""+$.value(_)+"\" class=\"gt-f-check\" "+($.checked(_)?" checked=\"checked\" ":"")+(!A?" disabled=\"disabled\" ":"")+" />";
  }
  function B($){
    return $.checkType=="checkbox"?"<input type=\"checkbox\" name=\""+$.name+"\" class=\"gt-f-totalcheck\" />":"<input type=\"radio\" name=\""+$.name+"\" />";
  }
  var _=C.columnList,D=["<table class=\"gt-table\" style=\"margin-left:0px\" cellSpacing=\"0\"  cellPadding=\"0\" border=\"0\" >","<col style=\"width:25px\" /><col style=\"width:105px\" />","<thead>",Sigma.T_G.rowStart(C,0),Sigma.T_G.cellStartHTML,B($),Sigma.T_G.cellEndHTML,Sigma.T_G.cellStartHTML,C.getMsg("COLUMNS_HEADER"),Sigma.T_G.cellEndHTML,Sigma.T_G.rowEndHTML,"</thead>","<tbody>"];
  for(var E=0;E<_.length;E++){
    D.push([Sigma.T_G.rowStart(C,E),Sigma.T_G.cellStartHTML,A($,_[E]),Sigma.T_G.cellEndHTML,Sigma.T_G.cellStartHTML,_[E].header||_[E].title,Sigma.T_G.cellEndHTML,Sigma.T_G.rowEndHTML].join(""));
  }
  D.push("</tbody></table>");
  return D.join("\n");
};
Sigma.checkChecked=function(D){
  D=Sigma.$grid(D);
  var F=D.chkAll,_=Sigma.U.Lu8("td",F),$=Sigma.U.getCellIndex(_),H=D.getAllRows(),C=0;
  for(var G=0,E=H.length;G<E;G++){
    var B=H[G].cells[$];
    if(B){
      var A=B.getElementsByTagName("input")[0];
      if(A&&D.checkedRows[A.value]){
        A.checked=true;
        C++;
      }
    }
  }
  F.checked=C==H.length;
};
Sigma.ad2=function(C,E){
  var $=C+"ColCheck",_=E.gridId,G=_+"_"+C+"ColDialog",D=Sigma.$grid(_),B=function(){
    var H=Sigma.$(G+"_div"),B=(Sigma.U.getTagName(H)=="TABLE")?H:H.getElementsByTagName("table")[0],F=B.tBodies[0],A=F.getElementsByTagName("input"),C=Sigma.U.getCheckboxState(A,$),I=[],J;
    for(J=0;J<D.columnList.length;J++){
      I.push(D.columnList[J].id);
    }
    for(J=0;J<I.length;J++){
      var _=D.columnMap[I[J]];
      if(C[_.id]){
        _[E.checkFn]();
      }else {
        _[E.uncheckFn]();
      }
    }
    if(E.autoClose!==false){
      D.Ao9();
      Sigma.WidgetCache[G].close();
    }
  },A=function(){
    Sigma.WidgetCache[G].close();
  },F=new Sigma.Dialog({id:G,gridId:_,title:E.title,width:260,height:220,buttonLayout:"v",body:["<div id=\""+G+"_div"+"\" onclick=\"Sigma.mc1.xj7(event)\" class=\"gt-column-dialog\" >","</div>"].join(""),buttons:[{text:D.getMsg("TEXT_OK"),onclick:B},{text:D.getMsg("TEXT_CLOSE"),onclick:A}],afterShow:function(){
      var A=Sigma.$grid(this.gridId),_=Sigma.dd4(this.gridId,{type:"checkbox",name:$,value:function($){
          return $.id;
        },checked:E.checkValid,checkType:E.checkType,canCheck:E.canCheck});
      Sigma.$(this.id+"_div").innerHTML=_;
    }});
  return F;
};
Sigma.fo0=function(I){
  var B=I.gridId,H=Sigma.$grid(B),K=B+"_filterDialog";
  H.justShowFiltered=I.justShowFiltered===true?true:(I.justShowFiltered===false?false:H.justShowFiltered);
  H.afterFilter=I.afterFilter||H.afterFilter;
  var A=function(){
    if(H._noFilter){
      F();
      H._noFilter=false;
    }
    var $=Sigma.$(K+"_column_select");
    if($&&$.options.length>0){
      var _=$.value,A=$.options[$.selectedIndex].text;
      Sigma.$(K+"_div").appendChild(Sigma.bC3(H,_,A));
    }
  },F=function(){
    Sigma.$(K+"_div").innerHTML="";
  },D=function(){
    var F=Sigma.$(K+"_div"),D=[],C=F.childNodes;
    for(var J=0;J<C.length;J++){
      if(Sigma.U.getTagName(C[J])=="DIV"&&C[J].className=="gt-filter-item"){
        var A=C[J].childNodes[1],B=C[J].childNodes[2],_=C[J].childNodes[3].firstChild,G=Sigma.U.getValue(A),$=H.columnMap[G];
        if($&&$.fieldName){
          D.push({columnId:G,fieldName:$.fieldName,logic:Sigma.U.getValue(B),value:Sigma.U.getValue(_)});
        }
      }
    }
    if(D.length>0){
      var E=H.filterGrid(D);
    }else {
      H.unfilterGrid();
    }
    if(I.autoClose!==false){
      H.Ao9();
      Sigma.WidgetCache[K].close();
    }
  },C=function(){
    Sigma.WidgetCache[K].close();
  },$=430,E=220,G=$-(Sigma.isBoxModel?16:18),_=E-(Sigma.isBoxModel?93:95),J=new Sigma.Dialog({id:K,gridId:B,title:I.title,width:$,height:E,buttonLayout:"h",body:["<div id=\""+K+"_div\" class=\"gt-filter-dialog\" style=\"width:"+G+"px;height:"+_+"px;\" onclick=\"Sigma.mc1.Ge0(event)\" >","</div>"].join(""),buttons:[{html:Sigma.Na2(H,K+"_column_select")},{text:H.getMsg("TEXT_ADD_FILTER"),onclick:A},{text:H.getMsg("TEXT_CLEAR_FILTER"),onclick:F},{breakline:true},{text:H.getMsg("TEXT_OK"),onclick:D},{text:H.getMsg("TEXT_CLOSE"),onclick:C}],afterShow:function(){
      var E=Sigma.$grid(this.gridId),D=E.filterInfo||[];
      F();
      for(var I=0;I<D.length;I++){
        var G=D[I].columnId,A=E.getColumn(G),H=(A.header||A.title),C=Sigma.bC3(E,G,H),_=C.childNodes[1],B=C.childNodes[2],$=C.childNodes[3].firstChild;
        Sigma.U.setValue(_,G);
        Sigma.U.setValue(B,D[I].logic);
        Sigma.U.setValue($,D[I].value);
        Sigma.$(this.id+"_div").appendChild(C);
      }
      if(D.length<1){
        Sigma.$(this.id+"_div").innerHTML="<div style=\"color:#999999;margin:10px;\">"+E.getMsg("DIAG_NO_FILTER")+"</div>";
        E._noFilter=true;
      }
    }});
  return J;
};
Sigma.Na2=function(B,$){
  B=Sigma.$grid(B);
  var _=["<select"+($?(" id=\""+$+"\" "):" ")+" class=\"gt-input-select\">"];
  for(var C=0;C<B.columnList.length;C++){
    var A=B.columnList[C];
    if(A&&A.filterable!==false){
      _.push("<option value=\""+A.id+"\" >"+(A.header||A.title)+"</option>");
    }
  }
  _.push("</select>");
  return _.join("");
};
Sigma.bh3=function(A,B){
  A=Sigma.$grid(A);
  var _=A.getColumn(B),$;
  if(typeof _.filterField=="function"){
    $=_.filterField(_);
  }else {
    if(_.filterField){
      $=_.filterField;
    }
  }
  $=$||"<input type=\"text\" class=\"gt-input-text gt-filter-field-text\" value=\"\" />";
  return "<div class=\"gt-filter-field-box\">"+$+"</div>";
};
Sigma.bC3=function(C,D,E){
  C=Sigma.$grid(C);
  var B=Sigma.$e("div",{className:"gt-filter-item"}),A="<input type=\"text\" readonly=\"readonly\" class=\"gt-input-text gt-filter-col-text\" value=\""+E+"\" />";
  A+="<input type=\"hidden\"  value=\""+D+"\" />";
  var $=Sigma.bh3(C,D),_="<button class=\"gt-input-button gt-filter-del\" >"+C.getMsg("TEXT_DEL")+"</button>"+"<button class=\"gt-input-button gt-filter-up\" >"+C.getMsg("TEXT_UP")+"</button>"+"<button class=\"gt-input-button gt-filter-down\" >"+C.getMsg("TEXT_DOWN")+"</button>";
  B.innerHTML=A+Sigma.T_D.Hm4+$+_;
  return B;
};
Sigma.mc1={currentElement:null,Ge0:function(A){
    A=A||window.event;
    var E=Sigma.U.getEventTarget(A),_=Sigma.U.Lu8("table",null,A,10);
    if(Sigma.U.getTagName(E)=="BUTTON"){
      var D=" "+E.className,$=E.parentNode;
      if(D.indexOf(" gt-filter-del")>=0){
        Sigma.U.removeNode($);
      }else {
        if(D.indexOf(" gt-filter-up")>=0){
          var B=$.previousSibling;
          if(B){
            $.parentNode.insertBefore($,B);
          }
        }else {
          if(D.indexOf(" gt-filter-down")>=0){
            var C=$.nextSibling;
            if(C){
              $.parentNode.insertBefore(C,$);
            }
          }
        }
      }
    }
  },xj7:function(A){
    A=A||window.event;
    var D=Sigma.U.getEventTarget(A),_=Sigma.U.Lu8("table",null,A,10);
    if(!D||(D.type!="checkbox"&&D.type!="radio")){
      return ;
    }
    if(Sigma.U.hasClass(D,"gt-f-totalcheck")){
      var C=_.tBodies[0],$=C.getElementsByTagName("input");
      for(var F=0;F<$.length;F++){
        if($[F].name==D.name&&$[F].type==D.type){
          $[F].checked=D.checked;
        }
      }
    }else {
      if(Sigma.U.hasClass(D,"gt-f-check")){
        var B=_.tHead,E=B.getElementsByTagName("input")[0];
        if(E){
          E.checked=false;
        }
      }
    }
  }};
Sigma.EditorDefault={gridId:null,left:0,top:0,render:Sigma.$empty,validator:null,isFocus:Sigma.$empty,onKeyPress:null,onClick:null,errMsg:null,isActive:null,valueDom:null,locked:false,Tb3:function($){
    if($){
      Sigma.$extend(this,$);
    }
    this.validator=this.validator||this.defaultValidator;
    if(Sigma.$type(this.validRule,"string")){
      this.validRule=this.validRule.split(",");
    }
    if(this.required){
      this.validRule=["required"].concat(this.validRule);
    }
    this.dom=this.dom||Sigma.$e("div",{className:"gt-editor-container"});
    Sigma.U.addEvent(this.dom,"click",function($){
      Sigma.U.stopEvent($);
    });
    Sigma.U.addEvent(this.dom,"dblclick",function($){
      Sigma.U.stopEvent($);
    });
  },destroy:function(){
    this.container=null;
    Sigma.U.removeNode(this.valueDom);
    this.valueDom=null;
    Sigma.U.removeNode(this.dom);
    this.dom=null;
    Sigma.WidgetCache[this.id]=null;
    delete Sigma.WidgetCache[this.id];
  },setValue:function(B,_,$,C,E,D,A){
	  //桑志豪这里修改：原因null会加到input上去
	if(B!=null&&B!='null')
		Sigma.U.setValue(this.valueDom,B);
	//startEdit
	if(this.onClick!=null){
		Sigma.U.addEvent(this.valueDom,'click',this.onClick(Sigma.activeGrid.activeCell));
	}
  },getValue:function(B,_,$,C,E,D,A){
    return Sigma.U.getValue(this.valueDom);
  },parseValue:function(B,_,$,C,E,D,A){
    return B;
  },getDisplayValue:function($){
    return $===undefined?this.getValue():$;
  },defaultValidator:function(H,D,C,I,E){
    var F=[],K=[].concat(E.validRule);
    for(var B=0;B<K.length;B++){
      var G=K[B],J=[H];
      if(Sigma.$type(G,"array")&&G.length>0){
        G=G[0];
        J=J.concat(G.slice(1));
      }
      var $=Sigma.Validator.getValidator(G),A=true;
      if(Sigma.$type($,"function")){
        A=$.apply($,J);
      }
      if(A!==true){
        var _=Sigma.Validator.getMessage(this.validRule[B])||String(A);
        F.push(_);
      }
    }
    if(!F||F.length<1){
      F="";
    }
    return F;
  },yb4:function(A,_,$,B){
    if(!this.validRule&&!this.validator){
      return true;
    }
    A=(A===undefined||A===null)?this.getValue():A;
    var C=this.validator(A,_,$,B,this);
    if(C===true||C===undefined||C===null||C===""){
      return true;
    }
    return C;
  },active:function(){
    Sigma.U.focus(this.valueDom);
  }};
Sigma.Editor=Sigma.Widget.extend(Sigma.EditorDefault);
Sigma.DialogEditor=Sigma.Editor.extend(Sigma.$extend({vP3:function(){
    if(!this.dom&&this.render){
      var $=Sigma.$grid(this.gridId);
      this.render($.gridMask);
    }
    return this.dom;
  }},Sigma.DialogDefault));
Sigma.EditDialog=Sigma.DialogEditor;
Sigma.Calendar=window.Calendar||{trigger:Sigma.$empty};
Sigma.$extend(Sigma.Editor,{vn4:function($,_){
    if(Sigma.$type($,"function")){
      $=$(_);
    }
    if(($ instanceof Sigma.DialogEditor)||($ instanceof Sigma.Dialog)){
      $.gridId=_.id;
      $.container=_.gridMask;
      return $;
    }
    if($ instanceof Sigma.Editor){
      return $;
    }
    $=Sigma.$type($,"string")?{type:$}:$;
    return $&&Sigma.Editor.EDITORS[$.type]?Sigma.Editor.EDITORS[$.type]($):null;
  },register:function(_,$){
    if($ instanceof Sigma.Editor){
      $=function(){
        return $;
      };
    }
    Sigma.Editor.EDITORS[_]=$;
  },EDITORS:{text:function($){
      $=new Sigma.Editor($);
      $.valueDom=Sigma.$e("input",{value:$.defaultValue||"",className:"gt-editor-text"});
      $.dom.appendChild($.valueDom);
      
      //桑志豪这里修改过事件
      //startEdit
      if($.onKeyPress!=null){
    	  Sigma.U.addEvent($.valueDom,'keyup',function(){$.onKeyPress(1);});
      }
      return $;
    },textarea:function($){
      $=new Sigma.Editor($);
      $.valueDom=Sigma.$e("textarea",{style:{width:$.width||"100px",height:$.height||"50px"},value:$.defaultValue||"",className:"gt-editor-text"});
      $.dom.appendChild($.valueDom);
      $.dom.style.width=$.valueDom.style.width;
      $.dom.style.height=$.valueDom.style.height;
      $.setSize=Sigma.$empty;
      return $;
    },select:function($){
      $=new Sigma.Editor($);
      $.valueDom=Sigma.U.createSelect($.options,null,{className:"gt-editor-text"});
      $.dom.appendChild($.valueDom);
      $.getDisplayValue=function($){
        $=$===undefined?this.getValue():$;
        for(var _=0;_<this.valueDom.options.length;_++){
          if(String(this.valueDom.options[_].value)===String($)){
            return this.valueDom.options[_].text||this.valueDom.options[_].innerHTML;
          }
        }
        return (this.defaultText||this.defaultText==="")?this.defaultText:null;
      };
      return $;
    },checkbox:function($){
      $=new Sigma.Editor($);
      $.valueDom=Sigma.U.createSelect($.options,null,{});
      $.dom.appendChild($.valueDom);
      return $;
    },date:function(_){
      _=new Sigma.Editor(_);
      var $=Sigma.$e("input",{value:_.defaultValue||"",className:"gt-editor-text",style:{width:"78px",styleFloat:"left"}}),A=Sigma.$e("input",{value:_.defaultValue||"",className:"gt-editor-date",styleFloat:"left"});
      _.dom.style.overflow="hidden";
      _.dom.appendChild($);
      _.dom.appendChild(A);
      _.setSize=function($,_){
        this.width=$||this.width;
        this.height=_||this.height;
        if(this.width/1&&this.width>0){
          this.dom.style.width=(this.width-1)+"px";
        }
        if(this.height/1&&this.height>0){
          this.dom.style.height=(this.height-1)+"px";
        }
        this.dom.firstChild.style.width=(this.width-20)+"px";
      };
      var B=function($){
        _.onClose&&_.onClose();
        $.hide();
      },C=function(){
        var A=_.format||"%Y-%m-%d";
        A=Sigma.U.replaceAll(A,"yyyy","%Y");
        A=Sigma.U.replaceAll(A,"MM","%m");
        A=Sigma.U.replaceAll(A,"dd","%d");
        A=Sigma.U.replaceAll(A,"HH","%H");
        A=Sigma.U.replaceAll(A,"mm","%M");
        A=Sigma.U.replaceAll(A,"ss","%S");
        Sigma.Calendar.trigger({inputField:$,ifFormat:A,showsTime:true,button:"date_button",singleClick:true,onClose:B,step:1});
      };
      Sigma.U.addEvent(A,"click",C);
      _.valueDom=$;
      return _;
    }}});
Sigma.Validator={hasDepend:/^datetime|^date|^time|^minlength|^maxlength|^DT|^D|^T|^MINL|^MAXL/,hasArgument:/^equals|^lessthen|^EQ|^LT/,DATE_FORMAT:"yyyy-MM-dd",TIME_FORMAT:"HH:mm:ss",DATETIME_FORMAT:"yyyy-MM-dd HH:mm:ss",KeyMapping:{"R":"required","DT":"datetime","D":"date","T":"time","E":"email","ID":"idcard","N":"number","int":"integer","I":"integer","F":"float","M":"money","RG":"range","EQ":"equals","LT":"lessthen","GT":"greatethen","U":"url","ENC":"enchar","CNC":"cnchar","MINL":"minlength","MAXL":"maxlength"},RegExpLib:{"email":/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,"number":/^\d+$/,"integer":/^[1-9]\d*|0$/,"float":/^([1-9]\d*\.\d+|0\.\d+|[1-9]\d*|0)$/,"money":/^([1-9]\d*\.\d{1,2}|0\.\d{1,2}|[1-9]\d*|0)$/,"telephone":/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,16})+$/,"enchar":/^[ \w]*$/,"cnchar":/^[\u4E00-\u9FA5\uF900-\uFA2D]*$/,"idcard":/^(\d{15}|\d{18}|\d{17}X?)$/i},getValidator:function($){
    return Sigma.Validator[$];
  },getMessage:function(msgKey){
    var msg=Sigma.Msg.Validator["default"][msgKey];
    if(!msg){
      msg=Sigma.Msg.Validator["default"][Sigma.Validator.KeyMapping[msgKey]];
    }
    var _format=((Sigma.Validator.KeyMapping[msgKey]||msgKey)+"_FORMAT").toUpperCase();
    _format=Sigma.Validator[_format];
    var fI8=(" "+msg).split(/\{[0-9]/).length-1;
    for(var i=1;i<=fI8;i++){
      var ns=arguments[i];
      if(i==2){
        ns=ns||_format;
      }
      var rex;
      eval("rex = /{("+(i-1)+"[^#}]*)#?([^}]*)}/;");
      var ostring=rex.exec(msg);
      if(ostring&&ostring.length>2){
        if(!ns){
          msg=Sigma.U.replaceAll(msg,ostring[0]," "+ostring[2]+" ");
        }else {
          msg=Sigma.U.replaceAll(msg,ostring[0]," "+ns+" ");
        }
      }
    }
    return msg;
  },"required":function($){
    if($===null||$===undefined){
      return false;
    }
    if(typeof ($)!="string"&&$.length){
      if($.length<1){
        return false;
      }
      for(var A=0;A<$.length;A++){
        var _=Sigma.Validator.required($[A]);
        if(_){
          return true;
        }
      }
      return false;
    }
    return Sigma.U.trim($+"").length>0;
  },"telephone":function($){
    if(!Sigma.Validator.RegExpLib.telephone.exec($)){
      return false;
    }
    return true;
  },"email":function($){
    return $&&Sigma.Validator.RegExpLib["email"].test($);
  },"enchar":function($){
    return $&&Sigma.Validator.RegExpLib["enchar"].test($);
  },"cnchar":function($){
    return $&&Sigma.Validator.RegExpLib["cnchar"].test($);
  },"number":function($){
    return !isNaN($/1);
  },"integer":function($){
    return (String($).indexOf(".")<0)&&!isNaN($/1)&&Sigma.Validator.RegExpLib["integer"].test(Math.abs($));
  },"float":function($){
    return !isNaN($/1)&&Sigma.Validator.RegExpLib["float"].test(Math.abs($));
  },"money":function($){
    return !isNaN($/1)&&Sigma.Validator.RegExpLib["money"].test($);
  },"idcard":function(_){
    if(!_||_.length<15||!Sigma.Validator.RegExpLib["idcard"].test(_)){
      return false;
    }
    var $;
    if(_.length==18){
      $=_.substr(6,8);
    }else {
      $="19"+_.substr(6,6);
    }
    return Sigma.Validator.date($,"YYYYMMDD");
  },"date":function(K,A){
    K=[].concat(K);
    if(!A||A.length<1){
      A=Sigma.Validator.DATE_FORMAT;
    }
    A=A.toUpperCase();
    var $=A.replace(/([$^.*+?=!:|\/\\\(\)\[\]\{\}])/g,"\\$1");
    $=$.replace("YYYY","([0-9]{4})");
    $=$.replace("YY","([0-9]{2})");
    $=$.replace("MM","(0[1-9]|10|11|12)");
    $=$.replace("M","([1-9]|10|11|12)");
    $=$.replace("DD","(0[1-9]|[12][0-9]|30|31)");
    $=$.replace("D","([1-9]|[12][0-9]|30|31)");
    $="^"+$+"$";
    var C=new RegExp($),J=0,H=1,D=1,I=A.match(/(YYYY|YY|MM|M|DD|D)/g);
    for(var _=0;_<K.length;_++){
      if(!C.test(K[_])){
        return false;
      }
      var G=C.exec(K[_]);
      for(var E=0;E<I.length;E++){
        switch(I[E]){
        case "YY":
        case "yy":
          var F=Number(G[E+1]);
          J=1900+(F<=30?F+100:F);
          break ;
        case "YYYY":
        case "yyyy":
          J=Number(G[E+1]);
          break ;
        case "M":
        case "MM":
          H=Number(G[E+1]);
          break ;
        case "D":
        case "d":
        case "DD":
        case "dd":
          D=Number(G[E+1]);
          break ;
        }
      }
      var B=(J%4===0&&(J%100!==0||J%400===0));
      if(D>30&&(H==2||H==4||H==6||H==9||H==11)){
        return false;
      }
      if(H==2&&(D==30||D==29&&!B)){
        return false;
      }
    }
    return true;
  },"time":function(_,B){
    _=[].concat(_);
    if(!B||B.length<1){
      B=Sigma.Validator.TIME_FORMAT;
    }
    var $=B.replace(/([.$?*!=:|{}\(\)\[\]\\\/^])/g,"\\$1");
    $=$.replace("HH","([01][0-9]|2[0-3])");
    $=$.replace("H","([0-9]|1[0-9]|2[0-3])");
    $=$.replace("mm","([0-5][0-9])");
    $=$.replace("m","([1-5][0-9]|[0-9])");
    $=$.replace("ss","([0-5][0-9])");
    $=$.replace("s","([1-5][0-9]|[0-9])");
    $="^"+$+"$";
    var C=new RegExp($);
    for(var A=0;A<_.length;A++){
      if(!C.test(_[A])){
        return false;
      }
    }
    return true;
  },"datetime":function(_,D){
    _=[].concat(_);
    var C=/^\S+ \S+$/;
    if(!D||D.length<1){
      D=Sigma.Validator.DATETIME_FORMAT;
    }else {
      if(!C.test(D)){
        return false;
      }
    }
    for(var A=0;A<_.length;A++){
      if(!C.test(_[A])){
        return false;
      }
      var $=_[A].split(" "),E=D.split(" "),B=Sigma.Validator.date($[0],E[0])&&Sigma.Validator.time($[1],E[1]);
      if(!B){
        return false;
      }
    }
    return true;
  },"range":function(A,$,_){
    if(!Sigma.$chk($)){
      return A<=_;
    }else {
      if(!Sigma.$chk(_)){
        return A>=$;
      }
    }
    return A>=$&&A<=_;
  },"equals":function($,_){
    _=[].concat(_);
    for(var A=0;A<_.length;A++){
      if($==_){
        return true;
      }
    }
    return false;
  },"lessthen":function($,_){
    _=[].concat(_);
    for(var A=0;A<_.length;A++){
      if($<=_){
        return true;
      }
    }
    return false;
  },"greatethen":function($,_){
    _=[].concat(_);
    for(var A=0;A<_.length;A++){
      if($>=_){
        return true;
      }
    }
    return false;
  },"minlength":function($,_){
    return Sigma.$chk($)&&($+"").length>=_;
  },"maxlength":function($,_){
    return Sigma.$chk($)&&($+"").length<=_;
  }};
(function(){
  for(var $ in Sigma.Validator.KeyMapping){
    Sigma.Validator[$]=Sigma.Validator[Sigma.Validator.KeyMapping[$]];
  }
})();
Sigma.Chart=Sigma.$class({Tb3:function($){
    this.defaultColor="66BBFF";
    this.type="column2D";
    this.swfPath="./charts/";
    this.swf=Sigma.Chart.SWFMapping[this.type];
    this.width="100%";
    this.height="100%";
    this.data=null;
    this.container=null;
    this.chart=null;
    Sigma.$extend(this,$);
    this.swf=Sigma.Chart.SWFMapping[this.type]||this.swf;
    if(this.swfPath.lastIndexOf("/")==this.swfPath.length-1){
      this.swfPath=this.swfPath.substring(0,this.swfPath.length-1);
    }
    this.container=Sigma.$(this.container);
    this.chart=this.chart||new FusionCharts(this.swfPath+"/"+this.swf,this.container.id+"_chart",this.width,this.height);
  },Sf4:function(_){
    if(_.isNull){
      if(_.name){
        _={name:_.name};
      }else {
        return "";
      }
    }else {
      if(_.color){
        _.color=_.color||this.defaultColor;
      }
    }
    var $=[];
    for(var A in _){
      $.push(A+"='"+_[A]+"'");
    }
    return ;
  },DT6:function(C){
    C=C||this.data;
    var E=[],A=[];
    for(var G=0;G<C.length;G++){
      var $=C[G],_,D,F,B;
      if($ instanceof Array){
        D=$[0];
        F=$[1];
        B=$[2];
        B=(F===null||F===undefined)?null:(B||this.defaultColor);
        D=(D===null||D===undefined)?F:D;
        A=[D!==null&&D!==undefined?"name='"+D+"'":"",F!==null&&F!==undefined?"value='"+F+"'":"",B!==null&&B!==undefined?"color='"+B+"'":""].join(" ");
      }else {
        if($){
          A=this.Sf4($);
        }
      }
      _=["<set",A,"/>"];
      _=_.join(" ");
      if(_=="<set />"||(F===null||F===undefined)){
      }
      E.push(_);
    }
    this.setsXML=E.join("");
    return this.setsXML;
  },QX5:function(A,$){
    $=$||this.setsXML;
    var _=["<graph","caption='"+(this.caption||"")+"'","subCaption='"+(this.subCaption||"")+"'","outCnvBaseFontSize='12'","animation='0'"];
    _.push(">"+$+"</graph>");
    this.chartXML=_.join(" ");
    return this.chartXML;
  },updateChart:function($,_){
    $=$||this.container;
    _=_||this.chartXML;
    window.updateChartXML&&(window.updateChartXML($,_));
  },qo7:function($,_){
    this.data=_||this.data;
    this.DT6();
    this.QX5();
    $=$||this.container;
    this.chart.setDataXML(this.chartXML);
    this.chart.render($);
  }});
Sigma.Chart.SWFMapping={"column2D":"FCF_Column2D.swf","pie3D":"FCF_Pie3D.swf"};