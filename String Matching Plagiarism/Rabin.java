import java.util.*;
import java.io.*;

public class Rabin 
{
    public static void main(String[] args) throws Exception
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

      	int prime = 13;

	int i, j, size, match;
        j = match = 0;

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
	  match = match + Search(Pattern.get(i), text, prime);
	}
	long end_time = System.nanoTime();
	
	double difference = (end_time - start_time) / 1e6;         

	double plag = (double) match / (double) Vec_Text.size();
	System.out.println("The precent Plagiarized between the texts is " +plag*100+ "%");
	
	if(plag > .5)
	  System.out.println("Heavy Plagiarism Detected in "+fileName2+", File is considered Plagiarized.");
	else if(plag > .3)
	  System.out.println("Signifincat Plagiarism Detected in "+fileName2+", File needs to be reviewed.");
	else
	  System.out.println("Not enough similarities to warrent review.");	

	System.out.println("Time that algorithm took was: "+difference +"ms");
    }

    static int Search(String pat, String txt, int q)
    {
	int match = 0;
	int d = 256;
        int M = pat.length();
        int N = txt.length();
        int i, j;
        int p = 0; // hash value for pattern
        int t = 0; // hash value for txt
        int h = 1;

        for (i = 0; i < M-1; i++)
            h = (h*d)%q;
     
        // Calculate the hash value of pattern and first
        // window of text
        for (i = 0; i < M; i++)
        {
            p = (d*p + pat.charAt(i))%q;
            t = (d*t + txt.charAt(i))%q;
        }

        for (i = 0; i <= N - M; i++)
        {     
            if ( p == t )
            {
                for (j = 0; j < M; j++)
                {
                    if (txt.charAt(i+j) != pat.charAt(j))
                        break;
                }
                if (j == M)
                    match++;
            }
     
            if ( i < N-M )
            {
                t = (d*(t - txt.charAt(i)*h) + txt.charAt(i+M))%q;

                if (t < 0)
               	    t = (t + q);
            }
        }
	return match;
    }
}