package me.flotsam.frettler.command;

import lombok.Data;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.IntervalPattern;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Base class for the primary commands
 * @author philwhiles
 *
 */
@Data
public class FrettlerCommand {

  @Parameters(index = "0", defaultValue="HORIZONTAL", description="The view to use")
  protected View view;
  
  @Parameters(index = "1", defaultValue="C", description="The root/tonic of the chord or scale")
  protected Note root;
  
  @Parameters(index = "2", defaultValue="MAJOR_SCALE", description="The IntervalPattern to use to generate the scale or arpeggio")
  protected IntervalPattern intervalPattern;
  
  @Option(names = {"-m", "--mono"}, description="Display in 'monochrome'")
  protected boolean mono;
  
  @Option(names = {"-i", "--intervals"}, description="Show interval labels instead of notes")
  protected boolean intervals;

  public enum View {
    HORIZONTAL(true), H(true), VERTICAL(false), V(false);
    private boolean horizontal;
    View(boolean horizontal) {
     this.horizontal = horizontal;
    }
    public boolean isHorizontal() {
      return horizontal;
    }
  }
}


