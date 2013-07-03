/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.ModelAndView;
import org.toilelibre.libe.youstream.model.EntreePlaylist;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.session.MusiqueSessionBeanLocal;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name = "SearchServlet", urlPatterns = {"/Search"})
@Configurable (dependencyCheck = true)
public class SearchController extends AbstractController {

  /**
	 * 
	 */
  private static final long       serialVersionUID = -2678541322723139741L;
  @Autowired
  private MusiqueSessionBeanLocal musiqueSessionBean;

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
    final String forwardTo = "/jsp/playlist/ecouter.jsp";
    final String query = request.getParameter ("query");
    final Collection<Musique> musiques = this.musiqueSessionBean
        .rechercheMusiques (query);
    final List<EntreePlaylist> playlistR = this.musicsToPlaylist (musiques);
    request.setAttribute ("liste", playlistR);
    request.setAttribute ("nomPlaylist", "Recherche de '" + query + "'");
    request.setAttribute ("plId", "-3");
    request.setAttribute ("query", query);
    final RequestDispatcher rd = request.getRequestDispatcher (forwardTo);
    rd.forward (request, response);
    return null;
  }

  private List<EntreePlaylist> musicsToPlaylist (
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
