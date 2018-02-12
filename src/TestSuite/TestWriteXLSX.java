package TestSuite;

import FileOut.WriteXLSX;
import ReadFiles.ReadTemplate;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Tests for WriteXLSX
 */

public class TestWriteXLSX {

    // private WriteXLSX testWriter;
    private String[] studentData;
    private Workbook original;

    @Before
    public void setup() throws FileNotFoundException {
        original = ReadTemplate.readTemplateXLSX("../Customarker/src/TestSuite/SampleFiles/template_second_assessor.xlsx");
        studentData = new String[]{"Owen", "Daynes", "1404314", "John Woods", "Can't remember name", "Text-Similarity Detection Engine"};
    }

    @Test
    public void testStaticWriteToFile() {
        WriteXLSX.WriteDataIntoWorkbook(original, "../Customarker/src/TestSuite/SampleFiles/" + studentData[1] + "_" + studentData[0] + ".xlsx", studentData, false);
        Assert.assertTrue(new File("../Customarker/src/TestSuite/SampleFiles/" + studentData[1] + "_" + studentData[0] + ".xlsx").exists());
    }

    @Test
    public void testDirectoryCreation() {
        WriteXLSX.directoryCreation("../Customarker/src/TestSuite/SampleFiles/Folder1/folder2/words.txt");
        Assert.assertTrue(new File("../Customarker/src/TestSuite/SampleFiles/Folder1/folder2/").exists());
    }

}
