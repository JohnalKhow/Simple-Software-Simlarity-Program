package GUI;

import Algorithms.Directory_checker;
import Algorithms.Read;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.text.DecimalFormat;


public class Main_Controller {
    @FXML private GridPane Correlation_matrix;
    private static DecimalFormat two_point = new DecimalFormat("#,##0.00");
    @FXML
    private void handle_btnCHECK(ActionEvent event){
        Read read= new Read();
        Directory_checker dir = new Directory_checker();
        File[] files=dir.showFiles("Directory");
        double [][] matrix = read.read_files();
        for(int i=0; i<files.length; i++){
            Correlation_matrix.add(new Label(files[i].getName()), 0, i+1);
            Correlation_matrix.add(new Label(files[i].getName()), i+1, 0);
            Correlation_matrix.getColumnConstraints().addAll(new ColumnConstraints(70));
            Correlation_matrix.getRowConstraints().addAll(new RowConstraints(20));
            for(int j=0;j<files.length; j++){
                Correlation_matrix.add(new Label(two_point.format(matrix[i][j])), i+1, j+1);
            }
        }




    }
}
