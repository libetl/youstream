/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.session;

import javax.ejb.Local;

import org.toilelibre.libe.youstream.model.Invitation;

/**
 * 
 * @author L3 miage
 */
@Local
public interface InvitationSessionBeanLocal {

  public void confirm (Invitation i);

  public void delete (Invitation i);

  public Invitation get (long iId);

  public java.util.List<org.toilelibre.libe.youstream.model.Invitation> getPendingIncoming (
      org.toilelibre.libe.youstream.model.Utilisateur u);

  public java.util.List<org.toilelibre.libe.youstream.model.Invitation> getPendingOutgoing (
      org.toilelibre.libe.youstream.model.Utilisateur u);

  public void put (Invitation i);
}
