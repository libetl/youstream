package org.toilelibre.libe.youstream.entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
  private EntityManagerFactory emfInstance = null;

  private String               factoryName;

  private EMF () {
  }

  public EntityManagerFactory get () {
    if (this.emfInstance == null) {
      this.emfInstance = Persistence
          .createEntityManagerFactory (this.factoryName);
    }
    return this.emfInstance;
  }

  public void setFactoryName (final String factoryName) {
    this.factoryName = factoryName;
  }
}