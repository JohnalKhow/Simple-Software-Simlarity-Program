package GUI;

import Algorithms.directory_algorithm;
import Algorithms.Read;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.text.DecimalFormat;


public class Main_Controller {
    @FXML
    private GridPane similarity_matrix;
    @FXML
    private ScrollPane scroll_pane;
    private static DecimalFormat two_point = new DecimalFormat("#,##0.00");

    @FXML
    private void handle_btnCHECK(ActionEvent event) {
        Read read = new Read();

        //Get list of file names directory
        File[] files = directory_algorithm.showFiles("Directory");
        double[][] matrix = read.read_files();

        /*Set scene details */
        similarity_matrix.setVisible(true);
        scroll_pane.setVisible(true);

        //Iterate through all files to get and to print the names
        for (int i = 0; i < files.length; i++) {

            //Create new column and rows
            similarity_matrix.add(new Label(files[i].getName()), 0, i + 1);
            similarity_matrix.add(new Label(files[i].getName()), i + 1, 0);

            //Column formatting
            similarity_matrix.getColumnConstraints().addAll(new ColumnConstraints(70));
            //NOTE: row constraints are required to properly format the the printing of the grid
            similarity_matrix.getRowConstraints().addAll(new RowConstraints(20));

            //Iterate through the files
            for (int j = 0; j < files.length; j++) {

                if (matrix[i][j] == 1.0) {
                    //Print out the corresponding similarity scores
                    similarity_matrix.add(new Label((two_point.format(matrix[i][j]))), i + 1, j + 1);
                } else {
                    similarity_matrix.add(new Label(two_point.format(matrix[i][j])), i + 1, j + 1);
                }
            }
        }


    }
}
