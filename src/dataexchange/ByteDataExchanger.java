package dataexchange;

import dataexchange.interfaces.DateExchanger;

import java.io.*;

// related task: Use byte and character streams for simulation of the data exchange in the system
//--------------------------------------------------
// Done: create a class ByteDataExchange with two static methods: writeToByteFile and readByteFile
// that write and read data from a file as bytes
public class ByteDataExchanger implements DateExchanger {
    public void writeToFile(String fileName, String data) {
        try (OutputStream outputStream = new FileOutputStream(fileName, true)) {
            byte[] dataBytes = (data + "\n").getBytes();
            outputStream.write(dataBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String fileName) {
        try (InputStream inputStream = new FileInputStream(fileName)) {
            int byteData;
            StringBuilder stringBuilder = new StringBuilder();

            while ((byteData = inputStream.read()) != -1) {
                System.out.print((char) byteData);
                stringBuilder.append((char) byteData);
            }
            System.out.println();

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
