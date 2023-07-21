import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.sleep;

public class EarTrainer extends JFrame {
    private final String[] INTERVALS = {"Perfect Unison", "Minor 2nd", "Major 2nd", "Minor 3rd", "Major 3rd", "Perfect 4th", "Tritone", "Perfect 5th", "Minor 6th", "Major 6th", "Minor 7th", "Major 7th", "Perfect Octave"};
    private Random random = new Random();
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private Instrument[] instruments;
    private TInstrument[] trainingInstruments;
    private TInstrument activeInstrument;
    private String activeInterval;
    private int note;
    JPanel panel;

    public EarTrainer() throws MidiUnavailableException {
        super("Ear Trainer");

        this.synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        this.channel = synthesizer.getChannels()[0];
        this.instruments = synthesizer.getAvailableInstruments();
        this.trainingInstruments = loadTInstruments();
        this.activeInstrument = this.trainingInstruments[0];
        channel.programChange(instruments[activeInstrument.getIndex()].getPatch().getProgram());
        this.note = NoteNameValueConverter.getNoteValue("C4");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel
        this.panel = new JPanel(new GridLayout(5,3));

        // Add buttons
        for (int i = 0; i < INTERVALS.length; i++)
        {
            JButton button = new JButton(INTERVALS[i]);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guess(button.getText());
                    try {
                        play();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            panel.add(button);
        }

        // Create replay button
        JButton button = new JButton("Replay");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    play();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(button);

        // Create combobox
        JComboBox cb = new JComboBox(trainingInstruments);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeInstrument((TInstrument)cb.getSelectedItem());
                chooseNextInterval();
                chooseNextNote();
                try {
                    play();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(cb);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void play() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setPanelEnabled(panel, false);
                    int highNote = note + getActiveIntervalNum();
                    channel.noteOn(note, 100);
                    sleep(500);
                    channel.noteOff(note);
                    sleep(100);
                    channel.noteOn(highNote, 100);
                    sleep(500);
                    channel.noteOff(highNote);
                    sleep(100);
                    channel.noteOn(note, 100);
                    channel.noteOn(highNote, 100);
                    sleep(1000);
                    channel.noteOff(note);
                    channel.noteOff(highNote);
                    setPanelEnabled(panel, true);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void guess(String guessInterval) {
        if (guessInterval.equals(activeInterval)) {
            System.out.println("Correct!");
            chooseNextInterval();
            chooseNextNote();
        }
        else {System.out.println("Wrong!");}
    }

    private void chooseNextInterval() {
        activeInterval = INTERVALS[random.nextInt(INTERVALS.length)];
    }

    private void chooseNextNote() {
        note = random.nextInt(activeInstrument.getRange()) + activeInstrument.getLowerBoundNum();
        if (note + getActiveIntervalNum() > activeInstrument.getUpperBoundNum()) { note -= getActiveIntervalNum(); }
    }

    private int getActiveIntervalNum() {
        return Arrays.asList(INTERVALS).indexOf(activeInterval);
    }

    private void changeInstrument(TInstrument newInstrument) {
        activeInstrument = newInstrument;
        channel.programChange(instruments[activeInstrument.getIndex()].getPatch().getProgram());
    }

    private TInstrument[] loadTInstruments() {
        trainingInstruments = new TInstrument[]{new TInstrument("Piano", 0, "C8", "A0"),
            new TInstrument("Strings", 48, "A7", "C1"),
            new TInstrument("Voice", 52, "A5", "E2")};
        return trainingInstruments;
    }

    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            component.setEnabled(isEnabled);
        }
    }

    public static void main(String[] args) throws MidiUnavailableException, InterruptedException {
        EarTrainer earTrainer = new EarTrainer();
        earTrainer.chooseNextInterval();
        earTrainer.chooseNextNote();
        earTrainer.play();
    }
}
