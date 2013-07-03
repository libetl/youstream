<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var li = document.createElement ("li");
    li.id = "playlist${requestScope['playlist'].id}";
    li.className = "notselected";
    li.onclick = function (){
        changeSelectedPlaylist(${playlist.id});
    }
    li.innerHTML = "${playlist.nomPlaylist}<c:if test="${playlist.proprietaire.id!=sessionScope['utilisateur'].id}"> (Partagé)</c:if>";
    var liTarget = new dojo.dnd.Target (li);
    liTarget.onDrop = function (){
        musicInPlaylist(${playlist.id});
    }
    dojo.byId ('playlistsContent').childNodes [0].appendChild (li);
    dijit.showTooltip ("Vous avez créé la playlist ${playlist.nomPlaylist}.", dojo.byId('outgoing'));
    window.setTimeout (function (){dijit.hideTooltip (dojo.byId('outgoing'))}, 5000)
</script>
