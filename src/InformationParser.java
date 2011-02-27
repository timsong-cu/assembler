import java.io.*;
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
			if (info.contains(insertion.accessionNo)) return insertion;
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
			if (nuc == 'A' || nuc == 'a') nuc = 'T';
			else if (nuc == 'C' || nuc == 'c') nuc = 'G';
			else if (nuc == 'G' || nuc == 'g') nuc = 'C';
			else if (nuc == 'T' || nuc == 't') nuc = 'A';
			else if (nuc == 'Y' || nuc == 'y') nuc = 'R';	//Y: C, T
			else if (nuc == 'R' || nuc == 'r') nuc = 'Y';	//R: A, G
			else if (nuc == 'W' || nuc == 'w') nuc = 'W';	//A, T
			else if (nuc == 'S' || nuc == 's') nuc = 'S';	//G, C
			else if (nuc == 'K' || nuc == 'k') nuc = 'M';	//K: T, G
			else if (nuc == 'M' || nuc == 'm') nuc = 'K';	//M: C, A
			else if (nuc == 'D' || nuc == 'd') nuc = 'H';	//D: A, G, T
			else if (nuc == 'H' || nuc == 'h') nuc = 'D'; 	//H: A, C, T
			else if (nuc == 'V' || nuc == 'v') nuc = 'B';	//V: A, C, G
			else if (nuc == 'B' || nuc == 'b') nuc = 'V';	//B: C, G, T
			else nuc = 'N';
			
			reversal.addFirst(new Character(nuc));
		}
	}
	
	
	/**
	 * Prints the reverse complement sequence.
	 * @param printWriter the Print Writer that is used to print
	 */
	public void printReversal(PrintWriter printWriter) {
		ListIterator<Character> reversalIterator = reversal.listIterator();
		while (reversalIterator.hasNext()) printWriter.print(reversalIterator.next());
		if (reversal.size() > 0) printWriter.println();
	}
	
	
	/**
	 * Clears the reversal.
	 */
	public void clearReversal() {
		reversal.clear();
	}
}
