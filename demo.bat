@(
    echo off
    setlocal enabledelayedexpansion
)

SET cmds[0]=frettler guitar horizontal C scale_major=Standard guitar, horizontal fretboard view of C Major scale
SET cmds[1]=frettler guitar horizontal C scale_major --intervals=Standard guitar, fretboard view of C Major scale, intervals instead of notes
SET cmds[2]=frettler guitar vertical D scale_minor=Standard guitar, vertical fretboard view of D Minor scale
SET cmds[3]=frettler guitar chord a chord_min=Standard guitar, chord chart of Am
SET cmds[4]=frettler guitar chord a chord_min -i=Standard guitar, chord chart of Am, intervals instead of notes
SET cmds[5]=frettler guitar horizontal C scale_major --verbose=Standard guitar, fretboard view of C Major scale, verbose mode on
SET cmds[6]=frettler guitar horizontal C scale_major --chords --verbose=Standard guitar, fretboard view of C Major scale, derive diatonic chords, verbose mode on
SET cmds[7]=frettler guitar horizontal C scale_major --octaves=Standard guitar, fretboard view of C Major scale, colourizing the fretboard relative octaves
SET cmds[8]=frettler guitar display --notes c=Standard guitar, just show all occurences of note 'C'
SET cmds[9]=frettler fifths=Display the Line Of Fifths generated and used internally by Frettler
SET cmds[10]=frettler lookup c,e,g=No instrument, just exact reverse chord lookup
SET cmds[11]=frettler lookup c,e --rule relaxed=No instrument, just relaxed reverse chord lookup
SET cmds[12]=frettler lookup c,e --rule loose=No instrument, just loose reverse chord lookup
SET cmds[13]=frettler guitar find -n c,e,g =Standard guitar, lookup chord and display vertically
SET cmds[14]=frettler guitar vertical A scale_minor --chords=Standard guitar, vertical view of A Minor chord, with diatonic chords
SET cmds[15]=frettler guitar vertical C chord_maj=Standard guitar, vertical view of C Major arpeggio 
SET cmds[16]=frettler bassguitar horizontal C scale_major_pentatonic=Bass guitar, fretboard view of C Major Pentatonic scale
SET cmds[17]=frettler guitar horizontal A scale_minor_pentatonic -s B,E,A,D,G,B,E=Am Pentatonic scale on a 7 string guitar
SET cmds[18]=frettler ukelele horizontal A scale_harmonic_minor=Ukelele, fretboard view of Am Harmonic scale 
SET cmds[19]=frettler guitar horizontal C scale_major --intervals=Standard guitar, fretboard view of C Major Scale with intervals
SET cmds[20]=frettler guitar horizontal A scale_minor -i=Again but with abbrev. intervals option, and an Am scale
SET cmds[21]=frettler guitar horizontal C chord_maj=Standard guitar, fretboard view of C Arpeggio
SET cmds[22]=frettler bassguitar horizontal C scale_major_pentatonic=Bass guitar, fretboard view of C Major Pentatonic scale
SET cmds[23]=frettler bassguitar horizontal C scale_major_pentatonic --intervals --frets 17=Bass guitar, fretboard view to 17th fret of C Major Pentatonic Scale, with intervals
SET cmds[24]=frettler banjo horizontal C scale_blues_major=Banjo, fretboard view of C Blues Major scale
SET cmds[25]=frettler guitar vertical G chord_maj=Guitar, vertical view of G Major arpeggio

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
