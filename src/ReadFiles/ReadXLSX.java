package ReadFiles;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static GUI.Window.errors;

/**
 * To read XLS or XLSX and store the data
 */
public class ReadXLSX {
    private static ArrayList<String[]> studentDetails; //store student details
    private static Map<String, Integer> positions = new HashMap<>(); //Stores position of headings

    // Reads the student XLSX and saves position of heading and stores the rows to map and gets data from XLSX in order
    // to input into arraylist
    public static List<String[]> xlsxReader(String filename, boolean titleError) {
        try {
            //Reads the XLSX
            studentDetails = new ArrayList<>();
            FileInputStream input = new FileInputStream(new File(filename));
            Workbook book = fileType(filename, input);
            if (book != null) {
                Sheet sheet = book.getSheetAt(0);

                //Iterate over the headings only to find position
                Iterator<Row> iterator = sheet.iterator();
                Row eachRow = iterator.next();
                Iterator<Cell> cellIterator = eachRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    positions.put(cell.getStringCellValue().toUpperCase(), cell.getColumnIndex());
                }

                //Retrieve the position of headings
                int firstNameID = positions.get("FIRST NAME");
                int lastNameID = positions.get("LAST NAME");
                int regID = positions.get("REGISTRATION NUMBER");
                int supervisorID = positions.get("SUPERVISOR");
                int assID = positions.get("SECOND ASSESSOR");
                int projID = positions.get("TITLE");

                DataFormatter format = new DataFormatter();
                // Inputs the cell values in order to store the student details
                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    String line = "";
                    for (Cell cell : row) {
                        line += format.formatCellValue(cell) + ", ";
                    }

                    if (titleError){
                        if (String.valueOf(row.getCell(projID)).equals("null")){
                            errors.add("Warning: Data missing at row " + row.getRowNum() + ", with content " + line + ".");
                        }
                        try{
                            if (ReadCSV.isItNumber(String.valueOf(row.getCell(projID)))){
                                throw new Exception("Error: Not a registration number at line " + row.getRowNum() + ", with content " + line + ".");
                            }
                        }catch (Exception e){
                            errors.add(e.getMessage());
                        }
                    }

                    if (String.valueOf(row.getCell(firstNameID)).equals("null") || String.valueOf(row.getCell(lastNameID)).equals("null")) {
                        errors.add("Warning: Data missing at row " + row.getRowNum() + ", with content " + line + ".");
                    }

                    try {
                        if (String.valueOf(row.getCell(regID)).equals("null") || String.valueOf(row.getCell(supervisorID)).equals("null") ||
                                String.valueOf(row.getCell(assID)).equals("null")) {
                            throw new Exception("Error: Data missing at row " + row.getRowNum() + ", with content " + line + ".");
                        } else if (ReadCSV.isItNumber(String.valueOf(row.getCell(firstNameID))) || ReadCSV.isItNumber(String.valueOf(row.getCell(lastNameID))) ||
                                ReadCSV.isItNumber(String.valueOf(row.getCell(supervisorID))) || ReadCSV.isItNumber(String.valueOf(row.getCell(assID)))) {
                            throw new Exception("Error: Not a string value at row " + row.getRowNum() + ", with content " + line + ".");
                        } else if (!ReadCSV.isItNumber(String.valueOf(format.formatCellValue(row.getCell(regID))))) {
                            throw new Exception("Error: Not a registration number at row " + row.getRowNum() + ", with content " + line + ".");
                        } else {
                            String[] temp = {
                                    row.getCell(firstNameID) != null ? String.valueOf(row.getCell(firstNameID)) : "",
                                    row.getCell(lastNameID) != null ? String.valueOf(row.getCell(lastNameID)) : "",
                                    format.formatCellValue(row.getCell(regID)),
                                    String.valueOf(row.getCell(supervisorID)),
                                    String.valueOf(row.getCell(assID)),
                                    row.getCell(projID) != null ? String.valueOf(row.getCell(projID)) : ""
                            };
                            studentDetails.add(temp);
                        }
                    } catch (Exception e) {
                        errors.add(e.getMessage());
                    }


                }

                // Closes the book and input
                book.close();
                input.close();

            } else {
                return studentDetails;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentDetails;
    }

    private static Workbook fileType(String filename, FileInputStream input) {
        try {

            String[] splitByDots = filename.split("\\\\");
            String lastElement = splitByDots[splitByDots.length - 1];
            String[] splitLastElement = lastElement.split("\\.");
            String extension = splitLastElement[splitLastElement.length - 1];

            if (extension.equals("xlsx")) {
                return new XSSFWorkbook(input);
            }

            if (extension.equals("xls")) {
                return new HSSFWorkbook(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

