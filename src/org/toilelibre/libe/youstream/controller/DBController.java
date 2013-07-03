/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * 
 * @author L3 miage
 */
// @WebServlet(name="DBServlet", urlPatterns={"/DB"})
@Configurable (dependencyCheck = true)
public class DBController extends AbstractController {
  /**
	 * 
	 */
  private static final long           serialVersionUID = -3882489771354066828L;

  public void index (final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
	  OutputStream os = response.getOutputStream();
	  os.write(java.lang.System.getenv("VCAP_SERVICES").getBytes());
	  os.flush();
	  os.close();
  }

}
