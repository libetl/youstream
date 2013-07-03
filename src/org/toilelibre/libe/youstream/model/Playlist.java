/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * @author L3 miage
 */
@Entity
public class Playlist implements Serializable {
  private static final long    serialVersionUID = 1L;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long                 id;
  @OneToMany (mappedBy = "playlist", cascade = CascadeType.REMOVE)
  private List<EntreePlaylist> liste;
  private String               nomPlaylist;
  @OneToMany (mappedBy = "playlist")
  private List<Notification>   notifications;
  @ManyToOne
  private Utilisateur          proprietaire;
  @ManyToMany
  private Set<Utilisateur>     utilisateurs;         // Utilisateur

  public Long getId () {
    return this.id;
  }

  public List<EntreePlaylist> getListe () {
    return this.liste;
  }

  public int getMusiqueSize () {
    return this.utilisateurs.size ();
  }

  public String getNomPlaylist () {
    return this.nomPlaylist;
  }

  public List<Notification> getNotifications () {
    return this.notifications;
  }

  public int getPlaylistSize () {
    return this.liste.size ();
  }

  public Utilisateur getProprietaire () {
    return this.proprietaire;
  }

  public Set<Utilisateur> getUtilisateurs () {
    return this.utilisateurs;
  }

  public void setId (final Long id) {
    this.id = id;
  }

  public void setListe (final List<EntreePlaylist> liste) {
    this.liste = liste;
  }

  public void setNomPlaylist (final String nomPlaylist) {
    this.nomPlaylist = nomPlaylist;
  }

  public void setNotifications (final List<Notification> notifications) {
    this.notifications = notifications;
  }

  public void setProprietaire (final Utilisateur proprietaire) {
    this.proprietaire = proprietaire;
  }

  public void setUtilisateurs (final Set<Utilisateur> utilisateurs) {
    this.utilisateurs = utilisateurs;
  }

}
