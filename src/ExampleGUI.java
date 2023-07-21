import java.awt.*;
import javax.swing.*;

public class ExampleGUI extends JFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JTable table;

    public ExampleGUI() {
        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 4));
        for (int i = 0; i < 12; i++) {
            buttonPanel.add(new JButton("Button " + (i+1)));
        }

        // Create the text area and scroll pane
        textArea = new JTextArea("Click the button to display a table here");
        scrollPane = new JScrollPane(textArea);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame properties
        setTitle("Example GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void displayTable(String[][] data, String[] headers) {
        // Create the table
        table = new JTable(data, headers);

        // Remove the text area from the scroll pane
        scrollPane.setViewportView(table);

        // Repaint the GUI to reflect the new content
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        ExampleGUI gui = new ExampleGUI();
        gui.setVisible(true);

        // Example usage: display a table when a button is clicked
        String[][] data = {{"1", "John"}, {"2", "Jane"}, {"3", "Bob"}};
        String[] headers = {"ID", "Name"};
        JButton button = new JButton("Display table");
        button.addActionListener(e -> gui.displayTable(data, headers));
        gui.getContentPane().add(button, BorderLayout.NORTH);
    }
}