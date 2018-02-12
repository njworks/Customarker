package FileOut;

import ReadFiles.ReadTemplate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by odayne on 08/12/2017.
 * Gets filled forms data by supervisor and second assessor and
 * and create a final report with all data
 */
public class Report {

    //Takes list with student data. Afterwards creates a workbook and add headings and data to
    //workbook. Export the workbook as XLSX file.
    public static void WriteData(List<String[]> Studentdata, String filename) {
        Workbook book = new XSSFWorkbook();
        XSSFSheet sheet = (XSSFSheet) book.createSheet("Data Report");
        XSSFRow row;

        String[] headings = {"Student First Name", "Student Surname", " Student Registration No.",
                "Supervisor First Name", "Supervisor Surname", "Second Assessor First Name",
                "Second Assessor Surname", "Initial Report Mark", "Interim Report Mark",
                "Poster Mark", "Final Report Mark", "Logbook Mark", "PDO Mark",
                "Module Total"};

        row = sheet.createRow(0);
        for (int k = 0; k < headings.length; k++) {
            Cell cell = row.createCell(k);
            cell.setCellValue(headings[k]);
        }

        int counter = 1;
        for (String[] data : Studentdata) {
            row = sheet.createRow(counter);
            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(data[i]);
            }
            counter++;
        }
        ReadTemplate.createFile(filename, book);
    }
}
