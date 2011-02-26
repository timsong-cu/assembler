import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
	
/**
 * Parses the reference sequences for the relevant instructions.
 * @author Rebecca A. Sealfon, Ruijie Song, Manish Vasani
 *
 */
public class ReferenceSequenceManager {
	File mainReference;
	File referenceInfo;
	ArrayList<FileInfo> insertions;
	ArrayList<String> insertionFileNames;
	String primary;
	
	/**
	 * Handles the reference files.
	 * @param references the files representing the reference genome references
	 */
	public ReferenceSequenceManager(String[] references) throws FileNotFoundException {
		mainReference = new File(references[0]);
		referenceInfo = new File(references[1]);
		insertions = new ArrayList<FileInfo>();
		
		//Records the file names of the insertions
		insertionFileNames = new ArrayList<String>();
		for (int i=2; i<references.length; i++) insertionFileNames.add(references[i]);
	}
	
	
	/**
	 * Loads information about the insertions into memory, based on the reference information file.
	 */
	public void loadInsertionInfo() {
		
		
	}
	
	
	/**
	 * Appends the insertions to the end of the chimeric sequence, to be analyzed by the assembler.
	 */
	public void makeChimera() {
		
	}
	
	
	public void readFiles() {
		Scanner lineScan = new Scanner(referenceInfo);
		Scanner scanner;
		
		//Reads the information about the sequence files
		while (lineScan.hasNextLine()) {
			String line = lineScan.nextLine();
			scanner = new Scanner(line);
			
			String accessionNo = scanner.next();
			
			//Chromosome number
			String chr = scanner.next();
			int chromosome = 0;	//Chromosome X
			if (chr.contains("y") || chr.contains("Y")) chromosome = -1;
			if (chr.endsWith("M") || chr.contains("mt") || chr.contains("MT") || chr.contains("chondri") 
					|| chr.contains("CHONDRI")) chromosome = -2;
			else {	//Numbered chromosome
				for (int i=0; i<chr.length(); i++) {
					if (Character.isDigit(chr.charAt(i))) {
						chr = chr.substring(i);
						break;
					}
				}
				for (int i=0; i<chr.length(); i++) {
					if (!Character.isDigit(chr.charAt(i))) {
						chr = chr.substring(0, i);
						break;
					}
				}
				chromosome = Integer.parseInt(chr);
			}
			
			//Whether the insertion is reverse-complement
			String rev = scanner.next();
			boolean reversed = true;
			if (rev.equals("0") || rev.equalsIgnoreCase("false") || rev.equalsIgnoreCase("fwd")) reversed = false;
			
			//Positional info for the insertion
			int cloneStart = Integer.parseInt(scanner.next());
			int cloneEnd = Integer.parseInt(scanner.next());
			int chromStart = Integer.parseInt(scanner.next());
			int chromEnd = -1;
			if (scanner.hasNext()) chromEnd = Integer.parseInt(scanner.next());
			
			insertions.add(new FileInfo(accessionNo, chromosome, reversed, cloneStart, cloneEnd, chromStart, chromEnd));
		}
		
		for(int i = 2; i < references.length; i++){
			Scanner input = new Scanner(new File(references[i]));
			//assume FASTA
			String sequence = "";
			while(input.hasNextLine()){
				String line = input.nextLine();
				if(line.contains(">") || line.contains(";"))
					continue;
				else
					sequence += line.trim();
			}
			insertions.get(i-2).sequence = sequence; 
		}
		//read main sequence now. Assume FASTA
		Scanner input = new Scanner(mainReference);
		//assume FASTA
		String seq = "";
		while(input.hasNextLine()){
			String line = input.nextLine();
			if(line.contains(">") || line.contains(";"))
				continue;
			else
				seq += line.trim();
		}
		this.primary = seq;
		Collections.sort(insertions);
	}
}
