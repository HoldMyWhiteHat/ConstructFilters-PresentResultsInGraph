/**
 * 
 */
package metadata;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author pvassil
 *
 */
public class NaiveFileMetadataManagerTest {
	private static NaiveFileMetadataManager metadataManager;
	private static String pAlias;
	private static File pFile;
	private static String pSeparator;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pAlias = "defaultAlias";
		pFile = new File("./test/resources/input/simple.csv");
		pSeparator = ",";
		metadataManager = new NaiveFileMetadataManager(pAlias, pFile, pSeparator);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		;
	}


//	/**
//	 * Test method for {@link metadata.NaiveFileMetadataManager#NaiveFileMetadataManager(java.lang.String, java.io.File, java.lang.String)}.
//	 */
//	@Test
//	public final void testNaiveFileMetadataManager() {
//		fail("Not yet implemented"); // TODO
//	}

	/**
	 * Test method for {@link metadata.NaiveFileMetadataManager#getFieldPositions()}.
	 */
	@Test
	public final void testGetFieldPositionsHappyDay() {
		
		String[] expectedColNames= {"HF:Financing scheme","HC:Function","HP:Provider","LOCATION:Country","TIME:Year","MSR:Value"};
		List<String> colList = Arrays.asList(expectedColNames);
		int expectedSchemePos = colList.indexOf("HF:Financing scheme"); //0
		int expectedTimePos = colList.indexOf("TIME:Year"); 			//4
		
		Map<String, Integer> resultMap = metadataManager.getFieldPositions();
		int schemePos = resultMap.get("HF:Financing scheme");		
		int timePos = resultMap.get("TIME:Year");
		assertEquals(expectedSchemePos, schemePos);
		assertEquals(expectedTimePos, timePos);
	}

	/**
	 * Test method for {@link metadata.NaiveFileMetadataManager#getDataFile()}.
	 */
	@Test
	public final void testGetDataFileHappyDay() {
		File resultFile = metadataManager.getDataFile();
		File referenceFile = new File("./test/resources/input/simple.csv");

		if (!resultFile.exists())
			fail("The result file does not exist");
		try {
			assert(referenceFile.getCanonicalPath().equals(resultFile.getCanonicalPath()) );
		} catch (IOException e) {
			System.err.println("NaiveFileMAtadataManagerTest::testGetDataFileHappyDay() failed to contract ref and result files");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link metadata.NaiveFileMetadataManager#getSeparator()}.
	 */
	@Test
	public final void testGetSeparatorHappyDay() {
		String resultSep = metadataManager.getSeparator();
		assertEquals(resultSep, ",");
	}

	/**
	 * Test method for {@link metadata.NaiveFileMetadataManager#getColumnNames()}.
	 */
	@Test
	public final void testGetColumnNamesHappyDay() {
		String[] resColNames = metadataManager.getColumnNames();
		String[] expectedColNames= {"HF:Financing scheme","HC:Function","HP:Provider","LOCATION:Country","TIME:Year","MSR:Value"};
		for (int i =0; i< resColNames.length;i++) {
			if (!resColNames[i].equals(expectedColNames[i]))
				fail("Erroneous col name arrays");
		}
	}

	/**
	 * Test method for {@link metadata.NaiveFileMetadataManager#getAlias()}.
	 */
	@Test
	public final void testGetAliasHappyDay() {
		String resultAlias = metadataManager.getAlias();
		assertEquals(resultAlias, "defaultAlias");
	}

	//@Test(expected = Exception.class)
	@Test(expected = NullPointerException.class)
	public final void testGetDataFileWrongFile() {
		String alias = "defaultAlias";
		File f = new File("./test/resources/input/NowyouSeeMeNowYouDont.csv");
		String separator = ",";
		NaiveFileMetadataManager anotherMetadataManager = new NaiveFileMetadataManager(alias, f, separator);
		//Why not just @Test?
		//the main idea of this test is to show how to trap any null pointer exception that might fire inside your constructor
		//if a null pointer exception occurs within your code the above configuration of @Test will catch it
		//... so maybe this will not behave well in all implementations ...
	}
	

	
}//end class
