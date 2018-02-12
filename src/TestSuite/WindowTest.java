package TestSuite;

import GUI.Window;
import ReadFiles.ReadCSV;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static GUI.Window.errors;
import static org.junit.Assert.assertEquals;

/**
 * Tests for Window class
 */
public class WindowTest
{
    // check if errors are correctly written to the text file
    @Test
    public void testWriteErrorsToFile() throws IOException {
        ReadCSV.readStudents("src\\TestSuite\\SampleFiles\\student_list_missing.csv", true);

        // write the errors to the text file
        if (!errors.isEmpty()){
            Window.writeErrors("src\\TestSuite\\SampleFiles\\");
        }

        List<String> expected = errors;
        List<String> actual = new ArrayList<>();

        // read the text file
        Scanner scanner = new Scanner(new File("src\\TestSuite\\SampleFiles\\errors.txt"));
        while (scanner.hasNext()){
            actual.add(scanner.nextLine());
        }
        scanner.close();

        // compare the two lists
        for (int i=0;i<expected.size();i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
    }

}
