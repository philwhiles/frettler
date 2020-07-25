package me.flotsam.frettler.instrument;

import me.flotsam.frettler.engine.Note;

public class Banjo extends FrettedInstrument {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.G, Note.D, Note.G, Note.B, Note.D};

  public Banjo() {
    this(DEFAULT_STRINGS, FRETS);
  }

  public Banjo(Note[] strings, Integer frets) {
    super("Banjo", frets, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
  
  public boolean isBanjo() {
    return true;
  }
}
