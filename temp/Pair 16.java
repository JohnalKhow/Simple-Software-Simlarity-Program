package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.awt.*;

public class Controller {
    public GridPane gridPane;
    public void initialize(){


        SimilarityChecker Main = new SimilarityChecker();
        Main.run();

        double[][] scores = Main.getSimilarityScore();
        String[] names = Main.getNamesString();

        Text[][] label= new Text[Main.getSubmissionSize()][Main.getSubmissionSize()];
        gridPane.setPadding(new Insets(6,6,6,6));

        for(int i=1;i<Main.getSubmissionSize()+1;i++)
        {
            gridPane.add(new Text(" "+names[i-1]+" "),i+1,1);
            gridPane.add(new Text(" "+names[i-1]+" "),1,i+1);
        }
        for(int i=1;i<Main.getSubmissionSize()+1;i++)
        {
            for(int j=1;j<Main.getSubmissionSize()+1;j++){
                label[i-1][j-1]=new Text(" "+String.valueOf(scores[i-1][j-1])+" ");
                gridPane.add(label[i-1][j-1],i+1,j+1);
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
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.RowConstraints?>

<Pane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="600.0" prefWidth="800.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="sample.Controller">
   <children>
      <ScrollPane prefHeight="600.0" prefWidth="800.0">
         <content>
            <GridPane fx:id="gridPane" prefHeight="604.0" prefWidth="802.0" style="-fx-border-width: 5; -fx-border-color: BLACK; -fx-grid-lines-visible: true;">
              <columnConstraints>
                  <ColumnConstraints minWidth="0.0" prefWidth="0.0" />
              </columnConstraints>
               <rowConstraints>
                  <RowConstraints minHeight="0.0" prefHeight="0.0" />
               </rowConstraints>
            </GridPane>
         </content>
      </ScrollPane>
   </children>
</Pane>
package sample;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimilarityChecker {

    private static int SubmissionSize;
    private int idx=0;
    private String[] NamesString;
    private double[][] similarityScore;


    public double[][] getSimilarityScore() {
        return similarityScore;
    }

    public int getSubmissionSize()
    {
        return SubmissionSize;
    }

    public String[] getNamesString(){
        return NamesString;
    }

    public void run(){
        //Location of the file directory
        File folder = new File("C:\\Users\\Emerson Tiu\\IdeaProjects\\Module0\\assets\\submissions");
        SubmissionSize = folder.listFiles().length;

        //Contains all submissions
        String[] SubmissionString = new String[SubmissionSize];

        //Contains the folder name of each submissions
        NamesString = new String[SubmissionSize];


        check(folder,SubmissionString,NamesString);

        //Print outs the string for each submission
        for(int i=0;i<SubmissionSize;i++){

            System.out.println(NamesString[i]+": \n"+SubmissionString[i]+"\n");
            //System.out.println("Size "+size+": "+SubmissionString[i]);
        }

        similarityScore = new double[SubmissionSize][SubmissionSize];
        for(int i=0;i<SubmissionSize;i++){
            for(int j=0;j<SubmissionSize;j++){
                similarityScore[i][j] = comparison(SubmissionString[i],SubmissionString[j]);
            }
        }

        BigDecimal bd;

        for(int i=0;i<SubmissionSize;i++)
        {
            for(int j=0;j<SubmissionSize;j++)
            {
                bd = new BigDecimal(similarityScore[i][j]).setScale(2, RoundingMode.HALF_UP);
                similarityScore[i][j]=bd.doubleValue();
                System.out.print(similarityScore[i][j]+"  \t  ");
            }
            System.out.println();
        }


    }

    //Reads all files in the directory and places them into one string array
    public void check(File folder, String[] SubmissionArray,String[] NamesString){
        String personText = "";

        //System.out.println(folder.getName());

        //Retrieves all files in the folder
        File[] filenames = folder.listFiles();
        for(File file: filenames){

            //Checks if theres a folder within the folder
            if(file.isDirectory()){
                check(file,SubmissionArray,NamesString);
                idx++;
            }
            else{
                try{
                    //Adds content of multiple files into one string
                    personText = personText+readContent(file, personText);
                    SubmissionArray[idx] = personText;
                    NamesString[idx] = folder.getName();

                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Reads all the content of the files
    public String readContent(File file, String test) throws IOException{
        //System.out.println(file.getName());

        String text1 = new String(Files.readAllBytes(Paths.get(file.getPath())));

        //Replaces all whitespace to "-"
        text1=text1.replaceAll("\\s+","-");

        test = test+text1;

        return test;
    }

    public double comparison(String file1, String file2){
        double percentage=0;
        String[] fileA = file1.split("-");
        String[] fileB = file2.split("-");

        double difference = 0;
        if(fileA.length>=fileB.length){
            for(int i=0;i<fileB.length;i++){
                if(fileA[i].compareTo(fileB[i])==0) difference++ /*PLACE SCORING HERE*/;
                else difference--;
                percentage=difference/fileB.length;
                }
        }else{
            for(int i=0;i<fileA.length;i++){
                if(fileB[i].compareTo(fileA[i])==0) difference++ /*PLACE SCORING HERE*/;
                else difference--;
            }
            percentage=difference/fileA.length;
        }

        return percentage;
    }

}
