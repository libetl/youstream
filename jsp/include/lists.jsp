<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><textarea cols="0" rows="0"  id="textarea" style="display:none"></textarea><div id="lists"><div id="playlists" style="width:25%;height:100%;border-right:solid 1px #1f4ea5;overflow-y:scroll;float:left;color:white;padding:20px"><div id="global"><c:if test="${sessionScope['utilisateur'].email == 'admin@localhost'}"><div id="playlist-3" class="notselected" onclick="javascript:goAdmin ()">Administration</div><hr color="#1f4ea5"/></c:if><div id="playlist-2" class="notselected" onclick="javascript:changeSelectedPlaylist(-2)">Collection globale</div><hr color="#1f4ea5"/><div id="playlist-1" class="notselected" onclick="javascript:changeSelectedPlaylist(-1)">Ma bibliotheque</div><hr color="#1f4ea5"/><p style="color:greenyellow">Recherches</p><div id="recherchesPlaylists"/><ul></ul></div><hr color="#1f4ea5"/></div><p style="color:greenyellow">Playlists</p><div id="playlistsContent"><%@include file="/jsp/include/playlistes.jsp"%></div><hr color="#1f4ea5"/></div><div id="musiques" style="height:100%;border-left:solid 1px #1f4ea5;overflow-y:hidden;color:white;padding:20px"><div id="titleList">Musiques</div><hr color="#1f4ea5"/><div id="musiquesContent"><p style="text-align:center;font-size:32px;color:#808080">Commencez d�s maintenant en selectionnant une liste de musique.<p></div></div></div>