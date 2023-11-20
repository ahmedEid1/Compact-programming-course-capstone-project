package dataexchange;


import dataexchange.interfaces.DateExchanger;

import java.io.*;

// related task: Use byte and character streams for simulation of the data exchange in the system
//--------------------------------------------------
// Done: create a class CharacterDataExchange with two static methods: writeToCharFile and readCharFile
// that write and read data from a file as characters
public class CharacterDataExchanger implements DateExchanger {

    public void writeToFile(String fileName, String data) {
        try (Writer writer = new FileWriter(fileName, true)) {
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (Reader reader = new FileReader(fileName)) {
            int charData;
            while ((charData = reader.read()) != -1) {
                System.out.print((char) charData);
                stringBuilder.append((char) charData);
            }
            System.out.println();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
