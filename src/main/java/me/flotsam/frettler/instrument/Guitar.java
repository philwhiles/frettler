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

package me.flotsam.frettler.instrument;

import me.flotsam.frettler.engine.Note;

public class Guitar extends FrettedInstrument {
  
  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};

  public Guitar() {
    this(DEFAULT_STRINGS, DEFAULT_FRETS);
  }

  public Guitar(Note[] strings, Integer frets) {
    super("Guitar", frets, strings.length > 0 ? strings : DEFAULT_STRINGS);
  }
}
