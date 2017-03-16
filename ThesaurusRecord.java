import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 */

public class ThesaurusRecord extends Record{
    private String word;
    private ArrayList <String> synonyms;

	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
    public ThesaurusRecord(int numFiles) {
    	super(numFiles);
    	clear();
    }

    /**
	 * This Comparator should simply behave like the default (lexicographic) compareTo() method
	 * for Strings, applied to the portions of the FileLines' Strings up to the ":"
	 * The getComparator() method of the ThesaurusRecord class will simply return an
	 * instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			String word1 = l1.getString().split(":")[0];//First word on line one
			String word2 = l2.getString().split(":")[0];//First word on line two
			
			if(word1.compareTo(word2) == 0) {
				return 0;
			} 
			else if (word1.compareTo(word2) < 0) {
				return -1;
			} 
			else {
				return 1;
			}
		}
		
		public boolean equals(Object o) {
			FileLine line = (FileLine) o;
			if (word.equals(line.getString().split(":")[0])) {
				return true;
			}
			else {
				return false;
			}
		}
    }
    
	/**
	 * This method should simply create and return a new instance of the ThesaurusLineComparator class.
	 */
    public Comparator<FileLine> getComparator() {
		return new ThesaurusLineComparator();
    }
	
	/**
	 * This method should (1) set the word to null and (2) empty the list of synonyms.
	 */
    public void clear() {
		word = null;
		synonyms = new ArrayList<String>();
    }
	
	/**
	 * This method should parse the list of synonyms contained in the given FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
    public void join(FileLine w) {
    	String[] line = w.getString().split(":");
    	String[] syns = line[1].split(",");
    	word = line[1];
    	for(String myWord : syns) {
    		if (!synonyms.contains(myWord)) {
    			synonyms.add(myWord);
    		}
    	}
    	Collections.sort(synonyms);
    }
	
	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		String product = (word + ": ");
		for (String myWord : synonyms) {
			product += (myWord + ",");
		}
		product = product.substring(0, product.length() - 1);
		return product;
	}
}
