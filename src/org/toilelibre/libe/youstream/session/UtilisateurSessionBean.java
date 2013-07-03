/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.session;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.toilelibre.libe.youstream.controller.AbstractController;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.model.Utilisateur;

/**
 * 
 * @author L3 miage
 */
@Stateless
public class UtilisateurSessionBean implements UtilisateurSessionBeanLocal {

  private EntityManager em;

  @Override
  public Utilisateur ajouterMusique (final long utilisateurId,
      final Musique musique) {
    this.em.getTransaction ().begin ();
    final Utilisateur u = this.get (utilisateurId);
    u.getMusiquesAjoutees ().add (musique);
    musique.setAjoutePar (u);
    this.em.merge (musique);
    this.em.merge (u);
    this.em.getTransaction ().commit ();
    return u;
  }

  @Override
  /**creation d'un utilisateur*/
  public Utilisateur ajouterUtilisateur (final Utilisateur u) {
    this.em.getTransaction ().begin ();
    this.em.persist (u);
    this.em.getTransaction ().commit ();
    return u;
  }

  @Override
  public Utilisateur get (final long utilisateurId) {
    final Utilisateur u = this.em.find (Utilisateur.class, utilisateurId);
    this.em.refresh (u);
    return u;
  }

  @Override
  public Utilisateur get (final Long key) {
    final Utilisateur u = this.em.find (Utilisateur.class, key);
    this.em.refresh (u);
    return u;
  }

  @SuppressWarnings ("unchecked")
  @Override
  public Utilisateur get (final String user) {
    try {
      final List<Utilisateur> list = this.em
          .createQuery ("select u from Utilisateur u where u.email = ?1")
          .setParameter (1, user).getResultList ();
      if (list.size () > 0) { return list.get (0); }
    } catch (final PersistenceException pe) {
      Logger.getLogger (AbstractController.class.getName ()).log (Level.SEVERE,
          null, pe);
    }
    return null;
  }

  /** affichage de la liste des utilisateurs */
  @SuppressWarnings ("unchecked")
  @Override
  public List<Utilisateur> listeUtilisateurs () {
    return this.em.createQuery ("select u from Utilisateur u").getResultList ();
  }

  @Override
  public void setDerniereConnexion (final Utilisateur u, final Date date) {
    u.setDateDerniereConnexion (date);
    this.em.getTransaction ().begin ();
    this.em.merge (u);
    this.em.getTransaction ().commit ();
  }

  public void setEm (final EntityManager em) {
    this.em = em;
  }

  /** suppression d'un utilisateur */
  @Override
  public void supprimerUtilisateur (final long id) {
    this.em.getTransaction ().begin ();
    final Query q = this.em
        .createQuery ("delete from Utilisateur u where u.id = : id");
    q.setParameter ("id", id);
    q.executeUpdate ();
    this.em.getTransaction ().commit ();
  }
}
