# Frettler - A CLI for generating musical scales and chords with fretboard and chord rendering for a variety of fretted instruments

## Synopsis
This is a CLI program, written in Java 11, that exposes the results of it's own music theory API through console rendering of a fretboard.
The rendering is performed with System.out.prinln statements, using Unicode boxing characters and ANSI colour coding.

Here is an example of its output :

<img src="https://github.com/philwhiles/frettler/blob/master/frettler.png"/>

## Building
Frettlers build script is a bash script, and the trickery it perfoms after the maven build to create a single executable, will only work on
Linux/macOS/UNIX. If you use Windows, take the mvn command from the build script to build the fat jar in the target folder. Then run frettler
with :

```

java -jar target/frettler-0.1.0-jar-with-dependencies.jar YOUR FRETTLER ARGS HERE

```


The application is built using maven, but you don't need to have maven pre-installed. Just run './build', which will:
- download its own maven
- compile the code
- build an executable fat jar
- create an executable shell command, 'frettler', which is a single relocatable binary that has the jar file embedded within it ie you can copy frettler to your favourite bin directory

## Execution
Frettler has a demo script that you can run to show the variety of arguments that you can use and the diagrams produced from them. It is probably the easiest way to understand how to
use Frettler. Run it and compare each example with the instructions below.

### Command Line
Run Frettler using the built executable shell command, frettler, ie :


```
./frettler guitar horizontal scale c major_scale

```
## Arguments
Frettler has two ways of viewing scales,chords and arpeggios. The first is the horizontal view of a fretboard, which tries to show the notes in position on strings. The second view is
the vertical view which looks more like the classic chord diagram you see widely. The notes in the vertical view are shown in the middle of each frets box, rather than on a string. Each view
can be used to display a scale or arpeggio, in which case both default to showing the first 12 frets.

The two views use the --chords optional argument to do different things:

- horizontal view : frettler will calculate the chords in that key and display each using the 'vertical' chord view.
- vertcial view : frettler will calculate the chord and rather than show it as an arpeggio down the 12 frets, will try and work out the open string fingering and show you
a classic chord diagram

### Required Arguments
In this order:

- Instrument - 'guitar', 'banjo', 'mandolin', 'bassguitar' or 'ukelele'
- View - 'horizontal' (fretboard view) or 'vertical' (vertical diagram akin to chord charts, which can also display scales)
- Root - the note of the scale or chord you want. ie 'C' or 'Ds' - note the 's' indicates a sharp
- Pattern - for the scale or chord ie 'major_scale' or 'minor_triad'

### Optional Arguments
- -s or --strings followed by your preferred tuning to override the instruments default tuning. Need to use drop D tuning? just use '-s D,A,D,G,B,E'.
- -f or --frets N will display either horizontal or vertical views with N frets instead of the default 12 for each instrument.
- -c or --chords an optional flag, with slightly different meanings to each view, see above.
- -i or --intervals an optional flad which makes Frettler display the note intervals(*) instead of the default note name. 
- -m or --mono an optional flag which will make Frettler display its diagrams sans colour.

(*) The interval labels use the following convention :
- P1 - perfect_unison/root
- m2 - minor_second
- M2 - major_second
- m3 - minor_third
- M3 - major_third
- P4 - perfect_fourth
- d5 - diminished_fifth
- P5 - perfect_fifth
- m6 - minor_sixth
- M6 - major_sixth
- m7 - minor_seventh
- M7 - major_seventh

### Instruments
A fretboard is a fretboard, and frettler can handle any number of strings with any tuning. For each instrument mentioned it has a default number of strings and their standard tunings.

The instrument 'banjo' will assume the fifth string starts at the sixth fret - if you want the display for a banjo having all strings full length,
just use any instrument other than banjo with --strings A,B,C,etc.

### Scales/Modes
- major_scale
- minor_scale
- melodic_minor_scale
- harmonic_minor_scale
- major_pentatonic_scale
- minor_pentatonic_scale
- blues_scale
- dorian_mode
- mixolydian_mode
- lydian_mode
- ionian_mode
- aeolian_mode 
- locrian_mode

### Chords
- major_triad
- minor_triad
- diminished_triad
- major_quadriad
- minor_quadriad
- diminished_quadriad
- minor_major_quadriad

### Chord Fingering Calculation
The chord calculation used in the 'vertical' view is still a work in progress. It appears to work for standard six string guitar, open string chords, but for anything else,
take the chord fingerings calculated with a pinch of salt. For instance the chord calculation for C Major with a seven string guitar is a bit out of whack, 
and that is probably an indication that it will fall short elsewhere. Any and all contributions to the rules needed to select the appropriate frets for a chord are welcomed.



### Tab Completion
If you use bash as your shell, frettler can output a tab completion script to use. Just use the following :

```
source <(./frettler completions)
```

### Programmatically
If you want to you can write your own Main class and create one of the FrettedInstrument subtypes, create a Scale or Chord object, create a view for your instrument
and then instruct the view to display your chord or object. The API is pretty straightfoward I think, and defaults standard tuning for each type of instrument.

Have a look at the GuitarCommand for some examples of usage, look at the constructors of the various classes such as Guitar, Scale, Chord and the ChordView and 
FretboardView classes, and their public methods.

## Engine
The engine can generate Lists of notes that represent given scales, and can calculate the chords within that scale.
The engine knows nothing about an instrument, it simply applies music theory to generate Java lists of the notes in scales and chords.


## View
Currently only console views, each constructed with a FrettedInstrument, which take the scale and chord constructs from the engine, and render them
on that instruments fretboard, up to the 12th fret.

Both the views can display the notes or intervals with unique ANSI colours, if you are
either running from the command line and using an ANSI colour friendly terminal, or in Eclipse using an ANSI Console
plugin (goto to Eclipse Marketplace and search for 'ANSI console').

The colours look best when Frettler is run in a terminal with a new black background.


## Caveats
This is a work in progress, pretty much like the authors understanding of music theory.

### Music theory
Contributions and advice are welcomed. Frettler was initially created as an exercise in learning music theory and the author still has a long way to go.

### Chord fingerings
The ChordView still requires some work - it can calculate the chord fingering for fairly standard, open string, major, minor and diminished chords
but further work is needed. 
The chord mode of the horizontal view calculates the chord diagrams by favouring the higher frets, can exclude strings lower 
than the tonic string, can avoid duplicating the same note in the same octave as it works from the sixth string to the first etc.
This algorithm is probably the area requiring the most testing and further refinement.

## Tips
Left to the end to avoid confusion and detracting from the understanding of the arguments required.

#### Abbreviations
The Instrument argument can be abbreviated, as long as the abbreviation is unique within the set of instruments. ie you can use 'g', but you must use 'ban' or 'bas'.
The View argument can only be abbreviated to 'h' or 'v'.

#### Defaults
All the arguments except for the instrument, have default values. This means you can incrementally drop the arguments from right to left, but cannot drop one in the middle.
So './frettler guitar' or even './frettler g', would do the same as './frettler guitar horiontal C major_scale'. 
And './frettler g v', would do the same as './frettler guitar vertical C major_scale'. 

## Todo
- chord and note labelling currently only uses sharps - I need to work out how to decide whether each should be labelled as sharp or flat
- blues scale does not support chord generation...



