package com.example.activitycounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.activitycounter.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String nowDate;
    long count;
    ActivityMainBinding binding;
    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper mDbOpenHelper;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding 설정
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // DB
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        // ListView
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        // count load
        mContext = this;
        count = PreferenceManager.getLong(mContext, "count");
        binding.countText.setText(count + " 개");

        // date variable
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("YYYY년 MM월 dd일");
        nowDate = sdfNow.format(date);

        loadDataBase();

        // save
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int weight = Integer.parseInt(binding.weightText.getText().toString());
                mDbOpenHelper.open();
                mDbOpenHelper.insertColumn(nowDate, count, weight);
                loadDataBase();
                count = 0;
                changeCount(count);
            }
        });

        // sum
        binding.sumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = binding.countInput.getText().toString();
                if (str.equals("")) {
                } else {
                    count += Integer.parseInt(str);
                    changeCount(count);
                }
            }
        });
    }

    private void changeCount(long count) {
        PreferenceManager.setLong(mContext, "count", count);
        binding.countText.setText(count + " 개");
        binding.countInput.setText("");
    }

    private void loadDataBase() {
        Cursor iCursor = mDbOpenHelper.selectColumns();
        arrayData.clear();
        arrayAdapter.clear();
        while ((iCursor.moveToNext())) {
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempdate = iCursor.getString(iCursor.getColumnIndex("date"));
            String tempcount = iCursor.getString(iCursor.getColumnIndex("count"));
            Integer tempweight = iCursor.getInt(iCursor.getColumnIndex("WEIGHT"));
            String Result = "        " + tempdate + " - " + tempcount + " 개 / " + tempweight + " kg";
            arrayData.add(Result);
        }
        arrayAdapter.addAll(arrayData);
        arrayAdapter.notifyDataSetChanged();
        iCursor.close();
    }
}