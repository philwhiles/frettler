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

import static me.flotsam.frettler.engine.ScaleInterval.M2;
import static me.flotsam.frettler.engine.ScaleInterval.M3;
import static me.flotsam.frettler.engine.ScaleInterval.M6;
import static me.flotsam.frettler.engine.ScaleInterval.M7;
import static me.flotsam.frettler.engine.ScaleInterval.P1;
import static me.flotsam.frettler.engine.ScaleInterval.P4;
import static me.flotsam.frettler.engine.ScaleInterval.P5;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum ScaleNoteSequence {
  //@formatter:off
  DIATONIC_SECONDS("Diatonic Seconds", 2, P1,M2, M2,M3, M3,P4, P4,P5, P5,M6, M6,M7, M7,P1);
  //@formatter:on

  @Getter
  private List<ScaleInterval> intervals;
  @Getter
  private String label;
  @Getter
  private int grouping;

  private ScaleNoteSequence(String label, int grouping, ScaleInterval ... intervals) {
    this.label = label;
    this.grouping = grouping;
    this.intervals = Arrays.asList(intervals);
  }
}
