package com.thd.report.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 适合2007版本的（不适合2003版本的）
 * @author Administrator
 *
 */
public class ReportTest {

	InputStream fs;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	public ReportTest() throws IOException {
		fs = new FileInputStream("D:\\Book1.xlsx");
		workbook = new XSSFWorkbook(fs);
	}

	public void resultSetToExcel(String[] str, int index) throws IOException {
		if (str.length == 0) {
			return;
		}
		if (workbook == null) {
			System.out.println("WorkSheet is null");
			return;
		}
		sheet = workbook.getSheetAt(index);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		//		style.setFillForegroundColor(HSSFColor.DARK_RED.index);
		//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//		style.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		//		style.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		//		style.setBorderRight(HSSFCellStyle.BORDER_THICK);
		//		style.setBorderTop(HSSFCellStyle.BORDER_THICK);//顶部线条
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);//设置水平对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置垂直对齐方式

		// 生成一个字体
		//		XSSFFont font = workbook.createFont();
		//		font.setColor(HSSFColor.VIOLET.index);
		//		font.setFontHeightInPoints((short) 12);
		//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//		// 把字体应用到当前的样式
		//		style.setFont(font);
		if (sheet != null) {
			row = sheet.createRow(1);
			for (int i = 0; i < str.length; i++) {
				cell = row.createCell(i);
				if (str[i] != null) {
					str[i] = str[i].toString();
					XSSFRichTextString richString = new XSSFRichTextString(str[i]);
					XSSFFont font3 = workbook.createFont();
					font3.setColor(HSSFColor.RED.index);//设置单元格字体颜色：BLUE蓝色 GOLD金色
					richString.applyFont(font3);
					cell.setCellValue(richString);
					cell.setCellStyle(style);
					System.out.println(cell);
				}
			}
			FileOutputStream fout = new FileOutputStream("D:\\bb.xlsx");
			workbook.write(fout);
			fout.flush();
			fout.close();
			JOptionPane.showMessageDialog(null, "报表生成!");
		}
	}

	public static void main(String[] args) throws IOException {
		ReportTest dt = new ReportTest();
		String[] str = { "1", "3", "5", "4", "6", "7" };
		dt.resultSetToExcel(str, 0);
	}
}
