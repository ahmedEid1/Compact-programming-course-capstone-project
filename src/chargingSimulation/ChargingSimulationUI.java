package chargingSimulation;

import cars.Car;
import station.ChargingStation;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargingSimulationUI extends JFrame {
    private Map<String, JTextArea> textAreas;

    public ChargingSimulationUI(List<ChargingStation> chargingStations, List<Car> cars) {
        setTitle("Charging Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        textAreas = new HashMap<>();
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel stationsPanel = new JPanel(new GridLayout(0, 1));
        stationsPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        JPanel carsPanel = new JPanel(new GridLayout(0, 1));
        carsPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));

        JPanel energyManagementPanel = new JPanel(new GridLayout(0, 1));
        energyManagementPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        JPanel BatteryPanel = new JPanel(new GridLayout(0, 1));
        BatteryPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        JPanel stationQueuePanel = new JPanel(new GridLayout(0, 1));
       stationQueuePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        for (ChargingStation station : chargingStations) {
            createLogBox(stationsPanel, station.getName());
            createLogBox(BatteryPanel, station.getName() + " battery");
            createLogBox(stationQueuePanel, "queue_for_" + station.getName());

            createLogBox(energyManagementPanel, station.getName() + "_SOLAR");
            createLogBox(energyManagementPanel, station.getName() + "_WIND");
            createLogBox(energyManagementPanel, station.getName() + "_GAS");
        }

        for (Car car : cars) {
            createLogBox(carsPanel, car.getId());
        }

        // Adding the "Energy Management" tab
        tabbedPane.addTab("Stations", null, stationsPanel, "Logs for Charging Stations");
//        tabbedPane.addTab("Station Queue", null, stationQueuePanel, "the queue of the stations");
        tabbedPane.addTab("Cars", null, carsPanel, "Logs for Cars");
        tabbedPane.addTab("Station Energy Sources", null, energyManagementPanel, "Logs for Energy Management");
        tabbedPane.addTab("Station Battery charging", null, BatteryPanel, "the battery charging");

        add(tabbedPane);

        pack();
        setVisible(true);
    }

    private void createLogBox(JPanel panel, String name) {
        JPanel logBoxPanel = new JPanel(new BorderLayout());
        logBoxPanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel titleLabel = new JLabel(name);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logBoxPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        logBoxPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(logBoxPanel);
        textAreas.put(name, textArea);
    }

    public void appendLog(String logBoxName, String log) {
        JTextArea textArea = textAreas.get(logBoxName);
        if (textArea != null) {
            // add timestamp to log (only minutes and seconds and milliseconds)
            String timestamp = String.format("[%tM:%tS.%tL]", System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis());
            log = timestamp + " " + log;
            textArea.append(log + "\n");
        }
    }

}
