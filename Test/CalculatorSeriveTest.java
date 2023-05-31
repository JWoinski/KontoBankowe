import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorSeriveTest {
    CalculatorSerive calculatorSerive;

    @BeforeEach
    public void setup() {
        calculatorSerive = new CalculatorSerive();
    }

    @Test
    public void addTest() {
        // given
        int a = 5;
        int b = 10;
        int expected = 15;

        // when
        final int actual = calculatorSerive.sum(a, b);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void subTest() {
        final int actual = calculatorSerive.sub(10, 5);

        Assertions.assertEquals(5, actual);
    }
}