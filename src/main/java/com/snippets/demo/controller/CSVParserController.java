package com.snippets.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snippets.demo.bean.ImportResult;
import com.snippets.demo.service.CSVParserService;

@RestController
//@RequestMapping(value = "upload")
public class CSVParserController {

	@Autowired
	CSVParserService parserService;

	@RequestMapping(value = "/upload", produces = "multipart/form-data", method = RequestMethod.POST)
	public List<ImportResult> importEmployeeData(HttpServletRequest request, @RequestParam(value = "fileName") String fileName) {
		List<ImportResult> importResults = parserService.importEmployeeData(fileName);

		// 
		return importResults;
	}
}
