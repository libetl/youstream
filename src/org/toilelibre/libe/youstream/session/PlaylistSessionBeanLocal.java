/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import javax.ejb.Local;

/**
 * 
 * @author L3 miage
 */
@Local
public interface PlaylistSessionBeanLocal {

  public void ajouterMusique (org.toilelibre.libe.youstream.model.Playlist p,
      org.toilelibre.libe.youstream.model.Musique m);

  public org.toilelibre.libe.youstream.model.Playlist creerNomPlaylist (
      org.toilelibre.libe.youstream.model.Utilisateur utilisateur,
      java.lang.String nom);

  public org.toilelibre.libe.youstream.model.Playlist get (long plId);

  public void remove (org.toilelibre.libe.youstream.model.EntreePlaylist ep);

  public void remove (org.toilelibre.libe.youstream.model.Playlist p);

  public void setNumeroApparition (
      org.toilelibre.libe.youstream.model.EntreePlaylist ep, int numero);
}
