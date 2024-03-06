/**
 * 
 */
package file.manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author pvassil
 *
 */
public class StructuredFileManagerTest {
	private static StructuredFileManager fileManager;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fileManager = new StructuredFileManager();
	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		;
	}

	/**
	 * Test method for {@link file.manager.StructuredFileManager#registerFile(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	@Test
	public final void testRegisterFileHappyDay() throws NullPointerException, IOException {
		String sAlias = "simple";		
		String sSeparator = ",";
		File sFile =		fileManager.registerFile(sAlias, "./test/resources/input/simple.csv", sSeparator);
		File sRefFile = new File("./test/resources/input/simple.csv");

		String cAlias = "covid";
		String cSeparator = ",";
		File cFile =		fileManager.registerFile(cAlias, "./test/resources/input/CovidData.csv", cSeparator);
		File cRefFile = new File("./test/resources/input/CovidData.csv");

		assert(sRefFile.getCanonicalPath().equals(sFile.getCanonicalPath()) );
		assert(cRefFile.getCanonicalPath().equals(cFile.getCanonicalPath()) );
	}

	/**
	 * Test method for {@link file.manager.StructuredFileManager#filterStructuredFile(java.lang.String, java.util.Map)}.
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	@Test
	public final void testFilterStructuredFileHappyDay() throws NullPointerException, IOException {
		String sAlias = "simple";		
		String sSeparator = ",";
		fileManager.registerFile(sAlias, "./test/resources/input/simple.csv", sSeparator);
		List<String[]> verySimpleResult = null;

		Map<String, List<String>> atomicFilters = new HashMap<String, List<String>>();
		List<String> countryFilter = new ArrayList<String>();
		countryFilter.add("AUS:Australia");
		atomicFilters.put("LOCATION:Country", countryFilter);

		verySimpleResult = fileManager.filterStructuredFile(sAlias, atomicFilters);
		assertEquals(15,verySimpleResult.size());
	}

	@Test
	public final void testFilterStructuredFileNoRecords() throws NullPointerException, IOException {
		String sAlias = "simple";		
		String sSeparator = ",";
		fileManager.registerFile(sAlias, "./test/resources/input/simple.csv", sSeparator);
		List<String[]> verySimpleResult = null;

		Map<String, List<String>> atomicFilters = new HashMap<String, List<String>>();
		List<String> countryFilter = new ArrayList<String>();
		countryFilter.add("StrangeLand");
		atomicFilters.put("LOCATION:Country", countryFilter);

		verySimpleResult = fileManager.filterStructuredFile(sAlias, atomicFilters);
		assertEquals(0,verySimpleResult.size());
	}
	
	/**
	 * Test method for {@link file.manager.StructuredFileManager#printResultsToPrintStream(java.util.List, java.io.PrintStream)}.
	 */
	@Test
	public final void testPrintResultsToPrintStreamHappyDay() {
		String outputFilePath = "./test/resources/output/testPrintStreamHappy.txt";
		String[] result1 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2010", "4261.055"};
		String[] result2 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2011", "4345.16"};
		List<String[]> result = new ArrayList<String[]>();
		result.add(result1); result.add(result2);
		
		int numRows = 0;
		FileOutputStream fOutStream = null;
		try {
			fOutStream = new FileOutputStream(outputFilePath);
		} catch (FileNotFoundException e) {
			System.err.println("structuredFileManagerTest::testPrintResultsHappyDay() failed to open fout stream");
			e.printStackTrace();
		}  
		PrintStream pOutStream = new PrintStream(fOutStream);  
		numRows = fileManager.printResultsToPrintStream(result, pOutStream);
		assertEquals(2, numRows);
	}

	/**
	 * Test method for {@link file.manager.StructuredFileManager#getFileColumnNames(java.lang.String)}.
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	@Test
	public final void testGetFileColumnNamesHappyDay() throws NullPointerException, IOException {
		String cAlias = "covid";
		String cSeparator = ",";
		fileManager.registerFile(cAlias, "./test/resources/input/CovidData.csv", cSeparator);

		String[] expectedColNames = {"DATE:dateRep", "DAY:day", "MONTH:month", "YEAR:year", 
				"MSR:cases", "MSR:deaths", "GEO:countriesAndTerritories", "GEO:geoId", "GEO:countryterritoryCode", "POPULATIONpopData2020", "GEO:continentExp"};

		String[] resColNames = fileManager.getFileColumnNames("covid");
		for (int i =0; i< resColNames.length;i++) {
			if (!resColNames[i].equals(expectedColNames[i]))
				fail("Erroneous col name arrays");
		}
	}

	@Test
	public final void testGetFileColumnNamesWrongAlias() throws NullPointerException, IOException {
		String cAlias = "covid";
		String cSeparator = ",";
		fileManager.registerFile(cAlias, "./test/resources/input/CovidData.csv", cSeparator);

		String[] resColNames = fileManager.getFileColumnNames("SomeoneElseSomewhereElse");
		assertEquals(0, resColNames.length);
//		assertNull(resColNames);
//DONE: fixed the method to return an empty array, instead of null		
	}

	@Test
	public final void testGetFileColumnNamesNullAlias() throws NullPointerException, IOException {
		String cAlias = "covid";
		String cSeparator = ",";
		fileManager.registerFile(cAlias, "./test/resources/input/CovidData.csv", cSeparator);

		String[] resColNames = fileManager.getFileColumnNames(null);
		assertEquals(0, resColNames.length);	
	}
}//end class
