<link id="themeStyles" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/claro/claro.css">

<style type="text/css">
    body {overflow:hidden}
    a {color:black}
    #login {color:white}
    #playlists #global div.selected
                            {list-style-type:disc;font-family:sans-serif}
    #playlists #global div.notselected
                            {list-style-type:none;font-family:sans-serif}
    #playlists #global div:hover {color:black;background-color:#FFFFFF;cursor:pointer}
    #playlistsContent .selected
                            {list-style-type:disc;border-bottom:solid 1px #FFFFFF;font-size:20px;
                             font-family:sans-serif}
    #recherchesPlaylists .selected
                            {list-style-type:disc;border-bottom:solid 1px #FFFFFF;font-size:20px;
                             font-family:sans-serif}
    #playlistsContent .notselected
                            {list-style-type:none;border-bottom:solid 1px #FFFFFF;font-size:20px;
                             font-family:sans-serif}
    #recherchesPlaylists .notselected
                            {list-style-type:none;border-bottom:solid 1px #FFFFFF;font-size:20px;
                             font-family:sans-serif}
    #playlistsContent ul li:hover
                            {border-bottom:solid 1px #FFFFFF;color:black;
                                   background-color:#FFFFFF;cursor:pointer;
                                   list-style-type:none;}
    #recherchesPlaylists ul li:hover
                            {border-bottom:solid 1px #FFFFFF;color:black;
                                   background-color:#FFFFFF;cursor:pointer}
    #playlistsContent ul li {text-decoration:none}
    #recherchesPlaylists ul li {text-decoration:none}
    #recherchesPlaylists:hover {background-color:transparent !important;color:white !important;
                                font-size:20px !important;}
    #recherchesPlaylists {background-color:transparent !important;color:white !important;
                                font-size:20px !important;}
    #musiquesContent ul {font-family:sans-serif;margin-top:20px;margin-bottom:-20px}
    #musiquesContent ul li {font-family:sans-serif}
    #musiquesContent ul li:hover {cursor:pointer}
    #musiquesContent ul li.head {list-style-type:none;}
    #musiquesContent ul li.head div {width:100%;background-color:#FFFFFF;font-weight:bold;
                                     background-image:url('images/gd_table.png');height: 30px}
    #musiquesContent ul li.head div div {float:left;height:20px;width:20%;color:#000000;margin-right:-1px;
                                         padding-top:10px;}
    #musiquesContent ul li div.odd {background-color:#173b80;height:15px;width:100%border-top:solid 1px #577bc0;border-bottom:solid 1px #577bc0}
    #musiquesContent ul li div.even {background-color:#0c1f42;height:15px;width:100%;border-top:solid 1px #4c5f82;border-bottom:solid 1px #4c5f82}
    #musiquesContent ul li div.odd div {float:left;width:20%;height:15px;overflow:auto;border-left: solid 1px #FFFFFF;margin-right:-1px}
    #musiquesContent ul li div.even div {float:left;width:20%;height:15px;overflow:auto;border-left: solid 1px #FFFFFF;margin-right:-1px}

    #musiquesContent ul li div.odd:hover {background-color:#577bc0;border-left-color:black;border-top:solid 1px #577bc0;border-bottom:solid 1px #577bc0}
    #musiquesContent ul li div.even:hover {background-color:#4c5f82;border-left-color:black;border-top:solid 1px #4c5f82;border-bottom:solid 1px #4c5f82}

    #musiquesContent ul li.selected div div {background-color:#FFFFFF;color:#000000;border-left-color:black}
    #musiquesContent ul li.selected {list-style-type:disc;list-style-position: outside;border-top:solid 1px #00000;border-bottom:solid 1px #000000;}
    #musiquesContent ul li.selected div {}
    #musiquesContent ul li.notselected {list-style-type:none}

    #musiquesContent ul li.indispotrue {color:#808080}

    #dewplayer_content {float:left;height:28px;width:300px}
    #dewplayer {float:left;height:28px;width:300px}
    #login {line-height:28px;height:28x;width:223px;overflow:hidden;}
</style>
    <script type="text/javascript">
        if (document.getElementById ('styleForChrome') == undefined &&
            navigator.userAgent.toLowerCase ().indexOf("chrome") != -1){
            document.write ("<span id=\"styleForChrome\"></span>" +
                "<style type=\"text/css\"> " +
                "#musiquesContent ul li.selected {margin-left: -20px;" +
                "margin-top: -15px;padding-left: 20px;}</style>");
        }
    </script>
