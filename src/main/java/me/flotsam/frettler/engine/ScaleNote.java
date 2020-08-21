/*
    Copyright (C) 2020  Philip Whiles

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package me.flotsam.frettler.engine;

import java.util.Optional;
import lombok.Getter;

// pitch
public class ScaleNote {
  @Getter private Pitch pitch;
  @Getter private Position position;
  @Getter private ScaleNote nextScaleNote;
  @Getter private ScaleNote prevScaleNote;
  @Getter private Optional<ScaleInterval> interval;
  @Getter private Scale scale;

  /**
   * Can only be created by the Scale class which will also assign it its Position in the Scale
   * linked list
   * @param note the note it represents
   * @param interval is interval within the scale it is being created for
   * @param scale the scale it will belong to
   */
  ScaleNote(Pitch pitch, Optional<ScaleInterval> interval, Scale scale) {
    this.pitch = pitch;
    this.interval = interval;
    this.scale = scale;
  }
  void setPrevScaleNote(ScaleNote scaleNote) {
    this.prevScaleNote = scaleNote;
  }
  void setNextScaleNote(ScaleNote scaleNote) {
    this.nextScaleNote = scaleNote;
  }
  void setPosition(Position position) {
    this.position = position;
  }

  public String toString() {
    return pitch.getLabel() + " " + interval.toString();
  }

  public boolean equalsTonally(ScaleNote other) {
    return (other.pitch == this.pitch);
  }
}
