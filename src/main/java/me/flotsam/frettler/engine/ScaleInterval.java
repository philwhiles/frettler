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
  P1("P1", 0, true),
  m2("m2", 1, false),
  M2("M2", 2, false),
  m3("m3", 3, false),
  M3("M3", 4, false),
  P4("P4", 5, true),

  d5("d5", 6, false),

  P5("P5", 7, true),
  m6("m6", 8, false),
  M6("M6", 9, false),
  m7("m7", 10, false),
  M7("M7", 11, false),
  P8("P8", 12, true),
  
  m9("m9", 13, false),
  M9("M9", 14, false),
  m10("m10", 15, false),
  M10("M10", 16, false),
  m11("m11", 17, false),
  M11("M11", 18, false);
  //@formatter:on

  @Getter
  private boolean perfect;
  @Getter
  private int semiTones;
  @Getter
  private String label;

  private ScaleInterval(String label, int semiTones, boolean perfect) {
    this.label = label;
    this.semiTones = semiTones;
    this.perfect = perfect;
  }
}
