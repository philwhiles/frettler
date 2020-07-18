package me.flotsam.frettler.instrument.guitar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import me.flotsam.frettler.engine.Note;

@Data
@AllArgsConstructor
@ToString
public class Fret {
  private final int index;
  private final Note note;
  private final int octave;
  private final int stringNum;
  private final Note stringNote;
  private final int fretNum;
  
  
  public boolean equalsTonally(Fret other) {
    return (other.note == this.note && other.octave == this.octave);
  }
}
