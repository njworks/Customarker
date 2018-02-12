package TestSuite;

import FileOut.Report;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for Report class
 */
public class ReportTest {
    private static List<String[]> studentsdata;

    @Before
    public void setup() {
        studentsdata = new ArrayList<>();
        String[] temp = {"Maribel", "Hance", "9067293", "Nick", "Zakhleniuk",
                "Luca", "Citi", "0", "0", "0", "0", "0", "0", "0"};
        String[] temp1 = {"Albina", "Folson", "2879045", "Nick", "Zakhleniuk",
                "Luca", " Citi", "0", "0", "0", "0", "0", "0", "0"};
        String[] temp2 = {"Mellisa", "Chenard", "9067293", "Nick", "Zakhleniuk",
                "Luca", " Citi", "0", "0", "0", "0", "0", "0", "0"};

        studentsdata.add(temp);
        studentsdata.add(temp1);
        studentsdata.add(temp2);
    }

    @Test
    public void writeData() throws Exception {
        Report.WriteData(studentsdata, "src\\TestSuite\\ReportFinal.xlsx");
    }

}