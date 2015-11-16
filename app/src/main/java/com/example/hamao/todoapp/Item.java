package com.example.hamao.todoapp;



/**
 * Created by hamao on 11/15/15.
 */
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Item")
public class Item extends Model {
    @Column(name = "itemName")
    public String itemName;

    public Item(String itemName) {
        super();
        this.itemName = itemName;
    }

    public Item() {
        this("");
    }

    @Override
    public String toString() {
        return itemName;
    }
}
