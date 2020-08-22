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
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Pitch;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
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

  @Option(names = {"-v", "--verbose"},
      description = "if you want some background to Frettlers application of music theory")
  boolean verbose = false;

  @Option(names = {"-m", "--mono"}, description = "Display in 'monochrome'")
  @Getter
  protected boolean mono;

  @Option(names = {"-r", "--rule"}, defaultValue="STRICT", description = "Chord lookup can be strict, relaxed or loose")
  @Getter
  protected Rule rule;

  @Override
  public void run() {
    switch (rule) {
      case STRICT:
        Optional<Chord> chord = Chord.findChord(notes);
        out.println(chord.isPresent() ? chord.get().getTitle() : "Could not find matching chord");
        break;
      case RELAXED:
        List<Chord> relaxedChords = Chord.findChords(notes);
        relaxedChords.forEach(c -> out.println(c.getTitle()));
        break;
      case LOOSE:
        List<Chord> looseChords = Chord.findAllChords(notes);
        looseChords.forEach(c -> out.println(c.getTitle()));
        break;
    }
  }

  public enum Rule {
    STRICT, RELAXED, LOOSE;
  }
}
