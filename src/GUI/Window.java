package GUI;

import FileOut.Report;
import FileOut.WriteXLSX;
import ReadFiles.ReadCSV;
import ReadFiles.ReadTemplate;
import ReadFiles.ReadXLSX;
import ReadFiles.ReadZIP;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by njosepa on 01/12/2017.
 * Ref:
 * JavaFX: https://docs.oracle.com/javafx/2/get_started/hello_world.htm
 * File Explorer: https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 * chose file icon https://docs.oracle.com/javafx/2/ui_controls/button.htm
 */

public class Window extends Application {

    public static List<String> errors = new ArrayList<>();

    private Scene mainScene;
    private Scene zipScene;

    private String supervisorPath = "";
    private String assessorPath = "";
    private String studentsListPath = "";
    private String destinationPath = "";
    private String zipPath = "";

    private CheckBox includeReportTitle;

    private static FileChooser chooseFile;
    private static FileChooser chooseFileXLSX;
    private static FileChooser chooseZip;
    private static DirectoryChooser chooser;

    private Label title;
    private Label supervisorTemplateLabel;
    private Label assessorTemplateLabel;
    private Label studentsTempLabel;
    private Label superVisorLabel;
    private Label assessorLabel;
    private Label studentLabel;
    private Label locationLabel;
    private Label chosenLocationLabel;
    private Label marksFromZipLabel;

    private Button fileLocation;
    private Button generate;
    private Button zipButton;
    private Button supervisorFile;
    private Button assessorFile;
    private Button studentFile;

