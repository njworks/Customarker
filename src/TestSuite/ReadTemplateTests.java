package TestSuite;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;

import static ReadFiles.ReadTemplate.createFile;
import static ReadFiles.ReadTemplate.readTemplateXLSX;

/*
 * Tests for ReadTemplate class
 */

public class ReadTemplateTests {

    /*
    TEST WHETHER THE BOOK CONTAINS THE TEMPLATE INFORMATION AS STUDENT NAME SHOULD BE IN ROW 2
     */
    @Test
    public void ReadTemplateXLSX() throws Exception {
        int expected = 2;
        int actual = 0;
        Workbook book = readTemplateXLSX("src\\TestSuite\\SampleFiles\\template_supervisor.xlsx");
        Sheet sheet = book.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        if (cell.getRichStringCellValue().getString().trim().equals("Students Name:")) {
                            actual = nextRow.getRowNum();
                        }
                }
            }
        }

        Assert.assertEquals(expected, actual);
    }

    /*
    CHECKS IF THE FILE IS CREATED
     */
    @Test
    public void CreateFile() throws Exception {
        createFile("src\\TestSuite\\SampleFiles\\supervisor.xlsx",
                readTemplateXLSX("src\\TestSuite\\SampleFiles\\template_supervisor.xlsx"));
        File file = new File("src\\TestSuite\\SampleFiles\\supervisor.xlsx");
        Assert.assertTrue(file.exists());
    }

}
