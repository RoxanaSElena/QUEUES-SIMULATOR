package queues;

import java.io.File;
import java.io.IOException;

public class app {
    public static void main(String[] args) throws IOException {

        //    File file=new File();
        //   File file = new File("out-text-1.txt");

        Store stores=new Store(args[0], args[1]);
    }
}