    private GridPane gridPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primary) {

        primary.setTitle("Customaker");
        primary.setResizable(false);
        primary.getIcons().add(new Image("essexLogoNB.png"));


        FileChooser.ExtensionFilter threeFilter = new FileChooser.ExtensionFilter("Files (*.xlsx), (*.xls), (*.csv)", "*.xlsx", "*.xls", "*.csv");
        chooseFile = new FileChooser();
        chooseFile.getExtensionFilters().add(threeFilter);

        FileChooser.ExtensionFilter xlsxFilter = new FileChooser.ExtensionFilter("XLSX Files (*.xlsx)", "*.xlsx");
        chooseFileXLSX = new FileChooser();
        chooseFileXLSX.getExtensionFilters().add(xlsxFilter);
        chooser = new DirectoryChooser();

        title = new Label("CSEE Customarker");
        title.setStyle("-fx-font-size: 20pt");

        supervisorTemplateLabel = new Label("Template Supervisor:");
        supervisorTemplateLabel.setStyle("-fx-font-size: 15pt");

        assessorTemplateLabel = new Label("Template Second Assessor:");
        assessorTemplateLabel.setStyle("-fx-font-size: 15pt");

        studentsTempLabel = new Label("Students List:");
        studentsTempLabel.setStyle("-fx-font-size: 15pt");

        supervisorFile = new Button("Choose File");
        supervisorFile.setStyle("-fx-font-size: 15pt");
        assessorFile = new Button("Choose File");
        assessorFile.setStyle("-fx-font-size: 15pt");
        studentFile = new Button("Choose File");
        studentFile.setStyle("-fx-font-size: 15pt");

        superVisorLabel = new Label();
        assessorLabel = new Label();
        studentLabel = new Label();

        locationLabel = new Label("Choose Save Location:");
        locationLabel.setStyle("-fx-font-size: 15pt");

        fileLocation = new Button("File Location");
        fileLocation.setStyle("-fx-font-size: 15pt");
        fileLocation.setDisable(true);

        chosenLocationLabel = new Label();

        generate = new Button("Generate");
        generate.setStyle("-fx-font-size: 15pt; -fx-base: #ff5950 ");
        generate.setDisable(true);

        marksFromZipLabel = new Label("Marks from a zip file");
        zipButton = new Button("Click Here");

        includeReportTitle = new CheckBox("Include Report Title");

        supervisorFile.setOnAction(
                e -> {
                    supervisorPath = getFilePathXLSX(primary);
                    populateFilePathLabel(supervisorPath, superVisorLabel);
                });

        assessorFile.setOnAction(
                e -> {
                    assessorPath = getFilePathXLSX(primary);
                    populateFilePathLabel(assessorPath, assessorLabel);
                });

        studentFile.setOnAction(
                e -> {
                    studentsListPath = getFilePathStudents(primary);
                    populateFilePathLabel(studentsListPath, studentLabel);
                });


        fileLocation.setOnAction(
                e -> {
                    File file = chooser.showDialog(primary);
                    if (file != null) {
                        destinationPath = file.toString();
                        chosenLocationLabel.setText(destinationPath);
                        generate.setDisable(false);
                    } else {
                        chosenLocationLabel.setText(null);
                    }
                });

        generate.setOnAction(
                e -> {
                    generate(supervisorPath, assessorPath, studentsListPath, destinationPath);
                });
        zipButton.setOnAction(
                event -> {
                    GridPane gridPane = new GridPane();
                    gridPane.setPrefSize(900, 600);
                    gridPane.setHgap(20);
                    gridPane.setVgap(20);
                    gridPane.setPadding(new Insets(10));
                    gridPane.setStyle("-fx-background-color: white");
                    gridPane.getColumnConstraints().add(new ColumnConstraints(300));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(300));
                    gridPane.getColumnConstraints().add(new ColumnConstraints(300));

                    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Zip File", "*.zip");
                    chooseZip = new FileChooser();
                    chooseZip.getExtensionFilters().add(filter);

                    Button backBtn = new Button("Back");

                    Button zipBtn = new Button("Choose File");
                    zipBtn.setStyle("-fx-font-size: 15pt");

                    generate = new Button("Generate");
                    generate.setStyle("-fx-font-size: 15pt; -fx-base: #ff5950 ");
                    generate.setDisable(true);

                    title = new Label("Upload a Zip File");
                    title.setStyle("-fx-font-size: 20pt");

                    locationLabel = new Label("Choose Location");
                    locationLabel.setStyle("-fx-font-size: 15pt");

                    locationLabel = new Label("Choose Location");
                    locationLabel.setStyle("-fx-font-size: 15pt");

                    fileLocation = new Button("File Location");
                    fileLocation.setStyle("-fx-font-size: 15pt");
                    fileLocation.setDisable(true);

                    Label fileLabel = new Label("");

                    fileLocation.setOnAction(
                            e -> {
                                File file = chooser.showDialog(primary);
                                if (file != null) {
                                    destinationPath = file.toString();
                                    chosenLocationLabel.setText(destinationPath);
                                    generate.setDisable(false);
                                } else {
                                    chosenLocationLabel.setText(null);
                                }
                            });

                    zipBtn.setOnAction(
                            e -> {
                                zipPath = getFilePathZip(primary);
                                populateFilePathLabel(zipPath, fileLabel);
                                fileLocation.setDisable(false);
                            });

                    generate.setOnAction(
                            event1 -> {
                                generateReport(zipPath,destinationPath);
                            });

                    Label zipLabel = new Label("Zip File of Student Marks");
                    zipLabel.setStyle("-fx-font-size: 15pt");
                    gridPane.setHalignment(zipLabel, HPos.RIGHT);

                    backBtn.setOnAction(event1 -> {
                        primary.setScene(mainScene);
                        primary.show();
                    });

                    gridPane.add(backBtn, 0, 0);
                    gridPane.add(title, 1, 0);
                    gridPane.add(generate, 1, 8);
                    gridPane.add(zipLabel, 0, 6);
                    gridPane.add(zipBtn, 1, 6);
                    gridPane.add(fileLabel, 2, 6);
                    gridPane.setHalignment(title, HPos.CENTER);
                    gridPane.setHalignment(zipBtn, HPos.CENTER);
                    gridPane.setHalignment(generate, HPos.CENTER);

                    gridPane.add(locationLabel, 0, 7);
                    gridPane.add(fileLocation, 1, 7);
                    gridPane.add(chosenLocationLabel, 2, 7);
                    gridPane.setHalignment(locationLabel, HPos.RIGHT);
                    gridPane.setHalignment(fileLocation, HPos.CENTER);

                    zipScene = new Scene(gridPane);
                    primary.setScene(zipScene);
                    primary.show();
                });

        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #FDFDFD");
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10));

        gridPane.setPrefSize(900, 600);
        gridPane.getColumnConstraints().add(new ColumnConstraints(300));
        gridPane.getColumnConstraints().add(new ColumnConstraints(300));
        gridPane.getColumnConstraints().add(new ColumnConstraints(300));

        gridPane.add(title, 1, 0);
        gridPane.setHalignment(title, HPos.CENTER);

        try {
            FileInputStream input = new FileInputStream("../Customarker/src/TestSuite/SampleFiles/essex_logo.png");
            Image logo = new Image(input, 300, 100, true, false);
            ImageView image = new ImageView(logo);
            gridPane.add(image, 2, 0);
        } catch (IOException e) {
        }

        gridPane.add(supervisorTemplateLabel, 0, 1);
        gridPane.add(assessorTemplateLabel, 0, 2);
        gridPane.add(studentsTempLabel, 0, 3);
        gridPane.setHalignment(supervisorTemplateLabel, HPos.RIGHT);
        gridPane.setHalignment(assessorTemplateLabel, HPos.RIGHT);
        gridPane.setHalignment(studentsTempLabel, HPos.RIGHT);

        gridPane.add(supervisorFile, 1, 1);
        gridPane.add(assessorFile, 1, 2);
        gridPane.add(studentFile, 1, 3);
        gridPane.setHalignment(supervisorFile, HPos.CENTER);
        gridPane.setHalignment(assessorFile, HPos.CENTER);
        gridPane.setHalignment(studentFile, HPos.CENTER);

        gridPane.add(superVisorLabel, 2, 1);
        gridPane.add(assessorLabel, 2, 2);
        gridPane.add(studentLabel, 2, 3);

        gridPane.add(marksFromZipLabel, 1, 12);
        gridPane.add(zipButton, 1, 13);
        gridPane.setHalignment(marksFromZipLabel, HPos.CENTER);
        gridPane.setHalignment(zipButton, HPos.CENTER);

        gridPane.add(locationLabel, 0, 5);
        gridPane.add(fileLocation, 1, 5);
        gridPane.add(chosenLocationLabel, 2, 5);
        gridPane.setHalignment(locationLabel, HPos.RIGHT);
        gridPane.setHalignment(fileLocation, HPos.CENTER);

        gridPane.add(includeReportTitle, 1, 4);
        gridPane.setHalignment(includeReportTitle, HPos.CENTER);

        gridPane.add(generate, 1, 7);
        gridPane.setHalignment(generate, HPos.CENTER);

        mainScene = new Scene(gridPane);
        primary.setScene(mainScene);
        primary.show();
    }

    public void populateFilePathLabel(String path, Label label) {

        if (!path.equals("")) {
            String[] temp = path.split("\\\\");
            String file_name = temp[temp.length - 1];
            label.setText(file_name);
            if (allFilesChosen(superVisorLabel, assessorLabel, studentLabel)) {
                fileLocation.setDisable(false);
            }
        } else {
            label.setText(null);
        }
    }

    public static String getFilePathStudents(Stage primary) {
        File file = chooseFile.showOpenDialog(primary);
        return file != null ? file.toString() : "";
    }

    public static String getFilePathXLSX(Stage primary) {
        File file = chooseFileXLSX.showOpenDialog(primary);
        return file != null ? file.toString(): "";
    }

    public static String getFilePathZip(Stage primary) {
        File file = chooseZip.showOpenDialog(primary);
        return file != null ? file.toString(): "";
    }


    public void generate(String supervisorTemplatePath, String secondAssessorTemplatePath, String fileNameStudent, String destinationPath) {
        try {
            errors.clear();
            String[] splitByDots = fileNameStudent.split("\\\\");
            String lastElement = splitByDots[splitByDots.length - 1];
            String[] splitLastElement = lastElement.split("\\.");
            String extension = splitLastElement[splitLastElement.length - 1];
            List<String[]> students = new ArrayList<>();
            switch (extension) {
                case "xlsx":
                    students = ReadXLSX.xlsxReader(fileNameStudent, includeReportTitle.isSelected());
                    break;
                case "xls":
                    students = ReadXLSX.xlsxReader(fileNameStudent, includeReportTitle.isSelected());
                    break;
                case "csv":
                    students = ReadCSV.readStudents(fileNameStudent, includeReportTitle.isSelected());
                    break;
            }

            if (errors.size() != 0) {
                writeErrors(destinationPath);
            }

            Workbook supervisorTemplate = ReadTemplate.readTemplateXLSX(supervisorTemplatePath);

            Workbook assessorTemplate = ReadTemplate.readTemplateXLSX(secondAssessorTemplatePath);

            System.out.println();

            for (String[] student : students) {

                String supervisorPath = destinationPath + "/Supervisors/" + student[3] + "/" +
                        student[1] + "_" + student[0] + ".xlsx";

                String secondAssessorPath = destinationPath + "/SecondAssessors/" + student[4] + "/" +
                        student[1] + "_" + student[0] + ".xlsx";

                WriteXLSX.directoryCreation(supervisorPath);
                WriteXLSX.directoryCreation(secondAssessorPath);

                WriteXLSX.WriteDataIntoWorkbook(supervisorTemplate, supervisorPath, student, includeReportTitle.isSelected());
                WriteXLSX.WriteDataIntoWorkbook(assessorTemplate, secondAssessorPath, student, includeReportTitle.isSelected());
            }

            showPopup(errors.size(), destinationPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeErrors(String path) {
        File file = new File(path + "\\errors.txt");
        try {
            PrintWriter writer = new PrintWriter(file);
            for (String error : Window.errors) {
                writer.println(error);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean allFilesChosen(Label svl, Label al, Label sl) {
        if ((svl.getText() != "") && (al.getText() != "") && (sl.getText() != "")) {
            return true;
        }
        return false;
    }

    private void showPopup(int numErrors, String destinationPath) {


        Alert alert;
        if (numErrors < 1) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Operation was successful!");
            alert.setContentText("Your files have been saved in: " + destinationPath);
        } else {

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("The process has been completed with " + numErrors + " errors.");
            alert.setContentText("An error log has been created in: " + destinationPath);
        }
        alert.showAndWait();
    }

    private void generateReport(String zipPath, String destinationPath){
        ReadZIP zipReader = new ReadZIP(zipPath, "\\temp");
        Report.WriteData(zipReader.readWorkbooks(), destinationPath+"\\report.xlsx");
        showPopup(0,destinationPath);
    }
}
