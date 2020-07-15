package me.flotsam.frettler;

import static java.lang.System.out;
import java.util.List;
import java.util.concurrent.Callable;
import me.flotsam.frettler.command.FrettlerCommand;
import me.flotsam.frettler.command.GuitarCommand;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.instrument.guitar.Guitar;
import me.flotsam.frettler.view.guitar.ChordView;
import me.flotsam.frettler.view.guitar.GuitarView;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "frettler", 
    description = "Generates guitar scales and chords",
    subcommands = {
        GuitarCommand.class
    }
)
public class Main implements Callable<Integer> {

  public static void main(String... args) throws Exception {
    int exitCode = new CommandLine(new Main()).execute(args);
//    Guitar guitar = new Guitar();
//    scratchPad(guitar);
  }

  @Override
  public Integer call() throws Exception {
      CommandLine.usage(new FrettlerCommand(), System.out);
      return 0;
  }
  
  /**
   * Left here for time being to act as API reminder for self
   * @param guitar the guitar to use
   * @throws Exception oops
   */
  private static void scratchPad(Guitar guitar) throws Exception {
    GuitarView guitarView = new GuitarView(guitar);
    GuitarView.Options gvOptionsintervalsInlaysColour = guitarView.new Options(true, false, true);
    GuitarView.Options gvOptionsNotesInlaysColour = guitarView.new Options(false, false, true);

    ChordView chordView = new ChordView(guitar);
    ChordView.Options cvOptionsNotesColour = chordView.new Options(false, true);
    ChordView.Options cvOptionsIntervalsColour = chordView.new Options(true, true);

//    out.println();
//    guitarView.showFretboard(gvOptionsintervalsInlaysColour);
    
    Scale cMajorScale = new Scale(Note.C, IntervalPattern.MAJOR_SCALE);

//    guitarView.showFretboard(cMajorScale, gvOptionsintervalsInlaysColour);
    out.println();

    List<Chord> chords = cMajorScale.createScaleChords();
    out.println();

    for (Chord chord : chords) {
      out.println();
//      guitarView.showFretboard(chord, gvOptionsNotesInlaysColour);
      chordView.showChord(chord, cvOptionsNotesColour);
//      chordView.showChord(chord, cvOptionsIntervalsColour);
    }
  }
}

