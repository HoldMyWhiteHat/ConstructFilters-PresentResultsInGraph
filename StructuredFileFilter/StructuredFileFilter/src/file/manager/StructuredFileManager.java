package file.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


import filtering.FilteringEngine;
import metadata.MetadataManager;

public class StructuredFileManager implements StructuredFileManagerInterface {

	private List<MetadataManager> metadataManagerList;
	
	public StructuredFileManager(){
		this.metadataManagerList = new ArrayList<MetadataManager>();
	}

	@Override
	public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException {
		for (MetadataManager manager : metadataManagerList){
			if (manager.getAlias().equals(pAlias)){
				return manager.getDataFile();
			}
		}
		MetadataManager metadataManager = new MetadataManager(pAlias, pPath, pSeparator);
		metadataManagerList.add(metadataManager);
		return metadataManager.getDataFile();
	}

	@Override
	public String[] getFileColumnNames(String pAlias) {
		for (MetadataManager manager : metadataManagerList){
			if (manager.getAlias().equals(pAlias)){
				return manager.getColumnNames();
			}
		}
		return null;
	}

	@Override
	public List<String[]> filterStructuredFile(String pAlias, Map<String, List<String>> pAtomicFilters) {
		MetadataManager metadataManager = null;
		List<String[]> resultsList;
		for (MetadataManager manager : metadataManagerList){
			if (manager.getAlias().equals(pAlias)){
				metadataManager = manager;
				break;
			}
		}
		FilteringEngine filteringEngine = new FilteringEngine();
		if(filteringEngine.setupFilteringEngine(pAtomicFilters, metadataManager) == 0){
			resultsList = filteringEngine.workWithFile();
			return resultsList;
		}
		return null;
	}

	@Override
	public int printResultsToPrintStream(List<String[]> recordList, PrintStream pOut) {
		if (recordList == null) return -1;
		if (pOut == null) return -1;
		for (String record[] : recordList){
			for (int i = 0; i < record.length; i++){
				pOut.print(record[i] + " ");
			}
			pOut.println();
		}
		return 0;
	}
	
}
