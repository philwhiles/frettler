package me.flotsam.frettler.view.console;

public enum Colour {
  //Color end string, color reset
  RESET("\033[0m"),
  BG_WHITE("\033[48;5;231m"),

  WHITE("\033[0;37;1m"),
  HOT_PINK("\033[38;5;206;1m"),
  DARK_ORANGE("\033[38;5;208;1m"),
  RED1("\033[38;5;196;1m"),
  GOLD1("\033[38;5;220;1m"),
  CHARTREUSE2("\033[38;5;112;1m"),
  SKY_BLUE1("\033[38;5;117;1m"),
  GREEN3("\033[38;5;40;1m"),
  LIGHT_STEEL_BLUE1("\033[38;5;189;1m"),
  THISTLE("\033[38;5;225;1m"),
  SKY_BLUE2("\033[38;5;111;1m"),
  YELLOW2("\033[38;5;190;1m"),
  YELLOW3("\033[38;5;148;1m"),
  MAGENTA2("\033[38;5;165;1m"),
;

  private final String code;

  Colour(String code) {
      this.code = code;
  }

  @Override
  public String toString() {
      return code;
  }
}