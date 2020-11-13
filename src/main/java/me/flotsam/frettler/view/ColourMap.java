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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used by the views to get a colour for a note or interval. They don't choose a colour - each time they get a colour
 * for an object, it is allocated a colour, and that colour is returned for the same object next time get is called. 
 * @author philwhiles
 *
 */
public class ColourMap {

  private static Map<Object, Colour> objColourMap = new HashMap<>();
  private static List<Colour> colourList = Arrays.asList(
      Colour.DARK_ORANGE,
      Colour.GREEN3,
      Colour.DARKISH_BLUE,
      Colour.RED1,
      Colour.HOT_PINK,
      Colour.YELLOW2,
      Colour.SKY_BLUE1,
      Colour.YELLOW3,
      Colour.MAGENTA2,
      Colour.MURKY_GREEN
 );

  private static int colourNum = -1;

  public static Colour get(Object obj) {
    Colour colour = objColourMap.get(obj);
    if (colour == null) {
      if (++colourNum == colourList.size()) {
        colourNum = 0;
      }
      colour = colourList.get(colourNum);
      objColourMap.put(obj, colour);
    }
    return colour;
  }
  
  private ColourMap() {}

}
