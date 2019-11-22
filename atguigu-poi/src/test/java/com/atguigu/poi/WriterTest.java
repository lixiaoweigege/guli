package com.atguigu.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class WriterTest {

    @Test
    //HSSF是对03版本的excle进行操作xls
    public void writer03() throws Exception {
        // 创建新的Excel 工作簿
        Workbook workbook = new HSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值 Sheet0
        Sheet sheet = workbook.createSheet("会员统计表");
        //创建行
        Row row = sheet.createRow(0);
        //创建列
        Cell cell_00 = row.createCell(0);
        cell_00.setCellValue("大家好");
        Cell cell_01 = row.createCell(1);
        cell_01.setCellValue("666");
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\23132\\Desktop\\out.xls");
        workbook.write(outputStream);
        outputStream.close();
        System.out.println("成功啦");

    }

    @Test
    //XSSF是对07版本的excle进行操作xlsx
    public void writer07() throws Exception {
        // 创建新的Excel 工作簿
        Workbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值 Sheet0
        Sheet sheet = workbook.createSheet("会员统计表");
        //创建行
        Row row = sheet.createRow(0);
        //创建列
        Cell cell_00 = row.createCell(0);
        cell_00.setCellValue("大家好");
        Cell cell_01 = row.createCell(1);
        cell_01.setCellValue("666");
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\23132\\Desktop\\out.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        System.out.println("成功啦");

    }
}
