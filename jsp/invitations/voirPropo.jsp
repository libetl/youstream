<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div dojoType="dijit.Dialog" id="voirPropo" title="Voir mes propositions" style="width: 350px">
    Vous avez proposé ${requestScope['propositionsSize']} partage(s) de playlist non répondu(s).<br/>
    <c:forEach var="proposition" items="${requestScope['propositions']}">
            Vous avez demandé à l'utilisateur ${proposition.destinataire.email} de partager votre
            playlist $(proposition.playlist.nomPlaylist}. Celui-ci n'a pas encore répondu.<br/>
    </c:forEach>
    <button dojoType="dijit.form.Button" type="reset">Fermer la popup</button>
    <script type="text/javascript">
        popupDisplay('voirPropo');
    </script>
</div>
