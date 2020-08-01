# Frettler - A CLI for generating musical scales and chords with fretboard and chord rendering for a variety of fretted instruments

## Synopsis
This is a CLI program, written in Java 11, that calculates scales, modes and chords and displays them on fretboard representations.
The rendering uses Unicode boxing characters and ANSI colour coding, the latter of which does not work in a standard Windows command prompt,
so has been disabled in the generated Windows bat file. If you install an ANSI capable console in Windows (see below), edit the build.bat to re-enable
color support.

Here is an example of its output :

<img src="https://github.com/philwhiles/frettler/blob/master/frettler.png"/>

## Building
Easily build Frettler from the command line or open in your favourite IDE.

### Linux/macOS 
Frettlers build script is a bash script, and the trickery it perfoms after the maven build to create a single executable, will only work on
Linux/macOS/UNIX. 

The application is built using maven, but you don't need to have maven pre-installed. Just run './build', which will:
- download its own maven
- compile the code
- build an executable fat jar
- create an executable shell command, 'frettler', which is a single relocatable binary that has the jar file embedded within it ie you can copy frettler to your favourite bin directory

### MS Windows 
The windows 'build.bat' will run the maven build, the only prerequisite, as for Linux/macOS, is to have Java 11 installed.
Once Frettler has built the build.bat creates the wrapper cmd file to launch Frettler, 'frettler.cmd'.

