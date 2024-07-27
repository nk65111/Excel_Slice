package com.parser.excel.service;

import org.springframework.web.multipart.MultipartFile;

import com.parser.excel.model.User;

public interface ExcelSliceService {
    
    public void slice(MultipartFile file,Integer to,Integer from,User user);
}
