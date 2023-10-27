package com.syllabus.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.exception.CsvException;

public class CsvHelper {

	public static final String TYPE = "text/csv";

	private CsvHelper() {
	}

	public static boolean hasCSVFormat(MultipartFile file) {

		return TYPE.equals(file.getContentType());
	}

	public static Iterable<CSVRecord> readFile(InputStream inputStream) {

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true)
					.setIgnoreHeaderCase(true).setTrim(true).build();

			return csvFormat.parse(fileReader);

		} catch (IOException ex) {
			throw new CsvException("fail to parse CSV file: " + ex.getMessage());
		}

	}

}
