<script type="text/javascript">
    dijit.showTooltip ("Vous avez envoyé une nouvelle musique ! " +
    "${requestScope['musique'].artiste} - ${requestScope['musique'].titre}", dojo.byId('outgoing'));
    window.setTimeout (function (){dijit.hideTooltip (dojo.byId('outgoing'));}, 5000);
    window.setTimeout (function (){if (selectedPlaylist == -1){selectedPlaylist = 4;
            changeSelectedPlaylist(-1);}}, 300);
</script>