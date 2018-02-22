package com.example.a007.xml;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.*;

import java.util.Arrays;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private EditText edtTxt;
    private String[] stringArray;


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

                if (textToIntArray() != null) {
                    try {
                        doExcel(textToIntArray());
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
                try {
                    doExcelTest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private void doExcel(int[] array) throws IOException {

        Cell cell;
        Row row;

        String root = Environment.getExternalStorageDirectory().toString() + "/proscrit/";
        File file = new File(root, "1.xls");

        if (file.exists()) {

            // Read XSL file
            FileInputStream inputStream = new FileInputStream(file);

            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            // Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < array.length; i++) {
                if (i % 2 == 0) {
                    if (i < array.length - 1) {
                        row = sheet.getRow(array[i] - 1);
                        cell = row.createCell(4, CellType.NUMERIC);
                        cell.setCellValue(array[i + 1]);
                    }
                }
            }
            inputStream.close();

            // Write File
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();

            Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, file.getName() + " doesn't exist!", Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    private int[] textToIntArray() {

        String edt1;
        String pattern;
        String replace;

        edt1 = edtTxt.getText().toString().toLowerCase();

        pattern = "[a-zа-я:\\.]";
        replace = "";

        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher(edt1);

        if (m.find()) {
            String q = m.replaceAll(replace).trim();

            pattern = "[\\s]";
            replace = "";

            p = Pattern.compile(pattern);
            m = p.matcher(q);

            if (m.find()) {
                q = m.replaceAll(replace);

                pattern = "[-]";
                replace = ",";

                p = Pattern.compile(pattern);
                m = p.matcher(q);

                if (m.find()) {
                    q = m.replaceAll(replace);
                    stringArray = q.split(",");
                    int[] intArray = new int[stringArray.length];
                    for (int i = 0; i < stringArray.length; i++) {
                        intArray[i] = Integer.parseInt(stringArray[i]);
                    }
                    Log.v("proscrit", Arrays.toString(intArray));

                    return intArray;
                }
            }
        }
        return null;
    }

    private void doExcelTest() throws IOException {
        Cell cell;
        Row row;
        Pattern p;
        Matcher m;
        String r;

        String root = Environment.getExternalStorageDirectory().toString() + "/proscrit/";
        File file = new File(root, "1.xls");

        if (file.exists()) {

            // Read XSL file
            FileInputStream inputStream = new FileInputStream(file);

            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            // Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            String text = edtTxt.getText().toString().toLowerCase();

            p = Pattern.compile("\\s");
            r = "";

            m = p.matcher(text);

            if (m.find()) {
                text = m.replaceAll(r);

                p = Pattern.compile("\\d+-\\d+");

                m = p.matcher(text);

                while (m.find()) {

                    String test = m.group();

                    stringArray = test.split("-");

                    int[] intArray = new int[stringArray.length];

                    for (int i = 0; i < stringArray.length; i++) {
                        intArray[i] = Integer.parseInt(stringArray[i]);
                    }
                    for (int i = 0; i < intArray.length; i++) {
                        if (i % 2 == 0) {
                            if (i < intArray.length - 1) {
                                row = sheet.getRow(intArray[i] - 1);
                                cell = row.createCell(4, CellType.NUMERIC);
                                cell.setCellValue(intArray[i + 1]);
                            }
                        }
                    }
                }
            }
            inputStream.close();

            // Write File
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();

            Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, file.getName() + " doesn't exist!", Toast.LENGTH_LONG).show();
        }
    }
}
