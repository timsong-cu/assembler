/**
 * Stores the information for an insertion sequence file, for the first iteration of the assembler.
 * @author Rebecca A. Sealfon, Ruijie Song, Manish Vasani
 *
 */
public class FileInfo implements Comparable<FileInfo> {
	String accessionNo;
	boolean reversed;
	
	/**
	 * Creates the sequence information for a read that maps to the start and end of a segment
	 * @param accessionNo accession number
	 * @param reversed whether the sequence is reversed
	 */
	public FileInfo(String accessionNo, boolean reversed) {
		this.accessionNo = accessionNo;
		this.reversed = reversed;
	}
	
	
	/**
	 * Orders the files by accession number.
	 */
	public int compareTo(FileInfo otherFile) {
		return this.accessionNo.compareTo(otherFile.accessionNo);
	}
}
