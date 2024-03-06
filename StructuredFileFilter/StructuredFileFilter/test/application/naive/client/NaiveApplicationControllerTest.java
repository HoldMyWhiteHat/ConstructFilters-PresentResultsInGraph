/**
 * 
 */
package application.naive.client;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author pvassil
 *
 */
public class NaiveApplicationControllerTest {
	private static NaiveApplicationController appController;
	private static List<String[]> result ;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		appController = new NaiveApplicationController();
		result = new ArrayList<String[]>();
		
		String sAlias = "simple";		
		String sSeparator = ",";
		appController.registerFile(sAlias, "./test/resources/input/simple.csv", sSeparator);


		String[] result1 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2010", "4261.055"};
		String[] result2 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2011", "4345.16"};
		
		result.add(result1); result.add(result2);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link application.naive.client.NaiveApplicationController#showSingleSeriesLineChart(java.lang.String, java.util.List, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testShowSingleSeriesLineChartHappyDay() {
		String outputFilePath = "./test/resources/output/naiveTestLine.png";
	    File cleanUp = new File(outputFilePath);
	    if(cleanUp.exists()) {
	    	boolean deletionStatus = cleanUp.delete(); 
	    	if (deletionStatus == false) { 
	    		fail("NaiveAppControllerTest::testBarChartHappy(): could not cleanup");
	    	} 
	    }
		//"HF:Financing scheme","HC:Function","HP:Provider","LOCATION:Country","TIME:Year","MSR:Value"
		appController.showSingleSeriesLineChart("simple", result, "TIME:Year", "MSR:Value", "./test/resources/output/naiveTestLine.png");
		
		File refFile = new File(outputFilePath);
		if (!refFile.exists())
			fail("NaiveAppControllerTest::testLineChartHappy(): output was not created");
		assertTrue(refFile.exists()); 
	}//end testLineHD


	/**
	 * Test method for {@link application.naive.client.NaiveApplicationController#showSingleSeriesBarChart(java.lang.String, java.util.List, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testShowSingleSeriesBarChartHappyDay() {
		String outputFilePath = "./test/resources/output/naiveTestBar.png";
	    File cleanUp = new File(outputFilePath);
	    if(cleanUp.exists()) {
	    	boolean deletionStatus = cleanUp.delete(); 
	    	if (deletionStatus == false) { 
	    		fail("NaiveAppControllerTest::testBarChartHappy(): could not cleanup");
	    	} 
	    }
		//"HF:Financing scheme","HC:Function","HP:Provider","LOCATION:Country","TIME:Year","MSR:Value"
		appController.showSingleSeriesBarChart("simple", result, "TIME:Year", "MSR:Value", "./test/resources/output/naiveTestBar.png");
		
		File refFile = new File(outputFilePath);
		if (!refFile.exists())
			fail("NaiveAppControllerTest::testBarChartHappy(): output was not created");
		assertTrue(refFile.exists()); 
	}//end testBarHD
	
}//end class
