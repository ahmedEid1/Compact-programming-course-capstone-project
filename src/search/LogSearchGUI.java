package search;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class LogSearchGUI extends JFrame {
    private final JTextField equipmentTextField;
    private final JTextField dateTextField;
    private final JTextArea resultTextArea;
    private final JComboBox<String> logFileComboBox;

    public LogSearchGUI() {
        super("LogSearchGUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
       

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 2, 5, 5));

        searchPanel.add(new JLabel("Equipment Name:"));
        equipmentTextField = new JTextField();
        searchPanel.add(equipmentTextField);

        searchPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        dateTextField = new JTextField();
        searchPanel.add(dateTextField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        logFileComboBox = new JComboBox<>();
        JPanel openPanel = new JPanel();
        openPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        openPanel.add(new JLabel("Select Log File:"));
        openPanel.add(logFileComboBox);

        JButton openButton = new JButton("Open");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSelectedLogFile();
            }
        });
        openPanel.add(openButton);

        mainPanel.add(openPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Use the system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new LogSearchGUI();
            }
        });
    }

    private void performSearch() {
        String equipmentName = equipmentTextField.getText().trim();
        String date = dateTextField.getText().trim();

        List<String> matches;
        if (!equipmentName.isEmpty() && date.isEmpty()) {
            matches = LogSearchUtility.searchLogsByEquipmentName(equipmentName, "logs");
        } else if (equipmentName.isEmpty() && !date.isEmpty()) {
            matches = LogSearchUtility.searchLogsByDate(date, "logs");
        } else if (!equipmentName.isEmpty() && !date.isEmpty()) {
            matches = LogSearchUtility.searchLogs(equipmentName, date, "logs");
        } else {
            resultTextArea.setText("No search criteria entered.");
            logFileComboBox.removeAllItems();
            return;
        }

        logFileComboBox.removeAllItems();
        for (String match : matches) {
            logFileComboBox.addItem(match);
        }

        StringBuilder resultText = new StringBuilder();
        resultText.append("Found ").append(matches.size()).append(" matches:\n");
        resultText.append("that match the search criteria which are:\n");
        resultText.append("Equipment name: ").append(equipmentName).append("\n");
        resultText.append("Date: ").append(date).append("\n");
        resultText.append("========================================\n");

        for (int i = 0; i < matches.size(); i++) {
            resultText.append((i + 1)).append(". ").append(matches.get(i)).append("\n");
        }

        resultText.append("========================================\n");

        resultTextArea.setText(resultText.toString());
    }

    private void openSelectedLogFile() {
        String selectedLogFile = (String) logFileComboBox.getSelectedItem();
        if (selectedLogFile != null) {
            try {
                Desktop.getDesktop().edit(new File(selectedLogFile));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening log file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
