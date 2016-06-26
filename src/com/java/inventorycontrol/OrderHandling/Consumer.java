package com.java.inventorycontrol.OrderHandling;

import com.java.inventorycontrol.model.Order;

/**
 * The consumer object is a straightforward implementation
 */
public class Consumer implements Runnable {
    private String name;
    private OrderBroker broker;

    public Consumer(String name, OrderBroker broker){
        this.name = name;
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            Order order = broker.get();
            while (!broker.timeToReport || order != null){
                Thread.sleep(100);
                order = broker.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
