package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    public TextField folderTextField;
    public Button searchButton;
    public Button checkButton;

    public File file;
    public GridPane matrix;
    public ScrollPane scrollPane;


    public void searchOnAction(ActionEvent actionEvent) {
        Stage resourceStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        file = directoryChooser.showDialog(resourceStage);
    }


    public void checkOnAction(ActionEvent actionEvent) throws IOException {
        scrollPane.setVisible(true);
        String[] files = file.list();
        assert files != null;

        similarityChecker sc = new similarityChecker();

        // Create a loop for reading the files

        ArrayList<String> projFiles = new ArrayList<>();

        for (String s : files) {
            projFiles.add(file.getAbsolutePath() + '\\' + s);
        }

        int rows = files.length;
        int cols = files.length;
        float[][] scores = new float[cols][rows];

        for (int x = 0; x < projFiles.size(); x++) {
            for (int y = 0; y < projFiles.size(); y++) {
                String proj1 = projFiles.get(x);
                String proj2 = projFiles.get(y);
                File code1 = new File(proj1);
                File code2 = new File(proj2);
                try {
                    float simScore = sc.check(code1, code2);
                    System.out.print(String.format("%.2f", simScore) + "\t");
                    scores[x][y] = simScore;
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
            System.out.print("\n");
        }
        matrix.setGridLinesVisible(true);
        for (int x = 0; x < projFiles.size(); x++) {
            for (int y = 0; y < projFiles.size(); y++) {
                String text = String.format("%.2f", scores[x][y]);
                Label label = new Label(text);
                matrix.add(label, x, y);
            }
        }
    }
}
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.*?>
<?import java.lang.*?>
<?import javafx.scene.layout.*?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.AnchorPane?>

<ScrollPane fx:id="scrollPane" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="sample.Controller">
  <content>
    <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="400.0" prefWidth="587.0">
         <children>
            <GridPane fx:id="matrix" layoutX="14.0" layoutY="110.0">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
            </GridPane>
            <TextField fx:id="folderTextField" layoutX="14.0" layoutY="22.0" prefHeight="25.0" prefWidth="486.0" />
            <Button fx:id="searchButton" layoutX="521.0" layoutY="22.0" mnemonicParsing="false" onAction="#searchOnAction" text="Search" />
            <Button fx:id="checkButton" layoutX="268.0" layoutY="56.0" mnemonicParsing="false" onAction="#checkOnAction" text="Check" />
         </children></AnchorPane>
  </content>
</ScrollPane>
package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class similarityChecker {
    public float check(File address1, File address2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(address1));
        BufferedReader reader2 = new BufferedReader(new FileReader(address2));
        float total=0, total1=0, total2=0;
        float sim1=0, sim2=0, sim=0;
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();

        while ((line1 != null)&&(line2 != null)){
            String[] words1 = line1.split("\\s+");
            String[] words2 = line2.split("\\s+");
            for(int i=0; i< words1.length; i++){
                for(int j=0; j< words2.length; j++){
                    if(words1[i].equals(words2[j])){
                        sim1++;
                    }
                }
                total1++;
            }
            for(int i=0; i< words2.length; i++){
                for(int j=0; j< words1.length; j++){
                    if(words2[i].equals(words1[j])){
                        sim2++;
                    }
                }
                total2++;
            }

            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }
        total = (total1+total2)/2;
        sim = (sim1+sim2)/2;
        if(sim > total){
            total = total +(sim - total);
        }
        float perc = (sim/total);
        //System.out.println("The similarity percentage of two files is " + perc*100 + "%.");
        reader1.close();
        reader2.close();
        return perc;
    }
}
