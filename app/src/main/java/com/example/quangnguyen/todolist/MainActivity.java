package com.example.quangnguyen.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quangnguyen.todolist.Adapter.TodoListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items = new ArrayList<>();
    ListView listView;
    public static final int REQUEST_CODE_EDIT_ITEM = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init list items in to do list
        listView = (ListView) findViewById(R.id.list_view);
        //handle Add item
        final EditText etAddItem = (EditText) findViewById(R.id.et_new_item);
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = etAddItem.getText().toString();
                if (itemName.length() == 0){
                    etAddItem.setError(getResources().getString(R.string.warning_empty_name));
                }else{
                    items.add(itemName);
                    listView.setAdapter(new TodoListItemAdapter(items));
                    etAddItem.setText("");
                    saveItems(items);
                }
            }
        });

        getItems();
    }

    private final String KEY_ITEM = "listItems";
    private final String PRIVATE_KEY = "privateKeyTest123";
    private void saveItems(List<String> items){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String itemList = "";
        for (int i = 0 ; i < items.size() ; i ++){
            if (i == 0)
                itemList = items.get(i);
            else
                itemList = itemList + PRIVATE_KEY + items.get(i);
        }
        editor.putString(KEY_ITEM, itemList);
        editor.commit();
    }

    private void getItems(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String listItems = sharedPref.getString(KEY_ITEM,"");
        if (listItems.length()>0) {
            for (String item : listItems.split(PRIVATE_KEY)) {
                items.add(item);
            }
            listView.setAdapter(new TodoListItemAdapter(items));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&  requestCode == REQUEST_CODE_EDIT_ITEM){
            String newItemName = data.getStringExtra(EditItemActivity.ITEM_NAME);
            int itemPos = data.getIntExtra(EditItemActivity.ITEM_POS,0);

            items.remove(itemPos);
            items.add(newItemName);
            saveItems(items);
            listView.setAdapter(new TodoListItemAdapter(items));
        }
    }
}
