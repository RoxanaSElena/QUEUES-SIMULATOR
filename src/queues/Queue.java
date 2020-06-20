package queues;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
    private LinkedBlockingQueue<Customer> clients=new LinkedBlockingQueue<>();
    private AtomicInteger waitingPeriod = new AtomicInteger(0);
    private int nrQ;
    private float avgTime=0;
    private int noClients;
    public Queue(int nrQ) {
        this.nrQ = nrQ;
    }

    public void addClients(Customer client)
    {
        clients.add(client);
    }

    public int getNoClients(){
        return noClients;
    }

    public int getQueueID() {
        return nrQ;
    }

    public float getavgTime() {
        return avgTime;
    }

    public int getSize()
    {
        return clients.size();
    }


    public int emptyQueue()
    {
        if(clients.isEmpty())
            return 0;
        else
            return 1;
    }

    public void moveClients(){
        Customer client;
        client=clients.peek();
        avgTime+=client.getService();
        noClients++;
        try{
            TimeUnit.SECONDS.sleep(client.getService());
        } catch (Exception e) {
            e.printStackTrace();
        }

        clients.remove();
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted())
        {
            if(!clients.isEmpty()){
                moveClients();}
        }
    }

    public AtomicInteger getServiceTime(){
        AtomicInteger s = new AtomicInteger(0);
        for(Customer client:clients)
        {
            // s=s+client.getService();
            s.addAndGet(client.getService());
        }
        return s;
    }

    @Override
    public String toString() {
        return clients + ";";
    }
}
