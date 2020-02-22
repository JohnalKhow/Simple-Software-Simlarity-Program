// This is the initial version of my plagiarism checker program. 
// The method or algorithm that i used is to check whether a source code 
// was directly copy pasted from another code. Which means that each letter 
// in the first file would be in the exact same position in the second file. 

// I realize this approach is extremely flawed; therefore, I would be revising the program.

#include <iostream>
#include <string.h>

using namespace std;

int main(){
	
	char ch, file1[50] = "test_program1.cpp", file2[50] = "test_program2.cpp";
	string content1 = "",content2 = "", streak = "";
	FILE *fp;
	
	fp = fopen(file1,"r");
	
	while ((ch = fgetc(fp)) != EOF){
		content1 += ch;
	}
	cout << "First file:" << endl << endl << content1 << endl;
	
	fclose(fp);
	
	fp = fopen(file2,"r");
	
	while ((ch = fgetc(fp)) != EOF){
		content2 += ch;
	}
	cout << endl << "Second File: " << endl << endl << content2 << endl;
	
	fclose(fp);
	
	double equalcounter = 0;
	double notequalcounter = 0;
	
	int length;
	
	if (content1.length() > content2.length()){
		length = content1.length();
	}
	else {
		length = content2.length();
	}
	
	for (int i = 0; i < length; i++){
		if (content1[i] == content2[i]){
			equalcounter++;
		}
		else if (content1[i] != content2[i]){
			notequalcounter++;
		}
	}
	
	for (int i = 0; i < equalcounter; i++){
		streak = streak + content1[i];
	}
	
	cout << endl << "Longest similarity streak" << endl << endl << streak;
	
	double total = equalcounter + notequalcounter;
	
	double comparison = equalcounter / total * 100;
	
	cout << endl << endl << "Percentage Comparison:" << endl << comparison << "%" << endl; 
}
#include <iostream>
#include <string.h>
#include <dirent.h>

//This is the updated version of the Plagiarism Checker v2
//The changes here include the fix for the trapping for my .exe file
//Instead of having the exact file numbering in the list be hard coded, the file name instead will be used and be skipped over
//If the code should read the said name like in Line 60.

//The code also is arranged more neatly where the names of the code owners are seen in the left side of the screen.

//Furthermore, the comparison and printing part of the code has been turned into a function instead of having everything cluttered into
//The main() function.


using namespace std;

double comparison(string con1[], string con2[]){
	
	double equalcounter = 0;
	double notequalcounter = 0;
	
	for (int i = 0; i < 1000; i++){
		int g = equalcounter;
		if (con1[i] == ""){
			continue;
		}
		for (int j = 0; j < 1000; j++){
			if (con1[i] == con2[j]){
				equalcounter++;
				break;
			}
		}
		if (g == equalcounter){
			notequalcounter++;
		}
	}
	
	double total = equalcounter + notequalcounter;
	
	double compare = equalcounter / total * 100;
	
	return compare;
			
}

int main(){
	
	struct dirent *dc;
	string file[100] = "";
	int count = 0;
	
	DIR *de = opendir(".");

    while ((dc = readdir(de)) != NULL){
    	file[count] += dc->d_name; 
    	count++;
	}
    closedir(de); 
	
	char ch, file1[50], file2[50];
	FILE *fp;
	
	for (int i = 2; i < 39; i++){
		if (file[i] == "JM.exe"){
			continue;
		}

		else{
			printf("%16s",file[i].c_str());
		}
		for (int j = 2; j < 39; j++){
			if (file[j] == "JM.exe"){
				continue;
			}

			string content1[1000] = "", content2[1000] = "";
			
			strcpy(file1, file[i].c_str());
			
			fp = fopen(file1, "r");
			
			int c = 0;
			while ((ch = fgetc(fp)) != EOF){
				if (ch == '\n' || ch == '  '){
					c++;
					continue;
				}
				else{
					content1[c] += ch;
				}
			}
			
			fclose(fp);
			
			strcpy(file2, file[j].c_str());
			fp = fopen(file2,"r");
			c = 0;
			while ((ch = fgetc(fp)) != EOF){
				if (ch == '\n' || ch == '  '){
					c++;
					continue;
				}
				else{
					content2[c] += ch;
				}
			}
	
			fclose(fp);

			printf("%4.0f ",comparison(content1,content2));
		}
		cout << endl;
	}
}
// This is the revised version of my plagiarism checker program. It is better than the previous version as it now uses 
// a line checking algorithm rather than a letter based one. The program would check each 
// line in the first file and it would check whether or not the same line is present anywhere in the second file. 

// The program is also capable of calling files from a directory so that multiple files can be checked at once.

// I would also like to add that on 

// line 25: if (i == 16){

// and on 

// line 29: if (j == 16){

// are lines of code to make sure that the .exe or executable file which normally comes up after running a 
// Dev C++ file would be excluded in the checking. The number 16 is the exact placement of where 
// the executable file will be in the directory and it is specifically set to the specification of my directory.

#include <iostream>
#include <string.h>
#include <dirent.h>

using namespace std;

int main(){
	
	struct dirent *dc;
	string file[100] = "";
	int count = 0;
	
	DIR *de = opendir(".");

    while ((dc = readdir(de)) != NULL){
    	file[count] += dc->d_name; 
    	count++;
	}
    closedir(de); 
	
	char ch, file1[50], file2[50];
	FILE *fp;
	
	for (int i = 2; i < 38; i++){
		if (i == 16){
			continue;
		}
		for (int j = 2; j < 38; j++){
			if (j == 16){
				continue;
			}
			string content1[1000] = "", content2[1000] = "";
			
			strcpy(file1, file[i].c_str());
			
			fp = fopen(file1, "r");
			
			int c = 0;
			while ((ch = fgetc(fp)) != EOF){
				if (ch == '\n' || ch == '  '){
					c++;
					continue;
				}
				else{
					content1[c] += ch;
				}
			}
			
			fclose(fp);
			
			strcpy(file2, file[j].c_str());
			fp = fopen(file2,"r");
			c = 0;
			while ((ch = fgetc(fp)) != EOF){
				if (ch == '\n' || ch == '  '){
					c++;
					continue;
				}
				else{
					content2[c] += ch;
				}
			}
	
			fclose(fp);
			
			double equalcounter = 0;
			double notequalcounter = 0;
	
			for (int i = 0; i < 1000; i++){
				int g = equalcounter;
				if (content1[i] == ""){
					continue;
				}
				for (int j = 0; j < 100; j++){
					if (content1[i] == content2[j]){
						equalcounter++;
						break;
					}
				}
				if (g == equalcounter){
					notequalcounter++;
				}
			}
	
			double total = equalcounter + notequalcounter;
	
			double comparison = equalcounter / total * 100;
			
			printf("%4.0f ",comparison);
		}
		cout << endl;
	}
}
