/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * 
 * @author L3 miage
 */
@Entity
public class Notification implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  private String            actionNotification;
  @ManyToOne
  private Utilisateur       auteur;
  @Temporal (javax.persistence.TemporalType.TIMESTAMP)
  @Column
  private Date              dateNotification;
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private Long              id;
  @OneToOne
  private Invitation        invitation;
  @ManyToOne
  private Musique           musique;
  @ManyToOne
  private Playlist          playlist;

  public Notification () {

  }

  public Notification (final Utilisateur auteur1, final String action,
      final Serializable[] sujets) {
    this.auteur = auteur1;
    for (final Serializable sujet : sujets) {
      if (sujet instanceof Playlist) {
        this.playlist = (Playlist) sujet;
      }
      if (sujet instanceof Musique) {
        this.musique = (Musique) sujet;
      }
      if (sujet instanceof Invitation) {
        this.invitation = (Invitation) sujet;
      }
    }
    this.dateNotification = new Date ();
    this.actionNotification = action;
  }

  public String getActionNotification () {
    return this.actionNotification;
  }

  public Utilisateur getAuteur () {
    return this.auteur;
  }

  public Date getDateNotification () {
    return this.dateNotification;
  }

  public Long getId () {
    return this.id;
  }

  public Invitation getInvitation () {
    return this.invitation;
  }

  public Musique getMusique () {
    return this.musique;
  }

  public Playlist getPlaylist () {
    return this.playlist;
  }

  public void setActionNotification (final String actionNotification1) {
    this.actionNotification = actionNotification1;
  }

  public void setAuteur (final Utilisateur auteur) {
    this.auteur = auteur;
  }

  public void setDateNotification (final Date dateNotification) {
    this.dateNotification = dateNotification;
  }

  public void setId (final Long id) {
    this.id = id;
  }

  public void setInvitation (final Invitation invitation) {
    this.invitation = invitation;
  }

  public void setMusique (final Musique musique) {
    this.musique = musique;
  }

  public void setPlaylist (final Playlist playlist) {
    this.playlist = playlist;
  }

}
