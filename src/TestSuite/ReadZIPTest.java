package TestSuite;

import ReadFiles.ReadZIP;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/*
 * Tests for ReadZIP class
 */

public class ReadZIPTest {

    ReadZIP zipReader;

    @Before
    public void setUp() throws Exception {
        zipReader = new ReadZIP("src\\TestSuite\\SampleFiles\\sample_zip.zip",
                "src\\TestSuite\\SampleFiles\\temp");
    }

    @Test
    public void getFiles() throws IOException {
        // testing if there are no files
        assertNotNull(zipReader.getWorkbooks());

        // checking if it returns a list of files
        ArrayList<Workbook> testFiles = new ArrayList<>();

        /*adding our files to testFiles, to verify that the contents of our zipReader are the same as the contents added
         to the testFiles ArrayList<File>*/
        testFiles.add(new XSSFWorkbook("src/TestSuite/SampleFiles/StudentsWithData/Jason_Red.xlsx"));
        testFiles.add(new XSSFWorkbook("src/TestSuite/SampleFiles/StudentsWithData/Jessica_Green.xlsx"));
        testFiles.add(new XSSFWorkbook("src/TestSuite/SampleFiles/StudentsWithData/John_Smith.xlsx"));
        testFiles.add(new XSSFWorkbook("src/TestSuite/SampleFiles/StudentsWithData/Rachel_Butcher.xlsx"));

        for (int a=0; a < 4; a++) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    assertEquals(testFiles.get(a).getSheetAt(0).getRow(i).getCell(j).getStringCellValue(),
                            zipReader.getWorkbook(a).getSheetAt(0).getRow(i).getCell(j).getStringCellValue());
                }
            }
        }
    }

    @Test
    public void readStudentMarks(){
        List<String[]> students = zipReader.readWorkbooks();
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"Jason,","Red", "6528883","Diego","Perez","Adrian", "Clark","50.0","50.0","50.0","50.0","50.0","50.0","50.0"});

        assertArrayEquals(expected.get(0),students.get(0));
    }
}