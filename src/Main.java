/**
 * Driver class allows the user to input the reference file, as well as the insertion sequence files.
 * @author Rebecca A. Sealfon, Ruijie Song, Manish Vasani
 *
 */
public class Main {

	/**
	 * Main method accepts file command line arguments.
	 * @param args[0] the file containing the original reference genome
	 * @param args[1] the relevant information about the insertions
	 * @param args[2,...] the files containing the insertion sequences
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 2) System.out.println("Usage: java Main primaryReference insertionInfoFile <insertionFiles>");
		else {
			ReferenceSequenceManager references = new ReferenceSequenceManager(args);
			references.loadInsertionInfo();
			references.makeChimera();
		}
	}
}
