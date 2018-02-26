package com.example.a007.xml.Processor;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateCellStyle {


    public static void createCellStyle(Workbook wb, Cell cell, BorderStyle styleBorderBottom, BorderStyle styleBorderLeft,
                                       BorderStyle styleBorderRight, BorderStyle styleBorderTop) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(styleBorderBottom);
        style.setBorderLeft(styleBorderLeft);
        style.setBorderRight(styleBorderRight);
        style.setBorderTop(styleBorderTop);
        style.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(style);
    }
}