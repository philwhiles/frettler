package me.flotsam.frettler.instrument.stringed;

import me.flotsam.frettler.engine.Note;

public class Banjo extends StringedInstrument {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.G, Note.D, Note.G, Note.B, Note.D};

  public Banjo() {
    this(DEFAULT_STRINGS);
  }

  public Banjo(Note[] strings) {
    super("Banjo", FRETS, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
}
