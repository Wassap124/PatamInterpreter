package flightsim.client;

public interface Client
{
    void connect(String ip, int port);
    void set(String path, Double value);
    Double getValue(String key);
    void sendLine(String line);
    void close();
    boolean isClose();
}
