package com.policybazaar.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class ExcelUtil {
    
    private static final Logger logger = LogManager.getLogger(ExcelUtil.class);

    public static void writeToExcel(LinkedHashMap<String, String> mapData, String fileName, String sheetName) {
        logger.info("Starting Excel write operation");
        
        try {
            logger.info("Creating new Excel workbook");
            Workbook workbook = new XSSFWorkbook();
            
            logger.info("Creating sheet: {}", sheetName);
            Sheet sheet = workbook.createSheet(sheetName);
            
            // Create header row
            logger.info("Creating header row");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Provider Name");
            headerRow.createCell(1).setCellValue("Price");
            logger.info("Header row created successfully");
            
            // Write data
            logger.info("Writing data rows to Excel");
            int rowIndex = 1;
            for (String providerName : mapData.keySet()) {
                Row dataRow = sheet.createRow(rowIndex);
                dataRow.createCell(0).setCellValue(providerName);
                dataRow.createCell(1).setCellValue(mapData.get(providerName));
                rowIndex++;
            }
            logger.info("Successfully written {} data rows", mapData.size());
            
            // Auto-size columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            
            // Write to file
            FileOutputStream fos = new FileOutputStream(fileName);
            workbook.write(fos);
            fos.close();
            workbook.close();
            logger.info("File written and resources closed successfully");
            
            logger.info("Excel write operation completed successfully - File: {}, Sheet: {}", fileName, sheetName);
            
        } catch (IOException e) {
            logger.error("Error writing to Excel file: {}", fileName, e);
            logger.error("Exception details: {}", e.getMessage());
            System.out.println("❌ Error writing to Excel: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Excel write operation", e);
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

	public static void writeListToExcel(List<String> items, String fileName, String sheetName) {
		logger.info("Starting Excel write operation for list");
		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(sheetName);

			// Header
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Name");

			int rowIndex = 1;
			for (String item : items) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(item);
			}

			sheet.autoSizeColumn(0);

			FileOutputStream fos = new FileOutputStream(fileName);
			workbook.write(fos);
			fos.close();
			workbook.close();
			logger.info("Excel list write completed - File: {}, Sheet: {}, Rows: {}", fileName, sheetName, items.size());
		} catch (IOException e) {
			logger.error("Error writing list to Excel: {}", fileName, e);
		} catch (Exception e) {
			logger.error("Unexpected error during list Excel write operation", e);
		}
	}

	public static List<String> readFirstColumn(String fileName, String sheetName) {
		logger.info("Reading first column from Excel - File: {}, Sheet: {}", fileName, sheetName);
		List<String> values = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(fileName); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				logger.warn("Sheet not found: {}", sheetName);
				return values;
			}
			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			for (int i = firstRow + 1; i <= lastRow; i++) { // skip header row
				Row row = sheet.getRow(i);
				if (row == null) continue;
				Cell cell = row.getCell(0);
				if (cell == null) continue;
				cell.setCellType(CellType.STRING);
				String val = cell.getStringCellValue();
				if (val != null && !val.trim().isEmpty()) {
					values.add(val.trim());
				}
			}
		} catch (IOException e) {
			logger.error("Error reading Excel: {}", e.getMessage());
		}
		logger.info("Read {} values from Excel first column", values.size());
		return values;
	}
}

