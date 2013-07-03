/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.toilelibre.libe.youstream.model.EntreePlaylist;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.model.Playlist;
import org.toilelibre.libe.youstream.model.Utilisateur;

/**
 * 
 * @author L3 miage
 */
@Stateless
public class PlaylistSessionBean implements PlaylistSessionBeanLocal {

  private EntityManager em;

  @Override
  public void ajouterMusique (final Playlist p, final Musique m) {
    final EntreePlaylist ep = new EntreePlaylist ();
    ep.setDateAjout (new Date ());
    ep.setNumeroApparition (p.getListe ().size ());
    ep.setPlaylist (p);
    ep.setMusique (m);
    p.getListe ().add (ep);
    this.em.getTransaction ().begin ();
    this.em.merge (p);
    this.em.getTransaction ().commit ();
  }

  @Override
  public Playlist creerNomPlaylist (final Utilisateur u, final String nom) {
    final Playlist playlist = new Playlist ();
    playlist.setNomPlaylist (nom);
    playlist.setProprietaire (u);
    this.em.getTransaction ().begin ();
    this.em.persist (playlist);
    this.em.getTransaction ().commit ();
    return playlist;
  }

  @Override
  public Playlist get (final long plId) {
    final Playlist p = this.em.find (Playlist.class, plId);
    Collections.sort (p.getListe (), new Comparator<EntreePlaylist> () {
      @Override
      public int compare (final EntreePlaylist o1, final EntreePlaylist o2) {
        return new Integer (o1.getNumeroApparition ()).compareTo (new Integer (
            o2.getNumeroApparition ()));
      }
    });
    return p;
  }

  @Override
  public void remove (final EntreePlaylist ep) {
    this.em.getTransaction ().begin ();
    final Query q = this.em.createQuery ("update EntreePlaylist ep set "
        + "ep.numeroApparition = ep.numeroApparition - 1 "
        + "where ep.playlist.id = :id and ep.numeroApparition > :numero");
    q.setParameter ("id", ep.getPlaylist ().getId ());
    q.setParameter ("numero", ep.getNumeroApparition ());
    q.executeUpdate ();
    this.em.remove (ep);
    this.em.getTransaction ().commit ();
  }

  @Override
  public void remove (final Playlist p) {
    this.em.getTransaction ().begin ();
    this.em.remove (p);
    this.em.getTransaction ().commit ();
  }

  public void setEm (final EntityManager em) {
    this.em = em;
  }

  @Override
  public void setNumeroApparition (final EntreePlaylist ep, final int numero) {
    this.em.getTransaction ().begin ();
    Query q = this.em.createQuery ("update EntreePlaylist ep set "
        + "ep.numeroApparition = ep.numeroApparition - 1 "
        + "where ep.playlist.id = :id and ep.numeroApparition >= :numero1 "
        + "and ep.numeroApparition < :numero2");
    q.setParameter ("id", ep.getPlaylist ().getId ());
    q.setParameter ("numero1", ep.getNumeroApparition ());
    q.setParameter ("numero2", numero);
    q.executeUpdate ();

    q = this.em.createQuery ("update EntreePlaylist ep set "
        + "ep.numeroApparition = ep.numeroApparition + 1 "
        + "where ep.playlist.id = :id and ep.numeroApparition >= :numero1 "
        + "and ep.numeroApparition < :numero2");
    q.setParameter ("id", ep.getPlaylist ().getId ());
    q.setParameter ("numero1", numero);
    q.setParameter ("numero2", ep.getNumeroApparition ());
    q.executeUpdate ();

    ep.setNumeroApparition (numero);
    this.em.merge (ep);
    this.em.getTransaction ().commit ();
  }

}
