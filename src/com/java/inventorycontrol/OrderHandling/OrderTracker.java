package com.java.inventorycontrol.OrderHandling;

import com.java.inventorycontrol.model.OrderTrackingEntry;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * OrderTracker is implemented as a Singleton object, to track every order
 * that is consumed and passed on by the OrderBroker. The orders are stored into
 * a LinkedHashSet to retain the order of insertion.
 */
public class OrderTracker {
    private static OrderTracker orderTracker = null;
    private static LinkedHashSet<OrderTrackingEntry> ordersPlaced = new LinkedHashSet<OrderTrackingEntry>();

    private OrderTracker(){}

    public static OrderTracker getInstance(){
        if(orderTracker == null){
            synchronized (OrderTracker.class){
                if(orderTracker == null){
                    orderTracker = new OrderTracker();
                }
            }
        }
        return orderTracker;
    }

    /**
     * Method used to print out the entries in the ordersPlaced
     * @param inventoryEntries
     */
    public void printReport(ArrayList<Character> inventoryEntries){
        for(OrderTrackingEntry entry : ordersPlaced){
            System.out.println(entry.formatForReport(inventoryEntries));
        }
    }

    public void inputOrderDetails(OrderTrackingEntry orderTrackingEntry){
        ordersPlaced.add(orderTrackingEntry);
    }
}

