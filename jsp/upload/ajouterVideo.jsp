<div dojoType="dijit.Dialog" id="ajouterVideo" title="Ajouter une bande son" style="width: 350px">
     <form action="Music.act?action=addFromVideo">
        <label for="url">Url de la bande son (Youtube, Dailymotion, Vodeo, ...)</label>
        <input dojoType="dijit.form.TextBox" type="text" name="url" style="width:320px"/><br/><br/>
        <div style="float:right">
            <button dojoType="dijit.form.Button" type="submit">Envoyer</button>
        </div>
        <div style="float:left">
            <button dojoType="dijit.form.Button" type="reset">Annuler</button>
        </div>
        <br/><br/>
     </form>
    <script type="text/javascript">
        popupDisplay('ajouterVideo');
    </script>
</div>
