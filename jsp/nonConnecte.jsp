<%if (request.getParameter("nocanvas") == null){%>
<%@include file="/jsp/include/template.jsp" %>
<%}%>
<div dojoType="dijit.Dialog" id="connexion" title="Connexion" style="width: 300px">

    <p>
        <c:if test="${not empty param ['error']}">
       <h2 style="color:#FF0000">${param['error']}</h2>
   </c:if>
   <c:if test="${not empty param ['loginError']}">
       <h2 style="color:#FF0000"> ${param['loginError']}</h2>
   </c:if>

   <div><span>Connectez vous</span></div>
       <form action="UI.act" method="POST">
           <table>
               <tr><td><label for="email">E-mail</label></td><td><input dojoType="dijit.form.TextBox" type="text" name="email"/></td></tr>
               <tr><td><label for="mdp">Mot de passe</label></td><td><input dojoType="dijit.form.TextBox" type="password" name="mdp"/></td></tr>
               <tr><td colspan="2"><input type="hidden" name="action" value="login"/><button dojoType="dijit.form.Button" type="submit">Connexion</button></td></tr>
           </table>
       </form>

   <div id="noaccount"><span>Pas de compte ?</span></div>
   <div>
       <form action="UI.act" method="post">
           <table>
               <tr><td><label for="email">E-mail</label></td><td><input dojoType="dijit.form.TextBox" type="text" name="email"/></td></tr>
               <tr><td><label for="mdp">Mot de passe</label></td><td><input dojoType="dijit.form.TextBox" type="password" name="mdp"/></td></tr>
               <tr><td><label for="mdp2">Confirmer<br/>Mot de passe</label></td><td><input dojoType="dijit.form.TextBox" type="password" name="mdp2"/></td></tr>
               <tr><td colspan="2"><input type="hidden" name="action" value="signup"/><button dojoType="dijit.form.Button" type="submit">Inscription</button></td></tr>
           </table>
       </form>
   </div>
<script type="text/javascript">
    popupDisplay("connexion");
    dojo.byId('lists').innerHTML =
        '<div id="playlists"></div><div id="musiques"></div>';
    dojo.byId ("login").innerHTML = "Login ?";
</script>
</div>