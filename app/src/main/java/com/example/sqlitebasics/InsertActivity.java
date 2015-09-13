package com.example.sqlitebasics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Button btnOk = (Button) findViewById(R.id.ok_button);
        btnOk.setHint("ชื่อ");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText inputName = (EditText) findViewById(R.id.mInputName);
                EditText inputPhone = (EditText) findViewById(R.id.mInputPhoneNumber);
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();

                if(name.length() == 0 )
                {
                    Toast.makeText(InsertActivity.this,"กรุณาป้อนชื่อ์",Toast.LENGTH_SHORT).show();
                    return ;
                }else if(phone.length() == 0){
                    Toast.makeText(InsertActivity.this,"กรุณาป้อนเบอร์โทรศัพท์",Toast.LENGTH_SHORT).show();
                    return ;
                }

                intent.putExtra(MainActivity.KEY_NAME,name);
                intent.putExtra(MainActivity.KEY_PHONE,phone);

                setResult(RESULT_OK,intent);
                finish();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.cancel_button);
        btnCancel.setHint("ยกเลิก");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert, menu);
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
