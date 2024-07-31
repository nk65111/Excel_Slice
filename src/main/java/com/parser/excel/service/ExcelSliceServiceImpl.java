package com.parser.excel.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.parser.excel.model.User;

@Service
public class ExcelSliceServiceImpl implements ExcelSliceService {

     @Autowired
     private EmailService emailService;

    @Override
    public void slice(MultipartFile file, Integer to, Integer from,User user)   {
       
        try{
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Workbook newWorkbook = new XSSFWorkbook();
        Sheet newSheet = newWorkbook.createSheet();

        int newRowNum = 0;
        for (int i = to ; i < from; i++) {  
            Row row = sheet.getRow(i);
            if(row==null){
                break;
            }
            if (row != null) {
                Row newRow = newSheet.createRow(newRowNum++);

                copyRow(row, newRow);
            }
        }
      
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newWorkbook.write(outputStream);

        workbook.close();
        newWorkbook.close();

        byte[] excelBytes = outputStream.toByteArray();
        
     // Create a DateTimeFormatter to format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        
        // Format the current date
        String formattedDate = currentDate.format(formatter);
        if(newRowNum>0) {
	        emailService.sendEmailWithAttachment(user.getEmail(), "Excel File Attached", "Please find the Excel file attached.", excelBytes, user.getName()+ "_"+formattedDate +".xlsx");
	
	        System.out.println("ESJSSK*************************");
        }
        }catch(Exception e){
            e.printStackTrace();
        }

       

    }

    private void copyRow(Row source, Row destination) {
        for (int i = 0; i < source.getLastCellNum(); i++) {
            Cell oldCell = source.getCell(i);
            Cell newCell = destination.createCell(i);

            if (oldCell != null) {
                switch (oldCell.getCellType()) {
                    case STRING:
                       
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(oldCell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(oldCell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellFormula(oldCell.getCellFormula());
                        break;
                    default:
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                }
            }
        }
    }


    
    
}
