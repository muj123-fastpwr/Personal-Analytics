package midFidelty;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.tartarus.snowball.ext.englishStemmer;

public class PreProcessing {
	
	
	public String[] clean(String text){
		String []token = text.split(" ");
		for(int i=0;i<token.length;i++){
			token[i]=token[i].trim().replaceAll("\\s+", "");
			
	//		token[i] = token[i].trim();
			token[i] = token[i].replace(".", "");
			token[i] = token[i].replace(":", "");
			token[i] = token[i].replace("(", "");
			token[i] = token[i].replace(")", "");
			token[i] = token[i].replace("\"", "");
			token[i] = token[i].replace("/", "");
			token[i] = token[i].replaceAll(";", "");
			
		}
		return token;
	}
	
	public String[] removeStopWords(String [] bag){
		ArrayList<String> wordsList = new ArrayList<String>();
		
		String []stopWordsList = null;
		try
		  {
		    BufferedReader reader = new BufferedReader(new FileReader("stopwordslist.txt"));
		    String line, SW=new String();
		    while ((line = reader.readLine()) != null)
		    {
		      SW = SW + line + " ";
		    }
		    reader.close();
		    stopWordsList = SW.split(" ");
		  }
		  catch (Exception e)
		  {
		    System.err.format("Exception occurred trying to read '%s'.", "stopwordslist.txt");
		    e.printStackTrace();
		  }
		//++++++++++++++++++++++++
/*		
	String s="This String Contains the stop words like is am are so this program will remove all of them at your will thanks to you for watching this tutorial";
	s=s.trim().replaceAll("\\s+", " ");
	String[] words = s.split(" ");
*/
	for (String word : bag) {
		wordsList.add(word);
		System.out.println(word);
	}

//remove stop words here from the temp list
	for (int i = 0; i < wordsList.size(); i++) {
		// get the item as string
		for (int j = 0; j < stopWordsList.length; j++) {
			if (stopWordsList[j].trim().contains(wordsList.get(i))) {
				wordsList.remove(i);
			}
		}
		bag[i] = wordsList.get(i);
	}
	
	for (String str : wordsList) {
		System.out.print(str+" ");
	}

		
	
		return bag;
	}
	
	public String stem(String[] words){
		String text="";
		englishStemmer english = new englishStemmer();
	    for(int i = 0; i < words.length; i++){
	            english.setCurrent(words[i]);
	            english.stem();
	            //System.out.println(english.getCurrent());
	            text = text + " " +english.getCurrent();
	    }
	    
	    
	    
	    return text;
	}
	
/*	
	//method to completely stem the words in an array list
public static ArrayList<String> completeStem(List<String> tokens1){
   // PorterAlgo pa = new PorterAlgo();
    ArrayList<String> arrstr = new ArrayList<String>();
    for (String i : tokens1){
        String s1 = pa.step1(i);
        String s2 = pa.step2(s1);
        String s3= pa.step3(s2);
        String s4= pa.step4(s3);
        String s5= pa.step5(s4);
        arrstr.add(s5);
    }
    return arrstr;
}

//method to tokenize a file
public static ArrayList<String> fileTokenizer(){
    StringTokenizer strtoken = new StringTokenizer("this is a book");
    ArrayList<String> filetoken = new ArrayList<String>();
    while(strtoken.hasMoreElements()){
        filetoken.add(strtoken.nextToken());
    }
    return filetoken;
}
	
}

*/
	
// ++++++++++++++ TF-IDF+++++++++++++++++
	
	/**
	 * @author Mohamed Guendouz
	 */
	class CalculateTFIDF {
	    /**
	     * @param doc  list of strings
	     * @param term String represents a term
	     * @return term frequency of term in document
	     */
	    public double tf(List<String> doc, String term) {
	        double result = 0;
	        for (String word : doc) {
	            if (term.equalsIgnoreCase(word))
	                result++;
	        }
	        return result / doc.size();
	    }

	    /**
	     * @param docs list of list of strings represents the dataset
	     * @param term String represents a term
	     * @return the inverse term frequency of term in documents
	     */
	    public double idf(List<List<String>> DOCS, String term) {
	        double n = 0;
	        for (List<String> doc : DOCS) {
	            for (String word : doc) {
	                if (term.equalsIgnoreCase(word)) {
	                    n++;
	                    break;
	                }
	            }
	        }
	        return Math.log(DOCS.size() / n);
	    }

	    /**
	     * @param doc a text document
	     * @param docs all documents
	     * @param term term
	     * @return the TF-IDF of term
	     */
	    public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
	        return tf(doc, term) * idf(docs, term);

	    }

	    public void dummyCheck(){
	    	
	        List<String> doc1 = Arrays.asList("Lorem", "ipsum", "dolor", "ipsum", "sit", "ipsum");
	        List<String> doc2 = Arrays.asList("Vituperata", "incorrupte", "at", "ipsum", "pro", "quo");
	        List<String> doc3 = Arrays.asList("Has", "persius", "disputationi", "id", "simul");
	        String [] doc4 = {"good", "color","at","time"};
	        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);

	        CalculateTFIDF calculator = new CalculateTFIDF();
	        double tfidf = calculator.tfIdf(doc1, documents, "sit");
	        System.out.println("TF-IDF = " + tfidf);
	        
	    }


	    
	    
	}
	
}
	
