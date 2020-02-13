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
    @FXML private GridPane similarity_matrix;
    @FXML private ScrollPane scroll_pane;
    private static DecimalFormat two_point = new DecimalFormat("#,##0.00");
    @FXML
    private void handle_btnCHECK(ActionEvent event){
        Read read= new Read();
        directory_algorithm dir = new directory_algorithm();
        File[] files=dir.showFiles("Directory");
        similarity_matrix.setVisible(true);
        scroll_pane.setVisible(true);
        double [][] matrix = read.read_files();
        for(int i=0; i<files.length; i++){
            similarity_matrix.add(new Label(files[i].getName()), 0, i+1);
            similarity_matrix.add(new Label(files[i].getName()), i+1, 0);
            similarity_matrix.getColumnConstraints().addAll(new ColumnConstraints(70));
            similarity_matrix.getRowConstraints().addAll(new RowConstraints(20));
            for(int j=0;j<files.length; j++){
                if(matrix[i][j]==1.0){
                    similarity_matrix.add(new Label((two_point.format(matrix[i][j]))), i+1, j+1);
                }
                else {
                    similarity_matrix.add(new Label(two_point.format(matrix[i][j])), i+1, j+1);
                }
            }
        }




    }
}
