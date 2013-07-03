/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.ModelAndView;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.session.MusiqueSessionBeanLocal;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name = "ListenServlet", urlPatterns = {"/Listen"})
@Configurable (dependencyCheck = true)
public class ListenController extends AbstractController {

  /**
	 * 
	 */
  private static final long       serialVersionUID = 5307124234400712667L;
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
    response.setContentType ("audio/mp3");
    response.setContentType ("application/octet-stream");
    final Musique m = this.musiqueSessionBean.get (Long.parseLong (request
        .getParameter ("mId")));
    final byte[] data = m.getMp3 ().getFileData ();
    final ByteBuffer bb = ByteBuffer.wrap (data);
    response.setContentLength (data.length);
    final ServletOutputStream out = response.getOutputStream ();
    if (request.getSession ().getAttribute ("lockDownload") == null) {
      request.getSession ().setAttribute ("lockDownload", new Object ());
    }

    // Objet lock pour empêcher plusieurs téléchargements en même temps
    final Object lock = request.getSession ().getAttribute ("lockDownload");

    // Empêcher tous les autres de télécharger
    synchronized (lock) {
      lock.notifyAll ();
    }

    int i = 0;
    final boolean[] stop = new boolean[1];
    stop[0] = false;
    // Thread qui détecte s'il faut arrêter ou non
    final Thread t = new Thread () {
      @Override
      public void run () {
        synchronized (lock) {
          try {
            lock.wait ();
          } catch (final InterruptedException ex) {
            Logger.getLogger (ListenController.class.getName ()).log (
                Level.INFO, "End of download");
          }
        }
        stop[0] = true;
      }
    };
    t.start ();

    char ch;
    while ((i < data.length) && !stop[0]) {
      ch = (char) bb.get (i++);
      out.write (ch);
    }

    t.interrupt ();
    if (stop[0] == true) {
      Logger.getLogger (ListenController.class.getName ()).log (Level.INFO,
          "Download interrupted");
    }
    if (out != null) {
      out.flush ();
      out.close ();
    }
    return null;

  }
}
