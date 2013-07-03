<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%if (request.getParameter("nocanvas") == null){%>
<%@include file="/jsp/include/template.jsp" %>
<%}%>
<script type="text/javascript">
    var plContent = "<ul id=\"plTitleUl\" >";
    <%long plId = -1;
    try {
        plId = Long.parseLong (request.getParameter ("plId"));
    }catch (NumberFormatException nfe){
    }
    boolean b = false;%>
    plContent += "<li class=\"head\"><div>";
    <%if (plId >= 0){%>
      plContent += "<div>#</div>";
    <%}%>
    plContent +="<div>Artiste</div><div>Titre</div><div>Année</div><div>Catégorie</div></div></li></ul>";
    plContent +="<ul dojoType=\"dojo.dnd.Source\" id=\"plContentUl\" style=\"overflow-y:auto;overflow-x:hidden\" copyOnly=\"true\">";
    <c:forEach var="ep" items="${requestScope['liste']}">
      plContent += "<li class=\"notselected indispo${ep.musique.indisponible} dojoDndItem\"  dojoType=\"dojo.dnd.Target\" onDrop=\"javascript:musicInPosition (${ep.numeroApparition})\" onmousedown=\"javascript:selectedDragMusic=${ep.musique.id};selectedDragPosition=${ep.numeroApparition}\" id=\"music${ep.numeroApparition}\"><div id=\"mp3${ep.musique.id}\" class=";
      <%if (b){%>
        plContent += "\"odd\"";
      <%}else{%>
        plContent += "\"even\"";
      <%} b = !b; %>
      plContent += " onclick=\"javascript:changeSelectedMusic(${ep.numeroApparition}, ${ep.musique.id})\">";
      <%if (plId >= 0){%>
          plContent +="<div>${ep.numeroApparition}&nbsp;</div>";
      <%}%>
      plContent += "<div>${ep.musique.artiste}&nbsp;</div><div>${ep.musique.titre}&nbsp;</div><div>${ep.musique.annee}&nbsp;</div><div>${ep.musique.categorie.nom}&nbsp;</div></div></li>";
    </c:forEach>
    plContent += "</ul>";

    dojo.byId ('musiquesContent').innerHTML = plContent;
    dojo.byId ('titleList').innerHTML = "${requestScope['nomPlaylist']}";

    if ('${requestScope['sp']}' != '${requestScope['plId']}' || '${requestScope['query']}' != ''){
      var dewplayerElem = dojo.byId ('dewplayer');
      var dewplayerElemSibling = dewplayerElem.nextSibling;
      var dewplayerElemParent = dewplayerElem.parentNode;
      dewplayerElemParent.removeChild (dewplayerElem);
      var divDewplayer = document.createElement ("div");
      divDewplayer.id = "dewplayer_content";
      divDewplayer.innerHTML = "&nbsp;"
      dewplayerElemParent.insertBefore (divDewplayer, dewplayerElemSibling);
    }

    window.setTimeout (function (){
        dojo.parser.parse ();
    }, 0);
    window.setTimeout ("retaille ()", 2000);
</script>
<%@include file="/jsp/include/dewplayer.jsp" %>