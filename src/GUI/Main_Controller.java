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

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Main_Controller {
    @FXML
    private GridPane similarity_matrix;
    @FXML
    private ScrollPane scroll_pane;
    private static DecimalFormat two_point = new DecimalFormat("#,##0.00");
    ArrayList<String> files = new ArrayList<>();
    ArrayList<String> file_name = new ArrayList<>();

    @FXML
    private void handle_btnCHECK(ActionEvent event) throws FileNotFoundException {
        Read read = new Read();

        //Get list of file names directory
        directory_algorithm dir = new directory_algorithm();
        file_name = dir.get_file_names();
        files = dir.get_files("Directory");
        double[][] matrix = read.read_files();

        /*Set scene details */
        similarity_matrix.setVisible(true);
        scroll_pane.setVisible(true);

        //Iterate through all files to get and to print the names
        for (int i = 0; i < files.size(); i++) {

            //Create new column and rows
            similarity_matrix.add(new Label(file_name.get(i)), 0, i + 1);
            similarity_matrix.add(new Label(file_name.get(i)), i + 1, 0);

            //Column formatting
            similarity_matrix.getColumnConstraints().addAll(new ColumnConstraints(70));
            //NOTE: row constraints are required to properly format the the printing of the grid
            similarity_matrix.getRowConstraints().addAll(new RowConstraints(20));

            //Iterate through the files
            for (int j = 0; j < files.size(); j++) {
                    //Print out the corresponding similarity scores
                    similarity_matrix.add(new Label((two_point.format(matrix[i][j]))), i + 1, j + 1);
            }
        }


    }
}
