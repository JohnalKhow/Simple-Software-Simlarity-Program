package Algorithms;

import java.text.DecimalFormat;

public class Compare {
    private static DecimalFormat two_point = new DecimalFormat("#,##0.00");


    public static void main(String[] args) {
        double [][]correlation_matrix;
        int count=0;
        Read file = new Read();
        Directory_checker dir= new Directory_checker();
        count = dir.count_files();
        correlation_matrix=file.read_files(".java");
        for(int i=0; i<count; i++){
            for(int j=0; j<count; j++){
                    System.out.print(two_point.format(correlation_matrix[i][j])+"\t");
                }
            System.out.println();
        }
    }
}
package GUI;

public class Controller {
}
package Algorithms;

import java.io.File;

public class Directory_checker {

    public static int count_files(){
            File directory=new File("Directory");
            int fileCount=directory.list().length;
        return fileCount;
    }
    public static File[] showFiles(String dir) {
        String dire="Directory";
        File[] files = new File(dire).listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
               showFiles(dir + "/" + files[i].getName());
            }
        }
        return files;
    }
}
<?import javafx.scene.layout.GridPane?>
<GridPane fx:controller="GUI.Controller"
          xmlns:fx="http://javafx.com/fxml" alignment="center" hgap="10" vgap="10">
</GridPane>
package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Read {
    public double[][] read_files(String file_ext) {
        String data1, data2;
        Directory_checker dir = new Directory_checker();
        File[] files = dir.showFiles("Directory");
        double [][] correlation_matrix= new double[files.length][files.length];
        double total = 0, similar = 0;
        try {
            for (int i = 0; i < files.length; i++) {
                for (int j = 0; j < files.length ; j++) {
                    Scanner first_cmp = new Scanner(files[i]);
                    Scanner second_cmp = new Scanner(files[j]);
                    while(first_cmp.hasNext() && second_cmp.hasNext()) {
                            data1 = first_cmp.nextLine();
                            data2 = second_cmp.nextLine();
                            total++;
                            if (data1.equals(data2)) {
                                similar++;
                            }
                        }
                    double percentage = (similar / total);
                    correlation_matrix[i][j]= percentage;
                    total=0;
                    similar=0;
                    first_cmp.close();
                    second_cmp.close();
                }
            }

        }
        catch(FileNotFoundException e) {
            System.out.println("FILE I/O EXCEPTION");
            e.printStackTrace();
        }
        return correlation_matrix;
    }
}
