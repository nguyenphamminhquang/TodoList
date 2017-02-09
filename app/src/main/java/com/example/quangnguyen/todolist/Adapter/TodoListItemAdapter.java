package com.example.quangnguyen.todolist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quangnguyen.todolist.EditItemActivity;
import com.example.quangnguyen.todolist.MainActivity;
import com.example.quangnguyen.todolist.R;

import java.util.List;

/**
 * Created by quangnguyen on 2/9/17.
 */

public class TodoListItemAdapter extends BaseAdapter {
    private List<String> items;
    private Activity activity;

    public TodoListItemAdapter(List<String> items){
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        activity = (Activity) parent.getContext();
        View v = LayoutInflater.from(activity).inflate(R.layout.item_todolist, parent, false);
        TextView textView = (TextView) v.findViewById(R.id.tv_item);
        textView.setText(getItem(position));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditItem = new Intent(activity, EditItemActivity.class);
                intentEditItem.putExtra(EditItemActivity.ITEM_NAME,items.get(position));
                intentEditItem.putExtra(EditItemActivity.ITEM_POS,position);
                activity.startActivityForResult(intentEditItem, MainActivity.REQUEST_CODE_EDIT_ITEM);
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Do you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return false;
            }
        });
        return v;
    }

}


