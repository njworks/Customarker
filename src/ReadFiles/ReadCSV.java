package ReadFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static GUI.Window.errors;

/**
 * Java class to read CSV files
 */

public class ReadCSV {

    // Returns a buffered reader for reading the lines in the file

    public static BufferedReader readFile(String file) {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return null;
    }

    // Splits the headings line into the array
    public static String[] readHeadings(String line) {
        // remove BOM(Byte Order Mark) if present as the first character
        if (line.charAt(0) == '\uFEFF') {
            line = line.substring(1);
        }
        return line.split(",");
    }


    // Sorts the headings into the agreed order

    public static String[] sortHeadings(String[] headings) {
        String[] temp = new String[headings.length];
        for (String heading : headings) {
            switch (heading.toUpperCase()) {
                case "FIRST NAME":
                    temp[0] = heading;
                    break;
                case "LAST NAME":
                    temp[1] = heading;
                    break;
                case "REGISTRATION NUMBER":
                    temp[2] = heading;
                    break;
                case "SUPERVISOR":
                    temp[3] = heading;
                    break;
                case "SECOND ASSESSOR":
                    temp[4] = heading;
                    break;
                case "TITLE":
                    temp[5] = heading;
                    break;
            }
        }
        return temp;
    }

    // reads all students and adds them to an ArrayList<String[]> for processing

    public static List<String[]> readStudents(String file, boolean titleError) throws IOException {
        BufferedReader reader = readFile(file);
        if (reader == null) {
            return null;
        }
        String line = reader.readLine();

        String[] fileHeadings = readHeadings(line);

        List<String[]> students = new ArrayList<>();
        int currLine = 2; // include heading line and start counting from 1 instead of 0
        while ((line = reader.readLine()) != null) {
            try {
                students.add(readStudent(line, currLine, fileHeadings, titleError));
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
            currLine++;
        }
        return students;
    }

    // reads and orders the student data into the string[] based on the headings
    public static String[] readStudent(String line, int currLine, String[] headings, boolean titleError) throws Exception {
        String[] student = line.split(",");
        String[] temp = new String[student.length];
        for (int i = 0; i < student.length; i++) {
            switch (headings[i].toUpperCase()) {
                case "FIRST NAME":
                    if (student[i].equals("")) {
                        errors.add("Warning: Data missing at line " + currLine + ", with content " + line + ".");
                    } else if (isItNumber(student[i])) {
                        throw new Exception("Error: Not a string value at line " + currLine + ", with content " + line + ".");
                    }
                    temp[0] = student[i];
                    break;

                case "LAST NAME":
                    if (student[i].equals("")) {
                        errors.add("Warning: Data missing at line " + currLine + ", with content " + line + ".");
                    } else if (isItNumber(student[i])) {
                        throw new Exception("Error: Not a string value at line " + currLine + ", with content " + line + ".");
                    }
                    temp[1] = student[i];
                    break;

                case "REGISTRATION NUMBER":
                    if (student[i].equals("")) {
                        throw new Exception("Error: Registration Number missing at line " + currLine + ", with content " + line + ".");
                    } else if (!isItNumber(student[i])) {
                        throw new Exception("Error: Not a registration number value at line " + currLine + ", with content " + line + ".");
                    }
                    temp[2] = student[i];
                    break;

                case "SUPERVISOR":
                    if (student[i].equals("")) {
                        throw new Exception("Error: Supervisor missing at line " + currLine + ", with content " + line + ".");
                    } else if (isItNumber(student[i])) {
                        throw new Exception("Error: Not a string value at line " + currLine + ", with content " + line + ".");
                    }
                    temp[3] = student[i];
                    break;

                case "SECOND ASSESSOR":

                    if (student[i].equals("")) {
                        throw new Exception("Error: Second Assessor missing at line " + currLine + ", with content " + line + ".");
                    } else if (isItNumber(student[i])) {
                        throw new Exception("Error: Not a string value at line " + currLine + ", with content " + line + ".");
                    }
                    temp[4] = student[i];
                    break;

                case "TITLE":
                    if (titleError) {
                        if (student[i].equals("")) {
                            errors.add("Warning: Data missing at line " + currLine + ", with content " + line + ".");
                        } else if (isItNumber(student[i])) {
                            throw new Exception("Error: Not a string value at line " + currLine + ", with content " + line + ".");
                        }
                    }
                    temp[5] = student[i];
                    break;
            }
        }
        return temp;
    }

    public static boolean isItNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
