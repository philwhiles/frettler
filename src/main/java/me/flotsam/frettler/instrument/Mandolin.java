package me.flotsam.frettler.instrument;

import me.flotsam.frettler.engine.Note;

public class Mandolin extends FrettedInstrument {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.G, Note.D, Note.A, Note.E};

  public Mandolin() {
    this(DEFAULT_STRINGS, FRETS);
  }

  public Mandolin(Note[] strings, Integer frets) {
    super("Mandolin", frets, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
}
