package edu.grinnell.csc207.texteditor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

public class SimpleStringBufferTests {
    /** TODO: fill me in with unit and property tests! */
    // Assert: curPos is correct, sz is correct, 
    // string is same as expected
    @Test
    public void simpleCreationTest() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        b.insert('a');
        System.out.println(b.string);
        assertEquals(b.string, "a");
    }
    
    @Test
    public void leftmostDeletion() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        b.moveLeft();
        b.moveLeft();
        b.moveLeft();
        b.delete();
        assertEquals(b.string, "abc");
        assertEquals(b.getCursorPosition(), 0);
        assertEquals(b.getSize(), 3);
    }
    
    @Test
    public void singleDeletion() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        b.moveLeft();
        b.delete();
        assertEquals(b.string, "ac");
        assertEquals(b.getCursorPosition(), 1);
        assertEquals(b.getSize(), 2);
    }
    
    @Test
    public void fullDeletion() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        b.insert('x');
        b.insert('y');
        b.insert('z');
        assertEquals(b.string, "xyz");
        b.delete();
        b.delete();
        b.delete();
        assertEquals(b.string, "");
        assertEquals(b.getCursorPosition(), 0);
        assertEquals(b.getSize(), 0);
    }
    
    @Test
    public void deleteAtEndResetCur() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        b.insert('d');
        b.moveLeft();
        b.delete();
        b.moveLeft();
        b.moveLeft();
        assertEquals(b.string, "abd");
        assertEquals(b.getCursorPosition(), 0);
        assertEquals(b.getSize(), 3);
    }
    
    @Test
    public void deleteAtEndThenMiddle() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        b.insert('d');
        b.insert('e');
        assertEquals(b.getCursorPosition(), 5);
        b.delete();
        assertEquals(4, b.getCursorPosition());
        b.moveLeft();
        assertEquals(3, b.getCursorPosition());
        b.delete();
        assertEquals("abd", b.string);
        assertEquals(b.getCursorPosition(), 2);
        assertEquals(b.getSize(), 3);
    }
    
    @Test
    public void insertionTest() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        for (int i = 0; i < 10; i++) {
            b.insert('X');
        }
        b.moveLeft();
        b.moveLeft();
        b.moveLeft();
        b.insert('c');
        b.insert('b');
        b.insert('a');
        assertEquals(b.string, "XXXXXXXcbaXXX");
        assertEquals(b.getCursorPosition(), 10);
        assertEquals(b.getSize(), 13);
    }
    
    @Test
    public void centralDeletionTest() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        for (int i = 0; i < 10; i++) {
            b.insert('o');
        }
        assertEquals(b.string, "oooooooooo");
        b.moveLeft();
        assertEquals(b.getCursorPosition(), 9);
        assertEquals(b.getSize(), 10);
        for (int i = 9; i > 1; i--) {
            b.delete();
        }
        assertEquals(b.string, "oo");
        assertEquals(b.getCursorPosition(), 1);
        assertEquals(b.getSize(), 2);
    }
    
    @Test
    public void getCharTestAndIllegalRights() {
        SimpleStringBuffer b = new SimpleStringBuffer();
        b.insert('z');
        b.moveRight();
        b.insert('y');
        b.moveRight();
        b.insert('x');
        b.moveRight();
        b.insert('w');
        b.moveRight();
        b.insert('v');
        b.moveRight();
        assertEquals('z', b.getChar(0));
        assertEquals('y', b.getChar(1));
        assertEquals('x', b.getChar(2));
        assertEquals('w', b.getChar(3));
        assertEquals('v', b.getChar(4));
    }
    
    @Property
    public boolean insertAtMiddleSubstringTest (@ForAll @IntRange(min = 10, max = 10000) int n) {
        SimpleStringBuffer b = new SimpleStringBuffer();
        for (int i = 0; i < n; i++) {
           b.insert((char) (i % 257 + 97));
        }
        for (int i = n; i > n/2; i--) {
            b.moveLeft();
        }
        b.insert('L');
        return n + 1 == b.getSize();
    }
}
