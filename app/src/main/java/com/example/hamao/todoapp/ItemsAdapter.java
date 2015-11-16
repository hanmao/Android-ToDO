package com.example.hamao.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hamao on 11/15/15.
 */
public class ItemsAdapter extends ArrayAdapter<Item> {
    public ItemsAdapter(Context context, ArrayList<Item> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup view for data population
        TextView todoName = (TextView) convertView.findViewById(R.id.todoItemName);

        // Populate the data into the template view using the data object
        todoName.setText(todoItem.itemName);
        // Return the completed view to render on screen
        return convertView;
    }
}
