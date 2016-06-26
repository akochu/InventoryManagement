package com.java.inventorycontrol.OrderHandling;
import com.java.inventorycontrol.Inventory.InventoryManager;
import com.java.inventorycontrol.model.Order;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * The Broker object coordinates the interaction between producers, consumers, the queue
 * storing the order objects, and also adjusts items and quantities in the inventory
 * using the InventoryManager as well as in the ordersPlaced datastructure using the OrderTracker.
 */
public class OrderBroker {

    public BlockingQueue<Order> queue = new SynchronousQueue<>();
    public Boolean timeToReport = Boolean.FALSE;
    InventoryManager inventoryManager = InventoryManager.getInstance();
    OrderTracker orderTracker = OrderTracker.getInstance();

    public void put(Order order) throws InterruptedException
    {
        this.queue.put(order);
    }

    public Order get() throws InterruptedException
    {
        Order order = this.queue.poll(1, TimeUnit.SECONDS);
        this.timeToReport = placeOrderAndReport(order);
        return order;
    }

    public boolean placeOrderAndReport(Order order){
        this.inventoryManager.applyOrder(order, this.orderTracker);
        return this.inventoryManager.timeToReport();
    }

    /**
     * Prints the report by interacting with InventoryManager
     */
    public void printReport(){
        this.orderTracker.printReport(inventoryManager.getInventoryEntries());
    }

}

