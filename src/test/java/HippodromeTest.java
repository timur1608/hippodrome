import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HippodromeTest {

    @Test
    public void nullListHorses(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals(e.getMessage(), "Horses cannot be null.");
    }

    @Test
    public void emptyListHorses(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals(e.getMessage(), "Horses cannot be empty.");
    }

    @Test
    public void correctGetHorses(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; ++i){
            horses.add(new Horse(String.valueOf(i), (double)i / 10, 0));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(hippodrome.getHorses(), horses);
    }

    @Test
    public void correctGetWinner(){
        List<Horse> horses = new ArrayList<>();
        int maxDistance = 0;
        for (int i = 0; i < 30; ++i){
            horses.add(new Horse(String.valueOf(i), (double)i / 10, i));
            maxDistance = Integer.max(maxDistance, i);
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(maxDistance, hippodrome.getWinner().getDistance());
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    public void horseAllMove(){
        List<Horse> horses = new ArrayList<>();
        int maxDistance = 0;
        for (int i = 0; i < 50; ++i){
            horses.add(Mockito.spy(new Horse(String.valueOf(i), (double)i / 10, i)));
            maxDistance = Integer.max(maxDistance, i);
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        horses.forEach(horse -> Mockito.verify(horse).move());

    }
}
