# Frettler - A CLI for generating musical scales and chords with fretboard and chord rendering for a variety of fretted instruments

## Synopsis
This is a CLI program, written in Java 11, that exposes the results of it's own music theory API through console rendering of a fretboard.
The rendering is just a bunch of System.out.prinln statements, but uses Unicode boxing characters and ANSI colour coding.
Example output can be seen at the bottom of this page. The rendering turned out a lot better than I had hoped tbh!

## Building
The application is built using maven, but you don't need to have maven pre-installed. Just run './build', which will:
- download its own maven
- compile the code
- build an executable fat jar
- create an executable shell command, 'frettler', which is a single relocatable binary that has the jar file embedded within it ie copy frettler to your favourite bin directory

## Execution

### Command Line
Run it using the build executable shell command, frettler, ie :


```
./frettler GUITAR HORIZONTAL SCALE C MAJOR_SCALE

```

- 

### Required Arguments
- #1 GUITAR or BANJO or MANDOLIN or BASSGUITAR or UKELELE
- #2 HORIZONTAL (fretboard view) or VERTICAL (vertical chord diagram which can also display scales)
- #3 SCALE or CHORD
- #4 root note of the scale or chord you want. ie 'C' or 'Ds' - note the 's' indicates a sharp
- #5 the identifier for the scale or chord ie MAJOR_SCALE or MINOR_TRIAD

### Optional Arguments
- -s or --strings followed by your preferred tuning to override the instruments default tuning. Need to use drop D tuning? just use '-s D,A,D,G,B,E'
- -c or --chords as an option when using SCALE and frettler will calculate the chords in that key and display each using the VERTICAL chord view.

### Instruments
A fretboard is a fretboard, and as frettler can handle any number of strings with any tuning. For each instrument mentioned it has a default number of strings and their standard tunings.

The default BANJO will assume the fifth string starts at the sixth fret - if you want the display for a banjo having all strings full length,
just use any instrument other than BANJO with --strings A,B,C,etc.

### Scales
- CHROMATIC_SCALE
- MAJOR_SCALE
- NATURAL_MINOR_SCALE
- MELODIC_MINOR_SCALE
- HARMONIC_MINOR_SCALE
- MAJOR_PENTATONIC_SCALE
- MINOR_PENTATONIC_SCALE
- BLUES_SCALE

### Chords
- MAJOR_TRIAD
- MINOR_TRIAD
- DIMINISHED_TRIAD
- MAJOR_QUADRIAD
- MINOR_QUADRIAD
- DIMINISHED_QUADRIAD
- MINOR_MAJOR_QUADRIAD

### Chord Fingering Calculation
The chord calculation used in the VERTICAL view is definately a work in progress. It appears to work for standard six string guitar, open string chords, but for anything else,
take the chord fingerings calculated with a pinch of salt. I already know its calculation for C Major with a seven string guitar is a bit out of whack, 
and that is probably an indication that it will fall short elsewhere. Any and all contributions to the rules needed to select the appropriate frets for a chord are welcomed.
I think it needs to consider the chord interval progression to the higher string when selecting each strings fret candidate? 

Here are some examples :

<img src="https://github.com/philwhiles/frettler/blob/master/frettler.png"/>


NOTE: the VERTICAL display of SCALE is TBD at this time.


### Tab Completion
If you use bash as your shell, frettler can output a tab completion script to use. Just use the following :

```
source <(./frettler COMPLETIONS)
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

The colours look good in my Eclipse with Dark mode (what programmer doesn't use Dark Mode?!), and they help to scan the notes and intervals
and easily see the patterns. 

The ChordView now calculates the open string fingering for a chord, but can still display all occurences using an alternate method.

## Caveats
This is a work in progress!

### Music theory
Prior to writing this app, my knowledge of music theory was pretty rudimentary. Still is to be honest.
I have been learning to play guitar for the last six months, and have been deliberately not rushing into it as I want to build up my knowledge of music
theory at the same time. I don't want to blindly learn the fingering for various chords without understanding how the scales are constructed, how the chords 
in that scale can be derived, and how to name those chords, and also how the chord fingering is arrived at.
A lot of the theory behind this code is formed from my reading random resources and trying to fit it all together, so forgive me if some of the music domain
names used are suspect, or the rules in my music theory code has some gaps or holes. I am finding this to be a great learning exercise, and I shall get there.

### Chord fingerings
The ChordView still requires some work - it can calculate the chord fingering for fairly standard, open string, major, minor and diminished chords
but I am still working on it - I need to check it's handiwork for a wider variety of chords and confirm that my algorithm for selecting the correct 
fingering works extensively. I havent found any resources online which explain how chord fingerings are derived. Yes, I know it's all about the tonic,
a third and fifth etc, but each string has multiple candidates for each note in a chord. My algorithm favours the higher frets, can exclude strings lower 
than the tonic string, can avoid duplicating the same note in the same octave as it works from the sixth string to the first etc. But I need to put that
theory to the test with a wider set of chords and confirm my assumptions hold water.

## TODO
- Add some Javadoc, for my own sanity if no one elses
- Arpeggios
- Modes
- Extend the types of scales it understands?
- Verify the VERTICAL CHORD fingering output for the more esoteric chords!
- Actually learn guitar!


