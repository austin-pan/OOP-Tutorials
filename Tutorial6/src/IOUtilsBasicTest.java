import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;

public class IOUtilsBasicTest {

    @Test
    public void loadFromFolderTest() {
        HashMap<String, GameGrid> result = IOUtils.loadFromFolder("nonexistingfolder");
    }

}
