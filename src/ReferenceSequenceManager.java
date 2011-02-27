import java.io.*;
import java.util.*;

/**
 * Parses the reference sequences for the relevant instructions.
 * @author Rebecca A. Sealfon, Ruijie Song, Manish Vasani
 *
 */
public class ReferenceSequenceManager {
	File mainReference;
	File referenceInfo;
	ArrayList<String> insertionFileNames;
	InformationParser parser;

	/**
	 * Handles the reference files.
	 * @param references the files representing the reference genome references
	 */
	public ReferenceSequenceManager(String[] references) throws FileNotFoundException {
		mainReference = new File(references[0]);
		referenceInfo = new File(references[1]);
		parser = new InformationParser();

		//Records the file names of the insertions
		insertionFileNames = new ArrayList<String>();
		for (int i=2; i<references.length; i++) insertionFileNames.add(references[i]);
	}


	/**
	 * Loads information about the insertions into memory, based on the reference information file.
	 */
	public void loadInsertionInfo() throws IOException {
		Scanner lineScan = new Scanner(referenceInfo);
		Scanner scanner;
		//Reads the information about the sequence files
		while (lineScan.hasNextLine()) {
			String line = lineScan.nextLine();
			scanner = new Scanner(line);

			if (scanner.hasNext()) {
				String accessionNo = scanner.next();
				scanner.next();	//Chromosome number is skipped over

				//Whether the insertion is reverse-complement
				String rev = scanner.next();
				boolean reversed = true;
				if (rev.equals("0") || rev.equalsIgnoreCase("false") || rev.equalsIgnoreCase("fwd")) reversed = false;

				//Adds the next insertion to the List
				parser.addInsertion(new FileInfo(accessionNo, reversed));
			}
		}
	}


	/**
	 * Appends the insertions to the end of the chimeric sequence, to be analyzed by the assembler.
	 */
	public void makeChimera() throws FileNotFoundException {
		Scanner scan = new Scanner(mainReference);
		PrintWriter concatenatedSeq = new PrintWriter(mainReference.getName() + ".withInsertions.fa");

		//Reads through the primary reference genome and copies it to the new file
		while (scan.hasNextLine()) concatenatedSeq.println(scan.nextLine());

		//Finds the insertions, determines whether they are forward or reverse, and prints them to concatenatedSeq
		ListIterator<String> insertionFileNameIterator = insertionFileNames.listIterator();
		while (insertionFileNameIterator.hasNext()) {
			String fileName = insertionFileNameIterator.next();
			File insertionFile = new File(fileName);
			FileInfo fileNameInfo = parser.lookupByName(fileName);
			FileInfo fileLineInfo = null;
			FileInfo fileInfo = null;

			//Reads through each insertion file
			scan = new Scanner(insertionFile);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();

				if (line.startsWith(">") || !scan.hasNextLine()) {
					if (fileInfo != null && fileInfo.reversed) parser.printReversal(concatenatedSeq);
					parser.clearReversal();
					if (fileNameInfo == null) fileLineInfo = parser.lookupByName(line);
					concatenatedSeq.println(line);
				}

				//Adds the insertion line to the file
				else {
					if (fileNameInfo != null) fileInfo = fileNameInfo;
					else fileInfo = fileLineInfo;

					if (fileInfo != null) {
						if (!fileInfo.reversed && line.length() > 0) concatenatedSeq.println(line);

						//Reverse the sequence
						else parser.addReverseCharacters(line);
					}
				}
			}
		}
		concatenatedSeq.close();
		System.out.println("Concatenated file successfully created.");
	}
}
