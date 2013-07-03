/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.ModelAndView;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.session.UtilisateurSessionBeanLocal;

//@WebServlet(name = "YouStreamServlet", urlPatterns = {"/YouStream"})
@Configurable (dependencyCheck = true)
public class YouStreamController extends AbstractController {

  /**
	 * 
	 */
  private static final long           serialVersionUID = 2674620472835304631L;
  @Autowired
  private UtilisateurSessionBeanLocal utilisateurSessionBean;

  private void goHome (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    String forwardTo = "/jsp/nonConnecte.jsp";
    final Object inSession = request.getSession ().getAttribute ("utilisateur");
    if (inSession != null) {
      forwardTo = "/jsp/connecte.jsp";

      Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
          "utilisateur");
      if (u != null) {
        u = this.utilisateurSessionBean.get (u.getEmail ());
        if (u != null) {
          request.setAttribute ("playlistes", u.getPlaylistes ());
          request.setAttribute ("partages", u.getPartages ());
        }
      }
    }
    final RequestDispatcher dp = request.getRequestDispatcher (forwardTo);
    dp.forward (request, response);
  }

  @Override
  public ModelAndView handleRequestInternal (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    if (request.getParameter ("action") != null) {
      super.handleRequestInternal (request, response);
    } else {
      this.goHome (request, response);
    }
    return null;
  }

  public void login (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    boolean ok = true;
    String forwardTo = "/jsp/connecte.jsp";
    final String user = request.getParameter ("email");
    final String pass = request.getParameter ("mdp");
    if ((user == null) || (user.length () < 4) || (pass == null)
        || (pass.length () < 2)) {
      ok = false;
      forwardTo = "/jsp/nonConnecte.jsp?loginError=Mauvaise%20saisie";
    }
    final Utilisateur u = this.utilisateurSessionBean.get (user);
    if (ok && (u == null)) {
      ok = false;
      forwardTo = "/jsp/nonConnecte.jsp?loginError=Utilisateur%20non%20trouve";
    }

    if (ok && (u != null) && !pass.equals (u.getMdp ())) {
      ok = false;
      forwardTo = "/jsp/nonConnecte.jsp?loginError=Echec%20de%20l%20authentification";
    }
    if (ok) {
      request.getSession ().setAttribute ("utilisateur", u);
      request.getSession ().setAttribute ("connexionPrecedente",
          u.getDateDerniereConnexion ());
      this.utilisateurSessionBean.setDerniereConnexion (u, new Date ());
      request.setAttribute ("playlistes", u.getPlaylistes ());
      request.setAttribute ("partages", u.getPartages ());
    }

    final RequestDispatcher dp = request.getRequestDispatcher (forwardTo);
    dp.forward (request, response);
  }

  public void logout (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String forwardTo = "/jsp/nonConnecte.jsp";
    request.getSession ().removeAttribute ("utilisateur");
    final RequestDispatcher dp = request.getRequestDispatcher (forwardTo);
    dp.forward (request, response);
  }

  public void signup (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {

    boolean ok = true;
    String forwardTo = "/jsp/connecte.jsp";
    final String user = request.getParameter ("email");
    final String pass = request.getParameter ("mdp");
    final String pass2 = request.getParameter ("mdp2");
    if ((user == null) || (user.length () < 4) || (pass == null)
        || (pass.length () < 2) || (pass2 == null) || (pass2.length () < 2)) {
      forwardTo = "/jsp/nonConnecte.jsp?loginError=Mauvaise%20saisie";
      ok = false;
    }
    if (!pass.equals (pass2)) {
      forwardTo = "/jsp/nonConnecte.jsp?loginError=Les%20mots%20%de%20passe%20sont%20differents";
      ok = false;
    }
    Utilisateur u = this.utilisateurSessionBean.get (user);
    if (u != null) {
      forwardTo = "/jsp/nonConnecte.jsp?loginError=L%20utilisateur%20existe%20deja";
      ok = false;
    }
    if (ok) {
      u = new Utilisateur ();
      u.setEmail (user);
      u.setMdp (pass);
      try {
        u.setDateDerniereConnexion (new Date ());
        request.getSession ().setAttribute ("connexionPrecedente",
            u.getDateDerniereConnexion ());
        this.utilisateurSessionBean.ajouterUtilisateur (u);
        request.getSession ().setAttribute ("utilisateur", u);
      } catch (final RuntimeException e) {
        Logger.getLogger (YouStreamController.class.getName ()).log (
            Level.SEVERE, null, e);
        forwardTo = "/jsp/nonConnecte.jsp?loginError=Erreur%20inattendue";
      }
    }
    final RequestDispatcher dp = request.getRequestDispatcher (forwardTo);
    dp.forward (request, response);
  }
}
