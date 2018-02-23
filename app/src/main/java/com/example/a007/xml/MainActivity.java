package com.example.a007.xml;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a007.xml.Processor.Process;
import com.example.a007.xml.Processor.ProcessTest;

import java.io.IOException;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    public static final String LOG_TAG = "proscrit";
    private EditText edtTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1;
        Button btn2;
        Button btn3;
//        TextView txtView1;
//        TextView txtView2;
//        TextView txtView3;

        edtTxt = (EditText) findViewById(R.id.editText1);
        btn1 = (Button) findViewById(R.id.button_1);
        btn2 = (Button) findViewById(R.id.button_2);
        btn3 = (Button) findViewById(R.id.button_3);
//        txtView1 = (TextView) findViewById(R.id.textView_1);
//        txtView2 = (TextView) findViewById(R.id.textView_2);
//        txtView3 = (TextView) findViewById(R.id.textView_3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    public void onClick(View v) {

        final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;

        if (TextUtils.isEmpty(edtTxt.getText().toString())) {
            Toast.makeText(this, "Nothing at input field", Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            case R.id.button_1:
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                }
                if (!new Process(this).testTextToIntArray()) {
                    Process process = new Process(this, edtTxt.getText().toString());
                    try {
                        process.ProcessString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Please format input string", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.button_2:
                break;

            case R.id.button_3:
                ProcessTest processTest = new ProcessTest(this, edtTxt.getText().toString());
                try {
                    processTest.doExcelTest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
