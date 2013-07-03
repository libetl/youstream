/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import java.io.File;
import java.io.InputStream;

import javax.ejb.Local;

import org.apache.commons.fileupload.FileItem;
import org.toilelibre.libe.youstream.model.Musique;

/**
 * 
 * @author L3 miage
 */
@Local
public interface MusiqueSessionBeanLocal {
  public Musique ajouterMusique (File file);

  public Musique ajouterMusique (String folder, FileItem fileItem);

  public Musique ajouterMusique (String dossier, String filename, InputStream is);

  public org.toilelibre.libe.youstream.model.Musique get (long mId);

  public Musique get (Long k);

  public java.util.List<org.toilelibre.libe.youstream.model.Musique> listeMusiques ();

  public java.util.List<org.toilelibre.libe.youstream.model.Musique> listeMusiquesAutorisees (
      org.toilelibre.libe.youstream.model.Utilisateur u);

  public java.util.List<org.toilelibre.libe.youstream.model.Musique> rechercheMusiques (
      java.lang.String query);

  public void setAjoutePar (org.toilelibre.libe.youstream.model.Musique m,
      org.toilelibre.libe.youstream.model.Utilisateur u);

  public void supprimerMusique (long idMusique);

  public void update (org.toilelibre.libe.youstream.model.Musique musique);

}
