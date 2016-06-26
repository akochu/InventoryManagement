package com.java.inventorycontrol.model;

/**
 * The model object InventoryEntry encompasses the details of the inventory entries,
 * inclusing product name, counts.
 */
public class InventoryEntry implements Comparable<InventoryEntry>{
    private String name;
    private int count;

    public InventoryEntry(String name, int count){
        this.name = name;
        this.count = count;
    }

    public String getName(){
        return name;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setName(String name){ this.name = name; }

    @Override
    public int compareTo(InventoryEntry inventoryEntry){
        return inventoryEntry.getName().compareTo(this.getName());
    }


}
