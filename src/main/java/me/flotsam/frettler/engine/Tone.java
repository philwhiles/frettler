package me.flotsam.frettler.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

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
