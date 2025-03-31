package markethelper.Model.BL;
import org.example.markethelper.Model.BL.PrimePart;
import org.example.markethelper.Model.BL.Relic;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.mockito.Mockito;

import java.util.ArrayList;

public class TestRelic {
    private static Relic relic;
    private static PrimePart part1;
    private static PrimePart part2;
    private static PrimePart part3;
    private static PrimePart part4;
    private static PrimePart part5;
    private static PrimePart part6;

    @BeforeAll
    public static void setUp() {
        part1 = new PrimePart(1, "part1", 10, 10, "Bronze");
        part2 = new PrimePart(2, "part2", 15, 15, "Bronze");
        part3 = new PrimePart(3, "part3", 20, 20, "Silver");
        part4 = new PrimePart(4, "part4", 25, 25, "Silver");
        part5 = new PrimePart(5, "part5", 30, 30, "Gold");
        part6 = new PrimePart(6, "part6", 10, 10, "Bronze");

        ArrayList<PrimePart> primeParts = new ArrayList<>();
        primeParts.add(part1);
        primeParts.add(part2);
        primeParts.add(part3);
        primeParts.add(part4);
        primeParts.add(part5);
        primeParts.add(part6);

        relic = new Relic(1, "TestRelic", primeParts, 100);
    }

    @Nested
    @DisplayName("Relic Tests")
    class RelicTests {

        @Test
        @DisplayName("Test computeSeperatedSet calculation")
        public void testRComputeSeperatedSet() {
            Assertions.assertEquals(110, relic.getSeperatedPrice());
        }

        @Test
        @DisplayName("Relic Test of AddPrimeParts that is called in constructor")
        public void testRPrimePartsEquality() {
            ArrayList<PrimePart> expectedParts = new ArrayList<>();
            expectedParts.add(part1);
            expectedParts.add(part2);
            expectedParts.add(part3);
            expectedParts.add(part4);
            expectedParts.add(part5);
            expectedParts.add(part6);

            for (int i = 0; i < expectedParts.size(); i++) {
                Assertions.assertEquals(expectedParts.get(i), relic.getPrimePart(i));
            }
        }
        @Test
        @DisplayName("Test adding PrimeParts beyond limits throws exception")
        public void testRAddPrimePartsLimit() {
            PrimePart extraBronze = new PrimePart(7, "extraBronze", 10, 10, "Bronze");
            PrimePart extraSilver = new PrimePart(8, "extraSilver", 20, 20, "Silver");
            PrimePart extraGold = new PrimePart(9, "extraGold", 30, 30, "Gold");

            ArrayList<PrimePart> extraParts = new ArrayList<>();
            extraParts.add(extraBronze);

            Exception bronzeException = Assertions.assertThrows(IllegalArgumentException.class, () -> relic.AddPrimeParts(extraParts));
            Assertions.assertEquals("Cannot add more PrimeParts of this rarity.(Bronze)", bronzeException.getMessage());

            extraParts.clear();
            extraParts.add(extraSilver);
            Exception silverException = Assertions.assertThrows(IllegalArgumentException.class, () -> relic.AddPrimeParts(extraParts));
            Assertions.assertEquals("Cannot add more PrimeParts of this rarity.(Silver)", silverException.getMessage());

            extraParts.clear();
            extraParts.add(extraGold);
            Exception goldException = Assertions.assertThrows(IllegalArgumentException.class, () -> relic.AddPrimeParts(extraParts));
            Assertions.assertEquals("Cannot add more PrimeParts of this rarity.(Gold)", goldException.getMessage());
        }

        @Test
        @DisplayName("Test findBronze, findSilver, and findGold")
        public void testRFindMethods() {
            ArrayList<PrimePart> testParts = new ArrayList<>();
            testParts.add(part1);
            testParts.add(part2);
            testParts.add(part3);
            testParts.add(part4);
            testParts.add(part5);
            testParts.add(part6);

            Assertions.assertEquals(3, relic.findBronze(testParts));
            Assertions.assertEquals(2, relic.findSilver(testParts));
            Assertions.assertEquals(1, relic.findGold(testParts));
        }

        @Test
        @DisplayName("Test toStringArray output")
        public void testRToStringArray() {
            String[] expected = {"Relic", "1", "TestRelic", "100", "1", "2", "3", "4", "5", "6"};
            Assertions.assertArrayEquals(expected, relic.toStringArray());
        }
    }
}

