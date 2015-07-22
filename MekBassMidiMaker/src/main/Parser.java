package main;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Parser {

	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	public static final int PROGRAM_CHANGE = 0xC0;
	//	    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

	public enum note {
		C, CSharp,
		DFlat, D, DSharp,
		EFlat, E, ESharp,
		F, FSharp,
		GFlat, G, GSharp,
		AFlat, A, ASharp,
		BFlat, B
	}

	public static void main(String[] args) {
		if (args.length < 1){
			return;
		}
		else{
			File midiFile = new File(args[0].toString());
			try {
				// Set the sequencer - the thing that allows MIDI files to be played
				Sequencer sequencer = MidiSystem.getSequencer();
				// Set the sequence to be examined to the midiFile...
				sequencer.setSequence(MidiSystem.getSequence(midiFile));
				// ... And store the sequence.
				Sequence sequence = sequencer.getSequence();

				// Store the tracks and the number of tracks, printing the number.
				Track[] tracks = sequence.getTracks();
				int trackNo = tracks.length;
				System.out.println("Number of Tracks = " + trackNo);

				// Set a counting variable
				int trackNumber = 0;

				// FOR EVERY TRACK...
				for (Track t : tracks){
					// ... Print out some basic information (Track Number and the Size of the track)
					System.out.println("Track " + trackNumber++ + ": size = " + t.size() + "\n");

					for (int i=0; i < t.size(); i++) {
						// GO THROUGH ALL THE EVENTS...
						MidiEvent event = t.get(i);
						// ... And get the messages at each one.
						MidiMessage message = event.getMessage();

						if (message instanceof MetaMessage) {
							// This is a META MESSAGE; convert the message and print basic information.
							// This information is WHEN the event happens, what kind of message it is and what TYPE it is.
							System.out.println("@" + event.getTick() + "(META_MESSAGE)");
							MetaMessage mm = (MetaMessage) message;
							System.out.println(String.format("%02x", Byte.parseByte(((Integer) mm.getType()).toString())));

							// Print the Message data, plus a new line
							System.out.println(new String(mm.getData(), "UTF-8"));
							System.out.println();
						}
						else if (message instanceof ShortMessage) {
							// This is a SHORT MESSAGE; convert the message and print basic information.
							// This information is WHEN the event happens, what kind of message it is,
							// what channel the message relates to and what TYPE of message it is.
							// HOWEVER, this will ONLY be printed if the command is recognized as IMPORTANT, such as:
							// NOTE_ON, NOTE_OFF, PROGRAM_CHANGE (i.e. instrument change).
							ShortMessage sm = (ShortMessage) message;
							String smString = String.format("%02x", Integer.parseInt(((Integer) sm.getStatus()).toString()));

							if (sm.getCommand() == NOTE_ON) {
		                        int key = sm.getData1();
		                        int octave = (key / 12)-1;
		                        int note = key % 12;
		                        String noteName = Parser.note.values()[note].name();
		                        int velocity = sm.getData2();
		                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
		                    } else if (sm.getCommand() == NOTE_OFF) {
		                        int key = sm.getData1();
		                        int octave = (key / 12)-1;
		                        int note = key % 12;
		                        String noteName = Parser.note.values()[note].name();
		                        int velocity = sm.getData2();
		                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
		                    }

							if (smString.startsWith("C") || smString.startsWith("c")){
								System.out.println("@" + event.getTick() + "(SHORT_MESSAGE)");
								System.out.println(smString);
								System.out.println(sm.getData1());
								System.out.println();
							}
						}
					}
				}

			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}