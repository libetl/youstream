/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.toilelibre.libe.youstream.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.farng.mp3.MP3File;
import org.farng.mp3.filename.FilenameTag;
import org.farng.mp3.id3.AbstractID3;
import org.toilelibre.libe.youstream.model.Categorie;
import org.toilelibre.libe.youstream.model.Musique;
import org.toilelibre.libe.youstream.session.CategorieSessionBeanLocal;

/**
 * 
 * @author L3 Miage
 */
public class ID3Helper {
  private static String format (final String input)
      throws CharacterCodingException {
    String output = input;
    final Charset charset = Charset.forName ("US-ASCII");
    final CharsetDecoder decoder = charset.newDecoder ();
    final CharsetEncoder encoder = charset.newEncoder ();
    final ByteBuffer bbuf = encoder.encode (CharBuffer.wrap (input));
    final CharBuffer cbuf = decoder.decode (bbuf);
    output = cbuf.toString ();
    return output;
  }

  private static void setId3 (
      final CategorieSessionBeanLocal categorieSessionBean,
      final Musique musique, final AbstractID3 id3) {
    final Categorie cat = categorieSessionBean.getCategorie (id3
        .getSongGenre ());
    try {
      musique.setArtiste (ID3Helper.format (id3.getLeadArtist ()));
    } catch (final UnsupportedOperationException uoe) {
      musique.setArtiste ("Artiste inconnu");
    } catch (final CharacterCodingException cee) {
      musique.setArtiste ("Artiste inconnu");
    }
    try {
      musique.setTitre (ID3Helper.format (id3.getSongTitle ()));
    } catch (final UnsupportedOperationException uoe) {
      musique.setTitre ("Titre inconnu");
    } catch (final CharacterCodingException cee) {
      musique.setArtiste ("Titre inconnu");
    }
    try {
      musique.setAlbum (ID3Helper.format (id3.getAlbumTitle ()));
    } catch (final UnsupportedOperationException uoe) {
      musique.setAlbum ("Album inconnu");
    } catch (final CharacterCodingException cee) {
      musique.setArtiste ("Album inconnu");
    }
    try {
      musique.setAnnee (Integer.parseInt (id3.getYearReleased ()));
    } catch (final NumberFormatException nfe) {
      musique.setAnnee (0);
    } catch (final UnsupportedOperationException uoe) {
      musique.setAnnee (0);
    }
    musique.setCategorie (cat);
  }

  private static void setId3 (
      final CategorieSessionBeanLocal categorieSessionBean,
      final Musique musique, final FilenameTag id3fn) {
    try {
      musique.setArtiste (id3fn.getAuthorComposer ());
      musique.setTitre (id3fn.getSongTitle ());
    } catch (final UnsupportedOperationException uoe) {
      musique.setArtiste ("Artiste inconnu");
      musique.setArtiste ("Titre inconnu");
    } catch (final NullPointerException npe) {
      musique.setArtiste ("Artiste inconnu");
      musique.setArtiste ("Titre inconnu");
    }
  }

  public static void setTags (
      final CategorieSessionBeanLocal categorieSessionBean,
      final Musique musique, final MP3File tags) {
    final AbstractID3 id3v2 = tags.getID3v2Tag ();
    final AbstractID3 id3v1 = tags.getID3v1Tag ();
    final FilenameTag id3fn = tags.getFilenameTag ();
    if (id3v2 != null) {
      ID3Helper.setId3 (categorieSessionBean, musique, id3v2);
    } else if (id3v1 != null) {
      ID3Helper.setId3 (categorieSessionBean, musique, id3v1);
    } else if (id3fn != null) {
      ID3Helper.setId3 (categorieSessionBean, musique, id3fn);
    }
  }
}
