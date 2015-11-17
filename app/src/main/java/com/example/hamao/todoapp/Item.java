package com.example.hamao.todoapp;



/**
 * Created by hamao on 11/15/15.
 */
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Item")
public class Item extends Model {

    @Column(name = "ItemName")
    public String itemName;

    @Column(name = "DueDate")
    public String dueDate;

    public Item(String itemName, String dueDate) {
        super();
        this.itemName = itemName;
        this.dueDate = dueDate;
    }

    public Item() {
        this("", "");
    }

}
