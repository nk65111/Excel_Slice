package com.parser.excel.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelSendService {
    
   void sendExcel(MultipartFile userData,MultipartFile actualData);
}
