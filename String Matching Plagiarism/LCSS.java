import java.util.*;
import java.io.*;
import java.lang.*;

public class LCSS 
{
   public static void main(String[] args) throws Exception
   {
	Scanner keyboard = new Scanner (System.in);

 	System.out.print("Enter Text File to be checked for Plagrism: ");
	String fileName = keyboard.nextLine();
 	
	System.out.print("Enter Text to be used as a source: ");
	String fileName2 = keyboard.nextLine();
	
	Vector<String> text1 = new Vector<String>();
	Vector<String> text2= new Vector<String>();	
	
	FileReader fr1 = new FileReader(fileName);
	FileReader fr2 = new FileReader(fileName2);

	int i, j, size, match;
        j = match = 0;

	text1.add(""); 
	text2.add("");
	
    	while ((i = fr1.read()) != -1)
	{
	   if((char) i == '\n' || (char) i == '\t')
	   { 
             j++;            
    	     text1.add("");
	   }
	   else
           {
	      if((char) i != ' ' && (char) i != '.' && (char) i != '!' && (char) i != '?')
	          text1.set(j, text1.get(j) + (char) i); 
           }
       }
       
       for(i = 0; i< text1.size();i++)
     	  if(text1.get(i).equals(""))
	  {
	    text1.remove("");
	    i--;
	  }	     
      
	j = 0;
	while ((i = fr2.read()) != -1)
	{
	   if((char) i == '\n' || (char) i == '\t')
	   { 
             j++;            
    	     text2.add("");
	   }
	   else
           {
	      if((char) i != ' ' && (char) i != '.' && (char) i != '!' && (char) i != '?')
	          text2.set(j, text2.get(j) + (char) i); 
           }
       }
       
       for(i = 0; i< text2.size();i++)
     	  if(text2.get(i).equals(""))
	  {
	    text2.remove("");
	    i--;
	  }	     


     int[][] plag = new int[text1.size()][text2.size()];
     int max = 0;;
     int cal = 0;

     long start_time = System.nanoTime();
     for(i = 0; i<text1.size();i++)
     {
	 int m = text1.get(i).length();
         for(j = 0; j<text2.size();j++)
	 {	   
           int n = text2.get(j).length();
	   plag[i][j] = Search(text1.get(i).toCharArray(), text2.get(j).toCharArray(), m, n);
	 }
     } 
     long end_time = System.nanoTime();

     double difference = (end_time - start_time) / 1e6;
      
    for(i = 0; i<text1.size();i++)
    {  
	for(j = 0; j<text2.size();j++)
	{ 
	  max = Math.max(max,plag[i][j]);
	}	
	if((double) max / (double) text1.get(i).length() >= .30)
	   cal++;	
	max = 0;
    }
	   
   double last = (double) cal / (double) text1.size();

    if(last >= .30)
    {   System.out.println("The File, " + fileName + " has plagrism at " + last*100 + "% and is considered plagiarised");}
    else
    {   System.out.println("The File, " + fileName + " has plagrism at " + last*100 + "% and does not have a significant amount of matches to be considered for plagiarism"); }
 	
    System.out.println("Time that algorithm took was: "+difference +"ms");
  }

  static int Search(char X[], char Y[], int m, int n) 
  {
   int LCS[][] = new int[m + 1][n + 1];
   int result = 0;  // To store length of the longest common substring
   
   for (int i = 0; i <= m; i++) 
   {
     for (int j = 0; j <= n; j++) 
     {
       if (i == 0 || j == 0)
          LCS[i][j] = 0;
       else if (X[i - 1] == Y[j - 1])
       {
         LCS[i][j] = LCS[i - 1][j - 1] + 1;
         result = Math.max(result, LCS[i][j]);
       } 
       else
         LCS[i][j] = 0;
     }
    }
    return result;
  }
}