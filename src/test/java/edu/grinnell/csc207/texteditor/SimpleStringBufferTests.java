package edu.grinnell.csc207.texteditor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

public class SimpleStringBufferTests {
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
        assertEquals("abc", b.string);
        b.insert('d');
        b.insert('e');
        assertEquals("abcde", b.string);
        assertEquals(b.getCursorPosition(), 5);
        b.delete();
        assertEquals(4, b.getCursorPosition());
        assertEquals("abcd", b.string);
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
        assertEquals(b.string, "XXXXXXXXXX");
        b.moveLeft();
        b.moveLeft();
        b.moveLeft();
        assertEquals(b.string, "XXXXXXXXXX");
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
        assertEquals(b.toString(), "z");
        b.moveRight();
        assertEquals(b.toString(), "z");
        b.insert('y');
        assertEquals(b.toString(), "zy");
        b.moveRight();
        assertEquals(b.toString(), "zy");
        assertEquals(b.getSize(), 2);
        b.insert('x');
        b.moveRight();
        assertEquals(b.toString(), "zyx");
        assertEquals(b.getSize(), 3);
        b.insert('w');
        b.moveRight();
        assertEquals(b.toString(), "zyxw");
        assertEquals(b.getSize(), 4);
        b.insert('v');
        b.moveRight();
        assertEquals('z', b.getChar(0));
        assertEquals('y', b.getChar(1));
        assertEquals('x', b.getChar(2));
        assertEquals('w', b.getChar(3));
        assertEquals('v', b.getChar(4));
        assertEquals(b.toString(), "zyxwv");
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
