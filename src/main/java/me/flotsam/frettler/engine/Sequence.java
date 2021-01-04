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

import static me.flotsam.frettler.engine.IntervalPattern.PatternType.CHORD;
import static me.flotsam.frettler.engine.IntervalPattern.PatternType.SCALE;

public enum Sequence {
  //@formatter:off


  NONE(SCALE, 2, "DO NOT CHOOSE THIS SEQUENCE",  1,2,3,4,5,6,7),
  PENTATONIC_1(SCALE, 2, "Pentatonic Pattern No. 1",  1,2,3,4,5),
  PENTATONIC_2(SCALE, 2, "Pentatonic Pattern No. 2",  2,3,4,5,1),
  PENTATONIC_3(SCALE, 2, "Pentatonic Pattern No. 3",  3,4,5,1,2),
  PENTATONIC_4(SCALE, 2, "Pentatonic Pattern No. 4",  4,5,1,2,3),
  PENTATONIC_5(SCALE, 2, "Pentatonic Pattern No. 5",  5,1,2,3,4);

//  CHORD_TRIAD(CHORD, 3, "3 Note Chord",  1,2,3),
//
//  CHORD_TETRAD(CHORD, 4, "4 Note Chord",  1,2,3,4),
//
//  DIATONIC_SECONDS(SCALE, 2, "Diatonic Seconds",  1,2,2,3,3,4,4,5,5,6,6,7,7,1),
//
//  // Diatonic Thirds: 1-3, 2-4, 3-5, 4-6, 5-7, 6-1, 7-2, 1-3
//  DIATONIC_THIRDS(SCALE, 2, "Diatonic Thirds",  1,3,2,4,3,5,4,6,5,7,6,1,7,2,1,3),
//
//  // Diatonic Fourths: 1-4, 2-5, 3-6, 4-7, 5-1, 6-2, 7-3, 1-4
//  DIATONIC_FOURTHS_1(SCALE, 2, "Diatonic Fourths No. 1",  1,4,2,5,3,6,4,7,5,1,6,2,7,3,1,4),
//
//  // Diatonic Fourths: 1-5, 7-4, 6-3, 5-2, 4-1, 3-7, 2-6
//  DIATONIC_FOURTHS_2(SCALE, 2, "Diatonic Fourths No. 2",  1,5,7,4,6,3,5,2,4,1,3,7,2,6),
//
//  // Diatonic Fifths: 1-5, 2-6, 3-7, 4-1, 5-2, 6-3, 7-4, 1-5
//  DIATONIC_FIFTHS_1(SCALE, 2, "Diatonic Fifths No. 1",  1,5,2,6,3,7,4,1,5,2,6,3,7,4,1,5),
//
//  // Diatonic Fifths: 1-4-3-7, 6-2-1-5, 4-7-6-3, 2-5-4-1
//  DIATONIC_FIFTHS_2(SCALE, 4, "Diatonic Fifths No. 2",  1,4,3,7,6,2,1,5,4,7,6,3,2,5,4,1),
//
//  // Diatonic Sixths: 1-6, 2-7, 3-1, 4-2, 5-3, 6-4, 7-5, 1-6
//  DIATONIC_SIXTHS(SCALE, 2, "Diatonic Sixths",  1,6,2,7,3,1,4,2,5,3,6,4,7,5,1,6),
//
//  // Diatonic Sevenths: 1-7, 2-(SCALE, 8), 3-2, 4-3, 5-4, 6-5, 7-6, 1-7
//  DIATONIC_SEVENTHS(SCALE, 2, "Diatonic Sevenths",  1,7,2,8,3,2,4,3,5,4,6,5,7,6,1,7),
//
//  // Diatonic Octaves: 1-(SCALE, 8), 2-9, 3-10, 4-11, 5-12, 6-13, 7-14, 8-15
//  DIATONIC_OCTAVES(SCALE, 2, "Diatonic Octaves",  1,8,2,9,3,10,4,11,5,12,6,13,7,14,8,15),
//
//  // Diatonic Thirds Sawtooth: 1-3-4-2, 3-5-6-4, 5-7-1-6, 7-2-3-1
//  DIATONIC_THIRDS_SAWTOOTH(SCALE, 4, "Diatonic Thirds Sawtooth",  1,3,4,2,3,5,6,4,5,7,1,6,7,2,3,1),
//
//
//  // Common Scale Sequences:
//
//  // Four Note Sequence No. 1: 1-2-3-4, 2-3-4-5, 3-4-5-6, 4-5-6-7, 5-6-7-1, 6-7-1-2, 7-1-2-3, 1-2-3-4
//  FOUR_NOTE(SCALE, 4, "Four Note Sequence No. 1",  1,2,3,4,2,3,4,5,3,4,5,6,4,5,6,7,5,6,7,1,6,7,1,2,7,1,2,3,1,2,3,4),
//
//  // Three Note Sequence No. 1: 1-2-3, 2-3-4, 3-4-5, 4-5-6, 5-6-7, 6-7-1, 7-1-2, 1-2-3
//  THREE_NOTE(SCALE, 3, "Three Note Sequence No. 1",  1,2,3,2,3,4,3,4,5,4,5,6,5,6,7,6,7,1,7,1,2,1,2,3),
//
//  // Triplet Pattern No. 1: 1-2-1, 2-3-2, 3-4-3, 4-5-4, 5-6-5, 6-7-6, 7-1-7
//  TRIPLET_1(SCALE, 3, "Triplet Pattern No. 1",  1,2,1,2,3,2,3,4,3,4,5,4,5,6,5,6,7,6,7,1,7),
//
//  // Triplet Pattern No. 2: 1-7-6, 7-6-5, 6-5-4, 5-4-3, 4-3-2, 3-2-1
//  TRIPLET_2(SCALE, 3, "Triplet Pattern No. 2",  1,7,6,7,6,5,6,5,4,5,4,3,4,3,2,3,2,1),
//
//  // Triad Arpeggio Sequence No. 1: 1-3-5, 2-4-6, 3-5-7, 4-6-1, 5-7-2, 6-1-3, 7-2-4
//  TRIAD_ARPEGGIO_1(SCALE, 3, "Triad Arpeggio Sequence No. 1",  1,3,5,2,4,6,3,5,7,4,6,1,5,7,2,6,1,3,7,2,4),
//
//  // Triad Arpeggio Sequence No. 2: 1-3-5, 6-4-2, 3-5-7, 1-6-4, 5-7-2, 3-1-6, 7-2-4
//  TRIAD_ARPEGGIO_2(SCALE, 3, "Triad Arpeggio Sequence No. 2",  1,3,5,6,4,2,3,5,7,1,6,4,5,7,2,3,1,6,7,2,4),
//
//  // Four Note Arpeggio Sequence No. 1: 1-3-5-7, 2-4-6-1, 3-5-7-2, 4-6-1-3, 5-7-2-4, 6-1-3-5, 7-2-4-6, 1-3-5-7
//  TETRAD_ARPEGGIO_1(SCALE, 4, "Four Note Arpeggio Sequence No. 1",  1,3,5,7,2,4,6,1,3,5,7,2,4,6,1,3,5,7,2,4,6,1,3,5,7,2,4,6,1,3,5,7),
//
//  // Four Note Arpeggio Sequence No. 2 (SCALE, bebop pattern): 1-3-5-7, 1-6-4-2, 3-5-7-2, 3-1-6-4, 5-7-2-4, 5-3-1-6, 7-2-4-6, 7-5-3-1
//  TETRAD_ARPEGGIO_2 (SCALE, 4, "Four Note Arpeggio Sequence No. 2 (bebop pattern)",  1,3,5,7,1,6,4,2,3,5,7,2,3,1,6,4,5,7,2,4,5,3,1,6,7,2,4,6,7,5,3,1),
//
//
//  // Use these patterns for any 5-note scale eg Major and Minor Pentatonic with (SCALE, Scale Degrees [1,2,3,4,5])
//  // Scale Sequences using the Major Pentatonic Scale (SCALE, Scale degrees: 1, 2, 3, 5, 6 of the Major scale) 
//  // Scale Sequences using the Minor Pentatonic Scale  (Scale degrees: 1, b3, 4, 5, b7 of the major scale)
//
//  // Major Pentatonic Pattern No. 1: 1-2, 2-3, 3-5, 5-6, 6-1 / # Minor Pentatonic Pattern No. 1: 1-b3, b3-4, 4-5, 5-b7, b7-1
//  PENTATONIC_1(SCALE, 2, "Pentatonic Pattern No. 1",  1,2,2,3,3,4,4,5,5,1),
//
//  // Major Pentatonic Pattern No. 2: 1-3, 2-5, 3-6, 5-1, 6-2, 1-3 / # Minor Pentatonic Pattern No. 2: 1-4, b3-5, 4-b7, 5-1, b7-b3, 1-4
//  PENTATONIC_2(SCALE, 2, "Pentatonic Pattern No. 2",  3,2,4,3,5,4,1,5,2,1,3),
//
//  // Major Pentatonic Pattern No. 3: 1-5, 2-6, 3-1, 5-2, 6-3, 1-5 / # Minor Pentatonic Pattern No. 3: 1-5, b3-b7, 4-1, 5-b3, b7-4, 1-5
//  PENTATONIC_3(SCALE, 2, "Pentatonic Pattern No. 3",  1,4,2,5,3,1,4,2,5,3,1,4),
//
//  // Major Pentatonic Pattern No. 4: 1-6, 2-1, 3-2, 5-3, 6-5, 1-6 / # Minor Pentatonic Pattern No. 3: 1-b7, b3-1, 4-b3, 5-4, b7-5, 1-b7
//  PENTATONIC_4(SCALE, 2, "Pentatonic Pattern No. 4",  1,5,2,1,3,2,4,3,5,4,1,5),
//
//  // Major Pentatonic Pattern No. 5: 1-3-6, 2-5-1, 3-6-2, 5-1-3, 6-2-5, 1-3-6 / # Minor Pentatonic Pattern No. 4: 1-4-b7, b3-5-1, 4-b7-b3, 5-1-4, b7-b3-5, 1-4-b7
//  PENTATONIC_5(SCALE, 3, "Pentatonic Pattern No. 4",  1,3,5,2,4,1,3,5,2,4,1,3,5,2,4),
//
//  // Major Pentatonic Pattern No. 6: 1-2-3-5, 3-5-6-1, 6-1-2-3, 2-3-5-6, 5-6-1-2 / # Minor Pentatonic Pattern No. 6: 1-b3-4-5, 4-5-b7-1, b7-1-b3-4, b3-4-5-b7, 5-b7-1-b3
//  PENTATONIC_6(SCALE, 4, "Pentatonic Pattern No. 5",  1,2,3,4,3,4,5,1,5,1,2,3,2,3,4,5,4,5,1,2),
//
//  // Major Pentatonic Pattern No. 7: 1-2-3-5-6-1, 3-5-6-1-2-3, 6-1-2-3-5-6, 2-3-5-6-1-2 / # Minor Pentatonic Pattern No. 7: 1-b3-4-5-b7-1, 4-5-b7-1-b3-4, b7-1-b3-4-5-b7, b3-4-5-b7-1-b3
//  PENTATONIC_7(SCALE, 6, "Pentatonic Pattern No. 7",  1,2,3,4,5,1,3,4,5,1,2,3,5,1,2,3,4,5,2,3,4,5,1,2),
//
//  // Major Pentatonic Pattern No. 8: 1-5-6-3, 3-1-2-6, 6-3-5-2 / # Minor Pentatonic Pattern No. 8: 1-5-b7-4, 4-1-b3-b7, b7-4-5-b3
//  PENTATONIC_8(SCALE, 4, "Pentatonic Pattern No. 8",  1,4,5,3,3,1,2,5,5,3,4,2),
//
//
//
//  // Major Pentatonic Four Note Sequence No. 1: 1-2-3-5, 2-3-5-6, 3-5-6-1, 5-6-1-2, 6-1-2-3 / # Minor Pentatonic Four Note Sequence No. 1: 1-b3-4-5, b3-4-5-b7, 4-5-b7-1, 5-b7-1-b3, b7-1-b3-4
//  PENTATONIC_FOUR_NOTE_1(SCALE, 4, "Pentatonic Four Note Sequence No. 1",  1,2,3,4,2,3,4,5,3,4,5,1,4,5,1,2,5,1,2,3),
//
//  // Major Pentatonic Three Note Sequence No. 1: 1-2-3, 2-3-5, 3-5-6, 5-6-1, 6-1-2, 1-2-3 / # Minor Pentatonic Three Note Sequence No. 1: 1-b3-4, b3-4-5, 4-5-b7, 5-b7-1, b7-1-b3, 1-b3-4
//  PENTATONIC_THREE_NOTE_1(SCALE, 3, "Pentatonic Three Note Sequence No. 1",  1,2,3,2,3,4,3,4,5,4,5,1,5,1,2,1,2,3),
//
//  // Major Pentatonic Triplet Pattern No. 1: 1-2-1, 2-3-2, 3-5-3, 5-6-5, 6-1-6, 1-2-1 / # Minor Pentatonic Triplet Pattern No. 1: 1-b3-1, b3-4-b3, 4-5-4, 5-b7-5, b7-1-b7, 1-b3-1
//  PENTATONIC_TRIPLET_1(SCALE, 3, "Pentatonic Triplet Pattern No. 1",  1,2,1,2,3,2,3,4,3,4,5,4,5,1,5,1,2,1),
//
//  // Major Pentatonic Three Note Arpeggio Sequence No. 1: 1-3-5, 2-5-6, 3-6-1, 5-1-2, 6-2-3, 1-3-5 / # Minor Pentatonic Three Note Arpeggio Sequence No. 1: 1-4-5, b3-5-b7, 4-b7-1, 5-1-b3, b7-b3-4, 1-4-5
//  PENTATONIC_THREE_NOTE_ARPEGGIO_1(SCALE, 3, "Pentatonic Three Note Arpeggio Sequence No. 1",  1,3,4,2,4,5,3,5,1,4,1,2,5,2,3,1,3,4),
//
//  // Major Pentatonic Three Note Arpeggio Sequence No. 2: 1-5-3, 6-1-5, 5-2-6, 6-3-1, 1-5-2, 2-6-3, 3-1-5 / # Minor Pentatonic Three Note Arpeggio Sequence No. 2: 1-5-4, b7-1-5, 5-b3-b7, b7-4-1, 1-5-b3, b3-b7-4, 4-1-5
//  PENTATONIC_THREE_NOTE_ARPEGGIO_2(SCALE, 3, "Pentatonic Three Note Arpeggio Sequence No. 2",  1,4,3,5,1,4,4,2,5,5,3,1,1,4,2,2,5,3,3,1,4),
//
//  // Major Pentatonic Four Note Sequence No. 1: 1-3-5-6, 1-6-5-3, 2-5-6-1, 2-1-6-5, 3-6-1-2, 3-2-1-6, 5-1-2-3, 5-3-2-1, 6-2-3-5, 6-5-3-2, 1-3-5-6 / # Minor Pentatonic Four Note Sequence No. 1: 1-4-5-b7, 1-b7-5-4, b3-5-b7-1, b3-1-b7-5, 4-b7-1-b3, 4-b3-1-b7, 5-1-b3-4, 5-4-b3-1, b7-b3-4-5, b7-5-4-b3, 1-4-5-b7
//  PENTATONIC_FOUR_NOTE_2(SCALE, 4, "Pentatonic Four Note Sequence No. 1",  1,3,4,5,1,5,4,3,2,4,5,1,2,1,5,4,3,5,1,2,3,2,1,5,4,1,2,3,4,3,2,1,5,2,3,4,5,4,3,2,1,3,4,5);
//

 
  
  //@formatter:on

  @Getter
  private int[] sequence;
  @Getter
  private String label;
  @Getter
  private int grouping;
  @Getter
  IntervalPattern.PatternType intendedPatternType;
  
  private Sequence(IntervalPattern.PatternType intendedPatterType, int grouping, String label, int ... sequence) {
    this.intendedPatternType = intendedPatterType;
    this.label = label;
    this.grouping = grouping;
    this.sequence = sequence;
  }
}
