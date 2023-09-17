package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {
    
    @Test
    public void AppFinalValues() {
        assertEquals(480, App.HEIGHT);
        assertEquals(480, App.WIDTH);
        assertEquals(60, App.FPS);
        assertEquals(32, App.IMAGE_SIZE);
    }



    
}
