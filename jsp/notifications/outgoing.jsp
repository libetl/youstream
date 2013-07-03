<%@page import="org.toilelibre.libe.youstream.model.Notification"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  Map<String, String> texts = new HashMap<String, String> ();
  texts.put ("addToPlaylist", "Vous avez ajouté '%m' à la playlist '%p'.");
  texts.put ("createNewPlaylist", "Vous avez créé la playlist '%p'");
  texts.put ("inviteOnPlaylist", "Vous avez invité %e à partager votre playlist '%p'.");
  texts.put ("acceptInvitation", "Vous avez accepté de partager la playlist '%p' avec %d.");
  texts.put ("denyInvitation", "Vous avez refusé de partager la playlist '%p' avec %d.");
  texts.put ("addMusic", "Vous avez ajouté '%m' à la collection.");
  texts.put ("addMusicFromVideo", "Vous avez ajouté '%m' à la collection (à partir d'un site de vidéos).");

%>
<div dojoType="dijit.Dialog" id="voirActions" title="Mes actions depuis ma précedente connexion (${sessionScope ['connexionPrecedente']})"
     style="width: 550px">
    <div style="height:200px;overflow-y:auto">
        <%
          for (Notification n : (List<Notification>) request.getAttribute ("notifications")){
          String d = ""; String e = ""; String p = "";String m = "";
          if (n.getInvitation() != null){d = n.getInvitation ().getDemandeur().getEmail();}
          if (n.getInvitation() != null){e = n.getInvitation ().getDestinataire().getEmail();}
          if (n.getPlaylist() != null){p = n.getPlaylist().getNomPlaylist();}
          if (n.getMusique() != null){
              m = n.getMusique().getArtiste() + " - " + n.getMusique().getTitre();}

        %>
        &rarr; <%=texts.get (n.getActionNotification ()).replaceAll("%d", d)
                                                        .replaceAll("%e", e)
                                                        .replaceAll("%p", p)
                                                        .replaceAll("%m", m)%>
        <br/>&nbsp;&nbsp;&nbsp;
        <span style="color:#808080"><%=n.getDateNotification ()%></span><br/>
        <%}%>
    </div>
    <button dojoType="dijit.form.Button" type="reset">Fermer la popup</button>
    <script type="text/javascript">
        popupDisplay('voirActions');
    </script>
</div>
