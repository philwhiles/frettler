package me.flotsam.frettler.command;

import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.instrument.guitar.Guitar;
import me.flotsam.frettler.view.guitar.ChordView;
import me.flotsam.frettler.view.guitar.ChordView.Options;
import me.flotsam.frettler.view.guitar.GuitarView;
import picocli.CommandLine.Command;

@Command(name = "GUITAR", description = "Generates guitar scales and chords")
public class GuitarCommand extends FrettlerCommand implements Runnable {

  @Override
  public void run() {
    
    Guitar guitar = new Guitar();

    if (this.view == View.HORIZONTAL) {
      GuitarView guitarView = new GuitarView(guitar);
      GuitarView.Options guitarViewOptions = guitarView.new Options(this.getLabels() == Labels.INTERVALS, true, this.getDisplay() == Display.COLOUR);
    
      if (this.type == Type.SCALE) {
        Scale scale = new Scale(this.scalePattern, this.root);
        guitarView.showFretboard(scale, guitarViewOptions);
      } else {
        Chord chord = new Chord(this.root, this.scalePattern);
        guitarView.showFretboard(chord, guitarViewOptions);
      }
    } else {
      ChordView chordView = new ChordView(guitar);
      ChordView.Options chordViewOptions = chordView.new Options(this.getLabels() == Labels.INTERVALS, this.getDisplay() == Display.COLOUR);
      
      if (this.type == Type.SCALE) {
        System.err.println("SCALE display in the VERTICAL view is TBD");
        System.exit(-1);
      } else {
        Chord chord = new Chord(this.root, this.scalePattern);
        chordView.showChord(chord, chordViewOptions);
      }
    }
  }
}
