package com.policybazaar.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.policybazaar.model.TravelInsuranceData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.LinkedHashMap;


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

    public static TravelInsuranceData readExcelData(String filePath, String sheetName) {
        
        String[] excelData = null;
        TravelInsuranceData data = null;

        logger.info("Reading data from Excel file.");

        try {
            // Open Excel file
            FileInputStream file = new FileInputStream(filePath);
            XSSFWorkbook workBook = new XSSFWorkbook(file);
            XSSFSheet sheet = workBook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);

            // Read all cells in the first row
            int cellCount = row.getLastCellNum();
            excelData = new String[cellCount];
            DataFormatter formatter = new DataFormatter();

            // Store formatted cell values in array
            for (int i = 0; i < cellCount; i++) {
                XSSFCell cell = row.getCell(i);
                excelData[i] = (cell != null) ? formatter.formatCellValue(cell) : "";
            }

            // Close resources
            workBook.close();
            logger.info("Workbook closed");
            file.close();
            logger.info("File closed");

            // Create RegistrationInfo object using read data
            data = new TravelInsuranceData(
                excelData[0], excelData[1], excelData[2],
                Integer.parseInt(excelData[3]),  Integer.parseInt(excelData[4]),  Integer.parseInt(excelData[5]),Boolean.parseBoolean(excelData[6])
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}