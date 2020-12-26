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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import me.flotsam.frettler.instrument.FrettedInstrument.InstrumentDefinition;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_7FLAT5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_7SHARP5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_ADD11;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_ADD9;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_AUG;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_DIM;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_DIM7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_DOM9;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ6;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ69;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ6MIN7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MAJ9;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN6;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN7FLAT5;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN7SUS4;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MIN9;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_MINMAJ7;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_SUS2;
import static me.flotsam.frettler.engine.IntervalPattern.CHORD_SUS4;
import static me.flotsam.frettler.engine.Note.A;
import static me.flotsam.frettler.engine.Note.Ab;
import static me.flotsam.frettler.engine.Note.B;
import static me.flotsam.frettler.engine.Note.Bb;
import static me.flotsam.frettler.engine.Note.C;
import static me.flotsam.frettler.engine.Note.D;
import static me.flotsam.frettler.engine.Note.Db;
import static me.flotsam.frettler.engine.Note.E;
import static me.flotsam.frettler.engine.Note.Eb;
import static me.flotsam.frettler.engine.Note.F;
import static me.flotsam.frettler.engine.Note.G;
import static me.flotsam.frettler.engine.Note.Gb;
import static me.flotsam.frettler.instrument.FrettedInstrument.InstrumentDefinition.GUITAR_EADGBE;

public class ChordBank {

