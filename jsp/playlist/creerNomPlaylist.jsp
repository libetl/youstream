<div dojoType="dijit.Dialog" id="creerNomPlaylist" title="Nouvelle playlist ?" style="width: 350px">
        <form action="Playlist.act"  method="post">
           <!-- il faudra recuperer l'id avec les variable de session
           une fois qu'on aura mis les connexions en place -->
           <label for="nomPlaylist">Nom Playlist</label>
           <input type="text" dojoType="dijit.form.TextBox" name="nomPlaylist"/><br/>
           <input type="hidden" name="action" value="createNewPlaylist"/>
           <button dojoType="dijit.form.Button" type="reset">Annuler</button>
           <button dojoType="dijit.form.Button" type="submit">Creer</button>
        </form>
<script type="text/javascript">
    popupDisplay('creerNomPlaylist');
</script>
</div>