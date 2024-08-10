import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    private static Horse horseFirst;
    private static Horse horseSecond;

    @BeforeAll
    static void setUp() {
        horseFirst = new Horse("NameFirst", 1, 2);
        horseSecond = new Horse("NameSecond", 2);
    }

    @Test
    void constructor_horse_is_first_argument_equal_null_throw_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
    }

    @Test
    void constructor_horse_is_first_argument_equal_null_is_response_error() {
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        assertEquals("Name cannot be null.", response.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void constructor_horse_is_first_argument_equal_tab_characters_is_throw_IllegalArgumentException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void constructor_horse_is_first_argument_equal_tab_characters_is_response_error(String name) {
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));
        assertEquals("Name cannot be blank.", response.getMessage());
    }

    @Test
    void constructor_horse_is_second_argument_equal_negative_number_throw_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name", -1, 2));
    }

    @Test
    void constructor_horse_is_second_argument_equal_negative_number_is_response_error() {
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () -> new Horse("Name", -1, 2));
        assertEquals("Speed cannot be negative.", response.getMessage());
    }

    @Test
    void constructor_horse_is_third_argument_equal_negative_number_throw_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name", 1, -1));
    }

    @Test
    void constructor_horse_is_third_argument_equal_negative_number_is_response_error() {
        IllegalArgumentException response = assertThrows(IllegalArgumentException.class, () -> new Horse("Name", 1, -1));
        assertEquals("Distance cannot be negative.", response.getMessage());
    }

    @Test
    void getName() {
        assertEquals("NameFirst", horseFirst.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(1, horseFirst.getSpeed());
    }

    @Test
    void getDistance() {
        assertAll(
                () -> assertEquals(2, horseFirst.getDistance()),
                () -> assertEquals(0, horseSecond.getDistance())
        );
    }

    @Test
    void method_move_check_verify_getRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseFirst.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2})
    void method_move_check_getRandomDouble_sum(double numbers) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(numbers);
            double distance = horseFirst.getDistance();
            horseFirst.move();
            assertEquals(distance + horseFirst.getSpeed() * numbers, horseFirst.getDistance());
        }
    }
}