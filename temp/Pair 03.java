package sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

    public TextField directoryText;
    public AnchorPane newPane;

    public void compute() throws Exception {
        Queue<String> access_paths = new LinkedList<>();
        Queue<String> matrix_maker = new LinkedList<>();
        String[] paths = new String[37];

        double same_counter = 0;
        double line_counter1 = 0;
        double line_counter2 = 0;
        double line_total;
        int count_file=0;
        String[][] matrix = new String[37][37];

        int spacerX = 0;
        int spacerY =0;

        try (Stream<Path> walk = Files.walk(Paths.get(directoryText.getText()))) {

            List<String> output = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".java")).collect(Collectors.toList());

            count_file=output.size();
            //System.out.println(count_file);

            for(int i=0;i<count_file;i++) {
                access_paths.add(output.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> walk = Files.walk(Paths.get(directoryText.getText()))) {

            List<String> output = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".cpp")).collect(Collectors.toList());

            count_file=count_file+output.size();
            //System.out.println(count_file);

            for(int i=0;i<output.size();i++) {
                access_paths.add(output.get(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<count_file;i++) {
            paths[i] = access_paths.remove();
        }

        for(int i=0;i<count_file;i++) {
            for (int j = 0; j < count_file; j++) {
                File file1 = new File(paths[i]);
                File file2 = new File(paths[j]);

                BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                BufferedReader reader2 = new BufferedReader(new FileReader(file2));

                String text1 = reader1.readLine();
                String text2 = reader2.readLine();

                while (text1 != null && text2 != null) {
                    if (text1.equalsIgnoreCase(text2)) {
                        same_counter++;
                    }

                    text1 = reader1.readLine();
                    text2 = reader2.readLine();
                }

                BufferedReader reader3 = new BufferedReader(new FileReader(file1));
                BufferedReader reader4 = new BufferedReader(new FileReader(file2));

                text1 = reader3.readLine();
                text2 = reader4.readLine();

                while (text1 != null) {
                    text1 = reader3.readLine();
                    line_counter1++;
                }

                while (text2 != null) {
                    text2 = reader4.readLine();
                    line_counter2++;
                }

                line_total = (line_counter1 + line_counter2) / 2;
                //System.out.println(line_counter1);
                //System.out.println(line_counter2);
                //System.out.println("Number of lines that are the same: " + same_counter);
                //System.out.println("Total number of lines: " + line_total);

                double score = ((same_counter / line_total)-0.5) * 2;
                if (score==1){
                    matrix_maker.add("1.00");
                }
                else if(score==-1){
                    matrix_maker.add("-1.00");
                }
                else {
                    //System.out.println("Your plagiarism score is: " + score);

                    matrix_maker.add(new DecimalFormat("#.##").format(score));
                }

                line_counter1=0;
                line_counter2=0;
                same_counter=0;
            }
        }

        /*for (int c = 0; c < count_file; c++) {
            for (int d = 0; d < count_file; d++) {
                matrix[c][d] = matrix_maker.remove();
            }
        }*/

        for (int c = 0; c < count_file; c++) {
            spacerX=0;
            for (int d = 0; d < count_file; d++) {
                Label data;
                data = new Label(matrix_maker.remove());
                data.setLayoutX(spacerX);
                data.setLayoutY(spacerY);
                newPane.getChildren().add(data);

                //System.out.print(matrix[c][d]+"\t");
                spacerX = spacerX +45;
            }

            spacerY=spacerY+20;
            //System.out.println("");
            /*Label data;
            data = new Label();
            data.setLayoutX(spacerX);
            data.setLayoutY(spacerY);
            newPane.getChildren().add(data);*/
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
        primaryStage.setTitle("Plagiarism Checker Matrix");
        primaryStage.setScene(new Scene(root, 1745, 850));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlagiarismChecker {

    public static void main(String[] args) throws Exception {
        Queue<String> access_paths = new LinkedList<>();
        Queue<String> matrix_maker = new LinkedList<>();
        String[] paths = new String[37];

        double same_counter = 0;
        double line_counter1 = 0;
        double line_counter2 = 0;
        double line_total;
        int count_file=0;
        String[][] matrix = new String[37][37];

        try (Stream<Path> walk = Files.walk(Paths.get("C:\\Users\\ambro\\Downloads\\Submissions\\Submissions"))) {

            List<String> output = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".java")).collect(Collectors.toList());

            count_file=output.size();
            //System.out.println(count_file);

            for(int i=0;i<count_file;i++) {
                access_paths.add(output.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> walk = Files.walk(Paths.get("C:\\Users\\ambro\\Downloads\\Submissions\\Submissions"))) {

            List<String> output = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".cpp")).collect(Collectors.toList());

            count_file=count_file+output.size();
            //System.out.println(count_file);

            for(int i=0;i<output.size();i++) {
                access_paths.add(output.get(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<count_file;i++) {
            paths[i] = access_paths.remove();
        }

        for(int i=0;i<count_file;i++) {
            for (int j = 0; j < count_file; j++) {
                File file1 = new File(paths[i]);
                File file2 = new File(paths[j]);

                BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                BufferedReader reader2 = new BufferedReader(new FileReader(file2));

                String text1 = reader1.readLine();
                String text2 = reader2.readLine();

                while (text1 != null && text2 != null) {
                    if (text1.equalsIgnoreCase(text2)) {
                        same_counter++;
                    }

                    text1 = reader1.readLine();
                    text2 = reader2.readLine();
                }

                BufferedReader reader3 = new BufferedReader(new FileReader(file1));
                BufferedReader reader4 = new BufferedReader(new FileReader(file2));

                text1 = reader3.readLine();
                text2 = reader4.readLine();

                while (text1 != null) {
                    text1 = reader3.readLine();
                    line_counter1++;
                }

                while (text2 != null) {
                    text2 = reader4.readLine();
                    line_counter2++;
                }

                line_total = (line_counter1 + line_counter2) / 2;
                //System.out.println(line_counter1);
                //System.out.println(line_counter2);
                //System.out.println("Number of lines that are the same: " + same_counter);
                //System.out.println("Total number of lines: " + line_total);

                double score = ((same_counter / line_total)-0.5) * 2;
                if (score==1){
                    matrix_maker.add("1.00");
                }
                else if(score==-1){
                    matrix_maker.add("-1.00");
                }
                else {
                    //System.out.println("Your plagiarism score is: " + score);

                    matrix_maker.add(new DecimalFormat("#.##").format(score));
                }

                line_counter1=0;
                line_counter2=0;
                same_counter=0;
            }
        }

        for (int c = 0; c < count_file; c++) {
            for (int d = 0; d < count_file; d++) {
                matrix[c][d] = matrix_maker.remove();
            }
        }

        for (int c = 0; c < count_file; c++) {
            for (int d = 0; d < count_file; d++) {
                System.out.print(matrix[c][d]+"\t");
            }

            System.out.println();
        }
    }
}


//----------------------------------------------------------ALL FILES--------------------------------------------------------------------//

//SEE File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\see\See_Measure of Software Similarity.cpp");
//CO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\malcolm_src\FileToString.java");
//VICENTE File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\VicenteMod1\Mod1\src\Main.java");
//VHONG File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\VHONG-11814888 - Module 0.cpp");
//TUPAL File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Tupal_Submission\Submission\Similarity\src\Main.java");
//TORO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Toro_Module_0.cpp");
//TEDDY File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\TeddyMoss.java");
//SOLIS File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Solis-Checker-Module0\Solis-Checker-Module0\src\programChecker.java");
//MEGINO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\ProgramComparison_Megino\programcomparison_Megino.cpp");
//PORTUGAL File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Portugal_11815027_Mod0\11815027_Mod0\src\main.java");
//PARCO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Parco_main.cpp");
//CONTRERAS File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Similarity_Checker_Contreras\Similarity_Checker\src\Compare.java");
//PANES File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\PanesSimilarityChecker.java");
//SEMIRA File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\semira_M0.cpp");
//VASQUEZ File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Module0-Vasquez\Module0-Vasquez\src\Module0Vasquez.java");
//REGACHO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Module0_Regacho\Module0_Regacho\main.cpp");
//CABATO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Module0_CABATO\Module0_CABATO\src\Main.java");
//CAOILE File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\MODULE 0_Caoile\MODULE 0\testmain.cpp");
//ANTOC File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Module 0_Antoc\Module 0\src\Main.java");
//SABU File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Module 0 SABULARSE\Module 0\src\SoftwareSimilarity.java");
//MAGALLANES File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Magallanes_Module0_11828668\LBYCP2D_Module0_11828668\src\Similaritor.java");
//LOPEZ File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Lopez_Module0_CodeComparison\Module0_CodeComparison\src\Main.java");
//LLARENAS File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Llarenas_Main.java");
//KHOW File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Khow_ACTIVITY 0\ACTIVITY 0\src\Compare.java");
//JAN GO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\JanGo11812249_Module0\11812249_Module0\LBYCPD2_Module0\src\sample\Main.java");
//HANS File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\HansModule0\Module0\src\Compare.java");
//BOMBITA File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Module 0\src\PlagiarismChecker.java");
//MARC GO File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\GoMarcModule 0\Module 0\src\Main.java");
//GABAY File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Gabay.0.cpp");
//ENGHOY File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\Enghoy_Main.java");
//EMER File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\src\CompareCode.java");
//CHUA File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\act1\src\check.java");
//CHIU File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\ChiuPlagiarism.java");
//CHAN File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\CHAN-module0.cpp");
//BAYETA File file2 = new File("C:\Users\ambro\Downloads\Submissions\Submissions\BayetaExer0\CompareTwoFiles\src\CompareFile.java");
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>


<GridPane alignment="center" hgap="10" prefHeight="768.0" prefWidth="1649.0" vgap="10" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/11.0.1" fx:controller="sample.Controller">
   <columnConstraints>
      <ColumnConstraints />
   </columnConstraints>
   <rowConstraints>
      <RowConstraints />
   </rowConstraints>
   <children>
      <BorderPane prefHeight="786.0" prefWidth="1673.0">
         <top>
            <HBox prefHeight="0.0" prefWidth="526.0" BorderPane.alignment="CENTER">
               <children>
                  <Region prefHeight="26.0" prefWidth="78.0" HBox.hgrow="ALWAYS" />
                  <TextField fx:id="directoryText" prefHeight="26.0" prefWidth="464.0" promptText="Directory...">
                     <HBox.margin>
                        <Insets />
                     </HBox.margin>
                  </TextField>
                  <Region prefHeight="26.0" prefWidth="174.0" />
                  <Button mnemonicParsing="false" onAction="#compute" text="Proceed" />
                  <Region prefHeight="200.0" prefWidth="200.0" HBox.hgrow="ALWAYS" />
               </children>
               <BorderPane.margin>
                  <Insets />
               </BorderPane.margin>
               <padding>
                  <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
               </padding>
            </HBox>
         </top>
         <center>
            <AnchorPane fx:id="newPane" prefHeight="200.0" prefWidth="200.0" BorderPane.alignment="CENTER" />
         </center>
      </BorderPane>
   </children>
</GridPane>
