@(
    echo off
    setlocal enabledelayedexpansion
)

SET cmds[0]=frettler guitar horizontal C scale_major=Standard guitar, fretboard view of C Major scale
SET cmds[1]=frettler guitar horizontal C scale_major --intervals=Standard guitar, fretboard view of C Major scale, intervals instead of notes
SET cmds[2]=frettler guitar horizontal C scale_major --verbose=Standard guitar, fretboard view of C Major scale, verbose mode on
SET cmds[3]=frettler guitar horizontal C scale_major --chords --verbose=Standard guitar, fretboard view of C Major scale, derive diatonic chords, verbose mode on
SET cmds[4]=frettler chord c,e,g=No instrument, just exact reverse chord lookup
SET cmds[5]=frettler chord c,e --rule relaxed=No instrument, just relaxed reverse chord lookup
SET cmds[6]=frettler chord c,e --rule loose=No instrument, just loose reverse chord lookup
SET cmds[7]=frettler guitar chord -n c,e,g =Standard guitar, lookup chord and display vertically
SET cmds[8]=frettler guitar vertical A scale_minor --chords=Standard guitar, vertical view of A Minor chord, with diatonic chords
SET cmds[9]=frettler guitar vertical C chord_maj=Standard guitar, vertical view of C Major arpeggio 
SET cmds[11]=frettler bassguitar horizontal C scale_major_pentatonic=Bass guitar, fretboard view of C Major Pentatonic scale
SET cmds[12]=frettler guitar horizontal A scale_minor_pentatonic -s B,E,A,D,G,B,E=Am Pentatonic scale on a 7 string guitar
SET cmds[13]=frettler ukelele horizontal A scale_harmonic_minor=Ukelele, fretboard view of Am Harmonic scale 
SET cmds[14]=frettler guitar horizontal C scale_major --intervals=Standard guitar, fretboard view of C Major Scale with intervals
SET cmds[15]=frettler guitar horizontal A scale_minor -i=Again but with abbrev. intervals option, and an Am scale
SET cmds[16]=frettler guitar horizontal C chord_maj=Standard guitar, fretboard view of C Arpeggio
SET cmds[17]=frettler bassguitar horizontal C scale_major_pentatonic=Bass guitar, fretboard view of C Major Pentatonic scale
SET cmds[18]=frettler bassguitar horizontal C scale_major_pentatonic --intervals --frets 17=Bass guitar, fretboard view to 17th fret of C Major Pentatonic Scale, with intervals
SET cmds[19]=frettler banjo horizontal C scale_blues=Banjo, fretboard view of C Blues scale
SET cmds[20]=frettler guitar vertical G chord_maj=Guitar, vertical view of G Major arpeggio

for /L %%i in (0 1 13) do (
    for /F "tokens=1,2 delims==" %%a in ("!cmds[%%i]!") do (
        echo ===================================================================================================================
        echo:
        echo %%i. %%b
        echo:
        echo %%a
        call %%a
    ) 
)
