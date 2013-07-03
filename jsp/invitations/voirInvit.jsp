<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div dojoType="dijit.Dialog" id="repondre" title="Repondre a vos invitations" style="width: 380px">
    Vous avez ${requestScope['invitationsSize']} invitation(s) en attente.<br/><br/>
    <c:forEach var="invitation" items="${requestScope['invitations']}">
        
            &rarr; L'utilisateur ${invitation.demandeur.email} souhaite vous inviter à partager sa
            playlist '${invitation.playlist.nomPlaylist}'.<br/>
            <b>Que souhaitez vous répondre ?</b>
            <form action="Invite.act" method="POST" style="display:inline">
                <input type="hidden" name="action" value="repondre"/>
                <input type="hidden" name="reply" value="accept"/>
                <input type="hidden" name="invitId" value="${invitation.id}"/>
                <button dojoType="dijit.form.Button" type="submit">Accepter</button>
            </form>
            <form action="Invite.act" method="POST" style="display:inline">
                <input type="hidden" name="action" value="repondre"/>
                <input type="hidden" name="reply" value="deny"/>
                <input type="hidden" name="invitId" value="${invitation.id}"/>
                <button dojoType="dijit.form.Button" type="submit">Refuser</button>
            </form><br/><br/>
    </c:forEach>
    <button dojoType="dijit.form.Button" type="reset">Fermer la popup</button>
    <script type="text/javascript">
        popupDisplay('repondre');
    </script>
</div>
