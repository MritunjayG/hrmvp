package sg.mvp.employee.api.facade.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import sg.mvp.employee.api.model.Employee;
import sg.mvp.employee.api.util.AppUtility;

public class CSVHelper {

	private static final Logger logger = LoggerFactory.getLogger(CSVHelper.class);

	public static String TYPE = "text/csv";
	static String[] HEADERs = { "id", "login", "name", "salary", "startDate" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Employee> csvToEmployees(InputStream is) {

		logger.info("Enter into csvToEmployees ..");
		AppUtility appUtil = new AppUtility();

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Employee> employees = new ArrayList<Employee>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Employee employee = new Employee(csvRecord.get("id"), csvRecord.get("login"), csvRecord.get("name"),
						Double.valueOf(csvRecord.get("salary")), appUtil.parseDate(csvRecord.get("startDate")));

				employees.add(employee);
			}

			return employees;
		} catch (IOException e) {
			throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
		}
	}

}