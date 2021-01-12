/*
    Copyright (C) 2020  Philip Whiles

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package me.flotsam.frettler.view;

public enum Colour {
  RESET("\033[0m"),
  INVERSE("\033[7m"),
  BLINK("\033[5m"),
  NORMAL("\033[22m"),
  BOLD("\033[1m"),
  BG_WHITE("\033[48;5;231m"),

  WHITE("\033[0;37;1m"),
  HOT_PINK("\033[38;5;206;1m"),
  DARKISH_BLUE("\033[38;5;33;1m"),
  DARK_ORANGE("\033[38;5;208;1m"),
  RED1("\033[38;5;196;1m"),
  GOLD1("\033[38;5;220;1m"),
  CHARTREUSE2("\033[38;5;112;1m"),
  SKY_BLUE1("\033[38;5;117;1m"),
  GREEN3("\033[38;5;40;1m"),
  MURKY_GREEN("\033[38;5;143;1m"),
  THISTLE("\033[38;5;225;1m"),
  YELLOW2("\033[38;5;190;1m"),
  YELLOW3("\033[38;5;148;1m"),
  MAGENTA2("\033[38;5;165;1m"),
  GREEN("\033[38;5;112;1m"),
  GREY("\033[38;5;21;1m");

  private final String code;

  Colour(String code) {
      this.code = code;
  }

  @Override
  public String toString() {
      return code;
  }
}