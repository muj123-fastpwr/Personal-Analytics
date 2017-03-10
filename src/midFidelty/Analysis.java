package midFidelty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Analysis {
	
	public void classify(String [] bagOfWords) throws IOException{
		ArrayList<String> research = new ArrayList<String>();
		ArrayList<String> teaching = new ArrayList<String>();
		BufferedReader reader1 = new BufferedReader(new FileReader("architecture.txt"));
		BufferedReader reader2 = new BufferedReader(new FileReader("research.txt"));
		String line = "";
		float R=0, T=0;
		 while ((line = reader1.readLine()) != null)
		    {
		      teaching.add(line);
		      T++;
		    }
		    reader1.close();
		    
		    while ((line = reader2.readLine()) != null)
		    {
		      research.add(line);
		      R++;
		    }
		    reader2.close();
		
		    float res=0, teach = 0;
		    for(int i=0;i<bagOfWords.length;i++){
		    	
		    	for(int j=0;j<teaching.size();j++){
		    	//	if(bagOfWords[i].equalsIgnoreCase(teaching.get(j)) || teaching.get(j).toLowerCase().contains(bagOfWords[i].toLowerCase()))
	    			if(bagOfWords[i].equalsIgnoreCase(teaching.get(j)))
		    			teach++;
		    	}
		    
		    	for(int k=0;k<research.size();k++){
		    	//	if(bagOfWords[i].equalsIgnoreCase(research.get(k)) || research.get(k).toLowerCase().contains(bagOfWords[i].toLowerCase()))
	    			if(bagOfWords[i].equalsIgnoreCase(research.get(k)))
		    			res++;
		    	}
		    }
		    
		    System.out.println("Research : "+ res/R);
		    System.out.println("Teaching : "+ teach/T);
	}
}
