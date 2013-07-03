<%@page import="org.toilelibre.libe.youstream.model.Notification"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%
  Map<String, String> texts = new HashMap<String, String> ();
  texts.put ("addToPlaylist", "%u a ajouté %m à la playlist %p.");
  texts.put ("inviteOnPlaylist", "%u vous a invité à partager sa playlist %p.");
  texts.put ("acceptInvitation", "%u a accepté de partager la playlist %p avec vous.");
  texts.put ("denyInvitation", "%u a refusé de partager la playlist %p avec vous.");
  texts.put ("addMusic", "%u a ajouté %m à la collection.");
  texts.put ("addMusicFromVideo", "%u a ajouté %m à la collection (à partir d'un site de vidéos).");

  Notification n = (Notification) request.getAttribute("notification");
  String u = ""; String p = "";String m = "";
  u = n.getAuteur().getEmail();
  if (n.getPlaylist() != null){p = n.getPlaylist().getNomPlaylist();}
  if (n.getMusique() != null){m = n.getMusique().getArtiste() + " - " + n.getMusique().getTitre();}
  String text = texts.get(n.getActionNotification()).replaceAll("%u", u)
                                                    .replaceAll("%p", p)
                                                    .replaceAll("%m", m)
                                                    .replaceAll("\"", "\\\"");
%>

<script type="text/javascript">
    dijit.showTooltip ("<%=text%>", dojo.byId('incoming'));
    window.setTimeout (function (){dijit.hideTooltip (dojo.byId('incoming'));}, 5000);
</script>