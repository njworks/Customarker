package ReadFiles;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
 * Read template files and make copy of it for creating and editing template document
 * REFERENCE: https://poi.apache.org/spreadsheet/quick-guide.html#NewWorkbook
 */
public class ReadTemplate {

    // Reads the template XLSX and returns workbook to work on
    public static Workbook readTemplateXLSX(String fileName) {
        Workbook book = null;
        try {
            //Gets the workbook using stream and close the stream
            FileInputStream input = new FileInputStream(new File(fileName));
            book = new XSSFWorkbook(input);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Returns the Workbook of template to work on
        return book;
    }

    // Create the XLSX file after template is edited
    public static void createFile(String fileName, Workbook book) {
        try {
            // Create the file
            File file = new File(fileName);
            FileOutputStream fileOut = new FileOutputStream(file);

            // Write the workbook to file and close book
            book.write(fileOut);
            fileOut.close();
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
