package com.parser.excel.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.parser.excel.model.User;

@Service
public class ExcelSendServiceImpl implements ExcelSendService{

    @Autowired 
    private ExcelSliceService excelSliceService;

    

    @Override
    public void sendExcel(MultipartFile userData, MultipartFile actualData) {
       
       int startRow=0;
        try {
            InputStream inputStream = userData.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> headerMap = new HashMap<>();
            boolean isHeaderRow = true;

            for (Row row : sheet) {
                if (isHeaderRow) {
                    for (Cell cell : row) {
                        headerMap.put(cell.getStringCellValue(), cell.getColumnIndex());
                    }
                    isHeaderRow = false;
                    continue;
                }

                User user = new User();
                for (String key : headerMap.keySet()) {
                    int columnIndex = headerMap.get(key);
                    Cell cell = row.getCell(columnIndex);

                    if (cell == null) {
                        continue;
                    }

                    switch (key) {
                        case "Name":
                            user.setName(cell.getStringCellValue());
                            break;
                        case "Mobile Number":
                            cell.setCellType(CellType.STRING); // Ensure mobile number is read as a string
                            user.setMobileNumber(cell.getStringCellValue());
                            break;
                        case "Requirement":
                            cell.setCellType(CellType.STRING); // Ensure requirement is read as a string
                            user.setRequirement(cell.getStringCellValue());
                            break;
                        case "Email":
                            user.setEmail(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                if(checkAllEmty(user)){
                    break;
                }
               Double lastRow= Double.parseDouble(user.getRequirement());
               excelSliceService.slice(actualData, startRow, startRow+lastRow.intValue(),user);
               startRow=startRow+lastRow.intValue(); 
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        
      
       
       
    }

    private boolean checkAllEmty(User user){
        if(user.getEmail()==null&&user.getMobileNumber()==null){
            return true;
        }

        return false;
    }
    
}
