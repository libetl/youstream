<%if (request.getParameter("nocanvas") == null) {%>
<%@include file="/jsp/include/template.jsp" %>
<%}%>
<div dojoType="dijit.Dialog" id="bienvenue" title="Bienvenue" style="width: 300px">
    <h1>Bienvenue, ${sessionScope['utilisateur'].email}</h1>

    <p>YouStream est une appli d'écoute et de partage de musique.</p>
    <div style="text-align:center;font-size:20px;float:left;width:50px">&uarr;</div><div style="margin-top:10px">Ajoutez des musiques et des playlists grâce au menu du haut</div>
    <div style="text-align:center;font-size:20px;float:left;width:50px">&larr;</div><div style="margin-top:20px">Vos playlists sont rangées sur la gauche</div>
    <div style="text-align:center;font-size:20px;float:left;width:50px">&rarr;</div><div style="margin-top:20px">Vos musiques se trouvent à droite</div>
    <div style="text-align:center;font-size:20px;float:left;width:50px">&darr;</div><div style="margin-top:15px">Pour quitter la session, c'est en bas de cette popup</div>

    <h2>Amusez vous bien</h2>
    <p>
        <button dojoType="dijit.form.Button" onclick="javascript:go('bienvenue', 'UI.act?action=logout&nocanvas=1')">Deconnexion</button>
        <button dojoType="dijit.form.Button" onclick="javascript:exitPopup('bienvenue')">Fermer la popup</button>
    </p>

    <script type="text/javascript">
        popupDisplay ("bienvenue");
        dojo.byId('lists').innerHTML = '<%@include file="/jsp/include/lists.jsp" %>';
        window.setTimeout ("transformButtons ()", 300);
        window.setTimeout ("transformLinks ()", 300);
        window.setTimeout ("retaille ()", 300);
        newsStreamInit = 0;
        window.setTimeout ("newsStream ()", 300);
        dojo.byId ("login").innerHTML =
            "Connecte en tant que ${sessionScope['utilisateur'].email}"
    </script>
</div>
