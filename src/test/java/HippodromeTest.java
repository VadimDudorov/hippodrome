import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    Hippodrome hippodrome;

    @BeforeEach
    void setUp() {
    }

    @Test
    void constructor_hippodrome_is_argument_equal_null_throw_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void constructor_hippodrome_is_argument_equal_null_response() {
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", response.getMessage());
    }

    @Test
    void constructor_hippodrome_is_empty_throw_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void constructor_hippodrome_is_empty_response() {
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", response.getMessage());
        ;
    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Name" + i, i));
        }
        hippodrome = new Hippodrome(horses);
        assertArrayEquals(horses.toArray(), hippodrome.getHorses().toArray(new Horse[0]));
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.spy(new Horse("Name", i)));
        }
        hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses){
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(new Horse("Name", 1, i));
        }
        hippodrome = new Hippodrome(horses);
        assertEquals(49, hippodrome.getWinner().getDistance());
    }
}