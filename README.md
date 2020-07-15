# Frettler - A CLI for generating musical scales and chords with guitar fretboard and chord rendering

## Synopsis
This is a CLI program, written in Java 11, that exposes the results of it's own music theory API through console rendering of a guitar fretboard,
The rendering is just a bunch of System.out.prinln statements, but uses Unicode boxing characters and ANSI colour coding.
Example output can be seen at the bottom of this page. The rendering turned out a lot better than I had hoped tbh!

## Building
The application is built using maven, but you don't need to have maven pre-installed - you can build an executable jar that will be
used by the included bash wrapper, with the following

```
./build

```

## Execution

### Command Line
Run it using the provided shell wrapper, frettler, ie :

```
./frettler GUITAR HORIZONTAL CHORD D MINOR_TRIAD

```

Or: 

```
./frettler GUITAR HORIZONTAL SCALE D HARMONIC_MINOR -l INTERVALS -d MONO
```

Or:

```
./frettler GUITAR VERTICAL CHORD D MINOR_TRIAD
```

NOTE: the VERTICAL display of SCALE is TBD at this time.

NOTE: the CLI arguments are handled by the Java framework, picocli, added to the codebase in the space of one evening (after actually practising guitar for 2 hours!),
as well as the maven fat jar executable generation and the maven wrapper for building, and the bash wrappers. 
This will all no doubt go through some further refinements soon to make it easier to use!

### Programmatically
If you want to you can write your own Main class and create a Guitar object, create a Scale or Chord object, create a Guitar and a view for that Guitar
and then instruct the View to display your chord or object. The API is pretty straightfoward I think, and defaults safely to a standard tuning EADGBE six 
string guitar etc
Have a look at the GuitarCommand for some examples of usage, look at the constructors of the various classes such as Guitar, Scale, Chord and the ChordView and 
GuitarView classes, and their public methods.

## Engine
The engine can generate Lists of notes that represent given scales, and can calculate the chords within that scale.
The engine knows nothing about a guitar, it simply applies music theory to generate Java lists of the notes in scales and chords.

## Instrument
Yeah - just guitar. The Guitar class understands the fretboard layout of a guitar having any number of strings with any given tuning.
It also contains a Tone class which represents an individual fret position on that guitar.

## View
Currently only console views, each constructed with a Guitar, which take the scale and chord constructs from the engine, and render them
on that guitars fretboard, up to the 12th fret.

Both the GuitarView and the ChordView can display the notes or intervals with unique ANSI colours, if you are
either running from the command line and using an ANSI colour friendly terminal, or in Eclipse using an ANSI Console
plugin (goto to Eclipse Marketplace and search for 'ANSI console').

The colours look good in my Eclipse with Dark mode (what programmer doesn't use Dark Mode?!), and they help to scan the notes and intervals
and easily see the patterns. 

The ChordView now calculates the open string fingering for a chord, but can still display all occurences using an alternate method.

## Caveats!

### General
Prior to writing this app, my knowledge of music theory was pretty rudimentary. Probably still is if I'm being honest.
I have been learning to play guitar for the last six months, and have been deliberately not rushing into it as I want to build up my knowledge of music
theory at the same time. I don't want to blindly learn the fingering for various chords without understanding how the scales are constructed, how the chords 
in that scale can be derived, and how to name those chords, and also how the chord fingering is arrived at.
A lot of the theory behind this code is formed from my reading random resources and trying to fit it all together, so forgive me if some of the music domain
names used are suspect, or the rules in my music theory code has some gaps or holes. I am finding this to be a great learning exercise, and I shall get there.
I hope!

### Chord fingerings
The ChordView may still require some work - it can calculate the chord fingering for fairly standard, open string, major, minor and diminished chords
but I am still working on it - I need to check it's handiwork for a wider variety of chords and confirm that my algorithm for selecting the correct 
fingering works extensively. I havent found any resources online which explain how chord fingerings are derived. Yes, I know it's all about the tonic,
a third and fifth etc, but each string has multiple candidates for each note in a chord. My algorithm favours the higher frets, can exclude strings lower 
than the tonic string, can avoid duplicating the same note in the same octave as it works from the sixth string to the first etc. But I need to put that
theory to the test with a wider set of chords and confirm my assumptions hold water.

## TODO
- Refine the CLI argument handling to expose the generation and display of all chords in a given key
- Extend the types of scales it understands
- Refine the CLI argument handling to expose the ability to use non standard tuning (the current Guitar class and its two view
classes can handle that already if you use it programmatically)
- Add the ability to have for instance a 7 string guitar
- Write the VERTICAL SCALE view and expose it through the CLI 
- Verify its VERTICAL CHORD fingering output for the more esoteric chords!
- Maybe ... add a Keyboad instrument class and corresponding Views. Maybe.

## Output

Here is an example with color taken from the Eclipse ANSI console plugin:

<img src="https://github.com/philwhiles/frettler/blob/master/frettler.png"/>
