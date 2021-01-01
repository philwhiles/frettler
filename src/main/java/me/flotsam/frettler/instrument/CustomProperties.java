package me.flotsam.frettler.instrument;

import lombok.Data;
import me.flotsam.frettler.engine.Note;

@Data
public class CustomProperties {
  private Note[] strings;
  private int frets;
}
