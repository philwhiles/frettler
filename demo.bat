@(
    echo off
    setlocal enabledelayedexpansion
)

SET cmds[0]=frettler guitar horizontal C scale_major=Standard guitar, horizontal fretboard view of C Major scale
SET cmds[1]=frettler guitar horizontal C scale_major --lefty=Standard guitar, horizontal fretboard view of C Major scale, for a left handed guitar
SET cmds[2]=frettler guitar horizontal C scale_major --lefty --tuning guitar_drop_d=Standard guitar, horizontal fretboard view of C Major scale, for a left handed guitar using drop D tuning
SET cmds[3]=frettler guitar horizontal C scale_major --intervals=Standard guitar, fretboard view of C Major scale, intervals instead of notes
SET cmds[4]=frettler guitar vertical D scale_minor=Standard guitar, vertical fretboard view of D Minor scale
SET cmds[5]=frettler guitar chord a chord_min=Standard guitar, chord chart of Am
SET cmds[6]=frettler guitar chord a chord_min --lefty --easy=Standard guitar, cowboy/default chord chart of Am for a left handed player
SET cmds[7]=frettler guitar chord a chord_min -i=Standard guitar, chord chart of Am, intervals instead of notes
SET cmds[8]=frettler guitar horizontal C scale_major --verbose=Standard guitar, fretboard view of C Major scale, verbose mode on
SET cmds[9]=frettler guitar horizontal C scale_major --chords --verbose=Standard guitar, fretboard view of C Major scale, derive diatonic chords, verbose mode on
SET cmds[10]=frettler guitar horizontal C scale_major --octaves=Standard guitar, fretboard view of C Major scale, colourizing the fretboard relative octaves
SET cmds[11]=frettler guitar display --notes c=Standard guitar, just show all occurences of note 'C'
SET cmds[12]=frettler fifths=Display the Line Of Fifths generated and used internally by Frettler
SET cmds[13]=frettler lookup --notes c,e,g=No instrument, just exact reverse chord lookup
SET cmds[14]=frettler lookup --notes c,e --rule relaxed=No instrument, just relaxed reverse chord lookup
SET cmds[15]=frettler lookup --notes c,e --rule loose=No instrument, just loose reverse chord lookup
SET cmds[16]=frettler guitar find -n c,e,g =Standard guitar, lookup chord and display vertically
SET cmds[17]=frettler guitar vertical A scale_minor --chords=Standard guitar, vertical view of A Minor chord, with diatonic chords
SET cmds[18]=frettler guitar vertical C chord_maj=Standard guitar, vertical view of C Major arpeggio 
SET cmds[19]=frettler bassguitar horizontal C scale_major_pentatonic=Bass guitar, fretboard view of C Major Pentatonic scale
SET cmds[20]=frettler guitar horizontal A scale_minor_pentatonic -s B,E,A,D,G,B,E=Am Pentatonic scale on a 7 string guitar
SET cmds[21]=frettler ukelele horizontal A scale_harmonic_minor=Ukelele, fretboard view of Am Harmonic scale 
SET cmds[22]=frettler guitar horizontal C scale_major --intervals=Standard guitar, fretboard view of C Major Scale with intervals
SET cmds[23]=frettler guitar horizontal A scale_minor -i=Again but with abbrev. intervals option, and an Am scale
SET cmds[24]=frettler guitar horizontal C chord_maj=Standard guitar, fretboard view of C Arpeggio
SET cmds[25]=frettler bassguitar horizontal C scale_major_pentatonic=Bass guitar, fretboard view of C Major Pentatonic scale
SET cmds[26]=frettler bassguitar horizontal C scale_major_pentatonic --intervals --frets 17=Bass guitar, fretboard view to 17th fret of C Major Pentatonic Scale, with intervals
SET cmds[27]=frettler banjo horizontal C scale_blues_major=Banjo, fretboard view of C Blues Major scale
SET cmds[28]=frettler guitar vertical G chord_maj=Guitar, vertical view of G Major arpeggio
SET cmds[29]=frettler ukelele progression c scale_major --p 1,4,5=Display the chords for a I,IV,V progression in C Major for a Ukelele
SET cmds[30]=frettler guitar box c scale_major=Display the first practise box for C Major for a Guitar
SET cmds[31]=frettler patterns -t scale=Display the names of the scales that Frettler supports
SET cmds[32]=frettler patterns -t chord=Display the names of the chords that Frettler supports
SET cmds[33]=frettler tunings=Display the names of the tunings that Frettler supports

for /L %%i in (0 1 33) do (
    for /F "tokens=1,2 delims==" %%a in ("!cmds[%%i]!") do (
        echo ===================================================================================================================
        echo:
        echo %%i. %%b
        echo:
        echo %%a
        call %%a
    ) 
)
