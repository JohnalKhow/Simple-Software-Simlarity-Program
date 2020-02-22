package CodeBusterClasses;

import java.io.IOException;
import java.util.ArrayList;

public class CompareClass {

    //this class is now the integration of the classes FingerPrint, GetCharacters, HashKgram, and K-Gram
    //input: filepath of the two files to be compared (as a string)
    //Output: similarity score (out of 100)

    String filepath1;
    String filepath2;
    float SimilarityScore;


    public CompareClass(String filepath1, String filepath2) throws IOException{
        this.filepath1 = filepath1;
        this.filepath2 = filepath2;
        CompareTwoFiles();
    }

    private void CompareTwoFiles() throws IOException {
        //this is for the first file
        GetCharacters prog1Char = new GetCharacters(filepath1);
        KGram prog1KGram = new KGram(prog1Char.CharactersFromFile());
        HashKGram prog1HashKGram = new HashKGram(prog1KGram.ReturnProcessedKGram());
        FingerPrint prog1FingerPrint = new FingerPrint(prog1HashKGram.ReturnProcessedHashes());
        ArrayList<Integer> fingerprintProg1= prog1FingerPrint.ReturnFingerPrint();

        //this is for the second file
        GetCharacters prog2Char = new GetCharacters(filepath2);
        KGram prog2KGram = new KGram(prog2Char.CharactersFromFile());
        HashKGram prog2HashKGram = new HashKGram(prog2KGram.ReturnProcessedKGram());
        FingerPrint prog2FingerPrint = new FingerPrint(prog2HashKGram.ReturnProcessedHashes());
        ArrayList<Integer> fingerprintProg2= prog2FingerPrint.ReturnFingerPrint();

        //this is for jaccard index. Jaccard index will be the basis of similarity
        float numSimilarity = 0;
        boolean isSimilarEncounter = false;
        int sizeFingerprint1 = fingerprintProg1.size();
        int sizeFingerprint2 = fingerprintProg2.size();

        for (Integer integer : fingerprintProg1) {

            for (Integer value : fingerprintProg2) {


                if ((integer.intValue() == value.intValue()) && !isSimilarEncounter) {
                    numSimilarity++;
                    isSimilarEncounter = true;
                }

            }


            isSimilarEncounter = false;
        }

        SimilarityScore = (numSimilarity / (sizeFingerprint1 + sizeFingerprint2 - numSimilarity) ) * 100 ;

    }

    public float getSimilarityScore() {
        return SimilarityScore;
    }

}
package CodeBusterClasses;

import java.util.ArrayList;

public class FingerPrint {

    //this class will get hashes of the inputted so that it will determine the
    //fingerprint of the sourcode for the comparison
    // an implementation of winnowing alogrithm

    private ArrayList<Integer> HashesKGram;
    private ArrayList<Integer> fingerPrint;
    private final int windowSize = 12;
    private final int p =50;

    public FingerPrint(){

    }

    public FingerPrint(ArrayList<Integer> HashesKGram){
        this.HashesKGram = HashesKGram;
        FindFingerPrints();
    }

    public ArrayList<Integer> ReturnFingerPrint(){return fingerPrint;}

    private void FindFingerPrints(){
        int tempWindow[];

        int location = -1;//it will save the index of the minimum hash of the current window

        int minLocation = -1; // it will save the indexx of the last selected fingerprint

        fingerPrint = new ArrayList<>();

        for (int i = 0; i < HashesKGram.size() - windowSize; i++) {

            tempWindow = new int[windowSize];

            for(int j =0 ; j<windowSize; j++){
                //this willl build the window
                tempWindow[j] = HashesKGram.get(i+j);
            }

            //it will get the index of the minimum hash based on the formed window
            location = i + findMinIndex(tempWindow);

            if(location != minLocation){
                //it will check if the minmum hash is not selected
                fingerPrint.add(findMin(tempWindow));
                minLocation = location;
            }

        }

    }

    public Integer findMin(int[] arr){
        int tempMin = 1000000;

        for (int i = 0; i < arr.length; i++) {
            if(tempMin > arr[i]){
                tempMin = arr[i];
            }
        }

        return tempMin;
    }

    public Integer findMinIndex(int[] arr){
        int tempMin = 10000;
        int index = -1;

        for (int i = 0; i < arr.length; i++) {
            if(tempMin > arr[i]){
                tempMin = arr[i];
                index = i;
            }
        }

        return index;
    }

}
package CodeBusterClasses;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetCharacters {

    private FileReader file;
    private String filepath;
    private ArrayList<Character> charactersFromFile;



    public GetCharacters(String filepath) throws IOException {
        file = new FileReader(filepath);
        this.filepath = filepath;
    }

    public void reset() throws IOException{
        //this will reset the reader of the file
        file = new FileReader(filepath);
    }

    public ArrayList<Character> CharactersFromFile() throws IOException{
       //this function will return an array of characters which is contained in the file
        //this function will exclude characters such as spaces, tabs and next line

        int tempInt = 0;
        charactersFromFile = new ArrayList<>();
        while(tempInt != -1){

            tempInt = file.read();

            if(tempInt != 32 && tempInt != 13 && tempInt != 10) charactersFromFile.add((char)tempInt);
        }
        reset();
        return charactersFromFile;
    }

    public Integer TotalCharacterFile () throws IOException{
        //this function will return the sum of the ascii values in the text file
        //this function will exclude characters such as spaces, tabs and next line

        int tempInt = 0;
        int sum = 0;

        while(tempInt != -1){

            tempInt = file.read();

            if(tempInt != 32 && tempInt != 13 && tempInt != 10) sum += tempInt;
        }
        reset();
        return sum;
    }

    public void setFilepath(String filepath) throws IOException {
        this.filepath = filepath;
        reset();
    }

    public String getFilepath() {
        return filepath;
    }

}
package CodeBusterClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetLines {

    private BufferedReader progLines;

    public GetLines(String filepath) throws IOException {
        progLines= new BufferedReader(new FileReader(filepath));
    }

    public Integer ReturnNumLines() throws IOException{
        //this function will return the number of lines in the given program file

        int line = 0 ;

        while(progLines.readLine() != null) line++;
        return line;
    }
}
package CodeBusterClasses;

