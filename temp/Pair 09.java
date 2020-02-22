package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.File;

public class Controller {
    @FXML
    private TextArea textarea;

    @FXML
    private Button startButton;

    @FXML
    void startProgram(ActionEvent event) throws Exception {
        int filecount = 0;
        int pair1 = 0, pair2 = 0;
        Double percent1, percent2;
        File f = new File("C:\\Users\\Jan Go\\Desktop\\Files");
        File[] files = f.listFiles();
        getSimilarity sim = new getSimilarity();
        StringBuilder s = new StringBuilder();

        try {

            System.out.println("Files are:");
            filecount = files.length;
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String[][] percentmatrix = new String[filecount][filecount];
        Double[][] percentmatrixdouble = new Double[filecount][filecount];

        for (int i =0; i <filecount;i++){
            for (int j=0; j<filecount;j++){
                String formatedString = String.format("%.1f",sim.getPercent(files, i, j));
                percentmatrixdouble[i][j] = sim.getPercent(files, i, j);
                percentmatrix[i][j] = formatedString;
            }
        }

        textarea.setPrefColumnCount(filecount+1);
        textarea.setPrefRowCount(filecount);

        System.out.println("\n\nMatrix of Percentage Similarity (follow format from 'Files are:'): \n");
        s.append("\n\nMatrix of Percentage Similarity (follow format from 'Files are:'): \n");
        s.append(String.format("%50s", ""));
        System.out.format("%16s", "");
        for (int i=0; i<filecount; i++) {
            System.out.format("%16s",files[i].getName());
            s.append(String.format("%50s", files[i].getName()));
        }
        System.out.println();
        s.append("\n");
        for (int i =0; i <filecount;i++) {
            System.out.format("%16s",files[i].getName());
            s.append(String.format("%50s", files[i].getName()));
            for (int j = 0; j < filecount; j++) {
                System.out.format("%16s", percentmatrix[i][j]);
                s.append(String.format("%53s",percentmatrix[i][j]));
            }
            System.out.println();
            s.append("\n");
        }

        textarea.setText(s.toString());

        percent1 = 0.0;

        for (int i=0; i<filecount; i++) {
            for (int j=0; j<filecount; j++) {
                if(i==j) continue;
                if(percent1 < percentmatrixdouble[i][j]) {
                    percent1 = percentmatrixdouble[i][j];
                    pair1 = i;
                    pair2 = j;
                }
            }
        }

        String formatedString = String.format("%.1f", percent1);
        System.out.println("Highest word percentage similarity: " + formatedString);
        System.out.println("Pair: " + files[pair1].getName() + " & " + files[pair2].getName());

    }
}
package sample;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class getSimilarity {
    double percent;
    int wordcount;
    double wordpercent;

    getSimilarity(){
        percent = 0;
        wordcount = 0;
        wordpercent = 0.0;
    }

    double getPercent(File[] files, int i, int j) throws Exception {
        percent = 0;
        wordcount = 0;
        wordpercent = 0.0;

        String text1 = readFileAsString("C:\\Users\\Jan Go\\Desktop\\Files\\"+files[i].getName());
        String text2 = readFileAsString("C:\\Users\\Jan Go\\Desktop\\Files\\"+files[j].getName());

        String[] words1 = text1.split("\\W+");
        String[] words2 = text2.split("\\W+");

        wordpercent = (100.0/(((double)words2.length+(double) words1.length)/2));

        for(i =0; i< words2.length;i++){
            for (j=0; j<words1.length;j++) {
                if (words1[j].equalsIgnoreCase(words2[i])) {
                    words1[j]="";
                    percent = percent + wordpercent;
                }
            }
        }
        return percent;
    }

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
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
        primaryStage.setTitle("Percentage Similarity Program");
        primaryStage.setScene(new Scene(root, 600, 412));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="412.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="sample.Controller">
   <children>
      <TextArea fx:id="textarea" editable="false" layoutX="14.0" layoutY="74.0" prefHeight="291.0" prefWidth="572.0" />
      <Button fx:id="startButton" layoutX="425.0" layoutY="374.0" mnemonicParsing="false" onAction="#startProgram" prefHeight="25.0" prefWidth="161.0" text="Get Similarity Percentage" />
      <Text layoutX="14.0" layoutY="51.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Percentage Similarity Program">
         <font>
            <Font size="24.0" />
         </font>
      </Text>
   </children>
</AnchorPane>
