/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.toilelibre.libe.youstream.model.Categorie;

/**
 * 
 * @author L3 miage
 */
@Stateless
public class CategorieSessionBean implements CategorieSessionBeanLocal {

  private EntityManager em;

  @Override
  public Categorie getCategorie (final String songGenre) {
    int id = 0;
    Categorie cat = null;
    String songGenreInt = songGenre;
    if ((songGenre != null) && (songGenre.length () > 0)) {
      songGenreInt = songGenre.trim ();
      if (songGenreInt.startsWith ("(") && songGenreInt.endsWith (")")) {
        songGenreInt = songGenreInt.substring (1, songGenre.length () - 1);
      }
      try {
        id = Integer.parseInt (songGenreInt);
      } catch (final NumberFormatException nfe) {
        id = -1;
      }
      if (id != -1) {
        cat = this.em.find (Categorie.class, new Integer (id));
      }
    }
    if ((cat == null) && (songGenreInt != null) && (songGenreInt.length () > 0)) {
      @SuppressWarnings ("unchecked")
      final List<Categorie> cats = this.em
          .createQuery ("select c from Categorie c where c.nom=:nom")
          .setParameter ("nom", songGenreInt).getResultList ();
      if ((cats != null) && (cats.size () > 0)) {
        cat = cats.get (0);
      }
    }
    if ((cat == null) && (songGenreInt != null) && (songGenreInt.length () > 0)) {
      cat = new Categorie (songGenreInt);
      this.em.persist (cat);
    } else if (cat == null) {
      cat = this.em.find (Categorie.class, new Integer (0));
    }
    return cat;
  }

  public void setEm (final EntityManager em) {
    this.em = em;
  }

}
