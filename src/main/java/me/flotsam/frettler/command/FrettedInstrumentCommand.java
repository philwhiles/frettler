package me.flotsam.frettler.command;

import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.instrument.FrettedInstrument;
import me.flotsam.frettler.view.ChordView;
import me.flotsam.frettler.view.FretboardView;
import picocli.CommandLine.Option;

/**
 * Base class that handles the initial instrument command param 
 * @author philwhiles
 *
 */
public abstract class FrettedInstrumentCommand extends FrettlerCommand {

  @Option(names = {"-c", "--chords"})
  boolean allChords = false;

  @Option(names = {"-s", "--strings"}, split = ",")
  Note[] strings = new Note[] {};
  
  public void exec(FrettedInstrument instrument) {
    
    Scale scale = null;
    Chord chord = null;
    if (this.type == Type.SCALE) {
      scale = new Scale(this.root, this.intervalPattern);
    } else {
      chord = new Chord(this.root, this.intervalPattern);
    }
    
    
    if (this.view == View.HORIZONTAL) {
      FretboardView instrumentView = new FretboardView(instrument);
      FretboardView.Options instrumentViewOptions = instrumentView.new Options(this.getLabels() == Labels.INTERVALS, true, this.getDisplay() == Display.COLOUR);
    
      if (this.type == Type.SCALE) {
        instrumentView.showFretboard(scale, instrumentViewOptions);
        
        if (allChords) {
          ChordView chordView = new ChordView(instrument);
          ChordView.Options chordViewOptions = chordView.new Options(this.getLabels() == Labels.INTERVALS, this.getDisplay() == Display.COLOUR);
          List<Chord> chords = scale.createScaleChords();
          for (Chord aChord : chords) {
            chordView.showChord(aChord, chordViewOptions);
          }
        }
      } else {
        chord = new Chord(this.root, this.intervalPattern);
        instrumentView.showFretboard(chord, instrumentViewOptions);
      }
    } else {
      ChordView chordView = new ChordView(instrument);
      ChordView.Options chordViewOptions = chordView.new Options(this.getLabels() == Labels.INTERVALS, this.getDisplay() == Display.COLOUR);
      
      if (this.type == Type.SCALE) {
        chordView.showScale(scale, chordViewOptions);
      } else {
        chordView.showChord(chord, chordViewOptions);
      }
    }
  }
}
