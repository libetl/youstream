
<script type="text/javascript">
    var flashvars = {
       javascript:"on",
       bgcolor:"4a4a4a",
       xml:"XmlPlaylistMP3.act?plId=${requestScope['plId']}${requestScope['query']}",
       autoreplay:1
    };
    var params = {
       wmode: "transparent"
    };
    var attributes = {
       id: "dewplayer"
    };
    swfobject.embedSWF ("swf/dewplayer-rect.swf",
                        "dewplayer_content",
                        "300", "25", "9.0.0", false, flashvars, params, attributes);
</script>
