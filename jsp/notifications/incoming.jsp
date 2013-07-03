<%@page import="org.toilelibre.libe.youstream.model.Notification"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  Map<String, String> texts = new HashMap<String, String> ();
  texts.put ("addToPlaylist", "%u a ajout� '%m' � la playlist '%p'.");
  texts.put ("inviteOnPlaylist", "%u vous a invit� � partager sa playlist '%p'.");
  texts.put ("acceptInvitation", "%u a accept� de partager la playlist '%p' avec vous.");
  texts.put ("denyInvitation", "%u a refus� de partager la playlist '%p' avec vous.");
  texts.put ("createNewPlaylist", "%u a cr�� la playlist '%p'");
  texts.put ("addMusic", "%u a ajout� '%m' � la collection.");
  texts.put ("addMusicFromVideo", "%u a ajout� '%m' � la collection (� partir d'un site de vid�os).");

%>
<div dojoType="dijit.Dialog" id="voirActions" title="Mes notifications depuis ma pr�cedente connexion (${sessionScope ['connexionPrecedente']})"
     style="width: 550px">
    <div style="height:200px;overflow-y:auto">
        <%
          for (Notification n : (List<Notification>) request.getAttribute ("notifications")){

  String u = ""; String p = "";String m = "";
  if (n.getAuteur() != null){
    u = n.getAuteur ().getEmail ();
  }
  if (n.getPlaylist() != null){p = n.getPlaylist().getNomPlaylist();}
  if (n.getMusique() != null){m = n.getMusique().getArtiste() + " - " + n.getMusique().getTitre();}
  %>
        &rarr; <%=(texts.get(n.getActionNotification()) != null ?
                texts.get(n.getActionNotification()).replaceAll("%u", u)
                                                    .replaceAll("%p", p)
                                                    .replaceAll("%m", m) :
                          n.getActionNotification())%>
        <br/>&nbsp;&nbsp;&nbsp;
        <span style="color:#808080"><%=n.getDateNotification ()%></span><br/>
        <%}%>
    </div>
    <button dojoType="dijit.form.Button" type="reset">Fermer la popup</button>
    <script type="text/javascript">
        popupDisplay('voirActions');
    </script>
</div>
