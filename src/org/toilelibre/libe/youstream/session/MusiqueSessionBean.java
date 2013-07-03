/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.apache.commons.fileupload.FileItem;
import org.farng.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.toilelibre.libe.youstream.model.MP3;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.util.ID3Helper;

/**
 * 
 * @author L3 miage
 */
@Stateless
public class MusiqueSessionBean implements MusiqueSessionBeanLocal {

  @Autowired
  private CategorieSessionBeanLocal categorieSessionBean;

  private EntityManager             em;

  @Override
  public Musique ajouterMusique (final File file) {
    Musique musique = null;
    try {
      musique = new Musique ();
      musique.setPublique (true);
      final MP3File tags = new MP3File (file);
      ID3Helper.setTags (this.categorieSessionBean, musique, tags);
      final InputStream is = new FileInputStream (file);
      final ByteArrayOutputStream baos = new ByteArrayOutputStream ();
      int read = 0;
      final byte[] bytes = new byte[1024];

      while ((read = is.read (bytes)) != -1) {
        baos.write (bytes, 0, read);
      }
      this.em.getTransaction ().begin ();
      this.em.persist (musique);
      this.em.getTransaction ().commit ();
    } catch (final Exception ex) {
      Logger.getLogger (MusiqueSessionBean.class.getName ()).log (Level.SEVERE,
          null, ex);
    }
    return musique;
  }

  @Override
  public Musique ajouterMusique (final String dossier, final FileItem fi) {
    Musique musique = null;
    try {
      musique = new Musique ();
      this.em.getTransaction ().begin ();
      musique.setPublique (true);
      final MP3File tags;
      try {
        tags = new MP3File (fi.getName ());
        ID3Helper.setTags (this.categorieSessionBean, musique, tags);
      } catch (final java.io.IOException ioe) {
        musique.setArtiste ("Artiste Inconnu");
        musique.setTitre ("Titre Inconnu");
      }
      this.em.persist (musique);
      final MP3 mp3 = new MP3 ();
      mp3.setFileData (fi.get ());
      musique.setMp3 (mp3);
      this.em.merge (musique);
      this.em.getTransaction ().commit ();
    } catch (final Exception ex) {
      Logger.getLogger (MusiqueSessionBean.class.getName ()).log (Level.SEVERE,
          null, ex);
    }
    return musique;
  }

  @Override
  public Musique ajouterMusique (final String dossier, final String filenameIs,
      final InputStream is) {
    Musique musique = null;
    try {
      musique = new Musique ();
      this.em.getTransaction ().begin ();
      this.em.persist (musique);
      this.em.getTransaction ().commit ();
      musique.setPublique (true);
      final String filename = dossier + "/" + musique.getId () + ".mp3";
      System.out.println (filename);
      final ByteArrayOutputStream baos = new ByteArrayOutputStream ();
      int read = 0;
      final byte[] bytes = new byte[1024];

      while ((read = is.read (bytes)) != -1) {
        baos.write (bytes, 0, read);
      }

      is.close ();
      if ((filename != null) && filenameIs.contains (" - ")) {
        final String[] split = filenameIs.split (" - ");
        musique.setArtiste (split[0]);
        musique.setTitre (split[1].replaceAll ("\\.mp3", ""));
      } else if (filename != null) {
        musique.setArtiste ("Artiste inconnu");
        musique.setTitre (filenameIs.replaceAll ("\\.mp3", ""));
      }
      final MP3 mp3 = new MP3 ();
      mp3.setFileData (baos.toByteArray ());
      musique.setMp3 (mp3);
      this.em.getTransaction ().begin ();
      this.em.merge (musique);
      this.em.getTransaction ().commit ();
    } catch (final Exception ex) {
      Logger.getLogger (MusiqueSessionBean.class.getName ()).log (Level.SEVERE,
          null, ex);
    }
    return musique;
  }

  @Override
  public Musique get (final long mId) {
    return this.em.find (Musique.class, mId);
  }

  @Override
  public Musique get (final Long k) {
    return this.em.find (Musique.class, k);
  }

  @SuppressWarnings ("unchecked")
  @Override
  public List<Musique> listeMusiques () {
    return this.em.createQuery (
        "select m from Musique m where m.indisponible = false "
            + "and m.publique = true").getResultList ();
  }

  @SuppressWarnings ("unchecked")
  @Override
  public List<Musique> listeMusiquesAutorisees (final Utilisateur u) {
    return this.em
        .createQuery (
            "select m from Musique m where "
                + "m.indisponible = false and exists (select m.utilisateurs from Musique u where"
                + " u.id=-1 or u.id=:id").setParameter ("id", u.getId ())
        .getResultList ();
  }

  @SuppressWarnings ("unchecked")
  @Override
  public List<Musique> rechercheMusiques (final String query) {
    final List<Musique> liste = this.em
        .createQuery (
            "select m from Musique m where m.indisponible = false "
                + "and m.publique = true and (m.artiste like :qustring1 or "
                + "m.titre like :qustring2)")
        .setParameter ("qustring1", "%" + query + "%")
        .setParameter ("qustring2", "%" + query + "%").getResultList ();
    return liste;
  }

  @Override
  public void setAjoutePar (final Musique m, final Utilisateur u) {
    m.setAjoutePar (u);
    this.em.getTransaction ().begin ();
    this.em.merge (m);
    this.em.getTransaction ().commit ();
  }

  public void setEm (final EntityManager em) {
    this.em = em;
  }

  @Override
  public void supprimerMusique (final long idMusique) {
    final Musique musique = this.em.find (Musique.class, idMusique);
    this.em.getTransaction ().begin ();
    musique.getMp3 ().setFileData (new byte[0]);
    musique.setIndisponible (true);
    this.em.merge (musique);
    this.em.getTransaction ().commit ();
  }

  @Override
  public void update (final Musique musique) {
    this.em.getTransaction ().begin ();
    this.em.merge (musique);
    this.em.getTransaction ().commit ();
  }

}
