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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import me.flotsam.frettler.engine.LineOfFifths;
import me.flotsam.frettler.engine.LineOfFifths.LineEntry;
import me.flotsam.frettler.engine.Note;
import picocli.CommandLine.Command;

/**
 * Handles the chord analysis of a set of provided notes
 * 
 * @author philwhiles
 *
 */
@Command(name = "fifths", description = "Displays Frettlers Line Of Fifths")
public class FifthsCommand implements Runnable {


  @Override
  public void run() {
    LinkedHashMap<Note, LineEntry> majorLine = LineOfFifths.getMajorLine();
    LinkedHashMap<Note, LineEntry> minorLine = LineOfFifths.getMinorLine();
    LinkedHashMap<Note, LineEntry> dimLine = LineOfFifths.getDimLine();
    List<Note> majorLineNotes = LineOfFifths.getMajorLine().keySet().stream().collect(Collectors.toList());
    
    for (Note majorNote : majorLineNotes) {
      LineEntry currentMajorLine = majorLine.get(majorNote);
      System.out.print (majorNote.getLabel() + "maj [");
      System.out.print(currentMajorLine.getAccidentals().stream().map(Note::getLabel).collect(Collectors.joining(", ")));
      System.out.println("]");

      System.out.println (currentMajorLine.getMinor().getLabel() + "min");
      System.out.println (currentMajorLine.getDim().getLabel() + "dim");
      System.out.println();
    }
  }

}
