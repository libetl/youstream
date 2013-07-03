/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 * 
 * @author L3 miage
 */
@Entity
public class Utilisateur implements Serializable {

  private static final long    serialVersionUID = 1L;
  @Temporal (javax.persistence.TemporalType.TIMESTAMP)
  private Date                 dateDerniereConnexion;
  private String               email;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long                 id;
  private String               mdp;
  @OneToMany (mappedBy = "ajoutePar")
  private Collection<Musique>  musiquesAjoutees;
  @OneToMany (mappedBy = "auteur")
  private List<Notification>   notifications;
  @ManyToMany (mappedBy = "utilisateurs")
  private Set<Playlist>        partages;             // Playlists
  @OneToMany (mappedBy = "proprietaire")
  private Collection<Playlist> playlistes;

  public Utilisateur () {
  }

  public Utilisateur (final String email, final String mdp) {
    this.email = email;
    this.mdp = mdp;
  }

  public Date getDateDerniereConnexion () {
    return this.dateDerniereConnexion;
  }

  public String getEmail () {
    return this.email;
  }

  public Long getId () {
    return this.id;
  }

  public String getMdp () {
    return this.mdp;
  }

  public Collection<Musique> getMusiquesAjoutees () {
    return this.musiquesAjoutees;
  }

  public List<Notification> getNotifications () {
    return this.notifications;
  }

  public Set<Playlist> getPartages () {
    return this.partages;
  }

  public Collection<Playlist> getPlaylistes () {
    return this.playlistes;
  }

  public void setDateDerniereConnexion (final Date dateDerniereConnexion) {
    this.dateDerniereConnexion = dateDerniereConnexion;
  }

  public void setEmail (final String email) {
    this.email = email;
  }

  public void setId (final Long id) {
    this.id = id;
  }

  public void setMdp (final String mdp) {
    this.mdp = mdp;
  }

  public void setMusiquesAjoutees (final Collection<Musique> musiquesAjoutees) {
    this.musiquesAjoutees = musiquesAjoutees;
  }

  public void setNotifications (final List<Notification> notifications) {
    this.notifications = notifications;
  }

  public void setPartages (final Set<Playlist> partages) {
    this.partages = partages;
  }

  public void setPlaylistes (final Collection<Playlist> playlistes) {
    this.playlistes = playlistes;
  }

}
