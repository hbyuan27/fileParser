package com.snippets.demo.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.snippets.demo.bean.EmployeeImportHeader;
import com.snippets.demo.bean.ImportResult;

@Service
public class CSVParserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<ImportResult> importEmployeeData(String fileName) {
		List<ImportResult> importResults = new ArrayList<ImportResult>();
		// parse csv file
		CSVParser parser = null;
		List<CSVRecord> records = null;
		FileReader reader = null;
		try {
			reader = new FileReader(fileName);
			CSVFormat format = CSVFormat.DEFAULT.withHeader(EmployeeImportHeader.class);
			format.withIgnoreHeaderCase(true).withSkipHeaderRecord(true);
			format.withIgnoreSurroundingSpaces(true).withIgnoreEmptyLines(true);
			parser = new CSVParser(reader, format);
			records = parser.getRecords();
		} catch (IOException e) {
			logger.error("parse csv file failed due to: " + e.getMessage());
			// TODO Auto-generated catch block
		} finally {
			IOUtils.closeQuietly(parser);
			IOUtils.closeQuietly(reader);
		}
		if (records != null) {
			for (int i = 0; i < records.size(); i++) {
				// skip the header
				if (i < 1) {
					continue;
				}
				// use bean adaptor to transfer data into core beans
				ImportResult result = buildImportResult(records.get(i));
				importResults.add(result);
			}
		}

		return importResults;
	}

	private ImportResult buildImportResult(CSVRecord record) {
		// TODO - use reflection or other method to build import result
		// automatically based on the EmployeeImportHeader enum
		for (EmployeeImportHeader header : EmployeeImportHeader.values()) {
			ImportResult result = new ImportResult();
			result.setStatus(record.get(header.name()));
		}
		return null;
	}
}
