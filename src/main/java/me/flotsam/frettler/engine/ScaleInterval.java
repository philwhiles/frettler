package me.flotsam.frettler.engine;


public enum ScaleInterval {
// not sure if i will need these so leaving for time being
//  DIMINISHED_SECOND("d2", 0, false),
//  AUGMENTED_UNISON("A1", 1, false),
//  DIMINISH£D_THIRD("d3", 2, false),
//  AUGMENTED_SECOND("A2", 3, false),
//  DIMINISH£D_FOURTH("d4", 4, false),
//  AUGMENTED_THIRD("A3", 5, false),
//  AUGMENTED_FOURTH("A4", 6, false),
//  DIMINISHED_FIFTH("d5", 6, false),
//  FLATTENED_FIFTH("b5", 6, false),
//  DIMINISH£D_SIXTH("d6", 7, false),
//  AUGMENTED_FIFTH("A5", 8, false),
//  DIMINISH£D_SEVENTH("d7", 9, false),
//  AUGMENTED_SIXTH("A6", 10, false),
//  DIMINISH£D_OCTAVE("d8", 11, false),
//  AUGMENTED_SEVENTH("A7", 12, false),
  
  PERFECT_UNISON("P1", 0, true),
  MINOR_SECOND("m2", 1, true),
  MAJOR_SECOND("M2", 2, true),
  MINOR_THIRD("m3", 3, true),
  MAJOR_THIRD("M3", 4, true),
  PERFECT_FOURTH("P4", 5, true),

  DIMINISHED_FIFTH("d5", 6, false),

  PERFECT_FIFTH("P5", 7, true),
  MINOR_SIXTH("m6", 8, true),
  MAJOR_SIXTH("M6", 9, true),
  MINOR_SEVENTH("m7", 10, true),
  MAJOR_SEVENTH("M7", 11, true),
  PERFECT_OCTAVE("P8", 12, true),
  
  NINTH("9", 13, false),
  MINOR_TENTH("m10", 15, false),
  TENTH("10", 16, false),
  ELEVENTH("11", 17, false),
  TWELTH("12", 19, false), 
  THIRTEENTH("13", 21, false), 
  FOURTEENTH("14", 23, false), 
  FIFTEENTH("15", 24, false); 

  private boolean perfect;
  private int semiTones;
  private String label;

  private ScaleInterval(String label, int semiTones, boolean perfect) {
    this.label = label;
    this.semiTones = semiTones;
    this.perfect = perfect;
  }

  public int getSemiTones() {
    return semiTones;
  }
  
  public String getLabel() {
    return label;
  }
  
  public boolean isPerfect() {
    return perfect;
  }
}
