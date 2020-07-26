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
  m2("m2", 1, true),
  M2("M2", 2, true),
  m3("m3", 3, true),
  M3("M3", 4, true),
  P4("P4", 5, true),

  d5("d5", 6, false),

  P5("P5", 7, true),
  m6("m6", 8, true),
  M6("M6", 9, true),
  m7("m7", 10, true),
  M7("M7", 11, true),
  P8("P8", 12, true),
  
  m9("m9", 14, false),
  M9("M9", 14, false),
  m10("m10", 16, false),
  M10("M10", 17, false),
  M11("M11", 18, false),
  m12("m12", 20, false), 
  M12("M12", 20, false), 
  M13("M13", 22, false), 
  M14("M14", 24, false), 
  M15("M15", 25, false); 
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
