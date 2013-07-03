/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * 
 * @author L3 miage
 */
@Entity
public class MP3 implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  @Lob
  @Column (length = 20000000, columnDefinition = "longblob")
  private byte[]            fileData;

  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private Long              id;

  @OneToOne (mappedBy = "mp3")
  private Musique           musique;

  @Override
  public boolean equals (final Object object) {
    if (!(object instanceof MP3)) { return false; }
    final MP3 other = (MP3) object;
    if (((this.id == null) && (other.id != null))
        || ((this.id != null) && !this.id.equals (other.id))) { return false; }
    return true;
  }

  public byte[] getFileData () {
    return this.fileData;
  }

  public Long getId () {
    return this.id;
  }

  public Musique getMusique () {
    return this.musique;
  }

  @Override
  public int hashCode () {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode () : 0);
    return hash;
  }

  public void setFileData (final byte[] fileData) {
    this.fileData = fileData;
  }

  public void setId (final Long id) {
    this.id = id;
  }

  public void setMusique (final Musique musique) {
    this.musique = musique;
  }

  @Override
  public String toString () {
    return "org.toilelibre.libe.youstream.model.Musique[id=" + this.id + "]";
  }
}
