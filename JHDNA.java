// Programmer: Jonathan Hill
// Class: CS 141
// Date: 11/23/21
// Assingment: Lab 6 - DNA
// This program will take an input file that has a list of nucleotides 
// in the form of a string and break those nucleotides down in the following way. 
// First, the program will give the region name and the string of nucleotides.
// Next, the program will break the nucleotides down into nuc. Counts and print that 
// information out in an array. The program will also calculate the total mass 
// of all 4 nuc. counts in the nucleotide and calculate the percentage of the mass compared 
// to the total mass of the nucleotide and print that information out in an array. 
// Finally, the program will break the nucleotides down into triplets called condons, print those
// in an array and tell the user whether our not the nucleotide is a protein.

import java.io.*;    // for File
import java.util.*;  // for Scanner

public class JHDNA { // Start of DNA2 class
   
    // start of main method
    public static void main(String[] args) 
        throws FileNotFoundException {  
        String inputfile = ""; // This string represents the inputfile that the user will enter
        String outputfile = ""; // This string represents the outputfile that the user will enter
        String option = "";  // This string represent user input
        Scanner userInput = new Scanner(System.in);
        int A = 0; // A nuc
        int T = 0; // T nuc
        int G = 0; // G nuc
        int C = 0; // C nuc
        int x = 0;  // number of characters in a string
        char a = 'a'; // intialize to use the charAt() method
        introduction();   
        System.out.printf("%s" , "Input file name: ");
        inputfile = userInput.nextLine();
        System.out.printf("%s" , "Output file name: ");
        outputfile = userInput.nextLine();
        System.out.println();
        PrintStream output = new PrintStream(new File(outputfile)); // output to a txt file
        Scanner input = new Scanner(new File(inputfile));  // pulls in a txt file
        while(input.hasNextLine()) { // start of while loop thats reading in the file two lines at a time.
            String Line1 = input.nextLine();
            String Line2 = input.nextLine();
            x = Line2.length();
            String nucleotides = Line2.toUpperCase();
            String nucleotidesWithOutHyphen = nucleotides.replace("-","");
            printLineOneTwo(Line1,nucleotidesWithOutHyphen, output);
            int [] nucCount = nucCount(input,nucleotides,x,a,A,C,G,T);  
            output.println("Nuc. Counts:  " + Arrays.toString(nucCount)); 
            double [] massCount = massCount(nucCount,A,C,G,T,x,nucleotides);
            double sum = sum(massCount);
            double [] massPercentage = massPercentage(massCount, sum);
            output.println("Total Mass%:  " + Arrays.toString(massPercentage) + " " + "of" + " " + sum);
            String [] condons = condons(x, nucleotidesWithOutHyphen);
            output.println("Condons List: " + Arrays.toString(condons));
            isProtein(condons, nucleotides, massCount, sum, output);
     }      // end of while loop
 }      // end of main method
     
    
    // introduces user to program 
    public static void introduction() { 
        System.out.printf("%s%n" , "This program reports information about DNA");
        System.out.printf("%s%n%n" , "nucleotide sequences that may encode proteins.");
    } // end of introduction method
     
    
    // prints first two lines from a file and out puts to another file
    public static void printLineOneTwo(String Line1, String nucleotides, PrintStream output) {
         output.println("Region Name:  " + Line1);
         output.println("Nucleotides:  " + nucleotides);
    } // end of printLineOneTwo method
     
    
    // Start of nucCount method.  Counts up the individual nucs. excluding '-' 
    public static int[] nucCount(Scanner input, String nucleotides, int x, char a, int A, int C, int G, int T) {
        for(int i = 0; i < x; i++) {
            int [] nucCount = new int [4];
            a = nucleotides.charAt(i);
            if (a =='A') {
                A++;
            } else if (a == 'C') {
                C++;
            } else if (a == 'G') {
                G++;
            } else if (a == 'T') {
                T++;
            }    // end of if/else           
        } // end of forloop
        int [] nucCount = new int [4];
        nucCount [0] = A;
        nucCount [1] = C;
        nucCount [2] = G;
        nucCount [3] = T;   
        return nucCount;     
    }  // end of nucCount method
      
    
    // Start of massCount method.  Calculates individual masses of each nuc count and '-'.
    public static double[] massCount(int [] nucCount, int A, int C, int G, int T, int x, String nucleotides) {
        double junk = 0.0;
        char a ='a';
        double [] massCount = new double [5];
        for (int i = 0; i < x; i++) {
            a = nucleotides.charAt(i);
            if  (a == '-') {
                junk++;
                massCount [4] = junk * 100.00;
            } // end of if/else
        } // end of forloop
       
        massCount [0] =  nucCount[0] * 135.128;
        massCount [1] = nucCount[1] * 111.103;
        massCount [2] = nucCount[2] * 151.128;
        massCount [3] = nucCount[3] * 125.107;
        return massCount;
    } // end of massCount method.
         
    
    // Start of sum method.  Adds up total mass of a nucleotide including '-'
    public static double sum(double [] massCount) {
        double sum = 0;
        for(int i = 0; i < 5; i++) {
            sum = sum + massCount[i];
        } // end of forloop
        sum = Math.round(sum * 10.0) / 10.0;
        return sum;
    } // end of sum method
      
    
    // Start of massPercentage method.  Calculates mass percentage of individual 
    // nucs compare to total mass of the nucleotide
    public static double [] massPercentage(double [] massCount, double sum) {   
        int i= 4;
        double [] massPercentage = new double [i];
            for(i = 0; i < 4; i++) {
                massPercentage[i] = massCount[i] / sum * 100;
                massPercentage[i] = Math.round(massPercentage[i] * 10.0) / 10.0;
            } // end of forloop
            return massPercentage;
    } // end of massPercentage method
       
    
    // Start of condons method.  Breaks nucleotides up into condons
    public static String [] condons (int x, String nucleotidesWithOutHyphen) {
        String [] condons = new String[x/3];
        String v;
        for(int j = 0; j<x/3 ; j++) {
            v = nucleotidesWithOutHyphen.substring(j * 3 , j * 3 + 3);
            condons [j] = v;
        } // end of forloop
        return condons;
    } // end of condons method
   
    
    // Start of isProtein method.  Figures out if a nucleotide is a protein  
    public static void isProtein(String [] condons, String nucleotides, double [] massCount, double sum, PrintStream output) {
        if (nucleotides.startsWith("ATG") && condons.length > 4 && (massCount[1] + massCount[2])/sum > .3) {
            if (nucleotides.endsWith("TAA") || nucleotides.endsWith("TAG") || nucleotides.endsWith("TGA")) {
                output.print("Is protein?:  YES");
                output.println("\n");
            } // end of nested if
        } else {
           output.print("Is protein?:  NO");
           output.println("\n");
        } // end of if/else

    }  // end of isProtein method
} // end of JHDNA class
               
   
           
 