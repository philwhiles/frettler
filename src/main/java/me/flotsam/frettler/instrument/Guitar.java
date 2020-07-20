package me.flotsam.frettler.instrument;

import me.flotsam.frettler.engine.Note;

public class Guitar extends FrettedInstrument {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};

  public Guitar() {
    this(DEFAULT_STRINGS);
  }

  public Guitar(Note[] strings) {
    super("Guitar", FRETS, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
}
