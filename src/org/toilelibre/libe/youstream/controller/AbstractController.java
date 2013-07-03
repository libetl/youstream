/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author L3 miage
 */
public abstract class AbstractController extends
    org.springframework.web.servlet.mvc.AbstractController {

  /**
	 * 
	 */
  private static final long serialVersionUID = -1431882139840719331L;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   * 
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   * @return
   * @throws ServletException
   *           if a servlet-specific error occurs
   * @throws IOException
   *           if an I/O error occurs
   */
  @Override
  public ModelAndView handleRequestInternal (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    final String action = request.getParameter ("action");
    try {
      final Method m = this.getClass ()
          .getMethod (
              action,
              new Class<?>[] { HttpServletRequest.class,
                  HttpServletResponse.class });
      m.invoke (this, new Object[] { request, response });
    } catch (final IllegalAccessException ex) {
      Logger.getLogger (AbstractController.class.getName ()).log (Level.SEVERE,
          null, ex);
    } catch (final IllegalArgumentException ex) {
      Logger.getLogger (AbstractController.class.getName ()).log (Level.SEVERE,
          null, ex);
    } catch (final InvocationTargetException ex) {
      Logger.getLogger (AbstractController.class.getName ()).log (Level.SEVERE,
          null, ex);
    } catch (final NoSuchMethodException ex) {
      Logger.getLogger (AbstractController.class.getName ()).log (Level.SEVERE,
          null, ex);
    } catch (final SecurityException ex) {
      Logger.getLogger (AbstractController.class.getName ()).log (Level.SEVERE,
          null, ex);
    }
    return null;

  }

}
