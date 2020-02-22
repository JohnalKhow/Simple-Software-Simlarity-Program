package Backend;


import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class DataObject {

    private Rectangle rect;
    private float data;
    private Label dataLabel;

    public DataObject(float data) {
        this.data = data;

        rect = new Rectangle();

        rect.setWidth(45);
        rect.setHeight(25);

        if (data == 1.0) rect.setFill(Color.hsb(330, 1, 1));
        if (0.90 <= data && data < 1.0) rect.setFill(Color.hsb(300, 1, 1));
        if (0.80 <= data && data < 0.90) rect.setFill(Color.hsb(270, 1, 1));
        if (0.70 <= data && data < 0.80) rect.setFill(Color.hsb(240, 1, 1));
        if (0.60 <= data && data < 0.70) rect.setFill(Color.hsb(210, 1, 1));
        if (0.50 <= data && data < 0.60) rect.setFill(Color.hsb(180, 1, 1));
        if (0.40 <= data && data < 0.50) rect.setFill(Color.hsb(150, 1, 1));
        if (0.30 <= data && data < 0.40) rect.setFill(Color.hsb(120, 1, 1));
        if (0.20 <= data && data < 0.30) rect.setFill(Color.hsb(90, 1, 1));
        if (0.10 <= data && data < 0.20) rect.setFill(Color.hsb(60, 1, 1));
        if (0.0 < data && data < 0.10) rect.setFill(Color.hsb(30, 1, 1));
        if (0.0 == data) rect.setFill(Color.hsb(0, 1, 1));


        dataLabel = new Label(Float.toString(data));

    }

    public Label getLabel() {
        return dataLabel;
    }

    public float getData() {
        return data;
    }

    public Rectangle getRect() {
        return rect;
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.RadioButton?>
<?import javafx.scene.control.ToggleGroup?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.VBox?>

<AnchorPane prefHeight="300.0" prefWidth="500.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="Controller.FilenamesController">
   <children>
      <Pane layoutX="10.0" layoutY="10.0" prefHeight="300.0" prefWidth="500.0">
         <children>
            <VBox alignment="CENTER" prefHeight="300.0" prefWidth="500.0" spacing="10.0">
               <children>
                  <VBox alignment="CENTER" prefHeight="77.0" prefWidth="500.0">
                     <children>
                        <Label text="Read and compare files:" />
                        <HBox alignment="CENTER" prefHeight="63.0" prefWidth="500.0" spacing="10.0">
                           <children>
                              <RadioButton fx:id="lineChoice" mnemonicParsing="false" onAction="#line" selected="true" text="Per Line">
                                 <toggleGroup>
                                    <ToggleGroup fx:id="comparison" />
                                 </toggleGroup></RadioButton>
                              <RadioButton fx:id="characterChoice" mnemonicParsing="false" onAction="#character" text="Per Character" toggleGroup="$comparison" />
                           </children>
                        </HBox>
                     </children>
                  </VBox>
                  <HBox alignment="CENTER" prefHeight="31.0" prefWidth="140.0" spacing="20.0">
                     <children>
                        <Button fx:id="next" mnemonicParsing="false" onAction="#toNext" text="Check" />
                        <Button fx:id="quit" mnemonicParsing="false" onAction="#toExit" text="Quit" />
                     </children>
                  </HBox>
               </children>
            </VBox>
         </children>
      </Pane>
   </children>
</AnchorPane>
package Controller;

import Backend.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class FilenamesController {

    private String comparison;
    public TextField filename1,filename2;
    public Button next;
    public RadioButton lineChoice,characterChoice;
    private Stage stage;
    private String fileName;
    private String message;
    private Similarity check= new Similarity();
    private int type = 0;

    public void passStage(Stage stage){
        this.stage = stage;
    }

    public void line() {
        comparison = "line";
    }

    public void character() {
        comparison = "character";
    }

    public void toExit()
    {
        stage.close();
    }

    public void toNext() throws IOException {
        //Stage popUp= new Stage();
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("../FXML/Status.fxml"));
        Parent root = (Parent) fxmloader.load();

        Scene scene = new Scene(root);

        //CREATE MATRIX
        if(lineChoice.isSelected()) line();
        else {
            character();
            type = 1;
        }

//        StatusController passCont = fxmloader.getController();
//        check.readFile(comparison,status);
        //passCont.passMatrix(check.getSB(),type);
//        check.clearSb();

        stage.setScene(scene);
        stage.setTitle("Software Similarity Program");
        stage.show();
    }

    private void clear()
    {
        filename1.clear();
        filename2.clear();
    }

    private void setMessage(String message) {
        this.message = message;
    }

}


package Controller;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage stage = primaryStage;

        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("../FXML/Status.fxml"));
        Parent root =fxmloader.load();

        primaryStage.setTitle("Software Similarity Program");
        primaryStage.setScene(new Scene(root, 1600, 850));

