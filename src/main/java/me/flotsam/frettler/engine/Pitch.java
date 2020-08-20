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

public enum Pitch {
  C("C"), Cs("C#"), D("D"), Ds("D#"), E("E"), F("F"), Fs("F#"), G("G"), Gs("G#"), A("A"), As( "A#"), B("B");

  @Getter private String label;

  private Pitch(String label) {
    this.label = label;
  }
}
