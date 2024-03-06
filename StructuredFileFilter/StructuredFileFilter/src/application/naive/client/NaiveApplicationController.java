package application.naive.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

import application.chart.management.VisualizationEngine;
import application.jtable.management.JTableViewer;
import file.manager.StructuredFileManagerFactory;
import file.manager.StructuredFileManagerInterface;

public class NaiveApplicationController {

	private final StructuredFileManagerInterface fileManager;
	private final VisualizationEngine visualizationEngine;
	
	public VisualizationEngine getVisualizationEngine() {
		return visualizationEngine;
	}
	
	public StructuredFileManagerInterface getFileManager() {
		return fileManager;
	}

	public NaiveApplicationController() {
		StructuredFileManagerFactory engineFactory = new StructuredFileManagerFactory();
		this.fileManager = engineFactory.createStructuredFileManager();
		this.visualizationEngine = new VisualizationEngine();
	}

	public File registerFile(String pAlias, String pPath, String pSeparator) {
		File resultFile = null;
		try {
			resultFile = this.fileManager.registerFile(pAlias, pPath, pSeparator);
		} catch (NullPointerException e) {
			System.err.println("NaiveApplicationController::registerFile NullPointerException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("NaiveApplicationController::registerFile IOException");
			e.printStackTrace();
		}
		return resultFile;
	}

	public List<String[]> executeFilterAndShowJTable(String alias, Map<String, List<String>> atomicFilters,
			String outputFileName) {
		List<String[]> result;
		String[] columnNames;
		int numRows;

		result = fileManager.filterStructuredFile(alias, atomicFilters);
		columnNames = fileManager.getFileColumnNames(alias);

		// Show in sysout
		System.out.println("\n\n");
		numRows = fileManager.printResultsToPrintStream(result, System.out);
		System.out.println("\n NUM ROWS: " + numRows + "\n");

		// try to save in a file instead of sysout
		saveToResultTextFile(outputFileName, result);

		// show in JTable
		showJTableViewer(result, columnNames);
		return result;
	}

	public int saveToResultTextFile(String outputFileName, List<String[]> result) {
		int numRows = 0;
		FileOutputStream fOutStream = null;
		try {
			fOutStream = new FileOutputStream(outputFileName);
		} catch (FileNotFoundException e) {
			System.err.println("NaiveClient:: failed to open fout stream");
			e.printStackTrace();
		}
		PrintStream pOutStream = new PrintStream(fOutStream);
		numRows = fileManager.printResultsToPrintStream(result, pOutStream);
		System.out.println("\n SAVED NUM ROWS: " + numRows + " in file: " + outputFileName + "\n");

		pOutStream.close();
		try {
			fOutStream.close();
		} catch (IOException e) {
			System.err.println("NaiveClient:: failed to close fout stream");
			e.printStackTrace();
		}
		return numRows;
	}

	private void showJTableViewer(List<String[]> result, String[] columnNames) {
		JTableViewer jTableViewer;

		jTableViewer = new JTableViewer(result, columnNames);
		jTableViewer.createAndShowJTable();
	}

	public void showSingleSeriesBarChart(String pAlias, List<String[]> series, String pXAxisName, String pYAxisName,
			String outputFileName) {
		String[] columnNames = this.fileManager.getFileColumnNames(pAlias);
		List<String> colList = Arrays.asList(columnNames);
		int xPos = colList.indexOf(pXAxisName);
		int yPos = colList.indexOf(pYAxisName);
		this.visualizationEngine.showSingleSeriesBarChart(pAlias, series, pXAxisName, pYAxisName, outputFileName, xPos,
				yPos);
	}

	public void showSingleSeriesLineChart(String pAlias, List<String[]> series, String pXAxisName, String pYAxisName,
			String outputFileName) {
		String[] columnNames = this.fileManager.getFileColumnNames(pAlias);
		List<String> colList = Arrays.asList(columnNames);
		int xPos = colList.indexOf(pXAxisName);
		int yPos = colList.indexOf(pYAxisName);
		this.visualizationEngine.showSingleSeriesLineChart(pAlias, series, pXAxisName, pYAxisName, outputFileName, xPos,
				yPos);
	}
	
	public List<String[]> executeFilter(String alias, Map<String, List<String>> atomicFilters) {
		List<String[]> result;
		result = fileManager.filterStructuredFile(alias, atomicFilters);
		return result;
	}
	
	public String[] getColumnNames(String alias) {
		return fileManager.getFileColumnNames(alias);
	}
	
	
    public static void main(String[] args){
    	NaiveApplicationController appController = new NaiveApplicationController();
    	Map<String, List<String[]>> filters = new HashMap<String, List<String[]>>();
    	int action = 0;
		Scanner userInput = new Scanner(System.in);
    	while(action != 5) {
    		System.out.println("\nChoose action number: ");
    		System.out.println("1) Read File              (input path, separator, alias)");
    		System.out.println("2) Place Filter           (category, values, name)");
    		System.out.println("3) Save Result to File    (filter name, output path)");
    		System.out.println("4) Visualize Result       (filter name, choose output method)");
    		System.out.println("5) Exit");
    		action = userInput.nextInt();
    		userInput.nextLine();
    		if (action == 1) {
    	        String alias = null;
    	        String separator = null;
    	        String inputPath = null;
    			System.out.println("Input path:");
    			inputPath = userInput.nextLine();
    			System.out.println("Input separator:");
    			separator = userInput.nextLine();
    			System.out.println("Input alias:");
    			alias = userInput.nextLine();
    			appController.registerFile(alias, inputPath, separator);
    			System.out.println("File registered.");
    		} else if (action == 2) {
    			Map<String, List<String>> filter = new HashMap<String, List<String>>();
    			String filterCategory = null;
    			List<String> filterValues = new ArrayList<String>();
    			String alias = null;
    			String name = null;
    			System.out.println("Select file to put filter on with alias:");
    			alias = userInput.nextLine();
    			
    			while(true) {
        			System.out.println("Add filter field:");
        			System.out.println("e.g. GEO:countriesAndTerritories");
        			filterCategory = userInput.nextLine();
        			System.out.println("Add filter values (separated with comma if more than one):");
        			System.out.println("e.g. Greece, Austria, Italy");
        			filterValues = Arrays.asList(userInput.nextLine().replaceAll("\\s", "").split(","));
        			filter.put(filterCategory, filterValues);
        			System.out.println("Do you want to add another field to the filter? (y/n)");
        			if (!userInput.nextLine().equals("y")) {
        				break;
        			}
    			}
    			
    			List<String[]> result = appController.executeFilter(alias, filter);
    			System.out.println("Filter applied successfully.");
    			System.out.println("Choose a spicy name:");
    			name = userInput.nextLine();
    			filters.put(name, result);
    		} else if (action == 3) {
    			List<String[]> filterResult = null;
    			String filterName = null;
    			String outputPath = null;
    			String answer = null;
    			System.out.println("Choose filter result to put into file:");
    			filterName = userInput.nextLine();
    			filterResult = filters.get(filterName);
    			System.out.println("Give output path:");
    			outputPath = userInput.nextLine();
    			appController.saveToResultTextFile(outputPath, filterResult);
    			System.out.println("Result Saved at " + outputPath + ".");
    			System.out.println("Do you want to show Jtable? (y/n)");
    			answer = userInput.nextLine();
    			if (answer.equals("y")) {
    				String alias = null;
    				System.out.println("Choose alias of file:");
    				alias = userInput.nextLine();
    				appController.showJTableViewer(filterResult, appController.getColumnNames(alias));
    			}
    		} else if (action == 4) {
    			int chartNumber = 0;
    			List<String[]> filter = null;
    			String filterName = null;
    			String alias = null;
    			String xAxis = null;
    			String yAxis = null;
    			String outputFilename = "";
    			System.out.println("Choose filter result to visualize:");
    			filterName = userInput.nextLine();
    			filter = filters.get(filterName);
    			System.out.println("Choose visualization method:");
    			System.out.println("1) Bar Chart");
    			System.out.println("2) Line Chart");
    			chartNumber = userInput.nextInt();
    			userInput.nextLine();
				System.out.println("Choose alias of file:");
				alias = userInput.nextLine();
				System.out.println("Choose X axis name:");
				xAxis = userInput.nextLine();
				System.out.println("Choose Y axis name:");
				yAxis = userInput.nextLine();
				System.out.println("Do you want to save it as png? (y/n)");
				if (userInput.nextLine().equals("y")) {
	    			System.out.println("Give output filename/path:");
	    			outputFilename = userInput.nextLine();
				}
    			if (chartNumber == 1) {
    				appController.showSingleSeriesBarChart(alias, filter, xAxis, yAxis, outputFilename);
    			} else if (chartNumber == 2) {
    				appController.showSingleSeriesLineChart(alias, filter, xAxis, yAxis, outputFilename);
    			}
    		} else if (action == 5) {
    			System.exit(0);
    		} else {
    			System.out.println("Invalid number.");
    		}
    	}
    	userInput.close();
	}
   /* public static void main1(String[] args){
        String alias = "test";
        String separator = ",";
        String path = "";
        
        NaiveApplicationController appController = new NaiveApplicationController();
        File file = appController.registerFile(alias, path, separator);
        List<String[]> recordList = new ArrayList<String[]>();
        
        Map<String, List<String>> filter = new HashMap<String, List<String>>();
        String firstFilter = "GEO:countriesAndTerritories";
        List<String> firstList = new ArrayList<String>();
        firstList.add("Greece"); firstList.add("Germany");
        String secondFilter = "MSR:deaths";
        List<String> secondList = new ArrayList<String>();
        secondList.add("8"); secondList.add("9");
        filter.put(firstFilter, firstList);
        filter.put(secondFilter, secondList);
        
        String output = "";
        recordList = appController.executeFilterAndShowJTable(alias, filter, output);
    }
    */
	
}

// end class

