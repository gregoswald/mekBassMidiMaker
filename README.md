README for Etemenanki

This Java program was made for the sole purpose of separating a single MIDI Track into 4 channels for use with Mechbass, made by James McVay.
For more information about MIDI Files and their structure visit http://www.ccarh.org/courses/253/handout/smf/

===

How do I use the Program?

1) Find a MIDI file you want to play on Mechbass.

2) Open the program.

3) Loading the File:
	Click the "Load" button, or go to <File> --> <Open MIDI File>.
	Alternatively you may use the open <filename> command from the text area.

4) Choose a bass track from the drop down menu.

5) Solving the File:
	Click the "Solve" button, type solve into the text area, or go to <File> --> <Solve MIDI File>.

6) Optional: Cleaning the file:
	Click the "Clean" button to remove unplayed notes from the file.

7) Saving the File:
	Click the "Save" button, or go to <File> --> <Save MIDI File>.
	When saving from text input either use save <filename> to save with a specific file name 
	or simply use save to save the file as out.mid

You should now have a MIDI file that can be played on Mechbass.

===

Features:
Text Input:
	Typing "help" into the text area will give a list and description of available text commands.
	
Visualisation:
	The main panel of the program displays a visual representation of the currently loaded MIDI file, with played notes split onto their respective strings.
	During playback the visualisation follows along, and also displays the simulated position of the frets of Mechbass.
	
	NOTE:
	String allocations and fret positions are only accurate if the MIDI has been solved.
	
Solving:
	Initially the MIDI is split into a number of tracks equal to 1 more than the number of strings it is solving for.
	The program currently uses a quick greedy algorithm in order to allocate notes onto strings. Track 0  is used to store all notes that cannot be allocated.
	After solving  MIDI the program adds prepositioning notes before played notes. The amount of time it prepositions by is dependent on both the timing data of the strings and an added constant variable input by the user.
	
	The MIDI can also be shifted up or down octaves before solving using the "Shift Octave up" and "Shift Octave down" options in the playback menu.
	
Preferences:
	Default data about the number and constraints of the strings Mechbass uses is stored in default.csv.
	Constraints can be changed in the program by the user, by using the "New Config" or "Load Config" options in the File menu, and saved using "Save Config".

	All timing units are milliseconds.
	Variable Descriptions:
		Preposition Length: The duration of each preposition note.
		Preposition Delay: The extra time added between the preposition note and played note.
		Number of Strings: The number of strings the instrument has.
		
		Lowest MIDI Note: The integer value for the lowest note the string can play.
		Highest MIDI Note: The integer value for the hightest note the string can play.
		Timings Between Notes: 	An array of timing intervals between each note the string can play and the next
								ie. if a string can play notes 1,2,3 and 4 it contains the time between notes 1&2, notes 2&3, and notes 3&4.
								
Playback:
	You can play the MIDI file at any point by pressing the "Play" button. You canalso use <Playback> --> <Play>.
	You can pause the MIDI file at any point by pressing the "Pause" button. You can resume MIDI File playback from the paused point by Playing the MIDI File again.
	You can stop the MIDI file at any point by pressing the "Stop" button. You can also use <Playback> --> <Stop>. This will stop playback and bring the MIDI File playback position to the beginning.
	
	NOTE:
	you SHOULD NOT "play" a file while a file is already playing. Doing so will play the file again from the start while the current file is still playing (i.e. you'll play the file on top of itself).
	
Planned Features (Unimplemented):

Alternative solving algorithms:
	While currently unimplemented the program can be extended to use alternative solving algorithms. These are made using the Solver interface, implementing the solve method. 
	The program source contains an incomplete solver using graph colouring.
	
Conflict Resolution:
	Conflict resolution has been disabled as while the appropriate actions can be taken (Drop either conflicting note, or delay the start of the second or push forward the end of the first), the user interface cannot display enough relevant information for the user to come to an informed conclusion on which to use.
	
Full string information:
	The program only considers the time between one note and the next, rather than accepting information on the time from each string to each other string.