import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ file.manager.StructuredFileManagerTest.class, filtering.FilteringEngineTest.class,
		metadata.NaiveFileMetadataManagerTest.class, application.naive.client.NaiveApplicationControllerTest.class })
public class AllTests {

}
