# Frettler - A CLI for generating musical scales and chords with fretboard and chord rendering for a variety of fretted instruments

## Synopsis
This is a CLI program, written in Java 11, that calculates scales, modes and chords and displays them on fretboard representations.
The rendering uses Unicode boxing characters and ANSI colour coding, the latter of which does not work in a standard Windows command prompt,
so has been disabled in the generated Windows bat file. If you install an ANSI capable console in Windows (see below), edit the build.bat to re-enable
color support.

Here is an example of its output :

<img src="https://github.com/philwhiles/frettler/blob/master/demo1.png"/>
<img src="https://github.com/philwhiles/frettler/blob/master/demo2.png"/>

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
./frettler guitar horizontal c scale_major
```

#### Windows
```
frettler.bat guitar horizontal c scale_major

```
The default windows command terminal does not support ANSI colour encoding, so Frettler defaults to use the '--mono' output instead. If you want the colourised output, you will have
to install an alternative terminal program such as [ConEmu](https://conemu.github.io) or [Cmder](https://cmder.net/). If you install an ANSI capable console, edit the build.bat and remove
the --mono, then rebuild Frettler.

## Demo
Do read the details below to get an understanding of how to drive Frettler, but perhaps first, after building it, try one of the provided demo scripts ('demo' for linux/macOS, 'demo.bat' for windows).
The demos will run Frettler with a variety of arguments, exercising it fully, and displaying the command used to generate each step demonstrated.

## Arguments
Frettler has two ways of viewing scales/modes and arpeggios. The first is the horizontal view of a fretboard, which tries to show the notes in position on strings. The second view is
the vertical view which looks more like the classic chord diagram you see widely. The notes in the vertical view are shown in the middle of each frets box, rather than on a string. Each view
can be used to display a scale or arpeggio, in which case both default to showing the first 12 frets.

### Required Arguments
The first argument to frettler must be one of the following:
- guitar
- bassguitar
- ukelele
- mandolin
- banjo
- completions
- chord

The arguments which follow (or don't in the case of completions!) are as follows.

### Required Arguments - Instrument Commands
When you want Frettler to display a scale or chord on a fretboard, in this order:

1. Instrument - 'guitar', 'banjo', 'mandolin', 'bassguitar' or 'ukelele'
1. View - 'horizontal' (fretboard view) or 'vertical' (vertical diagram akin to chord charts, which can also display scales)
1. Root - the note of the scale or chord you want. ie 'C' or 'Ds' - note the 's' indicates a sharp
1. Pattern - for the scale or chord ie 'scale_major' or 'chord_min9'

### Optional Arguments - Instrument Commands
- -s or --strings followed by your preferred tuning to override the instruments default tuning. Need to use drop D tuning? just use '-s D,A,D,G,B,E'.
(If you regularly want to use a non default string setup, see below)
- -f or --frets N will display either horizontal or vertical views with N frets instead of the default 12 for each instrument.
- -i or --intervals an optional flag which makes Frettler display the note intervals(*) instead of the default note name.
- -m or --mono an optional flag which will make Frettler display its diagrams without colour.
- -c or --chords will calculate and list the diatonic chords in the scale (Chord name and notes).
- -v or --verbose will print out some explantory info about Frettlers decision making, see below.

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

#### Verbose mode
It's all well and good that Frettler can work out the scales and chords for you, but if you want to understand what choices it makes that leads to the notes displayed
and how it identifies the chords, ie its application of music theory, you may like to use the verbose mode occasionally.
With verbose mode on, Frettler will print out a bit of background info to help explain what it is doing.

For example:

```
./frettler guitar horizontal e scale_major --chords --verbose
```

Produces (truncated output here) :

<img src="https://github.com/philwhiles/frettler/blob/master/demo-verbose.png"/>

### Customising your Frettler script
The 'frettler' scripts created by the build, simply pass all arguments to the java program, and if you find yourself regularly using Frettler with say a strings argument
you can make your life easier by creating your own frettler script. Say you want to use drop D tuning all the time :

Create a file in the top frettler directory, and call it 'dropd', with the following content:

#### Linux/macOS
```
#!/bin/bash
java -jar target/frettler-0.1.0-jar-with-dependencies.jar $@ --strings D,A,D,G,B,E
```

Save your dropd file, then back at your terminal prompt, execute :

```
chmod +x dropd
```

#### Windows
```
@echo off
chcp 65001 ^> nul
java -Dfile.encoding=UTF8 -jar ./target/frettler-0.1.0-jar-with-dependencies.jar %%\* --strings D,A,D,G,B,E
```


Then use 'dropd' as an alternative to 'frettler', just drop(pun!) the strings argument you got so tired of typing!

## Reverse Chord Lookup
Frettler also has a 'chord' command. In fact it has two...
#### Print chord name found
The simplest takes the form :


```
./frettler chord As,D,F
A#maj (A# maj) [A#,D,F]
```

- It expects it to be followed by a series of notes, (using its notation for sharps, not flats)
- The notes in this simple form follow the 'chord' keyword immediately.
- The output is simply the printing of the chord found as shown above.

The chord matching can follow one of three rules, using the '--rules [strict,relaxed,loose]' optional param :

- strict
This is the default rule, in which the first note is assumed to be the tonic, and only the chord having all the notes and only the notes will be listed.
If you are wondering about the significance of the tonic, consider Cm7b5 (C m7b5) [C,D#,F#,A#] and D#min6 (D# min6) [D#,F#,A#,C].
```
> ./frettler chord c,e,g
Cmaj   (c chord_maj)   [C(P1), E(M3), G(P5)]
```

- relaxed
The first note is again considered to be the tonic, the listed chords will contain all the provided notes, but not exclusively ie the chords may include other notes.
```
> ./frettler chord c,g -r relaxed
Cmin11   (c chord_min11)   [C(P1), D#(m3), G(P5), A#(m7), D(M9), F#(M11)]
Cdom11   (c chord_dom11)   [C(P1), E(M3), G(P5), A#(m7), D(M9), F#(M11)]
  etc
Cmaj   (c chord_maj)   [C(P1), E(M3), G(P5)]
C5   (c chord_5)   [C(P1), G(P5)]```
```

- loose
None of the notes is considered the sole tonic, and all chords, with any tonic, even those not provided, will be included in the list. This will be a long list if
all you provide is one note.
```
> ./frettler chord c,g -r loose
Cmin11   (c chord_min11)   [C(P1), D#(m3), G(P5), A#(m7), D(M9), F#(M11)]
Cdom11   (c chord_dom11)   [C(P1), E(M3), G(P5), A#(m7), D(M9), F#(M11)]
  etc
  etc
  etc
Am7b5   (a chord_min7flat5)   [A(P1), C(m3), D#(d5), G(m7)]
A#maj6/9   (as chord_maj69)   [A#(P1), D(M3), F(P5), G(M6), C(M9)]
```


#### View the chord found
The second way of getting Frettler to do a reverse chord lookup is to use 'chord' as the keyword immediately following your instrument, as follows :

```
./frettler guitar chord -n As,D,f
```

With this form, frettler will display the chord found using its Vertical view on the selected instrument (and still handles --strings and --frets).
With this command, Frettler will only display the one chord that has only the provided notes, with the tonic being the first.


## Patterns Command
Frettler understands three types of interval patterns - scales, modes and chords. At times it will expect you to provide it one type and not the other. The framework it uses for
parsing the various commands will help you by suggesting the possible type at times, and bash tab completion can help you a lot - if you had typed 'scale' then hit tab, it would list 
only the possible scales you can use (that's why the patterns are prefixed with their type). But if you don't use bash and Frettlers provided tab completion, it might help you to list
the possible patterns occassionally.  This is where the patterns command comes in. Simply use :
```
./frettler patterns
```
and you will get a list of the pattern superset.

Want to just see the list of Frettler understood chords?
```
./frettler patterns -type chord
```
Or 'scale'. Or 'mode'. You get the idea.

## Instruments
A fretboard is a fretboard, and frettler can handle any number of strings with any tuning. For each instrument mentioned it has a default number of strings and their standard tunings.

The instrument 'banjo' will assume the fifth string starts at the fifth fret - if you want the display for a banjo having all strings full length,
just use any instrument other than banjo with --strings A,B,C,etc.

### Chord fingerings
I am working on a version of the vertical view which can display fingering for a given chord. It appears to work for standard six string guitar, open string chords, but for anything else,
it currently gets it wrong. I am finding it difficult to write the code that can make the right decisions, and using a database containing recognised chord fingerings is not an option
when you consider that Frettlers flexibility around the number of strings and their tunings. ie a fingering for Am on a six string guitar would not translate to a 5 string banjo with different
tuning.

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
- change the ANSI colour encoding used to be cross platform using [jansi](https://github.com/fusesource/jansi). If I hear of enough Windows users wanting this...
- chord and note labelling currently only uses sharps - I need to work out how to decide whether each should be labelled as sharp or flat
- blues scale does not support chord generation...
- write some unit tests!

## Contact
If you have any issues with running Frettler, questions or can help providing input or even code, do please get in touch.

email: <phil.whiles@gmail.com>
