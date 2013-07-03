package org.toilelibre.libe.youstream.video2mp3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownloadStream {

  public static void download (final InputStream is, final File f,
      final long size) throws IOException {
    final FileOutputStream fos = new FileOutputStream (f);
    f.createNewFile ();
    int read = 0;
    long totalRead = read;
    final byte[] bytes = new byte[1024];

    while ((read = is.read (bytes)) != -1) {
      fos.write (bytes, 0, read);
      totalRead += read;
      DownloadStream.showBar (40, totalRead, size);
    }

    System.out.println ();

    is.close ();
    fos.flush ();
    fos.close ();

  }

  private static void showBar (final int widthBar, final long read,
      final long size) {
    final int blocs = (int) (((double) read / size) * widthBar);
    final double percent = Math.ceil (((double) read / size) * 10000) / 100;
    System.out.print ("\rEtape 4 / 4 - Téléchargement [\033[1;4;37;42m");
    for (int i = 0 ; i < widthBar ; i++) {
      System.out.print (" ");
      if ((i == blocs - 1) || ((blocs == 0) && (i == 0))) {
        System.out.print ("\033[1;4;37;40m");
      }
    }
    System.out.print ("\033[24;37m]\033[1m " + percent + "%\033[0m");
  }

}
