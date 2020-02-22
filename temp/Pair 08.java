import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class controller {
    @FXML
    private GridPane correlationMatrix;
    private String path;

    public void setPath(String path){
        this.path = path;
    }

    @FXML
   public void initialize() throws IOException {
    }

    public void printTable() throws IOException {
        int row = 1;
        programSimilarityChecker test =  new programSimilarityChecker(path);
        ArrayList<StackPane> names = new ArrayList<>();
        names.add(new StackPane(new Text("Scores")));
        for (String name : test.crossCompare().keySet()) {
            names.add(new StackPane(new Text(name)));
        }
        StackPane[] nameList = new StackPane[names.size()];
        names.toArray(nameList);
        correlationMatrix.addRow(row++, nameList);
        for (Map.Entry<String, ArrayList<Double>> entry1 : test.crossCompare().entrySet()) {
            String name = entry1.getKey();
            ArrayList<Double> scores = entry1.getValue();
            ArrayList<Pane> scorePanes = new ArrayList<>();
            Pane namePane = new StackPane();
            namePane.getChildren().add(new Text(name));
            scorePanes.add(namePane);
            for (Double score : scores){
                Pane newPane = new StackPane();
                newPane.setBackground(new Background(new BackgroundFill(Color.color(score / 100, 1 - (score / 100), 0), null,null)));
                newPane.getChildren().add(new Text(String.format("%.2f", score)));
                scorePanes.add(newPane);
            }

            Pane[] scorePaneArray = new Pane[scorePanes.size()];
            scorePanes.toArray(scorePaneArray);
            correlationMatrix.addRow(row++, scorePaneArray);
        }
    }
}
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<AnchorPane prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="mainMenuController">
   <children>
      <Button fx:id="directory" layoutX="274.0" layoutY="260.0" mnemonicParsing="false" onAction="#buttonClicked" text="enter" />
      <TextField fx:id="pathDirectory" layoutX="119.0" layoutY="173.0" prefHeight="66.0" prefWidth="363.0" />
      <Text layoutX="119.0" layoutY="107.0" strokeType="OUTSIDE" strokeWidth="0.0" text="ChuaSolis Program Similarity Checker">
         <font>
            <Font size="22.0" />
         </font>
      </Text>
      <Text layoutX="277.0" layoutY="167.0" strokeType="OUTSIDE" strokeWidth="0.0" text="path">
         <font>
            <Font size="23.0" />
         </font>
      </Text>
   </children>
</AnchorPane>
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class mainMenuController {
    @FXML
    public TextField pathDirectory;
    public Button directory;
    @FXML
    public void buttonClicked() throws IOException {
        String dir = pathDirectory.getText();

        Stage insertionSortStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("similarityChecker.fxml"));
        Parent insertRoot = loader.load();
        controller correlationMatrixController = loader.getController();
        correlationMatrixController.setPath(dir);
        correlationMatrixController.printTable();
        insertionSortStage.setTitle("Similarity Checker");
        insertionSortStage.setScene(new Scene(insertRoot, 1000, 500));
        insertionSortStage.show();
    }
}
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class programSimilarityChecker {
    private String currentLine="";
    private HashMap<String, ArrayList<String>> storage = new HashMap<>();
    private String fileName;

    private ArrayList<String> toArrayList(File file) throws IOException {
        ArrayList<String> tmpStorage = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((currentLine = br.readLine()) != null) {
            currentLine = currentLine.replaceAll("\\s+", "");
            if (!tmpStorage.contains(currentLine)) {
                tmpStorage.add(currentLine);
            }
        }
        br.close();
        return tmpStorage;
    }
    public programSimilarityChecker(String pathName) throws IOException {
        File folder = new File(pathName);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            fileName = file.getName();
            if(file.isDirectory()) {
                try(Stream<Path> fileStream = Files.walk(file.toPath())){
                    ArrayList<String> majorLines = new ArrayList<>();
                    fileStream
                            .filter(Files::isRegularFile)
                            .forEach(path -> {
                                try {
                                    ArrayList<String> lines = toArrayList(path.toFile());
                                    majorLines.addAll(lines);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                    storage.put(fileName, majorLines);
                }
            }
            else if (file.isFile()) {
                if (fileName.endsWith(".txt") || fileName.endsWith(".TXT") || fileName.endsWith(".txt") || fileName.endsWith(".cpp") || fileName.endsWith(".txt") || fileName.endsWith(".java")) {
                    storage.put(fileName, toArrayList(file));
                }
            }
        }
    }
    HashMap<String, ArrayList<Double>> table = crossCompare(storage);

    public void printCorrelationMatrix() {
        for (Map.Entry<String, ArrayList<Double>> entry : table.entrySet()) {
            String name = entry.getKey();
            ArrayList<Double> scores = entry.getValue();
            System.out.printf("%15s\t", name);
            for (Double score : scores) {
                System.out.printf("%.2f\t", score);
            }
            System.out.println();
        }
    }
    public double compare(ArrayList<String> project1, ArrayList<String> project2){
        double countComparison = 0;
        for (String temp1 : project1) {
            for (String temp2 : project2) {
                if (temp1.equals(temp2)) {
                    countComparison++;
                }
            }
        }
        return (countComparison/Math.max(project1.size(), project2.size()) ) * 100;
    }

    public HashMap<String, ArrayList<Double>> crossCompare(){
        return crossCompare(storage);
    }

    private HashMap<String, ArrayList<Double>> crossCompare(HashMap<String, ArrayList<String>> projects){
        HashMap<String, ArrayList<Double>> comparisons = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : projects.entrySet()){
            String name = entry.getKey();
            ArrayList<String> lines = entry.getValue();
            ArrayList<Double> results = new ArrayList<>();
            for (ArrayList<String> other : projects.values()){
                results.add(compare(lines, other));
            }
            comparisons.putIfAbsent(name, results);
        }
        return comparisons;
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>

<ScrollPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller">
   <content>
      <GridPane id="correlationMatrix" fx:id="correlationMatrix" prefHeight="43.0" prefWidth="584.0" style="-fx-grid-lines-visible: true;">
        <columnConstraints>
          <ColumnConstraints minWidth="100.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
      </GridPane>
   </content>
</ScrollPane>
