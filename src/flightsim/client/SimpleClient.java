package flightsim.client;

import java.io.*;
import java.net.Socket;

public class SimpleClient implements Client
{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter printWriter;

    @Override
    public void connect(String ip, int port)
    {
        try
        {
            socket = new Socket(ip, port);
            Thread.sleep(1000);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void set(String path, Double value)
    {
        this.sendLine("set " + path + " " + value);
    }

    @Override
    public Double getValue(String path) {
        this.sendLine("get "+path);
        try {
            String s=null;
            while (!(s = in.readLine()).contains(path));
            String substring = s.substring(s.indexOf("'")+1, s.lastIndexOf("'"));
            return Double.parseDouble(!substring.isEmpty()?substring:"0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendLine(String line) {
        printWriter.println(line);
        printWriter.flush();
    }

    @Override
    public void close()
    {
        try
        {
            in.close();
            printWriter.close();
            socket.close();
        }
        catch (IOException|NullPointerException e)
        {
        }
    }

    @Override
    public boolean isClose() {
        if (socket ==null) return true;
        return socket.isClosed();
    }
}
