package me.flotsam.frettler;

import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScalePattern;
import me.flotsam.frettler.instrument.Guitar;

public class Main {


  // private final Logger LOGGER = LoggerFactory.getLogger(Main.class);



  public static void main(String[] args) throws Exception {
    Main main = new Main();
    Note[] strings = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};
    Guitar guitar = new Guitar(strings);
    main.cMajorScale(guitar);

  }

  public void cMajorScale(Guitar guitar) throws Exception {

    Scale cMajorScale = new Scale(ScalePattern.MAJOR, Note.C);
    System.out.println(cMajorScale);
    System.out.println("\n");


    // Chord cMajorChord = new Chord(Note.D, ScalePattern.MINOR_TRIAD);
    // System.out.println(cMajorChord);

    guitar.printFretboard(cMajorScale);
    System.out.println("\n");


    List<Chord> chords = Chord.createScaleChords(cMajorScale);

    for (Chord chord : chords) {
      System.out.println(chord);
    }
    System.out.println("\n");

    for (Chord chord : chords) {
      System.out.println("\n");
      guitar.printFretboard(chord);
    }
  }

}

