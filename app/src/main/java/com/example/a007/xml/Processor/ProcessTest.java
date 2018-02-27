package com.example.a007.xml.Processor;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.a007.xml.MainActivity.LOG_TAG;
import static com.example.a007.xml.Processor.CreateCellStyle.createCellStyle;

public class ProcessTest {
    private final Context mContext;
    private final String mInputString;
    private final BorderStyle thin = BorderStyle.THIN;


    public ProcessTest(final Context mContext, final String mInputString) {
        this.mContext = mContext;
        this.mInputString = mInputString;
    }

    public void doExcelTest() throws IOException {

        Cell cell;
        Row row;
        Pattern p;
        Matcher m;
        String r;
        String text;
        String[] stringArray;


        if (mInputString != null) {
            text = mInputString.toLowerCase();

            String root = Environment.getExternalStorageDirectory().toString() + "/proscrit/";
            File file = new File(root, "1.xls");

            if (file.exists()) {

                // Read XSL file
                FileInputStream inputStream = new FileInputStream(file);

                // Get the workbook instance for XLS file
                HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

                // Get first sheet from the workbook
                HSSFSheet sheet = workbook.getSheetAt(0);

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
                                    createCellStyle(workbook, cell, thin, thin, thin, thin);
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

                Toast.makeText(mContext, "Done!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, file.getName() + " doesn't exist!", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.i(LOG_TAG, "mInputString == null");
        }
    }
}
