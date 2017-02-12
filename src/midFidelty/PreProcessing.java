package midFidelty;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.util.FileTokenizer;

public class PreProcessing {
	PorterAlgo pa = new PorterAlgo();
/*
	 //checks for vowels in a given string
    System.out.println(pa.containsVowel("vaibhav"));
    
    //removes special characters
    System.out.println(pa.Clean("vaibhav's book"));

    //check for a given suffix
    NewString stem = new NewString();
    System.out.println(pa.hasSuffix("corresponding","ing",stem));

    //stemming the words
    ArrayList<String> tok = new ArrayList<String>();
    String[] tokens = {"normalize","technical","education"};
    for (String x: tokens){
        tok.add(x);
    }
    System.out.println(completeStem(tok));

    String fileName = ((args.length > 0) ? args[0] : DEFAULT_TEST_FILE);
    FileReader fileReader = new FileReader(new File(fileName));
    FileTokenizer fileTokenizer = new FileTokenizer();
    List<String> tokens1 = fileTokenizer.tokenize(fileReader);

    System.out.println("Tokenizing the input file:");
    System.out.print(completeStem(tokens1));
}
*/
//method to completely stem the words in an array list
public static ArrayList<String> completeStem(List<String> tokens1){
    PorterAlgo pa = new PorterAlgo();
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









class NewString {
	  public String str;

	  NewString() {
	     str = "";
	  }
	}

	class PorterAlgo {

	  String Clean( String str ) {
	     int last = str.length();

	     new Character( str.charAt(0) );
	     String temp = "";

	     for ( int i=0; i < last; i++ ) {
	         if ( Character.isLetterOrDigit( str.charAt(i) ) )
	            temp += str.charAt(i);
	     }

	     return temp;
	  } //clean

	  boolean hasSuffix( String word, String suffix, NewString stem ) {

	     String tmp = "";

	     if ( word.length() <= suffix.length() )
	        return false;
	     if (suffix.length() > 1) 
	        if ( word.charAt( word.length()-2 ) != suffix.charAt( suffix.length()-2 ) )
	           return false;

	     stem.str = "";

	     for ( int i=0; i<word.length()-suffix.length(); i++ )
	         stem.str += word.charAt( i );
	     tmp = stem.str;

	     for ( int i=0; i<suffix.length(); i++ )
	         tmp += suffix.charAt( i );

	     if ( tmp.compareTo( word ) == 0 )
	        return true;
	     else
	        return false;
	  }

	  boolean vowel( char ch, char prev ) {
	     switch ( ch ) {
	        case 'a': case 'e': case 'i': case 'o': case 'u': 
	  return true;
	case 'y': {

	  switch ( prev ) {
	    case 'a': case 'e': case 'i': case 'o': case 'u': 
	              return false;

	            default: 
	              return true;
	          }
	        }

	        default : 
	          return false;
	     }
	  }

	  int measure( String stem ) {

	    int i=0, count = 0;
	    int length = stem.length();

	    while ( i < length ) {
	       for ( ; i < length ; i++ ) {
	           if ( i > 0 ) {
	              if ( vowel(stem.charAt(i),stem.charAt(i-1)) )
	                 break;
	           }
	           else {  
	              if ( vowel(stem.charAt(i),'a') )
	            break; 
	       }
	   }

	   for ( i++ ; i < length ; i++ ) {
	       if ( i > 0 ) {
	          if ( !vowel(stem.charAt(i),stem.charAt(i-1)) )
	              break;
	          }
	       else {  
	          if ( !vowel(stem.charAt(i),'?') )
	             break;
	       }
	   } 
	  if ( i < length ) {
	     count++;
	     i++;
	  }
	} //while

	    return(count);
	  }

	  boolean containsVowel( String word ) {

	     for (int i=0 ; i < word.length(); i++ )
	         if ( i > 0 ) {
	            if ( vowel(word.charAt(i),word.charAt(i-1)) )
	               return true;
	         }
	         else {  
	            if ( vowel(word.charAt(0),'a') )
	               return true;
	         }

	     return false;
	  }

	  boolean cvc( String str ) {
	     int length=str.length();

	     if ( length < 3 )
	        return false;

	     if ( (!vowel(str.charAt(length-1),str.charAt(length-2)) )
	        && (str.charAt(length-1) != 'w') && (str.charAt(length-1) != 'x') && (str.charAt(length-1) != 'y')
	&& (vowel(str.charAt(length-2),str.charAt(length-3))) ) {

	if (length == 3) {
	   if (!vowel(str.charAt(0),'?')) 
	              return true;
	           else
	              return false;
	        }
	        else {
	           if (!vowel(str.charAt(length-3),str.charAt(length-4)) ) 
	              return true; 
	           else
	              return false;
	        } 
	     }   

	     return false;
	  }

	  String step1( String str ) {

	     NewString stem = new NewString();

	     if ( str.charAt( str.length()-1 ) == 's' ) {
	if ( (hasSuffix( str, "sses", stem )) || (hasSuffix( str, "ies", stem)) ){
	   String tmp = "";
	   for (int i=0; i<str.length()-2; i++)
	       tmp += str.charAt(i);
	   str = tmp;
	}
	else {
	   if ( ( str.length() == 1 ) && ( str.charAt(str.length()-1) == 's' ) ) {
	      str = "";
	      return str;
	   }
	   if ( str.charAt( str.length()-2 ) != 's' ) {
	      String tmp = "";
	          for (int i=0; i<str.length()-1; i++)
	              tmp += str.charAt(i);
	          str = tmp;
	       }
	    }  
	 }

	 if ( hasSuffix( str,"eed",stem ) ) {
	   if ( measure( stem.str ) > 0 ) {
	      String tmp = "";
	          for (int i=0; i<str.length()-1; i++)
	              tmp += str.charAt( i );
	          str = tmp;
	       }
	 }
	 else {  
	    if (  (hasSuffix( str,"ed",stem )) || (hasSuffix( str,"ing",stem )) ) { 
	   if (containsVowel( stem.str ))  {

	      String tmp = "";
	      for ( int i = 0; i < stem.str.length(); i++)
	          tmp += str.charAt( i );
	      str = tmp;
	      if ( str.length() == 1 )
	         return str;

	      if ( ( hasSuffix( str,"at",stem) ) || ( hasSuffix( str,"bl",stem ) ) || ( hasSuffix( str,"iz",stem) ) ) {
	         str += "e";

	      }
	      else {   
	         int length = str.length(); 
	         if ( (str.charAt(length-1) == str.charAt(length-2)) 
	            && (str.charAt(length-1) != 'l') && (str.charAt(length-1) != 's') && (str.charAt(length-1) != 'z') ) {

	            tmp = "";
	            for (int i=0; i<str.length()-1; i++)
	                tmp += str.charAt(i);
	            str = tmp;
	         }
	         else
	            if ( measure( str ) == 1 ) {
	               if ( cvc(str) ) 
	                  str += "e";
	                }
	          }
	       }
	    }
	 }

	 if ( hasSuffix(str,"y",stem) ) 
	if ( containsVowel( stem.str ) ) {
	   String tmp = "";
	   for (int i=0; i<str.length()-1; i++ )
	       tmp += str.charAt(i);
	   str = tmp + "i";
	        }
	     return str;  
	  }

	  String step2( String str ) {

	     String[][] suffixes = { { "ational", "ate" },
	                            { "tional",  "tion" },
	                            { "enci",    "ence" },
	                            { "anci",    "ance" },
	                            { "izer",    "ize" },
	                            { "iser",    "ize" },
	                            { "abli",    "able" },
	                            { "alli",    "al" },
	                            { "entli",   "ent" },
	                            { "eli",     "e" },
	                            { "ousli",   "ous" },
	                            { "ization", "ize" },
	                            { "isation", "ize" },
	                            { "ation",   "ate" },
	                            { "ator",    "ate" },
	                            { "alism",   "al" },
	                            { "iveness", "ive" },
	                            { "fulness", "ful" },
	                            { "ousness", "ous" },
	                            { "aliti",   "al" },
	                            { "iviti",   "ive" },
	                            { "biliti",  "ble" }};
	     NewString stem = new NewString();


	     for ( int index = 0 ; index < suffixes.length; index++ ) {
	         if ( hasSuffix ( str, suffixes[index][0], stem ) ) {
	            if ( measure ( stem.str ) > 0 ) {
	               str = stem.str + suffixes[index][1];
	               return str;
	            }
	         }
	     }

	     return str;
	  }

	  String step3( String str ) {

	        String[][] suffixes = { { "icate", "ic" },
	                               { "ative", "" },
	                               { "alize", "al" },
	                               { "alise", "al" },
	                               { "iciti", "ic" },
	                               { "ical",  "ic" },
	                               { "ful",   "" },
	                               { "ness",  "" }};
	        NewString stem = new NewString();

	        for ( int index = 0 ; index<suffixes.length; index++ ) {
	            if ( hasSuffix ( str, suffixes[index][0], stem ))
	               if ( measure ( stem.str ) > 0 ) {
	                  str = stem.str + suffixes[index][1];
	                  return str;
	               }
	        }
	        return str;
	  }

	  String step4( String str ) {

	     String[] suffixes = { "al", "ance", "ence", "er", "ic", "able", "ible", "ant", "ement", "ment", "ent", "sion", "tion",
	                   "ou", "ism", "ate", "iti", "ous", "ive", "ize", "ise"};

	     NewString stem = new NewString();

	     for ( int index = 0 ; index<suffixes.length; index++ ) {
	         if ( hasSuffix ( str, suffixes[index], stem ) ) {

	            if ( measure ( stem.str ) > 1 ) {
	               str = stem.str;
	               return str;
	            }
	         }
	     }
	     return str;
	  }

	  String step5( String str ) {

	     if ( str.charAt(str.length()-1) == 'e' ) { 
	if ( measure(str) > 1 ) {/* measure(str)==measure(stem) if ends in vowel */
	   String tmp = "";
	   for ( int i=0; i<str.length()-1; i++ ) 
	       tmp += str.charAt( i );
	   str = tmp;
	}
	else
	   if ( measure(str) == 1 ) {
	      String stem = "";
	          for ( int i=0; i<str.length()-1; i++ ) 
	              stem += str.charAt( i );

	          if ( !cvc(stem) )
	             str = stem;
	       }
	 }

	 if ( str.length() == 1 )
	    return str;
	 if ( (str.charAt(str.length()-1) == 'l') && (str.charAt(str.length()-2) == 'l') && (measure(str) > 1) )
	if ( measure(str) > 1 ) {/* measure(str)==measure(stem) if ends in vowel */
	   String tmp = "";
	           for ( int i=0; i<str.length()-1; i++ ) 
	               tmp += str.charAt( i );
	           str = tmp;
	        } 
	     return str;
	  }

	  String stripPrefixes ( String str) {

	     String[] prefixes = { "kilo", "micro", "milli", "intra", "ultra", "mega", "nano", "pico", "pseudo"};

	 int last = prefixes.length;
	 for ( int i=0 ; i<last; i++ ) {
	     if ( str.startsWith( prefixes[i] ) ) {
	        String temp = "";
	            for ( int j=0 ; j< str.length()-prefixes[i].length(); j++ )
	                temp += str.charAt( j+prefixes[i].length() );
	            return temp;
	         }
	     }

	     return str;
	  }


	  private String stripSuffixes( String str ) {

	     str = step1( str );
	     if ( str.length() >= 1 )
	        str = step2( str );
	     if ( str.length() >= 1 )
	        str = step3( str );
	     if ( str.length() >= 1 )
	        str = step4( str );
	     if ( str.length() >= 1 )
	        str = step5( str );

	     return str; 
	  }


	  public String stripAffixes( String str ) {

	    str = str.toLowerCase();
	    str = Clean(str);

	    if (( str != "" ) && (str.length() > 2)) {
	   str = stripPrefixes(str);

	   if (str != "" ) 
	      str = stripSuffixes(str);

	}   

	return str;
	} //stripAffixes

	} //class

	
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
	    public double idf(List<List<String>> docs, String term) {
	        double n = 0;
	        for (List<String> doc : docs) {
	            for (String word : doc) {
	                if (term.equalsIgnoreCase(word)) {
	                    n++;
	                    break;
	                }
	            }
	        }
	        return Math.log(docs.size() / n);
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
	        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);

	        CalculateTFIDF calculator = new CalculateTFIDF();
	        double tfidf = calculator.tfIdf(doc1, documents, "sit");
	        System.out.println("TF-IDF = " + tfidf);
	        
	    }


	    
	    
	}
	
