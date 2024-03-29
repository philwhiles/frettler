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

public class BassGuitar extends FrettedInstrument {
  
  private static final Note[] DEFAULT_STRINGS = new Note[] {Note.E, Note.A, Note.D, Note.G};

  public BassGuitar() {
    this(DEFAULT_STRINGS, null, DEFAULT_FRETS);
  }

  public BassGuitar(Note[] strings, Tuning tuning, Integer frets) {
    super(FrettedInstrument.InstrumentType.BASSGUITAR, frets, strings.length > 0 ? strings : DEFAULT_STRINGS, tuning);
  }
}
