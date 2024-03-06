package file.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * Provides a contract for the back-end to all its clients. The contract
 * guarantees that the back-end provides methods for registering files, getting
 * metadata information for them, applying filters, and, storing the results of
 * a filter in an output stream.
 * 
 * @author pvassil
 *
 */
public interface StructuredFileManagerInterface {
	/**
	 * A method to allow the back end engine to learn the characteristics of a file
	 * 
	 * @param pAlias     a string with a short code-name for the registered file, to
	 *                   allow retrieval by it in later method calls
	 * @param pPath      a String with the path to the registered file
	 * @param pSeparator a String with the separator between the columns of each
	 *                   record
	 * @return a File object for the newly registered engine
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException;

	/**
	 * Returns an array of String with the column names of the file registered with
	 * the alias parameter
	 * 
	 * @param pAlias a string with a short code-name for the registered file
	 * @return an array of String with the column names of the file; null if the
	 *         alias has not been registered
	 */
	public String[] getFileColumnNames(String pAlias);

	/**
	 * Applies a composite conjunctive filter, composed of atomic filters, to the
	 * file and returns the rows that satisfy its conditions.
	 * 
	 * The result includes exactly those rows of the file that satisfy every atomic
	 * filter in the filter submitted.
	 * 
	 * @param pAlias         a String with the alias of a registered file
	 * @param pAtomicFilters a Map with the name of the filtered field as key and
	 *                       the list of permissible values as the value of the map.
	 *                       All values are treated as strings in the files that the
	 *                       system handles.
	 * @return a List of String[], which is the result set of the filter. Each
	 *         record in the result is represented as an array of strings.
	 */
	public List<String[]> filterStructuredFile(String pAlias, Map<String, List<String>> pAtomicFilters);

	/**
	 * Directs a filter result in a PrintStream
	 * 
	 * @param recordList a List of String[] which is typically the result set of a
	 *                   filter
	 * @param pOut       a PrintStream (that must have been externally prepared)
	 *                   where the results are presented
	 * 
	 * @return -1 if the printing is impossible due to file or null problems; the
	 *         number of records directed to the PrintStream otherwise.
	 */
	public int printResultsToPrintStream(List<String[]> recordList, PrintStream pOut);

}