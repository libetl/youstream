/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import java.util.Date;

import javax.ejb.Local;

import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.model.Utilisateur;

/**
 * 
 * @author L3 miage
 */
@Local
public interface UtilisateurSessionBeanLocal {
  public Utilisateur ajouterMusique (long utilisateurId, Musique musique);

  public org.toilelibre.libe.youstream.model.Utilisateur ajouterUtilisateur (
      org.toilelibre.libe.youstream.model.Utilisateur u);

  public Utilisateur get (long utilisateurId);

  public Utilisateur get (Long key);

  public Utilisateur get (String email);

  public java.util.List<org.toilelibre.libe.youstream.model.Utilisateur> listeUtilisateurs ();

  public void setDerniereConnexion (Utilisateur u, Date date);

  public void supprimerUtilisateur (long id);

}
