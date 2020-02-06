package GUI;

import Algorithms.Directory_checker;
import Algorithms.Read;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.List;

public class Main_Controller {
    List<TableColumn> columns = new ArrayList<TableColumn>();
    List<TableRow> rows = new ArrayList<TableRow>();
    @FXML private TableView Correlation_matrix;

    @FXML
    private void handle_btnCHECK(ActionEvent event){
        Directory_checker dir = new Directory_checker();
        int no_of_files=dir.count_files();
        System.out.println(no_of_files);
        for(int i=1; i<no_of_files; i++){
            columns.add(new TableColumn(""+i));
        }

        Correlation_matrix.getColumns().addAll(columns);


    }
}
