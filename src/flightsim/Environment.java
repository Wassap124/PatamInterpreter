package flightsim;

import flightsim.client.Client;
import flightsim.language.interpreter.Parser;
import flightsim.server.Server;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class Environment
{
    private volatile Map<String, String> bindTable = new ConcurrentHashMap<>();
    private volatile Map<String, Double> symbolTable = new ConcurrentHashMap<>();
    private volatile Client client;
    private volatile Server server;
    private volatile Parser parser;
    private volatile int returnValue = 0;
    private volatile Queue<Double> defaultValues =new ConcurrentLinkedQueue<>();

    public Queue<Double> getDefaultValues()
    {
        return defaultValues;
    }

    public int getReturnValue()
    {
        return returnValue;
    }

    public void setReturnValue(int returnValue)
    {
        this.returnValue = returnValue;
    }

    private Double getBind(String bindTableKey)
    {
        String path = this.bindTable.get(bindTableKey);
        Double value = this.client.getValue(path);
        updateAllBinds(path,value);
        return value;
    }

    private void setBind(String bindTableKey,Double value)
    {
        String path = this.bindTable.get(bindTableKey);
        this.client.set(path,value);
        updateAllBinds(path,value);
    }

    private void updateAllBinds(String path,Double value)
    {
        Stream<Map.Entry<String, String>> entryStream = this.bindTable.entrySet().stream()
                .filter(stringStringEntry -> stringStringEntry.getValue().equals(path));
        entryStream.forEach(stringStringEntry -> {
                    setSymbol(stringStringEntry.getKey(), value);
                });
    }

    private Double getSymbol(String symbolTableKey)
    {
        return this.symbolTable.get(symbolTableKey);
    }

    private void setSymbol(String symbolTableKey,Double value)
    {
        this.symbolTable.put(symbolTableKey,value);
    }

    public void addBind(String bindTableKey,String path)
    {
        this.bindTable.put(bindTableKey,path);
        try
        {
            setValue(bindTableKey, this.getDefaultValues().remove());
        }
        catch (NoSuchElementException e){}
    }

    public void addSymbol(String symboTableKey,Double value)
    {
        this.symbolTable.put(symboTableKey,value);
    }

    public Double getValue(String key)
    {
        if (bindTable.containsKey(key) && !client.isClose())
            return this.getBind(key);
        else
            if (symbolTable.containsKey(key))
                return this.getSymbol(key);
            throw new RuntimeException("key not found in tables for key="+key);
    }

    public void setValue(String key,Double value)
    {
        if (bindTable.containsKey(key) && !client.isClose())
            this.setBind(key,value);
        else
            if (symbolTable.containsKey(key))
                this.setSymbol(key,value);
            else
                this.addSymbol(key,value);
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Server getServer()
    {
        return server;
    }

    public void setServer(Server server)
    {
        this.server = server;
    }

    public Parser getParser()
    {
        return parser;
    }

    public void setParser(Parser parser)
    {
        this.parser = parser;
    }

    public void closeAll() {
        defaultValues.clear();
        symbolTable.clear();
        bindTable.clear();
        getClient().close();
        getServer().stop();
    }
}
