package me.flotsam.frettler.instrument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import me.flotsam.frettler.engine.Note;

@Data
@AllArgsConstructor
@ToString
public class Tone {
  private int index;
  private Note note;
  private int octave;
  private int stringNum;
  private Note stringNote;
  private int fret;
}
