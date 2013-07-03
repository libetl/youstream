/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.toilelibre.libe.youstream.model.Notification;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.notifications.NewsStream;
import org.toilelibre.libe.youstream.session.NotificationSessionBeanLocal;
import org.toilelibre.libe.youstream.session.UtilisateurSessionBeanLocal;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name = "NotificationsServlet", urlPatterns = {"/Notifications"})
@Configurable (dependencyCheck = true)
public class NotificationsController extends AbstractController {

  /**
	 * 
	 */
  private static final long            serialVersionUID = -1227843854055290622L;
  @Autowired
  private NotificationSessionBeanLocal notificationSessionBean;
  @Autowired
  private UtilisateurSessionBeanLocal  utilisateurSessionBean;

  public void liveChanges (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    Notification notification = null;
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");

    Thread t = (Thread) request.getSession ()
        .getAttribute ("liveChangesThread");

    if ((t != null) && t.isAlive ()) {
      try {
        t.checkAccess ();
        t.interrupt ();
      } catch (final SecurityException se) {

      }
    }

    t = Thread.currentThread ();
    request.getSession ().setAttribute ("liveChangesThread", t);

    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
    } else {
      response.getOutputStream ().print ("stop");
      response.flushBuffer ();
      return;
    }

    while ((notification == null) && (u != null)) {
      final Notification notificationCandidate = NewsStream
          .waitForNotification (Math.abs (request.getSession ()
              .getMaxInactiveInterval ()) * 1000);

      if (notificationCandidate == null) {
        response.getOutputStream ().print ("stop");
        response.flushBuffer ();
        return;
      }

      u = (Utilisateur) request.getSession ().getAttribute ("utilisateur");

      if (u != null) {
        u = this.utilisateurSessionBean.get (u.getId ());
      }
      if (NewsStream.isConcerned (notificationCandidate, u)) {
        System.out.println (notificationCandidate);
        request.setAttribute ("notification", notificationCandidate);
        final RequestDispatcher rd = request
            .getRequestDispatcher ("/jsp/notifications/new.jsp");
        rd.forward (request, response);
        notification = notificationCandidate;
      }
    }
  }

  public void seeIncoming (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");

    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
      Date d = (Date) request.getSession ()
          .getAttribute ("connexionPrecedente");
      if (d == null) {
        d = new Date (0);
      }
      final List<Notification> ln = this.notificationSessionBean.incomingSince (
          u, d);
      final List<Notification> lnConcerned = new LinkedList<Notification> ();
      for (final Notification n : ln) {
        if (NewsStream.isConcerned (n, u)) {
          lnConcerned.add (n);
        }
      }
      Collections.reverse (lnConcerned);
      request.setAttribute ("notifications", lnConcerned);
      final RequestDispatcher rd = request
          .getRequestDispatcher ("/jsp/notifications/incoming.jsp");
      rd.forward (request, response);
    }
  }

  public void seeOutgoing (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
        "utilisateur");

    if (u != null) {
      u = this.utilisateurSessionBean.get (u.getEmail ());
      Date d = (Date) request.getSession ()
          .getAttribute ("connexionPrecedente");
      if (d == null) {
        d = new Date (0);
      }
      final List<Notification> ln = this.notificationSessionBean
          .utilisateurOutgoing (u, d);
      Collections.reverse (ln);
      request.setAttribute ("notifications", ln);
      final RequestDispatcher rd = request
          .getRequestDispatcher ("/jsp/notifications/outgoing.jsp");
      rd.forward (request, response);
    }
  }
}
