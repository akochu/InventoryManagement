package com.java.inventorycontrol.Inventory;
import com.java.inventorycontrol.OrderHandling.OrderTracker;
import com.java.inventorycontrol.model.InventoryEntry;
import com.java.inventorycontrol.model.Order;
import com.java.inventorycontrol.model.OrderTrackingEntry;

import java.util.*;

/**
 * Singleton object created as a Manager of Inventory in the framework, so that only one instance of
 * the inventory exists in the framework.
 */
public class InventoryManager {

    private static InventoryManager inventoryManager = null;
    private static HashMap<Character, InventoryEntry> inventory = new LinkedHashMap<>();
    private static int size;
    private static void InventoryManager(){}

    public static InventoryManager getInstance(){
        if(inventoryManager == null){
            synchronized (InventoryManager.class){
                if(inventoryManager == null){
                    inventoryManager = new InventoryManager();
                    initInventory();
                }
            }
        }
        return inventoryManager;
    }

    /**
     * The initial state of the inventory is hardcoded here. This can be improved,
     * by reading from a file
     */
    public static void initInventory(){
        inventory.put('A', new InventoryEntry("A", 1));
        inventory.put('B', new InventoryEntry("B", 3));
        inventory.put('C', new InventoryEntry("C", 1));
        inventory.put('D', new InventoryEntry("D", 2));
        inventory.put('E', new InventoryEntry("E", 2));

        size = 9;
    }

    /**
     * The list of product names is returned as a list, this ensures that the
     * order in the report is the same as that maintained in the inventory
     * @return
     */
    public ArrayList<Character> getInventoryEntries(){
        return new ArrayList<Character>(inventory.keySet());
    }

    public synchronized void applyOrder(Order order, OrderTracker orderTracker){
        if(order != null) {
            ArrayList<Order.Line> lines = (ArrayList<Order.Line>) order.getLines();
            HashMap<Character, OrderTrackingEntry.LineDetails> linesMap = new HashMap<>();
            for (Order.Line line : lines) {
                if (inventory.containsKey(line.getProductName().charAt(0))) {
                    InventoryEntry inventoryEntry = inventory.get(line.getProductName().charAt(0));
                    if (inventoryEntry.getCount() >= line.getQuantity()) {
                        inventoryEntry.setCount(inventoryEntry.getCount() - line.getQuantity());
                        inventory.put(line.getProductName().charAt(0), inventoryEntry);
                        OrderTrackingEntry.LineDetails lineDetails =
                                new OrderTrackingEntry.LineDetails(
                                        line.getProductName().charAt(0),
                                        line.getQuantity(),
                                        line.getQuantity(),
                                        0);
                        linesMap.put(line.getProductName().charAt(0), lineDetails);
                        size = size - line.getQuantity();
                    }
                    else {
                        OrderTrackingEntry.LineDetails lineDetails =
                                new OrderTrackingEntry.LineDetails(
                                        line.getProductName().charAt(0),
                                        line.getQuantity(),
                                        0,
                                        line.getQuantity());
                        linesMap.put(line.getProductName().charAt(0), lineDetails);
                    }
                }
            }
            OrderTrackingEntry orderTrackingEntry = new OrderTrackingEntry(order.getHeader(), order.getStreamId(), linesMap);
            orderTracker.inputOrderDetails(orderTrackingEntry);
        }
    }

    public boolean timeToReport(){
        if(size <= 0){
            return true;
        }
        return false;
    }


}
