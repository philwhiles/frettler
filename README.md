
Frettler - A flexible command line program for generating and displaying musical scales and chords for guitar or any fretted instrument
=============================

- [Synopsis](#Synopsis)
- [Building](#Building)
- [Demo](#Demo)
- [Tutorial](#Tutorial)
- [Arguments](#Arguments)
  - [Instrument Commands](#Instrument-Commands)
    - [Custom Instrument](#Custom-Instrument)
    - [Required Arguments](#Required-Arguments)
    - [Optional Arguments](#Optional-Arguments)
    - [Verbose Mode](#Verbose-Mode)
    - [Chord Subcommand](#Chord-Subcommand)
      - [Added Notes](#Added-Notes)
    - [Progression Subcommand](#Progression-Subcommand)
    - [Box Subcommand](#Box-Subcommand)
    - [Tab Subcommand](#Tab-Subcommand)
    - [Display Subcommand](#Display-Subcommand)
    - [Find Subcommand](#Find-Subcommand)
  - [Menu Command](#Menu-Command)
  - [Fifths Command](#Fifths-Command)
  - [Lookup Command](#Lookup-Command)
  - [Patterns Command](#Pattern-Command)
  - [Tab Completion Command](#Tab-Completion-Command)
- [Customising Frettler Startup](#Customising-Frettler-Startup)
- [Caveats](#Caveats)
- [Tips](#Tips)
- [Todo](#Todo)
- [Credits](#Credits)
- [License](#License)
- [Contact](#Contact)
  

----------------------------------


## Synopsis
This is a CLI program, written in Java 11, that calculates scales, modes and chords and displays them on fretboard representations.
It comes with out of the box knowledge of the standard tuning and number of strings for guitar, bass guitar, mandolin, ukelele and banjo, but through use
of the optional arguments, can display its full scales and chords for any fretted instrument you may imagine. If your instrument or its tuning is not one
of the out of box instruments, take a look at the [Custom Instrument](#Custom-Instrument)

Frettler performs most of its scale and chord calculations using first principles, only the `chord` subcommand resorts to a database of predetermined fingerings.

**UPDATE** Chord progressions now added

**UPDATE** Box and Tab subcommands have been added which display a vertical and a tabsheet view of an isolated scale sequence.


Frettler can display chord 'fingerings'. ie as an alternative to showing all the occurences of a chords notes on the fret board, frettler can now show a chord as it
is meant to be fingered, like a traditional chord diagram. Read the section below on regarding 'frettler guitar chord'.
With the new chord display comes a change to the normal vertical view also - the vertical and chord views not have the notes/interval aligned on the string, rather than in a fret
box, making the rendering consistent with a typical chord chart, and easy to read.
Adding the `chord` subcommand required renaming some of the existing subcommands to make more sense - `chord` became `lookup` and `find` became `display`.

The internet archive of OLGA provided the bulk of the guitar fingering definitions, which were munged, with corrections, into Frettlers own repesentation, including chords with added notes.
The horizontal and vertical views are also capable of displaying these chords with added notes.

Chord definitions have been added for some of the other Frettler instruments as well, and the chord definitions currently stand at :


| Instrument              | Chord Definitions |
| ----------------------- |-------------------|
| GUITAR                  | 1023              |
| BASSGUITAR              | 96                |
| UKELELE                 | 165               |
| MANDOLIN                | 129               |


For posterity there is a change log [here]

Here is an example of its output :

<img src="https://github.com/philwhiles/frettler/blob/master/images/example.png"/>

## Building
Easily build Frettler from the command line, but there are caveats:
- regardless of the platform you are using your mileage may vary depending on the font your terminal uses. That is out of Frettlers control. I use [GoMono for Poweline](https://github.com/powerline/fonts/blob/master/GoMono/Go%20Mono%20for%20Powerline.ttf)
- if you use windows, by default color support is disabled, but it can be re-enabled. See [below](#Windows).
- you will need the [Java11 JDK](https://adoptopenjdk.net/index.html) installed and in your PATH environment variable

If you are new to git you can either skip using git to download Frettler, and simply click the green 'Code' button, top right, and select the Zip download, unpack the download, then go to 'Building' below
or if you feeling adventurous, you can install [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git), then :

```
git clone https://github.com/philwhiles/frettler.git
```

And then go to the build section below!

The advantage to installing and using git is that you can periodically go back to the folder where you keep your copy of the git code, and auto pull the latest updates then repeat the build to stay up to date
with new features and fixes, using :

```
git pull
./build
```

### Linux/macOS
Frettlers build script is a bash script, and the trickery it perfoms after the maven build to create a single executable, will only work on
Linux/macOS/UNIX.

The application is built using maven, but you don't need to have maven pre-installed. Just run `./build`, which will:
- download its own maven
- compile the code
- build an executable fat jar
- create an executable shell command, 'frettler', which is a single relocatable binary that has the jar file embedded within it ie you can copy frettler to your favourite bin directory

### MS Windows
The windows 'build.bat' will run the maven build, the only prerequisite, as for Linux/macOS, is to have Java 11 installed.
Once Frettler has built the build.bat creates the wrapper cmd file to launch Frettler, 'frettler.cmd'.
The rendering uses Unicode boxing characters and ANSI colour coding, the latter of which does not work in a standard Windows command prompt,
so has been disabled in the generated Windows bat file. If you install an ANSI capable console in Windows (see below), edit the build.bat to re-enable
color support.

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
The default windows command terminal does not support ANSI colour encoding, so Frettler defaults to use the `--mono` output instead. If you want the colourised output, you will have
to install an alternative terminal program such as [ConEmu](https://conemu.github.io) or [Cmder](https://cmder.net/). If you install an ANSI capable console, edit the build.bat and remove
the `--mono`, then rebuild Frettler.

## Demo
Do read the details below to get an understanding of how to drive Frettler, but perhaps first, after building it, try one of the provided demo scripts ('demo' for linux/macOS, 'demo.bat' for windows).
The demos will run Frettler with a variety of arguments, exercising it fully, and displaying the command used to generate each step demonstrated.
The output of the demo command can be seen [Here](https://htmlpreview.github.io/?https://github.com/philwhiles/frettler/blob/master/demo.html)

## Tutorial
Similar to the demo, but interactive, try the `tutorial` script. It will execute a number of frettler invocations, pausing after each with the last invocation it ran ready for you to edit and experiment with.
After your edit, press return to see the result. Once you have tired of each example, simply delete the example invocation shown and press return to move to the next step.

## Arguments
Frettler has two ways of viewing entire scales/modes and chords. One is horizontal, the other is vertical. Your mileage may vary, so you get to choose the one your prefer!
Each view can be used to display a scale or chord in its entirety, and each default to showing the first 12 frets.

Frettlers first argument can either be an instrument, in which case the following commands must follow the instrument conventions below, or it can be one of its secondary, ancilliary, arguments,
such as 'patterns' or 'lookup', each of which have expectations for the subsequent arguments and options.


### Instrument Commands
The following instruments are supported:

- guitar
- bassguitar
- ukelele
- mandolin
- banjo
- custom

A fretboard is a fretboard, and frettler can handle any number of strings with any tuning. For each instrument mentioned it has a default number of strings and their standard tunings.

The instrument `banjo` will assume the fifth string starts at the fifth fret - if you want the display for a banjo having all strings full length,
just use any instrument other than banjo with `--strings A,B,C,etc`.

#### Custom Instrument
The `custom` instrument differs from the others, in that Frettler has no hard wired understanding of how many strings it has or what their tunings are.
There is text file called 'custom.properties' in the root folder of Frettler, which defines the name of the custom instrument, its strings/tunings and the number of frets you want displayed 
by default. If you utilise the `custom` instrument, the `--strings` and `--frets` optional arguments will be ignored.
The 'name' property will be used in Frettlers titling of scales and chords.

So with the example 'custom.properties' provided :

```
name=Irish Bouzouki
strings=G,D,A,D
frets=12
```

With :

```
./frettler custom horizontal c scale_major
```

You will get :

<img src="https://github.com/philwhiles/frettler/blob/master/images/irish-bouzouki.png"/>


#### Required Arguments 
When you want Frettler to display a scale or chord on a fretboard, in this order:

1. Instrument - `custom`, `guitar`, `banjo`, `mandolin`, `bassguitar` or `ukelele`
1. SubCommand - see subcommands such as `horizontal`, `chord`, `progression` in the sub sections below
1. Root - the note of the scale or chord you want. ie `C` or `Ds` or `Eb` - note the 's' indicates a sharp, the 'b' indicates a flat.
1. Pattern - for the scale or chord ie `scale_major` or `chord_min9`. 

The patterns Frettler understands at the time of writing are:

| Scales/Modes            | Chords            | Chords            |
| ----------------------- |-------------------|-------------------|
| scale_major             | chord_min11       | chord_dom7        |
| mode_ionian(a)          | chord_dom11       | chord_7(a)        |
| scale_melodic_minor     | chord_dom9        | chord_min6        |
| scale_major_pentatonic  | chord_7sharp9     | chord_maj6        |
| scale_minor             | chord_7flat9      | chord_minmaj7     |
| scale_harmonic_minor(a) | chord_maj69       | chord_min7flat5   |
| mode_aeolian(a)         | chord_maj6min7    | chord_7plus       |
| scale_minor_pentatonic  | chord_maj9        | chord_min7sus4    |
| scale_blues_major       | chord_min9        | chord_add9        |
| scale_blues_minor       | chord_dim7        | chord_add11       |
| mode_dorian             | chord_7flat5      | chord_aug         |
| mode_mixolydian         | chord_min7        | chord_dim         |
| mode_lydian             | chord_aug7        | chord_min         |
| mode_ionian             | chord_maj7        | chord_sus2        |
| mode_locrian            | chord_maj         | chord_sus4        |
|                         |                   | chord_5                          |

(Frettler understands interval pattern aliases, so in the above, those denoted as (a) are an alias for the preceeding pattern)

To see the patterns Frettler currently understands use the [Patterns Command](#Patterns-Command)

#### Optional Arguments 
- `-s` or `--strings` followed by your preferred tuning to override the instruments default tuning. Need to use drop D tuning? just use `-s D,A,D,G,B,E`.
(If you regularly want to use a non default string setup, see below)
- `-f` or `--frets` N will display either horizontal or vertical views with N frets instead of the default 12 for each instrument.
- `-i` or `--intervals` an optional flag which makes Frettler display the note intervals(*) instead of the default note name.
- `-m` or `--mono` an optional flag which will make Frettler display its diagrams without colour.
- `-c` or `--chords` will calculate and list the diatonic chords in the scale (Chord name and notes).
- `-v` or `--verbose` will print out some explanatory info about Frettlers decision making, see below.
- `-o` or `--octaves` will colourize the notes or intervals according to the relative octave (**)

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

(**) The octave colouring is based on the frets octave, relative to the other frets on the fretboard, and not relative to piano octaves.

#### Verbose Mode
It's all well and good that Frettler can work out the scales and chords for you, but if you want to understand what choices it makes that leads to the notes displayed
and how it identifies the chords, ie its application of music theory, you may like to use the verbose mode occasionally.
With verbose mode on, Frettler will print out a bit of background info to help explain what it is doing.

For example:

```
./frettler guitar horizontal e scale_major --chords --verbose
```

Produces (truncated output here) :

<img src="https://github.com/philwhiles/frettler/blob/master/images/verbose.png"/>

#### Chord Subcommand 
The `chord` mode displays a more traditional chord chart and differs from the `vertical` view in that it displays filled circles for each note, and gives a summary below
the chart aligned with the strings, showing their notes and intervals.
While the rest of frettlers calculations are all done from first principles of music theory ie scales and fully displayed chords are calculated using music theory
formulae/logic, it can display chord fingerings using a vertical view that resembles typical chord charts, using a built in database of 'fingerings' (currently in excess of 1000).
ie to get frettler to show the fingering for 'Am':

```
./frettler guitar chord a chord_min
```

Produces:

<img src="https://github.com/philwhiles/frettler/blob/master/images/chord.png"/>

If it is a barre chord, then frettler will show you:

<img src="https://github.com/philwhiles/frettler/blob/master/images/barre.png"/>

The `chord` mode also allows use of the `--octaves` and `--mono` arguments.

If you would like to see which chords frettler can show you for a given root, ie for A, use :

```
./frettler guitar chord a --list
Asus2   (a chord_sus2)    [A(P1), B(M2), E(P5)] 
Asus4/B   (a chord_sus4/B)    [A(P1), D(P4), E(P5)] 
Asus4/C   (a chord_sus4/C)    [A(P1), D(P4), E(P5)] 
Asus4   (a chord_sus4)    [A(P1), D(P4), E(P5)] 
A5   (a chord_5)    [A(P1), E(P5)] 
etc
etc
```

If you want to see what chord definitions Frettler has for a specific chord type, add the interval pattern name as well :

```
./frettler guitar chord d chord_dim --list
Ddim/C   (d chord_dim/C)    [D(P1), F(m3), G#(d5)] 
Ddim/B   (d chord_dim/B)    [D(P1), F(m3), G#(d5)] 
Ddim   (d chord_dim)    [D(P1), F(m3), G#(d5)] 
Ddim/Bb   (d chord_dim/Bb)    [D(P1), F(m3), G#(d5)] 
```

Note that the chord definitions found include those with added notes.

The database currently contains over 1000 chords for guitar, six string, standard tuning.
If you currently choose an instrument other than 'guitar' or use the '--strings' argument, frettler will politely exit, as it will not have a chord definition for that
instrument/tuning combination. Until you email it to me and I add it to Frettler for you!

The 'chord' feature is fairly new, and over time I would like to build up the data in the database such that it can display chord fingerings for a wider selection of guitar chords,
but also chords for other instruments, and with different tunings.

If you would like to have additional chord fingerings added, email me with the following info, and I will gladly add them for you:

- Instrument: Guitar
- Tuning: EADGBE
- Root: A
- Chord: CHORD_MIN
- Frets: x,0,2,2,1,0
- Fingering: x,x,2,3,1,x

'Frets' is... the frets to be pressed (where 0 is open string), while 'Fingering is, well you get it. Currently frettler does not use the fingering data, but it exists in the database for the currently
known chords, and in time I will add an optional argument and the wherewithall to display finger numbers in the chart instead of the note or interval.

##### Added notes
With the `chord` mode, Frettler does have _some_ definitions of standard chords with added bass notes. Use the optional `-added' argument followed by the added note.

#### Progression Subcommand 
When using the `horizontal` or `vertical` subcommands for an instrument to display a scale, you can use the `--chords` option to get Frettler to print out the chords in that scale, but the `progression`
subcommand takes this functionality one step further, combining it with the ability to display chord fingerings for the nominated chords in a scale.

```
./frettler guitar progression a scale_major -p 1,4,5
```

Will show the default chord fingerings for each of the I, IV and V chords in the scale of A Minor
The chord fingerings will be the default from possibly many variations for the root and chord type, usually the simpler cowboy chord fingerings. If you want to see the other variants, use the `chord` subcommand
above individually.

#### Box Subcommand
Frettler can show a 'boxed' view of a scale, centered around one part of the fretboard, for practising. This has only recently been added, and requires a few tweaks to its note selection, so pull the code
periodically and check for updates. It looks at the scale you want boxing, and displays the notes in the scale sequence, by default starting the box sequence at the first occurence of the scales root note on
the lowest string and then finding the following notes (2 for diatonic scales or 3 for pentatonic scales), on the same string before moving to the next highest string. This produces the typical scale boxes that 
you see on various sites and tutorials. 

In the `box` view, the root of the scale is displayed inverted, to make it stand out. This is because there are two optional arguments you can use with the `box` subcommand, which make the sequence of the scales
notes different, and keeping sight of the root helps when practicing:

`--group` or `-g` followed by a number determines the number of notes to look for on each string, before moving to the next string. If not used, the default group is 0.
`--position` or `-p` followed by a number determines where the box sequence starts in the scale. If not used, it defaults to 1, meaning the sequence starts on the root or P1. Use `-p 2` and the sequence
will start on the 2nd note of the scale.

Here is an example of a boxed diatonic chord, using `./frettler guitar box c scale_major` :

<img src="https://github.com/philwhiles/frettler/blob/master/images/diatonic-box.png"/>

And here is an example of a boxed diatonic chord, using `./frettler guitar box c scale_major_pentatonic` :

<img src="https://github.com/philwhiles/frettler/blob/master/images/pentatonic-box.png"/>

The idea for the boxing came from an experiment with practice sequences, which first triggered the development of the `tab` subcommand. I tried a wide variety of practise sequences as used in the
open source project 'Fretboard' (see credits), but the results were quite mixed. The jury is out on those sequences, but I might resurrect those later.

#### Tab Subcommand
As mentioned above, the `tab` subcommand came about from experimenting with sequences, but they were parked for the time being. But I realised that the `tab` might be useful for practising the boxed scales 
so it stayed. If you want to see a standard tab for a boxed scale, just use `./frettler guitar box c scale_major` :

<img src="https://github.com/philwhiles/frettler/blob/master/images/tab.png"/>

Just like the `box` subcommand, the `--group` and `--position` options will move the tab sequence around for you.

#### Display Subcommand
Frettler can display all occurences of arbitrary notes on the fretboard for you with the `display` command. ie to see all occurences of the notes
c and g, try:

```
./frettler guitar display --notes c,g
```
#### Find Subcommand
One way of getting Frettler to do a reverse chord lookup is to use `find` as the keyword immediately following your instrument. In fact there are two ways to get frettler to `find` chords:

#### Find by note
```
./frettler guitar find --notes As,D,f
```

With this form, frettler will display the chord found using its Vertical view on the selected instrument ().
With this command, Frettler will only display the one chord that has only the provided notes, with the tonic being the first.

#### Find by fingering
Find and display the chord chart matching the provided fret numbering.

```
./frettler guitar find --digits x,0,2,2,1,0
```

Using this command, Frettler will search its chord definitions, looking for a match, instead of dynamically calculating the entire chord across the fretboard and looking for a note match.
I would have used `--fingers` but `--frets` was there first, and as the shortform for that is `-f`, I opted for `digits` instead. Only so many options in the alphabet!

### Menu Command
Start Frettler with the menu argument and it will offer a simple one line menu. Depending on your key presses it will display your fretboard over and over, each time 
reflecting your choices. It will become apparent when you start it ie `./frettler menu`:

<img src="https://github.com/philwhiles/frettler/blob/master/images/demo-menu.png"/>

All the usual instrument mode optional arguments can be used when first starting Frettler in menu mode (`--intervals` `--verbose` `--chords` `--mono` `--octaves` `--strings` `--frets`). 
The first three of those can be toggled with key presses once in menu mode, while the others will be used constantly.

#### Fifths Command
Frettler generates dynamically a representation of the Circle Of Fifths, that it uses to determine if scales should use sharps or flats as the accidentals.
It is perhaps better described as a Line Of Fifths, due to its somewhat flat structure, and the clockwise and anticlockwise arms don't overlap, but the `fifths` command
will print this out for you if your curious or want to refer to it.

#### Lookup Command
Frettler also has a chord reverse `lookup` command. 
The simplest takes the form :


```
./frettler lookup As,D,F
A#maj (A# maj) [A#,D,F]
```

- It expects it to be followed by a series of notes, (using its notation for sharps or flats)
- The notes in this simple form follow the `chord` keyword immediately.
- The output is simply the printing of the chord found as shown above.

The chord matching can follow one of three rules, using the `--rules [strict,relaxed,loose]` optional param :

- `strict`
This is the default rule, in which the first note is assumed to be the tonic, and only the chord having all the notes and only the notes will be listed.
If you are wondering about the significance of the tonic, consider Cm7b5 (C m7b5) [C,D#,F#,A#] and D#min6 (D# min6) [D#,F#,A#,C].

```
> ./frettler lookup c,e,g
Cmaj   (c chord_maj)   [C(P1), E(M3), G(P5)]
```

- `relaxed`
The first note is again considered to be the tonic, the listed chords will contain all the provided notes, but not exclusively ie the chords may include other notes.

```
> ./frettler lookup c,g -r relaxed
Cmin11   (c chord_min11)   [C(P1), D#(m3), G(P5), A#(m7), D(M9), F#(M11)]
Cdom11   (c chord_dom11)   [C(P1), E(M3), G(P5), A#(m7), D(M9), F#(M11)]
  etc
Cmaj   (c chord_maj)   [C(P1), E(M3), G(P5)]
C5   (c chord_5)   [C(P1), G(P5)]```
```

- `loose`
None of the notes is considered the sole tonic, and all chords, with any tonic, even those not provided, will be included in the list. This will be a long list if
all you provide is one note.

```
> ./frettler lookup c,g -r loose
Cmin11   (c chord_min11)   [C(P1), D#(m3), G(P5), A#(m7), D(M9), F#(M11)]
Cdom11   (c chord_dom11)   [C(P1), E(M3), G(P5), A#(m7), D(M9), F#(M11)]
  etc
  etc
  etc
Am7b5   (a chord_min7flat5)   [A(P1), C(m3), D#(d5), G(m7)]
A#maj6/9   (as chord_maj69)   [A#(P1), D(M3), F(P5), G(M6), C(M9)]
```

#### Patterns Command
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

#### Tab Completion Command
If you use bash as your shell, frettler can output a tab completion script to use. Just use the following :

```
source <(./frettler completions)
```

Tab completion in bash helps greatly with Frettler - bash will complete all of the args for you and show you the possible completions, handy with the Frettler interval pattern names.
The interval patterns all have a prefix or either 'scale_', 'mode_' or 'chord_' largely to allow the tab completion to show you just the selection of patterns that is relevant to you.

I use macOS for all my work and play, and used linux for many years prior to that. I use the bash shell exclusively (v5.0.7 installed via Homebrew).
I like to make my life as easy as possible and spend a huge amount of time at the command line - one of the reasons Frettler doesn't have a GUI I guess!
I keep lots of random personal utils in a bin folder off my home folder, which is in my PATH and executable from any folder in the command line.
I keep a copy of Frettler in ~/bin and in my ~/.bashrc I also have :

```
alias f='frettler'
source <(~bin/frettler completion)
complete -F _frettler_completions f
```

Which ensures that whenever I start a new shell session with bash, the completions are loaded, I have an alias for frettler, so
I can just use 'f' instead of 'frettler' and the completions still work if I need them with the alias.
I also use the single letter short cuts (see below) in frettler as an alternative to using tab completion - I have tried to make Frettler as
easy and as helpful as I can! So I often end up using something like :

```
f g c c chord_maj
```

instead of :

```
frettler guitar chord c chord_maj
```

If you use Frettler for a while, once you have learned its command line verbs, using shortcuts comes more naturall.

## Customising Frettler Startup
If the [custom instrument](#Custom-Instrument) doesn't do it for you, you can customise Frettler by writing a wrapper script.
If you find yourself regularly using Frettler with the same old optional arguments, say the `--strings` you can make your life easier by creating your own script wrapping the 
frettler script/command. Say you want to use drop D tuning all the time :

Create a file in the top frettler directory, and call it 'dropd', with the following content:

### Linux/macOS customisation
```bash
#!/bin/bash
./frettler $@ --strings D,A,D,G,B,E
```

Save your dropd file, then back at your terminal prompt, execute :

```bash
chmod +x dropd
```

### Windows customisation
```Batchfile
@echo off
frettler.bat %%\* --strings D,A,D,G,B,E
```


Then use 'dropd' as an alternative to 'frettler', just drop(pun!) the strings argument you got so tired of typing!


And if you prefer menu mode and want to use a custom tuning, number of frets etc:
### Linux/macOS
```bash
#!/bin/bash
./frettler menu $@ --strings D,A,D,G,B,E
```

There is also a small bash script provided that will iterate through a set of notes and collect frettlers output for all their scales  
To run it simply provide the scale type you want :
```bash
#!/bin/bash
./scales scale_major
```

The output will be written to 'scale_major.out'. I wrote this as I wanted to print out some scale sheets for practising. The output is by default coloured, so if printing that does not work,
add the `--mono' option to the frettler command in the 'scales' script. I have some local tricks to convert it to html which I then open in safari and print, but those tricks are peculiar to the
editor I use - Vim. If you use Vim, try the plugin [AnsiEsc](http://www.drchip.org/astronaut/vim/#ANSIESC) and then use ":TOhtml".

## Caveats
This is a work in progress, pretty much like the authors understanding of music theory.
Contributions and advice are welcomed. Frettler was initially created as an exercise in learning music theory and the author still has a long way to go.

## Tips
Left to the end to avoid confusion and detracting from the understanding of the arguments required.

#### Abbreviations
The Instrument argument can be abbreviated, as long as the abbreviation is unique within the set of instruments. ie you can use `g`, but you must use `ban` or `bas`.
The View argument can only be abbreviated to `h` or `v`.

#### Defaults
All the arguments except for the instrument, have default values. This means you can incrementally drop the arguments from right to left, but cannot drop one in the middle.
So './frettler guitar' or even './frettler g', would do the same as './frettler guitar horiontal C scale_major'.
And './frettler g v', would do the same as './frettler guitar vertical C scale_major'.

## Todo
- change the ANSI colour encoding used to be cross platform using [jansi](https://github.com/fusesource/jansi). If I hear of enough Windows users wanting this...
- blues scale does not support chord generation...

## Credits
- The chord fingerings in the ChordBank class were initially seeded from a dataset within [fusionproggy/Fretboard](https://github.com/fusionprogguy/Fretboard)
- The chord fingerings were given a huge boost by a transformation of the data found in the web archive of [OLGA](https://web.archive.org/web/20060610121532/http://www.olga.net/)

## License
This work is provided as is, with no warranties or guarantees, and is subject to the [GNU Affero General Public License v3.0](https://github.com/philwhiles/frettler/blob/master/LICENSE)

## Contact
If you have any issues with running Frettler, questions or can help providing input, chord fingerings or even code, do please get in touch.
And if you like Frettler, star the GitHub repo, top right of the page, and I shall at least get the satisfaction of knowing that you like and use Frettler.

email: <phil.whiles@gmail.com>
