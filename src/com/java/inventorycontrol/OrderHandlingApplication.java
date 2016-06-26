package com.java.inventorycontrol;

import com.java.inventorycontrol.OrderHandling.Consumer;
import com.java.inventorycontrol.OrderHandling.OrderBroker;
import com.java.inventorycontrol.OrderHandling.OrderProducer;

import java.util.concurrent.*;

/**
 * Main Application class that can be used to create as mmany Producers and Consumers
 */
public class OrderHandlingApplication {

    public static void main(String args[])
    {
        try {
            OrderBroker broker = new OrderBroker();
            ExecutorService threadPool = Executors.newFixedThreadPool(3);

            threadPool.execute(new Consumer("1", broker));
            threadPool.execute(new OrderProducer(broker, 2));
            //threadPool.execute(new OrderProducer(broker,2));
            //threadPool.execute(new Consumer("2", broker));

            threadPool.shutdown();
            threadPool.awaitTermination(50, TimeUnit.SECONDS);

            broker.printReport();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}