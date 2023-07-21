import java.util.Arrays;

public class NoteNameValueConverter {
    static final int SEMITONESINOCTAVE = 12;
    public static String getNoteName(int note) {
        String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        int octave = (note / SEMITONESINOCTAVE) - 1;
        int noteIndex = note % SEMITONESINOCTAVE;
        return noteNames[noteIndex] + octave;
    }

    public static int getNoteValue(String noteName) {
        String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        String note = noteName.substring(0, 1).toUpperCase();
        int octave = Integer.parseInt(noteName.substring(noteName.length()-1));
        int noteIndex = Arrays.asList(noteNames).indexOf(note);
        return (octave + 1) * SEMITONESINOCTAVE + noteIndex;
    }
}
