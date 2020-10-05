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

public class IntervalPatternMetadata {

  @Getter
  boolean minorSecond;
  @Getter
  boolean majorSecond;
  @Getter
  boolean augmentedSecond;
  @Getter
  boolean minorThird;
  @Getter
  boolean majorThird;
  @Getter
  boolean perfectFourth;
  @Getter
  boolean diminishedFifth;
  @Getter
  boolean perfectFifth;
  @Getter
  boolean minorSixth;
  @Getter
  boolean majorSixth;
  @Getter
  boolean minorSeventh;
  @Getter
  boolean majorSeventh;
  @Getter
  boolean majorNinth;
  @Getter
  boolean majorEleventh;
  @Getter
  boolean majorRange;
  @Getter
  boolean minorRange;
  @Getter
  boolean suspended;
  
  public enum Quality {
    MAJOR, MINOR, AUGMENTED, DIMINISHED, DOMINANT
  }
}
