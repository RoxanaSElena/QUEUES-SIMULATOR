package queues;

public class Customer {
    private int ID;
    private int tArrival;
    private int tService;

    public Customer(int ID, int tArrival, int tService) {
        this.ID = ID;
        this.tArrival = tArrival;
        this.tService = tService;
    }

    public int getArrival() {
        return tArrival;
    }

    public int getService() {
        return tService;
    }

    @Override
    public String toString() {
        return " (" + ID + "," + tArrival + "," + tService + ") ";
    }

}
