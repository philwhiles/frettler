package me.flotsam.frettler.engine;


public enum Note {
  C("C"), Cs("C#"), D("D"), Ds("D#"), E("E"), F("F"), Fs("F#"), G("G"), Gs("G#"), A("A"), As(
      "A#"), B("B");

  private String label;

  private Note(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

}
