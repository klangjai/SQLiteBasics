package com.example.sqlitebasics;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    protected static final int REQUEST_INSERT_CODE = 1;
    protected static final String KEY_NAME = "name";
    protected static final String KEY_PHONE = "phone";

    private MyHelper dbHelper;
    private SQLiteDatabase db;

    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = readAllData();

        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{MyHelper.COL_NAME,MyHelper.COL_PHONE_NUMBER},
                new int[]{android.R.id.text1,android.R.id.text2}
        );
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                String sql = "SELECT " + MyHelper.COL_NAME +" , " + MyHelper.COL_PHONE_NUMBER +
                        " FROM "+MyHelper.TABLE_NAME +
                        " WHERE " + MyHelper.COL_ID + " = '" + id+"';";
                Cursor cursor = db.rawQuery(sql,null);
                cursor.moveToNext();

                String msg = "คุณต้องการลบชื่อผู้ติดต่อ "+
                        cursor.getString(cursor.getColumnIndex(MyHelper.COL_NAME));

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("ยืนยันการลบ")
                        .setMessage(msg)
                        .setPositiveButton(
                                "ตกลง",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.execSQL(
                                                "DELETE FROM " + MyHelper.TABLE_NAME +
                                                " WHERE " + MyHelper.COL_ID + " = '" + id+"';"
                                        );
                                        Cursor cursor = readAllData();
                                        adapter.changeCursor(cursor);
                                    }
                                })
                        .setNegativeButton("ยกเลิก",null)
                .show();



            }
        });
        list.setAdapter(adapter);

        Button btnInsert = (Button) findViewById(R.id.insert_button);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivityForResult(intent,REQUEST_INSERT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_INSERT_CODE){
            if(resultCode == RESULT_OK){
                String name = data.getStringExtra(KEY_NAME);
                String phone = data.getStringExtra(KEY_PHONE);
                ContentValues cv = new ContentValues();
                cv.put(MyHelper.COL_NAME,name);
                cv.put(MyHelper.COL_PHONE_NUMBER, phone);
                db.insert(MyHelper.TABLE_NAME, null, cv);

                Cursor  cursor = readAllData();
                adapter.changeCursor(cursor);
            }
        }
    }

    private Cursor readAllData(){
        String[] columns = {
                MyHelper.COL_ID,
                MyHelper.COL_NAME,
                MyHelper.COL_PHONE_NUMBER
        };
        Cursor cursor = db.query(MyHelper.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
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
}
