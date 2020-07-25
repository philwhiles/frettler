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

  /**
   * Can only be created by the Scale class which will also assign it its Position in the Scale
   * linked list
   * @param note the note it represents
   * @param interval is interval within the scale it is being created for
   * @param scale the scale it will belong to
   */
  ScaleNote(Note note, Optional<ScaleInterval> interval, Scale scale) {
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

  public boolean equalsTonally(ScaleNote other) {
    return (other.note == this.note);
  }
}
