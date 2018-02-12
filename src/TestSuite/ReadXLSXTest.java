package TestSuite;

import ReadFiles.ReadXLSX;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static GUI.Window.errors;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/*
 * Tests for ReadXLSX class
 */

public class ReadXLSXTest {
    @Test
    public void TestXLSXReader() throws Exception {
        List<String[]> actual = ReadXLSX.xlsxReader("src\\TestSuite\\SampleFiles\\student_list.xlsx", true);
        String[] expected = {"Smith", "John", "3472398", "Adrian Clark", "Riccardo Poli", "A novel method for investigating Android apps"};
        assertArrayEquals(expected, actual.get(0));
    }


    @Test
    public void TestXLSXReaderReordered() throws Exception {
        List<String[]> actual = ReadXLSX.xlsxReader("src\\TestSuite\\SampleFiles\\student_list_reordered.xlsx", true);
        String[] expected = {"Smith", "John", "3472398", "Adrian Clark", "Riccardo Poli", "A novel method for investigating Android apps"};
        System.out.println(Arrays.toString(actual.get(0)));
        System.out.println(Arrays.toString(actual.get(actual.size() - 1)));
        assertArrayEquals(expected, actual.get(0));
    }

    @Test
    public void TestXLSXReaderUsingXLSFileFormat() throws Exception {
        List<String[]> actual = ReadXLSX.xlsxReader("src\\TestSuite\\SampleFiles\\student_list_reordered.xls", true);
        String[] expected = {"Smith", "John", "3472398", "Adrian Clark", "Riccardo Poli", "A novel method for investigating Android apps"};
        System.out.println(Arrays.toString(actual.get(0)));
        System.out.println(Arrays.toString(actual.get(actual.size() - 1)));
        assertArrayEquals(expected, actual.get(0));
    }

    @Test
    public void TestWrongFileFormat() throws Exception {
        List<String[]> actual = ReadXLSX.xlsxReader("src\\TestSuite\\SampleFiles\\sampleCSV.csv", true);
        List<String[]> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    public void MissingData() throws Exception {
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"Smith", "John", "3472398", "Adrian Clark", "Riccardo Poli", "A novel method for investigating Android apps"});
        expected.add(new String[]{"", "Rachel", "9894893", "Luca Citi", "Sam Steel", "TBA"});
        List<String[]> actualMissingData = ReadXLSX.xlsxReader("src\\TestSuite\\SampleFiles\\student_list_missing.xlsx", true);


        // print errors and warnings
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        }

        for (int i = 0; i < expected.size(); i++) {
            System.out.println(Arrays.toString(actualMissingData.get(i)));
            assertArrayEquals(expected.get(i), actualMissingData.get(i));
        }

    }
}