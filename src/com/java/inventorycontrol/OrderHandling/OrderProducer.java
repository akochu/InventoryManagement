package com.java.inventorycontrol.OrderHandling;

import com.java.inventorycontrol.model.Order;

import java.util.ArrayList;
import java.util.Random;

/**
 * Producer implementation that produces Order objects and passes it to the broker
 * to be inserted into the Queue.
 */
public class OrderProducer implements Runnable  {
    private OrderBroker broker;
    private int noLines;

    public OrderProducer(OrderBroker broker, int noLines){
        this.broker = broker;
        this.noLines = noLines;
    }

    @Override
    public void run() {
        try {
            while (!broker.timeToReport){
                Thread.sleep(100);
                Random random = new Random();
                ArrayList<Order.Line> lines = new ArrayList<>();
                for(int j = 0; j<noLines; j++){
                    int no = random.nextInt(5);
                    char name = (char) ('A' + no);
                    int count = random.nextInt(5);
                    Order.Line line = new Order.Line(String.valueOf(name), count+1);
                    lines.add(line);
                }

                Order order = new Order(random.nextInt(100), Thread.currentThread().getId(), lines);
                System.out.println("Order produced with header: " + order.getHeader()
                        + " and streamid: " + order.getStreamId()
                        + " and lines are ==>");
                for(Order.Line line : order.getLines()){
                    System.out.println("Product: " + line.getProductName() + " quantity: " + line.getQuantity());
                }

                broker.put(order);
            }

            System.out.println("Consumer has been running, Producer finished its job; terminating, timeToreport: ");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

