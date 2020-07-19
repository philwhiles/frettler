package me.flotsam.frettler.instrument.stringed;

import me.flotsam.frettler.engine.Note;

public class Ukelele extends StringedInstrument {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.G, Note.C, Note.E, Note.A};

  public Ukelele() {
    this(DEFAULT_STRINGS);
  }

  public Ukelele(Note[] strings) {
    super("Ukelele", FRETS, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
}
