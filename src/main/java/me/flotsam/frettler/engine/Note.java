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

package me.flotsam.frettler.engine;

import static me.flotsam.frettler.engine.Note.Accidental.FLAT;
import static me.flotsam.frettler.engine.Note.Accidental.NATURAL;
import static me.flotsam.frettler.engine.Note.Accidental.SHARP;
import java.util.Optional;
import lombok.Getter;

public enum Note {
  // @formatter:off
  C(Pitch.C, NATURAL, "C"),
  D(Pitch.D, NATURAL, "D"),
  E(Pitch.E, NATURAL, "E"),
  F(Pitch.F, NATURAL, "F"),
  G(Pitch.G, NATURAL, "G"),
  A(Pitch.A, NATURAL, "A"),
  B(Pitch.B, NATURAL, "B"),

  Cs(Pitch.Cs, SHARP, "C#"),
  Ds(Pitch.Ds, SHARP, "D#"),
  Es(Pitch.F, SHARP, "E#"),
  Fs(Pitch.Fs, SHARP, "F#"),
  Gs(Pitch.Gs, SHARP, "G#"),
  As(Pitch.As, SHARP, "A#"),
  Bs(Pitch.C, SHARP, "B#"),

  Cb(Pitch.B, FLAT, "Cb"),
  Db(Pitch.Cs, FLAT, "Db"),
  Eb(Pitch.Ds, FLAT, "Eb"),
  Fb(Pitch.E, FLAT, "Fb"),
  Gb(Pitch.Fs, FLAT, "Gb"),
  Ab(Pitch.Gs, FLAT, "Ab"),
  Bb(Pitch.As, FLAT, "Bb");
  // @formatter:on

  @Getter
  private Pitch pitch;
  @Getter
  private Accidental accidental;
  @Getter
  private String label;

  private Note(Pitch pitch, Accidental accidental, String label) {
    this.pitch = pitch;
    this.accidental = accidental;
    this.label = label;
  }

  public Note getAlternate() {
    if (this.accidental != NATURAL) {
      for (Note note : values()) {
        // find another Note having same pitch but diff accidental
        if (this != note && this.accidental != note.accidental && note.pitch == this.pitch) {
          return note;
        }
      }
    }
    return this;
  }

  public Note getFlat() {
    for (Note note : values()) {
      if (FLAT == note.accidental && note.pitch == this.pitch) {
        return note;
      }
    }
    return this;
  }

//  public Note getSharp() {
//    for (Note note : values()) {
//      if (SHARP == note.accidental && note.pitch == this.pitch) {
//        return note;
//      }
//    }
//    return this;
//  }

//  public boolean isFlat() {
//    return accidental == FLAT;
//  }
//
//  public boolean isSharp() {
//    return accidental == SHARP;
//  }

  public static Note forPitch(Pitch pitch) {
    for (Note candidateNote : values()) {
      if (candidateNote.pitch == pitch) {
        return candidateNote;
      }
    }
    return null;// won't happen
  }

  public static Optional<Note> getSharp(Pitch pitch) {
    return getAccidental(pitch, SHARP);
  }

  public static Optional<Note> getFlat(Pitch pitch) {
    return getAccidental(pitch, FLAT);
  }

  public static Optional<Note> getAccidental(Pitch pitch, Accidental accidental) {
    Optional<Note> note = Optional.empty();
    for (Note candidateNote : values()) {
      if (candidateNote.pitch == pitch && candidateNote.accidental == accidental) {
        note = Optional.of(candidateNote);
        break;
      }
    }
    return note;
  }

  public enum Accidental {
    NATURAL, SHARP, FLAT
  }
}
