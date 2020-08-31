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

import lombok.Getter;

public enum ScaleInterval {
  //@formatter:off
  P1("P1", 0, true, false),
  m2("m2", 1, false, true),
  M2("M2", 2, false, false),
  m3("m3", 3, false, true),
  M3("M3", 4, false, false),
  P4("P4", 5, true, false),

  d5("d5", 6, false, true),

  P5("P5", 7, true, false),
  m6("m6", 8, false, true),
  M6("M6", 9, false, false),
  m7("m7", 10, false, true),
  M7("M7", 11, false, false),
  P8("P8", 12, true, false),
  
  m9("m9", 13, false, true),
  M9("M9", 14, false, false),
  m10("m10", 15, false, false),
  M10("M10", 16, false, false),
  m11("M11", 18, false, true),
  M11("M11", 18, false, false);
  //@formatter:on

  @Getter
  private boolean flat;
  @Getter
  private boolean perfect;
  @Getter
  private int semiTones;
  @Getter
  private String label;

  private ScaleInterval(String label, int semiTones, boolean perfect, boolean flat) {
    this.label = label;
    this.semiTones = semiTones;
    this.perfect = perfect;
    this.flat = flat;
  }
}