//        UsernameController passCont = fxmloader.getController();
//        passCont.passStage(stage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
package Backend;

import java.io.File;
import java.io.IOException;

public class MainOperations {


    public static void main(String[] args) throws IOException {

        SystemMetrics metrics = new SystemMetrics();

        File masterFile = new File("Codes");  //for our files [Codes or src only]
        metrics.createSystemMetricsTable(masterFile);  //check this for other's files


    }


}

package Backend;

import java.util.ArrayList;

public class Matrix {
    private ArrayList<ArrayList<Float>> matrix = new ArrayList<>();
    private ArrayList<Float> arrayTemp;

    public void newMatrix()
    {
        matrix= new ArrayList<>();
    }

    public ArrayList<ArrayList<Float>> getMatrix() {
        return matrix;
    }

    public void setNewArray() {
        arrayTemp= new ArrayList<>();
    }

    public void addArray(float value) {
        arrayTemp.add(value);
    }

    public void setMatrix() {
        matrix.add(arrayTemp);
    }

    public int arraySize(){
        return arrayTemp.size();
    }

    public int matrixSize(){
        return matrix.size();
    }

}
package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Similarity {
    private float  percentage = 0;
    private Scanner prog1Scan, prog2Scan;
    private File filename1, filename2;
    private Matrix data = new Matrix();
    private ArrayList<String> arrayA,arrayB;


    public void ReadCodeLine() {
        float sameLines=0,totalLines=0,shorterLimit=0,longestLength=0;
        String longestString = "";

        arrayA = new ArrayList<>();
        arrayB = new ArrayList<>();

        while(prog1Scan.hasNextLine() || prog2Scan.hasNextLine()) {

            if(!prog1Scan.hasNextLine() && prog2Scan.hasNextLine()) {
                String line2 = prog2Scan.nextLine();

                if(!line2.trim().isEmpty()) {
                    arrayB.add(line2);
                    totalLines++;
                }
            }
            else if (prog1Scan.hasNextLine() && !prog2Scan.hasNextLine()) {
                String line1 = prog1Scan.nextLine();

                if(!line1.trim().isEmpty()) {
                    arrayA.add(line1);
                    totalLines++;
                }
            }
            else {
                String line1 = prog1Scan.nextLine();
                String line2 = prog2Scan.nextLine();

                if(!line1.trim().isEmpty()) arrayA.add(line1);
                if(!line2.trim().isEmpty()) arrayB.add(line2);
                //if(!line1.trim().isEmpty() && !line2.trim().isEmpty()) totalLines++;
            }
        }

        if(arrayA.size() > arrayB.size()) totalLines = arrayA.size();
        else totalLines = arrayB.size();

//        totalLines = arrayA.size() + arrayB.size();

        System.out.println("\nNUMBER OF PROG1 LINES: " + arrayA.size());
        System.out.println("NUMBER OF PROG2 LINES: " + arrayB.size() + "\n");


        for(int x=0;x<arrayA.size();x++){  //arrayA.size()
//            System.out.println("PROG 1 LINE: "+arrayA.get(x));
            boolean compared = false;
            for(int y=0;y<arrayB.size();y++){  //arrayB.size()
//                System.out.println("PROG 2 LINE: "+arrayB.get(y));
                if(arrayA.get(x).equals(arrayB.get(y))){
                    if(!compared) {
                        System.out.println("PROG 1 LINE #"+(x+1)+": "+arrayA.get(x));
                        System.out.println("PROG 2 LINE #"+(y+1)+": "+arrayB.get(y));
                        sameLines++;
                        System.out.println(sameLines);
                    }
                    compared = true;
                }
            }
        }

        System.out.println("\nNUMBER OF SAME LINES: " + sameLines);
        System.out.println("NUMBER OF TOTAL LINES: " + totalLines + "\n");
        percentage = (sameLines / totalLines);
        //percentage = (float)((sameLines/totalLines)-0.5) * 2; //testing for negatives
    }

    public void ReadCodeCharacter() {
        int countChar = 0;
        int countTotal = 0;
        while (true) {
            if (prog1Scan.hasNext() && prog2Scan.hasNext()) {
                String data1 = prog1Scan.nextLine();
                String data2 = prog2Scan.nextLine();
                int i = 0;
                while (true) {
                    if (i < data1.length() && i < data2.length()) {
                        if (data1.charAt(i) == data2.charAt(i)) {
                            countChar++;
                            countTotal++;
                        }
                        i++;
                    } else if (i < data2.length()) {
                        countTotal++;
                        i++;
                    } else if (i < data1.length()) {
                        countTotal++;
                        i++;
                    } else {
                        break;
                    }
                }

            } else if (prog1Scan.hasNext()) {
                countTotal = countTotal + prog1Scan.nextLine().length();
            } else if (prog2Scan.hasNext()) {
                countTotal = countTotal + prog2Scan.nextLine().length();
            } else {
                break;
            }

        }
        prog1Scan.close();
        prog2Scan.close();
        percentage = ((float) countChar / (float) countTotal);
        //percentage = (float)((countChar/countTotal)-0.5) * 2; //testing for negatives

    }

    public void readFile(String comparison) throws FileNotFoundException {

        File prog1File = new File("Codes");
        File prog2File = new File("Codes");
        File[] file1 = prog1File.listFiles();
        File[] file2 = prog2File.listFiles();

        data.newMatrix();

        for(int i=0; i<file1.length; i++) //file1.length
        {
            data.setNewArray();

            this.filename1=file1[i];
            for(int j=0; j<file2.length; j++) //file2.length
            {
                this.filename2=file2[j];
                prog1Scan = new Scanner(filename1);
                prog2Scan = new Scanner(filename2);
                if(comparison.equals("line")) ReadCodeLine();
                else ReadCodeCharacter();

                //form.addArray(percentage);  //raw percentage
                data.addArray((float)(Math.round(percentage*100.0)/100.0));  //two decimal points
            }
            data.setMatrix();
        }

    }

    public Matrix getMatrix(){
        return data;
    }


}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.RadioButton?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.ToggleGroup?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<AnchorPane prefHeight="850.0" prefWidth="1600.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="Controller.StatusController">
   <children>
      <VBox prefHeight="850.0" prefWidth="1600.0">
         <children>
            <HBox prefHeight="100.0" prefWidth="200.0">
               <children>
                  <HBox alignment="CENTER" prefHeight="100.0" prefWidth="450.0" spacing="10.0">
                     <children>
                        <Label text="Username:" />
                        <TextField fx:id="username" />
                     </children>
                  </HBox>
                  <HBox alignment="CENTER" prefHeight="100.0" prefWidth="700.0" spacing="30.0">
                     <children>
                        <RadioButton fx:id="lineChoice" mnemonicParsing="false" onAction="#line" selected="true" text="Per Line">
                           <toggleGroup>
                              <ToggleGroup fx:id="toggleC" />
                           </toggleGroup>
                        </RadioButton>
                        <RadioButton fx:id="characterChoice" mnemonicParsing="false" onAction="#character" text="Per Character" toggleGroup="$toggleC" />
                     </children>
                  </HBox>
                  <HBox alignment="CENTER" prefHeight="50.0" prefWidth="450.0" spacing="30.0">
                     <children>
                        <Button fx:id="check" mnemonicParsing="false" onAction="#createMatrix" text="Check" />
                        <Button fx:id="quit" mnemonicParsing="false" onAction="#toExit" text="Quit" />
                     </children>
                  </HBox>
               </children>
            </HBox>
            <GridPane fx:id="gridPane" prefHeight="850.0" prefWidth="1600.0">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
            </GridPane>
         </children>
      </VBox>
   </children>
