package org.toilelibre.libe.youstream.video2mp3;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 
 * @author L3 Miage
 */
public class MP3Converter {

  public static ConvertedStream convert (final String url) throws IOException {
    final HttpClient client = new HttpClient ();
    String data[] = new String[0];
    String waitPage = "";
    while (data.length < 6) {
      final PostMethod post = new PostMethod (
          "http://www.video2mp3.net/check.php");
      post.addParameter ("url", url);
      post.addParameter ("hq", "1");
      post.addParameter ("server", "");
      post.addParameter ("lang", "fr");
      post.addParameter ("adblock", "0");
      MP3Converter.executeLoop (client, post, true);
      waitPage = post.getResponseBodyAsString ();
      data = waitPage.split ("\\|");
      if (data.length < 6) {
        try {
          Thread.sleep (1000);
        } catch (final InterruptedException e) {
        }
      }
    }
    final String pattern = data[2];
    final String service = data[3];
    String hostname = data[5];

    final String url2 = "http://www.video2mp3.net/update.php" + "?v=" + pattern
        + "&service=" + service + "&hostname=" + hostname + "&lang=fr";
    GetMethod get = new GetMethod (url2);
    String linkPage = "MESSAGE";
    int failures = 0;
    if (waitPage.startsWith ("WAIT")) {
      linkPage = waitPage;
      hostname = data[4];
    } else {
      while (linkPage.startsWith ("MESSAGE") && !waitPage.startsWith ("WAIT")
          && (failures < 10)) {
        MP3Converter.executeLoop (client, get, true);
        linkPage = get.getResponseBodyAsString ();
        if ((linkPage != null)
            && linkPage.contains ("Waiting for conversion server")) {
          failures++;
        }
        try {
          Thread.sleep (10000);
        } catch (final InterruptedException ex) {
        }
      }
    }
    if (failures == 10) { return null; }
    int start = linkPage.indexOf ("http://www.video2mp3.net/fr/view/");
    if (start == -1) { return null; }
    String downloadHref = linkPage.substring (start);
    if (downloadHref.indexOf ('|') != -1) {
      downloadHref = downloadHref.substring (0, downloadHref.indexOf ('|'));
    }
    get = new GetMethod (downloadHref);
    MP3Converter.executeLoop (client, get, true);
    final String downloadPage = get.getResponseBodyAsString ();
    start = downloadPage.indexOf ("\"http://www.video2mp3.net/load");
    final int end = downloadPage.indexOf ("\"", start + 1);
    final String downloadLink = downloadPage.substring (start + 1, end);
    get = new GetMethod (downloadLink);
    MP3Converter.executeLoop (client, get, false);
    final InputStream is = get.getResponseBodyAsStream ();
    String filename = get.getPath ();
    if (downloadLink.indexOf ('/') != -1) {
      filename = filename.substring (filename.lastIndexOf ('/') + 1);
    }

    final ConvertedStream cs = new ConvertedStream ();
    cs.setSize (get.getResponseContentLength ());
    cs.setStream (is);
    cs.setUrl (downloadLink);
    cs.setFilename (URLDecoder.decode (filename, "UTF-8"));
    return cs;
  }

  private static void displayProgress (final String message) {
    System.out.print ("\rEtape ");
    if (message.contains ("<h3>Patientez s'il-vous-pla")
        || message.contains ("<h3>Waiting for conversion server...</h3>")) {
      System.out.print ("0 / 4 - La conversion n'a pas démarré");
    } else if (message.contains ("Position dans la file d'attente: <h2>?</h2>")) {
      System.out.print ("2 / 4 - Placement dans une file d'attente");
    } else if (message.contains ("Saisie de la video:")) {
      final Matcher m = Pattern.compile ("(.)+<h2>([0-9\\.]+)%</h2>(.)+")
          .matcher (message);
      if (m.find ()) {
        final double percent = Double.parseDouble (m.group (2));
        System.out.print ("1 / 4 - Capture en cours...");
        MP3Converter.showBar (60, percent);
      } else {
        System.out.print ("1 / 4 - Tentative de capture            ");
      }
    } else if (message
        .matches ("(.)+Position dans la file d'attente: <h2>([0-9]+)</h2>")) {
      final Matcher m = Pattern.compile ("(.)+<h2>([0-9]+)</h2>(.)+").matcher (
          message);
      m.find ();
      final int position = Integer.parseInt (m.group (2));
      System.out.print ("1 / 4 - Position dans " + "deuxième file d'attente : "
          + position);
    } else if (message.contains ("Conversion de la video en format MP3:")) {
      final Matcher m = Pattern.compile ("(.)+<h2>([0-9\\.]+)%</h2>(.)+")
          .matcher (message);
      m.find ();
      final int percent = (int) Float.parseFloat (m.group (2));
      System.out.print ("2 / 4 - Conversion en cours...");
      MP3Converter.showBar (60, percent);
    } else if (message.startsWith ("REDIRECT|") || message.startsWith ("WAIT|")) {
      System.out.print ("3 / 4 - Recherche du fichier audio");
    }
  }

  private static void executeLoop (final HttpClient client,
      final HttpMethod hm, final boolean logMessage) {
    int responseCode = 0;
    while (responseCode != 200) {
      try {
        responseCode = client.executeMethod (hm);
        if (logMessage) {
          MP3Converter.displayProgress (hm.getResponseBodyAsString ());
        }
      } catch (final HttpException e) {
      } catch (final IOException e) {
      }
      try {
        Thread.sleep (1000);
      } catch (final InterruptedException e) {
      }
    }
  }

  private static void showBar (final int widthBar, final double percent) {
    final int blocs = (int) (percent / 100.0 * widthBar);
    System.out.print ("[\033[1;4;35m");
    for (int i = 0 ; i < widthBar ; i++) {
      System.out.print (" ");
      if (i == blocs - 1) {
        System.out.print ("\033[0m");
      }
      System.out.print (" ");
    }
    System.out.print ("] " + percent + "%");
  }
}
