package markethelper.Model.BL;
import org.example.markethelper.Model.BL.PrimePart;
import org.example.markethelper.Model.BL.PrimeSet;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.mockito.Mockito;

import java.util.ArrayList;

public class TestPrimeSet {
    private static PrimeSet set;
    private static PrimePart part1;
    private static PrimePart part2;
    private static PrimePart part3;

    @BeforeAll
    public static void setUp() {
        TestPrimeSet.part1 = new PrimePart(1,"part1",15,15,"Bronze");
        TestPrimeSet.part2 = new PrimePart(2,"part2",15,15,"Bronze");
        TestPrimeSet.part3 = new PrimePart(3,"part3",15,15,"Bronze");
        ArrayList<PrimePart> primeParts = new ArrayList<>();
        primeParts.add(part1);
        primeParts.add(part2);
        primeParts.add(part3);
        TestPrimeSet.set = new PrimeSet(4,"test",primeParts,22);
    }

    @Nested
    @DisplayName("PrimeSet Test")
    class testPrimeSet {
        @Test
        @DisplayName("PrimeSet Test of get prime part")
        public void testPSPrimePart() {
            Assertions.assertEquals(part2,set.getPrimePart(1));
        }
        @Test
        @DisplayName("PrimeSet Test of computeSeperatedSet")
        public void testPSComputeSeperatedSet() {
            Assertions.assertEquals(45,set.getSeperatedPrice(),0.001);
        }
        @Test
        @DisplayName("PrimeSet Test of toStringArray")
        public void testPSToStringArray() {
            String[] expected = {"PrimeSet", "4", "test", "22", "1", "2", "3", null, null, null};
            Assertions.assertArrayEquals(expected,set.toStringArray());
        }

    }

}
