package me.flotsam.frettler;

import static java.lang.System.out;
import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScalePattern;
import me.flotsam.frettler.instrument.guitar.Guitar;
import me.flotsam.frettler.view.guitar.ChordView;
import me.flotsam.frettler.view.guitar.GuitarView;

public class Main {


  // private final Logger LOGGER = LoggerFactory.getLogger(Main.class);



  public static void main(String[] args) throws Exception {
    Main main = new Main();
    Note[] strings = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};
    Guitar guitar = new Guitar(strings);
    main.cMajorScale(guitar);

  }

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

