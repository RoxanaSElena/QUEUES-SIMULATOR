package queues;

import java.io.File;
import java.util.Scanner;

public class MyData {
    private int noClients;
    private int noQueue;
    private int tSimulationMax;
    private int minArrivingTime;
    private int maxArrivingTime;
    private int minWaitingTime;
    private int maxWaitingTime;
    public void readData(String fileName_in)
    {
        try{
            File file=new File(fileName_in);
            Scanner reader=new Scanner(file);
            String data=reader.nextLine();
            noClients=Integer.parseInt(data);
            data=reader.nextLine();
            noQueue=Integer.parseInt(data);
            data=reader.nextLine();
            tSimulationMax=Integer.parseInt(data);
            data=reader.nextLine();
            String[] aux=data.split("\\,");
            minArrivingTime=Integer.parseInt(aux[0]);
            maxArrivingTime=Integer.parseInt(aux[1]);
            data=reader.nextLine();
            aux=data.split("\\,");
            minWaitingTime=Integer.parseInt(aux[0]);
            maxWaitingTime=Integer.parseInt(aux[1]);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNoClients() {
        return noClients;
    }

    public int getNoQueue() {
        return noQueue;
    }

    public int gettSimulationMax() {
        return tSimulationMax;
    }

    public int getMinArrivingTime() {
        return minArrivingTime;
    }

    public int getMaxArrivingTime() {
        return maxArrivingTime;
    }

    public int getMinWaitingTime() {
        return minWaitingTime;
    }

    public int getMaxWaitingTime() {
        return maxWaitingTime;
    }
}
