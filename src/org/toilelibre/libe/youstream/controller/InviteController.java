/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.toilelibre.libe.youstream.model.Invitation;
import org.toilelibre.libe.youstream.model.Notification;
import org.toilelibre.libe.youstream.model.Playlist;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.notifications.NewsStream;
import org.toilelibre.libe.youstream.session.InvitationSessionBeanLocal;
import org.toilelibre.libe.youstream.session.NotificationSessionBeanLocal;
import org.toilelibre.libe.youstream.session.PlaylistSessionBeanLocal;
import org.toilelibre.libe.youstream.session.UtilisateurSessionBeanLocal;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name = "InviteServlet", urlPatterns = {"/Invite"})
@Configurable (dependencyCheck = true)
public class InviteController extends AbstractController {

  /**
	 * 
	 */
  private static final long            serialVersionUID = 3217458654315485126L;
  @Autowired
  private InvitationSessionBeanLocal   invitationSessionBean;
  @Autowired
  private NotificationSessionBeanLocal notificationSessionBean;
  @Autowired
  private PlaylistSessionBeanLocal     playlistSessionBean;
  @Autowired
  private UtilisateurSessionBeanLocal  utilisateurSessionBean;

  public void proposer (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
      final String email = request.getParameter ("emailPersonne");
      final String plIdS = request.getParameter ("playlist");
      long plId = -1;
      try {
        plId = Long.parseLong (plIdS);
      } catch (final NumberFormatException nfe) {
      }
      if ((email != null) && (plId != -1)) {
        final Utilisateur dest = this.utilisateurSessionBean.get (email);
        final Playlist pl = this.playlistSessionBean.get (plId);
        final Invitation i = new Invitation ();
        i.setDemandeur (u);
        i.setDestinataire (dest);
        i.setPlaylist (pl);
        this.invitationSessionBean.put (i);
        final Notification n = new Notification (u, "inviteOnPlaylist",
            new Serializable[] { i, pl });
        NewsStream.saveNotification (this.notificationSessionBean, n);
      }
    }
  }

  public void proposerPopup (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/invitations/proposer.jsp";
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
      request.setAttribute ("playlistes", u.getPlaylistes ());
    }
    final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
    rd.forward (request, response);
  }

  public void repondre (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/playlist/creerNomPlaylistJS.jsp";
    boolean redirect = false;
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
      final String iIdS = request.getParameter ("invitId");
      long iId = -1;
      try {
        iId = Long.parseLong (iIdS);
      } catch (final NumberFormatException nfe) {
      }

      final Invitation i = this.invitationSessionBean.get (iId);

      request.setAttribute ("playlist", i.getPlaylist ());

      final String reply = request.getParameter ("reply");

      if ((i != null) && "accept".equals (reply)) {
        this.invitationSessionBean.confirm (i);

        final Notification n = new Notification (u, "acceptInvitation",
            new Serializable[] { i, i.getPlaylist () });
        NewsStream.saveNotification (this.notificationSessionBean, n);
        redirect = true;
      }

      if ((i != null) && ("accept".equals (reply) || "deny".equals (reply))) {
        this.invitationSessionBean.delete (i);

        final Notification n = new Notification (u, "denyInvitation",
            new Serializable[] { i, i.getPlaylist () });
        NewsStream.saveNotification (this.notificationSessionBean, n);
      }
    }
    if (redirect) {
      final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
      rd.forward (request, response);
    }
  }

  public void voirInvitPopup (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/invitations/voirInvit.jsp";
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
      final List<Invitation> invitations = this.invitationSessionBean
          .getPendingIncoming (u);
      request.setAttribute ("invitationsSize", invitations.size ());
      // (cause Lazy initialisation)
      request.setAttribute ("invitations", invitations);
    }
    final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
    rd.forward (request, response);
  }

  public void voirPropoPopup (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/invitations/voirPropo.jsp";
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
      final List<Invitation> propositions = this.invitationSessionBean
          .getPendingOutgoing (u);
      request.setAttribute ("propositionsSize", propositions.size ());
      // (cause Lazy initialisation)
      request.setAttribute ("propositions", propositions);
    }
    final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
    rd.forward (request, response);
  }
}
