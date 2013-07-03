/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.session.MusiqueSessionBeanLocal;
import org.toilelibre.libe.youstream.session.UtilisateurSessionBeanLocal;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name="AdminSectionServlet", urlPatterns={"/AdminSection"})
@Configurable (dependencyCheck = true)
public class AdminSectionController extends AbstractController {
  private static String               EMAIL_ADMIN      = "admin@localhost";
  /**
	 * 
	 */
  private static final long           serialVersionUID = -3882489771354066828L;
  @Autowired
  private MusiqueSessionBeanLocal     musiqueSessionBean;

  @Autowired
  private UtilisateurSessionBeanLocal utilisateurSessionBean;

  public void index (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/admin/index.jsp";
    final Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");
    if ((u != null)
        && AdminSectionController.EMAIL_ADMIN.equals (u.getEmail ())) {
      request.setAttribute ("pathFolder", System.getProperty ("user.home")
          + "/Ma musique");
      final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
      rd.forward (request, response);
    }
  }

  public void listeUtilisateurs (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/admin/listeUtilisateurs.jsp";
    request.setAttribute ("listeUtilisateurs",
        this.utilisateurSessionBean.listeUtilisateurs ());
    final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
    rd.forward (request, response);
  }

  private void scan (final File f, final FileFilter ff) {
    final File[] folderContent = f.listFiles (ff);
    if (folderContent == null) { return; }
    for (final File entry : folderContent) {
      if (entry.isDirectory ()) {
        this.scan (entry, ff);
      } else {
        this.musiqueSessionBean.ajouterMusique (entry);
      }
    }
  }

  public void scanFolder (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String folder = request.getParameter ("folder");
    final File f = new File (folder);
    final FileFilter ff = new FileFilter () {
      @Override
      public boolean accept (final File pathname) {
        return pathname.isDirectory ()
            || pathname.getName ().toLowerCase ().endsWith (".mp3");
      }
    };
    this.scan (f, ff);
  }

  public void supprimerMusique (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    long musiqueId = -1;
    try {
      musiqueId = Long.parseLong (request.getParameter ("musiqueId"));
    } catch (final NumberFormatException nfe) {
    }
    if (musiqueId != -1) {
      this.musiqueSessionBean.supprimerMusique (musiqueId);
    }
  }

  public void supprimerUtilisateur (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    long utilisateurId = -1;
    try {
      utilisateurId = Long.parseLong (request.getParameter ("utilisateurId"));
    } catch (final NumberFormatException e) {
    }
    if (utilisateurId != -1) {
      this.utilisateurSessionBean.supprimerUtilisateur (utilisateurId);
    }
    this.listeUtilisateurs (request, response);
  }

}
