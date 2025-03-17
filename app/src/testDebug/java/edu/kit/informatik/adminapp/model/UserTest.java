package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static java.lang.Math.min;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class tests the {@link User} class.
 */
public class UserTest {
    private Name[] names = {
            new Name("Max Mustermann"),
            new Name(""),
            new Name("§"),
            new Name("%&askjn"),
            new Name("7126t")
    };
    private UserId[] userIds = {
            new UserId("1"),
            new UserId("bashjbcsa ajhsbcs"),
            new UserId("&(!&(//=`"),
            new UserId("as"),
            new UserId("")
    };
    private Token[][] tokensOfUsers = {
            {},
            {new Token(new TokenId("aksbasd"))},
            {new Token(new TokenId("aksbasd")), new Token(new TokenId("&&!&/&/!)/&((=``"))},
            {new Token(new TokenId("aksbasd")), new Token(new TokenId("&&!&/&/!)/&((=``")),
                    new Token(new TokenId("21638712"))},
            {new Token(new TokenId("aksbasd")), new Token(new TokenId("&&!&/&/!)/&((=``")),
                    new Token(new TokenId("21638712")), new Token(new TokenId("ASsahbda71263uas"))}
    };
    private User[] users;

    /**
     * Initializes the test users.
     */
    @Before
    public void setUp() {
        this.users = new User[min(userIds.length, names.length)];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User(this.names[i], this.userIds[i], tokensOfUsers[i]);
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (int i = 0; i < users.length; i++) {
            assertEquals(names[i].toString(), users[i].toString());
        }
    }

    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName() {
        for (int i = 0; i < users.length; i++) {
            assertEquals(names[i], users[i].getName());
        }
    }

    /**
     * Tests the getId method.
     */
    @Test
    public void testGetId() {
        for (int i = 0; i < users.length; i++) {
            assertEquals(userIds[i], users[i].getId());
        }
    }

    /**
     * Tests the getTokens method.
     */
    @Test
    public void testGetTokens() {
        for (int i = 0; i < users.length; i++) {
            equals(Arrays.asList(tokensOfUsers[i]), users[i].getTokens());
        }
    }

    /**
     * Tests the add method.
     */
    @Test
    public void testAdd() {
        User user = new User(names[0], userIds[0]);
        Collection<Token> tokensOfUser = new LinkedList<>();

        for (int i = 0; i < tokensOfUsers.length; i++) {
            for (int j = 0; j < tokensOfUsers[i].length; j++) {
                user.add(tokensOfUsers[i][j]);
                tokensOfUser.add(tokensOfUsers[i][j]);
                equals(tokensOfUser, user.getTokens());
            }
        }
    }

    /**
     * Tests the remove method.
     */
    @Test
    public void testRemove() {
        for (int i = 0; i < users.length; i++) {
            Collection<Token> tokensOfUser = new LinkedList<>(Arrays.asList(tokensOfUsers[i]));
            for (int j = 0; j < tokensOfUsers[i].length; j++) {
                users[i].remove(tokensOfUsers[i][j]);
                tokensOfUser.remove(tokensOfUsers[i][j]);
                equals(tokensOfUser, users[i].getTokens());
            }
        }
    }

    /**
     * Tests the removeAllTokens method.
     */
    @Test
    public void testRemoveAllTokens() {
        for (User user : users) {
            user.removeAllTokens();
            assertTrue(user.getTokens().isEmpty());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        for (User user : users) {
            assertTrue(user.equals(user));
        }
    }

    /**
     * Tests the equals method for two identical objects.
     */
    @Test
    public void testEquals_same() {
        User[] users1 = new User[min(userIds.length, names.length)];
        User[] users2 = new User[min(userIds.length, names.length)];
        for (int i = 0; i < users1.length; i++) {
            users1[i] = new User(this.names[i], this.userIds[i], tokensOfUsers[i]);
            users2[i] = new User(this.names[i], this.userIds[i], tokensOfUsers[i]);
        }

        for (int i = 0; i < users1.length; i++) {
            assertTrue(users1[i].equals(users2[i]));
            assertTrue(users2[i].equals(users1[i]));
        }
    }

    /**
     * Tests the equals method for two different objects.
     */
    @Test
    public void testEquals_different() {
        for (int i = 0; i < users.length; i++) {
            for (int j = 0; j < users.length; j++) {
                if (i != j) {
                    assertFalse(users[i].equals(users[j]));
                    assertFalse(users[j].equals(users[i]));
                }
            }
        }
    }

    /**
     * Tests the equals method for null.
     */
    @Test
    public void testEquals_null() {
        for (User user : users) {
            assertFalse(user.equals(null));
        }
    }

    /**
     * Tests whether two identical objects return the same hash code.
     */
    @Test
    public void testHashCode_same() {
        User[] users1 = new User[min(userIds.length, names.length)];
        User[] users2 = new User[min(userIds.length, names.length)];
        for (int i = 0; i < users1.length; i++) {
            users1[i] = new User(this.names[i], this.userIds[i], tokensOfUsers[i]);
            users2[i] = new User(this.names[i], this.userIds[i], tokensOfUsers[i]);
        }

        for (int i = 0; i < users1.length; i++) {
            assertEquals(users1[i].hashCode(), users2[i].hashCode());
        }
    }

    /**
     * Tests whether two different objects return different hash codes.
     */
    @Test
    public void testHashCode_different() {
        for (int i = 0; i < users.length; i++) {
            for (int j = 0; j < users.length; j++) {
                if (i != j) {
                    assertNotEquals(users[i].hashCode(), users[j].hashCode());
                }
            }
        }
    }

    private <T> boolean equals(Collection<T> col1, Collection<T> col2) {
        if (col1 == null && col2 != null) return true;
        if (col1 == null || col2 == null) return false;

        for (T t : col1) {
            if (!col2.contains(t)) return false;
        }
        for (T t : col2) {
            if (!col1.contains(t)) return false;
        }

        return true;
    }
}
