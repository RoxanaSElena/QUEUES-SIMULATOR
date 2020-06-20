package queues;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;

public class Store {
    private int nbOfClients;
    private int nbOfQueues;
    private int timeLimit;
    private int minArrivingTime;
    private int maxArrivingTime;
    private int minProcessingTime;
    private int maxProcessingTime;
    private int s=0;
    private String fileName_in;
    private String fileName_out;
    private Random random=new Random();
    private MyData rd=new MyData();
    //  private WriteToFile wt = new WriteToFile();
    File file;// = new File(fileName_out);
    private FileWriter fr;// = new FileWriter(file,true);
    private ArrayList<Queue> queues=new ArrayList<Queue>(nbOfQueues);
    public ArrayList<Thread> threads=new ArrayList<Thread>(nbOfQueues); //each queue has a thread=> nr of queues=nr threads
    public ArrayList<Customer> clients=new ArrayList<Customer>(nbOfClients);

    public void gettingData()
    {
        rd.readData(fileName_in);
        nbOfClients=rd.getNoClients();
        nbOfQueues=rd.getNoQueue();
        timeLimit=rd.gettSimulationMax();
        minArrivingTime=rd.getMinArrivingTime();
        maxArrivingTime=rd.getMaxArrivingTime();
        minProcessingTime=rd.getMinWaitingTime();
        maxProcessingTime=rd.getMaxWaitingTime();
    }

    public void createThreads(){
        for(int i=0;i<nbOfQueues;i++)
        {
            queues.add(i,new Queue(i));
            Thread th=new Thread(queues.get(i));
            threads.add(i,th);

        }
    }

    public void stopThreads(){
        for(Thread thread:threads)
        {
            thread.interrupt();
        }
    }


    public int getMinAvarageTimeOfQueue(){
        int minTime=Integer.MAX_VALUE;
        int minQueue=0;
        for(int i=0;i<nbOfQueues;i++)
        {
            if(queues.get(i).getServiceTime().get()<minTime)
            {
                minTime=queues.get(i).getServiceTime().get();
                minQueue=i;
            }
        }
        return minQueue;
    }

    public void generateNRandomClients() throws IOException {
        int N = 0;
        for (int i = 0; i < nbOfClients; i++) {
            int randomService = random.nextInt(maxProcessingTime - minProcessingTime) + 1;
            int randomArriving = random.nextInt(maxArrivingTime - minArrivingTime) + 1;
            N++;
            clients.add(new Customer(N, randomArriving, randomService));
        }
        for(Customer client:clients)
        {
            // System.out.println(client);
            fr.write(client+"\n");
        }
    }

    Timer timer=new Timer();
    TimerTask task=new TimerTask() {
        @Override
        public void run() {
            while(s<=timeLimit) {

                s++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            timer.purge();  //used to remove all cancelled tasks from this timer's task queue
            timer.cancel();
        }
    };
    public void start()
    {
        //method is used to schedule the specified task for repeated fixed-rate execution,
        // beginning at the specified time.
        timer.scheduleAtFixedRate(task,1000,1000);

    }
    public float averageTime()
    {   int sum=0;
        for(Queue queue:queues)
        {
            sum+=queue.getavgTime();
        }
        return sum/nbOfClients;
    }
    public void actions() throws IOException {
        while(s<=timeLimit) {
            System.out.println("TIME: "+s);
            fr.write("TIME"+s+"\n");
            for (Customer cl : clients) {
                if (cl.getArrival() == s) {
                    // A thread is alive if it has been started and has not yet died
                    if(threads.get(getMinAvarageTimeOfQueue()).isAlive()==false){
                        threads.get(getMinAvarageTimeOfQueue()).start();}
                    queues.get(getMinAvarageTimeOfQueue()).addClients(cl);
                }
            }
            for(int i=0;i<nbOfQueues;i++)
            {
                if(queues.get(i).emptyQueue()==0)
                {
                    System.out.println("Queue "+queues.get(i).getQueueID()+" closed");
                    fr.write("Queue "+queues.get(i).getQueueID()+" closed\n");
                }else
                {
                    System.out.println("Queue "+queues.get(i).getQueueID()+" "+queues.get(i));
                    fr.write("Queue "+queues.get(i).getQueueID()+" "+queues.get(i)+"\n");
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stopThreads();
    }
    public Store(String arg1,String arg2) throws IOException {
        this.fileName_in =arg1;
        this.fileName_out=arg2;
        fr = new FileWriter(fileName_out,true);
        gettingData();
        generateNRandomClients();
        createThreads();
        start();
        actions();
        stopThreads();
        //  System.out.println(averageTime());
        fr.write((int) averageTime()+"\n");
        fr.close();


    }
}
