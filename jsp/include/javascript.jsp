<script src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" type="text/javascript" djConfig="parseOnLoad: true"></script>
<script type="text/javascript" src="swf/swfobject.js"></script>
<script type="text/javascript">
    var browserName = navigator.appName;
    var browserVer = parseInt(navigator.appVersion);
    var version;
    if (browserName == "Netscape" && browserVer >= 3) version ="n3";
    else {version = "n2"};
    if (navigator.appVersion.substring(0,3) == '4.0') {version="n3"};
    if (version == "n3") {
        nav1on = new Image;
        nav1on.src = "images/bt_1_lit.png";
        nav1off = new Image;
        nav1off.src = "images/bt_1.png";
        nav2on = new Image;
        nav2on.src = "images/bt_2_lit.png";
        nav2off = new Image;
        nav2off.src = "images/bt_2.png";
        nav3on = new Image;
        nav3on.src = "images/bt_3_lit.png";
        nav3off = new Image;
        nav3off.src = "images/bt_3.png";
        nav4on = new Image;
        nav4on.src = "images/bt_4_lit.png";
        nav4off = new Image;
        nav4off.src = "images/bt_4.png";
        nav5on = new Image;
        nav5on.src = "images/bt_5_lit.png";
        nav5off = new Image;
        nav5off.src = "images/bt_5.png";
    }
    function img_act(imgName) {
        if (version == "n3") {
            imgOn = eval(imgName + "on.src");
            document [imgName].src = imgOn;
        }
    }
    function img_inact(imgName) {
        if (version == "n3") {
            imgOff = eval(imgName + "off.src");
            document [imgName].src = imgOff;
        }
    }
    dojo.require("dijit.Dialog");
    dojo.require("dijit.Menu");
    dojo.require("dijit.Tooltip");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dijit.form.Select");
    dojo.require("dijit.layout.ContentPane");
    dojo.require("dijit.ProgressBar");
    dojo.require("dojox.form.FileInput");
    dojo.require("dojox.form.FileInputAuto");
    dojo.require("dojo.dnd.Source")
    dojo.require("dojo.parser");
    //dojo.require("dojo.io.IframeIO");

    function exitPopup (idLink){
                var link = dojo.byId (idLink);
                var w = getDialog (link);
                if (w != undefined){
                    w.hide();
                    w.destroy (false);
                }
    }

    function go (idLink, action){
        if (idLink.indexOf ("menu") != -1){
            dojo.byId (idLink).style.display = "none";
        }else{
            if (idLink != ''){
                var link = dojo.byId (idLink);
                var w = getDialog (link);
                if (w != undefined){
                    w.hide();
                    w.destroy (false);
                }
            }
        }
        var xhrArgs = {
            handleAs: "text",
            url:action,
            preventCache:true,
            load: function(data) {
                var pageContent = dojo.byId ('pageContent');
                pageContent.innerHTML = data;
                onNewFrame (pageContent);
            },
            error: function(error) {
            }
        }
        dojo.xhrGet(xhrArgs);
    }

    function goAdmin (){
      go("playlist-3", "AdminSection.act?action=index");
    }

    function onNewFrame (elemContent){
        window.setTimeout (function (){
            dojo.parser.parse (elemContent);}, 0);
        window.setTimeout (function (){
            transformButtons (elemContent)}, 0);
        window.setTimeout (function (){
            transformLinks (elemContent)}, 0);
        window.setTimeout (function (){
            dojo.query("script").forEach(
            function(tag) {
                var textJS = tag.innerHTML;
                eval (textJS);
            })}, 0);
    }

    function getDialog (node){
        var parentDialog = node;
        while (parentDialog != undefined &&
            parentDialog.className != undefined &&
            parentDialog.className.indexOf("dijitDialog ") == -1){
            parentDialog = parentDialog.parentNode;
        }

        var w = undefined;
        if (parentDialog != undefined){
            w = dijit.byId(parentDialog.id);
        }
        return w;
    }

    function transformButtons (elemContent){
        if (elemContent == undefined){
            elemContent = document;
        }
        dojo.query("input[type='reset']", elemContent).forEach (
        function (inputTag){
            inputTag.onclick = function(e){
                e.preventDefault();
                e.stopPropagation();
                dojo.stopEvent(e);
                var btn = e.target;
                var w = getDialog (btn.parentNode);
                if (w != undefined){
                    w.hide();
                    w.destroy (false);
                    var elem = dojo.byId (w.id);
                    if (elem != null){
                        elem.parentNode.removeChild(elem);
                    }
                }
            }});

        dojo.query("input[type='submit']", elemContent).forEach (
        function (inputTag){
            inputTag.onclick = function(e){
                e.preventDefault();
                e.stopPropagation();
                dojo.stopEvent(e);
                var btn = e.target;
                var parentForm = btn.parentNode;
                while (parentForm.tagName.toLowerCase() != "form"){
                    parentForm = parentForm.parentNode;
                }
                var nocanvasInput = document.createElement ("input");
                nocanvasInput.type = "hidden";
                nocanvasInput.name = "nocanvas";
                nocanvasInput.value = "1";
                parentForm.appendChild (nocanvasInput);
                var w = getDialog (btn.parentNode);
                if (w != undefined){
                    w.hide();
                    w.destroy (false);
                    var elem = dojo.byId (w.id);
                    if (elem != null){
                        elem.parentNode.removeChild(elem);
                    }
                }

                var xhrArgs = {
                    form: parentForm,
                    handleAs: "text",
                    preventCache:true,
                    load: function(data) {
                        var pageContent = dojo.byId ('pageContent');
                        pageContent.innerHTML = data;
                        onNewFrame (pageContent);
                    },
                    error: function(error) {
                    }
                }
                dojo.xhrPost(xhrArgs);
            }});

    }

    function transformLinks (elemContent){
        if (elemContent == undefined){
            elemContent = document;
        }
        var i = 0;
        dojo.query("a", elemContent).forEach(
        function(tag) {
            if (tag.href != undefined && tag.href != "#" &&
                tag.href.indexOf ("javascript:") != 0){
                var action = tag.href;
                if (action.lastIndexOf ('#') == action.length - 1){
                    action = action.substring (0, action.length - 1);
                }
                if (action.indexOf ('?') == -1){
                    action += "?nocanvas=1";
                }else{
                    action += "&nocanvas=1";
                }
                tag.id = "link" + i;
                tag.href = "javascript:go ('link" + i + "', '" + action + "')";
                i++;
            }
        });
    }

    function toggleMenu (idDiv){
        for (var idMenu in menus){
            dojo.byId (menus [idMenu]).style.display = "none";
        }
        if (idDiv != undefined){
            var div = dojo.byId (idDiv);
            if (div.style.display == "none"){
                div.style.display = "block";
            }else{
                div.style.display = "none";
            }
        }
    }

    function popupDisplay (id){
        dojo.addOnLoad(function(){
            var w = dijit.byId(id);
            w.show();
            w.onHide = function () {dojo.hitch (this, window.setTimeout (function (){
                    w.hide();
                    w.destroy (false);
                    var div = dojo.byId (id);
                    if (div != undefined){
                        var parent = div.parentNode;
                        parent.removeChild (div);
                    }
                }, 1))};
        });
    }

    function retaille (event){
        dojo.byId ('musiques').style.height  = (window.innerHeight - 165) + "px";
        dojo.byId ('playlists').style.height = (window.innerHeight - 165) + "px";
        if (dojo.byId ('plContentUl') != undefined){
          dojo.byId ('plContentUl').style.height = (window.innerHeight - 225) + "px";
        }
    }

    function changeSelectedPlaylist (num){
        var sp = selectedPlaylist;
        if (num != sp || dojo.byId('playlist' + num).className.indexOf ('notselected') != -1){
            dojo.byId('playlist' + num).className = 'selected dojoDndItem';
            if (sp >= 0 && dojo.byId('playlist' + sp) != undefined){
                dojo.byId('playlist' + sp).className = 'notselected dojoDndItem';
            }
            if (selectedQuery != undefined && dojo.byId('query-' + selectedQuery) != undefined){
                dojo.byId('query-' + selectedQuery).className = 'notselected';
            }
            selectedPlaylist = num;
            selectedMusic = -1;
            go ('', 'Playlist.act?action=play&nocanvas=1&plId=' + num + '&sp=' + sp);
        }
    }

    function changeSelectedQuery (query){
        var sq = selectedQuery;
        if (query != sq || dojo.byId('query-' + query).className.indexOf ('notselected') != -1){
            dojo.byId('query-' + query).className = 'selected';
            if (sq != undefined && dojo.byId('query-' + sq) != undefined){
                dojo.byId('query-' + sq).className = 'notselected';
            }
            if (selectedPlaylist >= 0 && dojo.byId('playlist' + selectedPlaylist) != undefined){
                dojo.byId('playlist' + selectedPlaylist).className = 'notselected dojoDndItem';
            }
            selectedQuery = query;
            selectedMusic = -1;
            go ('', 'Search.act?nocanvas=1&query=' + query + '&sq=' + sq);
        }
    }

    function changeSelectedMusic (num, id){
        if (num != selectedMusic){
            dojo.byId('music' + num).className = 'selected dojoDndItem';
            if (selectedMusic != -1 && dojo.byId('music' + selectedMusic) != undefined){
                dojo.byId('music' + selectedMusic).className = 'notselected dojoDndItem';
            }
            selectedMusic = num;
            dewplayerPlay (num, id);
        }
    }

    function musicInPlaylist (playlist){
        go ('', "/Playlist.act?action=addMusic&mId=" +
            selectedDragMusic + "&plId=" + playlist);
    }

    function musicInPosition (position){
        go ('', "/Playlist.act?action=setPosition&epId=" +
            selectedDragPosition + "&plId=" + selectedPlaylist + "&pos=" + position);
    }

    function dewplayerPlay (num, id){
        var dewplayer = dojo.byId ('dewplayer');
        if (dewplayer != undefined){
            dewplayer.dewgo(num + 1);
            //dewplayer.dewset("/Music.act?mId=" + id);
        }
    }

    function chercher (query){
     var elem = document.createElement ("li");
     dojo.byId ('recherchesPlaylists').firstChild.appendChild (elem);
     elem.innerHTML = "\"" + query + "\"";
     elem.id = "query-" + query;
     elem.onclick = function (e){
         changeSelectedQuery (query);
     }
    }

    function newsStreamFirst (){
    	window.setTimeout ("newsStream ()", 1000);
    }
    
    function newsStream (){
        if (newsStreamInit == 1){
            return;
        }
        newsStreamInit = 1;
        var xhrArgs = {
            handleAs: "text",
            url:"Notifications.act?action=liveChanges",
            preventCache:true,
            handle: function(data, ioargs) {
                if (ioargs.xhr.status == 200 && data != "stop") {
                  var pageContent = dojo.byId ('pageContent');
                  pageContent.innerHTML = data;
                  newsStreamInit = 0;
                  onNewFrame (pageContent);
                  window.setTimeout("newsStream ()", 0);
                }
            },
            error: function(error) {
            }
        }
        dojo.xhrGet(xhrArgs);
    }

    dojo.addOnLoad (transformButtons);
    dojo.addOnLoad (transformLinks);
    dojo.addOnLoad (retaille);
    dojo.addOnLoad (newsStreamFirst);
</script>

<%@include file="/jsp/include/dewplayer.jsp" %>

<script type="text/javascript">
    var menus = new Array ();
    var newsStreamInit = 0;
    var selectedPlaylist = -4;
    var selectedMusic = -1;
    var selectedQuery = undefined;

    var selectedDragMusic = -1;
    var selectedDragPosition = -1;

    window.onresize = retaille;
</script>