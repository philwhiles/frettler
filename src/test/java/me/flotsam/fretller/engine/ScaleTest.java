package me.flotsam.fretller.engine;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import me.flotsam.frettler.engine.IntervalPattern;
import me.flotsam.frettler.engine.Note;
import me.flotsam.frettler.engine.Pitch;
import me.flotsam.frettler.engine.Scale;

public class ScaleTest {

  @Test
  public void testGetNthScaleNote() {
    Scale cScale = new Scale(Note.C, IntervalPattern.SCALE_MAJOR);
    Pitch [] expected = new Pitch[] {Pitch.C, Pitch.D, Pitch.E, Pitch.F, Pitch.G, Pitch.A, Pitch.B, Pitch.C};
    
    for (int n = 1; n < 8;n++) {
      assertEquals("should be " + expected[n-1], expected[n-1], cScale.getNthScaleNote(n).getNote().getPitch());
    }
  }
}