  //@formatter:off
  @Getter
  private static Map<InstrumentDefinition, List<ChordDefinition>> definitions = Map.of(
      GUITAR_EADGBE, Arrays.asList(
        new ChordDefinition(A, Ab, CHORD_DIM, "x,x,1,2,1,4"), // (C  Eb Ab A) : diminished triad (altered bass)
        new ChordDefinition(A, Ab, CHORD_MAJ, "x,0,2,1,2,0"), // (Db E  Ab A) : major triad (altered bass)
        new ChordDefinition(A, Ab, CHORD_SUS2, "x,0,2,1,0,0"), // (E  Ab A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, Ab, CHORD_SUS4, "4,x,0,2,3,0"), // (D  E  Ab A) : sus4 triad (altered bass)
        new ChordDefinition(A, B, CHORD_MAJ, "0,0,2,4,2,0"), // (Db E  A  B) : major triad (altered bass)
        new ChordDefinition(A, B, CHORD_MAJ, "x,0,7,6,0,0"), // (Db E  A  B) : major triad (altered bass)
        new ChordDefinition(A, B, CHORD_MIN, "0,0,7,5,0,0"), // (C  E  A  B) : minor triad (altered bass)
        new ChordDefinition(A, B, CHORD_MIN, "x,3,2,2,0,0"), // (C  E  A  B) : minor triad (altered bass)
        new ChordDefinition(A, B, CHORD_SUS4, "0,2,0,2,0,0"), // (D  E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(A, Bb, CHORD_SUS4, "0,1,x,2,3,0"), // (D  E  A  Bb) : sus4 triad (altered bass)
        new ChordDefinition(A, C, CHORD_SUS2, "0,0,7,5,0,0"), // (C  E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, C, CHORD_SUS2, "x,3,2,2,0,0"), // (C  E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, C, CHORD_SUS4, "x,x,0,2,1,0"), // (C  D  E  A) : sus4 triad (altered bass)
        new ChordDefinition(A, C, CHORD_SUS4, "x,x,0,5,5,5"), // (C  D  E  A) : sus4 triad (altered bass)
        new ChordDefinition(A, CHORD_5, "5,7,7,x,x,0"), // (E  A) : root and 5th (power chord)
        new ChordDefinition(A, CHORD_5, "5,7,7,x,x,5"), // (E  A): root and 5th (power chord)
        new ChordDefinition(A, CHORD_5, "5,7,7,x,x,x", new String[] {"1,3,4,x,x,x"}),
        new ChordDefinition(A, CHORD_5, "x,0,2,2,x,0"), // (E  A) : root and 5th (power chord)
        new ChordDefinition(A, CHORD_5, "x,0,2,x,x,x", new String[] {"x,2,x,x,x,x"}),
        new ChordDefinition(A, CHORD_7, "3,x,2,2,2,0"), // (Db E  G  A) : major triad, minor 7th
        new ChordDefinition(A, CHORD_7, "x,0,2,0,2,0", new String[] {"x,x,1,x,2,x", "x,x,2,x,3,x"}),
        new ChordDefinition(A, CHORD_7, "x,0,2,2,2,3", new String[] {"x,x,1,1,1,2", "x,x,1,2,3,4"}),
        new ChordDefinition(A, CHORD_7FLAT5, "x,0,1,2,2,3", new String[] {"x,x,1,2,2,4"}),
        new ChordDefinition(A, CHORD_7SHARP5, "x,0,3,0,2,1", new String[] {"x,x,3,x,2,1"}),
        new ChordDefinition(A, CHORD_ADD9, "0,0,2,4,2,0"), // (Db E  A  B) : major triad plus 9th
        new ChordDefinition(A, CHORD_ADD9, "x,0,7,6,0,0"), // (Db E  A  B) : major triad plus 9th
        new ChordDefinition(A, CHORD_AUG, "x,0,3,2,2,1", new String[] {"x,x,4,2,3,1"}),
        new ChordDefinition(A, CHORD_AUG, "x,0,x,2,2,1"), // (Db F  A) : augmented triad
        new ChordDefinition(A, CHORD_DIM, "5,x,x,5,4,5", new String[] {"2,x,x,3,1,4"}),
        new ChordDefinition(A, CHORD_DIM, "x,0,1,2,1,x", new String[] {"x,x,1,3,2,x", "x,x,1,3,2,x"}),
        new ChordDefinition(A, CHORD_DIM7, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad, diminished 7th
        new ChordDefinition(A, CHORD_MAJ, "0,0,2,2,2,0"), // (Db E  A) : major triad
        new ChordDefinition(A, CHORD_MAJ, "0,4,x,2,5,0"), // (Db E  A) : major triad
        new ChordDefinition(A, CHORD_MAJ, "5,7,7,6,5,5"), // (Db E  A) : major triad
        new ChordDefinition(A, CHORD_MAJ, "x,0,2,2,2,0", new String[] {"x,x,1,2,3,x", "x,x,2,1,3,x"}),
        new ChordDefinition(A, CHORD_MAJ, "x,4,7,x,x,5"), // (Db E  A) : major triad
        new ChordDefinition(A, CHORD_MAJ6, "0,0,2,2,2,2"), // (Db E  Gb A) : major triad plus 6th
        new ChordDefinition(A, CHORD_MAJ6, "0,x,4,2,2,0"), // (Db E  Gb A) : major triad plus 6th
        new ChordDefinition(A, CHORD_MAJ6, "2,x,2,2,2,0"), // (Db E  Gb A) : major triad plus 6th
        new ChordDefinition(A, CHORD_MAJ6, "x,0,4,2,2,0"), // (Db E  Gb A) : major triad plus 6th
        new ChordDefinition(A, CHORD_MAJ6, "x,x,2,2,2,2"), // (Db E  Gb A) : major triad plus 6th
        new ChordDefinition(A, CHORD_MAJ6MIN7, "0,0,2,0,2,2"), // (Db E  Gb G  A) : major triad plus 6th, minor 7th
        new ChordDefinition(A, CHORD_MAJ7, "x,0,2,1,2,0"), // (Db E  Ab A) : major triad, major 7th
        new ChordDefinition(A, CHORD_MIN, "x,0,2,2,1,0", new String[] {"x,x,2,3,1,x"}),
        new ChordDefinition(A, CHORD_MIN, "x,3,2,2,1,0"), // (C  E  A) : minor triad
        new ChordDefinition(A, CHORD_MIN, "8,12,x,x,x,0"), // (C  E  A) : minor triad
        new ChordDefinition(A, CHORD_MIN, "x,0,7,5,5,5"), // (C  E  A) : minor triad
        new ChordDefinition(A, CHORD_MIN6, "x,0,2,2,1,2"), // (C  E  Gb A) : minor triad plus 6th
        new ChordDefinition(A, CHORD_MIN6, "x,x,2,2,1,2"), // (C  E  Gb A) : minor triad plus 6th
        new ChordDefinition(A, CHORD_MIN7, "0,0,2,0,1,3"), // (C  E  G  A) : minor triad, minor 7th
        new ChordDefinition(A, CHORD_MIN7, "x,0,2,0,1,0", new String[] {"x,x,2,x,1,x"}),
        new ChordDefinition(A, CHORD_MIN7, "x,0,2,2,1,3", new String[] {"x,x,2,3,1,4", "x,x,2,3,1,4"}),
        new ChordDefinition(A, CHORD_MIN7, "x,0,5,5,5,8"), // (C  E  G  A) : minor triad, minor 7th
        new ChordDefinition(A, CHORD_MIN7FLAT5, "5,x,5,5,4,x", new String[] {"2,x,3,4,1,x"}),
        new ChordDefinition(A, CHORD_MIN7FLAT5, "x,0,1,2,1,3", new String[] {"x,x,1,2,1,4"}),
        new ChordDefinition(A, CHORD_MIN7FLAT5, "x,x,1,2,1,3", new String[] {"x,x,1,3,2,4"}),
        new ChordDefinition(A, CHORD_MIN7SUS4, "5,x,0,0,3,0"), // (D  E  G  A) : sus4 triad, minor 7th
        new ChordDefinition(A, CHORD_MIN7SUS4, "x,0,0,0,x,0"), // (D  E  G  A) : sus4 triad, minor 7th
        new ChordDefinition(A, CHORD_MIN7SUS4, "x,0,2,0,3,0"), // (D  E  G  A) : sus4 triad, minor 7th
        new ChordDefinition(A, CHORD_MIN7SUS4, "x,0,2,0,3,3"), // (D  E  G  A) : sus4 triad, minor 7th
        new ChordDefinition(A, CHORD_MIN7SUS4, "x,0,2,2,3,3"), // (D  E  G  A) : sus4 triad, minor 7th
        new ChordDefinition(A, CHORD_SUS2, "0,0,2,2,0,0"), // (E  A  B) : no 3rd but a 2nd from a major triad
        new ChordDefinition(A, CHORD_SUS2, "0,0,2,4,0,0"), // (E  A  B) : no 3rd but a 2nd from a major triad
        new ChordDefinition(A, CHORD_SUS2, "0,2,2,2,0,0"), // (E  A  B) : no 3rd but a 2nd from a major triad
        new ChordDefinition(A, CHORD_SUS2, "5,x,x,4,5,5", new String[] {"2,x,x,1,3,4"}),
        new ChordDefinition(A, CHORD_SUS2, "x,0,2,2,0,0", new String[] {"x,x,1,2,x,x", "x,x,1,2,x,x"}),
        new ChordDefinition(A, CHORD_SUS2, "x,x,2,2,0,0"), // (E  A  B) : no 3rd but a 2nd from a major triad
        new ChordDefinition(A, CHORD_SUS4, "0,0,2,2,3,0"), // (D  E  A) : no 3rd but a 4th from a major triad
        new ChordDefinition(A, CHORD_SUS4, "5,5,7,7,x,0"), // (D  E  A) : no 3rd but a 4th from a major triad
        new ChordDefinition(A, CHORD_SUS4, "x,0,0,2,3,0"), // (D  E  A) : no 3rd but a 4th from a major triad
        new ChordDefinition(A, CHORD_SUS4, "x,0,2,2,3,0", new String[] {"x,x,1,2,3,x", "x,x,1,2,4,x"}),
        new ChordDefinition(A, D, CHORD_AUG, "x,x,0,2,2,1"), // (Db D  F  A) : augmented triad (altered bass)
        new ChordDefinition(A, D, CHORD_MAJ, "x,0,0,2,2,0"), // (Db D  E  A) : major triad (altered bass)
        new ChordDefinition(A, D, CHORD_MAJ, "x,x,0,2,2,0"), // (Db D  E  A) : major triad (altered bass)
        new ChordDefinition(A, D, CHORD_MAJ, "x,x,0,6,5,5"), // (Db D  E  A) : major triad (altered bass)
        new ChordDefinition(A, D, CHORD_MAJ, "x,x,0,9,10,9"), // (Db D  E  A) : major triad (altered bass)
        new ChordDefinition(A, D, CHORD_MIN, "x,x,0,2,1,0"), // (C  D  E  A) : minor triad (altered bass)
        new ChordDefinition(A, D, CHORD_MIN, "x,x,0,5,5,5"), // (C  D  E  A) : minor triad (altered bass)
        new ChordDefinition(A, D, CHORD_SUS2, "0,2,0,2,0,0"), // (D  E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, D, CHORD_SUS2, "x,2,0,2,3,0"), // (D  E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, Db, CHORD_SUS2, "0,0,2,4,2,0"), // (Db E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, Db, CHORD_SUS2, "x,0,7,6,0,0"), // (Db E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, Db, CHORD_SUS4, "x,0,0,2,2,0"), // (Db D  E  A) : sus4 triad (altered bass)
        new ChordDefinition(A, Db, CHORD_SUS4, "x,x,0,2,2,0"), // (Db D  E  A) : sus4 triad (altered bass)
        new ChordDefinition(A, Db, CHORD_SUS4, "x,x,0,6,5,5"), // (Db D  E  A) : sus4 triad (altered bass)
        new ChordDefinition(A, Db, CHORD_SUS4, "x,x,0,9,10,9"), // (Db D  E  A) : sus4 triad (altered bass)
        new ChordDefinition(A, E, CHORD_DIM, "0,3,x,2,4,0"), // (C  Eb E  A) : diminished triad (altered bass)
        new ChordDefinition(A, Eb, CHORD_MIN, "0,3,x,2,4,0"), // (C  Eb E  A) : minor triad (altered bass)
        new ChordDefinition(A, Eb, CHORD_SUS2, "x,2,1,2,0,0"), // (Eb E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, F, CHORD_DIM, "x,x,1,2,1,1"), // (C  Eb F  A) : diminished triad (altered bass)
        new ChordDefinition(A, F, CHORD_DIM, "x,x,3,5,4,5"), // (C  Eb F  A) : diminished triad (altered bass)
        new ChordDefinition(A, F, CHORD_MIN, "0,0,3,2,1,0"), // (C  E  F  A) : minor triad (altered bass)
        new ChordDefinition(A, F, CHORD_MIN, "1,3,3,2,1,0"), // (C  E  F  A) : minor triad (altered bass)
        new ChordDefinition(A, F, CHORD_MIN, "1,x,2,2,1,0"), // (C  E  F  A) : minor triad (altered bass)
        new ChordDefinition(A, F, CHORD_MIN, "x,x,2,2,1,1"), // (C  E  F  A) : minor triad (altered bass)
        new ChordDefinition(A, F, CHORD_MIN, "x,x,3,2,1,0"), // (C  E  F  A) : minor triad (altered bass)
        new ChordDefinition(A, F, CHORD_SUS2, "0,0,3,2,0,0"), // (E  F  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, F, CHORD_SUS4, "x,x,7,7,6,0"), // (D  E  F  A) : sus4 triad (altered bass)
        new ChordDefinition(A, G, CHORD_AUG, "1,0,3,0,2,1"), // (Db F  G  A) : augmented triad (altered bass)
        new ChordDefinition(A, G, CHORD_DIM, "x,x,1,2,1,3"), // (C  Eb G  A) : diminished triad (altered bass)
        new ChordDefinition(A, G, CHORD_MAJ, "3,x,2,2,2,0"), // (Db E  G  A) : major triad (altered bass)
        new ChordDefinition(A, G, CHORD_MAJ, "x,0,2,0,2,0"), // (Db E  G  A) : major triad (altered bass)
        new ChordDefinition(A, G, CHORD_MAJ, "x,0,2,2,2,3"), // (Db E  G  A) : major triad (altered bass)
        new ChordDefinition(A, G, CHORD_MIN, "0,0,2,0,1,3"), // (C  E  G  A) : minor triad (altered bass)
        new ChordDefinition(A, G, CHORD_MIN, "x,0,2,0,1,0"), // (C  E  G  A) : minor triad (altered bass)
        new ChordDefinition(A, G, CHORD_MIN, "x,0,2,2,1,3"), // (C  E  G  A) : minor triad (altered bass)
        new ChordDefinition(A, G, CHORD_MIN, "x,0,5,5,5,8"), // (C  E  G  A) : minor triad (altered bass)
        new ChordDefinition(A, G, CHORD_SUS2, "3,x,2,2,0,0"), // (E  G  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, G, CHORD_SUS2, "x,0,2,0,0,0"), // (E  G  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, G, CHORD_SUS2, "x,0,5,4,5,0"), // (E  G  A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, G, CHORD_SUS4, "x,0,0,0,x,0"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(A, G, CHORD_SUS4, "x,0,2,0,3,0"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(A, G, CHORD_SUS4, "x,0,2,0,3,3"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(A, G, CHORD_SUS4, "x,0,2,2,3,3"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_DIM, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_MAJ, "0,0,2,2,2,2"), // (Db E  Gb A) : major triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_MAJ, "0,x,4,2,2,0"), // (Db E  Gb A) : major triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_MAJ, "2,x,2,2,2,0"), // (Db E  Gb A) : major triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_MAJ, "x,0,4,2,2,0"), // (Db E  Gb A) : major triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_MAJ, "x,x,2,2,2,2"), // (Db E  Gb A) : major triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_MIN, "x,0,2,2,1,2"), // (C  E  Gb A) : minor triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_MIN, "x,x,2,2,1,2"), // (C  E  Gb A) : minor triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS2, "x,0,4,4,0,0"), // (E  Gb A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS2, "x,2,4,2,5,2"), // (E  Gb A  B) : sus2 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS4, "0,0,0,2,3,2"), // (D  E  Gb A) : sus4 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS4, "0,0,4,2,3,0"), // (D  E  Gb A) : sus4 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS4, "2,x,0,2,3,0"), // (D  E  Gb A) : sus4 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS4, "x,0,2,2,3,2"), // (D  E  Gb A) : sus4 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS4, "x,5,4,2,3,0"), // (D  E  Gb A) : sus4 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS4, "x,9,7,7,x,0"), // (D  E  Gb A) : sus4 triad (altered bass)
        new ChordDefinition(A, Gb, CHORD_SUS4, "x,x,2,2,3,2"), // (D  E  Gb A) : sus4 triad (altered bass)
  
        new ChordDefinition(Ab, A, CHORD_MAJ, "x,x,1,2,1,4"), // (C  Eb Ab A) : major triad (altered bass)
        new ChordDefinition(Ab, CHORD_5, "4,6,6,x,x,4"), // (Eb Ab): root and 5th (power chord)
        new ChordDefinition(Ab, CHORD_7, "x,x,1,1,1,2", new String[] {"x,x,1,1,1,2", "x,x,1,1,1,2"}),
        new ChordDefinition(Ab, CHORD_7, "x,x,4,5,4,4"), // (C  Eb Gb Ab) : major triad, minor 7th
        new ChordDefinition(Ab, CHORD_7FLAT5, "4,x,4,5,3,x", new String[] {"2,x,3,4,1,x", "x,x,3,4,2,x"}),
        new ChordDefinition(Ab, CHORD_7FLAT5, "x,x,0,1,1,2", new String[] {"x,x,x,1,1,2"}),
        new ChordDefinition(Ab, CHORD_7SHARP5, "0,3,2,1,1,2", new String[] {"x,4,2,1,1,3"}),
        new ChordDefinition(Ab, CHORD_7SHARP5, "4,x,4,5,5,x", new String[] {"1,x,2,3,4,x"}),
        new ChordDefinition(Ab, CHORD_AUG, "0,3,2,1,1,0", new String[] {"x,4,3,1,2,x"}),
        new ChordDefinition(Ab, CHORD_AUG, "x,3,2,1,1,0"), // (C  E  Ab) : augmented triad
        new ChordDefinition(Ab, CHORD_DIM, "4,x,x,4,3,4", new String[] {"2,x,x,3,1,4"}),
        new ChordDefinition(Ab, CHORD_DIM, "x,2,0,1,3,4", new String[] {"x,2,x,1,3,4"}),
  //      new ChordDefinition(Ab, CHORD_DIM, "x,x,0,1,0,1", new String[] {"x,x,x,1,0,2"}),
        new ChordDefinition(Ab, CHORD_DIM7, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(Ab, CHORD_DIM7, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(Ab, CHORD_DIM7, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(Ab, CHORD_MAJ, "4,3,1,1,1,x", new String[] {"4,3,1,1,1,x", "x,3,1,1,1,x"}),
        new ChordDefinition(Ab, CHORD_MAJ, "4,6,6,5,4,4"), // (C  Eb Ab) : major triad
        new ChordDefinition(Ab, CHORD_MAJ6, "x,8,10,8,9,8"), // (C  Eb F  Ab) : major triad plus 6th
        new ChordDefinition(Ab, CHORD_MAJ6, "x,x,1,1,1,1"), // (C  Eb F  Ab) : major triad plus 6th
        new ChordDefinition(Ab, CHORD_MIN, "x,2,1,1,4,4", new String[] {"x,2,1,1,4,4"}),
        new ChordDefinition(Ab, CHORD_MIN, "x,x,1,4,4,4", new String[] {"x,x,1,4,4,4"}),
        new ChordDefinition(Ab, CHORD_MIN, "x,x,6,4,4,4"), // (Eb Ab B) : minor triad
        new ChordDefinition(Ab, CHORD_MIN7, "4,x,4,4,4,x", new String[] {"2,x,3,3,3,x"}),
        new ChordDefinition(Ab, CHORD_MIN7, "x,x,4,4,4,4"), // (Eb Gb Ab B) : minor triad, minor 7th
        new ChordDefinition(Ab, CHORD_MIN7FLAT5, "4,x,4,4,3,x", new String[] {"2,x,3,4,1,x"}),
        new ChordDefinition(Ab, CHORD_SUS2, "x,1,1,1,4,4", new String[] {"x,1,1,1,4,4"}),
        new ChordDefinition(Ab, CHORD_SUS2, "x,1,1,1,x,x", new String[] {"x,1,2,3,x,x"}),
        new ChordDefinition(Ab, CHORD_SUS4, "x,x,1,1,2,4", new String[] {"x,x,1,1,2,4"}),
        new ChordDefinition(Ab, CHORD_SUS4, "x,x,6,6,4,4"), // (Db Eb Ab) : no 3rd but a 4th from a major triad
        new ChordDefinition(Ab, D, CHORD_MIN, "x,x,0,4,4,4"), // (D  Eb Ab B) : minor triad (altered bass)
        new ChordDefinition(Ab, E, CHORD_DIM, "0,2,0,1,0,0"), // (D  E  Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, E, CHORD_DIM, "0,2,2,1,3,0"), // (D  E  Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, E, CHORD_DIM, "x,2,0,1,3,0"), // (D  E  Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, E, CHORD_DIM, "x,x,0,1,0,0"), // (D  E  Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, E, CHORD_MIN, "0,2,1,1,0,0"), // (Eb E  Ab B) : minor triad (altered bass)
        new ChordDefinition(Ab, E, CHORD_MIN, "0,x,6,4,4,0"), // (Eb E  Ab B) : minor triad (altered bass)
        new ChordDefinition(Ab, E, CHORD_MIN, "x,x,1,1,0,0"), // (Eb E  Ab B) : minor triad (altered bass)
        new ChordDefinition(Ab, Eb, CHORD_DIM, "x,x,0,4,4,4"), // (D  Eb Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, F, CHORD_DIM, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, F, CHORD_DIM, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, F, CHORD_DIM, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(Ab, F, CHORD_MAJ, "x,8,10,8,9,8"), // (C  Eb F  Ab) : major triad (altered bass)
        new ChordDefinition(Ab, F, CHORD_MAJ, "x,x,1,1,1,1"), // (C  Eb F  Ab) : major triad (altered bass)
        new ChordDefinition(Ab, Gb, CHORD_MAJ, "x,x,1,1,1,2"), // (C  Eb Gb Ab) : major triad (altered bass)
        new ChordDefinition(Ab, Gb, CHORD_MAJ, "x,x,4,5,4,4"), // (C  Eb Gb Ab) : major triad (altered bass)
        new ChordDefinition(Ab, Gb, CHORD_MIN, "x,x,4,4,4,4"), // (Eb Gb Ab B) : minor triad (altered bass)
  
        new ChordDefinition(B, A, CHORD_DIM, "1,2,3,2,3,1"), // (D  F  A  B) : diminished triad (altered bass)
        new ChordDefinition(B, A, CHORD_DIM, "x,2,0,2,0,1"), // (D  F  A  B) : diminished triad (altered bass)
        new ChordDefinition(B, A, CHORD_DIM, "x,x,0,2,0,1"), // (D  F  A  B) : diminished triad (altered bass)
        new ChordDefinition(B, A, CHORD_MAJ, "2,x,1,2,0,2"), // (Eb Gb A  B) : major triad (altered bass)
        new ChordDefinition(B, A, CHORD_MAJ, "x,0,1,2,0,2"), // (Eb Gb A  B) : major triad (altered bass)
        new ChordDefinition(B, A, CHORD_MAJ, "x,2,1,2,0,2"), // (Eb Gb A  B) : major triad (altered bass)
        new ChordDefinition(B, A, CHORD_MAJ, "x,2,4,2,4,2"), // (Eb Gb A  B) : major triad (altered bass)
        new ChordDefinition(B, A, CHORD_MIN, "x,0,4,4,3,2"), // (D  Gb A  B) : minor triad (altered bass)
        new ChordDefinition(B, A, CHORD_MIN, "x,2,0,2,0,2"), // (D  Gb A  B) : minor triad (altered bass)
        new ChordDefinition(B, A, CHORD_MIN, "x,2,0,2,3,2"), // (D  Gb A  B) : minor triad (altered bass)
        new ChordDefinition(B, A, CHORD_MIN, "x,2,4,2,3,2"), // (D  Gb A  B) : minor triad (altered bass)
        new ChordDefinition(B, A, CHORD_MIN, "x,x,0,2,0,2"), // (D  Gb A  B) : minor triad (altered bass)
        new ChordDefinition(B, A, CHORD_SUS4, "x,0,4,4,0,0"), // (E  Gb A  B) : sus4 triad (altered bass)
        new ChordDefinition(B, A, CHORD_SUS4, "x,2,4,2,5,2"), // (E  Gb A  B) : sus4 triad (altered bass)
        new ChordDefinition(B, Ab, CHORD_DIM, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(B, Ab, CHORD_DIM, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(B, Ab, CHORD_DIM, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(B, Ab, CHORD_MAJ, "x,x,4,4,4,4"), // (Eb Gb Ab B) : major triad (altered bass)
        new ChordDefinition(B, Ab, CHORD_SUS4, "0,2,2,1,0,2"), // (E  Gb Ab B) : sus4 triad (altered bass)
        new ChordDefinition(B, Ab, CHORD_SUS4, "0,x,4,1,0,0"), // (E  Gb Ab B) : sus4 triad (altered bass)
        new ChordDefinition(B, Ab, CHORD_SUS4, "2,2,2,1,0,0"), // (E  Gb Ab B) : sus4 triad (altered bass)
        new ChordDefinition(B, CHORD_5, "7,9,9,x,x,2"), // (Gb B): root and 5th (power chord)
        new ChordDefinition(B, CHORD_5, "x,2,4,4,x,2"), // (Gb B): root and 5th (power chord)
        new ChordDefinition(B, CHORD_7, "2,x,1,2,0,2"), // (Eb Gb A  B) : major triad, minor 7th
        new ChordDefinition(B, CHORD_7, "x,0,1,2,0,2"), // (Eb Gb A  B) : major triad, minor 7th
        new ChordDefinition(B, CHORD_7, "x,2,1,2,0,2", new String[] {"x,2,1,3,x,4", "x,2,1,3,x,4"}),
        new ChordDefinition(B, CHORD_7, "x,2,4,2,4,2", new String[] {"x,1,3,1,4,1"}),
        new ChordDefinition(B, CHORD_7SHARP5, "x,2,1,2,0,3", new String[] {"x,2,1,3,x,4"}),
        new ChordDefinition(B, CHORD_7SHARP5, "x,2,5,2,4,x", new String[] {"x,1,4,1,3,x"}),
        new ChordDefinition(B, CHORD_AUG, "3,2,1,0,0,3"), // (Eb G  B) : augmented triad
        new ChordDefinition(B, CHORD_AUG, "3,x,1,0,0,3"), // (Eb G  B) : augmented triad
        new ChordDefinition(B, CHORD_AUG, "x,2,1,0,0,3", new String[] {"x,2,1,x,x,4"}),
        new ChordDefinition(B, CHORD_AUG, "x,2,5,4,4,3", new String[] {"x,1,4,2,3,1"}),
        new ChordDefinition(B, CHORD_DIM, "x,2,0,4,3,1", new String[] {"x,2,x,4,3,1"}),
        new ChordDefinition(B, CHORD_DIM, "x,2,3,4,3,x", new String[] {"x,1,2,4,3,x"}),
        new ChordDefinition(B, CHORD_DIM7, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(B, CHORD_DIM7, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(B, CHORD_DIM7, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(B, CHORD_MAJ, "x,2,4,4,4,2", new String[] {"x,1,3,3,3,1", "x,1,3,3,3,x"}),
        new ChordDefinition(B, CHORD_MAJ6, "x,x,4,4,4,4"), // (Eb Gb Ab B) : major triad plus 6th
        new ChordDefinition(B, CHORD_MIN, "2,2,4,4,3,2"), // (D  Gb B) : minor triad
        new ChordDefinition(B, CHORD_MIN, "x,2,4,4,3,2", new String[] {"x,1,3,4,2,1"}),
        new ChordDefinition(B, CHORD_MIN, "x,2,4,4,3,x", new String[] {"x,1,3,4,2,x"}),
        new ChordDefinition(B, CHORD_MIN, "x,5,4,4,3,x", new String[] {"x,4,2,3,1,x"}),
        new ChordDefinition(B, CHORD_MIN, "x,x,0,4,3,2"), // (D  Gb B) : minor triad
        new ChordDefinition(B, CHORD_MIN7, "x,0,4,4,3,2"), // (D  Gb A  B) : minor triad, minor 7th
        new ChordDefinition(B, CHORD_MIN7, "x,2,0,2,0,2", new String[] {"x,1,x,2,x,3", "x,2,x,3,x,4"}),
        new ChordDefinition(B, CHORD_MIN7, "x,2,0,2,3,2"), // (D  Gb A  B) : minor triad, minor 7th
        new ChordDefinition(B, CHORD_MIN7, "x,2,4,2,3,2", new String[] {"x,1,3,1,2,1"}),
        new ChordDefinition(B, CHORD_MIN7, "x,x,0,2,0,2"), // (D  Gb A  B) : minor triad, minor 7th
        new ChordDefinition(B, CHORD_MIN7FLAT5, "1,2,3,2,3,1"), // (D  F  A  B) : diminished triad, minor 7th : half-diminished 7th
        new ChordDefinition(B, CHORD_MIN7FLAT5, "x,x,0,2,0,1"), // (D  F  A  B) : diminished triad, minor 7th : half-diminished 7th
        new ChordDefinition(B, CHORD_MIN7FLAT5, "x,2,0,2,0,1", new String[] {"x,2,x,3,x,1"}),
        new ChordDefinition(B, CHORD_MIN7FLAT5, "x,2,3,2,3,x", new String[] {"x,1,3,2,4,x"}),
        new ChordDefinition(B, CHORD_MIN7SUS4, "x,0,4,4,0,0"), // (E  Gb A  B) : sus4 triad, minor 7th
        new ChordDefinition(B, CHORD_MIN7SUS4, "x,2,4,2,5,2"), // (E  Gb A  B) : sus4 triad, minor 7th
        new ChordDefinition(B, CHORD_SUS2, "x,2,4,4,2,2", new String[] {"x,1,3,4,1,1"}),
        new ChordDefinition(B, CHORD_SUS2, "x,4,4,4,x,2"), // (Db Gb B): no 3rd but a 2nd from a major triad
        new ChordDefinition(B, CHORD_SUS2, "x,x,4,4,2,2"), // (Db Gb B) : no 3rd but a 2nd from a major triad
        new ChordDefinition(B, CHORD_SUS4, "7,9,9,x,x,0"), // (E  Gb B) : no 3rd but a 4th from a major triad
        new ChordDefinition(B, CHORD_SUS4, "x,2,4,4,0,0", new String[] {"x,1,3,4,x,x"}),
        new ChordDefinition(B, CHORD_SUS4, "x,2,4,4,5,2", new String[] {"x,1,2,3,4,1"}),
        new ChordDefinition(B, CHORD_SUS4, "x,2,4,4,x,0"), // (E  Gb B) : no 3rd but a 4th from a major triad
        new ChordDefinition(B, Db, CHORD_SUS4, "x,4,4,4,x,0"), // (Db E  Gb B) : sus4 triad (altered bass)
        new ChordDefinition(B, E, CHORD_AUG, "3,x,1,0,0,0"), // (Eb E  G  B) : augmented triad (altered bass)
        new ChordDefinition(B, E, CHORD_AUG, "x,x,1,0,0,0"), // (Eb E  G  B) : augmented triad (altered bass)
        new ChordDefinition(B, E, CHORD_MAJ, "x,2,2,4,4,2"), // (Eb E  Gb B) : major triad (altered bass)
        new ChordDefinition(B, E, CHORD_MAJ, "x,x,4,4,4,0"), // (Eb E  Gb B) : major triad (altered bass)
        new ChordDefinition(B, E, CHORD_SUS2, "x,4,4,4,x,0"), // (Db E  Gb B) : sus2 triad (altered bass)
        new ChordDefinition(B, Eb, CHORD_SUS4, "x,2,2,4,4,2"), // (Eb E  Gb B) : sus4 triad (altered bass)
        new ChordDefinition(B, Eb, CHORD_SUS4, "x,x,4,4,4,0"), // (Eb E  Gb B) : sus4 triad (altered bass)
        new ChordDefinition(B, G, CHORD_DIM, "1,x,0,0,0,3"), // (D  F  G  B) : diminished triad (altered bass)
        new ChordDefinition(B, G, CHORD_DIM, "3,2,0,0,0,1"), // (D  F  G  B) : diminished triad (altered bass)
        new ChordDefinition(B, G, CHORD_DIM, "x,x,0,0,0,1"), // (D  F  G  B) : diminished triad (altered bass)
        new ChordDefinition(B, G, CHORD_MIN, "2,2,0,0,0,3"), // (D  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(B, G, CHORD_MIN, "2,2,0,0,3,3"), // (D  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(B, G, CHORD_MIN, "3,2,0,0,0,2"), // (D  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(B, G, CHORD_MIN, "x,x,4,4,3,3"), // (D  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(B, G, CHORD_SUS4, "0,2,2,0,0,2"), // (E  Gb G  B) : sus4 triad (altered bass)
        new ChordDefinition(B, G, CHORD_SUS4, "0,2,4,0,0,0"), // (E  Gb G  B) : sus4 triad (altered bass)
        new ChordDefinition(B, G, CHORD_SUS4, "0,x,4,0,0,0"), // (E  Gb G  B) : sus4 triad (altered bass)
        new ChordDefinition(B, G, CHORD_SUS4, "2,2,2,0,0,0"), // (E  Gb G  B) : sus4 triad (altered bass)
  
        new ChordDefinition(Bb, A, CHORD_MAJ, "1,1,3,2,3,1"), // (D  F  A  Bb) : major triad (altered bass)
        new ChordDefinition(Bb, Ab, CHORD_MAJ, "x,1,3,1,3,1"), // (D  F  Ab Bb) : major triad (altered bass)
        new ChordDefinition(Bb, Ab, CHORD_MAJ, "x,x,3,3,3,4"), // (D  F  Ab Bb) : major triad (altered bass)
        new ChordDefinition(Bb, Ab, CHORD_MIN, "x,1,3,1,2,1"), // (Db F  Ab Bb) : minor triad (altered bass)
        new ChordDefinition(Bb, C, CHORD_DIM, "x,3,x,3,2,0"), // (C  Db E  Bb) : diminished triad (altered bass)
        new ChordDefinition(Bb, CHORD_5, "6,8,8,x,x,6"), // (F  Bb): root and 5th (power chord)
        new ChordDefinition(Bb, CHORD_5, "x,1,3,3,x,6"), // (F  Bb): root and 5th (power chord)
        new ChordDefinition(Bb, CHORD_7, "x,1,3,1,3,1", new String[] {"x,1,3,1,4,1"}),
        new ChordDefinition(Bb, CHORD_7, "x,x,3,3,3,4", new String[] {"x,x,1,1,1,2"}),
        new ChordDefinition(Bb, CHORD_7FLAT5, "x,1,0,1,3,0", new String[] {"x,1,x,1,4,x"}),
        new ChordDefinition(Bb, CHORD_7FLAT5, "x,1,2,1,3,x", new String[] {"x,1,2,1,4,x"}),
        new ChordDefinition(Bb, CHORD_7SHARP5, "x,1,4,1,3,x", new String[] {"x,1,4,1,3,x"}),
        new ChordDefinition(Bb, CHORD_ADD11,"x,1,3,3,3,0"), // (D  E  F  Bb) : major triad, augmented 11th
        new ChordDefinition(Bb, CHORD_AUG, "x,1,0,3,3,2", new String[] {"x,1,x,3,3,2"}),
        new ChordDefinition(Bb, CHORD_AUG, "x,1,4,3,3,2", new String[] {"x,1,4,2,3,1"}),
        new ChordDefinition(Bb, CHORD_AUG, "x,x,0,3,3,2"), // (D  Gb Bb) : augmented triad
        new ChordDefinition(Bb, CHORD_DIM, "x,1,2,3,2,0", new String[] {"x,1,2,4,3,x"}),
        new ChordDefinition(Bb, CHORD_DIM, "x,1,2,3,2,x", new String[] {"x,1,2,4,3,x"}),
        new ChordDefinition(Bb, CHORD_DIM, "x,4,2,3,2,x", new String[] {"x,3,1,2,1,x"}),
        new ChordDefinition(Bb, CHORD_DIM7, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(Bb, CHORD_DIM7, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(Bb, CHORD_MAJ, "1,1,3,3,3,1"), // (D  F  Bb) : major triad
        new ChordDefinition(Bb, CHORD_MAJ, "x,1,3,3,3,1", new String[] {"x,1,3,3,3,1"}),
        new ChordDefinition(Bb, CHORD_MAJ, "x,1,3,3,3,x", new String[] {"x,1,3,3,3,x"}),
        new ChordDefinition(Bb, CHORD_MAJ, "x,x,0,3,3,1", new String[] {"x,x,x,3,4,1"}),
        new ChordDefinition(Bb, CHORD_MAJ6, "3,5,3,3,3,3"), // (D  F  G  Bb) : major triad plus 6th
        new ChordDefinition(Bb, CHORD_MAJ6, "x,x,3,3,3,3"), // (D  F  G  Bb) : major triad plus 6th
        new ChordDefinition(Bb, CHORD_MAJ7, "1,1,3,2,3,1"), // (D  F  A  Bb) : major triad, major 7th
        new ChordDefinition(Bb, CHORD_MAJ9, "x,3,3,3,3,5"), // (C  D  F  A  Bb) : major triad, major 7th plus 9th
        new ChordDefinition(Bb, CHORD_MIN, "1,1,3,3,2,1"), // (Db F  Bb) : minor triad
        new ChordDefinition(Bb, CHORD_MIN, "x,1,3,3,2,1", new String[] {"x,1,3,4,2,1"}),
        new ChordDefinition(Bb, CHORD_MIN7, "x,1,3,1,2,1", new String[] {"x,1,3,1,2,1"}),
        new ChordDefinition(Bb, CHORD_MIN7FLAT5, "x,1,2,1,2,0", new String[] {"x,1,3,2,4,x"}),
        new ChordDefinition(Bb, CHORD_MIN7SUS4, "x,1,3,1,4,1"), // (Eb F  Ab Bb) : sus4 triad, minor 7th
        new ChordDefinition(Bb, CHORD_SUS2, "x,1,3,3,1,1", new String[] {"x,1,3,4,1,1"}),
        new ChordDefinition(Bb, CHORD_SUS2, "x,x,3,3,1,1"), // (C  F  Bb) : no 3rd but a 2nd from a major triad
        new ChordDefinition(Bb, CHORD_SUS4, "x,1,3,3,4,1", new String[] {"x,1,2,3,4,1"}),
        new ChordDefinition(Bb, D, CHORD_DIM, "x,x,0,3,2,0"), // (Db D  E  Bb) : diminished triad (altered bass)
        new ChordDefinition(Bb, D, CHORD_MIN, "x,x,0,6,6,6"), // (Db D  F  Bb) : minor triad (altered bass)
        new ChordDefinition(Bb, Db, CHORD_MAJ, "x,x,0,6,6,6"), // (Db D  F  Bb) : major triad (altered bass)
        new ChordDefinition(Bb, E, CHORD_AUG, "2,x,4,3,3,0"), // (D  E  Gb Bb) : augmented triad (altered bass)
        new ChordDefinition(Bb, E, CHORD_MAJ, "x,1,3,3,3,0"), // (D  E  F  Bb) : major triad (altered bass)
        new ChordDefinition(Bb, G, CHORD_DIM, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(Bb, G, CHORD_DIM, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(Bb, G, CHORD_MAJ, "3,5,3,3,3,3"), // (D  F  G  Bb) : major triad (altered bass)
        new ChordDefinition(Bb, G, CHORD_MAJ, "x,x,3,3,3,3"), // (D  F  G  Bb) : major triad (altered bass)
        new ChordDefinition(Bb, Gb, CHORD_DIM, "2,4,2,3,2,2"), // (Db E  Gb Bb) : diminished triad (altered bass)
        new ChordDefinition(Bb, Gb, CHORD_DIM, "x,x,4,3,2,0"), // (Db E  Gb Bb) : diminished triad (altered bass)
        new ChordDefinition(Bb, Gb, CHORD_MIN, "x,x,3,3,2,2"), // (Db F  Gb Bb) : minor triad (altered bass)
  
        new ChordDefinition(C, A, CHORD_DIM, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad (altered bass)
        new ChordDefinition(C, A, CHORD_MAJ, "0,0,2,0,1,3"), // (C  E  G  A) : major triad (altered bass)
        new ChordDefinition(C, A, CHORD_MAJ, "x,0,2,0,1,0"), // (C  E  G  A) : major triad (altered bass)
        new ChordDefinition(C, A, CHORD_MAJ, "x,0,2,2,1,3"), // (C  E  G  A) : major triad (altered bass)
        new ChordDefinition(C, A, CHORD_MAJ, "x,0,5,5,5,8"), // (C  E  G  A) : major triad (altered bass)
        new ChordDefinition(C, A, CHORD_MIN, "x,x,1,2,1,3"), // (C  Eb G  A) : minor triad (altered bass)
        new ChordDefinition(C, A, CHORD_SUS2, "x,5,7,5,8,3"), // (C  D  G  A): sus2 triad (altered bass)
        new ChordDefinition(C, A, CHORD_SUS2, "x,x,0,2,1,3"), // (C  D  G  A) : sus2 triad (altered bass)
        new ChordDefinition(C, A, CHORD_SUS4, "3,x,3,2,1,1"), // (C  F  G  A) : sus4 triad (altered bass)
        new ChordDefinition(C, A, CHORD_SUS4, "x,x,3,2,1,3"), // (C  F  G  A) : sus4 triad (altered bass)
        new ChordDefinition(C, Ab, CHORD_DIM, "x,x,1,1,1,2"), // (C  Eb Gb Ab) : diminished triad (altered bass)
        new ChordDefinition(C, Ab, CHORD_DIM, "x,x,4,5,4,4"), // (C  Eb Gb Ab) : diminished triad (altered bass)
        new ChordDefinition(C, B, CHORD_MAJ, "0,3,2,0,0,0"), // (C  E  G  B) : major triad (altered bass)
        new ChordDefinition(C, B, CHORD_MAJ, "x,2,2,0,1,0"), // (C  E  G  B) : major triad (altered bass)
        new ChordDefinition(C, B, CHORD_MAJ, "x,3,5,4,5,3"), // (C  E  G  B) : major triad (altered bass)
        new ChordDefinition(C, B, CHORD_SUS2, "3,3,0,0,0,3"), // (C  D  G  B) : sus2 triad (altered bass)
        new ChordDefinition(C, B, CHORD_SUS2, "x,3,0,0,0,3"), // (C  D  G  B) : sus2 triad (altered bass)
        new ChordDefinition(C, B, CHORD_SUS4, "x,3,3,0,0,3"), // (C  F  G  B) : sus4 triad (altered bass)
        new ChordDefinition(C, Bb, CHORD_MAJ, "x,3,5,3,5,3"), // (C  E  G  Bb) : major triad (altered bass)
        new ChordDefinition(C, Bb, CHORD_MIN, "x,3,5,3,4,3"), // (C  Eb G  Bb) : minor triad (altered bass)
        new ChordDefinition(C, Bb, CHORD_SUS4, "x,3,5,3,6,3"), // (C  F  G  Bb) : sus4 triad (altered bass)
        new ChordDefinition(C, CHORD_5, "x,3,5,5,x,3"), // (C  G): root and 5th (power chord)
        new ChordDefinition(C, CHORD_7, "x,3,2,3,1,0", new String[] {"x,3,2,4,1,x"}),
        new ChordDefinition(C, CHORD_7, "x,3,5,3,5,3", new String[] {"x,1,3,1,4,1"}),
        new ChordDefinition(C, CHORD_7FLAT5, "2,x,2,3,1,x", new String[] {"2,x,3,4,1,x"}),
        new ChordDefinition(C, CHORD_7FLAT5, "x,3,4,3,5,x", new String[] {"x,1,2,1,4,x", "x,1,3,2,4,x"}),
        new ChordDefinition(C, CHORD_7SHARP5, "x,x,2,3,1,4", new String[] {"x,x,2,3,1,4"}),
        new ChordDefinition(C, CHORD_ADD9, "3,x,0,0,1,0"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_ADD9, "x,10,12,12,13,0"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_ADD9, "x,3,0,0,1,0"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_ADD9, "x,3,2,0,3,0"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_ADD9, "x,3,2,0,3,3"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_ADD9, "x,5,5,5,x,0"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_ADD9, "x,x,0,0,1,0"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_ADD9, "x,x,0,5,5,3"), // (C  D  E  G) : major triad plus 9th
        new ChordDefinition(C, CHORD_AUG, "x,3,2,1,1,0"), // (C  E  Ab) : augmented triad
        new ChordDefinition(C, CHORD_AUG, "x,3,2,1,1,x", new String[] {"x,3,2,1,1,x"}),
        new ChordDefinition(C, CHORD_AUG, "x,3,x,5,5,4", new String[] {"x,1,x,3,4,2"}),
        new ChordDefinition(C, CHORD_AUG, "x,x,2,1,1,4", new String[] {"x,x,2,1,1,4"}),
        new ChordDefinition(C, CHORD_DIM, "x,x,4,5,4,2", new String[] {"x,x,2,4,3,1"}),
        new ChordDefinition(C, CHORD_DIM7, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad, diminished 7th
        new ChordDefinition(C, CHORD_MAJ, "0,3,2,0,1,0", new String[] {"x,3,2,x,1,x"}),
        new ChordDefinition(C, CHORD_MAJ, "0,3,5,5,5,3"), // (C  E  G) : major triad
        new ChordDefinition(C, CHORD_MAJ, "0,3,5,5,5,x", new String[] {"x,1,3,3,3,x"}),
        new ChordDefinition(C, CHORD_MAJ, "3,3,2,0,1,0"), // (C  E  G) : major triad
        new ChordDefinition(C, CHORD_MAJ, "3,x,2,0,1,0"), // (C  E  G) : major triad
        new ChordDefinition(C, CHORD_MAJ, "x,3,2,0,1,0"), // (C  E  G) : major triad
        new ChordDefinition(C, CHORD_MAJ, "x,3,5,5,5,0"), // (C  E  G) : major triad
        new ChordDefinition(C, CHORD_MAJ6, "0,0,2,0,1,3"), // (C  E  G  A) : major triad plus 6th
        new ChordDefinition(C, CHORD_MAJ6, "x,0,2,0,1,0"), // (C  E  G  A) : major triad plus 6th
        new ChordDefinition(C, CHORD_MAJ6, "x,0,2,2,1,3"), // (C  E  G  A) : major triad plus 6th
        new ChordDefinition(C, CHORD_MAJ6, "x,0,5,5,5,8"), // (C  E  G  A) : major triad plus 6th
        new ChordDefinition(C, CHORD_MAJ7, "0,3,2,0,0,0"), // (C  E  G  B) : major triad, major 7th
        new ChordDefinition(C, CHORD_MAJ7, "x,2,2,0,1,0"), // (C  E  G  B) : major triad, major 7th
        new ChordDefinition(C, CHORD_MAJ7, "x,3,5,4,5,3"), // (C  E  G  B) : major triad, major 7th
        new ChordDefinition(C, CHORD_MAJ9, "x,3,0,0,0,0"), // (C  D  E  G  B) : major triad, major 7th plus 9th
        new ChordDefinition(C, CHORD_MIN, "x,3,1,0,1,x", new String[] {"x,4,2,x,1,x"}),
        new ChordDefinition(C, CHORD_MIN, "x,3,5,5,4,3"), // (C  Eb G) : minor triad
        new ChordDefinition(C, CHORD_MIN, "x,x,5,5,4,3"), // (C  Eb G) : minor triad
        new ChordDefinition(C, CHORD_MIN6, "x,x,1,2,1,3"), // (C  Eb G  A) : minor triad plus 6th
        new ChordDefinition(C, CHORD_MIN7, "3,x,1,3,1,x", new String[] {"3,x,1,4,1,x"}),
        new ChordDefinition(C, CHORD_MIN7, "x,3,5,3,4,3", new String[] {"x,1,3,1,2,1"}),
        new ChordDefinition(C, CHORD_MIN7, "x,x,1,3,1,3", new String[] {"x,x,1,3,1,4"}),               
        new ChordDefinition(C, CHORD_MIN7FLAT5, "2,x,1,3,1,x", new String[] {"2,x,1,4,1,x"}),
        new ChordDefinition(C, CHORD_MIN7FLAT5, "x,3,4,3,4,x", new String[] {"x,1,2,1,3,x", "x,1,2,2,4,x"}),
        new ChordDefinition(C, CHORD_MIN7SUS4, "x,3,5,3,6,3"), // (C  F  G  Bb) : sus4 triad, minor 7th
        new ChordDefinition(C, CHORD_SUS2, "x,10,12,12,13,3"), // (C  D  G): no 3rd but a 2nd from a major triad
        new ChordDefinition(C, CHORD_SUS2, "x,3,0,0,1,3", new String[] {"x,3,x,x,1,4"}),
        new ChordDefinition(C, CHORD_SUS2, "x,3,0,0,3,3"), // (C  D  G) : no 3rd but a 2nd from a major triad
        new ChordDefinition(C, CHORD_SUS2, "x,3,5,5,3,3", new String[] {"x,1,3,4,1,1"}),
        new ChordDefinition(C, CHORD_SUS2, "x,5,5,5,x,3"), // (C  D  G): no 3rd but a 2nd from a major triad
        new ChordDefinition(C, CHORD_SUS4, "x,3,3,0,1,1"), // (C  F  G) : no 3rd but a 4th from a major triad
        new ChordDefinition(C, CHORD_SUS4, "x,x,3,0,1,1"), // (C  F  G) : no 3rd but a 4th from a major triad
        new ChordDefinition(C, D, CHORD_DIM, "x,5,4,5,4,2"), // (C  D  Eb Gb): diminished triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "3,x,0,0,1,0"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "x,10,12,12,13,0"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "x,3,0,0,1,0"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "x,3,2,0,3,0"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "x,3,2,0,3,3"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "x,5,5,5,x,0"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "x,x,0,0,1,0"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_MAJ, "x,x,0,5,5,3"), // (C  D  E  G) : major triad (altered bass)
        new ChordDefinition(C, D, CHORD_SUS4, "3,3,0,0,1,1"), // (C  D  F  G) : sus4 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "3,x,0,0,1,0"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "x,10,12,12,13,0"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "x,3,0,0,1,0"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "x,3,2,0,3,0"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "x,3,2,0,3,3"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "x,5,5,5,x,0"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "x,x,0,0,1,0"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS2, "x,x,0,5,5,3"), // (C  D  E  G) : sus2 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS4, "x,3,3,0,1,0"), // (C  E  F  G) : sus4 triad (altered bass)
        new ChordDefinition(C, E, CHORD_SUS4, "x,x,3,0,1,0"), // (C  E  F  G) : sus4 triad (altered bass)
        new ChordDefinition(C, F, CHORD_MAJ, "x,3,3,0,1,0"), // (C  E  F  G) : major triad (altered bass)
        new ChordDefinition(C, F, CHORD_MAJ, "x,x,3,0,1,0"), // (C  E  F  G) : major triad (altered bass)
        new ChordDefinition(C, F, CHORD_SUS2, "3,3,0,0,1,1"), // (C  D  F  G) : sus2 triad (altered bass)
  
        new ChordDefinition(D, Ab, CHORD_SUS2, "4,x,0,2,3,0"), // (D  E  Ab A) : sus2 triad (altered bass)
        new ChordDefinition(D, B, CHORD_DIM, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(D, B, CHORD_DIM, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(D, B, CHORD_DIM, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(D, B, CHORD_MAJ, "x,0,4,4,3,2"), // (D  Gb A  B) : major triad (altered bass)
        new ChordDefinition(D, B, CHORD_MAJ, "x,2,0,2,0,2"), // (D  Gb A  B) : major triad (altered bass)
        new ChordDefinition(D, B, CHORD_MAJ, "x,2,0,2,3,2"), // (D  Gb A  B) : major triad (altered bass)
        new ChordDefinition(D, B, CHORD_MAJ, "x,2,4,2,3,2"), // (D  Gb A  B) : major triad (altered bass)
        new ChordDefinition(D, B, CHORD_MAJ, "x,x,0,2,0,2"), // (D  Gb A  B) : major triad (altered bass)
        new ChordDefinition(D, B, CHORD_MIN, "1,1,3,2,3,1"), // (D  F  A  Bb) : minor triad (altered bass)
        new ChordDefinition(D, B, CHORD_MIN, "1,2,3,2,3,1"), // (D  F  A  B) : minor triad (altered bass)
        new ChordDefinition(D, B, CHORD_MIN, "x,2,0,2,0,1"), // (D  F  A  B) : minor triad (altered bass)
        new ChordDefinition(D, B, CHORD_MIN, "x,x,0,2,0,1"), // (D  F  A  B) : minor triad (altered bass)
        new ChordDefinition(D, B, CHORD_SUS2, "0,2,0,2,0,0"), // (D  E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(D, B, CHORD_SUS2, "x,2,0,2,3,0"), // (D  E  A  B) : sus2 triad (altered bass)
        new ChordDefinition(D, B, CHORD_SUS4, "3,0,0,0,0,3"), // (D  G  A  B) : sus4 triad (altered bass)
        new ChordDefinition(D, B, CHORD_SUS4, "3,2,0,2,0,3"), // (D  G  A  B) : sus4 triad (altered bass)
        new ChordDefinition(D, Bb, CHORD_DIM, "x,1,3,1,3,1"), // (D  F  Ab Bb) : diminished triad (altered bass)
        new ChordDefinition(D, Bb, CHORD_DIM, "x,x,3,3,3,4"), // (D  F  Ab Bb) : diminished triad (altered bass)
        new ChordDefinition(D, Bb, CHORD_SUS2, "0,1,x,2,3,0"), // (D  E  A  Bb) : sus2 triad (altered bass)
        new ChordDefinition(D, C, CHORD_DIM, "x,x,0,1,1,1"), // (C  D  F  Ab) : diminished triad (altered bass)
        new ChordDefinition(D, C, CHORD_MAJ, "x,0,0,2,1,2"), // (C  D  Gb A) : major triad (altered bass)
        new ChordDefinition(D, C, CHORD_MAJ, "x,3,x,2,3,2"), // (C  D  Gb A) : major triad (altered bass)
        new ChordDefinition(D, C, CHORD_MAJ, "x,5,7,5,7,2"), // (C  D  Gb A): major triad (altered bass)
        new ChordDefinition(D, C, CHORD_MAJ, "x,5,7,5,7,5"), // (C  D  Gb A) : major triad (altered bass)
        new ChordDefinition(D, C, CHORD_MIN, "x,5,7,5,6,5"), // (C  D  F  A) : minor triad (altered bass)
        new ChordDefinition(D, C, CHORD_MIN, "x,x,0,2,1,1"), // (C  D  F  A) : minor triad (altered bass)
        new ChordDefinition(D, C, CHORD_MIN, "x,x,0,5,6,5"), // (C  D  F  A) : minor triad (altered bass)
        new ChordDefinition(D, C, CHORD_SUS2, "x,x,0,2,1,0"), // (C  D  E  A) : sus2 triad (altered bass)
        new ChordDefinition(D, C, CHORD_SUS2, "x,x,0,5,5,5"), // (C  D  E  A) : sus2 triad (altered bass)
        new ChordDefinition(D, C, CHORD_SUS4, "x,5,7,5,8,3"), // (C  D  G  A): sus4 triad (altered bass)
        new ChordDefinition(D, C, CHORD_SUS4, "x,x,0,2,1,3"), // (C  D  G  A) : sus4 triad (altered bass)
        new ChordDefinition(D, CHORD_5, "5,5,7,7,x,5"), // (D  A): root and 5th (power chord)
        new ChordDefinition(D, CHORD_5, "x,0,0,2,3,5"), // (D  A): root and 5th (power chord)
        new ChordDefinition(D, CHORD_5, "x,5,7,7,x,x", new String[] {"x,x,1,2,3,x"}),
        new ChordDefinition(D, CHORD_5, "x,x,0,2,x,x", new String[] {"x,x,x,2,x,x"}),
        new ChordDefinition(D, CHORD_7, "x,0,0,2,1,2", new String[] {"x,x,x,2,1,3"}),
        new ChordDefinition(D, CHORD_7, "x,3,x,2,3,2"), // (C  D  Gb A) : major triad, minor 7th
        new ChordDefinition(D, CHORD_7, "x,5,7,5,7,2"), // (C  D  Gb A): major triad, minor 7th
        new ChordDefinition(D, CHORD_7, "x,5,7,5,7,5"), // (C  D  Gb A) : major triad, minor 7th
        new ChordDefinition(D, CHORD_7, "x,x,0,2,1,2", new String[] {"x,x,x,2,1,3"}),
        new ChordDefinition(D, CHORD_7, "x,x,4,5,3,5", new String[] {"x,x,2,3,1,4"}),
        new ChordDefinition(D, CHORD_7FLAT5, "x,x,0,1,1,2", new String[] {"x,x,x,1,1,3"}),
        new ChordDefinition(D, CHORD_7FLAT5, "x,x,4,5,3,4", new String[] {"x,x,2,4,1,3"}),
        new ChordDefinition(D, CHORD_7SHARP5, "x,x,0,3,1,2", new String[] {"x,x,x,3,1,2"}),
        new ChordDefinition(D, CHORD_ADD9, "0,0,0,2,3,2"), // (D  E  Gb A) : major triad plus 9th
        new ChordDefinition(D, CHORD_ADD9, "0,0,4,2,3,0"), // (D  E  Gb A) : major triad plus 9th
        new ChordDefinition(D, CHORD_ADD9, "2,x,0,2,3,0"), // (D  E  Gb A) : major triad plus 9th
        new ChordDefinition(D, CHORD_ADD9, "x,0,2,2,3,2"), // (D  E  Gb A) : major triad plus 9th
        new ChordDefinition(D, CHORD_ADD9, "x,5,4,2,3,0"), // (D  E  Gb A) : major triad plus 9th
        new ChordDefinition(D, CHORD_ADD9, "x,9,7,7,x,0"), // (D  E  Gb A) : major triad plus 9th
        new ChordDefinition(D, CHORD_ADD9, "x,x,2,2,3,2"), // (D  E  Gb A) : major triad plus 9th
        new ChordDefinition(D, CHORD_AUG, "x,5,4,3,3,x", new String[] {"x,3,2,1,1,x"}),
        new ChordDefinition(D, CHORD_AUG, "x,x,0,3,3,2", new String[] {"x,x,x,2,3,1"}),
        new ChordDefinition(D, CHORD_DIM, "x,x,0,1,3,1", new String[] {"x,x,x,1,3,1"}),
        new ChordDefinition(D, CHORD_DIM7, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(D, CHORD_DIM7, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(D, CHORD_DIM7, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(D, CHORD_DOM9, "0,0,0,2,1,2"), // (C  D  E  Gb A) : major triad, minor 7th plus 9th
        new ChordDefinition(D, CHORD_DOM9, "2,x,0,2,1,0"), // (C  D  E  Gb A) : major triad, minor 7th plus 9th
        new ChordDefinition(D, CHORD_DOM9, "x,5,7,5,7,0"), // (C  D  E  Gb A) : major triad, minor 7th plus 9th
        new ChordDefinition(D, CHORD_MAJ, "2,0,0,2,3,2"), // (D  Gb A) : major triad
        new ChordDefinition(D, CHORD_MAJ, "x,0,0,2,3,2", new String[] {"x,x,x,1,3,2"}),
        new ChordDefinition(D, CHORD_MAJ, "x,0,4,2,3,2"), // (D  Gb A) : major triad
        new ChordDefinition(D, CHORD_MAJ, "x,5,4,2,3,2"), // (D  Gb A): major triad
        new ChordDefinition(D, CHORD_MAJ, "x,9,7,7,x,2"), // (D  Gb A): major triad
        new ChordDefinition(D, CHORD_MAJ, "x,x,0,2,3,2", new String[] {"x,x,x,1,3,2"}),
        new ChordDefinition(D, CHORD_MAJ, "x,x,0,7,7,5"), // (D  Gb A) : major triad
        new ChordDefinition(D, CHORD_MAJ6, "x,0,4,4,3,2"), // (D  Gb A  B) : major triad plus 6th
        new ChordDefinition(D, CHORD_MAJ6, "x,2,0,2,0,2"), // (D  Gb A  B) : major triad plus 6th
        new ChordDefinition(D, CHORD_MAJ6, "x,2,0,2,3,2"), // (D  Gb A  B) : major triad plus 6th
        new ChordDefinition(D, CHORD_MAJ6, "x,2,4,2,3,2"), // (D  Gb A  B) : major triad plus 6th
        new ChordDefinition(D, CHORD_MAJ6, "x,x,0,2,0,2"), // (D  Gb A  B) : major triad plus 6th
        new ChordDefinition(D, CHORD_MAJ69, "0,0,2,4,3,2"), // (D  E  Gb A  B) : major triad plus 6th and 9th
        new ChordDefinition(D, CHORD_MAJ69, "0,2,0,2,0,2"), // (D  E  Gb A  B) : major triad plus 6th and 9th
        new ChordDefinition(D, CHORD_MAJ7, "x,x,0,14,14,14"), // (Db D  Gb A) : major triad, major 7th
        new ChordDefinition(D, CHORD_MAJ7, "x,x,0,2,2,2"), // (Db D  Gb A) : major triad, major 7th
        new ChordDefinition(D, CHORD_MIN, "x,0,0,2,3,1", new String[] {"x,x,x,2,3,1"}),
        new ChordDefinition(D, CHORD_MIN, "x,x,0,2,3,1", new String[] {"x,x,x,2,3,1"}),
        new ChordDefinition(D, CHORD_MIN6, "1,2,3,2,3,1"), // (D  F  A  B) : minor triad plus 6th
        new ChordDefinition(D, CHORD_MIN6, "x,2,0,2,0,1"), // (D  F  A  B) : minor triad plus 6th
        new ChordDefinition(D, CHORD_MIN6, "x,x,0,2,0,1"), // (D  F  A  B) : minor triad plus 6th
        new ChordDefinition(D, CHORD_MIN7, "x,5,7,5,6,5"), // (C  D  F  A) : minor triad, minor 7th
        new ChordDefinition(D, CHORD_MIN7, "x,x,0,2,1,1", new String[] {"x,x,x,2,1,1"}),
        new ChordDefinition(D, CHORD_MIN7, "x,x,0,5,6,5"), // (C  D  F  A) : minor triad, minor 7th
        new ChordDefinition(D, CHORD_MIN7FLAT5, "4,x,3,5,3,x", new String[] {"2,x,1,3,1,x"}),
        new ChordDefinition(D, CHORD_MIN7FLAT5, "x,x,0,1,1,1", new String[] {"x,x,x,1,1,1"}),
        new ChordDefinition(D, CHORD_MIN7SUS4, "x,5,7,5,8,3"), // (C  D  G  A): sus4 triad, minor 7th
        new ChordDefinition(D, CHORD_MIN7SUS4, "x,x,0,2,1,3"), // (C  D  G  A) : sus4 triad, minor 7th
        new ChordDefinition(D, CHORD_MINMAJ7, "x,x,0,2,2,1"), // (Db D  F  A) : minor triad, major 7th
        new ChordDefinition(D, CHORD_SUS2, "0,0,2,2,3,0"), // (D  E  A) : no 3rd but a 2nd from a major triad
        new ChordDefinition(D, CHORD_SUS2, "5,5,7,7,x,0"), // (D  E  A): no 3rd but a 2nd from a major triad
        new ChordDefinition(D, CHORD_SUS2, "x,0,0,2,3,0"), // (D  E  A): no 3rd but a 2nd from a major triad
        new ChordDefinition(D, CHORD_SUS2, "x,0,2,2,3,0"), // (D  E  A) : no 3rd but a 2nd from a major triad
        new ChordDefinition(D, CHORD_SUS2, "x,x,0,2,3,0", new String[] {"x,x,x,1,3,x", "x,x,x,1,3,x"}),
        new ChordDefinition(D, CHORD_SUS4, "3,0,0,0,3,3"), // (D  G  A) : no 3rd but a 4th from a major triad
        new ChordDefinition(D, CHORD_SUS4, "5,x,0,0,3,5"), // (D  G  A): no 3rd but a 4th from a major triad
        new ChordDefinition(D, CHORD_SUS4, "x,0,0,0,3,3"), // (D  G  A) : no 3rd but a 4th from a major triad
        new ChordDefinition(D, CHORD_SUS4, "x,x,0,2,3,3", new String[] {"x,x,x,1,3,4"}),
        new ChordDefinition(D, Db, CHORD_MAJ, "x,x,0,14,14,14"), // (Db D  Gb A) : major triad (altered bass)
        new ChordDefinition(D, Db, CHORD_MAJ, "x,x,0,2,2,2"), // (Db D  Gb A) : major triad (altered bass)
        new ChordDefinition(D, Db, CHORD_MIN, "x,x,0,2,2,1"), // (Db D  F  A) : minor triad (altered bass)
        new ChordDefinition(D, Db, CHORD_SUS2, "x,0,0,2,2,0"), // (Db D  E  A) : sus2 triad (altered bass)
        new ChordDefinition(D, Db, CHORD_SUS2, "x,x,0,2,2,0"), // (Db D  E  A) : sus2 triad (altered bass)
        new ChordDefinition(D, Db, CHORD_SUS2, "x,x,0,6,5,5"), // (Db D  E  A) : sus2 triad (altered bass)
        new ChordDefinition(D, Db, CHORD_SUS2, "x,x,0,9,10,9"), // (Db D  E  A) : sus2 triad (altered bass)
        new ChordDefinition(D, E, CHORD_AUG, "2,x,4,3,3,0"), // (D  E  Gb Bb) : augmented triad (altered bass)
        new ChordDefinition(D, E, CHORD_MAJ, "0,0,0,2,3,2"), // (D  E  Gb A) : major triad (altered bass)
        new ChordDefinition(D, E, CHORD_MAJ, "0,0,4,2,3,0"), // (D  E  Gb A) : major triad (altered bass)
        new ChordDefinition(D, E, CHORD_MAJ, "2,x,0,2,3,0"), // (D  E  Gb A) : major triad (altered bass)
        new ChordDefinition(D, E, CHORD_MAJ, "x,0,2,2,3,2"), // (D  E  Gb A) : major triad (altered bass)
        new ChordDefinition(D, E, CHORD_MAJ, "x,5,4,2,3,0"), // (D  E  Gb A) : major triad (altered bass)
        new ChordDefinition(D, E, CHORD_MAJ, "x,9,7,7,x,0"), // (D  E  Gb A) : major triad (altered bass)
        new ChordDefinition(D, E, CHORD_MAJ, "x,x,2,2,3,2"), // (D  E  Gb A) : major triad (altered bass)
        new ChordDefinition(D, E, CHORD_MIN, "x,x,7,7,6,0"), // (D  E  F  A) : minor triad (altered bass)
        new ChordDefinition(D, E, CHORD_SUS4, "5,x,0,0,3,0"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(D, E, CHORD_SUS4, "x,0,0,0,x,0"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(D, E, CHORD_SUS4, "x,0,2,0,3,0"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(D, E, CHORD_SUS4, "x,0,2,0,3,3"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(D, E, CHORD_SUS4, "x,0,2,2,3,3"), // (D  E  G  A) : sus4 triad (altered bass)
        new ChordDefinition(D, F, CHORD_SUS2, "x,x,7,7,6,0"), // (D  E  F  A) : sus2 triad (altered bass)
        new ChordDefinition(D, G, CHORD_MAJ, "3,x,0,2,3,2"), // (D  Gb G  A) : major triad (altered bass)
        new ChordDefinition(D, G, CHORD_MAJ, "5,x,4,0,3,5"), // (D  Gb G  A): major triad (altered bass)
        new ChordDefinition(D, G, CHORD_SUS2, "5,x,0,0,3,0"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(D, G, CHORD_SUS2, "x,0,0,0,x,0"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(D, G, CHORD_SUS2, "x,0,2,0,3,0"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(D, G, CHORD_SUS2, "x,0,2,0,3,3"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(D, G, CHORD_SUS2, "x,0,2,2,3,3"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS2, "0,0,0,2,3,2"), // (D  E  Gb A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS2, "0,0,4,2,3,0"), // (D  E  Gb A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS2, "2,x,0,2,3,0"), // (D  E  Gb A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS2, "x,0,2,2,3,2"), // (D  E  Gb A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS2, "x,5,4,2,3,0"), // (D  E  Gb A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS2, "x,9,7,7,x,0"), // (D  E  Gb A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS2, "x,x,2,2,3,2"), // (D  E  Gb A) : sus2 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS4, "3,x,0,2,3,2"), // (D  Gb G  A) : sus4 triad (altered bass)
        new ChordDefinition(D, Gb, CHORD_SUS4, "5,x,4,0,3,5"), // (D  Gb G  A): sus4 triad (altered bass)
  
        new ChordDefinition(Db, A, CHORD_DIM, "3,x,2,2,2,0"), // (Db E  G  A) : diminished triad (altered bass)
        new ChordDefinition(Db, A, CHORD_DIM, "x,0,2,0,2,0"), // (Db E  G  A) : diminished triad (altered bass)
        new ChordDefinition(Db, A, CHORD_DIM, "x,0,2,2,2,3"), // (Db E  G  A) : diminished triad (altered bass)
        new ChordDefinition(Db, A, CHORD_MIN, "x,0,2,1,2,0"), // (Db E  Ab A) : minor triad (altered bass)
        new ChordDefinition(Db, B, CHORD_DIM, "0,2,2,0,2,0"), // (Db E  G  B) : diminished triad (altered bass)
        new ChordDefinition(Db, B, CHORD_MAJ, "x,4,3,4,0,4"), // (Db F  Ab B) : major triad (altered bass)
        new ChordDefinition(Db, B, CHORD_MIN, "0,2,2,1,2,0"), // (Db E  Ab B) : minor triad (altered bass)
        new ChordDefinition(Db, B, CHORD_MIN, "x,4,6,4,5,4"), // (Db E  Ab B) : minor triad (altered bass)
        new ChordDefinition(Db, Bb, CHORD_DIM, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(Db, Bb, CHORD_DIM, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(Db, Bb, CHORD_MAJ, "x,1,3,1,2,1"), // (Db F  Ab Bb) : major triad (altered bass)
        new ChordDefinition(Db, Bb, CHORD_SUS4, "x,x,4,3,2,4"), // (Db Gb Ab Bb) : sus4 triad (altered bass)
        new ChordDefinition(Db, C, CHORD_MAJ, "x,3,3,1,2,1"), // (C  Db F  Ab) : major triad (altered bass)
        new ChordDefinition(Db, C, CHORD_MAJ, "x,4,6,5,6,4"), // (C  Db F  Ab) : major triad (altered bass)
        new ChordDefinition(Db, CHORD_5, "x,4,6,6,x,4"), // (Db Ab): root and 5th (power chord)
        new ChordDefinition(Db, CHORD_7, "x,4,3,4,0,4"), // (Db F  Ab B) : major triad, minor 7th
        new ChordDefinition(Db, CHORD_7, "x,4,3,4,2,x", new String[] {"x,3,2,4,1,x"}),
        new ChordDefinition(Db, CHORD_7, "x,x,3,4,2,4", new String[] {"x,x,2,3,1,4", "x,x,2,3,1,4"}),
        new ChordDefinition(Db, CHORD_7FLAT5, "3,x,3,4,2,x", new String[] {"2,x,3,4,1,x"}),
        new ChordDefinition(Db, CHORD_7SHARP5, "x,4,3,2,0,1", new String[] {"x,4,3,2,x,1"}),
        new ChordDefinition(Db, CHORD_7SHARP5, "x,x,3,4,2,5", new String[] {"x,x,2,3,1,4"}),
        new ChordDefinition(Db, CHORD_AUG, "x,0,3,2,2,1"), // (Db F  A) : augmented triad
        new ChordDefinition(Db, CHORD_AUG, "x,0,x,2,2,1"), // (Db F  A) : augmented triad
        new ChordDefinition(Db, CHORD_AUG, "x,4,3,2,2,x", new String[] {"x,3,2,1,1,x", "x,4,3,1,2,x"}),
        new ChordDefinition(Db, CHORD_DIM, "x,4,2,0,2,0", new String[] {"x,4,1,x,2,x"}),
        new ChordDefinition(Db, CHORD_DIM7, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(Db, CHORD_DIM7, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(Db, CHORD_MAJ, "4,4,6,6,6,4"), // (Db F  Ab) : major triad
        new ChordDefinition(Db, CHORD_MAJ, "x,4,3,1,2,1", new String[] {"x,4,3,1,2,1", "x,4,3,1,2,1"}),
        new ChordDefinition(Db, CHORD_MAJ, "x,4,6,6,6,4"), // (Db F  Ab) : major triad
        new ChordDefinition(Db, CHORD_MAJ, "x,x,3,1,2,1"), // (Db F  Ab) : major triad
        new ChordDefinition(Db, CHORD_MAJ, "x,x,6,6,6,4"), // (Db F  Ab) : major triad
        new ChordDefinition(Db, CHORD_MAJ6, "x,1,3,1,2,1"), // (Db F  Ab Bb) : major triad plus 6th
        new ChordDefinition(Db, CHORD_MAJ7, "x,3,3,1,2,1"), // (C  Db F  Ab) : major triad, major 7th
        new ChordDefinition(Db, CHORD_MAJ7, "x,4,6,5,6,4"), // (C  Db F  Ab) : major triad, major 7th
        new ChordDefinition(Db, CHORD_MIN, "x,4,6,6,5,4"), // (Db E  Ab) : minor triad
        new ChordDefinition(Db, CHORD_MIN, "x,4,6,6,x,0"), // (Db E  Ab) : minor triad
        new ChordDefinition(Db, CHORD_MIN, "x,x,2,1,2,0", new String[] {"x,x,2,1,3,x"}),
        new ChordDefinition(Db, CHORD_MIN7, "0,2,2,1,2,0"), // (Db E  Ab B) : minor triad, minor 7th
        new ChordDefinition(Db, CHORD_MIN7, "x,4,2,1,0,0", new String[] {"x,4,2,1,x,x"}),
        new ChordDefinition(Db, CHORD_MIN7, "x,4,6,4,5,4"), // (Db E  Ab B) : minor triad, minor 7th
        new ChordDefinition(Db, CHORD_MIN7, "x,x,2,4,2,4", new String[] {"x,x,1,3,1,4"}),
        new ChordDefinition(Db, CHORD_MIN7FLAT5, "x,2,2,0,2,0", new String[] {"x,1,2,x,3,x"}),
        new ChordDefinition(Db, CHORD_MIN7FLAT5, "x,4,2,0,0,0", new String[] {"x,3,1,x,x,x"}),
        new ChordDefinition(Db, CHORD_MIN7FLAT5, "x,4,5,4,5,x", new String[] {"x,1,2,1,3,x"}),
        new ChordDefinition(Db, CHORD_SUS2, "x,x,6,6,4,4"), // (Db Eb Ab) : no 3rd but a 2nd from a major triad
        new ChordDefinition(Db, CHORD_SUS4, "x,x,x,1,2,2", new String[] {"x,x,x,1,3,4"}),
        new ChordDefinition(Db, D, CHORD_AUG, "x,x,0,2,2,1"), // (Db D  F  A) : augmented triad (altered bass)
        new ChordDefinition(Db, D, CHORD_DIM, "3,x,0,0,2,0"), // (Db D  E  G) : diminished triad (altered bass)
        new ChordDefinition(Db, D, CHORD_DIM, "x,x,0,0,2,0"), // (Db D  E  G) : diminished triad (altered bass)
        new ChordDefinition(Db, G, CHORD_AUG, "1,0,3,0,2,1"), // (Db F  G  A) : augmented triad (altered bass)
  
        new ChordDefinition(E, A, CHORD_MAJ, "x,0,2,1,0,0"), // (E  Ab A  B) : major triad (altered bass)
        new ChordDefinition(E, A, CHORD_MIN, "3,x,2,2,0,0"), // (E  G  A  B) : minor triad (altered bass)
        new ChordDefinition(E, A, CHORD_MIN, "x,0,2,0,0,0"), // (E  G  A  B) : minor triad (altered bass)
        new ChordDefinition(E, A, CHORD_MIN, "x,0,5,4,5,0"), // (E  G  A  B) : minor triad (altered bass)
        new ChordDefinition(E, A, CHORD_SUS2, "x,0,4,4,0,0"), // (E  Gb A  B) : sus2 triad (altered bass)
        new ChordDefinition(E, A, CHORD_SUS2, "x,2,4,2,5,2"), // (E  Gb A  B) : sus2 triad (altered bass)
        new ChordDefinition(E, Ab, CHORD_SUS2, "0,2,2,1,0,2"), // (E  Gb Ab B) : sus2 triad (altered bass)
        new ChordDefinition(E, Ab, CHORD_SUS2, "0,x,4,1,0,0"), // (E  Gb Ab B) : sus2 triad (altered bass)
        new ChordDefinition(E, Ab, CHORD_SUS2, "2,2,2,1,0,0"), // (E  Gb Ab B) : sus2 triad (altered bass)
        new ChordDefinition(E, Ab, CHORD_SUS4, "x,0,2,1,0,0"), // (E  Ab A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, C, CHORD_DIM, "x,3,5,3,5,3"), // (C  E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(E, C, CHORD_MIN, "0,3,2,0,0,0"), // (C  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, C, CHORD_MIN, "x,2,2,0,1,0"), // (C  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, C, CHORD_MIN, "x,3,5,4,5,3"), // (C  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, C, CHORD_SUS4, "0,0,7,5,0,0"), // (C  E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, C, CHORD_SUS4, "x,3,2,2,0,0"), // (C  E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, CHORD_5, "0,2,2,x,x,x", new String[] {"x,1,2,x,x,x"}),
        new ChordDefinition(E, CHORD_5, "0,2,x,x,x,0"), // (E  B) : root and 5th (power chord)
        new ChordDefinition(E, CHORD_5, "0,2,x,x,x,x", new String[] {"x,1,x,x,x,x"}),
        new ChordDefinition(E, CHORD_5, "x,7,9,9,x,0"), // (E  B) : root and 5th (power chord)
        new ChordDefinition(E, CHORD_7, "0,2,0,1,0,0", new String[] {"x,2,x,1,x,x"}),
        new ChordDefinition(E, CHORD_7, "0,2,0,1,3,0", new String[] {"0,2,0,1,4,0"}),
        new ChordDefinition(E, CHORD_7, "0,2,2,1,3,0"), // (D  E  Ab B) : major triad, minor 7th
        new ChordDefinition(E, CHORD_7, "x,2,0,1,3,0"), // (D  E  Ab B) : major triad, minor 7th
        new ChordDefinition(E, CHORD_7, "x,x,0,1,0,0"), // (D  E  Ab B) : major triad, minor 7th
        new ChordDefinition(E, CHORD_7, "x,x,2,4,3,4", new String[] {"x,x,1,3,2,4"}),
        new ChordDefinition(E, CHORD_7FLAT5, "x,1,0,1,3,0", new String[] {"x,1,x,2,4,x"}),
        new ChordDefinition(E, CHORD_7FLAT5, "x,x,2,3,3,4", new String[] {"x,x,1,2,3,4"}),
        new ChordDefinition(E, CHORD_7SHARP5, "x,x,0,1,1,0", new String[] {"x,x,x,1,2,x"}),
        new ChordDefinition(E, CHORD_7SHARP5, "x,x,2,5,3,4", new String[] {"x,x,1,4,2,3"}),
        new ChordDefinition(E, CHORD_ADD9, "0,2,2,1,0,2"), // (E  Gb Ab B) : major triad plus 9th
        new ChordDefinition(E, CHORD_ADD9, "0,2,4,1,0,0", new String[] {"x,2,3,1,x,x"}),
        new ChordDefinition(E, CHORD_ADD9, "0,x,4,1,0,0"), // (E  Gb Ab B) : major triad plus 9th
        new ChordDefinition(E, CHORD_ADD9, "2,2,2,1,0,0"), // (E  Gb Ab B) : major triad plus 9th
        new ChordDefinition(E, CHORD_AUG, "0,3,2,1,1,0", new String[] {"x,4,3,1,2,x"}),
        new ChordDefinition(E, CHORD_AUG, "4,x,x,5,5,4", new String[] {"1,x,x,3,4,2"}),
        new ChordDefinition(E, CHORD_AUG, "x,3,2,1,1,0"), // (C  E  Ab) : augmented triad
        new ChordDefinition(E, CHORD_AUG, "x,x,2,1,1,0", new String[] {"x,x,3,1,2,x"}),
        new ChordDefinition(E, CHORD_DIM, "x,x,2,3,5,3", new String[] {"x,x,1,2,4,3"}),
        new ChordDefinition(E, CHORD_DIM7, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(E, CHORD_DIM7, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(E, CHORD_DOM9, "0,2,0,1,0,2"), // (D  E  Gb Ab B) : major triad, minor 7th plus 9th
        new ChordDefinition(E, CHORD_DOM9, "2,2,0,1,0,0"), // (D  E  Gb Ab B) : major triad, minor 7th plus 9th
        new ChordDefinition(E, CHORD_MAJ, "0,2,2,1,0,0", new String[] {"x,2,3,1,x,x"}),
        new ChordDefinition(E, CHORD_MAJ, "x,7,6,4,5,0"), // (E  Ab B) : major triad
        new ChordDefinition(E, CHORD_MAJ6, "0,2,2,1,2,0"), // (Db E  Ab B) : major triad plus 6th
        new ChordDefinition(E, CHORD_MAJ6, "x,4,6,4,5,4"), // (Db E  Ab B) : major triad plus 6th
        new ChordDefinition(E, CHORD_MAJ7, "0,2,1,1,0,0"), // (Eb E  Ab B) : major triad, major 7th
        new ChordDefinition(E, CHORD_MAJ7, "0,x,6,4,4,0"), // (Eb E  Ab B) : major triad, major 7th
        new ChordDefinition(E, CHORD_MAJ7, "x,x,1,1,0,0"), // (Eb E  Ab B) : major triad, major 7th
        new ChordDefinition(E, CHORD_MAJ9, "0,2,1,1,0,2"), // (Eb E  Gb Ab B) : major triad, major 7th plus 9th
        new ChordDefinition(E, CHORD_MAJ9, "4,x,4,4,4,0"), // (Eb E  Gb Ab B) : major triad, major 7th plus 9th
        new ChordDefinition(E, CHORD_MIN, "0,2,2,0,0,0", new String[] {"x,2,3,x,x,x"}),
        new ChordDefinition(E, CHORD_MIN, "3,x,2,0,0,0"), // (E  G  B) : minor triad
        new ChordDefinition(E, CHORD_MIN, "x,2,5,x,x,0"), // (E  G  B) : minor triad
        new ChordDefinition(E, CHORD_MIN, "x,x,2,4,5,3", new String[] {"x,x,1,3,4,2"}),
        new ChordDefinition(E, CHORD_MIN6, "0,2,2,0,2,0"), // (Db E  G  B) : minor triad plus 6th
        new ChordDefinition(E, CHORD_MIN7, "0,2,0,0,0,0", new String[] {"x,2,x,x,x,x"}),
        new ChordDefinition(E, CHORD_MIN7, "0,2,0,0,3,0", new String[] {"x,2,x,x,4,x", "x,2,3,x,4,x"}),
        new ChordDefinition(E, CHORD_MIN7, "0,2,2,0,3,3"), // (D  E  G  B) : minor triad, minor 7th
        new ChordDefinition(E, CHORD_MIN7, "0,x,0,0,0,0"), // (D  E  G  B) : minor triad, minor 7th
        new ChordDefinition(E, CHORD_MIN7, "x,10,12,12,12,0"), // (D  E  G  B) : minor triad, minor 7th
        new ChordDefinition(E, CHORD_MIN7, "x,x,0,0,0,0"), // (D  E  G  B) : minor triad, minor 7th
        new ChordDefinition(E, CHORD_MIN7, "x,x,0,12,12,12"), // (D  E  G  B) : minor triad, minor 7th
        new ChordDefinition(E, CHORD_MIN7, "x,x,0,9,8,7"), // (D  E  G  B) : minor triad, minor 7th
        new ChordDefinition(E, CHORD_MIN7, "x,x,2,4,3,3", new String[] {"x,x,1,4,2,3"}),
        new ChordDefinition(E, CHORD_MIN7FLAT5, "3,x,0,3,3,0"), // (D  E  G  Bb) : diminished triad, minor 7th : half-diminished 7th
        new ChordDefinition(E, CHORD_MIN7FLAT5, "x,x,2,3,3,3", new String[] {"x,x,1,2,3,4", "x,x,1,3,3,3"}),
        new ChordDefinition(E, CHORD_MIN7SUS4, "0,2,0,2,0,0"), // (D  E  A  B) : sus4 triad, minor 7th
        new ChordDefinition(E, CHORD_MIN9, "0,2,0,0,0,2"), // (D  E  Gb G  B) : minor triad, minor 7th plus 9th
        new ChordDefinition(E, CHORD_MIN9, "0,2,0,0,3,2"), // (D  E  Gb G  B) : minor triad, minor 7th plus 9th
        new ChordDefinition(E, CHORD_MIN9, "2,2,0,0,0,0"), // (D  E  Gb G  B) : minor triad, minor 7th plus 9th
        new ChordDefinition(E, CHORD_MINMAJ7, "3,x,1,0,0,0"), // (Eb E  G  B) : minor triad, major 7th
        new ChordDefinition(E, CHORD_MINMAJ7, "x,x,1,0,0,0"), // (Eb E  G  B) : minor triad, major 7th
        new ChordDefinition(E, CHORD_SUS2, "7,9,9,x,x,0"), // (E  Gb B): no 3rd but a 2nd from a major triad
        new ChordDefinition(E, CHORD_SUS2, "x,2,4,4,x,0", new String[] {"x,1,3,4,x,x"}),
        new ChordDefinition(E, CHORD_SUS2, "x,x,2,4,5,2", new String[] {"x,x,1,3,4,1"}),
        new ChordDefinition(E, CHORD_SUS4, "0,0,2,2,0,0"), // (E  A  B) : no 3rd but a 4th from a major triad
        new ChordDefinition(E, CHORD_SUS4, "0,0,2,4,0,0"), // (E  A  B) : no 3rd but a 4th from a major triad
        new ChordDefinition(E, CHORD_SUS4, "0,2,2,2,0,0", new String[] {"x,2,3,4,x,x", "x,2,3,4,x,x"}),
        new ChordDefinition(E, CHORD_SUS4, "x,0,2,2,0,0"), // (E  A  B) : no 3rd but a 4th from a major triad
        new ChordDefinition(E, CHORD_SUS4, "x,x,2,2,0,0"), // (E  A  B) : no 3rd but a 4th from a major triad
        new ChordDefinition(E, CHORD_SUS4, "x,x,2,4,5,5", new String[] {"x,x,1,3,4,4"}),
        new ChordDefinition(E, D, CHORD_DIM, "3,x,0,3,3,0"), // (D  E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(E, D, CHORD_MAJ, "0,2,0,1,0,0"), // (D  E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, D, CHORD_MAJ, "0,2,2,1,3,0"), // (D  E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, D, CHORD_MAJ, "x,2,0,1,3,0"), // (D  E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, D, CHORD_MAJ, "x,x,0,1,0,0"), // (D  E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "0,2,0,0,0,0"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "0,2,0,0,3,0"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "0,2,2,0,3,0"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "0,2,2,0,3,3"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "0,x,0,0,0,0"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "x,10,12,12,12,0"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "x,x,0,12,12,12"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "x,x,0,9,8,7"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_MIN, "x,x,2,4,3,3"), // (D  E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, D, CHORD_SUS4, "0,2,0,2,0,0"), // (D  E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, D, CHORD_SUS4, "x,2,0,2,3,0"), // (D  E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, Db, CHORD_DIM, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(E, Db, CHORD_DIM, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(E, Db, CHORD_MAJ, "0,2,2,1,2,0"), // (Db E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, Db, CHORD_MAJ, "x,4,6,4,5,4"), // (Db E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, Db, CHORD_MIN, "0,2,2,0,2,0"), // (Db E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, Db, CHORD_SUS2, "x,4,4,4,x,0"), // (Db E  Gb B) : sus2 triad (altered bass)
        new ChordDefinition(E, Db, CHORD_SUS4, "0,0,2,4,2,0"), // (Db E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, Db, CHORD_SUS4, "x,0,7,6,0,0"), // (Db E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_DIM, "x,x,5,3,4,0"), // (Eb E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_MAJ, "0,2,1,1,0,0"), // (Eb E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_MAJ, "0,x,6,4,4,0"), // (Eb E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_MAJ, "x,x,1,1,0,0"), // (Eb E  Ab B) : major triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_MIN, "3,x,1,0,0,0"), // (Eb E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_MIN, "x,x,1,0,0,0"), // (Eb E  G  B) : minor triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_SUS2, "x,2,2,4,4,2"), // (Eb E  Gb B) : sus2 triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_SUS2, "x,x,4,4,4,0"), // (Eb E  Gb B) : sus2 triad (altered bass)
        new ChordDefinition(E, Eb, CHORD_SUS4, "x,2,1,2,0,0"), // (Eb E  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, F, CHORD_SUS4, "0,0,3,2,0,0"), // (E  F  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, G, CHORD_SUS2, "0,2,2,0,0,2"), // (E  Gb G  B) : sus2 triad (altered bass)
        new ChordDefinition(E, G, CHORD_SUS2, "0,2,4,0,0,0"), // (E  Gb G  B) : sus2 triad (altered bass)
        new ChordDefinition(E, G, CHORD_SUS2, "0,x,4,0,0,0"), // (E  Gb G  B) : sus2 triad (altered bass)
        new ChordDefinition(E, G, CHORD_SUS2, "2,2,2,0,0,0"), // (E  Gb G  B) : sus2 triad (altered bass)
        new ChordDefinition(E, G, CHORD_SUS4, "3,x,2,2,0,0"), // (E  G  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, G, CHORD_SUS4, "x,0,2,0,0,0"), // (E  G  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, G, CHORD_SUS4, "x,0,5,4,5,0"), // (E  G  A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_MAJ, "0,2,2,1,0,2"), // (E  Gb Ab B) : major triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_MAJ, "0,x,4,1,0,0"), // (E  Gb Ab B) : major triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_MAJ, "2,2,2,1,0,0"), // (E  Gb Ab B) : major triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_MIN, "0,2,2,0,0,2"), // (E  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_MIN, "0,2,4,0,0,0"), // (E  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_MIN, "0,x,4,0,0,0"), // (E  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_MIN, "2,2,2,0,0,0"), // (E  Gb G  B) : minor triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_SUS4, "x,0,4,4,0,0"), // (E  Gb A  B) : sus4 triad (altered bass)
        new ChordDefinition(E, Gb, CHORD_SUS4, "x,2,4,2,5,2"), // (E  Gb A  B) : sus4 triad (altered bass)
  
        new ChordDefinition(Eb, Ab, CHORD_SUS2, "x,1,3,1,4,1"), // (Eb F  Ab Bb) : sus2 triad (altered bass)
        new ChordDefinition(Eb, B, CHORD_DIM, "2,x,1,2,0,2"), // (Eb Gb A  B) : diminished triad (altered bass)
        new ChordDefinition(Eb, B, CHORD_DIM, "x,0,1,2,0,2"), // (Eb Gb A  B) : diminished triad (altered bass)
        new ChordDefinition(Eb, B, CHORD_DIM, "x,2,1,2,0,2"), // (Eb Gb A  B) : diminished triad (altered bass)
        new ChordDefinition(Eb, B, CHORD_DIM, "x,2,4,2,4,2"), // (Eb Gb A  B) : diminished triad (altered bass)
        new ChordDefinition(Eb, C, CHORD_DIM, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad (altered bass)
        new ChordDefinition(Eb, C, CHORD_MAJ, "x,3,5,3,4,3"), // (C  Eb G  Bb) : major triad (altered bass)
        new ChordDefinition(Eb, CHORD_5, "x,6,8,8,x,6"), // (Eb Bb): root and 5th (power chord)
        new ChordDefinition(Eb, CHORD_7, "x,1,1,3,2,3"), // (Db Eb G  Bb) : major triad, minor 7th
        new ChordDefinition(Eb, CHORD_7, "x,4,x,3,4,3", new String[] {"x,2,x,1,3,1"}),
        new ChordDefinition(Eb, CHORD_7, "x,6,8,6,8,6"), // (Db Eb G  Bb) : major triad, minor 7th
        new ChordDefinition(Eb, CHORD_7, "x,x,1,3,2,3", new String[] {"x,x,1,3,2,4", "x,x,1,3,2,4"}),
        new ChordDefinition(Eb, CHORD_7FLAT5, "x,x,1,2,2,3", new String[] {"x,x,1,2,3,4"}),
        new ChordDefinition(Eb, CHORD_7SHARP5, "x,2,1,0,2,3", new String[] {"x,2,1,x,3,4"}),
        new ChordDefinition(Eb, CHORD_AUG, "3,2,1,0,0,3", new String[] {"x,2,1,x,x,4"}),
        new ChordDefinition(Eb, CHORD_AUG, "3,x,1,0,0,3"), // (Eb G  B) : augmented triad
        new ChordDefinition(Eb, CHORD_AUG, "x,x,1,0,0,3", new String[] {"x,x,1,x,x,3"}),
        new ChordDefinition(Eb, CHORD_AUG, "x,x,5,4,4,3", new String[] {"x,x,4,2,3,1"}),
        new ChordDefinition(Eb, CHORD_DIM, "x,x,4,2,4,2", new String[] {"x,x,3,1,4,1"}),
        new ChordDefinition(Eb, CHORD_DIM7, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad, diminished 7th
        new ChordDefinition(Eb, CHORD_MAJ, "x,1,1,3,4,3"), // (Eb G  Bb) : major triad
        new ChordDefinition(Eb, CHORD_MAJ, "x,x,1,3,4,3", new String[] {"x,x,1,2,4,3"}),
        new ChordDefinition(Eb, CHORD_MAJ, "x,x,5,3,4,3", new String[] {"x,x,4,1,3,2"}),
        new ChordDefinition(Eb, CHORD_MAJ6, "x,3,5,3,4,3"), // (C  Eb G  Bb) : major triad plus 6th
        new ChordDefinition(Eb, CHORD_MAJ7, "x,6,8,7,8,6"), // (D  Eb G  Bb) : major triad, major 7th
        new ChordDefinition(Eb, CHORD_MIN, "x,x,1,3,4,2", new String[] {"x,x,1,3,4,2"}),
        new ChordDefinition(Eb, CHORD_MIN, "x,x,4,3,4,2", new String[] {"x,x,3,2,4,1"}),
        new ChordDefinition(Eb, CHORD_MIN7, "x,x,1,3,2,2", new String[] {"x,x,1,4,2,3", "x,x,1,4,2,3"}),
        new ChordDefinition(Eb, CHORD_MIN7FLAT5, "x,x,1,2,2,2", new String[] {"x,x,1,2,3,4", "x,x,1,3,3,3"}),
        new ChordDefinition(Eb, CHORD_SUS2, "1,1,1,3,x,1", new String[] {"x,1,1,3,x,1"}),
        new ChordDefinition(Eb, CHORD_SUS2, "x,x,1,3,4,1", new String[] {"x,x,1,3,4,1"}),
        new ChordDefinition(Eb, CHORD_SUS4, "4,x,1,3,4,x", new String[] {"3,x,1,2,4,x"}),
        new ChordDefinition(Eb, CHORD_SUS4, "x,x,1,3,4,4", new String[] {"x,x,1,3,4,4"}),
        new ChordDefinition(Eb, D, CHORD_MAJ, "x,6,8,7,8,6"), // (D  Eb G  Bb) : major triad (altered bass)
        new ChordDefinition(Eb, Db, CHORD_MAJ, "x,1,1,3,2,3"), // (Db Eb G  Bb) : major triad (altered bass)
        new ChordDefinition(Eb, Db, CHORD_MAJ, "x,6,8,6,8,6"), // (Db Eb G  Bb) : major triad (altered bass)
        new ChordDefinition(Eb, Db, CHORD_MAJ, "x,x,1,3,2,3"), // (Db Eb G  Bb) : major triad (altered bass)
        new ChordDefinition(Eb, Db, CHORD_MIN, "x,x,1,3,2,2"), // (Db Eb Gb Bb) : minor triad (altered bass)
        new ChordDefinition(Eb, E, CHORD_AUG, "3,x,1,0,0,0"), // (Eb E  G  B) : augmented triad (altered bass)
        new ChordDefinition(Eb, E, CHORD_AUG, "x,x,1,0,0,0"), // (Eb E  G  B) : augmented triad (altered bass)
        new ChordDefinition(Eb, E, CHORD_MAJ, "x,x,5,3,4,0"), // (Eb E  G  Bb) : major triad (altered bass)
        new ChordDefinition(Eb, F, CHORD_SUS4, "x,1,3,1,4,1"), // (Eb F  Ab Bb) : sus4 triad (altered bass)
  
        new ChordDefinition(F, A, CHORD_SUS2, "3,x,3,2,1,1"), // (C  F  G  A) : sus2 triad (altered bass)
        new ChordDefinition(F, A, CHORD_SUS2, "x,x,3,2,1,3"), // (C  F  G  A) : sus2 triad (altered bass)
        new ChordDefinition(F, B, CHORD_SUS2, "x,3,3,0,0,3"), // (C  F  G  B) : sus2 triad (altered bass)
        new ChordDefinition(F, Bb, CHORD_SUS2, "x,3,5,3,6,3"), // (C  F  G  Bb) : sus2 triad (altered bass)
        new ChordDefinition(F, CHORD_5, "1,3,3,x,x,1"), // (C  F): root and 5th (power chord)
        new ChordDefinition(F, CHORD_5, "1,3,3,x,x,x", new String[] {"1,3,4,x,x,x"}),
        new ChordDefinition(F, CHORD_5, "1,3,x,x,x,x", new String[] {"1,3,x,x,x,x"}),
        new ChordDefinition(F, CHORD_5, "x,8,10,x,x,1"), // (C  F): root and 5th (power chord)
        new ChordDefinition(F, CHORD_7, "1,3,1,2,1,1", new String[] {"1,3,1,2,1,1", "x,3,1,2,1,1"}),
        new ChordDefinition(F, CHORD_7, "x,x,1,2,1,1"), // (C  Eb F  A) : major triad, minor 7th
        new ChordDefinition(F, CHORD_7, "x,x,3,5,4,5", new String[] {"x,x,1,3,2,4"}),
        new ChordDefinition(F, CHORD_7FLAT5, "1,x,1,2,0,x", new String[] {"x,x,2,3,x,x"}),
        new ChordDefinition(F, CHORD_7FLAT5, "x,x,1,2,0,1", new String[] {"x,x,1,3,x,2"}),
        new ChordDefinition(F, CHORD_7FLAT5, "x,x,3,4,4,5", new String[] {"x,x,1,2,3,4"}),
        new ChordDefinition(F, CHORD_7SHARP5, "x,x,1,2,2,1", new String[] {"x,x,1,2,3,1"}),
        new ChordDefinition(F, CHORD_ADD9, "3,x,3,2,1,1"), // (C  F  G  A) : major triad plus 9th
        new ChordDefinition(F, CHORD_ADD9, "x,x,3,2,1,3"), // (C  F  G  A) : major triad plus 9th
        new ChordDefinition(F, CHORD_AUG, "1,x,3,2,2,1", new String[] {"x,x,4,2,3,1"}),
        new ChordDefinition(F, CHORD_AUG, "x,0,3,2,2,1"), // (Db F  A) : augmented triad
        new ChordDefinition(F, CHORD_AUG, "x,0,x,2,2,1"), // (Db F  A) : augmented triad
        new ChordDefinition(F, CHORD_AUG, "x,x,3,2,2,1", new String[] {"x,x,4,2,3,1"}),
        new ChordDefinition(F, CHORD_DIM, "x,x,3,1,0,1", new String[] {"x,x,4,1,x,2"}),
        new ChordDefinition(F, CHORD_DIM7, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(F, CHORD_DIM7, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(F, CHORD_DIM7, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad, diminished 7th
        new ChordDefinition(F, CHORD_MAJ, "1,3,3,2,1,1", new String[] {"1,3,4,2,1,1"}),
        new ChordDefinition(F, CHORD_MAJ, "x,0,3,2,1,1"), // (C  F  A) : major triad
        new ChordDefinition(F, CHORD_MAJ, "x,3,3,2,1,1"), // (C  F  A) : major triad
        new ChordDefinition(F, CHORD_MAJ, "x,x,3,2,1,1", new String[] {"x,x,3,2,1,1"}),
        new ChordDefinition(F, CHORD_MAJ6, "x,5,7,5,6,5"), // (C  D  F  A) : major triad plus 6th
        new ChordDefinition(F, CHORD_MAJ6, "x,x,0,2,1,1"), // (C  D  F  A) : major triad plus 6th
        new ChordDefinition(F, CHORD_MAJ6, "x,x,0,5,6,5"), // (C  D  F  A) : major triad plus 6th
        new ChordDefinition(F, CHORD_MAJ69, "3,x,0,2,1,1"), // (C  D  F  G  A) : major triad plus 6th and 9th
        new ChordDefinition(F, CHORD_MAJ7, "0,0,3,2,1,0"), // (C  E  F  A) : major triad, major 7th
        new ChordDefinition(F, CHORD_MAJ7, "1,3,3,2,1,0"), // (C  E  F  A) : major triad, major 7th
        new ChordDefinition(F, CHORD_MAJ7, "1,x,2,2,1,0"), // (C  E  F  A) : major triad, major 7th
        new ChordDefinition(F, CHORD_MAJ7, "x,x,2,2,1,1"), // (C  E  F  A) : major triad, major 7th
        new ChordDefinition(F, CHORD_MAJ7, "x,x,3,2,1,0", new String[] {"x,x,3,2,1,x"}),
        new ChordDefinition(F, CHORD_MAJ9, "0,0,3,0,1,3"), // (C  E  F  G  A) : major triad, major 7th plus 9th
        new ChordDefinition(F, CHORD_MIN, "1,3,3,1,1,1", new String[] {"1,3,4,1,1,1"}),
        new ChordDefinition(F, CHORD_MIN, "x,3,3,1,1,1"), // (C  F  Ab) : minor triad
        new ChordDefinition(F, CHORD_MIN, "x,x,3,1,1,1"), // (C  F  Ab) : minor triad
        new ChordDefinition(F, CHORD_MIN6, "x,x,0,1,1,1"), // (C  D  F  Ab) : minor triad plus 6th
        new ChordDefinition(F, CHORD_MIN7, "1,3,1,1,1,1", new String[] {"1,3,1,1,1,1"}),
        new ChordDefinition(F, CHORD_MIN7, "1,3,3,1,4,1", new String[] {"1,2,3,1,4,1"}),
        new ChordDefinition(F, CHORD_MIN7, "x,8,10,8,9,8"), // (C  Eb F  Ab) : minor triad, minor 7th
        new ChordDefinition(F, CHORD_MIN7, "x,x,1,1,1,1", new String[] {"x,x,1,1,1,1"}),
        new ChordDefinition(F, CHORD_MIN7, "x,x,3,5,4,4", new String[] {"x,x,1,4,2,3"}),
        new ChordDefinition(F, CHORD_MIN7FLAT5, "x,x,3,4,4,4", new String[] {"x,x,1,2,3,4", "x,x,1,3,3,3"}),
        new ChordDefinition(F, CHORD_SUS2, "x,3,3,0,1,1", new String[] {"x,3,4,x,1,1"}),
        new ChordDefinition(F, CHORD_SUS2, "x,x,3,0,1,1", new String[] {"x,x,3,x,1,1"}),
        new ChordDefinition(F, CHORD_SUS4, "1,1,3,3,1,1", new String[] {"1,1,4,3,1,1"}),
        new ChordDefinition(F, CHORD_SUS4, "1,3,3,3,1,1", new String[] {"1,2,3,4,1,1", "x,2,3,4,1,1"}),
        new ChordDefinition(F, CHORD_SUS4, "x,x,3,3,1,1"), // (C  F  Bb) : no 3rd but a 4th from a major triad
        new ChordDefinition(F, D, CHORD_DIM, "x,2,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(F, D, CHORD_DIM, "x,x,0,1,0,1"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(F, D, CHORD_DIM, "x,x,3,4,3,4"), // (D  F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(F, D, CHORD_MAJ, "x,5,7,5,6,5"), // (C  D  F  A) : major triad (altered bass)
        new ChordDefinition(F, D, CHORD_MAJ, "x,x,0,2,1,1"), // (C  D  F  A) : major triad (altered bass)
        new ChordDefinition(F, D, CHORD_MAJ, "x,x,0,5,6,5"), // (C  D  F  A) : major triad (altered bass)
        new ChordDefinition(F, D, CHORD_MIN, "x,x,0,1,1,1"), // (C  D  F  Ab) : minor triad (altered bass)
        new ChordDefinition(F, D, CHORD_SUS2, "3,3,0,0,1,1"), // (C  D  F  G) : sus2 triad (altered bass)
        new ChordDefinition(F, Db, CHORD_DIM, "x,4,3,4,0,4"), // (Db F  Ab B) : diminished triad (altered bass)
        new ChordDefinition(F, Db, CHORD_MIN, "x,3,3,1,2,1"), // (C  Db F  Ab) : minor triad (altered bass)
        new ChordDefinition(F, Db, CHORD_MIN, "x,4,6,5,6,4"), // (C  Db F  Ab) : minor triad (altered bass)
        new ChordDefinition(F, E, CHORD_MAJ, "0,0,3,2,1,0"), // (C  E  F  A) : major triad (altered bass)
        new ChordDefinition(F, E, CHORD_MAJ, "1,3,3,2,1,0"), // (C  E  F  A) : major triad (altered bass)
        new ChordDefinition(F, E, CHORD_MAJ, "1,x,2,2,1,0"), // (C  E  F  A) : major triad (altered bass)
        new ChordDefinition(F, E, CHORD_MAJ, "x,x,2,2,1,1"), // (C  E  F  A) : major triad (altered bass)
        new ChordDefinition(F, E, CHORD_MAJ, "x,x,3,2,1,0"), // (C  E  F  A) : major triad (altered bass)
        new ChordDefinition(F, E, CHORD_SUS2, "x,3,3,0,1,0"), // (C  E  F  G) : sus2 triad (altered bass)
        new ChordDefinition(F, E, CHORD_SUS2, "x,x,3,0,1,0"), // (C  E  F  G) : sus2 triad (altered bass)
        new ChordDefinition(F, Eb, CHORD_MAJ, "x,x,1,2,1,1"), // (C  Eb F  A) : major triad (altered bass)
        new ChordDefinition(F, Eb, CHORD_MAJ, "x,x,3,5,4,5"), // (C  Eb F  A) : major triad (altered bass)
        new ChordDefinition(F, Eb, CHORD_MIN, "x,8,10,8,9,8"), // (C  Eb F  Ab) : minor triad (altered bass)
        new ChordDefinition(F, Eb, CHORD_MIN, "x,x,1,1,1,1"), // (C  Eb F  Ab) : minor triad (altered bass)
        new ChordDefinition(F, G, CHORD_MAJ, "3,x,3,2,1,1"), // (C  F  G  A) : major triad (altered bass)
        new ChordDefinition(F, G, CHORD_MAJ, "x,x,3,2,1,3"), // (C  F  G  A) : major triad (altered bass)
        new ChordDefinition(F, G, CHORD_SUS2, "x,3,5,3,6,3"), // (C  F  G  Bb) : sus4 triad (altered bass)
  
        new ChordDefinition(G, A, CHORD_MAJ, "3,0,0,0,0,3"), // (D  G  A  B) : major triad (altered bass)
        new ChordDefinition(G, A, CHORD_MAJ, "3,2,0,2,0,3"), // (D  G  A  B) : major triad (altered bass)
        new ChordDefinition(G, A, CHORD_SUS4, "x,5,7,5,8,3"), // (C  D  G  A): sus4 triad (altered bass)
        new ChordDefinition(G, A, CHORD_SUS4, "x,x,0,2,1,3"), // (C  D  G  A) : sus4 triad (altered bass)
        new ChordDefinition(G, B, CHORD_SUS2, "3,0,0,0,0,3"), // (D  G  A  B) : sus2 triad (altered bass)
        new ChordDefinition(G, B, CHORD_SUS2, "3,2,0,2,0,3"), // (D  G  A  B) : sus2 triad (altered bass)
        new ChordDefinition(G, B, CHORD_SUS4, "3,3,0,0,0,3"), // (C  D  G  B) : sus4 triad (altered bass)
        new ChordDefinition(G, B, CHORD_SUS4, "x,3,0,0,0,3"), // (C  D  G  B) : sus4 triad (altered bass)
        new ChordDefinition(G, C, CHORD_MAJ, "3,3,0,0,0,3"), // (C  D  G  B) : major triad (altered bass)
        new ChordDefinition(G, C, CHORD_MAJ, "x,3,0,0,0,3"), // (C  D  G  B) : major triad (altered bass)
        new ChordDefinition(G, C, CHORD_SUS2, "x,5,7,5,8,3"), // (C  D  G  A): sus2 triad (altered bass)
        new ChordDefinition(G, C, CHORD_SUS2, "x,x,0,2,1,3"), // (C  D  G  A) : sus2 triad (altered bass)
        new ChordDefinition(G, CHORD_5, "3,5,5,x,x,3"), // (D  G): root and 5th (power chord)
        new ChordDefinition(G, CHORD_5, "3,5,5,x,x,x", new String[] {"1,3,4,x,x,x"}),
        new ChordDefinition(G, CHORD_5, "3,x,0,0,3,3"), // (D  G) : root and 5th (power chord)
        new ChordDefinition(G, CHORD_7, "1,x,0,0,0,3"), // (D  F  G  B) : major triad, minor 7th
        new ChordDefinition(G, CHORD_7, "3,2,0,0,0,1", new String[] {"3,2,x,x,x,1", "x,2,x,x,x,1"}),
        new ChordDefinition(G, CHORD_7, "3,5,3,4,3,3", new String[] {"1,3,1,2,1,1"}),
        new ChordDefinition(G, CHORD_7, "x,x,0,0,0,1"), // (D  F  G  B) : major triad, minor 7th
        new ChordDefinition(G, CHORD_7FLAT5, "3,x,3,4,2,x", new String[] {"2,x,3,4,1,x", "x,x,3,4,1,x"}),
        new ChordDefinition(G, CHORD_7FLAT5, "x,4,3,0,0,1", new String[] {"x,4,3,x,x,1"}),
        new ChordDefinition(G, CHORD_ADD9, "3,0,0,0,0,3"), // (D  G  A  B) : major triad plus 9th
        new ChordDefinition(G, CHORD_ADD9, "3,2,0,2,0,3"), // (D  G  A  B) : major triad plus 9th
        new ChordDefinition(G, CHORD_AUG, "3,2,1,0,0,3", new String[] {"3,2,1,x,x,4"}),
        new ChordDefinition(G, CHORD_AUG, "3,x,1,0,0,3"), // (Eb G  B) : augmented triad
        new ChordDefinition(G, CHORD_AUG, "3,x,5,4,4,x", new String[] {"1,x,4,2,3,x"}),
        new ChordDefinition(G, CHORD_AUG, "x,x,5,4,4,3", new String[] {"x,x,4,2,3,1"}),
        new ChordDefinition(G, CHORD_DIM, "x,1,x,0,2,3", new String[] {"x,1,x,x,2,3"}),
        new ChordDefinition(G, CHORD_DIM7, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(G, CHORD_DIM7, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad, diminished 7th
        new ChordDefinition(G, CHORD_MAJ, "3,2,0,0,0,3", new String[] {"2,1,x,x,x,3"}),
        new ChordDefinition(G, CHORD_MAJ, "3,2,0,0,3,3"), // (D  G  B) : major triad
        new ChordDefinition(G, CHORD_MAJ, "3,5,5,4,3,3", new String[] {"1,3,4,2,1,1"}),
        new ChordDefinition(G, CHORD_MAJ, "3,x,0,0,0,3"), // (D  G  B) : major triad
        new ChordDefinition(G, CHORD_MAJ, "x,10,12,12,12,10"), // (D  G  B): major triad
        new ChordDefinition(G, CHORD_MAJ, "x,5,5,4,3,3"), // (D  G  B) : major triad
        new ChordDefinition(G, CHORD_MAJ, "x,x,0,4,3,3"), // (D  G  B) : major triad
        new ChordDefinition(G, CHORD_MAJ, "x,x,0,7,8,7"), // (D  G  B) : major triad
        new ChordDefinition(G, CHORD_MAJ6, "0,2,0,0,0,0"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "0,2,0,0,3,0"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "0,2,2,0,3,0"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "0,2,2,0,3,3"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "0,x,0,0,0,0"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "x,10,12,12,12,0"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "x,x,0,12,12,12"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "x,x,0,9,8,7"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ6, "x,x,2,4,3,3"), // (D  E  G  B) : major triad plus 6th
        new ChordDefinition(G, CHORD_MAJ69, "0,0,0,0,0,0"), // (D  E  G  A  B) : major triad plus 6th and 9th
        new ChordDefinition(G, CHORD_MAJ69, "0,0,0,0,0,3"), // (D  E  G  A  B) : major triad plus 6th and 9th
        new ChordDefinition(G, CHORD_MAJ69, "3,x,0,2,0,0"), // (D  E  G  A  B) : major triad plus 6th and 9th
        new ChordDefinition(G, CHORD_MAJ7, "2,2,0,0,0,3"), // (D  Gb G  B) : major triad, major 7th
        new ChordDefinition(G, CHORD_MAJ7, "2,2,0,0,3,3"), // (D  Gb G  B) : major triad, major 7th
        new ChordDefinition(G, CHORD_MAJ7, "3,2,0,0,0,2"), // (D  Gb G  B) : major triad, major 7th
        new ChordDefinition(G, CHORD_MAJ7, "x,x,4,4,3,3"), // (D  Gb G  B) : major triad, major 7th
        new ChordDefinition(G, CHORD_MAJ9, "x,0,0,0,0,2"), // (D  F  G  A  B) : major triad, minor 7th plus 9th
        new ChordDefinition(G, CHORD_MIN, "3,1,0,0,3,3", new String[] {"2,1,x,x,3,4", "x,1,x,x,3,4"}),
        new ChordDefinition(G, CHORD_MIN, "3,5,5,3,3,3", new String[] {"1,3,4,1,1,1"}),
        new ChordDefinition(G, CHORD_MIN, "x,x,0,3,3,3"), // (D  G  Bb) : minor triad
        new ChordDefinition(G, CHORD_MIN6, "3,x,0,3,3,0"), // (D  E  G  Bb) : minor triad plus 6th
        new ChordDefinition(G, CHORD_MIN6, "x,x,5,3,3,0", new String[] {"x,x,3,1,2,x"}),
        new ChordDefinition(G, CHORD_MIN7, "3,5,3,3,3,3", new String[] {"1,3,1,1,1,1"}),
        new ChordDefinition(G, CHORD_MIN7, "3,x,3,3,3,x", new String[] {"2,x,3,3,3,x"}),
        new ChordDefinition(G, CHORD_MIN7, "x,1,3,0,3,x", new String[] {"x,1,3,x,4,x"}),
        new ChordDefinition(G, CHORD_MIN7, "x,x,3,3,3,3"), // (D  F  G  Bb) : minor triad, minor 7th
        new ChordDefinition(G, CHORD_MIN7FLAT5, "3,x,3,3,2,x", new String[] {"2,x,3,4,1,x"}),
        new ChordDefinition(G, CHORD_MIN7SUS4, "3,3,0,0,1,1"), // (C  D  F  G) : sus4 triad, minor 7th
        new ChordDefinition(G, CHORD_MIN9, "3,5,3,3,3,5"), // (D  F  G  A  Bb) : minor triad, minor 7th plus 9th
        new ChordDefinition(G, CHORD_SUS2, "3,0,0,0,3,3", new String[] {"x,x,x,x,3,4"}),
        new ChordDefinition(G, CHORD_SUS2, "3,0,0,2,3,3", new String[] {"2,x,x,1,3,4"}),
        new ChordDefinition(G, CHORD_SUS2, "5,x,0,0,3,5"), // (D  G  A): no 3rd but a 2nd from a major triad
        new ChordDefinition(G, CHORD_SUS2, "x,0,0,0,3,3"), // (D  G  A) : no 3rd but a 2nd from a major triad
        new ChordDefinition(G, CHORD_SUS2, "x,x,0,2,3,3"), // (D  G  A) : no 3rd but a 2nd from a major triad
        new ChordDefinition(G, CHORD_SUS4, "3,3,0,0,1,3", new String[] {"2,3,x,x,1,4"}),
        new ChordDefinition(G, CHORD_SUS4, "3,3,5,5,3,3", new String[] {"1,1,4,3,1,1"}),
        new ChordDefinition(G, CHORD_SUS4, "3,5,5,5,3,3", new String[] {"1,2,3,4,1,1", "x,2,3,4,1,1"}),
        new ChordDefinition(G, CHORD_SUS4, "x,10,12,12,13,3"), // (C  D  G): no 3rd but a 4th from a major triad
        new ChordDefinition(G, CHORD_SUS4, "x,3,0,0,3,3"), // (C  D  G) : no 3rd but a 4th from a major triad
        new ChordDefinition(G, CHORD_SUS4, "x,3,5,5,3,3"), // (C  D  G) : no 3rd but a 4th from a major triad
        new ChordDefinition(G, CHORD_SUS4, "x,5,5,5,3,3"), // (C  D  G) : no 3rd but a 4th from a major triad
        new ChordDefinition(G, E, CHORD_AUG, "3,x,1,0,0,0"), // (Eb E  G  B) : augmented triad (altered bass)
        new ChordDefinition(G, E, CHORD_AUG, "x,x,1,0,0,0"), // (Eb E  G  B) : augmented triad (altered bass)
        new ChordDefinition(G, E, CHORD_DIM, "x,1,2,0,2,0"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(G, E, CHORD_DIM, "x,x,2,3,2,3"), // (Db E  G  Bb) : diminished triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "0,2,0,0,0,0"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "0,2,0,0,3,0"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "0,2,2,0,3,0"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "0,2,2,0,3,3"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "0,x,0,0,0,0"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "x,10,12,12,12,0"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "x,x,0,12,12,12"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "x,x,0,9,8,7"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MAJ, "x,x,2,4,3,3"), // (D  E  G  B) : major triad (altered bass)
        new ChordDefinition(G, E, CHORD_MIN, "3,x,0,3,3,0"), // (D  E  G  Bb) : minor triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS2, "5,0,0,0,3,0"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS2, "x,0,2,0,3,0"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS2, "x,0,2,0,3,3"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS2, "x,0,2,2,3,3"), // (D  E  G  A) : sus2 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "3,x,0,0,1,0"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "x,10,12,12,13,0"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "x,3,0,0,1,0"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "x,3,2,0,3,0"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "x,3,2,0,3,3"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "x,5,5,5,x,0"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "x,x,0,0,1,0"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, E, CHORD_SUS4, "x,x,0,5,5,3"), // (C  D  E  G) : sus4 triad (altered bass)
        new ChordDefinition(G, Eb, CHORD_DIM, "x,1,1,3,2,3"), // (Db Eb G  Bb) : diminished triad (altered bass)
        new ChordDefinition(G, Eb, CHORD_DIM, "x,6,8,6,8,6"), // (Db Eb G  Bb) : diminished triad (altered bass)
        new ChordDefinition(G, Eb, CHORD_DIM, "x,x,1,3,2,3"), // (Db Eb G  Bb) : diminished triad (altered bass)
        new ChordDefinition(G, Eb, CHORD_MIN, "x,6,8,7,8,6"), // (D  Eb G  Bb) : minor triad (altered bass)
        new ChordDefinition(G, F, CHORD_MAJ, "1,x,0,0,0,3"), // (D  F  G  B) : major triad (altered bass)
        new ChordDefinition(G, F, CHORD_MAJ, "3,2,0,0,0,1"), // (D  F  G  B) : major triad (altered bass)
        new ChordDefinition(G, F, CHORD_MAJ, "x,x,0,0,0,1"), // (D  F  G  B) : major triad (altered bass)
        new ChordDefinition(G, F, CHORD_MIN, "3,5,3,3,3,3"), // (D  F  G  Bb) : minor triad (altered bass)
        new ChordDefinition(G, F, CHORD_MIN, "x,x,3,3,3,3"), // (D  F  G  Bb) : minor triad (altered bass)
        new ChordDefinition(G, F, CHORD_SUS4, "3,3,0,0,1,1"), // (C  D  F  G) : sus4 triad (altered bass)
        new ChordDefinition(G, Gb, CHORD_MAJ, "2,2,0,0,0,3"), // (D  Gb G  B) : major triad (altered bass)
        new ChordDefinition(G, Gb, CHORD_MAJ, "2,2,0,0,3,3"), // (D  Gb G  B) : major triad (altered bass)
        new ChordDefinition(G, Gb, CHORD_MAJ, "3,2,0,0,0,2"), // (D  Gb G  B) : major triad (altered bass)
        new ChordDefinition(G, Gb, CHORD_MAJ, "x,x,4,4,3,3"), // (D  Gb G  B) : major triad (altered bass)
        new ChordDefinition(G, Gb, CHORD_SUS2, "3,x,0,2,3,2"), // (D  Gb G  A) : sus2 triad (altered bass)
        new ChordDefinition(G, Gb, CHORD_SUS2, "5,x,4,0,3,5"), // (D  Gb G  A): sus2 triad (altered bass)
  
        new ChordDefinition(Gb, Ab, CHORD_MAJ, "x,x,4,3,2,4"), // (Db Gb Ab Bb) : major triad (altered bass)
        new ChordDefinition(Gb, Bb, CHORD_SUS2, "x,x,4,3,2,4"), // (Db Gb Ab Bb) : sus2 triad (altered bass)
        new ChordDefinition(Gb, CHORD_7, "2,4,2,3,2,2", new String[] {"1,3,1,2,1,1", "x,3,1,2,1,1"}),
        new ChordDefinition(Gb, CHORD_7, "x,x,4,3,2,0", new String[] {"x,x,3,2,1,x"}),
        new ChordDefinition(Gb, CHORD_7FLAT5, "2,x,2,3,1,x", new String[] {"x,x,3,4,1,x"}),
        new ChordDefinition(Gb, CHORD_7FLAT5, "x,x,4,3,1,0", new String[] {"x,x,4,3,1,x"}),
        new ChordDefinition(Gb, CHORD_7SHARP5, "x,x,4,3,3,0", new String[] {"x,x,3,1,2,x"}),
        new ChordDefinition(Gb, CHORD_ADD9, "x,x,4,3,2,4"), // (Db Gb Ab Bb) : major triad plus 9th
        new ChordDefinition(Gb, CHORD_AUG, "x,x,0,3,3,2", new String[] {"x,x,x,2,3,1"}),
        new ChordDefinition(Gb, CHORD_AUG, "x,x,4,3,3,2", new String[] {"x,x,4,2,3,1"}),
        new ChordDefinition(Gb, CHORD_DIM, "2,x,x,2,1,2", new String[] {"2,x,x,3,1,4"}),
        new ChordDefinition(Gb, CHORD_DIM7, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad, diminished 7th
        new ChordDefinition(Gb, CHORD_MAJ, "2,4,4,3,2,2", new String[] {"1,3,4,2,1,1", "x,3,4,2,1,1"}),
        new ChordDefinition(Gb, CHORD_MAJ, "x,4,4,3,2,2"), // (Db Gb Bb) : major triad
        new ChordDefinition(Gb, CHORD_MAJ, "x,x,4,3,2,2"), // (Db Gb Bb) : major triad
        new ChordDefinition(Gb, CHORD_MAJ6, "x,x,1,3,2,2"), // (Db Eb Gb Bb) : major triad plus 6th
        new ChordDefinition(Gb, CHORD_MAJ7, "x,x,3,3,2,2"), // (Db F  Gb Bb) : major triad, major 7th
        new ChordDefinition(Gb, CHORD_MIN, "2,4,4,2,2,2", new String[] {"1,3,4,1,1,1", "x,3,4,1,1,1"}),
        new ChordDefinition(Gb, CHORD_MIN, "x,4,4,2,2,2"), // (Db Gb A) : minor triad
        new ChordDefinition(Gb, CHORD_MIN, "x,x,4,2,2,2"), // (Db Gb A) : minor triad
        new ChordDefinition(Gb, CHORD_MIN7, "0,0,2,2,2,2"), // (Db E  Gb A) : minor triad, minor 7th
        new ChordDefinition(Gb, CHORD_MIN7, "0,x,4,2,2,0"), // (Db E  Gb A) : minor triad, minor 7th
        new ChordDefinition(Gb, CHORD_MIN7, "2,4,2,2,2,2", new String[] {"1,3,1,1,1,1"}),
        new ChordDefinition(Gb, CHORD_MIN7, "2,4,4,2,5,2", new String[] {"x,3,4,1,4,1"}),
        new ChordDefinition(Gb, CHORD_MIN7, "2,x,2,2,2,0"), // (Db E  Gb A) : minor triad, minor 7th
        new ChordDefinition(Gb, CHORD_MIN7, "x,0,4,2,2,0"), // (Db E  Gb A) : minor triad, minor 7th
        new ChordDefinition(Gb, CHORD_MIN7, "x,x,2,2,2,2"), // (Db E  Gb A) : minor triad, minor 7th
        new ChordDefinition(Gb, CHORD_MIN7FLAT5, "2,x,2,2,1,x", new String[] {"2,x,3,4,1,x"}),
        new ChordDefinition(Gb, CHORD_MIN7FLAT5, "x,x,2,2,1,2", new String[] {"x,x,2,3,1,4"}),
        new ChordDefinition(Gb, CHORD_MIN7FLAT5, "x,x,4,5,5,5", new String[] {"x,x,1,3,3,3"}),
        new ChordDefinition(Gb, CHORD_MIN7SUS4, "x,4,4,4,x,0"), // (Db E  Gb B) : sus4 triad, minor 7th
        new ChordDefinition(Gb, CHORD_SUS2, "2,x,x,1,2,2", new String[] {"2,x,x,1,3,4", "x,x,x,1,3,4"}),
        new ChordDefinition(Gb, CHORD_SUS4, "2,2,4,4,2,2", new String[] {"1,1,4,3,1,1", "1,2,3,4,1,1"}),
        new ChordDefinition(Gb, CHORD_SUS4, "x,4,4,4,2,2"), // (Db Gb B) : no 3rd but a 4th from a major triad
        new ChordDefinition(Gb, D, CHORD_DIM, "x,0,0,2,1,2"), // (C  D  Gb A) : diminished triad (altered bass)
        new ChordDefinition(Gb, D, CHORD_DIM, "x,3,x,2,3,2"), // (C  D  Gb A) : diminished triad (altered bass)
        new ChordDefinition(Gb, D, CHORD_DIM, "x,5,7,5,7,2"), // (C  D  Gb A): diminished triad (altered bass)
        new ChordDefinition(Gb, D, CHORD_DIM, "x,5,7,5,7,5"), // (C  D  Gb A) : diminished triad (altered bass)
        new ChordDefinition(Gb, D, CHORD_MIN, "x,x,0,14,14,14"), // (Db D  Gb A) : minor triad (altered bass)
        new ChordDefinition(Gb, D, CHORD_MIN, "x,x,0,2,2,2"), // (Db D  Gb A) : minor triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_AUG, "2,x,4,3,3,0"), // (D  E  Gb Bb) : augmented triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_DIM, "x,0,2,2,1,2"), // (C  E  Gb A) : diminished triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_DIM, "x,x,2,2,1,2"), // (C  E  Gb A) : diminished triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_MAJ, "2,4,2,3,2,2"), // (Db E  Gb Bb) : major triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_MAJ, "x,x,4,3,2,0"), // (Db E  Gb Bb) : major triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_MIN, "0,0,2,2,2,2"), // (Db E  Gb A) : minor triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_MIN, "0,x,4,2,2,0"), // (Db E  Gb A) : minor triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_MIN, "2,x,2,2,2,0"), // (Db E  Gb A) : minor triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_MIN, "x,0,4,2,2,0"), // (Db E  Gb A) : minor triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_MIN, "x,x,2,2,2,2"), // (Db E  Gb A) : minor triad (altered bass)
        new ChordDefinition(Gb, E, CHORD_SUS4, "x,4,4,4,x,0"), // (Db E  Gb B) : sus4 triad (altered bass)
        new ChordDefinition(Gb, Eb, CHORD_DIM, "x,x,1,2,1,2"), // (C  Eb Gb A) : diminished triad (altered bass)
        new ChordDefinition(Gb, Eb, CHORD_MAJ, "x,x,1,3,2,2"), // (Db Eb Gb Bb) : major triad (altered bass)
        new ChordDefinition(Gb, F, CHORD_MAJ, "x,x,3,3,2,2") // (Db F  Gb Bb) : major triad (altered bass)
    )
  );
  //@formatter:on

  public static List<ChordDefinition> findChordDefinitions(
      InstrumentDefinition instrumentDefinition) {
    return definitions.get(instrumentDefinition);
  }

  public static List<ChordDefinition> findChordDefinitions(
      InstrumentDefinition instrumentDefinition, Note chordRoot) {
    return definitions.get(instrumentDefinition).stream()
        .filter(ce -> ce.getChordRoot().getPitch() == chordRoot.getPitch())
        .collect(Collectors.toList());
  }

  public static List<ChordDefinition> findChordDefinitions(
      InstrumentDefinition instrumentDefinition, Note chordRoot, IntervalPattern chordPattern,
      Note addedNote) {
    return definitions.get(instrumentDefinition).stream().filter(cd -> {
      return cd.getChordRoot().getPitch() == chordRoot.getPitch()
          && cd.getChordPattern() == chordPattern
          && (addedNote == null ? (cd.getAddedNote() == null ? true : false)
              : (cd.getAddedNote() == null ? false
                  : cd.getAddedNote().getPitch() == addedNote.getPitch()));
    }).collect(Collectors.toList());
  }

  public static class ChordDefinition {
    @Getter
    private Note chordRoot;
    @Getter
    private Note addedNote;
    @Getter
    private IntervalPattern chordPattern;
    @Getter
    private List<String> strings;
    @Getter
    private List<String> fingers;

    public ChordDefinition(Note chordRoot, IntervalPattern chordPattern, String strings,
        String[] fingers) {
      this(chordRoot, null, chordPattern, strings, fingers);
    }

    public ChordDefinition(Note chordRoot, IntervalPattern chordPattern, String strings) {
      this(chordRoot, null, chordPattern, strings, null);
    }

    public ChordDefinition(Note chordRoot, Note addedNote, IntervalPattern chordPattern,
        String strings) {
      this(chordRoot, addedNote, chordPattern, strings, null);
    }

    public ChordDefinition(Note chordRoot, Note addedNote, IntervalPattern chordPattern,
        String strings, String[] fingers) {
      this.chordRoot = chordRoot;
      this.addedNote = addedNote;
      this.chordPattern = chordPattern;
      this.strings = Arrays.asList(strings.split(","));
      // this.fingers = Arrays.asList(fingers != null ? fingers.split("") : new String[] {});
    }

  }
}

