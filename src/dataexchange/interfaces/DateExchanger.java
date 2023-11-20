package dataexchange.interfaces;

public interface DateExchanger {

    String readFile(String fileName);

    void writeToFile(String fileName, String data);

}
