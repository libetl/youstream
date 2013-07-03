/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.model.Notification;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.notifications.NewsStream;
import org.toilelibre.libe.youstream.session.MusiqueSessionBeanLocal;
import org.toilelibre.libe.youstream.session.NotificationSessionBeanLocal;
import org.toilelibre.libe.youstream.session.UtilisateurSessionBeanLocal;
import org.toilelibre.libe.youstream.video2mp3.ConvertedStream;
import org.toilelibre.libe.youstream.video2mp3.MP3Converter;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name = "MusicServlet", urlPatterns = {"/Music"})
@Configurable (dependencyCheck = true)
public class MusicController extends AbstractController {

  /**
	 * 
	 */
  private static final long            serialVersionUID = -2482528585670575901L;
  @Autowired
  private MusiqueSessionBeanLocal      musiqueSessionBean;
  @Autowired
  private NotificationSessionBeanLocal notificationSessionBean;
  @Autowired
  private UtilisateurSessionBeanLocal  utilisateurSessionBean;

  public void add (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {

    final String dossier = System.getProperty ("java.io.tmpdir");

    final DiskFileItemFactory fileItemFactory = new DiskFileItemFactory ();
    fileItemFactory.setRepository (new File (dossier));
    final ServletFileUpload uploadHandler = new ServletFileUpload (
        fileItemFactory);
    try {
      @SuppressWarnings ("unchecked")
      final List<FileItem> parseRequest = uploadHandler.parseRequest (request);
      for (final FileItem fi : parseRequest) {
        final Musique musique = this.musiqueSessionBean.ajouterMusique (
            dossier, fi);
        Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
            "utilisateur");
        if (u != null) {
          u = this.utilisateurSessionBean.get (u.getId ());
        }
        this.musiqueSessionBean.setAjoutePar (musique, u);
        final Notification n = new Notification (u, "addMusic",
            new Serializable[] { musique });
        request.setAttribute ("musique", musique);
        NewsStream.saveNotification (this.notificationSessionBean, n);
      }
    } catch (final FileUploadException ex) {
      Logger.getLogger (MusicController.class.getName ()).log (Level.SEVERE,
          null, ex);
      final RequestDispatcher rd = request
          .getRequestDispatcher ("/jsp/upload/echec.jsp");
      rd.forward (request, response);
      return;
    }
    final RequestDispatcher rd = request
        .getRequestDispatcher ("/jsp/upload/ajoutReussi.jsp");
    rd.forward (request, response);
  }

  public void addFromVideo (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final ConvertedStream cs = MP3Converter.convert (request
        .getParameter ("url"));
    if (cs == null) {
      final RequestDispatcher rd = request
          .getRequestDispatcher ("/jsp/upload/echec.jsp");
      rd.forward (request, response);
      return;
    }
    System.out.println (cs.getFilename ());
    final String dossier = "";
    final Musique musique = this.musiqueSessionBean.ajouterMusique (dossier,
        cs.getFilename (), cs.getStream ());
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getId ());
    }
    this.musiqueSessionBean.setAjoutePar (musique, u);
    final Notification n = new Notification (u, "addMusicFromVideo",
        new Serializable[] { musique });
    NewsStream.saveNotification (this.notificationSessionBean, n);
    request.setAttribute ("musique", musique);
    final RequestDispatcher rd = request
        .getRequestDispatcher ("/jsp/upload/ajoutReussi.jsp");
    rd.forward (request, response);
  }

  public void delete (final HttpServletRequest request,
      final HttpServletResponse response) {
    final String mIdS = request.getParameter ("mId");
    long mId = -1;
    try {
      if (mIdS != null) {
        mId = Long.parseLong (mIdS);
      }
    } catch (final NumberFormatException nfe) {
    }
    if ((mIdS != null) && (mId != -1)) {
      this.musiqueSessionBean.supprimerMusique (mId);
    }
  }
}
