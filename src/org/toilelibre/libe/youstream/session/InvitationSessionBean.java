/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.toilelibre.libe.youstream.model.Invitation;
import org.toilelibre.libe.youstream.model.Playlist;
import org.toilelibre.libe.youstream.model.Utilisateur;

/**
 * 
 * @author L3 miage
 */
@Stateless
public class InvitationSessionBean implements InvitationSessionBeanLocal {

  private EntityManager em;

  @Override
  public void confirm (final Invitation i) {
    this.em.getTransaction ().begin ();
    final Playlist pl = i.getPlaylist ();
    pl.getUtilisateurs ().add (i.getDestinataire ());
    i.getDestinataire ().getPartages ().add (pl);
    this.em.merge (pl);
    this.em.getTransaction ().commit ();
  }

  @Override
  public void delete (final Invitation i) {
    this.em.getTransaction ().begin ();
    final Invitation i1 = this.em.find (Invitation.class, i.getId ());
    i1.setEnCours (false);
    this.em.getTransaction ().commit ();
  }

  @Override
  public Invitation get (final long iId) {
    return this.em.find (Invitation.class, iId);
  }

  @SuppressWarnings ("unchecked")
  @Override
  public List<Invitation> getPendingIncoming (final Utilisateur u) {
    final Query q = this.em
        .createQuery ("select i from Invitation i where i.destinataire.id = :idU"
            + " and i.enCours = :enCours");
    q.setParameter ("idU", u.getId ());
    q.setParameter ("enCours", true);
    return q.getResultList ();
  }

  @SuppressWarnings ("unchecked")
  @Override
  public List<Invitation> getPendingOutgoing (final Utilisateur u) {
    final Query q = this.em
        .createQuery ("select i from Invitation i where i.demandeur.id = :idU"
            + " and i.enCours = :enCours");
    q.setParameter ("idU", u.getId ());
    q.setParameter ("enCours", true);
    return q.getResultList ();
  }

  @Override
  public void put (final Invitation i) {
    this.em.getTransaction ().begin ();
    i.setEnCours (true);
    this.em.persist (i);
    this.em.getTransaction ().commit ();
  }

  public void setEm (final EntityManager em) {
    this.em = em;
  }
}
