/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.video2mp3;

import java.io.InputStream;

/**
 * 
 * @author L3 Miage
 */
public class ConvertedStream {

  private String      filename;
  private long        size;
  private InputStream stream;
  private String      url;

  public String getFilename () {
    return this.filename;
  }

  public long getSize () {
    return this.size;
  }

  public InputStream getStream () {
    return this.stream;
  }

  public String getUrl () {
    return this.url;
  }

  public void setFilename (final String filename) {
    this.filename = filename;
  }

  public void setSize (final long size) {
    this.size = size;
  }

  public void setStream (final InputStream stream) {
    this.stream = stream;
  }

  public void setUrl (final String url) {
    this.url = url;
  }

}
