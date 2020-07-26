package me.flotsam.frettler.engine;


public enum ScaleInterval {
  P1("P1", 0, true),
  m2("m2", 1, true),
  M2("M2", 2, true),
  m3("m3", 3, true),
  M3("M3", 4, true),
  P4("P4", 5, true),

  d5("d5", 6, false),

  P5("P5", 7, true),
  m6("m6", 8, true),
  M6("M6", 9, true),
  m7("m7", 10, true),
  M7("M7", 11, true),
  P8("P8", 12, true),
  
  m9("m9", 14, false),
  M9("M9", 14, false),
  m10("m10", 16, false),
  M10("M10", 17, false),
  M11("M11", 18, false),
  m12("m12", 20, false), 
  M12("M12", 20, false), 
  M13("M13", 22, false), 
  M14("M14", 24, false), 
  M15("M15", 25, false); 

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
