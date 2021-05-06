# 6th May 2021
- Build updated - now Java 16

# 2nd May 2021
- Added out of the box `--tuning` support

# 28th April 2021
- Added left handed instrument support

# 3rd February 2021
- Added the progression subcommand

# 31st December 2020
- Happy New Year!
- added find --digits/-d (couldn't use --fingers as --frets/-f got there first...)
- added the first ukelele chord definition

# 26th December 2020
- Happy Xmas!
- chords from the internet archive of OLGA have been merged into the chord bank, which now has 1000+ chord definitions
- as some of the OLGA chords have introduced notes, Frettler can both display them and search the chord bank for them
through the use of the -a parameter ie `frettler guitar chord c chord_dim -a d`

# 22nd December 2020
- colours used now sycnhronised across all views
- chord charts fret inlays aligned

# 21st December 2020
- chord charts improved to show circles and note/interval summary below

# 16th December 2020
- chord fingering charts added with new `chord` command
- vertical view now aligns notes on strings rather than in boxes
- the `find` command renamed to `display`
- the original `chord` command renamed to `lookup`

# 16th November 2020
- added interactive menu mode (in a day!)
- fixed banjo display of chords

# 8th November 2020
- very extensive changes to accomplish understanding of flats within scales
- fifths command added

# 16th Aug 2020
- octaves working again
- find command added
- fixed mono display with intervals

# 15th Aug 2020
- oops. broke it all with --octaves! Fixed but octaves temporarily disabled. I really must write some unit tests.

# 11th Aug 2020
- added --octaves option

# 10th Aug 2020
- on a roll, added the patterns command

# 10th Aug 2020
- added --rule to the chord command with strict(default), relaxed and loose

# 6th Aug 2020
- added verbose mode to explain Frettlers decision making

# 4th Aug 2020
- windows demo script added

# 3rd Aug 2020
- display of M10,M11 etc improved in the vertical view
- added reverse chord lookup
- aug7 chord corrected
- chord analysis now tighter as it requires all intervals to match all those in the chord

# 26th Jul 2020
- chord analysis completely refactored to make it more extensible
- windows build and run scripts
- minor refactoring
- melodic_minor_scale fixed

# 25th Jul 2020
- CLI command structure re-organised - scale|chord was unnecessary
- added some modes for scale display
- completions now generated using lower case params
- --frets option added to allow display of greater than (or less than!) the default 12 frets 
- (code) patterns can be aliased
- (code) patterns can have a parent pattern (used for chord calculation for pentatonic scales)
- pentatonic chords generation fixed
- display of intervals in the vertical view fixed
- demo script added

# 23rd Jul 2020
- lower case param handling

# 23rd Jul 2020
- titles now include instrument details
- more refactoring to correctly name some variables
- BLUES_SCALE added
- VERTICAL handling of SCALE added
- unix/macOS executable shell replaced the simple frettler wrapper shell - built by the build shell. 
