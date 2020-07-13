Both the GuitarView and the ChordView can display the notes or intervals with unique ANSI colours, if you are
either running from the command line and using an ANSI colour friendly terminal, or in Eclipse using an ANSI Console
plugin (goto to Eclipse Marketplace and search for 'ANSI console').

The colours look good in my Eclipse with Dark mode (what programmer doesn't use Dark Mode?!), and they help to scan the notes and intervals
and easily see the patterns. 

The ChordView is more a work in progress. I plan to write code to identify the three/four finger chord positions for any given chord, and get it
to display create standard chord diagrams, as an alternative to display each and every occurence of the chords notes.

The Main class is my scratchpad for creating scales, chords and viewing them with the console views.

The ChordView now calculates the open string fingering for a chord, but can still display all occurences using an alternate method.
The open string chord diagrams are a WIP - it can handle the standard cases where the tonic is the lowest open string, but needs some
more work - the B dim chord diagram is concocts has an unplayed middle string!

Here is an example with color taken from the Eclipse ANSI console plugin:

<img src="https://github.com/philwhiles/frettler/blob/master/frettler-ansi.png"/>
