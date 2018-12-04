package com.example.openai.translate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AutoCompleteTextView input;
    String[] arrword;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        final DbHelpter db = new DbHelpter(this);
        final List<String> list = db.gethis();
        listView = (ListView) findViewById(R.id.listview1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        List<String> auto = db.getword();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, auto);
        input.setAdapter(adapter);
        input.setThreshold(1);

        final ArrayAdapter<String> finalAdapter = adapter;
        input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tu;
                tu = input.getText().toString();
                Intent maintomean = new Intent(MainActivity.this, Mean.class);
                Bundle b = new Bundle();
                b.putString("word", tu);
                maintomean.putExtra("maintomean", b);
                db.addhis(tu);
                finalAdapter.notifyDataSetChanged();
                startActivity(maintomean);
                recreate();
            }
        });
        final ArrayAdapter<String> finalAdapter1 = adapter;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tu = list.get(position).toString();
                Intent maintomean = new Intent(MainActivity.this, Mean.class);
                Bundle b = new Bundle();
                b.putString("word", tu);
                maintomean.putExtra("maintomean", b);
                db.addhis(tu);
                finalAdapter1.notifyDataSetChanged();
                startActivity(maintomean);
                recreate();

            }
        });
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
        final DbHelpter db = new DbHelpter(this);

        if (id == R.id.action_your) {
            Intent maintovo = new Intent(MainActivity.this, Fav.class);
            startActivity(maintovo);
            return true;
        }

        if (id == R.id.action_toeic) {
            Intent maintovo = new Intent(MainActivity.this, Toeic.class);
            startActivity(maintovo);
            return true;
        }

        if (id == R.id.action_toelf) {
            Intent maintovo = new Intent(MainActivity.this, Toefl.class);
            startActivity(maintovo);
            return true;
        }

        if (id == R.id.action_ielts) {
            Intent maintovo = new Intent(MainActivity.this, Ielts.class);
            startActivity(maintovo);
            return true;
        }
        if (id == R.id.action_del) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn xóa toàn bộ lịch sử dịch không ?" +
                    "\nĐiều này là không thể hoàn tác.");
            builder.setCancelable(false);
            builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.delallhis();
                    Toast.makeText(MainActivity.this, "Đã xóa toàn bộ lịch sử dịch.", Toast.LENGTH_LONG).show();
                    recreate();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