</AnchorPane>
package Controller;

import Backend.*;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatusController implements Initializable {

    public GridPane gridPane;
    public RadioButton lineChoice,characterChoice;
    public TextField username;
    public Button check,quit;
    private Similarity compare = new Similarity();
    private String comparison = "line";
    private DataObject dataObj;



    public void line() {
        comparison = "line";
    }

    public void character() {
        comparison = "character";
    }

    public void createMatrix() throws IOException {

        compare.readFile(comparison);

        MatrixToGridpane();

    }

    public void MatrixToGridpane(){
        System.out.println("MATRIX BY "+comparison.toUpperCase()+":");

        for(int x = 0; x<compare.getMatrix().arraySize(); x++){
            //System.out.print(form.getMatrix().get(0).get(x) + " ");  //to see MatrixToGridpane row values
            for(int y = 0; y<compare.getMatrix().matrixSize(); y++){
                System.out.print(compare.getMatrix().getMatrix().get(y).get(x) + "  ");
                dataObj = new DataObject(compare.getMatrix().getMatrix().get(y).get(x));


                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                vbox.getChildren().addAll(dataObj.getLabel());

                StackPane pane = new StackPane();
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().addAll(dataObj.getRect(),vbox);

                gridPane.add(pane,y,x);
                //gridPane.setGridLinesVisible(true);
            }
            System.out.println();
        }
    }

    public void toExit()
    {
        Stage stage = (Stage) quit.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        lineChoice.setToggleGroup(toggleChoice);
//        characterChoice.setToggleGroup(toggleChoice);

    }
}
package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SystemMetrics {
    private File[] folders;
    private int numOfFiles=0;
    private String[] filesArray = new String[35];
    private String[] operators = {".","( )","if","for","=","+","while","!",";","==","<=","<","float","int",">",
    "++","new","&&","||","/","{ }"};
    private String[] operands = {"x","y","i","j"};
    //private int[] operandsCount = new int[10];

    public void getAllFiles(File directory) throws IOException {
        folders = directory.listFiles();
        for(File file : folders){  //folders.length
            if(file.isDirectory()){  //folders[x]
                System.out.println("\nFOLDER: "+file.getCanonicalPath());
                getAllFiles(file);
            } else {
                System.out.println("FILE: "+file.getCanonicalPath());
                if(file.getCanonicalPath().contains(".java") || file.getCanonicalPath().contains(".cpp")){
                    filesArray[numOfFiles++] = file.getCanonicalPath();
                }
            }
        }
        //System.out.println(numOfFiles);
    }

    public void searchOperations() throws FileNotFoundException {
        String word = "";
        int dots=0,par=0,ifs=0,fors=0,equal=0,plus=0,whiles=0,not=0,colon=0,equals=0,lessEqual=0,less=0,
                floats=0,ints=0,greater=0,pluses=0,news=0,ands=0,ors=0,fslash=0,curly=0;
        int xs=0,ys=0,is=0,js=0;
        int sum_operators,sum_operands;

        for(int i=0; i<numOfFiles; i++) {  //numOfFiles [change numOfFiles to 1 for the first file only)
            File file = new File(filesArray[i]);
            Scanner fileScanner = new Scanner(file);
            //System.out.println(filesArray[i]);

            while(fileScanner.hasNextLine()){
                word = fileScanner.nextLine();
                //System.out.println(word);

                for(int x=0;x<word.length();x++){
                    //OPERATORS
                    if(word.charAt(x) == '.') dots++;
                    if(word.charAt(x) == '(') par++;
                    if(word.charAt(x) == 'i' && word.charAt(x+1) == 'f') ifs++;
                    if(word.charAt(x) == 'f' && word.charAt(x+1) == 'o' && word.charAt(x+2) == 'r') fors++;
                    if(word.charAt(x) == '=') equal++;
                    if(word.charAt(x) == '+') plus++;
                    if(word.charAt(x) == 'w' && word.charAt(x+1) == 'h' && word.charAt(x+2) == 'i' && word.charAt(x+3) == 'l' && word.charAt(x+4) == 'e') whiles++;
                    if(word.charAt(x) == '!') not++;
                    if(word.charAt(x) == ';') colon++;
                    if(word.charAt(x) == '=' && word.charAt(x+1) == '=') equals++;
                    if(word.charAt(x) == '<' && word.charAt(x+1) == '=') lessEqual++;
                    if(word.charAt(x) == '<') less++;
                    if(word.charAt(x) == 'f' && word.charAt(x+1) == 'l' && word.charAt(x+2) == 'o' && word.charAt(x+3) == 'a' && word.charAt(x+4) == 't') floats++;
                    if(word.charAt(x) == 'i' && word.charAt(x+1) == 'n' && word.charAt(x+2) == 't') ints++;
                    if(word.charAt(x) == '>') greater++;
                    if(word.charAt(x) == '+' && word.charAt(x+1) == '+') pluses++;
                    if(word.charAt(x) == 'n' && word.charAt(x+1) == 'e' && word.charAt(x+2) == 'w') news++;
                    if(word.charAt(x) == '&' && word.charAt(x+1) == '&') ands++;
                    if(word.charAt(x) == '|' && word.charAt(x+1) == '|') ors++;
                    if(word.charAt(x) == '/') fslash++;
                    if(word.charAt(x) == '{') curly++;

                    //OPERATORS
                    /*if(word.charAt(x) == 'x') xs++;    //considers all x,y,i,j
                    if(word.charAt(x) == 'y') ys++;
                    if(word.charAt(x) == 'i') xs++;
                    if(word.charAt(x) == 'j') ys++;*/

                    //considers only x,y,i,j used in for loops or basta as variables lang
                    if(word.charAt(x) == 'x' && (word.charAt(x+1) == '<' || word.charAt(x+1) == '=' || word.charAt(x+1) == '+' || word.charAt(x+1) == ')' || word.charAt(x+1) == ']')) xs++;
                    if(word.charAt(x) == 'y' && (word.charAt(x+1) == '<' || word.charAt(x+1) == '=' || word.charAt(x+1) == '+' || word.charAt(x+1) == ')' || word.charAt(x+1) == ']')) ys++;
                    if(word.charAt(x) == 'i' && (word.charAt(x+1) == '<' || word.charAt(x+1) == '=' || word.charAt(x+1) == '+' || word.charAt(x+1) == ')' || word.charAt(x+1) == ']')) is++;
//                    if(word.charAt(x) == 'j' && (word.charAt(x+1) == '<' || word.charAt(x+1) == '=' || word.charAt(x+1) == '+' || word.charAt(x+1) == ')' || word.charAt(x+1) == ']')) js++;
                }

            }
        }


        sum_operators = dots+par+ifs+fors+equal+plus+whiles+not+colon+equals+lessEqual+less+floats+ints+greater+pluses+news+ands+ors+fslash+curly;
        sum_operands = ys+ys+is+js;

        System.out.println("\n-- OPERATORS --");
        System.out.println(operators[0] + " -> " + dots);
        System.out.println(operators[1] + " -> " + par);
        System.out.println(operators[2] + " -> " + ifs);
        System.out.println(operators[3] + " -> " + fors);
        System.out.println(operators[4] + " -> " + equal);
        System.out.println(operators[5] + " -> " + plus);
        System.out.println(operators[6] + " -> " + whiles);
        System.out.println(operators[7] + " -> " + not);
        System.out.println(operators[8] + " -> " + colon);
        System.out.println(operators[9] + " -> " + equals);
        System.out.println(operators[10] + " -> " + lessEqual);
        System.out.println(operators[11] + " -> " + less);
        System.out.println(operators[12] + " -> " + floats);
        System.out.println(operators[13] + " -> " + ints);
        System.out.println(operators[14] + " -> " + greater);
        System.out.println(operators[15] + " -> " + pluses);
        System.out.println(operators[16] + " -> " + news);
        System.out.println(operators[17] + " -> " + ands);
        System.out.println(operators[18] + " -> " + ors);
        System.out.println(operators[19] + " -> " + fslash);
        System.out.println(operators[20] + " -> " + curly);
        System.out.println("SUM -> " + sum_operators);


        System.out.println("\n-- OPERANDS --");
        System.out.println(operands[0] + " -> " + xs);
        System.out.println(operands[1] + " -> " + ys);
        System.out.println(operands[2] + " -> " + is);
        System.out.println(operands[3] + " -> " + js);
        System.out.println("SUM -> " + sum_operands);


    }

    public void createSystemMetricsTable(File directory) throws IOException {

        getAllFiles(directory);
        searchOperations();


    }

}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<AnchorPane prefHeight="300.0" prefWidth="500.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="Controller.UsernameController">
   <children>
      <Pane layoutX="10.0" layoutY="10.0" prefHeight="300.0" prefWidth="500.0">
         <children>
            <VBox alignment="CENTER" prefHeight="300.0" prefWidth="500.0">
               <children>
                  <Label text="Software Similarity Program">
                     <font>
                        <Font size="29.0" />
                     </font>
                  </Label>
                  <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" spacing="10.0">
                     <children>
                        <Label text="Username:" />
                        <TextField fx:id="nameField" promptText="name" />
                     </children>
                  </HBox>
                  <Button fx:id="enter" mnemonicParsing="false" onAction="#toNext" text="Enter" />
               </children>
            </VBox>
         </children>
      </Pane>
   </children>
</AnchorPane>
package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UsernameController {

    public Button enter;
    public TextField nameField;
    private Stage stage;

    public void toNext() throws IOException {
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("../FXML/Filenames.fxml"));
        Parent root = (Parent) fxmloader.load();

        Scene scene = new Scene(root);

        FilenamesController passCont = fxmloader.getController();
        passCont.passStage(stage);

        stage.setScene(scene);

        stage.setTitle("Similarity Software Program");

        stage.show();
    }

    public void passStage(Stage stage){
        this.stage = stage;
    }



}


