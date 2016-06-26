package com.java.inventorycontrol.model;


import java.util.*;

/**
 * Model object OrderTrackingEntry are entries in the OrderTracker singleton object. Every order,
 * translates into an OrderTrackerEntry. It stores the header id, stream id, the lines which are stored using
 * instances of LineDetails. LineDetails encompass product name, quantities ordered, quantities allocated
 * and if the order cannot be fulfilled, then backordered quantity.
 */
public class OrderTrackingEntry implements Comparable<OrderTrackingEntry>{
    private int headerId;
    private long streamId;
    private final String COMMA = ",";

    private Map<Character, LineDetails> lineDetails = new HashMap<>();

    public static class LineDetails{
        private char productName;
        private int productQuantity;
        private int quantityAllocated;
        private int quantityBackOrdered;

        public LineDetails(char productName, int productQuantity, int quantityAllocated, int quantityBackOrdered){
            this.productName = productName;
            this.productQuantity = productQuantity;
            this.quantityAllocated = quantityAllocated;
            this.quantityBackOrdered = quantityBackOrdered;
        }

        public char getProductName() {
            return productName;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public int getQuantityAllocated() {
            return quantityAllocated;
        }

        public int getQuantityBackOrdered() {
            return quantityBackOrdered;
        }
    }

    public OrderTrackingEntry(int headerId, long streamId, HashMap<Character, LineDetails> lineDetails){
        this.headerId = headerId;
        this.streamId = streamId;
        this.lineDetails = lineDetails;
    }

    public String formatForReport(ArrayList<Character> inventoryKeys){

        StringBuilder orderQuantities = new StringBuilder();
        StringBuilder allocatedQuantities = new StringBuilder();
        StringBuilder backOrderedQuantities = new StringBuilder();

        HashMap<Character, LineDetails> lineDetailsMap = (HashMap<Character, LineDetails>) this.getLineDetails();
        char first = inventoryKeys.iterator().next();

        for(Character key : inventoryKeys){
            if(!key.equals(first)){
                orderQuantities.append(COMMA);
                allocatedQuantities.append(COMMA);
                backOrderedQuantities.append(COMMA);
            }
            if(lineDetailsMap.containsKey(key)){
                orderQuantities.append(lineDetailsMap.get(key).getProductQuantity());
                allocatedQuantities.append(lineDetailsMap.get(key).getQuantityAllocated());
                backOrderedQuantities.append(lineDetailsMap.get(key).getQuantityBackOrdered());
            }
            else{
                orderQuantities.append("0");
                allocatedQuantities.append("0");
                backOrderedQuantities.append("0");
            }
        }
        String result = String.format("%3d: %s::%s::%s",
                this.getHeaderId(),
                orderQuantities.toString(),
                allocatedQuantities.toString(),
                backOrderedQuantities.toString());

        return result;
    }

    @Override
    public int compareTo(OrderTrackingEntry o) {
        if(Integer.compare(this.headerId, o.headerId) == 0)
            return (Long.compare(this.streamId, o.streamId));

        return (Integer.compare(this.headerId, o.headerId));

    }

    public int getHeaderId(){
        return headerId;
    }

    public long getStreamId(){
        return streamId;
    }

    public Map<Character, LineDetails> getLineDetails(){
        return lineDetails;
    }

}
