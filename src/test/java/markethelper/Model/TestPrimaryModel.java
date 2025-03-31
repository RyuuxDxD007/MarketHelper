package markethelper.Model;

import org.example.markethelper.Model.BL.*;
import org.example.markethelper.Model.DAL.Item.*;
import org.example.markethelper.Model.DAL.Mod.*;
import org.example.markethelper.Model.DAL.PrimePart.*;
import org.example.markethelper.Model.DAL.PrimeSet.*;
import org.example.markethelper.Model.DAL.Riven.*;
import org.example.markethelper.Model.PrimaryModel;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class PrimaryModelTest {

    private PrimaryModel primaryModel;
    private IItemDAO mockItemDAO;
    private IModDAO mockModDAO;
    private IRivenDAO mockRivenDAO;
    private IPrimePartDAO mockPrimePartDAO;
    private IPrimeSetDAO mockPrimeSetDAO;

    @BeforeEach
    void setUp() throws Exception {
        mockItemDAO = Mockito.mock(IItemDAO.class);
        mockModDAO = Mockito.mock(IModDAO.class);
        mockRivenDAO = Mockito.mock(IRivenDAO.class);
        mockPrimePartDAO = Mockito.mock(IPrimePartDAO.class);
        mockPrimeSetDAO = Mockito.mock(IPrimeSetDAO.class);

        primaryModel = new PrimaryModel();

        setField(primaryModel, "itemDAO", mockItemDAO);
        setField(primaryModel, "modDAO", mockModDAO);
        setField(primaryModel, "rivenDAO", mockRivenDAO);
        setField(primaryModel, "primePartDAO", mockPrimePartDAO);
        setField(primaryModel, "primeSetDAO", mockPrimeSetDAO);
    }


    private void setField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private boolean getBooleanField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getBoolean(object);
    }

    @Test
    void testFilteringByMinPrice() {
        Item item1 = new Item(1, "Item1", 100);
        Item item2 = new Item(2, "Item2", 200);

        ArrayList<Item> mockItems = new ArrayList<>();
        mockItems.add(item1);
        mockItems.add(item2);

        when(mockItemDAO.getItems()).thenReturn(mockItems);

        primaryModel.boolChange("select-min");
        primaryModel.updateMinI("150");

        ArrayList<Item> filteredItems = primaryModel.Filtring();

        Assertions.assertEquals(1, filteredItems.size());
        Assertions.assertEquals(200, filteredItems.get(0).getPrice());
        Assertions.assertFalse(filteredItems.contains(item1));
    }

    @Test
    void testFilteringByMaxPrice() {
        Item item1 = new Item(1, "Item1", 100);
        Item item2 = new Item(2, "Item2", 200);

        ArrayList<Item> mockItems = new ArrayList<>();
        mockItems.add(item1);
        mockItems.add(item2);

        when(mockItemDAO.getItems()).thenReturn(mockItems);

        primaryModel.boolChange("select-max");
        primaryModel.updateMaxI("150");

        ArrayList<Item> filteredItems = primaryModel.Filtring();

        Assertions.assertEquals(1, filteredItems.size());
        Assertions.assertEquals(100, filteredItems.get(0).getPrice());
        Assertions.assertFalse(filteredItems.contains(item2));
    }

    @Test
    void testFilteringByItemType() {
        Item item1 = new Mod(1, "Mod1", 50, "V");
        Item item2 = new PrimePart(2, "PrimePart1", 100, 45, "Gold");

        ArrayList<Item> mockItems = new ArrayList<>();
        mockItems.add(item1);
        mockItems.add(item2);

        when(mockItemDAO.getItems()).thenReturn(mockItems);

        primaryModel.boolChange("select-prime");

        ArrayList<Item> filteredItems = primaryModel.Filtring();

        Assertions.assertEquals(1, filteredItems.size());
        Assertions.assertTrue(filteredItems.get(0) instanceof Mod);
        Assertions.assertFalse(filteredItems.contains(item2));
    }

    @Test
    void testFilteringByModType() {
        Mod mod1 = new Mod(1, "Mod1", 50, "V");
        Mod mod2 = new Mod(2, "Mod2", 100, "Aura");

        ArrayList<Mod> mockMods = new ArrayList<>();
        mockMods.add(mod1);
        mockMods.add(mod2);

        when(mockModDAO.getMods()).thenReturn(mockMods);

        primaryModel.boolChange("select-mod");

        ArrayList<Item> filteredItems = primaryModel.Filtring();

        Assertions.assertEquals(0, filteredItems.size());
        Assertions.assertTrue(filteredItems.isEmpty());
    }

    @Test
    void testFilteringByRiven() {
        Riven riven1 = new Riven(1, "Riven1", 100, "R", 5);
        Riven riven2 = new Riven(2, "Riven2", 200, "D", 10);

        ArrayList<Riven> mockRivens = new ArrayList<>();
        mockRivens.add(riven1);
        mockRivens.add(riven2);

        when(mockRivenDAO.getRivens()).thenReturn(mockRivens);

        primaryModel.boolChange("select-riven");

        ArrayList<Item> filteredItems = primaryModel.Filtring();

        Assertions.assertEquals(0, filteredItems.size());
        Assertions.assertTrue(filteredItems.isEmpty());
    }

    @Test
    void testFilteringByPrimePart() {
        PrimePart part1 = new PrimePart(1, "Part1", 50, 15, "Bronze");
        PrimePart part2 = new PrimePart(2, "Part2", 100, 45, "Silver");

        ArrayList<PrimePart> mockParts = new ArrayList<>();
        mockParts.add(part1);
        mockParts.add(part2);

        when(mockPrimePartDAO.getPrimeParts()).thenReturn(mockParts);

        primaryModel.boolChange("select-prime");

        ArrayList<Item> filteredItems = primaryModel.Filtring();

        Assertions.assertEquals(0, filteredItems.size());
        Assertions.assertTrue(filteredItems.isEmpty());
    }


    @Test
    void testToggleMinBFlag() throws Exception {
        primaryModel.boolChange("select-min");
        Assertions.assertTrue(getBooleanField(primaryModel, "minB"));
    }

    @Test
    void testToggleMaxBFlag() throws Exception {
        primaryModel.boolChange("select-max");
        Assertions.assertTrue(getBooleanField(primaryModel, "maxB"));
    }

    @Test
    void testToggleModBFlag() throws Exception {
        primaryModel.boolChange("select-mod");
        Assertions.assertTrue(getBooleanField(primaryModel, "modB"));
    }

    @Test
    void testToggleRivenBFlag() throws Exception {
        primaryModel.boolChange("select-riven");
        Assertions.assertTrue(getBooleanField(primaryModel, "rivenB"));
    }

    @Test
    void testTogglePrimePartBFlag() throws Exception {
        primaryModel.boolChange("select-prime");
        Assertions.assertTrue(getBooleanField(primaryModel, "primeB"));
    }
    @Test
    void testSetFilteringByMinPrice() {
        PrimeSet set1 = new PrimeSet(1, "Set1", new ArrayList<>(), 100);
        PrimeSet set2 = new PrimeSet(2, "Set2", new ArrayList<>(), 200);

        ArrayList<PrimeSet> mockSets = new ArrayList<>(Arrays.asList(set1, set2));

        when(mockPrimeSetDAO.getAllPrimeSets()).thenReturn(mockSets);

        primaryModel.boolChange("select-min-set");
        primaryModel.updateMinI("150");

        ArrayList<PrimeSet> filteredSets = primaryModel.SetFiltring();

        Assertions.assertEquals(1, filteredSets.size());
        Assertions.assertEquals(200, filteredSets.get(0).getSetPrice());
    }

    @Test
    void testSetFilteringByMaxPrice() {
        PrimeSet set1 = new PrimeSet(1, "Set1", new ArrayList<>(), 100);
        PrimeSet set2 = new PrimeSet(2, "Set2", new ArrayList<>(), 200);

        ArrayList<PrimeSet> mockSets = new ArrayList<>(Arrays.asList(set1, set2));

        when(mockPrimeSetDAO.getAllPrimeSets()).thenReturn(mockSets);

        primaryModel.boolChange("select-max-set");
        primaryModel.updateMaxI("150");

        ArrayList<PrimeSet> filteredSets = primaryModel.SetFiltring();

        Assertions.assertEquals(1, filteredSets.size());
        Assertions.assertEquals(100, filteredSets.get(0).getSetPrice());
    }

    @Test
    void testSetNoFiltersApplied() {
        PrimeSet set1 = new PrimeSet(1, "Set1", new ArrayList<>(), 100);
        PrimeSet set2 = new PrimeSet(2, "Set2", new ArrayList<>(), 200);

        ArrayList<PrimeSet> mockSets = new ArrayList<>(Arrays.asList(set1, set2));

        when(mockPrimeSetDAO.getAllPrimeSets()).thenReturn(mockSets);

        ArrayList<PrimeSet> filteredSets = primaryModel.SetFiltring();

        Assertions.assertEquals(2, filteredSets.size());
        Assertions.assertTrue(filteredSets.contains(set1));
        Assertions.assertTrue(filteredSets.contains(set2));
    }
    @Test
    void testAddNotNullPart_ValidId() throws Exception {
        int validPartId = 1;
        PrimePart mockPart = new PrimePart(validPartId, "TestPart", 50, 10, "Common");
        when(mockPrimePartDAO.getPrimePart(validPartId)).thenReturn(mockPart);

        ArrayList<PrimePart> parts = new ArrayList<>();

        java.lang.reflect.Method method = PrimaryModel.class.getDeclaredMethod("addNotNullPart", ArrayList.class, int.class);
        method.setAccessible(true);
        method.invoke(primaryModel, parts, validPartId);

        Assertions.assertEquals(1, parts.size());
        Assertions.assertEquals(mockPart, parts.get(0));
    }

    @Test
    void testAddNotNullPart_ErrorCodeId() throws Exception {
        int errorCode = PrimaryModel.ERROR_CODE;
        ArrayList<PrimePart> parts = new ArrayList<>();

        java.lang.reflect.Method method = PrimaryModel.class.getDeclaredMethod("addNotNullPart", ArrayList.class, int.class);
        method.setAccessible(true);

        method.invoke(primaryModel, parts, errorCode);

        Assertions.assertEquals(0, parts.size());
        verify(mockPrimePartDAO, never()).getPrimePart(anyInt());
    }

        @Test
    void testBoolChange() throws Exception {
        String[] cases = {"select-min", "select-max", "select-averageMin", "select-averageMax",
                "select-item", "select-mod", "select-prime", "select-riven",
                "select-rarity", "select-min-set", "select-max-set", "select-averageMin-set", "select-averageMax-set"};

        String[] fieldNames = {"minB", "maxB", "averageMinB", "averageMaxB",
                "itemB", "modB", "primeB", "rivenB",
                "rarityB", "minB", "maxB", "averageMinB", "averageMaxB"};

        for (int i = 0; i < cases.length; i++) {
            if (i < 9) {
                setField(primaryModel, fieldNames[i], false);
                Assertions.assertFalse(getBooleanField(primaryModel, fieldNames[i]), "Initial state should be false for " + cases[i]);
            } else {
                setField(primaryModel, fieldNames[i], false);
                Assertions.assertFalse(getBooleanField(primaryModel, fieldNames[i]), "Initial state should be false for " + cases[i]);
            }


            primaryModel.boolChange(cases[i]);
            if (i < 9) {
                Assertions.assertTrue(getBooleanField(primaryModel, fieldNames[i]), "State should be true after boolChange for " + cases[i]);
            } else {
                Assertions.assertTrue(getBooleanField(primaryModel, fieldNames[i]), "State should be true after boolChange for " + cases[i]);
            }


            primaryModel.boolChange(cases[i]); // Toggle back to false
            if (i < 9) {
                Assertions.assertFalse(getBooleanField(primaryModel, fieldNames[i]), "State should be false after second boolChange for " + cases[i]);
            } else {
                Assertions.assertFalse(getBooleanField(primaryModel, fieldNames[i]), "State should be false after second boolChange for " + cases[i]);
            }


        }
    }

    @Test
    void testGetItemType() {
        Item primePart = new PrimePart(1, "Part", 50, 10, "Common");
        Item riven = new Riven(2, "Riven", 100, "Weapon", 5);
        Item mod = new Mod(3, "Mod", 200, "Aura");
        Item item = new Item(4, "GenericItem", 50);

        Assertions.assertEquals("PrimePart", primaryModel.getItemType(primePart));
        Assertions.assertEquals("Riven", primaryModel.getItemType(riven));
        Assertions.assertEquals("Mod", primaryModel.getItemType(mod));
        Assertions.assertEquals("Item", primaryModel.getItemType(item));
    }
}

