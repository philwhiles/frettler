package me.flotsam.frettler;

import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.engine.ScalePattern;
import me.flotsam.frettler.instrument.Guitar;

public class Main {

  
//  private final Logger LOGGER = LoggerFactory.getLogger(Main.class);



  public static void main(String[] args) throws Exception {
    Main main = new Main();
    Guitar  engine = new Guitar();
    main.cMajorScale(engine);

  }

  public void cMajorScale(Guitar engine) throws Exception {
    Note[] strings = new Note[] {Note.E, Note.A, Note.D, Note.G, Note.B, Note.E};
    Guitar guitar = new Guitar(strings);
    
    Scale cMajorScale = new Scale(ScalePattern.MAJOR, Note.A);
    System.out.println(cMajorScale);
    
    List<Chord> chords = Chord.createScaleChords(cMajorScale);
    System.out.println(chords);
    System.out.println("\n");
    
    
    Chord cMajorChord = new Chord(Note.D, ScalePattern.MINOR_TRIAD);
    System.out.println(cMajorChord);
    
    System.out.println("\n");
    guitar.printFretboard(cMajorScale);
    System.out.println("\n");
    guitar.printFretboard(cMajorChord);
    
    
    
    
  }

}

