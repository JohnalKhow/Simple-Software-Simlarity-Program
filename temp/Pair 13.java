import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Matrix.fxml"));
        primaryStage.setTitle("Matrix");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>


<AnchorPane pickOnBounds="false" prefHeight="720.0" prefWidth="1280.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="Matrix">
   <children>
      <ScrollPane hbarPolicy="ALWAYS" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="720.0" prefWidth="1280.0" vbarPolicy="ALWAYS">
        <content>
          <AnchorPane minHeight="0.0" minWidth="0.0">
               <children>
                  <GridPane fx:id="grid" alignment="CENTER" gridLinesVisible="true">
                    <columnConstraints>
                      <ColumnConstraints hgrow="SOMETIMES" maxWidth="114.0" minWidth="10.0" prefWidth="97.0" />
                    </columnConstraints>
                    <rowConstraints>
                      <RowConstraints fillHeight="false" maxHeight="243.0" prefHeight="35.0" />
                      <RowConstraints fillHeight="false" maxHeight="450.0" />
                    </rowConstraints>
                  </GridPane>
               </children>
            </AnchorPane>
        </content>
      </ScrollPane>
   </children>
</AnchorPane>
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix {
    public GridPane grid;
    ArrayList<String> file = new ArrayList<>();


    int size;
    public void initialize() throws IOException {
        double score;
        Matrix compare = new Matrix();
        ArrayList<String> files = new ArrayList<>();
        files=compare.read("Assets/tocheck",files);

        for(int m=0; m<files.size();m++)
        {
            String name[] = files.get(m).split("/");
            grid.getColumnConstraints().addAll(new ColumnConstraints(60));
            grid.add(new Label(name[2]), 0, m+1);
            grid.add(new Label(name[2]), m+1, 0);

            for(int h=0;h<files.size();h++){
                score = compare.compare(files.get(m),files.get(h), m, h);
                grid.add(new Label(String.valueOf(score)), m+1, h+1);
            }
        }


    }
    public void setFile(ArrayList<String> file) {
        this.file = file;
    }

    public void setSize(int size) {
        this.size = size;
    }


    ArrayList<String> read(String file,ArrayList<String>files){
        File folder = new File(file);
        File[] fileList = folder.listFiles();
        for(int i = 0; i <fileList.length; i++){
            if(fileList[i].isFile()){
                files.add(file+"/"+fileList[i].getName());
            }
            else if (fileList[i].isDirectory()) {
                read(file.toString()+"/"+fileList[i].getName(),files);
            }
        }
        return files;
    }
    void compareNew() throws IOException{


    }
    double compare(String file1, String file2, int m, int h) throws IOException{
        File test_program1 = new File(file1);
        File test_program2 = new File(file2);

        BufferedReader tp1 = new BufferedReader(new FileReader(test_program1));
        BufferedReader tp2 = new BufferedReader(new FileReader(test_program2));

        String string1=tp1.readLine();
        String string2=tp2.readLine();

        double total=0;
        double same=0;

        while (string1!=null){
            total++;
            if(string1.equals(string2)){
                same++;
            }
            string1=tp1.readLine();
            string2=tp2.readLine();
        }
        double percent = same/total*100;
        percent = Math.round(percent*100.0)/100.0;

        return percent;
    }
}
