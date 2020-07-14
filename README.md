# Frettler - A Java 11 musical scale and chord generator with guitar fretboard and chord rendering

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

CAVEAT EMPTOR
The ChordView may still require some work - it can calculate the chord fingering for fairly standard, open string, major, minor and diminished chords
but I am still working on it - I need to check it's handiwork for a wider variety of chords and confirm that my algorithm for selecting the correct 
fingering works extensively. I havent found any resources online which explain how chord fingerings are derived. Yes, I know it's all about the tonic,
a third and fifth etc, but each string has multiple candidates for each note in a chord. My algorithm favours the higher frets, can exclude strings lower 
than the tonic string, can avoid duplicating the same note in the same octave as it works from the sixth string to the first etc. But I need to put that
theory to the test with a wider set of chords and confirm my assumptions hold water.

## Main
The Main class is my scratchpad for creating scales, chords and viewing them with the console views.


Here is an example with color taken from the Eclipse ANSI console plugin:

<img src="https://github.com/philwhiles/frettler/blob/master/frettler.png"/>
