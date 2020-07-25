package me.flotsam.frettler.command;

import java.util.List;
import me.flotsam.frettler.engine.Chord;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Scale;
import me.flotsam.frettler.instrument.FrettedInstrument;
import me.flotsam.frettler.view.VerticalView;
import me.flotsam.frettler.view.HorizontalView;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Base class that handles the initial instrument command param
 * 
 * @author philwhiles
 *
 */
@Command
public abstract class FrettedInstrumentCommand extends FrettlerCommand {

  @Option(names = {"-c", "--chord"}, description="chord mode (view dependant)")
  boolean chordMode = false;

  @Option(names = {"-s", "--strings"}, split = ",", paramLabel="note", description="comma separated list of string tunings ie E,A,D,G,B,E")
  Note[] strings = new Note[] {};

  @Option(names = {"-f", "--frets"}, paramLabel="num", description="overrides the default 12 frets displayed")
  Integer frets = 12;

  public void exec(FrettedInstrument instrument) {

    Scale scale = null;
    Chord chord = null;
    if (this.intervalPattern.getPatternType() == IntervalPattern.PatternType.CHORD) {
      chord = new Chord(this.root, this.intervalPattern);
    } else {
      scale = new Scale(this.root, this.intervalPattern);
    }


    if (this.view.isHorizontal()) {
      HorizontalView instrumentView = new HorizontalView(instrument);
      HorizontalView.Options instrumentViewOptions = instrumentView.new Options(intervals, true, !mono);

      if (scale != null) {
        instrumentView.showScale(scale, instrumentViewOptions);

        if (chordMode) {
          VerticalView chordView = new VerticalView(instrument);
          VerticalView.Options chordViewOptions = chordView.new Options(intervals, !mono);
          List<Chord> chords = scale.createScaleChords();
          for (Chord aChord : chords) {
            chordView.showChord(aChord, chordViewOptions);
          }
        }
      } else {
        chord = new Chord(this.root, this.intervalPattern);
        instrumentView.showChord(chord, instrumentViewOptions);
      }
    } else {
      VerticalView chordView = new VerticalView(instrument);
      VerticalView.Options chordViewOptions = chordView.new Options(intervals, !mono);

      if (scale != null) {
        chordView.showScale(scale, chordViewOptions);
      } else {
        if (chordMode) {
          chordView.showChord(chord, chordViewOptions);
        } else {
          chordView.showArpeggio(chord, chordViewOptions);
        }
      }
    }
  }
}
