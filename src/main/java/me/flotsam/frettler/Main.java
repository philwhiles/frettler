package me.flotsam.frettler;

import static java.lang.System.out;
import java.util.List;
import java.util.concurrent.Callable;
import me.flotsam.frettler.command.FrettlerCommand;
import me.flotsam.frettler.command.GuitarCommand;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScalePattern;
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

  public static void main(String... args) {
    int exitCode = new CommandLine(new Main()).execute(args);
    System.out.println(exitCode);
  }

  @Override
  public Integer call() throws Exception {
      CommandLine.usage(new FrettlerCommand(), System.out);
      return 0;
  }
  
  
  //  public static void main(String[] args) throws Exception {
//    // Main main = new Main();
//    // Note[] strings = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};
//    // Guitar guitar = new Guitar(strings);
//    // main.cMajorScale(guitar);
//
//  }
  
  
  
  public void cMajorScale(Guitar guitar) throws Exception {
    GuitarView guitarView = new GuitarView(guitar);
    GuitarView.Options gvOptionsintervalsInlaysColour = guitarView.new Options(true, false, true);
    GuitarView.Options gvOptionsNotesInlaysColour = guitarView.new Options(false, false, true);

    ChordView chordView = new ChordView(guitar);
    ChordView.Options cvOptionsNotesColour = chordView.new Options(false, true);
    ChordView.Options cvOptionsIntervalsColour = chordView.new Options(true, true);

    out.println();
    guitarView.showFretboard(gvOptionsintervalsInlaysColour);

    Scale cMajorScale = new Scale(ScalePattern.MAJOR, Note.C);

    guitarView.showFretboard(cMajorScale, gvOptionsintervalsInlaysColour);
    out.println();

    List<Chord> chords = cMajorScale.createScaleChords();
    out.println();

    for (Chord chord : chords) {
      out.println();
      guitarView.showFretboard(chord, gvOptionsNotesInlaysColour);
      chordView.showChord(chord, cvOptionsNotesColour);
      chordView.showChord(chord, cvOptionsIntervalsColour);
    }
  }
}

