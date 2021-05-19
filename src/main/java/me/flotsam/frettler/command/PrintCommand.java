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
import java.util.stream.Collectors;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Prints out the calculated scale or chord, no instrument
 * 
 * @author philwhiles
 *
 */
@Command(name = "print", description = "Calculates the requested scale or chord and prints it out")
public class PrintCommand implements Runnable {

  @Parameters(index = "0", defaultValue = "C", description = "The root/tonic of the chord or scale")
  Note root;

  @Parameters(index = "1", defaultValue = "SCALE_MAJOR",
      description = "The interval pattern to use")
  IntervalPattern intervalPattern;
  
  @Override
  public void run() {
    if (intervalPattern.getPatternType() != IntervalPattern.PatternType.CHORD) {
      Scale scale = new Scale(this.root, this.intervalPattern);
      out.println(scale.getScaleNotes().stream().map(n->n.toString()).collect(Collectors.joining(", ")));
    } else {
      Chord chord = new Chord(this.root, this.intervalPattern, null);
      out.println(chord.getChordNotes().stream().map(n->n.toString()).collect(Collectors.joining(", ")));
    }
  }
}
