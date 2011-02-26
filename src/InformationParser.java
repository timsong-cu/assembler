import java.util.*;

/**
 * Parses the file information.
 * @author Rebecca A. Sealfon
 *
 */
public class InformationParser {
	ArrayList<FileInfo> insertions;
	LinkedList<Character> reversal;
	
	/**
	 * Constructor initializes the list of insertions.
	 */
	public InformationParser() {
		insertions = new ArrayList<FileInfo>();
		reversal = new LinkedList<Character>();
	}
	
	
	/**
	 * Adds the information about an insertion to the ArrayList stored in memory.
	 * @param insertion the information about the insertion
	 */
	public void addInsertion(FileInfo insertion) {
		insertions.add(insertion);
	}
	
	
	/**
	 * Looks up the information about the reference, based on the FASTA comment or file name
	 * @param info the FASTA comment or file name
	 * @return the file info stored in memory
	 */
	public FileInfo lookupByName(String info) {
		for (FileInfo insertion : insertions) {
			if (insertion.accessionNo.contains(info)) return insertion;
		}
		return null;
	}
	
	
	/**
	 * Adds the reverse complement of the next file line to the list of reverse characters, in reverse order.
	 * @param line the next file line
	 */
	public void addReverseCharacters(String line) {
		for (int i=0; i<line.length(); i++) {
			char nuc = line.charAt(i);
			
			//Change to complementary nucleotide; includes ambiguous nucleotides
			if (nuc == 'A') nuc = 'T';
			else if (nuc == 'C') nuc = 'G';
			else if (nuc == 'G') nuc = 'C';
			else if (nuc == 'T') nuc = 'A';
			else if (nuc == 'Y') nuc = 'R';	//Y: C, T
			else if (nuc == 'R') nuc = 'Y';	//R: A, G
			else if (nuc == 'W');			//A, T
			else if (nuc == 'S');			//G, C
			else if (nuc == 'K') nuc = 'M';	//K: T, G
			else if (nuc == 'M') nuc = 'K';	//M: C, A
			else if (nuc == 'D') nuc = 'H';	//D: A, G, T
			else if (nuc == 'H') nuc = 'D'; //H: A, C, T
			else if (nuc == 'V') nuc = 'B';	//V: A, C, G
			else if (nuc == 'B') nuc = 'V';	//B: C, G, T
			else nuc = 'N';
			
			reversal.addFirst(nuc);
		}
	}
	
	
	/**
	 * Returns the reverse complement sequence as a String.
	 * @return the reverse complement sequence
	 */
	public String getReversal() {
		char[] reversalArray = new char[reversal.size()];
		ListIterator<Character> reversalIterator = reversal.listIterator();
		for (int i=0; i<reversalArray.length; i++) {
			char nuc = reversalIterator.next();
			reversalArray[i] = nuc;
		}
		return reversalArray.toString();
	}
	
	
	/**
	 * Clears the reversal.
	 */
	public void clearReversal() {
		reversal.clear();
	}
}
