package com.example.hamao.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private ItemsAdapter itemAdapter;
    private ListView lvItems;
    private final String POSITION = "position";
    private final int EDIT_TEXT_REQUEST_CODE = 1;
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
        Item item = new Item(itemText);
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
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra(EditItemActivity.ITEM, items.get(position).itemName);
                i.putExtra(POSITION, position);
                startActivityForResult(i, EDIT_TEXT_REQUEST_CODE);
            }
        });
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
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_REQUEST_CODE) {
            String itemText = data.getStringExtra(EditItemActivity.ITEM).trim();
            int position = data.getIntExtra(POSITION, -1);
            if ((itemText == null) || itemText.isEmpty() || (position == -1) || (position >= items.size())) {
                return;
            }

            Item item = new Item(itemText);
//            items.set(position, item);
            items.set(position, item);
            itemAdapter.notifyDataSetChanged();
            item.save();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
