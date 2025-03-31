package markethelper.Model;

import org.example.markethelper.Model.IdDispenser;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestIdDispenser {
    private IdDispenser idDispenser;

    @BeforeEach
    public void setUp() {
        ArrayList<Integer> used = new ArrayList<>();
        used.add(1);
        used.add(3);
        used.add(5);
        idDispenser = new IdDispenser(used);
    }

    @Nested
    @DisplayName("IdDispenser Test")
    class testIdDispenser {

        @Test
        @DisplayName("IdDispenser Test of Initialization - Empty Set")
        public void testIDInitializationEmpty() {
            IdDispenser emptyDispenser = new IdDispenser(new ArrayList<>());
            Assertions.assertTrue(emptyDispenser.getUsedIDs().isEmpty());
        }

        @Test
        @DisplayName("IdDispenser Test of Initialization - Existing IDs")
        public void testIDInitializationWithIDs() {
            Assertions.assertTrue(idDispenser.getUsedIDs().containsAll(Set.of(1, 3, 5)));
        }

        @Test
        @DisplayName("IdDispenser Test of getNextID - Assigns Smallest Unused ID")
        public void testIDGetNextID() {
            Assertions.assertEquals(2, idDispenser.getNextID());
            Assertions.assertEquals(4, idDispenser.getNextID());
            Assertions.assertEquals(6, idDispenser.getNextID());
        }

        @Test
        @DisplayName("IdDispenser Test of getNextID - Assigns Sequential IDs After Max")
        public void testIDGetNextIDSequential() {
            idDispenser.getNextID(); // 2
            idDispenser.getNextID(); // 4
            idDispenser.getNextID(); // 6
            Assertions.assertEquals(7, idDispenser.getNextID());
            Assertions.assertEquals(8, idDispenser.getNextID());
        }

        @Test
        @DisplayName("IdDispenser Test of getNextID - Fills Deleted Gaps")
        public void testIDGetNextIDFillsGaps() {
            idDispenser.getNextID();//2
            idDispenser.getNextID();//4
            idDispenser.deleteID(3);
            Assertions.assertEquals(3, idDispenser.getNextID());
        }

        @Test
        @DisplayName("IdDispenser Test of getNextID - Generates ID After Highest")
        public void testIDGetNextIDAfterHighest() {
            idDispenser.getNextID(); // 2
            idDispenser.getNextID(); // 4
            idDispenser.getNextID(); // 6
            idDispenser.getNextID(); // 7
            idDispenser.getNextID(); // 8
            Assertions.assertEquals(9, idDispenser.getNextID());
        }

        @Test
        @DisplayName("IdDispenser Test of deleteID and reuse")
        public void testIDDeleteAndReuseID() {
            idDispenser.deleteID(1);
            Assertions.assertFalse(idDispenser.getUsedIDs().contains(1));
            Assertions.assertEquals(1, idDispenser.getNextID());
        }

        @Test
        @DisplayName("IdDispenser Test of deleteID - Non-Existent ID")
        public void testIDDeleteNonExistentID() {
            int sizeBefore = idDispenser.getUsedIDs().size();
            idDispenser.deleteID(99);
            Assertions.assertEquals(sizeBefore, idDispenser.getUsedIDs().size());
        }

        @Test
        @DisplayName("IdDispenser Test of delete all IDs and generate new ones")
        public void testIDDeleteAllIDs() {
            ArrayList<Integer> ids = new ArrayList<>(idDispenser.getUsedIDs());
            for (int id : ids) {
                idDispenser.deleteID(id);
            }
            Assertions.assertEquals(1, idDispenser.getNextID());
        }

    }
}
