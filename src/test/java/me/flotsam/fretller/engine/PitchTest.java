package me.flotsam.fretller.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import me.flotsam.frettler.engine.Pitch;

public class PitchTest {
  
  @Test
  public void testIsBefore() {
    Pitch f = Pitch.F;
    Pitch [] beforeF = new Pitch[] {Pitch.C, Pitch.Cs, Pitch.D, Pitch.Ds, Pitch.E};
    Pitch [] afterF = new Pitch[] {Pitch.F, Pitch.Fs, Pitch.G, Pitch.Gs, Pitch.A, Pitch.As, Pitch.B};
    for (Pitch testPitch:beforeF) {
      assertTrue(testPitch.getLabel(), testPitch.isBefore(f));
    }
    for (Pitch testPitch:afterF) {
      assertFalse(testPitch.getLabel(), testPitch.isBefore(f));
    }
  }

}
