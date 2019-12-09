package cz.neumimto.rpg.api.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemDamageProcessorTest {

    @Test
    void testSum() {
        double v = new Sum().get(10.0D, 5.0D);
        Assertions.assertEquals(15.0D, v);
    }

    @Test
    void testMax() {
        double v = new Max().get(10.0D, 5.0D);
        Assertions.assertEquals(10.0D, v);
    }
}