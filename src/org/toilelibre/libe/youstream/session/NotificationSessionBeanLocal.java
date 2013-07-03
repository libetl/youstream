/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.toilelibre.libe.youstream.model.Notification;
import org.toilelibre.libe.youstream.model.Utilisateur;

/**
 * 
 * @author L3 miage
 */
@Local
public interface NotificationSessionBeanLocal {

  public List<Notification> incomingSince (Utilisateur u, Date d);

  public org.toilelibre.libe.youstream.model.Notification save (
      org.toilelibre.libe.youstream.model.Notification n);

  public java.util.List<org.toilelibre.libe.youstream.model.Notification> utilisateurOutgoing (
      org.toilelibre.libe.youstream.model.Utilisateur u, java.util.Date dateMin);
}
