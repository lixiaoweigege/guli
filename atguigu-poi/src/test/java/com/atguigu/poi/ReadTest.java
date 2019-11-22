package com.atguigu.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadTest {
    @Test
    public void read03() throws IOException {
//1.创建excle文件流
        FileInputStream stream = new FileInputStream("C:\\Users\\23132\\Desktop\\test.xls");
        //2创建workbookduix
        HSSFWorkbook workbook = new HSSFWorkbook(stream);
//3根据这个workBook对象读取哪一个Sheet（可以用名称取，也可以使用0-x）
        HSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();//获取总行数
        System.out.println("总行数为:" + rows);
        HSSFRow row = sheet.getRow(1);//读取第一行
        int cells = row.getPhysicalNumberOfCells();//获取总列数
        System.out.println("列数为：" + cells);
        HSSFCell cell = row.getCell(1); //读取第一列
        //获取列中的数据
        String value = cell.getStringCellValue();
        System.out.println(value);
        for (int i = 0; i < rows; i++) {
            HSSFRow row1 = sheet.getRow(i);
            for (int j = 0; j < cells; j++) {
                HSSFCell cell1 = row1.getCell(j);
                cell1.setCellType(Cell.CELL_TYPE_STRING);//把这一列中的所有数据都强转为string类型
                String value1 = cell1.getStringCellValue();
                if (value1 != null && value1 != "") {
                    System.out.print(value1 + "|");
                }

            }
            System.out.println(",");
        }
        stream.close();
    }

    @Test
    public void read07() throws Exception {
        FileInputStream stream = new FileInputStream("C:\\Users\\23132\\Desktop\\test.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(1);
        System.out.println(rows);
        System.out.println(cell.getStringCellValue());
    }

    @Test
    public void testCellType() throws Exception {
        //1.创建excle文件流
        FileInputStream stream = new FileInputStream("C:\\Users\\23132\\Desktop\\test.xls");
        //2创建workbookduix
        HSSFWorkbook workbook = new HSSFWorkbook(stream);
        //3根据这个workBook对象读取哪一个Sheet（可以用名称取，也可以使用0-x）
        HSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();//获取总行数
        System.out.println("总行数为:" + rows);
        HSSFRow row = sheet.getRow(1);//读取第一行
        int cells = row.getPhysicalNumberOfCells();//获取总列数
        System.out.println("列数为：" + cells);
        HSSFCell cell = row.getCell(1); //读取第一列
        //获取列中的数据
        String value = cell.getStringCellValue();
        System.out.println(value);
        for (int i = 0; i < rows; i++) {
            HSSFRow row1 = sheet.getRow(i);
            for (int j = 0; j < cells; j++) {
                HSSFCell cell1 = row1.getCell(j);
                System.out.println(i+":"+j);
                int cellType = cell.getCellType();
                System.out.println(cellType);
            }
        }
        stream.close();
    }
}
