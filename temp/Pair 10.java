import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Compare {


     String checkFiles(String file1, String file2) throws FileNotFoundException {
        File program1 = new File(file1);
        File program2 = new File(file2);


        Scanner sc1 = new Scanner(program1);
        Scanner sc2 = new Scanner(program2);

        float count=0;
        float similar=0;
        String string1;
        String string2;
        while (sc1.hasNext() && sc2.hasNext()){
            string1 = sc1.next();
            string2 = sc2.next();
            if(string1.equalsIgnoreCase(string2)){
                similar++;
            }
            count++;
        }
        return new DecimalFormat("#.##").format(similar/count *100);
    }
}

import java.io.File;
import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) throws FileNotFoundException {

        Compare compare = new Compare();
        System.out.println("Pair 1 has a similarity score of "+ compare.checkFiles("C:\\Users\\TJ\\Desktop\\Code Repositories\\test_program1.java",
                "C:\\Users\\TJ\\Desktop\\Code Repositories\\test_program2.java"));

        System.out.println("Pair 2 has a similarity score of "+compare.checkFiles("C:\\Users\\TJ\\Desktop\\Code Repositories\\test_program1.cpp",
                "C:\\Users\\TJ\\Desktop\\Code Repositories\\test_program2.cpp"));

        Student students[] = new Student[34];
        File directory = new File("C:\\Users\\TJ\\IdeaProjects\\Similarity_Checker\\Codes");
        File[] fList = directory.listFiles();

        for(int i=0; i< fList.length;i++){
            students[i] = new Student();
            students[i].setName(fList[i].getName());
            students[i].setStudentFile("C:\\Users\\TJ\\IdeaProjects\\Similarity_Checker\\Codes\\"+fList[i].getName());
        }

        System.out.println(compare.checkFiles(students[0].getStudentFile(), students[1].getStudentFile()));
        String matrix[][] = new String[34][35];

        //initializing the values of the correlation matrix
        for(int i=0;i<34;i++){
            matrix[i][0] = students[i].getName();
            for(int k=1;k<35;k++){
                matrix[i][k] = (compare.checkFiles(students[i].getStudentFile(),students[k-1].getStudentFile()));
            }
        }

        //header of the table
        System.out.format("%15s", " ");
        for(int i=0; i < students.length;i++){
            System.out.format("%15s", students[i].getName());
        }
        System.out.println();

        for(int i=0; i<34;i++){
            for(int k=0; k < 35; k++){
                System.out.format("%15s", matrix[i][k]);
                //System.out.print(matrix[i][k]+ "\t\t");
            }
            System.out.println();
        }



    }
}
import java.io.File;

public class Student {
    private String name;
    private String studentFile;

    public String getName() {
        return name;
    }
    public void setName(String studentName){
        int length;
        length = studentName.length();
        if(studentName.endsWith(".cpp")){
            name = studentName.substring(0,length-4);
        }
        else if(studentName.endsWith(".java")){
            name = studentName.substring(0,length-5);
        }
    }
    public String getStudentFile(){
        return studentFile;
    }

    public void setStudentFile(String path){
        studentFile = path;
    }
}

