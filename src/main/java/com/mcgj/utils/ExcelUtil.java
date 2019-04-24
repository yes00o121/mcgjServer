package com.mcgj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel工具
 * @author ad
 *
 */
public class ExcelUtil {
	
	/**
	 * @param fileName 路径
	 * @param sheet	sheet
	 * @return
	 */
	public static Map<String,ArrayList<String>> readData(String fileName,String sheetName){
		try{
			Workbook workbook = null;
			File file  = new File(fileName);
			if(!file.exists()){
				return null;//文件不存在
			}
			InputStream fis = new FileInputStream(file);
			/** 对文件的合法性进行验证 */
			if (file.getName().matches("^.+\\.(?i)(xlsx)$")) {
				workbook = new XSSFWorkbook(fis);
			}
			/** 对文件的合法性进行验证 */
			if (file.getName().matches("^.+\\.(?i)(xls)$")) {
				workbook = new HSSFWorkbook(fis);
			}
			return transformMap(read(workbook,sheetName));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param fileName 路径
	 * @param sheet	sheetNum
	 * @return
	 */
	public static Map<String,ArrayList<String>> readData(String fileName,Integer sheetNum){
		try{
			Workbook workbook = null;
			File file  = new File(fileName);
			if(!file.exists()){
				return null;//文件不存在
			}
			InputStream fis = new FileInputStream(file);
			/** 对文件的合法性进行验证 */
			if (file.getName().matches("^.+\\.(?i)(xlsx)$")) {
				workbook = new XSSFWorkbook(fis);
			}
			/** 对文件的合法性进行验证 */
			if (file.getName().matches("^.+\\.(?i)(xls)$")) {
				workbook = new HSSFWorkbook(fis);
			}
			List<ArrayList<String>> list = read(workbook,sheetNum);
			return transformMap(read(workbook,sheetNum));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	//将list转为Map
	private static Map<String,ArrayList<String>> transformMap(List<ArrayList<String>> list){
		Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		for(int i=0;i<list.size();i++){
			//继续遍历
			String key = "";//记录第一条数据,后续作为key
			ArrayList<String> value = new ArrayList<String>();//value值
			for(int j = 0;j<list.get(i).size();j++){
				//获取第一条数据作为map的key
				if(j == 0){
					key = list.get(i).get(j);
					continue;
				}
				value.add(list.get(i).get(j));
				if(j == list.get(i).size()-1){//到了最后一条数据，将数据追加到map中
					map.put(key, value);
				}
			}
		}
		return map;
	}
	//根据sheetName获取
	private static List<ArrayList<String>> read(Workbook wb, String sheetName) {

		/** 单个sheet总行数 */
		int totalRows = 0;
		/** 单个sheet总列数 */
		int totalCells = 0;

		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		/** 得到sheet */
		sheet = wb.getSheet(sheetName);
		totalRows = sheet.getPhysicalNumberOfRows();
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		/** 循环Excel的行，注：从第二行开始读取数据，因为基础数据表.xls和教材目录表.xls模板都有标题 */
		for (int r = 1; r < totalRows; r++) {
			row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			ArrayList<String> rowLst = new ArrayList<String>();
			/** 循环Excel的列 */
			for (short c = 0; c < totalCells; c++) {
				cell = row.getCell(c);
				String cellValue = "";
				if (cell == null) {
					continue;
				}

				/** 处理空值类型 */
				if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
					continue;
				}
				/** 处理字符串型 */
				else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
					cellValue = cell.getStringCellValue();
				}
				/** 其它的,非以上几种数据类型 */
				else {
					cellValue = cell.toString() + "";
				}
				rowLst.add(cellValue);
			}
			dataLst.add(rowLst);
		}
		return dataLst;
	}
	
	/******************/
	//根据下标获取
	private static List<ArrayList<String>> read(Workbook wb, int sheetNum) {

		/** 单个sheet总行数 */
		int totalRows = 0;
		/** 单个sheet总列数 */
		int totalCells = 0;

		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		/** 得到sheet */
		sheet = wb.getSheetAt(sheetNum);
		totalRows = sheet.getPhysicalNumberOfRows();
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		Map<String,ArrayList<String>> maps = new HashMap<String, ArrayList<String>>();
		/** 循环Excel的行，注：从第二行开始读取数据，因为基础数据表.xls和教材目录表.xls模板都有标题 */
		for (int r = 1; r < totalRows; r++) {
			row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			ArrayList<String> rowLst = new ArrayList<String>();
			/** 循环Excel的列 */
			for (short c = 0; c < totalCells; c++) {
				cell = row.getCell(c);
				String cellValue = "";
				if (cell == null) {
					continue;
				}

				/** 处理空值类型 */
				if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
					continue;
				}
				/** 处理字符串型 */
				else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
					cellValue = cell.getStringCellValue();
				}
				/** 其它的,非以上几种数据类型 */
				else {
					cellValue = cell.toString() + "";
				}
				rowLst.add(cellValue);
			}
			
			dataLst.add(rowLst);
		}
		return dataLst;
	}
}