<!--FileInput.css-->
<style type="text/css">
    .dijitFileInput {
	position:relative;
	height:1.3em;
	/*padding:2px;*/
}

.dijitFileInputReal {
	position:absolute;
	z-index:2;
	filter:alpha(opacity:0);
	opacity:0;
	cursor:pointer;
}
.dijitFileInputRealBlind {
	right:0;
}
.dijitFileInputReal:hover { cursor:pointer; }

.dijitFileInputButton,
.dijitFileInputText {
	border:1px solid #333;
	padding:2px 12px 2px 12px;
	cursor:pointer;
}

.dijitFileInputButton {
	z-index:3;
	visibility:hidden;
}
.dijitFakeInput { position:absolute; top:0; left:0; z-index:1; white-space: nowrap; }

.dijitProgressOverlay {
	display:none;
	width:250px;
	height:1em;
	position:absolute;
	top:0; left:0;
	border:1px solid #333;
	background:#cad2de url('../../../dijit/themes/tundra/images/dijitProgressBarAnim.gif') repeat-x top left;
	padding:2px;
}

/* tundra */
.tundra .dijitProgressOverlay {
	border:1px solid #84a3d1;
	background-color:#cad2de;
}
.tundra .dijitFakeInput input {
	/*font-size: inherit;*/
	padding: 0;
	background:#fff url("../../../dijit/themes/tundra/images/validationInputBg.png") repeat-x top left;
	border:1px solid #9b9b9b;
	line-height: normal;
}
.tundra .dijitFileInputButton,
.tundra .dijitFileInputText {
	border:1px solid #9b9b9b;
	padding:0px 12px 0px 12px; /* .3em .4em .2em .4em; */
	background:#e9e9e9 url("../../../dijit/themes/tundra/images/buttonEnabled.png") repeat-x top;
}

/* Soria */
.soria .dijitProgressOverlay {
	border:1px solid #8BA0BD;
	background-color:#cad2de;
}
.soria .dijitFakeInput input {
	border:1px solid #8BA0BD;
	background:#fff url("../../../dijit/themes/soria/images/validationInputBg.png") repeat-x top left;
	line-height:normal;
	background-position:0 -30px;
	padding:0.2em 0.3em;
}
.soria .dijitFileInputButton,
.soria .dijitFileInputText {
	border:1px solid #8BA0BD;
	padding:2px 12px 2px 12px;
	background:#b7cdee url('../../../dijit/themes/soria/images/buttonEnabled.png') repeat-x;
}

/* Nihilo */
.nihilo .dijitProgressOverlay {
	border:1px solid #DEDEDE;
	background-color:#cad2de;
}
.nihilo .dijitFakeInput input {
	border:1px solid #DEDEDE;
	background:#fff url("../../../dijit/themes/nihilo/images/validationInputBg.png") repeat-x top left;
	line-height:normal;
	background-position:0 -30px;
	padding:0.2em 0.3em;
}
.nihilo .dijitFileInputButton,
.nihilo .dijitFileInputText {
	border:1px solid #DEDEDE;
	padding:2px 12px 2px 12px;
	background:#b7cdee url('../../../dijit/themes/nihilo/images/buttonEnabled.png') repeat-x;
}

/* Claro */
.claro .dijitProgressOverlay {
	border:1px solid #769dc0;
	background-color:#769dc0;
}
.claro .dijitFakeInput input {
	border: 1px solid #bcc8dd;
	background-color: #fff;
	background-repeat: repeat-x;
	background-position: top left;
	background-image:url("../../../dijit/themes/claro/images/textBox_back.png");
	line-height:normal;
	padding:0.2em 0.3em;
}

.claro .dijitFileInputButton,
.claro .dijitFileInputText {
	background-image: url("../../../dijit/themes/claro/form/images/button_back_full.png");
	background-position: center top;
	background-repeat: repeat-x;
	background-color: #cde3f6;
	border: 1px solid #799ab7;
	border-radius: 4px;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	box-shadow:0px 1px 1px rgba(0,0,0,0.2);
	-webkit-box-shadow:0px 1px 1px rgba(0,0,0,0.2);
	-moz-box-shadow: 0px 1px 1px rgba(0,0,0,0.2);
}
</style>