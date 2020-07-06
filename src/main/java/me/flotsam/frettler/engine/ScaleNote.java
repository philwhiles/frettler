package me.flotsam.frettler.engine;

import java.util.Optional;
import lombok.Data;

@Data
public class ScaleNote {
  private Note note;
  private Position position;
  private ScaleNote nextScaleNote;
  private Optional<ScaleInterval> interval;
  private Scale scale;

  public ScaleNote(Note note, Optional<ScaleInterval> interval, Scale scale) {
    this.note = note;
    this.interval = interval;
    this.scale = scale;
  }

  void setPosition(Position position) {
    this.position = position;
  }

  public String toString() {
    return note.getLabel() + " " + interval.toString();
  }
}