import java.util.ArrayList;

public class HashKGram {
    //this is for hashing of the k-grams

    private ArrayList<String> kGrams;
    private ArrayList<Integer> hashKGrams;
    private final int prime = 2;// this is the b in the formula

    public HashKGram(ArrayList<String> kGrams){
        this.kGrams = kGrams;
        ProcessHashKGrams();
    }


    private Integer HashOneString(String string){

        int k = string.length();
        int sumComputed = 0;

        for (int i = 0; i < string.length() ; i++) {
            k --;
            sumComputed += string.charAt(i) * Math.pow(prime,k);


        }

        return sumComputed;
    }

    private void  ProcessHashKGrams(){
        hashKGrams = new ArrayList<>();

        int k = kGrams.get(0).length();

        hashKGrams.add(HashOneString(kGrams.get(0)));

        for (int i = 1; i < kGrams.size(); i++) {
            hashKGrams.add( (int)((hashKGrams.get(i-1)- (kGrams.get(i-1).charAt(0) * Math.pow(prime,k-1))) * prime + kGrams.get(i).charAt(k-1)) );
        }

    }

    public ArrayList<Integer> ReturnProcessedHashes(){ return hashKGrams;}

    public Integer getHash(int index){
        return hashKGrams.get(index);
    }

}
package CodeBusterClasses;

import java.util.ArrayList;

public class KGram {

    //this function will produce k-grams which each substring having length 7
    private  ArrayList<String> kGrams;
    private ArrayList<Character> streamOfChar;
    private final int kLength = 7;

    public KGram(ArrayList<Character> input){
        streamOfChar = input;
        ProcessKGram();
    }

    public void setStreamOfChar(ArrayList<Character> streamOfChar) {
        this.streamOfChar = streamOfChar;
    }

    public ArrayList<String> ReturnProcessedKGram(){
        return kGrams;
    }

    private void ProcessKGram(){
        kGrams = new ArrayList<>();
        StringBuilder tempString;

        for(int i=0; i<=streamOfChar.size() - kLength; i++){
            tempString = new StringBuilder();

            for(int j = 0 ; j<kLength; j++){
                tempString.append(streamOfChar.get(i+j));
            }

            kGrams.add(tempString.toString());
        }
    }

}
import CodeBusterClasses.*;
import java.io.*;

public class Similaritor {

    private static void Print2DArray(float array[][], String arraySecond[], int length){
        //length is the size of the matrix

        System.out.print("\t\t\t");
        for (String name : arraySecond) {
            System.out.printf("%-11s ", name);
        }
        System.out.println();

        for (int i = 0; i < length; i++) {

            System.out.printf("%-10s \t",arraySecond[i]);

            for (int j = 0 ; j < length ; j++){

                System.out.printf("%7.2f \t",array[i][j]);

            }

            System.out.println();

        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here

        float matrixSimilarity[][];
        String names[];
        File folder = new File("./src/Programs_To_Compare");
        matrixSimilarity = new float[folder.listFiles().length][folder.listFiles().length];
        names = new String[folder.listFiles().length];

        float highestScore = 0;
        int jIndex = 0;
        int iIndex =0;

        System.out.println("File names inside the directory:");
        for (int i = 0; i < folder.listFiles().length; i++) {
            String temp [] = folder.listFiles()[i].getName().split("_");
            names[i] = temp[0];
            System.out.println(names[i] + " : " + folder.listFiles()[i].getName());
        }

        System.out.println("\n\nPairs with more than 90 as score:");
        for (int i = 0; i < folder.listFiles().length ; i++) {

            for (int j = 0; j<folder.listFiles().length;j++){

                matrixSimilarity[i][j] = new CompareClass(folder.listFiles()[i].getAbsolutePath(),folder.listFiles()[j].getAbsolutePath()).getSimilarityScore();

                if(matrixSimilarity[i][j] > 90 && matrixSimilarity[i][j] < 100){
                    System.out.printf("%s and %s with a score of %.2f\n", names[i],names[j],matrixSimilarity[i][j] );

                }

                if(matrixSimilarity[i][j] >highestScore && matrixSimilarity[i][j] != 100){
                    highestScore = matrixSimilarity[i][j] ;
                    jIndex = j;
                    iIndex = i;
                }

            }
        }

        System.out.printf("\n\n\nPair with the highest score is %s and %s with the score of %.2f",  names[iIndex], names[jIndex], matrixSimilarity[iIndex][jIndex]);

        System.out.println("\n\nMatrix:");

        Print2DArray(matrixSimilarity, names, folder.listFiles().length);

    }

}
