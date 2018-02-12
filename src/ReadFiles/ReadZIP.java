package ReadFiles;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
 * Reads ZIP file, unpacks the ZIP and stores the data
 */
public class ReadZIP {

    private ArrayList<Workbook> workbooks;
    private String zipName;
    private String tempDirName;

    // Constructor
    public ReadZIP(String filename, String tDirName) {
        this.workbooks = new ArrayList<Workbook>();
        this.zipName = filename;
        this.tempDirName = tDirName;

        try {
            this.unzip();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unzip() throws IOException {
        byte[] buffer = new byte[1024];

        try {
            // make the temp folder
            File folder = new File(tempDirName);
            if (!folder.exists()) {
                folder.mkdir();
            }

            ZipInputStream zipInput = new ZipInputStream(new FileInputStream(this.zipName));
            ZipEntry zipEntry = zipInput.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(tempDirName + File.separator + fileName);

                if (zipEntry.isDirectory()) {
                    if (!newFile.exists()) {
                        newFile.mkdir();
                    }
                } else {
                    FileOutputStream fOutStream = new FileOutputStream(newFile);

                    int length;
                    while ((length = zipInput.read(buffer)) > 0) {
                        fOutStream.write(buffer, 0, length);
                    }
                    fOutStream.close();
                    this.workbooks.add(new XSSFWorkbook(newFile));
                }
                zipEntry = zipInput.getNextEntry();
            }

            zipInput.closeEntry();
            zipInput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    // Public methods
    public ArrayList<Workbook> getWorkbooks() {
        return this.workbooks;
    }

    public Workbook getWorkbook(int index) {
        return this.workbooks.get(index);
    }

    public List<String[]> readWorkbooks() {
        List<String[]> students = new ArrayList<>();
        for (Workbook workbook : workbooks) {
            DataFormatter format = new DataFormatter();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheetAt(0);

            Cell name = sheet.getRow(2).getCell(2);
            Cell regNo = sheet.getRow(3).getCell(2);
            Cell supervisor = sheet.getRow(4).getCell(2);
            Cell secondAccessor = sheet.getRow(5).getCell(2);

            Cell initialReportMark = sheet.getRow(13).getCell(9);
            Cell interimReportMark = sheet.getRow(19).getCell(9);
            Cell posterMark = sheet.getRow(25).getCell(9);
            Cell finalReportMark = sheet.getRow(31).getCell(9);
            Cell logbookMark = sheet.getRow(35).getCell(9);
            Cell pdoMark = sheet.getRow(42).getCell(9);
            Cell moduleTotal = sheet.getRow(44).getCell(10);

            String[] stud = new String[14];

            // evaluate formulas in cells
            CellValue initialReportMarkCV = evaluator.evaluate(initialReportMark);
            CellValue interimReportMarkCV = evaluator.evaluate(interimReportMark);
            CellValue posterMarkCV = evaluator.evaluate(posterMark);
            CellValue finalReportMarkCV = evaluator.evaluate(finalReportMark);
            CellValue logbookMarkCV = evaluator.evaluate(logbookMark);
            CellValue pdoMarkCV = evaluator.evaluate(pdoMark);
            CellValue moduleTotalCV = evaluator.evaluate(moduleTotal);

            // store student data and formula results in array
            stud[0] = name.getStringCellValue().split(" ")[0];
            stud[1] = name.getStringCellValue().split(" ")[1];
            stud[2] = format.formatCellValue(regNo);
            stud[3] = supervisor.getStringCellValue().split(" ")[0];
            stud[4] = supervisor.getStringCellValue().split(" ")[1];
            stud[5] = secondAccessor.getStringCellValue().split(" ")[0];
            stud[6] = secondAccessor.getStringCellValue().split(" ")[1];
            stud[7] = Double.toString(initialReportMarkCV.getNumberValue());
            stud[8] = Double.toString(interimReportMarkCV.getNumberValue());
            stud[9] = Double.toString(posterMarkCV.getNumberValue());
            stud[10] = Double.toString(finalReportMarkCV.getNumberValue());
            stud[11] = Double.toString(logbookMarkCV.getNumberValue());
            stud[12] = Double.toString(pdoMarkCV.getNumberValue());
            stud[13] = Double.toString(moduleTotalCV.getNumberValue());

            // add student array to list
            students.add(stud);
        }
        return students;
    }

}
