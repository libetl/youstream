<div dojoType="dijit.Dialog" id="ajouterMusique" title="Ajouter un MP3" style="width: 350px">
        <label for="file">Fichier MP3</label>
        <input dojoType="dojox.form.FileInputAuto" name="mp3" url="Music.act?action=add"/><br/>
        <button dojoType="dijit.form.Button" type="reset">Annuler</button>
    <script type="text/javascript">
        popupDisplay('ajouterMusique');
    </script>
</div>
