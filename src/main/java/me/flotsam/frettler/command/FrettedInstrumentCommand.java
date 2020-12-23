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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.ChordBank;
import me.flotsam.frettler.engine.ChordBank.ChordDefinition;
import me.flotsam.frettler.engine.ChordBankInstance;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScaleNote;
import me.flotsam.frettler.instrument.Banjo;
import me.flotsam.frettler.instrument.FrettedInstrument;
import me.flotsam.frettler.view.Colour;
import me.flotsam.frettler.view.ColourMap;
import me.flotsam.frettler.view.HorizontalView;
import me.flotsam.frettler.view.TabView;
import me.flotsam.frettler.view.TabView.Options;
import me.flotsam.frettler.view.VerticalView;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Base class that handles the initial instrument command param
 * 
 * @author philwhiles
 *
 */
@Command
public abstract class FrettedInstrumentCommand extends FrettlerCommand implements Runnable {

  @Option(names = {"-n", "--notes"}, description = "The chord notes to find", split = ",")
  Note[] notes = new Note[] {};

  @Option(names = {"-c", "--chords"}, description = "chord mode (view dependant)")
  boolean chordMode = false;

  @Option(names = {"-s", "--strings"}, split = ",", paramLabel = "note",
      description = "comma separated list of string tunings ie E,A,D,G,B,E")
  Note[] strings = new Note[] {};

  @Option(names = {"-f", "--frets"}, paramLabel = "num",
      description = "overrides the default 12 frets displayed")
  Integer frets = 12;

  @Option(names = {"-v", "--verbose"},
      description = "use if you want some background to Frettlers application of music theory")
  boolean verbose = false;

  public void exec(FrettedInstrument instrument) {

    Scale scale = null;
    Chord chord = null;

    if (instrument instanceof Banjo && isOctaves()) {
      out.println(
          "Sorry - haven't worked out how to handle that 5th string in octave calculation - yet");
      return;
    }

    switch (this.view.getType()) {
      case SEQUENCE:
        TabView tabView = new TabView(instrument);
        TabView.Options tabViewOptions =
            tabView.new Options(intervals, true, !isMono(), isOctaves());

        if (intervalPattern.getPatternType() != IntervalPattern.PatternType.CHORD) {
          scale = new Scale(this.root, this.intervalPattern);
          tabView.showScale(scale, tabViewOptions);
          List<Chord> chords = new ArrayList<>();
          if (chordMode) {
            chords = scale.createScaleChords();
          }
          if (verbose) {
            explain(scale, chords);
          } else {
            for (Chord aChord : chords) {
              out.println(String.format("%s%s%s", (isMono() ? "" : Colour.GREEN), aChord.getTitle(),
                  Colour.RESET));
            }
            out.println();
          }
        } else {
          chord = new Chord(this.root, this.intervalPattern);
          tabView.showChord(chord, tabViewOptions);
          if (verbose) {
            out.println(chord.describe(isMono()));
          }
        }
        break;

      case HORIZONTAL:
        HorizontalView horizontalView = new HorizontalView(instrument);
        HorizontalView.Options horizontalViewOptions =
            horizontalView.new Options(intervals, true, !isMono(), isOctaves());

        if (intervalPattern.getPatternType() != IntervalPattern.PatternType.CHORD) {
          scale = new Scale(this.root, this.intervalPattern);
          horizontalView.showScale(scale, horizontalViewOptions);
          List<Chord> chords = new ArrayList<>();
          if (chordMode) {
            chords = scale.createScaleChords();
          }
          if (verbose) {
            explain(scale, chords);
          } else {
            for (Chord aChord : chords) {
              out.println(String.format("%s%s%s", (isMono() ? "" : Colour.GREEN), aChord.getTitle(),
                  Colour.RESET));
            }
            out.println();
          }
        } else {
          chord = new Chord(this.root, this.intervalPattern);
          horizontalView.showChord(chord, horizontalViewOptions);
          if (verbose) {
            out.println(chord.describe(isMono()));
          }
        }
        break;

      case VERTICAL:
        VerticalView verticalView = new VerticalView(instrument);
        VerticalView.Options verticalViewOptions =
            verticalView.new Options(intervals, !isMono(), isOctaves());

        if (intervalPattern.getPatternType() != IntervalPattern.PatternType.CHORD) {
          scale = new Scale(this.root, this.intervalPattern);
          verticalView.showScale(scale, verticalViewOptions);
          List<Chord> chords = new ArrayList<>();
          if (chordMode) {
            chords = scale.createScaleChords();
          }
          if (verbose) {
            explain(scale, chords);
          } else {
            for (Chord aChord : chords) {
              out.println(String.format("%s%s%s", (isMono() ? "" : Colour.GREEN), aChord.getTitle(),
                  Colour.RESET));
            }
            out.println();
          }
        } else {
          chord = new Chord(this.root, this.intervalPattern);
          verticalView.showArpeggio(chord, verticalViewOptions);
          if (verbose) {
            out.println(chord.describe(isMono()));
          }
        }
        break;

      case DISPLAY:
        HorizontalView finderView = new HorizontalView(instrument);
        HorizontalView.Options finderViewOptions =
            finderView.new Options(false, true, !isMono(), isOctaves());
        Scale arbitraryScale = new Scale(
            Arrays.asList(notes).stream().map(n -> n.getPitch()).collect(Collectors.toList()));
        out.println();
        finderView.display(arbitraryScale.getScaleNotes(), finderViewOptions);
        break;

      case FIND:
        VerticalView findView = new VerticalView(instrument);
        VerticalView.Options findViewOptions =
            findView.new Options(intervals, !isMono(), isOctaves());
        Optional<Chord> foundChordOpt = Chord.findChord(notes);
        if (foundChordOpt.isPresent()) {
          findView.showArpeggio(foundChordOpt.get(), findViewOptions);
          if (verbose) {
            out.println(foundChordOpt.get().describe(isMono()));
          }
        } else {
          out.println("Could not find matching chord");
        }
        break;

      case CHORD:
        VerticalView chordView = new VerticalView(instrument);
        VerticalView.Options chordViewOptions =
            chordView.new Options(intervals, !isMono(), isOctaves());

        Optional<FrettedInstrument.InstrumentDefinition> optInstrument =
            FrettedInstrument.InstrumentDefinition.findInstrument(instrument.getInstrumentType(),
                instrument.getStringNotes());
        if (optInstrument.isEmpty()) {
          out.println("The current instrument is not currently defined for chords");
          return;
        } else {
          FrettedInstrument.InstrumentDefinition instrumentDefinition = optInstrument.get();

          // a bit hacky but skip the interval and it defaults to a non chord interval so...
          if (intervalPattern == IntervalPattern.SCALE_MAJOR) {
            ChordBank.findChordDefinitions(instrumentDefinition, root).stream().map(cd->cd.getChordPattern()).distinct().forEach(ip->out.println(ip.toString().toLowerCase()));
            return;
          }

          List<ChordDefinition> chordDefinitions =
              ChordBank.findChordDefinitions(instrumentDefinition, root, intervalPattern);
          if (chordDefinitions.size() == 0) {
            out.println("Don't have that chord definition - why not contribute it?");
            out.println("Send me the instrument/tuning/fretNumbering and I will add it for you");
            out.println("ie GUITAR/EADGBE/A/CHORD_MIN/x0121x - Share and enjoy!");
            out.println("phil.whiles@gmail.com");
            return;
          } else {
            ChordDefinition lastDef = null;
            for (ChordDefinition chordDefinition : chordDefinitions) {
              // until we handle fingerings skip dupes
              if (lastDef != null && lastDef.getStrings().equals(chordDefinition.getStrings())) {
                continue;
              }
              lastDef = chordDefinition;
              chord = new Chord(root, intervalPattern);
              ChordBankInstance chordBankInstance = new ChordBankInstance(chord, chordDefinition);
              chordView.showChord(chordBankInstance, chordViewOptions);
              if (verbose) {
                out.println(chord.describe(isMono()));
              }
            }
          }
        }
        break;

      default:
        break;
    }
  }

