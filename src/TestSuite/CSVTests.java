
package TestSuite;

import ReadFiles.ReadCSV;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static GUI.Window.errors;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

/*
 * Tests for ReadCSV class
 */

public class CSVTests {
    private BufferedReader reader = null;
    private String file = "src\\TestSuite\\SampleFiles\\student_list.csv";
    @Before
    public void setUp() throws Exception {

        reader = ReadCSV.readFile(file);
    }

    @Test
    public void testFileExists() {
        assertNotNull(reader);
    }

    @Test
    public void testFileNotEmpty() throws IOException {
        String line = reader.readLine();

        assertNotNull(line);
    }

    @Test
    public void testReadHeadings() throws IOException {
        String line = reader.readLine();

        String[] originalHeadings = ReadCSV.readHeadings(line);

        String[] desiredHeadings = ReadCSV.sortHeadings(originalHeadings);

        assertArrayEquals(new String[]{"First name", "Last name", "Registration number", "Supervisor", "Second assessor", "Title"}, desiredHeadings);
    }

    @Test
    public void testReadStudent() throws IOException {
        String line = reader.readLine();
        String[] fileHeadings = ReadCSV.readHeadings(line);

        line = reader.readLine();
        int currLine = 2;
        try {
            String[] student = ReadCSV.readStudent(line ,currLine, fileHeadings, true);
            assertArrayEquals(new String[]{"Smith","John","3472398","Adrian Clark","Riccardo Poli","A novel method for investigating Android apps"}, student);
        }catch (Exception e){

        }

    }

    @Test
    public void testReadStudents() throws IOException{
        List<String[]> students = ReadCSV.readStudents(file, true);
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"Smith","John","3472398","Adrian Clark","Riccardo Poli","A novel method for investigating Android apps"});
        expected.add(new String[]{"Butcher","Rachel","9894893","Luca Citi","Sam Steel","TBA"});
        expected.add(new String[]{"Green","Jessica","3948398","Anthony Vickers","Luca Citi","Predicting forecasts using logistic regression"});
        expected.add(new String[]{"Red","Jason","6528883","Diego Perez","Adrian Clark","An AI for board games"});

        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i),students.get(i));
        }
    }

    @Test
    public void testMissingData() throws IOException {
        List<String[]> students = ReadCSV.readStudents("src\\TestSuite\\SampleFiles\\student_list_missing.csv", true);

        // print errors and warnings
        if (!errors.isEmpty()){
            errors.forEach(System.out::println);
        }

        // assert that the lines with errors were not added to the list
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"","John","3472398","Adrian Clark","Riccardo Poli","A novel method for investigating Android apps"});

        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i),students.get(i));
        }
    }

}
