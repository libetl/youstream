/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.toilelibre.libe.youstream.model.EntreePlaylist;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.model.Notification;
import org.toilelibre.libe.youstream.model.Playlist;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.notifications.NewsStream;
import org.toilelibre.libe.youstream.session.MusiqueSessionBeanLocal;
import org.toilelibre.libe.youstream.session.NotificationSessionBeanLocal;
import org.toilelibre.libe.youstream.session.PlaylistSessionBeanLocal;
import org.toilelibre.libe.youstream.session.UtilisateurSessionBeanLocal;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name = "PlaylistServlet", urlPatterns = {"/Playlist"})
@Configurable (dependencyCheck = true)
public class PlaylistController extends AbstractController {

  /**
	 * 
	 */
  private static final long            serialVersionUID = 6312061437947769705L;
  @Autowired
  private MusiqueSessionBeanLocal      musiqueSessionBean;
  @Autowired
  private NotificationSessionBeanLocal notificationSessionBean;
  @Autowired
  private PlaylistSessionBeanLocal     playlistSessionBean;
  @Autowired
  private UtilisateurSessionBeanLocal  utilisateurSessionBean;

  public void addMusic (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final long plId = Long.parseLong (request.getParameter ("plId"));
    final long mId = Long.parseLong (request.getParameter ("mId"));
    final Playlist pl = this.playlistSessionBean.get (plId);
    final Musique m = this.musiqueSessionBean.get (mId);
    this.playlistSessionBean.ajouterMusique (pl, m);
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getId ());
    }
    final Notification n = new Notification (u, "addToPlaylist",
        new Serializable[] { pl, m });
    NewsStream.saveNotification (this.notificationSessionBean, n);
  }

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   * 
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   * @throws ServletException
   *           if a servlet-specific error occurs
   * @throws IOException
   *           if an I/O error occurs
   */
  public void createNewPlaylist (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/playlist/creerNomPlaylistJS.jsp";
    final String nom = request.getParameter ("nomPlaylist");
    final Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    final Playlist pl = this.playlistSessionBean.creerNomPlaylist (u, nom);
    final Notification n = new Notification (u, "createNewPlaylist",
        new Serializable[] { pl });
    this.notificationSessionBean.save (n);
    request.setAttribute ("playlist", pl);
    final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
    rd.forward (request, response);
  }

  private List<EntreePlaylist> musicsToPlaylist (
      final Collection<Musique> musiques) {
    final List<EntreePlaylist> simulatedPlaylist = new LinkedList<EntreePlaylist> ();
    int i = 0;
    for (final Musique m : musiques) {
      final EntreePlaylist ep = new EntreePlaylist ();
      ep.setMusique (m);
      ep.setNumeroApparition (i++);
      simulatedPlaylist.add (ep);
    }
    return simulatedPlaylist;
  }

  public void play (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/playlist/ecouter.jsp";
    final String plIdS = request.getParameter ("plId");
    long plId;
    if (plIdS != null) {
      request.setAttribute ("plId", plIdS);
      // Ancien id Playlist
      request.setAttribute ("sp", request.getParameter ("sp"));
      try {
        plId = Long.parseLong (plIdS);
        if (plId >= 0) {
          final Playlist pl = this.playlistSessionBean.get (plId);
          request.setAttribute ("nomPlaylist", pl.getNomPlaylist ());
          request.setAttribute ("liste", pl.getListe ());
        } else if (plId == -1) {
          Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
              "utilisateur");
          if (u != null) {
            u = this.utilisateurSessionBean.get (u.getEmail ());
            if (u != null) {
              final Collection<Musique> musiques = u.getMusiquesAjoutees ();
              request.setAttribute ("nomPlaylist", "Bibliothèque");
              final List<EntreePlaylist> playlistC = this
                  .musicsToPlaylist (musiques);
              request.setAttribute ("liste", playlistC);
            }
          }
        } else if (plId == -2) {
          request.setAttribute ("nomPlaylist", "Collection globale");
          request.setAttribute ("liste",
              this.musicsToPlaylist (this.musiqueSessionBean.listeMusiques ()));
        }
      } catch (final NumberFormatException nfe) {
      }
    }
    final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
    rd.forward (request, response);
  }

  public void setPosition (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final long plId = Long.parseLong (request.getParameter ("plId"));
    final long epId = Long.parseLong (request.getParameter ("epId"));
    final long pos = Long.parseLong (request.getParameter ("pos"));
    final Playlist pl = this.playlistSessionBean.get (plId);
    if (pl != null) {
      final EntreePlaylist ep = pl.getListe ().get ((int) epId);
      this.playlistSessionBean.setNumeroApparition (ep, (int) pos);
    }
  }
}
