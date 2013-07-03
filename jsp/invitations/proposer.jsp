<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div dojoType="dijit.Dialog" id="proposer" title="Inviter quelqu'un sur une playlist" style="width: 350px">
       <form action="Invite.act" method="POST">
        <label for="personne">Personne (via e-mail) :</label>
        <input type="hidden" name="action" value="proposer"/>
        <input dojoType="dijit.form.TextBox" name="emailPersonne"/>
        <br/><br/>
        <label for="playlist">Playlist :</label>
        <select dojoType="dijit.form.Select" name="playlist">
            <c:forEach var="playlist" items="${requestScope['playlistes']}">
                <option value="${playlist.id}">${playlist.nomPlaylist}</option>
            </c:forEach>
        </select><br/><br/>
        <button dojoType="dijit.form.Button" type="reset">Annuler</button>
        <button dojoType="dijit.form.Button" type="submit" style="float:right">Valider</button>
       </form>
    <script type="text/javascript">
        popupDisplay('proposer');
    </script>
</div>
