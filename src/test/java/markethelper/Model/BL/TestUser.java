package markethelper.Model.BL;

import org.junit.jupiter.api.*;
import org.example.markethelper.Model.BL.User;

public class TestUser {
    private static User user;

    @BeforeAll
    public static void setUp() {
        TestUser.user = new User("testUser", "");
    }

    @Nested
    @DisplayName("User Test")
    class testUser {

        @Test
        @DisplayName("User Test of computeHash")
        public void testUComputeHash() {
            String password = "securePassword123";
            user.computeHash(password);
            Assertions.assertNotNull(user.getHash());
            Assertions.assertNotEquals(password, user.getHash());
        }

        @Test
        @DisplayName("User Test of hashValid - Correct Password")
        public void testUHashValidCorrect() {
            String password = "securePassword123";
            user.computeHash(password);
            Assertions.assertTrue(user.hashValid(password));
        }

        @Test
        @DisplayName("User Test of hashValid - Incorrect Password")
        public void testUHashValidIncorrect() {
            String password = "securePassword123";
            user.computeHash(password);
            Assertions.assertFalse(user.hashValid("wrong"));
        }

        @Test
        @DisplayName("User Test of computeHash generating different hashes")
        public void testUComputeHashDifferentHashes() {
            user.computeHash("password1");
            String firstHash = user.getHash();
            user.computeHash("password2");
            String secondHash = user.getHash();
            Assertions.assertNotEquals(firstHash, secondHash);
        }

        @Test
        @DisplayName("User Test of computeHash with empty password")
        public void testUComputeHashEmptyPassword() {
            user.computeHash("");
            Assertions.assertNotNull(user.getHash());
            Assertions.assertFalse(user.getHash().isEmpty());
        }
    }
}

