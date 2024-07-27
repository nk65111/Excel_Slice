package com.parser.excel.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.parser.excel.service.ExcelSendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ParserController {

    @Autowired
    private ExcelSendService excelSendService;

    @PostMapping("/api/upload")
    public String excelHandler(@RequestParam("user_data")  MultipartFile userData, @RequestParam("actual_data") MultipartFile actulaData) {
        //TODO: process POST request
        excelSendService.sendExcel(userData, actulaData);
        return "";
    }
    
    
}
