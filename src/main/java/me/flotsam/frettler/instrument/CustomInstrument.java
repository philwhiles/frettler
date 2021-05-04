/*
 * Copyright (C) 2020 Philip Whiles
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package me.flotsam.frettler.instrument;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import me.flotsam.frettler.engine.Note;

public class CustomInstrument extends FrettedInstrument {

  private static CustomProperties customProperties;

  static {
    try (InputStream input = new FileInputStream("custom.properties")) {

      Properties prop = new Properties();
      prop.load(input);
      customProperties = new CustomProperties();
      Note [] stringNotes = Arrays.asList(prop.getProperty("strings").split(",")).stream().map(s->Note.valueOf(s)).toArray(Note[]::new);
      customProperties.setStrings(stringNotes);
      customProperties.setFrets(Integer.parseInt(prop.getProperty("frets")));
      customProperties.setName(prop.getProperty("name"));
      customProperties.setLefty(Boolean.parseBoolean(prop.getProperty("lefty")));
    } catch (IOException ex) {
      System.out.println("There was a problem loading the custom.properties");
      System.exit(-1);
    }
  }

  public CustomInstrument() {
    this(customProperties.getStrings(), null, customProperties.getFrets(), customProperties.isLefty());
  }

  public CustomInstrument(Note[] strings, Tuning tuning, Integer frets, boolean lefty) {
    super(FrettedInstrument.InstrumentType.CUSTOM, customProperties.getFrets(), customProperties.getStrings(), tuning, customProperties.isLefty());
  }
  
  public String getLabel() {
    return customProperties.getName();
  }
}
