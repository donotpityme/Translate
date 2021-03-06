package com.example.openai.translate;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class Toefl extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toefl);

        listView = findViewById(R.id.listview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Từ vựng Toefl");
        final DbHelpter dbHelpter = new DbHelpter(this);
        final List<String> list = dbHelpter.gettoefl();
        ListView listView = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tu = list.get(position).toString();
                Intent maintomean = new Intent(Toefl.this, Mean.class);
                Bundle b = new Bundle();
                b.putString("word", tu);
                maintomean.putExtra("maintomean", b);
                dbHelpter.addhis(tu);
                adapter.notifyDataSetChanged();
                startActivity(maintomean);
            }
        });
    }
}
