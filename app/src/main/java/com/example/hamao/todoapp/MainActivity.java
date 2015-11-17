package com.example.hamao.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditItemDialog.EditTodoItemDialogListener{

    private ArrayList<Item> items;
    private ItemsAdapter itemAdapter;
    private ListView lvItems;
    private Item selectedItem;
    private final int REQUEST_CODE = 42;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        itemAdapter = new ItemsAdapter(this, items);;
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(itemAdapter);
        setupListViewListener();

    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Item item = new Item(itemText, DateFormat.getDateInstance().format(new Date()));
        itemAdapter.add(item);
        etNewItem.setText("");
        item.save();
    }


    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                Item item = items.remove(position);
                item.delete();
                itemAdapter.notifyDataSetChanged();
                return true;

            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = items.get(position);
                showEditItemDialog();
            }
        });
    }

    private void showEditItemDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editTodoItemDialog = EditItemDialog.newInstance("Edit Item", selectedItem.itemName, selectedItem.dueDate);
        editTodoItemDialog.show(fm, "edit_item");
    }

    private void readItems(){
        try{
            List<Item> dbItems = new Select().from(Item.class).execute();
            items = new ArrayList<>(dbItems);
        }catch(Exception e){
            items = new ArrayList<Item>();
        }
    }

    @Override
    public void onFinishEditDialog(String inputText, String Date) {
        selectedItem.itemName = inputText;
        selectedItem.dueDate = Date;
        selectedItem.save();
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            itemAdapter.notifyDataSetChanged();
        }
    }
}
