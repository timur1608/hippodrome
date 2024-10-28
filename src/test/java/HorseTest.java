import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    public void nullNameHorse(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 100));
        try{
            new Horse(null, 10, 100);
        } catch (IllegalArgumentException e){
            assertEquals(e.getMessage(), "Name cannot be null.");
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\t "})
    public void emptyNameHorse(String argument){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 10, 100));
        assertEquals(e.getMessage(), "Name cannot be blank.");
    }

    @Test
    public void negativeSpeedException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 100));
        assertEquals(e.getMessage(), "Speed cannot be negative.");
    }

    @Test
    public void negativeDistanceException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 10, -100));
        assertEquals(e.getMessage(), "Distance cannot be negative.");
    }

    @Test
    public void correctGetName(){
        String name = "name";
        Horse horse = new Horse(name, 2.5, 1);
        assertEquals(name, horse.getName());
    }

    @Test
    public void correctGetSpeed(){
        double speed = 2.5;
        Horse horse = new Horse("name", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void correctGetDistance(){
        double distance = 1;
        Horse horse = new Horse("name", 2.5, distance);
        assertEquals(distance, horse.getDistance());
        Horse horse1 = new Horse("Name", 3);
        assertEquals(0, horse1.getDistance());
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    public void correctMove(){
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse mockhorse = Mockito.spy(new Horse("name", 3.0, 1));
            mockhorse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ExtendWith(MockitoExtension.class)
    @ValueSource(doubles={1.5, 2.5, 3.5, 4.5})
    public void correctMoveDistance(double value){
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2,0.9)).thenReturn(value);
            Horse horse = Mockito.spy(new Horse("name", 3.0));
            double prevDistance = horse.getDistance();
            horse.move();
            assertEquals(prevDistance + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9), horse.getDistance());
        }
    }


}
