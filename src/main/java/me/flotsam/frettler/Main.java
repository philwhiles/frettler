package me.flotsam.frettler;

import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScalePattern;
import me.flotsam.frettler.instrument.Guitar;
import me.flotsam.frettler.view.ChordConsole;
import me.flotsam.frettler.view.GuitarConsole;
import static java.lang.System.out;

public class Main {


  // private final Logger LOGGER = LoggerFactory.getLogger(Main.class);



  public static void main(String[] args) throws Exception {
    Main main = new Main();
    Note[] strings = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};
    Guitar guitar = new Guitar(strings);
    main.cMajorScale(guitar);

  }

  public void cMajorScale(Guitar guitar) throws Exception {
    GuitarConsole guitarView = new GuitarConsole(guitar);
    ChordConsole chordView = new ChordConsole(guitar);

    Scale cMajorScale = new Scale(ScalePattern.MAJOR, Note.C);
    out.println(cMajorScale);
    out.println("\n");

    guitarView.showFretboard();

    Chord dMajorChord = new Chord(Note.D, ScalePattern.MAJOR_TRIAD);
    chordView.showChord(dMajorChord);

    guitarView.showFretboard(cMajorScale);
    out.println("\n");


    List<Chord> chords = cMajorScale.createScaleChords();

    for (Chord chord : chords) {
      out.println(chord);
    }
    out.println("\n");

    for (Chord chord : chords) {
      out.println("\n");
      guitarView.showFretboard(chord);
    }

  }

}

