package com.example.a007.xml.Processor;


import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Process {
    private final Context mContext;
    private final String mInputString;


    public Process(final Context mContext) {
        this.mContext = mContext;
        this.mInputString = null;
    }

    public Process(final Context mContext, final String mInputString) {
        this.mContext = mContext;
        this.mInputString = mInputString;
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

            Toast.makeText(mContext, "Done!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, file.getName() + " doesn't exist!", Toast.LENGTH_LONG).show();
        }
    }
    @Nullable
    private int[] textToIntArray() {

        String edt1;
        String pattern;
        String replace;
        String[] stringArray;

        if (mInputString != null) {
            edt1 = mInputString.toLowerCase();

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
        }
        return null;
    }
    public void ProcessString() throws IOException {
        doExcel(textToIntArray());
    }

    public boolean testTextToIntArray() {
        return textToIntArray() != null;
    }
}
