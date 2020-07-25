package me.flotsam.frettler.instrument;

import me.flotsam.frettler.engine.Note;

public class Ukelele extends FrettedInstrument {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.G, Note.C, Note.E, Note.A};

  public Ukelele() {
    this(DEFAULT_STRINGS, FRETS);
  }

  public Ukelele(Note[] strings, Integer frets) {
    super("Ukelele", frets, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
}
