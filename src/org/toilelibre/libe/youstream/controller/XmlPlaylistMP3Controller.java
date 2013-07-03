/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.ModelAndView;
import org.toilelibre.libe.youstream.model.EntreePlaylist;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.model.Playlist;
import org.toilelibre.libe.youstream.model.Utilisateur;
import org.toilelibre.libe.youstream.session.MusiqueSessionBeanLocal;
import org.toilelibre.libe.youstream.session.PlaylistSessionBeanLocal;
import org.toilelibre.libe.youstream.session.UtilisateurSessionBeanLocal;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name = "XmlPlaylistMP3Servlet", urlPatterns =
// {"/XmlPlaylistMP3"})
@Configurable (dependencyCheck = true)
public class XmlPlaylistMP3Controller extends AbstractController {

  /**
	 * 
	 */
  private static final long           serialVersionUID = 3454797546106679588L;
  @Autowired
  private MusiqueSessionBeanLocal     musiqueSessionBean;
  @Autowired
  private PlaylistSessionBeanLocal    playlistSessionBean;
  @Autowired
  private UtilisateurSessionBeanLocal utilisateurSessionBean;

  private void addEntreePlaylist (final HttpServletRequest request,
      final HttpServletResponse response, final EntreePlaylist ep)
      throws IOException {

    final PrintWriter out = response.getWriter ();
    out.println ("<track>");
    out.println ("<location>Listen.act?mId=" + ep.getMusique ().getId ()
        + "</location>");
    out.println ("<creator>" + this.format (ep.getMusique ().getArtiste ())
        + "</creator>");
    out.println ("<album>" + this.format (ep.getMusique ().getAlbum ())
        + "</album>");
    out.println ("<title>n°" + ep.getNumeroApparition () + ": "
        + this.format (ep.getMusique ().getArtiste ()) + " - "
        + this.format (ep.getMusique ().getTitre ()) + "</title>");
    out.println ("<annotation></annotation>");
    out.println ("<duration></duration>");
    out.println ("<image></image>");
    out.println ("<info># : " + ep.getNumeroApparition () + " ; Ajout le "
        + ep.getDateAjout () + "</info>");

  }

  private String format (final String input) {
    if (input == null) { return "?"; }
    String output = input;
    output = output.replaceAll ("&", "&amp;");
    output = output.replaceAll ("<", "&lt;");
    output = output.replaceAll (">", "&gt;");
    output = output.replace ('\0', ' ');
    try {
      output = new String (output.getBytes ("UTF8"));
    } catch (final UnsupportedEncodingException ex) {
      Logger.getLogger (XmlPlaylistMP3Controller.class.getName ()).log (
          Level.SEVERE, null, ex);
    }
    return output;
  }

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   * 
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   * @throws ServletException
   *           if a servlet-specific error occurs
   * @throws IOException
   *           if an I/O error occurs
   */
  @Override
  public ModelAndView handleRequestInternal (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    response.setContentType ("text/xml;charset=UTF-8");
    long plId = -1;
    final String plIdS = request.getParameter ("plId");
    if (plIdS != null) {
      try {
        plId = Long.parseLong (plIdS);
      } catch (final NumberFormatException nfe) {
      }
    }
    Playlist pl = new Playlist ();
    if (plId >= 0) {
      pl = this.playlistSessionBean.get (plId);
    } else if (plIdS.startsWith ("-3")) {
      final String query = plIdS.substring (2);
      pl.setNomPlaylist ("Recherche de '" + query + "'");
      final Collection<Musique> musiques = this.musiqueSessionBean
          .rechercheMusiques (query);
      pl.setListe (this.musiquesToPlaylist (musiques));
      pl.setProprietaire (new Utilisateur ());
      pl.setUtilisateurs (new HashSet<Utilisateur> ());
    } else if (plId == -1) {
      Utilisateur u = (Utilisateur) request.getSession ().getAttribute (
          "utilisateur");
      pl.setUtilisateurs (new HashSet<Utilisateur> ());
      pl.setNomPlaylist ("Ma Bibliotheque");
      if (u != null) {
        u = this.utilisateurSessionBean.get (u.getEmail ());
        if (u != null) {
          final Collection<Musique> musiques = u.getMusiquesAjoutees ();
          pl.setListe (this.musiquesToPlaylist (musiques));
          pl.setProprietaire (u);
        }
      } else {
        pl.setProprietaire (new Utilisateur ());
        pl.getProprietaire ().setEmail ("Identifiez vous !");
        pl.setListe (new LinkedList<EntreePlaylist> ());
      }
    } else if (plId == -2) {
      pl.setNomPlaylist ("Collection globale");
      pl.setListe (this.musiquesToPlaylist (this.musiqueSessionBean
          .listeMusiques ()));
      pl.setProprietaire (new Utilisateur ());
      pl.setUtilisateurs (new HashSet<Utilisateur> ());
      pl.getProprietaire ().setEmail ("admin@localhost");
    }
    final PrintWriter out = response.getWriter ();
    try {
      out.println ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      out.println ("<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">");
      if (pl != null) {
        out.println ("<title>" + pl.getNomPlaylist () + "</title>");
        if (pl.getProprietaire () != null) {
          out.println ("<creator>" + pl.getProprietaire ().getEmail ()
              + "</creator>");
        }
      }
      out.println ("<link>Playlist.act?action=play&amp;plId=" + plId
          + "</link>");
      if (pl != null) {
        out.println ("<info>[" + plId + "] partage avec "
            + pl.getUtilisateurs ().size () + " utilisateurs</info>");
      }

      out.println ("<image></image>");

      out.println ("<trackList>");

      if ((pl != null) && (pl.getListe () != null)) {
        for (int i = 0 ; i < pl.getListe ().size () ; i++) {
          final EntreePlaylist ep = pl.getListe ().get (i);
          if (!ep.getMusique ().isIndisponible ()) {
            this.addEntreePlaylist (request, response, ep);
          }
          out.println ("</track>");
        }
      }
      out.println ("</trackList>");
      out.println ("</playlist>");
    } finally {
      out.close ();
    }
    return null;
  }

  private List<EntreePlaylist> musiquesToPlaylist (
      final Collection<Musique> musiques) {
    final List<EntreePlaylist> simulatedPlaylist = new LinkedList<EntreePlaylist> ();
    int i = 0;
    for (final Musique m : musiques) {
      final EntreePlaylist ep = new EntreePlaylist ();
      ep.setMusique (m);
      ep.setNumeroApparition (i++);
      simulatedPlaylist.add (ep);
    }
    return simulatedPlaylist;
  }

}
