// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class with main method
public class TemperatureConverterGUI {
    public static void main(String[] args) {

        // Create the main window
        JFrame window = new JFrame("~~~Temperature Converter~~~");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 300);
        window.setLayout(new GridLayout(5, 2));

        // Create the interactive elements and labels to them
        JLabel temperatureDemandLabel = new JLabel("Enter Temperature Here:");
        JTextField temperatureInput = new JTextField();

        JLabel sourceScaleLabel = new JLabel("From Unit:");
        String[] temperatureScales = {"Celsius", "Fahrenheit", "Kelvin"};
        JComboBox<String> sourceScale = new JComboBox<>(temperatureScales);

        JLabel targetScaleLabel = new JLabel("To Unit:");
        JComboBox<String> targetScale = new JComboBox<>(temperatureScales);

        JButton convertButton = new JButton("Convert");
        JLabel conversionResultLabel = new JLabel("Result: ");

        // Add the elements to the main window
        window.add(temperatureDemandLabel);
        window.add(temperatureInput);
        window.add(sourceScaleLabel);
        window.add(sourceScale);
        window.add(targetScaleLabel);
        window.add(targetScale);
        window.add(convertButton);
        window.add(conversionResultLabel);

        // Set up the converter and provide error handling
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double inputValue = Double.parseDouble(temperatureInput.getText());
                    String oldScale = (String) sourceScale.getSelectedItem();
                    String newScale = (String) targetScale.getSelectedItem();

                    if ((oldScale.equals("Kelvin") && inputValue < 0) ||
                        (oldScale.equals("Celsius") && inputValue < -273.15) ||
                        (oldScale.equals("Fahrenheit") && inputValue < -459.67)) {
                            JOptionPane.showMessageDialog(window, 
                            "ERROR: Temperature below absolute zero!\n" +
                            "Absolute zero values:\n" +
                            "# 0 K\n" + 
                            "# -273.15°C\n" +
                            "# -459.67°F", 
                            "Physics error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double convertedValue = calculateTemperature(inputValue, oldScale, newScale);
                    conversionResultLabel.setText("Result: " + String.format("%.2f %s", convertedValue, newScale));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "ERROR! Invalid Input.\n" +
                    "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        window.setVisible(true);
    }

    // Logic of converting
    public static double calculateTemperature(double value, String oldScale, String newScale) {
        double celsiusValue;

        switch (oldScale) {
            case "Celsius":
                celsiusValue = value;
                break;
            case "Fahrenheit":
                celsiusValue = (value - 32) * 5 / 9;
                break;
            case "Kelvin":
                celsiusValue = value - 273.15;
                break;
            default:
                celsiusValue = 0;
                break;
        }

        switch (newScale) {
            case "Celsius":
                return celsiusValue;
            case "Fahrenheit":
                return celsiusValue * 9 / 5 + 32;
            case "Kelvin":
                return celsiusValue + 273.15;
            default:
                throw new IllegalArgumentException("Invalid temperature scale (How did you do it!?)");
        }
    }
}