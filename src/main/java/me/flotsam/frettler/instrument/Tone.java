package me.flotsam.frettler.instrument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import me.flotsam.frettler.engine.Note;

@Data
@AllArgsConstructor
@ToString
public class Tone {
  private final int index;
  private final Note note;
  private final int octave;
  private final int stringNum;
  private final Note stringNote;
  private final int fret;
  
  
  public boolean equalsTone(Tone other) {
    return (other.note == this.note && other.octave == this.octave);
  }
}
