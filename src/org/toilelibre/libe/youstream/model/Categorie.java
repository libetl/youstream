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

/**
 * 
 * @author L3 miage
 */
@Entity
public class Categorie implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private int               id;
  private String            nom;

  public Categorie () {

  }

  public Categorie (final int id) {
    this.id = id;
  }

  public Categorie (final int i, final String string) {
    this.id = i;
    this.nom = string;
  }

  public Categorie (final String songGenre) {
    this.nom = songGenre;
  }

  @Override
  public boolean equals (final Object object) {
    // TODO: Warning - this method won't work in the case the id fields are
    // not set
    if (!(object instanceof Categorie)) { return false; }
    final Categorie other = (Categorie) object;
    if (this.id != other.id) { return false; }
    return true;
  }

  public int getId () {
    return this.id;
  }

  public String getNom () {
    return this.nom;
  }

  @Override
  public int hashCode () {
    int hash = 0;
    hash += this.id;
    return hash;
  }

  public void setId (final int id1) {
    this.id = id1;
  }

  public void setNom (final String nom1) {
    this.nom = nom1;
  }

  @Override
  public String toString () {
    return "org.toilelibre.libe.youstream.model.Categorie[id=" + this.id
        + ", nom=" + this.nom + "]";
  }

}
