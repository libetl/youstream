/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.notifications;

import java.util.Iterator;

import org.toilelibre.libe.youstream.model.Notification;
import org.toilelibre.libe.youstream.model.Playlist;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.session.NotificationSessionBeanLocal;

/**
 * 
 * @author L3 Miage
 */
public class NewsStream {

  private static Notification currentNotification;
  private final static Object lock = new Object ();

  public static Notification getCurrentNotification () {
    return NewsStream.currentNotification;
  }

  public static Object getLock () {
    return NewsStream.lock;
  }

  public static boolean isConcerned (final Notification n, final Utilisateur u) {
    if ((n == null) || (n.getActionNotification () == null)) { return false; }
    if (n.getActionNotification ().equals ("addToPlaylist")) {
      final long plId = n.getPlaylist ().getId ();
      boolean found = false;
      final Iterator<Playlist> it = u.getPlaylistes ().iterator ();
      while (!found && it.hasNext ()) {
        found |= it.next ().getId () == plId;
      }
      final Iterator<Playlist> itKey = u.getPartages ().iterator ();
      while (!found && itKey.hasNext ()) {
        found |= itKey.next ().getId () == plId;
      }
      return found;

    } else if (n.getActionNotification ().equals ("inviteOnPlaylist")) {
      return n.getInvitation ().getDestinataire ().getId () == u.getId ();
    } else if (n.getActionNotification ().equals ("createNewPlaylist")) {
      return n.getPlaylist ().getProprietaire ().getId () == u.getId ();
    } else if (n.getActionNotification ().equals ("acceptInvitation")
        || n.getActionNotification ().equals ("denyInvitation")) {
      return n.getInvitation ().getDemandeur ().getId () == u.getId ();
    } else if (n.getActionNotification ().equals ("addMusic")
        || n.getActionNotification ().equals ("addMusicFromVideo")) {
      if (n.getMusique ().isPublique ()) { return true; }
    }
    return false;
  }

  public static void saveNotification (final NotificationSessionBeanLocal nsbl,
      final Notification n) {
    nsbl.save (n);
    NewsStream.setCurrentNotification (n);
    synchronized (NewsStream.lock) {
      NewsStream.lock.notifyAll ();
    }
  }

  public static void setCurrentNotification (
      final Notification currentNotification) {
    NewsStream.currentNotification = currentNotification;
  }

  public static Notification waitForNotification (final long delay) {
    synchronized (NewsStream.lock) {
      try {
        NewsStream.lock.wait (delay);
      } catch (final InterruptedException ex) {
        return null;
      }
    }
    return NewsStream.currentNotification;

  }
}
