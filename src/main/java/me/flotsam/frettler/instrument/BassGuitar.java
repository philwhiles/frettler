package me.flotsam.frettler.instrument;

import me.flotsam.frettler.engine.Note;

public class BassGuitar extends FrettedInstrument {
  
  public static final int FRETS = 12;

  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.E, Note.A, Note.D, Note.G};

  public BassGuitar() {
    this(DEFAULT_STRINGS);
  }

  public BassGuitar(Note[] strings) {
    super("Bass Guitar", FRETS, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
}
