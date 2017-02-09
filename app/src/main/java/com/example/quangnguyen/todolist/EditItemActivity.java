package com.example.quangnguyen.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    public static final String ITEM_NAME = "ITEM NAME";
    public static final String ITEM_POS = "ITEM POS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        final EditText etName = (EditText) findViewById(R.id.et_item);

        String itemName = getIntent().getStringExtra(ITEM_NAME);
        final int itemPos = getIntent().getIntExtra(ITEM_POS,0);

        etName.setText(itemName);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = etName.getText().toString();
                if (itemName.length() == 0){
                    etName.setError(getResources().getString(R.string.warning_empty_name));
                }else {
                    Intent dataCallBack = new Intent();
                    dataCallBack.putExtra(ITEM_NAME,itemName);
                    dataCallBack.putExtra(ITEM_POS,itemPos);
                    setResult(RESULT_OK, dataCallBack);
                    finish();
                }
            }
        });
    }
}
