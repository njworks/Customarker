package FileOut;

/**
 * Created by odayne on 14/11/2017.
 */

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

public class WriteXLSX {

    private static DataFormatter formatter = new DataFormatter();

    public static void WriteDataIntoWorkbook(Workbook copiedTemplate, String destinationPath, String[] studentData, boolean projectTitleWanted) {
        for (Row row : copiedTemplate.getSheetAt(0)) {
            Iterator<Cell> cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cellFormat(cell, cellIterator, "FIRST NAME", studentData[0]);
                cellFormat(cell, cellIterator, "LAST NAME", studentData[1]);
                cellFormat(cell, cellIterator, "STUDENTS NAME:", studentData[1] + " " + studentData[0]);
                cellFormat(cell, cellIterator, "REG NO:", studentData[2]);
                cellFormat(cell, cellIterator, "SUPERVISOR:", studentData[3]);
                cellFormat(cell, cellIterator, "SECOND ASSESSOR:", studentData[4]);
                if( projectTitleWanted ) cellFormat(cell, cellIterator, "PROJECT TITLE:", studentData[5]);
            }
        }

        directoryCreation(destinationPath);

        try {
            FileOutputStream os = new FileOutputStream(destinationPath);
            copiedTemplate.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cellFormat(Cell cell, Iterator<Cell> cellIterator, String heading, String studentData){
        if(formatter.formatCellValue(cell).toUpperCase().equals(heading)){
            cell = cellIterator.next();
            cell.setCellType(CellType.STRING);
            cell.setCellValue(studentData);
        }
    }
    public static boolean directoryCreation(String path) {
        String[] splitPath = path.split("/");
        String newPath = "";
        for(int i = 0; i < splitPath.length - 1; i++) {
            newPath += splitPath[i] + "/";
        }
        return new File(newPath).mkdirs();
    }

}
