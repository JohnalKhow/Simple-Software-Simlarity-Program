package GUI;

import Algorithms.Files;
import Algorithms.Calculator;
import Metrics.HalsteadMetrics;
import Metrics.MetricGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class mainController implements Initializable {
    @FXML
    private GridPane similarityMatrix;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox topFiveCont;
    @FXML
    private ListView topFiveList;
    private static DecimalFormat twoPoint = new DecimalFormat("#,##0.00");
    ArrayList<String> files = new ArrayList<>();
    ArrayList<String> fileName = new ArrayList<>();
    ArrayList<Double> top = new ArrayList<>();

    private void sortFive(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[i].length; j++) {
                    top.add(matrix[i][j]);
            }
        }
        Collections.sort(top, Collections.reverseOrder());
        for(int p=0; p<5; p++){
            System.out.println(top.get(p));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Calculator calculator = new Calculator();
        //Get list of file names directory
        Files dir = new Files();
        try {
            files = dir.getFiles("Directory", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // int size = calculator.getSize();
        double[][] matrix = new double[0][];
        try {
            matrix = calculator.getScore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Set scene details */
        similarityMatrix.setVisible(true);
        scrollPane.setVisible(true);
        //Iterate through all files to get and to print the names
        for (int i = 0; i < files.size(); i++) {
            //Create new column and rows
            String[] tempArray = files.get(i).split(Pattern.quote("\\"));
            String data1Ref = tempArray[1];
            String[] nameArray = data1Ref.split(Pattern.quote("."));
            String name = nameArray[0];
            similarityMatrix.add(new Label(name), 0, i + 1);
            similarityMatrix.add(new Label(name), i + 1, 0);
            //Column formatting
            similarityMatrix.getColumnConstraints().addAll(new ColumnConstraints(45));
            //NOTE: row constraints are required to properly format the the printing of the grid
            similarityMatrix.getRowConstraints().addAll(new RowConstraints(18));
            //Iterate through the files
            for (int j = 0; j < files.size(); j++) {
                //Print out the corresponding similarity scores
                Label label = new Label("   " + twoPoint.format(matrix[i][j]) + "    ");
                if (matrix[i][j] == 1.0) {
                    //Add corresponding color
                    label.setStyle("-fx-background-color:RED");
                    similarityMatrix.add(label, i + 1, j + 1);
                } else if (matrix[i][j] < 1.0 && matrix[i][j] >= 0.75) {
                    //Add corresponding color
                    label.setStyle("-fx-background-color:ORANGE");
                    similarityMatrix.add(label, i + 1, j + 1);
                } else if (matrix[i][j] < 0.75 && matrix[i][j] >= 0.50) {
                    //Add corresponding color
                    label.setStyle("-fx-background-color:YELLOW");
                    similarityMatrix.add(label, i + 1, j + 1);
                } else if (matrix[i][j] < 0.50 && matrix[i][j] >= 0.25) {
                    //Add corresponding color
                    label.setStyle("-fx-background-color:#ADFF2F");
                    similarityMatrix.add(label, i + 1, j + 1);
                } else if (matrix[i][j] < 0.25 && matrix[i][j] >= 0) {
                    //Add corresponding color
                    label.setStyle("-fx-background-color:GREEN");
                    similarityMatrix.add(label, i + 1, j + 1);
                }
            }
        }
        sortFive(matrix);
        for(int k=0; k<5; k++){
            topFiveList.getItems().add(top.get(k));
        }
    }

    @FXML
    private void exportMetrics(ActionEvent event) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Metrics.txt", false));
        String pathDirectory = "src";
        HalsteadMetrics hal = MetricGenerator.getMetrics(pathDirectory);
        writer.write("\nOverall Distinct Operators= "+ hal.getDistOperators());
        writer.write("\nOverall Distinct Operands= "+ hal.getDistOperands());
        writer.write("\nOverall Total Operators= "+ hal.getTotOperators());
        writer.write("\nOverall Total Operands= "+ hal.getTotOperands());
        writer.write("\n");
        writer.write("\n###### Halstead Complexity Metrics ######");
        writer.write("\nVocabulary= "+ hal.getVocabulary());
        writer.write("\nProgram Length= "+ hal.getProglen());
        writer.write("\nCalculated Program Length= "+ hal.getCalcProgLen());
        writer.write("\nVolume= "+ hal.getVolume());
        writer.write("\nDifficulty Level= "+ hal.getDifficulty());
        writer.write("\nEffort Level= "+ hal.getEffort());
        writer.write("\nProgramming time= "+ Math.round(hal.getTimeReqProg()) + " seconds");
        writer.write("\nDelivered bugs= "+ hal.getTimeDelBugs());
        writer.close();
        Desktop.getDesktop().open(new File("Metrics.txt"));
    }
}
