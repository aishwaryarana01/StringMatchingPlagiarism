
import java.util.*;
import java.io.*;

class KMP
{
    public static void main(String args[]) throws Exception
    {
	Scanner keyboard = new Scanner (System.in);

 	System.out.print("Enter Text File to be checked for Plagrism: ");
	String fileName = keyboard.nextLine();
 	
	System.out.print("Enter Pattern to be used: ");
	String fileName2 = keyboard.nextLine();

	String text = "";
	Vector<String> Vec_Text = new Vector<String>();
	Vector<String> Pattern= new Vector<String>();	
	
	FileReader fr1 = new FileReader(fileName);
	FileReader fr2 = new FileReader(fileName2);

	int i, j, size, match;
        j = match =0;

	Pattern.add(""); 
	Vec_Text.add("");
	
	while ((i = fr1.read()) != -1)
	{
	   if((char) i == '.' || (char) i == '?' || (char) i == '!')
	   { 
             j++;            
    	     Vec_Text.add("");
	   }
	   else
           {
	      if((char) i != ' ' && (char) i != '\n' && (char) i != '\t')
	          Vec_Text.set(j, Vec_Text.get(j) + (char) i); 
           }
       }
       
       for(i = 0; i< Vec_Text.size();i++)
     	  if(Vec_Text.get(i).equals(""))
	  {
	    Vec_Text.remove("");
	    i--;
	  }	     

	 for(i = 0; i< Vec_Text.size();i++)
	     text = Vec_Text.get(i) + text;

	j = 0;
	
	while ((i = fr2.read()) != -1)
	{
	   if((char) i == '.' || (char) i == '?' || (char) i == '!')
	   { 
             j++;            
    	     Pattern.add("");
	   }
	   else
           {
	      if((char) i != ' ' && (char) i != '\n' && (char) i != '\t')
	          Pattern.set(j, Pattern.get(j) + (char) i); 
           }
       }
       
       for(i = 0; i< Pattern.size();i++)
     	  if(Pattern.get(i).equals(""))
	  {
	    Pattern.remove("");
	    i--;
	  }	     
	
	long start_time = System.nanoTime();
        for(i = 0; i< Pattern.size();i++)
	{
	  match = match + Search(Pattern.get(i), text);
	}
        long end_time = System.nanoTime();
	
	double difference = (end_time - start_time) / 1e6;
 
	double plag = (double) match / (double) Vec_Text.size();
	System.out.println("The precent Plagiarized between the texts is " +plag * 100+ "%");
	
	if(plag > .5)
	  System.out.println("Heavy Plagiarism Detected in "+fileName+", File is considered Plagiarized.");
	else if(plag > .3)
	  System.out.println("Signifincat Plagiarism Detected in "+fileName+", File needs to be reviewed.");
	else
	  System.out.println("Not enough similarities to warrent review.");	

	System.out.println("Time that algorithm took was: "+difference +"ms");
    }

    static int Search(String pattern, String text)
    {
	int match = 0;
        int M = pattern.length();
        int N = text.length();
       
        int lps[] = new int[M]; // array that will hold the longest prefix suffix values for pattern
        int j = 0;  // index for pattern array
 
        LPS(pattern,M,lps);
 
        int i = 0;  // index for text array

        while (i < N)
        {
            if (pattern.charAt(j) == text.charAt(i)) 
            {
                j++;
                i++;
            }
            if (j == M) 
            {
                match++;		
                j = lps[j-1];
            }
            else if (i < N && pattern.charAt(j) != text.charAt(i))
            {
                if (j != 0)
                    j = lps[j-1];
                else
                    i = i+1;
            }
        }
	return match;
    }
 
    static void LPS(String pattern, int M, int lps[])
    {        
        int len = 0;
        int i = 1;
        lps[0] = 0;  
     
        while (i < M)
        {
            if (pattern.charAt(i) == pattern.charAt(len))
            {
                len++;
                lps[i] = len;
                i++;
            }
            else
            {               
                if (len != 0)
                {
                    len = lps[len-1];
                }
                else  // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }       
}
