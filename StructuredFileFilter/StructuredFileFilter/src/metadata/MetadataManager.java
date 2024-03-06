package metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MetadataManager implements MetadataManagerInterface {
	
	private File file;
	private String alias;
	private String path;
	private String separator;
	
	private Map<String, Integer> fieldPositions;
	private String[] columnNames;
	
	public MetadataManager(String alias, String path, String separator) {
		this.alias = alias;
		this.separator = separator;
		this.path = path;
		this.file = new File(this.path);
		setFields();
	}
	
	void setFields(){
		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		String str = null;
		try {
			Scanner firstLine = new Scanner(this.file);
			str = firstLine.nextLine();
			firstLine.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		String[] tempString = str.split(this.separator);
		int n = tempString.length;
		tempString[0] = tempString[0].replace("\"", "");
		tempString[n-1] = tempString[n-1].replace("\"", "");
		for (int i = 0; i < n; i++){
			tempMap.put(tempString[i], i);
		}
		this.fieldPositions = tempMap;
		this.columnNames = tempString;
	}
	
	@Override
	public Map<String, Integer> getFieldPositions() {
		return fieldPositions;
	}

	@Override
	public File getDataFile() {
		return file;
	}

	@Override
	public String getSeparator() {
		return separator;
	}
	
	public String getAlias(){
		return alias;
	}

	@Override
	public String[] getColumnNames() {
		return columnNames;
	}

}


