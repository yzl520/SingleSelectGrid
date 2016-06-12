package com.android.yzl.singleselectgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.yzl.lib.SingleSelectGrid;

public class MainActivity extends AppCompatActivity implements SingleSelectGrid.SingleSelectGridAdapter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingleSelectGrid ssg = (SingleSelectGrid) findViewById(R.id.ssg);
        ssg.setAdapter(this);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public View getView(int pos, ViewGroup parent) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_ssg, parent, false);
        return view;
    }

    @Override
    public void onSelect(int pos, View v, View lastSelectItem) {
        Toast.makeText(this, "" + pos, Toast.LENGTH_SHORT).show();
    }
}
