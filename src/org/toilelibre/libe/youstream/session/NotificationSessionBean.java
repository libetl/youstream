/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.toilelibre.libe.youstream.model.Notification;
import org.toilelibre.libe.youstream.model.Utilisateur;

/**
 * 
 * @author L3 miage
 */
@Stateless
public class NotificationSessionBean implements NotificationSessionBeanLocal {

  private EntityManager em;

  @SuppressWarnings ("unchecked")
  @Override
  public List<Notification> incomingSince (final Utilisateur u, final Date d) {
    final Query q = this.em.createQuery ("select n from Notification n where "
        + "n.dateNotification >= :dateN");
    q.setParameter ("dateN", d);
    return q.getResultList ();
  }

  @Override
  public Notification save (final Notification n) {
    this.em.getTransaction ().begin ();
    this.em.persist (n);
    this.em.getTransaction ().commit ();
    return n;
  }

  public void setEm (final EntityManager em) {
    this.em = em;
  }

  @SuppressWarnings ("unchecked")
  @Override
  public List<Notification> utilisateurOutgoing (final Utilisateur u,
      final Date dateMin) {
    final Query q = this.em
        .createQuery ("select n from Notification n where n.auteur.email = :email"
            + " and n.dateNotification >= :dateN");
    q.setParameter ("email", u.getEmail ());
    q.setParameter ("dateN", dateMin);
    return q.getResultList ();
  }
}
