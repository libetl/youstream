/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * @author L3 miage
 */
@Entity
public class Invitation implements Serializable {

  private static final long serialVersionUID = 1L;
  @ManyToOne
  private Utilisateur       demandeur;
  @ManyToOne
  private Utilisateur       destinataire;
  private boolean           enCours;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long              id;
  @OneToOne (mappedBy = "invitation")
  private Notification      notification;
  @ManyToOne
  private Playlist          playlist;

  public Utilisateur getDemandeur () {
    return this.demandeur;
  }

  public Utilisateur getDestinataire () {
    return this.destinataire;
  }

  public Long getId () {
    return this.id;
  }

  public Notification getNotification () {
    return this.notification;
  }

  public Playlist getPlaylist () {
    return this.playlist;
  }

  public boolean isEnCours () {
    return this.enCours;
  }

  public void setDemandeur (final Utilisateur demandeur) {
    this.demandeur = demandeur;
  }

  public void setDestinataire (final Utilisateur destinataire) {
    this.destinataire = destinataire;
  }

  public void setEnCours (final boolean enCours) {
    this.enCours = enCours;
  }

  public void setId (final Long id) {
    this.id = id;
  }

  public void setNotification (final Notification notification) {
    this.notification = notification;
  }

  public void setPlaylist (final Playlist playlist) {
    this.playlist = playlist;
  }

}
