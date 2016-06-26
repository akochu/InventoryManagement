package com.java.inventorycontrol.model;


import java.util.ArrayList;
import java.util.List;

/**
 * The model object Order is immutable, because it cannot be changed once it is created.
 * The only way to change it, would be to create a new Order object.
 */
public class Order {
    private int header;
    private long streamId;

    public static class Line {
        private String productName;
        private int quantity;

        public Line(String productName, int quantity){
            this.productName = productName;
            this.quantity = quantity;
        }

        public String getProductName(){
            return productName;
        }

        public int getQuantity(){
            return quantity;
        }

    }

    private List<Line> lines;

    public Order(int header, long streamId, ArrayList<Line> lines){
        this.header = header;
        this.streamId = streamId;
        this.lines = lines;
    }

    public int getHeader(){
        return header;
    }

    public long getStreamId(){
        return streamId;
    }

    public List<Line> getLines(){
        return this.lines;
    }

}
