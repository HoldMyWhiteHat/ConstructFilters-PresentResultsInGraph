
package filtering;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import metadata.MetadataManagerInterface;

public class FilteringEngine implements FilteringEngineInterface {

	MetadataManagerInterface metadataManager;
	Map<String, List<String>> atomicFilters;

	@Override
	public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters,
			MetadataManagerInterface pMetadataManager) {
		if (pMetadataManager == null) return -1;
		this.metadataManager = pMetadataManager;
		if (pAtomicFilters == null) return -1;
		this.atomicFilters = pAtomicFilters;
		return 0;
	}

	@Override
	public List<String[]> workWithFile() {
		List<String[]> FilteredList = new ArrayList<String[]>();
		File file = this.metadataManager.getDataFile();
		String separator = this.metadataManager.getSeparator();
		String str;
		try {
			Scanner allLines = new Scanner(file);
			while(allLines.hasNextLine()){
				str = allLines.nextLine();
				String[] tempString = str.split(separator);
				if(checkLineWithFilter(tempString)){
					FilteredList.add(tempString);
				}
			}
			allLines.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		return FilteredList;
	}
	
	private boolean checkLineWithFilter(String[] line){
		Map<String, Integer> fileMap = this.metadataManager.getFieldPositions();
		for (Map.Entry<String, List<String>> entry : atomicFilters.entrySet()){
            String key = entry.getKey();
            int index = fileMap.get(key);
            String field = line[index];
            if (!entry.getValue().contains(field)) return false;
        }
		return true;
	}

}
