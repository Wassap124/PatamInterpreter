package flightsim.server;


import flightsim.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class DataReaderServer implements Server
{
    private HashMap<String, Double> serverData = new HashMap<>(); ///  symbol table of the simulator
    private boolean stop = false;
    private Thread serverThread;
    private final Environment env;

    public DataReaderServer(Environment env)
    {
        this.env = env;
    }

    @Override
    public void listen(int port,int timeout)
    {
        serverThread = new Thread(() -> {
            try
            {
                ServerSocket server = new ServerSocket(port,timeout);
                server.setSoTimeout(5000);
                while (!stop)
                {
                    try
                    {
                        Socket aClient = server.accept(); // blocking call
                        try
                        {
                            InputStream clientInputStream = aClient.getInputStream();
                            handleClient(clientInputStream,timeout);
                            clientInputStream.close();
                            aClient.close();
                        }
                        catch (IOException ignored)
                        {
                        }
                    }
                    catch (SocketTimeoutException ignored)
                    {
                    }
                }
                server.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    private void handleClient(InputStream inputStream,int timeout) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = in.readLine()) != null)
        {
            String[] split = line.split(",");
            for (String s : split)
            {
                Double d =Double.parseDouble(s);
                env.getDefaultValues().add(d);
            }
        }

    }

    @Override
    public void stop()
    {
        stop = true;
        if (serverThread !=null)
            serverThread.interrupt();
    }

    @Override
    public Thread serverThread()
    {
        return serverThread;
    }

    @Override
    public Double getValue(String key)
    {
        return getData().get(key);
    }

    @Override
    public void setValue(String key,double value)
    {
        getData().put(key,value);
    }

    @Override
    public Map<String, Double> getData()
    {
        return serverData;
    }
}
