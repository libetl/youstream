/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import javax.ejb.Local;

import org.toilelibre.libe.youstream.model.Categorie;

/**
 * 
 * @author L3 miage
 */
@Local
public interface CategorieSessionBeanLocal {

  public Categorie getCategorie (String songGenre);
}
