/*
 * Copyright (C) 2020 Philip Whiles
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package me.flotsam.frettler.instrument;

import static me.flotsam.frettler.instrument.FrettedInstrument.InstrumentType.GUITAR;
import static me.flotsam.frettler.engine.Note.C;
import static me.flotsam.frettler.engine.Note.D;
import static me.flotsam.frettler.engine.Note.E;
import static me.flotsam.frettler.engine.Note.F;
import static me.flotsam.frettler.engine.Note.G;
import static me.flotsam.frettler.engine.Note.A;
import static me.flotsam.frettler.engine.Note.B;

import static me.flotsam.frettler.engine.Note.Cs;
import static me.flotsam.frettler.engine.Note.Ds;
import static me.flotsam.frettler.engine.Note.Es;
import static me.flotsam.frettler.engine.Note.Fs;
import static me.flotsam.frettler.engine.Note.Gs;
import static me.flotsam.frettler.engine.Note.As;
import static me.flotsam.frettler.engine.Note.Bs;

import static me.flotsam.frettler.engine.Note.Cb;
import static me.flotsam.frettler.engine.Note.Db;
import static me.flotsam.frettler.engine.Note.Eb;
import static me.flotsam.frettler.engine.Note.Fb;
import static me.flotsam.frettler.engine.Note.Gb;
import static me.flotsam.frettler.engine.Note.Ab;
import static me.flotsam.frettler.engine.Note.Bb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import me.flotsam.frettler.engine.Note;

public enum Tuning {

  //@formatter:off
  GUITAR_STANDARD(
      GUITAR,
      "Standard",
      E, A, D, G, B, E
  ),
  GUITAR_DADGAD(
      GUITAR,
      "DADGAD",
      D, A, D, G, A, D
  ),
  GUITAR_DOBRO(
      GUITAR,
      "Dobro",
      G, B, D, G, B, D
  ),
  GUITAR_AUS4(
      GUITAR,
      "Asus4",
      E, A, D, E, A, E
  ),
  GUITAR_HALF_STEP_DOWN(
      GUITAR,
      "Half Step Down",
      Eb, Ab, Db, Gb, Bb, Eb
  ),
  GUITAR_B_MODAL(
      GUITAR,
      "B Modal",
      B, Fs, Cs, Fs, B, Ds
  ),
  GUITAR_FULL_STEP_DOWN(
      GUITAR,
      "Full Step Down",
      D, G, C, F, A, D
  ),
  GUITAR_ONE_PLUS_HALF_STEP_DOWN(
      GUITAR,
      "1.5 Steps Down",
      Db, Gb, B, E, Ab, Db
  ),
  GUITAR_TWO_STEPS_DOWN(
      GUITAR,
      "Two Steps Down",
      C, F, Bb, Eb, G, C
  ),
  GUITAR_BARITONE(
      GUITAR,
      "Baritone",
      B, E, A, D, Fs, B
  ),
  GUITAR_DROP_D(
      GUITAR,
      "Drop D",
      D, A, D, G, B, E
  ),
  GUITAR_DROP_C_SHARP(
      GUITAR,
      "Drop Cs",
      Cs, Gs, Cs, Fs, As, Ds
  ),
  GUITAR_DROP_C(
      GUITAR,
      "Drop C",
      C, G, C, F, A, D
  ),
  GUITAR_DROP_B(
      GUITAR,
      "Drop B",
      B, Fs, B, E, Gs, Cs
  ),
  GUITAR_DROP_A_SHARP(
      GUITAR,
      "Drop As",
      As, F, As, Ds, G, C
  ),
  GUITAR_DROP_A(
      GUITAR,
      "Drop A",
      A, E, A, D, Fs, B
  ),
  GUITAR_DROP_G_SHARP(
      GUITAR,
      "Drop Gs",
      Gs, Ds, Gs, Cs, F, As
  ),
  GUITAR_DROP_E(
      GUITAR,
      "Drop E",
      E, B, E, A, Cs, Fs
  ),
  GUITAR_DOUBLE_DROP_D(
      GUITAR,
      "Double Drop D",
      D, A, D, G, B, D
  ),
  GUITAR_DOUBLE_DROP_C_SHARP(
      GUITAR,
      "Double Drop Cs",
      Cs, Gs, Cs, Fs, As, Cs
  ),
  GUITAR_DOUBLE_DROP_C(
      GUITAR,
      "Double Drop C",
      C, G, C, F, A, C
  ),
  GUITAR_DOUBLE_DROP_B(
      GUITAR,
      "Double Drop B",
      B, Fs, B, E, Gs, B
  ),
  GUITAR_DOUBLE_DROP_A_SHARP(
      GUITAR,
      "Double Drop As",
      Bb, F, Bb, Eb, G, Bb
  ),
  GUITAR_DOUBLE_DROP_A(
      GUITAR,
      "Double Drop A",
      A, E, A, D, Fs, A
  ),
  GUITAR_CROSS_NOTE_C(
      GUITAR,
      "Cross Note C",
      C, G, C, G, C, Eb
  ),
  GUITAR_CROSS_NOTE_D(
      GUITAR,
      "Cross Note D",
      D, A, D, F, A, D
  ),
  GUITAR_CROSS_NOTE_E(
      GUITAR,
      "Cross Note E",
      E, B, E, G, B, E
  ),
  GUITAR_CROSS_NOTE_F(
      GUITAR,
      "Cross Note F",
      F, Ab, C, F, C, F
  ),
  GUITAR_CROSS_NOTE_G(
      GUITAR,
      "Cross Note G",
      D, G, D, G, Bb, D
  ),
  GUITAR_CROSS_NOTE_A(
      GUITAR,
      "Cross Note A",
      E, A, E, A, C, E
  ),
  GUITAR_OPEN_A(
      GUITAR,
      "Open A",
      E, A, Cs, E, A, E
  ),
  GUITAR_OPEN_B(
      GUITAR,
      "Open .",
      B, Fs, B, Fs, B, Ds
  ),
  GUITAR_OPEN_C(
      GUITAR,
      "Open C",
      C, G, C, G, C, E
  ),
  GUITAR_OPEN_D(
      GUITAR,
      "Open D",
      D, A, D, Fs, A, D
  ),
  GUITAR_OPEN_E(
      GUITAR,
      "Open E",
      E, B, E, Gs, B, E
  ),
  GUITAR_OPEN_F(
      GUITAR,
      "Open F",
      F, A, C, F, C, F
  ),
  GUITAR_OPEN_G(
      GUITAR,
      "Open G",
      D, G, D, G, B, D
  );

  //@formatter:on

  @Getter
  private String label;
  @Getter
  private Note[] notes;
  @Getter
  private FrettedInstrument.InstrumentType instrumentType;


  private Tuning(FrettedInstrument.InstrumentType instrumentType, String label, Note... notes) {
    this.instrumentType = instrumentType;
    this.label = label;
    this.notes = notes;
  }
}
