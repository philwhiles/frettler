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

package me.flotsam.frettler.command;

import static java.lang.System.out;
import java.util.Arrays;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.IntervalPattern.PatternType;
import me.flotsam.frettler.engine.Note;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Handles the chord analysis of a set of provided notes
 * 
 * @author philwhiles
 *
 */
@Command(name = "chord", description = "Analyses the given notes and attempts to name that chord")
public class ChordCommand implements Runnable {


  @Parameters(index = "0", description = "The chord notes to analyse", split = ",")
  Note[] notes = new Note[] {};

  @Override
  public void run() {
    for (Note note : notes) {
      for (IntervalPattern pattern : IntervalPattern.values()) {
        if (pattern.getPatternType() != PatternType.CHORD) {
          continue;
        }
        Chord candidate = new Chord(note, pattern);
        if (candidate.containsOnlyNotes(notes)) {
          out.println(candidate.getTitle());
        }
      }
    }
  }
}
