package file.manager;

public class StructuredFileManagerFactory {
	
	public StructuredFileManager createStructuredFileManager(){
			return new StructuredFileManager();
	}
}
/* public class MainEngineFactory{

	public MainEngine createMainEngine(String engineType) {
		if(engineType.equals("MainEngine"))
			return new MainEngine();
		else
			return null;
	}
}*/

