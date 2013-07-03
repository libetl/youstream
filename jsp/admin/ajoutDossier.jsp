    <form action="AdminSection.act" method="post">
        <div><label for="folder">Charger un dossier de mp3s</label></div>
        <div style="float:right;">
            <input type="submit"
                   onsubmit="javascript:dojo.byId ('scanProgress').style.display='block';return true"
                   value="Scanner" value="Scanner"/>
        </div>
        <div style="margin-right:90px">
            <input type="text" style="width:100%;color:black" dojoType="dijit.form.TextBox" value="${requestScope['pathFolder']}" style="color:black;width:100%" name="folder"/>
        </div>
        <input type="hidden" name="action" value="scanFolder"/>
    </form>
    <!--div dojoType="dijit.ProgressBar" style="width:100%;display:none" jsId="jsProgress" id="scanProgress" maximum="10"></div-->
    <script type="text/javascript">
        window.setTimeout (function (){
            var srcNode = dojo.byId ('pageContent');
            var targetNode = dojo.byId ('musiquesContent');
            if (dojo.byId ('adminPageContent') != undefined){
                var text = srcNode.innerHTML;
                targetNode.innerHTML = text;
                srcNode.innerHTML = "";
                dojo.byId ('titleList').innerHTML = "Partie admin";
                transformButtons (targetNode);
            }
        }, 500);
    </script>