  private void explain(Scale scale, List<Chord> chords) {
    out.println("The scale is :");
    out.println(scale.describe(isMono()));
    out.println(
        "\n┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈\n");
    for (Chord aChord : chords) {
      StringBuilder sb = new StringBuilder("Take " + colourNote(aChord.getChordRootNote().getNote())
          + " and the following 2 alternate notes from the source scale:\n\n          ");
      Note[] chordNotes = aChord.getChordNotes().stream().map(sn -> sn.getNote())
          .collect(Collectors.toList()).toArray(new Note[] {});
      List<ScaleNote> scaleNotesTwice = new ArrayList<>();
      scaleNotesTwice.addAll(scale.getScaleNotes());
      scaleNotesTwice.addAll(scale.getScaleNotes());
      int chordNoteIdx = 0;

      for (ScaleNote scaleNote : scaleNotesTwice) {
        boolean isChordNote = false;
        if (chordNoteIdx < chordNotes.length) {
          isChordNote = chordNotes[chordNoteIdx].getPitch() == scaleNote.getNote().getPitch();
          if (isChordNote) {
            chordNoteIdx++;
          }
        }
        sb.append(isChordNote ? colourNote(scaleNote.getNote()) : scaleNote.getNote().getLabel())
            .append("    ");
      }
      out.println(sb.toString());
      out.println("\nFind those notes in the chromatic scale relative to "
          + colourNote(aChord.getChordRootNote().getNote()));
      out.println(aChord.describe(isMono()));
      out.print("Those intervals identify the chord as : ");
      out.println(String.format("%s%s%s", (isMono() ? "" : Colour.GREEN), aChord.getTitle(),
          (isMono() ? "" : Colour.RESET)));
      out.println(
          "\n┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈");
      out.println();
    }
  }

  private String colourNote(Note note) {
    return "" + (isMono() ? "" : ColourMap.get(note.getPitch())) + note.getLabel()
        + (isMono() ? "" : Colour.RESET);
  }
}
