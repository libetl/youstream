/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 
 * @author L3 miage
 */
@Entity
public class Musique implements Serializable {

  private static final long          serialVersionUID = 1L;
  @ManyToOne
  private Utilisateur                ajoutePar;
  private String                     album;
  private int                        annee;
  private String                     artiste;
  @ManyToOne (cascade = { CascadeType.MERGE, CascadeType.PERSIST })
  private Categorie                  categorie;
  @OneToMany (mappedBy = "musique")
  private Collection<EntreePlaylist> entreePlaylists;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long                       id;
  private boolean                    indisponible;
  @OneToOne
  private MP3                        mp3;
  @OneToMany (mappedBy = "musique")
  private List<Notification>         notifications;
  private boolean                    publique;
  private String                     titre;

  public Utilisateur getAjoutePar () {
    return this.ajoutePar;
  }

  public String getAlbum () {
    return this.album;
  }

  public int getAnnee () {
    return this.annee;
  }

  public String getArtiste () {
    return this.artiste;
  }

  public Categorie getCategorie () {
    return this.categorie;
  }

  public Collection<EntreePlaylist> getEntreePlaylists () {
    return this.entreePlaylists;
  }

  public Long getId () {
    return this.id;
  }

  public MP3 getMp3 () {
    return this.mp3;
  }

  public List<Notification> getNotifications () {
    return this.notifications;
  }

  public String getTitre () {
    return this.titre;
  }

  public boolean isIndisponible () {
    return this.indisponible;
  }

  public boolean isPublique () {
    return this.publique;
  }

  public void setAjoutePar (final Utilisateur ajoutePar) {
    this.ajoutePar = ajoutePar;
  }

  public void setAlbum (final String album) {
    this.album = album;
  }

  public void setAnnee (final int annee) {
    this.annee = annee;
  }

  public void setArtiste (final String artiste) {
    this.artiste = artiste;
  }

  public void setCategorie (final Categorie categorie) {
    this.categorie = categorie;
  }

  public void setEntreePlaylists (
      final Collection<EntreePlaylist> entreePlaylists) {
    this.entreePlaylists = entreePlaylists;
  }

  public void setId (final Long id) {
    this.id = id;
  }

  public void setIndisponible (final boolean indisponible) {
    this.indisponible = indisponible;
  }

  public void setMp3 (final MP3 mp3) {
    this.mp3 = mp3;
  }

  public void setNotifications (final List<Notification> notifications) {
    this.notifications = notifications;
  }

  public void setPublique (final boolean publique) {
    this.publique = publique;
  }

  public void setTitre (final String titre) {
    this.titre = titre;
  }

}