### IDE
If you want to edit and build Frettler in your IDE, you will need to install the Lombok plugin for your IDE from [Lombok](https://projectlombok.org)

## Execution
Frettler has a demo script that you can run to show the variety of arguments that you can use and the diagrams produced from them. It is probably the easiest way to understand how to
use Frettler. Run it and compare each example with the instructions below.

### Command Line
Run Frettler using the built executable shell command.

#### Linux/macOS

```
./frettler guitar horizontal scale c scale_major
```

#### Windows
```
frettler.bat guitar horizontal scale c scale_major

```
The default windows command terminal does not support ANSI colour encoding, so Frettler defaults to use the '--mono' output instead. If you really want the colourised output, you will have
to install an alternative terminal program such as [ConEmu](https://conemu.github.io) or [Cmder](https://cmder.net/).

## Arguments
Frettler has two ways of viewing scales/modes and arpeggios. The first is the horizontal view of a fretboard, which tries to show the notes in position on strings. The second view is
the vertical view which looks more like the classic chord diagram you see widely. The notes in the vertical view are shown in the middle of each frets box, rather than on a string. Each view
can be used to display a scale or arpeggio, in which case both default to showing the first 12 frets.

### Required Arguments
In this order:

1. Instrument - 'guitar', 'banjo', 'mandolin', 'bassguitar' or 'ukelele'
1. View - 'horizontal' (fretboard view) or 'vertical' (vertical diagram akin to chord charts, which can also display scales)
1. Root - the note of the scale or chord you want. ie 'C' or 'Ds' - note the 's' indicates a sharp
1. Pattern - for the scale or chord ie 'scale_major' or 'chord_min9'

### Optional Arguments
- -s or --strings followed by your preferred tuning to override the instruments default tuning. Need to use drop D tuning? just use '-s D,A,D,G,B,E'.
- -f or --frets N will display either horizontal or vertical views with N frets instead of the default 12 for each instrument.
- -i or --intervals an optional flag which makes Frettler display the note intervals(*) instead of the default note name. 
- -m or --mono an optional flag which will make Frettler display its diagrams without colour.
- -c or --chords will calculate and list the diatonic chords in the scale (Chord name and notes)

(*) The interval labels use the following convention :
- P1 - perfect unison/root
- m2 - minor second
- M2 - major second
- m3 - minor third
- M3 - major third
- P4 - perfect fourth
- d5 - diminished fifth
- P5 - perfect fifth
- m6 - minor sixth
- M6 - major sixth
- m7 - minor seventh
- M7 - major seventh

### Instruments
A fretboard is a fretboard, and frettler can handle any number of strings with any tuning. For each instrument mentioned it has a default number of strings and their standard tunings.

The instrument 'banjo' will assume the fifth string starts at the fifth fret - if you want the display for a banjo having all strings full length,
just use any instrument other than banjo with --strings A,B,C,etc.

### Scales/Modes/Chords
Frettler maps the scale or chord name provided to an internal representation of its interval pattern. Whichever is provided will determine how Frettler deals with it and displays it.
To aid the bash tab completion, and to make scales stand out from chords, each is prefixed with 'scale_', 'mode_' or 'chord_'. ie if you enter the Frettler command as far as 'scale' then
hit tab, it can show you only the scales.

- scale_chromatic
- scale_major
- scale_melodic_minor
- scale_harmonic_minor
- scale_major_pentatonic
- scale_minor
- scale_minor_pentatonic
- scale_blues

- mode_aeolian
- mode_dorian_
- mode_mixolydian
- mode_lydian
- mode_ionian
- mode_locrian

- chord_min11
- chord_dom11
- chord_9flat5
- chord_m7add9
- chord_7sharp9
- chord_7flat9
- chord_dom9
- chord_maj69
- chord_maj9
- chord_min9
- chord_dim7
- chord_7sharp5
- chord_7flat5
- chord_min7
- chord_aug7
- chord_maj7
- chord_dom7
- chord_min6
- chord_maj6
- chord_minmaj7
- chord_min7flat5
- chord_7plus
- chord_7sus4
- chord_add9
- chord_add11
- chord_aug
- chord_dim
- chord_min
- chord_sus2
- chord_sus4
- chord_maj

### Chord fingerings
I am working on a version of the vertical view which can display fingering for a given chord. It appears to work for standard six string guitar, open string chords, but for anything else,
it currently gets it wrong. I am finding it difficult to write the code that can make the right decisions, and am debating abandoning this feature or possibly taking a different
approach, and rather than try to analyse the possible note candidates and choose the appropriate fingering, instead use a database of common chord fingerings and apply them with some 
fretboard shifting to the chord in question.

Any and all contributions to the rules needed to select the appropriate frets for a chord are welcomed.


### Tab Completion
If you use bash as your shell, frettler can output a tab completion script to use. Just use the following :

```
source <(./frettler completions)
```

Tab completion in bash helps greatly with Frettler - bash will complete all of the args for you and show you the possible completions, handy with the Frettler interval pattern names.
The interval patterns all have a prefix or either 'scale_', 'mode_' or 'chord_' largely to allow the tab completion to show you just the selection of patterns that is relevant to you.

## Programmatically
If you want to you can write your own Main class and create one of the FrettedInstrument subtypes, create a Scale or Chord object, create a view for your instrument
and then instruct the view to display your chord or object. The API is pretty straightfoward I think, and defaults standard tuning for each type of instrument.

Have a look at the GuitarCommand for some examples of usage, look at the constructors of the various classes such as Guitar, Scale, Chord and the VerticalView and 
HorizontalView classes, and their public methods.

### Engine
The engine can generate Lists of notes that represent given scales, and can calculate the chords within that scale.
The engine knows nothing about an instrument, it simply applies music theory to generate Java lists of the notes in scales and chords.

### Instrument
As mentioned, a fretted instrument is largely the same as another, and the classes in this package exist mainly so that Frettler can easily generate views for each 
instrument from the command line, without you having to specify the default strings and their tunings each time.

### View
Currently only console views, each constructed with a FrettedInstrument, which take the scale and chord constructs from the engine, and render them
on that instruments fretboard, up to the 12th fret, by default.

Both the views can display the notes or intervals with unique ANSI colours, if you are
either running from the command line and using an ANSI colour friendly terminal, or in an IDE such as Eclipse using an ANSI Console
plugin (goto to Eclipse Marketplace and search for 'ANSI console').

The colours look best when Frettler is run in a terminal with a near black background.


## Caveats
This is a work in progress, pretty much like the authors understanding of music theory.

### Music theory
Contributions and advice are welcomed. Frettler was initially created as an exercise in learning music theory and the author still has a long way to go.

## Tips
Left to the end to avoid confusion and detracting from the understanding of the arguments required.

#### Abbreviations
The Instrument argument can be abbreviated, as long as the abbreviation is unique within the set of instruments. ie you can use 'g', but you must use 'ban' or 'bas'.
The View argument can only be abbreviated to 'h' or 'v'.

#### Defaults
All the arguments except for the instrument, have default values. This means you can incrementally drop the arguments from right to left, but cannot drop one in the middle.
So './frettler guitar' or even './frettler g', would do the same as './frettler guitar horiontal C scale_major'. 
And './frettler g v', would do the same as './frettler guitar vertical C scale_major'. 

## Todo
- add an additional command ie './frettler chord C,E,G' and then use its interval knowledge to name the chord for you. Prototype code has been written, and Frettler can easily work
out for instance that C,E,G is Cmaj, but I am wondering how to handle major ninths for instance.
- change the ANSI colour encoding used to be cross platform using [jansi](https://github.com/fusesource/jansi). If I hear of enough Windows users wanting this...
- chord and note labelling currently only uses sharps - I need to work out how to decide whether each should be labelled as sharp or flat
- blues scale does not support chord generation...
- write some unit tests!

## Contact
If you have any issues with running Frettler, questions or can help providing input or even code, do please get in touch.

email: <phil.whiles@gmail.com>



