import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;



public class MainTest {

    @Disabled("Тест слишком затратный")
    @Timeout(value=22)
    @Test
    public void lessThanSomeTime() throws Exception {
        Main.main(new String[]{"1"});
    }
}
