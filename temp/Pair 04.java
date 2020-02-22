package sample;

import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Controller {

    public TextField developer;
    public TilePane t1;
    public TilePane t2;
    public TilePane t3;
    public TilePane t4;
    public TilePane t5;
    public TilePane t6;
    public TilePane t7;
    public TilePane t8;
    public TilePane t9;
    public TilePane t10;
    public TilePane t11;
    public TilePane t12;
    public TilePane t13;
    public TilePane t14;
    public TilePane t15;
    public TilePane t16;
    public TilePane t17;
    public TilePane t18;
    public TilePane t19;
    public TilePane t20;
    public TilePane t21;
    public TilePane t22;
    public TilePane t23;
    public TilePane t24;
    public TilePane t25;
    public TilePane t26;
    public TilePane t27;
    public TilePane t28;
    public TilePane t29;
    public TilePane t30;
    public TilePane t31;
    public TilePane t32;
    public TilePane t33;
    public TilePane t34;
    public TilePane t35;

    public void viewCorrelation() {
        float[] correlation = individualCorrelation(developer.getText());
    }

    public void viewFullMatrix() {
        Similator();

    }

    private void Similator() {
        float [][] correlation = new float[35][35];
        String[][] files = {{"11812249","Submissions/11812249_Module0/LBYCPD2_Module0/src/sample/Main.java"},
                {"11815027","Submissions/11815027_Mod0/src/main.java"},
                {"meh", "Submissions/act1/src/check.java"},
                {"meh", "Submissions/ACTIVITY 0/src/Compare.java"},
                {"Bayeta", "Submissions/BayetaExer0/CompareTwoFiles/src/CompareFile.java"},
                {"Lopez", "Submissions/Lopez_Module0_CodeComparison/Module0_CodeComparison/src/Main.java"},
                {"Magallanes", "Submissions/Magallanes_Module0_11828668/LBYCP2D_Module0_11828668/src/Similaritor.java"},
                {"Vicente", "Submissions/Mod1/src/Main.java"},
                {"meh", "Submissions/Module/prog.cpp"},
                {"meh", "Submissions/Module0/src/Compare.java"},
                {"meh", "Submissions/Module0 2/src/Compare.java"},
                {"Vasquez", "Submissions/Module0-Vasquez/src/Module0Vasquez.java"},
                {"Cabato", "Submissions/Module0_CABATO/src/Main.java"},
                {"meh", "Submissions/Module 0/src/PlagiarismChecker.java"},
                {"meh", "Submissions/Module 0 2/src/Main.java"},
                {"meh", "Submissions/Module 0 3/src/SoftwareSimilarity.java"},
                {"meh", "Submissions/Module 0 4/src/Main.java"},
                {"Portugal", "Submissions/MODULE 0 5/testmain.cpp"},
                {"Megino", "Submissions/ProgramComparison_Megino/programcomparison_Megino.cpp"},
                {"See", "Submissions/see/Main.java"},
                {"Contreras", "Submissions/Similarity_Checker/src/Compare.java"},
                {"Solis", "Submissions/Solis-Checker-Module0/Solis-Checker-Module0/src/programChecker.java"},
                {"meh", "Submissions/src/CompareCode.java"},
                {"Tupal", "Submissions/Submission/Similarity/src/Main.java"},
                {"Chan", "Submissions/CHAN-module0.cpp"},
                {"Chiu", "Submissions/ChiuPlagiarism.java"},
                {"Enghoy", "Submissions/Enghoy_Main.java"},
                {"Gabay", "Submissions/Gabay.0.cpp"},
                {"Llarenas", "Submissions/Llarenas_Main.java"},
                {"Panes", "Submissions/PanesSimilarityChecker.java"},
                {"Parco", "Submissions/Parco_main.cpp"},
                {"Semira", "Submissions/semira_M0.cpp"},
                {"Coteok", "Submissions/TeddyMoss.java"},
                {"Toro", "Submissions/Toro_Module_0.cpp"},
                {"Vhong", "Submissions/VHONG-11814888 - Module 0.cpp"}};

        for (int o = 0; o < files.length; o++) {
            for (int q = 0; q < files.length; q++) {
                String dev1[] = files[o];
                String dev2[] = files[q];
                String[] first = new String[1000];
                String[] second = new String[1000];
                String[] words1 = new String[1000];
                String[] words2 = new String[1000];
                int i = 0;
                int y = 0;
                int j = 0;

                //System.out.println(dev1[0]);
                File code1 = new File(dev1[1]);
                try (BufferedReader br = new BufferedReader(new FileReader(code1))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        first[i] = line;
                        i++;
                        String[] words = line.split("\\s+");
                        for (int x = 0; x < words.length; x++) {
                            words1[y] = words[x];
                            y++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //System.out.println(dev2[0]);
                y = 0;
                File code2 = new File(dev2[1]);
                try (BufferedReader br = new BufferedReader(new FileReader(code2))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        second[j] = line;
                        j++;
                        String[] words = line.split("\\s+");
                        for (int x = 0; x < words.length; x++) {
                            words2[y] = words[x];
                            y++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                float x = 0;
                for (int z = 0; z < i; z++) {
                    for (int n = 0; n < j; n++) {
                        if (first[z].equals(second[n])) {
                            x++;
                            break;
                        }
                    }
                }

                float perLine = (x / i);
                //System.out.print(String.format("%.2f", perLine) + "\t");
                correlation[o][q] = perLine;
            }
            //System.out.print("\n");
        }
    }

    private float[] individualCorrelation(String lastName) {
        float [] correlation = new float[34];
        int index = 0;
        String[] dev;
        String[][] files = {{"11812249","Submissions/11812249_Module0/LBYCPD2_Module0/src/sample/Main.java"},
                {"11815027","Submissions/11815027_Mod0/src/main.java"},
                {"meh", "Submissions/act1/src/check.java"},
                {"meh", "Submissions/ACTIVITY 0/src/Compare.java"},
                {"Bayeta", "Submissions/BayetaExer0/CompareTwoFiles/src/CompareFile.java"},
                {"Lopez", "Submissions/Lopez_Module0_CodeComparison/Module0_CodeComparison/src/Main.java"},
                {"Magallanes", "Submissions/Magallanes_Module0_11828668/LBYCP2D_Module0_11828668/src/Similaritor.java"},
                {"Vicente", "Submissions/Mod1/src/Main.java"},
                {"meh", "Submissions/Module/prog.cpp"},
                {"meh", "Submissions/Module0/src/Compare.java"},
                {"meh", "Submissions/Module0 2/src/Compare.java"},
                {"Vasquez", "Submissions/Module0-Vasquez/src/Module0Vasquez.java"},
                {"Cabato", "Submissions/Module0_CABATO/src/Main.java"},
                {"meh", "Submissions/Module 0/src/PlagiarismChecker.java"},
                {"meh", "Submissions/Module 0 2/src/Main.java"},
                {"meh", "Submissions/Module 0 3/src/SoftwareSimilarity.java"},
                {"meh", "Submissions/Module 0 4/src/Main.java"},
                {"Portugal", "Submissions/MODULE 0 5/testmain.cpp"},
                {"Megino", "Submissions/ProgramComparison_Megino/programcomparison_Megino.cpp"},
                {"See", "Submissions/see/Main.java"},
                {"Contreras", "Submissions/Similarity_Checker/src/Compare.java"},
                {"Solis", "Submissions/Solis-Checker-Module0/Solis-Checker-Module0/src/programChecker.java"},
                {"meh", "Submissions/src/CompareCode.java"},
                {"Tupal", "Submissions/Submission/Similarity/src/Main.java"},
                {"Chan", "Submissions/CHAN-module0.cpp"},
                {"Chiu", "Submissions/ChiuPlagiarism.java"},
                {"Enghoy", "Submissions/Enghoy_Main.java"},
                {"Gabay", "Submissions/Gabay.0.cpp"},
                {"Llarenas", "Submissions/Llarenas_Main.java"},
                {"Panes", "Submissions/PanesSimilarityChecker.java"},
                {"Parco", "Submissions/Parco_main.cpp"},
                {"Semira", "Submissions/semira_M0.cpp"},
                {"Coteok", "Submissions/TeddyMoss.java"},
                {"Toro", "Submissions/Toro_Module_0.cpp"},
                {"Vhong", "Submissions/VHONG-11814888 - Module 0.cpp"}};

        for (int i = 0; i < files.length; i++) {
            dev = files[i];
            if (dev[0].equals(lastName)) {
                index = i;
            }
        }

        for (int o = 0; o < files.length; o++) {
            String[] dev2 = files[o];
            String[] first = new String[1000];
            String[] words1 = new String[1000];
            String[] second = new String[1000];
            String[] words2 = new String[1000];
            int i = 0;
            int y = 0;
            int j = 0;

            dev = files[index];
            File code1 = new File(dev[1]);
            try (BufferedReader br = new BufferedReader(new FileReader(code1))) {
                String line;
                while ((line = br.readLine()) != null) {
                    first[i] = line;
                    i++;
                    String[] words = line.split("\\s+");
                    for (int x = 0; x < words.length; x++) {
                        words1[y] = words[x];
                        y++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            File code2 = new File(dev2[1]);
            try (BufferedReader br = new BufferedReader(new FileReader(code2))) {
                String line;
                while ((line = br.readLine()) != null) {
                    second[j] = line;
                    j++;
                    String[] words = line.split("\\s+");
                    for (int x = 0; x < words.length; x++) {
                        words2[y] = words[x];
                        y++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            float x = 0;
            for (int z = 0; z < i; z++) {
                for (int n = 0; n < j; n++) {
                    if (first[z].equals(second[n])) {
                        x++;
                        break;
                    }
                }
            }
            float perLine = (x / i);
            correlation[o] = perLine;
        }
        return correlation;
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
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("Correlation Matrix");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Metric
{
    public static String filename;

    public static ArrayList<String> reorderVariables(ArrayList<String> variables)
    {
        int[] lengths = new int[variables.size()];
        for(int i=0;i<variables.size();i++)
        {
            lengths[i] = variables.get(i).length();
        }
        for(int i=0;i<lengths.length;i++)
        {
            for(int j=i+1;j<lengths.length;j++)
            {
                if(lengths[i] < lengths[j])
                {
                    int temp = lengths[i];
                    lengths[i] = lengths[j];
                    lengths[j] = temp;

                    String tmp_var = variables.get(i);
                    variables.set(i,variables.get(j));
                    variables.set(j,tmp_var);
                }
            }
        }
        return variables;
    }
    public static ArrayList<String> extractConstants(String line) // extract constants from string
    {
        boolean continueFlag = false;
        ArrayList<String> extracted = new ArrayList<String>();
        String temp = "";
        for(int i=0;i<line.length();i++)
        {
            if(line.charAt(i) >= '0' && line.charAt(i) <= '9')
            {
                if(!continueFlag)
                    continueFlag = !continueFlag;
                temp = temp + line.charAt(i) + "";
            }
            else
            {
                if(continueFlag)
                {
                    extracted.add(temp);
                    temp = "";
                    continueFlag = !continueFlag;
                }
            }
        }
        return extracted;
    }
    public static Map<String,Integer> getUniqueCount(ArrayList<String> list)
    {
        Map<String,Integer> uniqueList = new HashMap<String,Integer>();
        for(int i=0;i<list.size();i++)
        {
            String s = list.get(i);
            if(!uniqueList.containsKey(s))
            {
                int count = 0;
                for(int j=0;j<list.size();j++)
                {
                    if(list.get(j).equals(s))
                        count++;
                }
                uniqueList.put(s, count);
            }
        }
        return uniqueList;
    }
    public static void displayMetrics(int N1,int N2,int n1,int n2)
    {
        int N,n;
        float V,D,L,E,T,B;

        N = N1+N2;
        n = n1+n2;
        V = N * (float)( Math.log(n) / Math.log(2));
        D = (n1/2)*(N2/n2);
        L = 1/D;
        E = V*D;
        T = E/18;
        B = (float)(Math.pow(E, 2/3)/3000);

        System.out.println("\t[N] Program Length      : "+N);
        System.out.println("\t[n] Vocabulary Size     : "+n);
        System.out.println("\t[V] Program Volume      : "+V);
        System.out.println("\t[D] Difficulty          : "+D);
        System.out.println("\t[K] Program Level       : "+L);
        System.out.println("\t[E] Effort to implement : "+E);
        System.out.print("\t[T] Time to implement   : ");
        System.out.format("%-10.5f%n", T);
        System.out.print("\t[B] # of delivered bugs : ");
        System.out.format("%-10.5f%n\n", B);

    }
    public static void main(String[] args)
    {
        try
        {
            String[] keywords = { "scanf","printf","main","static" }; 	// datatypes shouldnt be added to keywords
            String[] datatypes = { "int","float","double","char"};
            ArrayList<String> operators = new ArrayList<String>();
            ArrayList<String> operands = new ArrayList<String>();
            ArrayList<String> variables = new ArrayList<String>();

            int operatorCount = 0, operandCount = 0;
            boolean skipFlag = false;
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;

            while((line = reader.readLine()) != null)
            {
                line = line.trim();
                for(String keyword : keywords)
                {
                    if(line.startsWith(keyword))
                    {
                        line = line.substring(0+keyword.length());
                        operators.add(keyword);
                        operatorCount++;
                    }
                }
                for(String datatype : datatypes)
                {
                    if(line.startsWith(datatype))
                    {
                        operators.add(datatype);
                        operatorCount++;
                        int index = line.indexOf(datatype);
                        line = line.substring(index+datatype.length(),line.length()-1);  // -1 to ignore the semicolon
                        String[] vars = line.split(",");
                        for(String v : vars)
                        {
                            v = v.trim();
                            variables.add(v);
                        }
                    }
                }
                variables = reorderVariables(variables);  // very important !
                skipFlag = false;
                for(int i=0;i<line.length();i++)
                {
                    if(line.charAt(i) >= 'A' && line.charAt(i) <= 'Z' || line.charAt(i) >= 'a' && line.charAt(i) <= 'z' || line.charAt(i) >= '0' && line.charAt(i) <= '9' || line.charAt(i) == ' ' || line.charAt(i) == ',' || line.charAt(i)==';' || line.charAt(i) == '(' || line.charAt(i) == '{')
                    {
                    }
                    else if(line.charAt(i) == ')'  )
                    {
                        if(skipFlag == false)
                        {
                            operatorCount++;
                            operators.add("()");
                        }
                    }
                    else if(line.charAt(i) == '}'  )
                    {
                        if(skipFlag == false)
                        {
                            operatorCount++;
                            operators.add("{}");
                        }
                    }
                    else if(line.charAt(i) == '"')   // for detecting double quotes
                    {
                        skipFlag = !skipFlag;
                        if(skipFlag)
                            operandCount++;
                        else
                        {
                            int startIndex = line.indexOf("\"");
                            int endIndex = line.lastIndexOf("\"");
                            operands.add(line.substring(startIndex,endIndex+1));
                        }
                    }
                    else
                    {
                        if(!skipFlag)
                        {
                            operators.add(line.charAt(i)+"");
                            operatorCount++;
                        }
                    }
                }
                // removing string literals from line if any
                if(line.contains("\""))
                {
                    int startIndex = line.indexOf("\"");
                    int endIndex = line.lastIndexOf("\"");
                    line = line.substring(0, startIndex) + line.substring(endIndex+1);
                }
                for(String variable : variables)
                {
                    while(line.contains(variable))
                    {
                        int index = line.indexOf(variable);
                        line = line.substring(0, index) + line.substring(index+variable.length(), line.length());
                        operands.add(variable);
                        operandCount++;
                    }
                }
                // checking for constants
                operands.addAll(extractConstants(line));
            }
            Map<String,Integer> uniqueOperators = Metric.getUniqueCount(operators);
            Map<String,Integer> uniqueOperands = Metric.getUniqueCount(operands);
            System.out.print(operators.size() + "\t" + operands.size() + "\t" + uniqueOperators.size() + "\t" + uniqueOperands.size());

            displayMetrics(operators.size(),operands.size(),uniqueOperators.size(),uniqueOperands.size());
            reader.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>


<GridPane alignment="center" hgap="10" vgap="10" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/11.0.1" fx:controller="sample.Controller">
   <columnConstraints>
      <ColumnConstraints />
   </columnConstraints>
   <rowConstraints>
      <RowConstraints />
   </rowConstraints>
   <children>
      <AnchorPane prefHeight="699.0" prefWidth="760.0">
         <children>
            <GridPane fx:id="gridPane" layoutX="23.0" layoutY="65.0" prefHeight="614.0" prefWidth="655.0">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="148.0" minWidth="10.0" />
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="145.0" minWidth="10.0" />
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
            <Button layoutX="626.0" layoutY="14.0" mnemonicParsing="false" prefHeight="27.0" prefWidth="120.0" text="View Full Matrix" />
         </children>
      </AnchorPane>
   </children>
</GridPane>
package sample;
import java.io.*;

public class Similator{
    public static void main (String [] args){
        float [][] correlation = new float[36][36];
        String[][] files = {{"Go, J","Submissions/11812249_Module0/LBYCPD2_Module0/src/sample/Main.java"},
                {"Portugal","Submissions/11815027_Mod0/src/main.java"},
                {"Chua", "Submissions/act1/src/check.java"},
                {"Khow", "Submissions/ACTIVITY 0/src/Compare.java"},
                {"Bayeta", "Submissions/BayetaExer0/CompareTwoFiles/src/CompareFile.java"},
                {"Lopez", "Submissions/Lopez_Module0_CodeComparison/Module0_CodeComparison/src/Main.java"},
                {"Magallanes", "Submissions/Magallanes_Module0_11828668/LBYCP2D_Module0_11828668/src/Similaritor.java"},
                {"Vicente", "Submissions/Mod1/src/Main.java"},
                {"Magcamit", "Submissions/Module/prog.cpp"},
                {"Ongsitco", "Submissions/Module0/src/Compare.java"},
                {"Regacho", "Submissions/Module0_Regacho/main.cpp"},
                {"Vasquez", "Submissions/Module0-Vasquez/src/Module0Vasquez.java"},
                {"Cabato", "Submissions/Module0_CABATO/src/Main.java"},
                {"Bombita", "Submissions/Module 0/src/PlagiarismChecker.java"},
                {"Go, M", "Submissions/Module 0 2/src/Main.java"},
                {"Sabularse", "Submissions/Module 0 3/src/SoftwareSimilarity.java"},
                {"Antoc", "Submissions/Module 0 4/src/Main.java"},
                {"Co", "Submissions/malcolm_src/FileToString.java"},
                {"Caoile", "Submissions/MODULE 0 5/testmain.cpp"},
                {"Megino", "Submissions/ProgramComparison_Megino/programcomparison_Megino.cpp"},
                {"See", "Submissions/see/Main.java"},
                {"Contreras", "Submissions/Similarity_Checker/src/Compare.java"},
                {"Solis", "Submissions/Solis-Checker-Module0/Solis-Checker-Module0/src/programChecker.java"},
                {"Tiu", "Submissions/src/CompareCode.java"},
                {"Tupal", "Submissions/Submission/Similarity/src/Main.java"},
                {"Chan", "Submissions/CHAN-module0.cpp"},
                {"Chiu", "Submissions/ChiuPlagiarism.java"},
                {"Enghoy", "Submissions/Enghoy_Main.java"},
                {"Gabay", "Submissions/Gabay.0.cpp"},
                {"Llarenas", "Submissions/Llarenas_Main.java"},
                {"Panes", "Submissions/PanesSimilarityChecker.java"},
                {"Parco", "Submissions/Parco_main.cpp"},
                {"Semira", "Submissions/semira_M0.cpp"},
                {"Coteok", "Submissions/TeddyMoss.java"},
                {"Toro", "Submissions/Toro_Module_0.cpp"},
                {"Vhong", "Submissions/VHONG-11814888 - Module 0.cpp"}};

        for (int o = 0; o < files.length; o++) {
            for (int q = 0; q < files.length; q++) {
                String dev1[] = files[o];
                String dev2[] = files[q];
                String[] first = new String[1000];
                String[] second = new String[1000];
                String[] words1 = new String[1000];
                String[] words2 = new String[1000];
                int i = 0;
                int y = 0;
                int j = 0;

                File code1 = new File(dev1[1]);
                try (BufferedReader br = new BufferedReader(new FileReader(code1))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        first[i] = line;
                        i++;
                        String[] words = line.split("\\s+");
                        for (int x = 0; x < words.length; x++) {
                            words1[y] = words[x];
                            y++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Metric.filename = dev1[1];
                Metric.main(args);

                y = 0;
                File code2 = new File(dev2[1]);
                try (BufferedReader br = new BufferedReader(new FileReader(code2))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        second[j] = line;
                        j++;
                        String[] words = line.split("\\s+");
                        for (int x = 0; x < words.length; x++) {
                            words2[y] = words[x];
                            y++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Metric.filename = dev2[1];
                Metric.main(args);

                float x = 0;
                for (int z = 0; z < i; z++) {
                    for (int n = 0; n < j; n++) {
                        if (first[z].equals(second[n])) {
                            x++;
                            break;
                        }
                    }
                }

                float perLine = (x / i);
                System.out.print(String.format("%.2f", perLine) + "\t");
                correlation[o][q] = perLine;
            }
            System.out.print("\n");
        }
        System.out.println("\n# of Operators | # of Operands | # of unique Oprts | # of unique Opnds | Program Length | Vocabulary Size | " +
                "Program Volume | Difficulty | Program Level | Effort to Implement | Time to implement | # of delivered bugs ");

    }
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.TilePane?>

<AnchorPane prefHeight="699.0" prefWidth="760.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="sample.Controller">
   <children>
      <TextField layoutX="14.0" layoutY="14.0" prefHeight="27.0" prefWidth="472.0" promptText="Enter Developer Name" />
      <Button layoutX="626.0" layoutY="14.0" mnemonicParsing="false" prefHeight="27.0" prefWidth="120.0" text="View Full Matrix" />
      <Button layoutX="497.0" layoutY="14.0" mnemonicParsing="false" prefHeight="27.0" prefWidth="120.0" text="View Correlation" />
      <TilePane fx:id="t1" layoutX="32.0" layoutY="88.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t6" layoutX="32.0" layoutY="168.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t11" layoutX="32.0" layoutY="248.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t16" layoutX="32.0" layoutY="328.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t21" layoutX="32.0" layoutY="408.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t26" layoutX="32.0" layoutY="488.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t31" layoutX="32.0" layoutY="568.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t2" layoutX="159.0" layoutY="88.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t7" layoutX="159.0" layoutY="168.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t12" layoutX="159.0" layoutY="248.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t17" layoutX="159.0" layoutY="328.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t22" layoutX="159.0" layoutY="408.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t27" layoutX="159.0" layoutY="488.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t32" layoutX="159.0" layoutY="568.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t3" layoutX="286.0" layoutY="88.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t8" layoutX="286.0" layoutY="168.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t13" layoutX="286.0" layoutY="248.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t18" layoutX="286.0" layoutY="328.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t23" layoutX="286.0" layoutY="408.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t28" layoutX="286.0" layoutY="488.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t33" layoutX="286.0" layoutY="568.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t4" layoutX="413.0" layoutY="88.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t9" layoutX="413.0" layoutY="168.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t14" layoutX="413.0" layoutY="248.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t19" layoutX="413.0" layoutY="328.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t24" layoutX="413.0" layoutY="408.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t29" layoutX="413.0" layoutY="488.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t34" layoutX="413.0" layoutY="568.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t5" layoutX="540.0" layoutY="88.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t10" layoutX="540.0" layoutY="168.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t15" layoutX="540.0" layoutY="248.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t20" layoutX="540.0" layoutY="328.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t25" layoutX="540.0" layoutY="408.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t30" layoutX="540.0" layoutY="488.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
      <TilePane fx:id="t35" layoutX="540.0" layoutY="568.0" prefHeight="69.0" prefWidth="114.0" style="-fx-background-color: grey;" />
   </children>
</AnchorPane>
