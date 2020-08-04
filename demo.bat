@(
    echo off
    setlocal enabledelayedexpansion
)

SET cmds[0]=frettler guitar horizontal C major_scale=Standard guitar, fretboard view of C Major scale
SET cmds[1]=frettler guitar vertical C major_triad=Standard guitar, vertical view of C Major arpeggio
SET cmds[2]=frettler guitar vertical C major_triad --chord=Standard guitar, vertical view of C Major chord 
SET cmds[3]=frettler bassguitar horizontal C major_pentatonic_scale=Bass guitar, fretboard view of C Major Pentatonic scale
SET cmds[4]=frettler guitar horizontal A minor_pentatonic_scale -s B,E,A,D,G,B,E=Am Pentatonic scale on a 7 string guitar
SET cmds[5]=frettler ukelele horizontal A harmonic_minor_scale=Ukelele, fretboard view of Am Harmonic scale 
SET cmds[6]=frettler guitar horizontal C major_scale --intervals=Standard guitar, fretboard view of C Major Scale with intervals
SET cmds[7]=frettler guitar horizontal C major_scale -i=Again but with abbrev. intervals option
SET cmds[8]=frettler guitar horizontal C major_triad=Standard guitar, fretboard view of C Arpeggio
SET cmds[9]=frettler bassguitar horizontal C major_pentatonic_scale=Bass guitar, fretboard view of C Major Pentatonic scale -m
SET cmds[10]=frettler bassguitar horizontal C major_pentatonic_scale --intervals --frets 17=Bass guitar, fretboard view to 17th fret of C Major Pentatonic Scale, with intervals
SET cmds[11]=frettler banjo horizontal C blues_scale=Banjo, fretboard view of C Blues scale
SET cmds[12]=frettler guitar vertical G major_triad=Guitar, vertical view of G Major arpeggio
SET cmds[13]=frettler guitar vertical G major_triad -c=Guitar, vertical view of G Major chord fingering
SET cmds[14]=frettler guitar horizontal C major_scale -c=Grand finale! Standard guitar, fretboard view of C Major Scale accompanied by chord diagrams of its diatonic chords

for /L %%i in (0 1 14) do (
    for /F "tokens=1,2 delims==" %%a in ("!cmds[%%i]!") do (
        echo -------------------------------------------------------------------------------------------------------------------
        echo:
        echo %%i. %%b
        echo:
        echo %%a
        call %%a
    ) 
)

echo Windows does not support ANSI colour encoding by default - see the README for alternative colour capable consoles
