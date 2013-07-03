/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * 
 * @author L3 miage
 */
@Entity
public class EntreePlaylist implements Serializable {
  private static final long serialVersionUID = 1L;
  @Temporal (javax.persistence.TemporalType.TIMESTAMP)
  private Date              dateAjout;
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private Long              id;
  @ManyToOne
  private Musique           musique;
  private int               numeroApparition;
  @ManyToOne
  private Playlist          playlist;

  @Override
  public boolean equals (final Object object) {
    // TODO: Warning - this method won't work in the case the id fields are
    // not set
    if (!(object instanceof EntreePlaylist)) { return false; }
    final EntreePlaylist other = (EntreePlaylist) object;
    if (((this.id == null) && (other.id != null))
        || ((this.id != null) && !this.id.equals (other.id))) { return false; }
    return true;
  }

  public Date getDateAjout () {
    return this.dateAjout;
  }

  public Long getId () {
    return this.id;
  }

  public Musique getMusique () {
    return this.musique;
  }

  public int getNumeroApparition () {
    return this.numeroApparition;
  }

  public Playlist getPlaylist () {
    return this.playlist;
  }

  @Override
  public int hashCode () {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode () : 0);
    return hash;
  }

  public void setDateAjout (final Date dateAjout) {
    this.dateAjout = dateAjout;
  }

  public void setId (final Long id) {
    this.id = id;
  }

  public void setMusique (final Musique musique) {
    this.musique = musique;
  }

  public void setNumeroApparition (final int numeroApparition) {
    this.numeroApparition = numeroApparition;
  }

  public void setPlaylist (final Playlist playlist) {
    this.playlist = playlist;
  }

  @Override
  public String toString () {
    return "org.toilelibre.libe.youstream.model.EntreePlaylist[id=" + this.id
        + "]";
  }

}
