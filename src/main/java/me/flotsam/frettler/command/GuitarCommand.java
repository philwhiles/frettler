package me.flotsam.frettler.command;

import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.instrument.guitar.Guitar;
import me.flotsam.frettler.view.guitar.ChordView;
import me.flotsam.frettler.view.guitar.GuitarView;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Handles the initial (and solitary right now) GUITAR command/param
 * @author philwhiles
 *
 */
@Command(name = "GUITAR", description = "Generates guitar scales and chords")
public class GuitarCommand extends FrettlerCommand implements Runnable {

  @Option(names = {"-c", "--chords"})
  boolean allChords = false;

  @Option(names = {"-s", "--strings"}, split = ",")
  Note[] strings = new Note[] {};
  
  @Override
  public void run() {
    
    Guitar guitar = new Guitar(strings);

    if (this.view == View.HORIZONTAL) {
      GuitarView guitarView = new GuitarView(guitar);
      GuitarView.Options guitarViewOptions = guitarView.new Options(this.getLabels() == Labels.INTERVALS, true, this.getDisplay() == Display.COLOUR);
    
      if (this.type == Type.SCALE) {
        Scale scale = new Scale(this.root, this.intervalPattern);
        guitarView.showFretboard(scale, guitarViewOptions);
        
        if (allChords) {
          ChordView chordView = new ChordView(guitar);
          ChordView.Options chordViewOptions = chordView.new Options(this.getLabels() == Labels.INTERVALS, this.getDisplay() == Display.COLOUR);
          List<Chord> chords = scale.createScaleChords();
          for (Chord chord : chords) {
            chordView.showChord(chord, chordViewOptions);
          }
        }
      } else {
        Chord chord = new Chord(this.root, this.intervalPattern);
        guitarView.showFretboard(chord, guitarViewOptions);
      }
    } else {
      ChordView chordView = new ChordView(guitar);
      ChordView.Options chordViewOptions = chordView.new Options(this.getLabels() == Labels.INTERVALS, this.getDisplay() == Display.COLOUR);
      
      if (this.type == Type.SCALE) {
        System.err.println("SCALE display in the VERTICAL view is TBD");
        System.exit(-1);
      } else {
        Chord chord = new Chord(this.root, this.intervalPattern);
        chordView.showChord(chord, chordViewOptions);
      }
    }
  }
}
