package edu.grinnell.csc207.texteditor;
import org.junit.jupiter.api.Test;


import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class GapBufferTests {
     @Test
    public void simpleCreationTest() {
        GapBuffer b = new GapBuffer();
        b.insert('a');
        assertEquals('a',b.getChar(0));
        assertEquals("a", b.toString());
    }
    
    @Test
    public void leftmostDeletion() {
        GapBuffer b = new GapBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        b.moveLeft();
        b.moveLeft();
        b.moveLeft();
        b.delete();
        assertEquals(0, b.getCursorPosition());
        assertEquals(4, b.getSize());
        assertEquals("abc", b.toString());
    }
    
    @Test
    public void singleDeletion() {
        GapBuffer b = new GapBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        b.moveLeft();
        b.delete();
        assertEquals(b.toString(), "ac");
    }
    
    @Test
    public void fullDeletion() {
        GapBuffer b = new GapBuffer();
        for(int i = 97; i < 97 + 26; i++) {
            b.insert((char) i);
        }
        for(int i = 0; i < 10; i++) {
            b.moveLeft();
        }
        for(int i = 97; i < 97 + 26; i++) {
            b.insert((char) i);
        }
        System.out.println(b.toString());
    }
    
    @Test
    public void deleteMiddlePlaceZeroTest() {
        GapBuffer b = new GapBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        b.insert('d');
        b.moveLeft();
        b.delete();
        b.moveLeft();
        b.moveLeft();
        b.insert('x');
        assertEquals(b.toString(), "xabd");
        assertEquals(b.getCursorPosition(), 1);
        assertEquals(4, b.getSize());
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
        GapBuffer b = new GapBuffer();
        for (int i = 0; i < 10; i++) {
            b.insert('X');
        }
        b.moveLeft();
        b.moveLeft();
        b.moveLeft();
        b.insert('c');
        b.insert('b');
        b.insert('a');
        assertEquals("XXXXXXXcbaXXX", b.toString());
        assertEquals(b.getCursorPosition(), 10);
        assertEquals(13, b.getSize());
    }
    
    @Test
    public void movingGapBuffer() {
        GapBuffer b = new GapBuffer();
        for (int i = 0; i < 10; i++) {
            b.insert('x');
        }
        for(int i = 0; i < 10; i++) {
            b.moveLeft();
            b.moveRight();
        }
        for(int i = 0; i < 10; i++) {
            b.moveRight();
        }
        for(int i = 0; i < 20; i++) {
            b.moveLeft();
        }
        b.insert('f');
        System.out.println(b.gapEnd);
        System.out.println(b.toString());
    }
    
    @Test
    public void centralDeletionTest() {
        GapBuffer b = new GapBuffer();
        for (int i = 0; i < 10; i++) {
            b.insert('o');
        }
        assertEquals(b.toString(), "oooooooooo");
        b.moveLeft();
        assertEquals(b.getCursorPosition(), 9);
        assertEquals(b.getSize(), 10);
        for (int i = 9; i > 1; i--) {
            b.delete();
        }
        assertEquals(b.toString(), "oo");
        assertEquals(b.getCursorPosition(), 1);
        assertEquals(b.getSize(), 2);
    }
    
    @Property
    public boolean correctElementNumTest (@ForAll @IntRange(min = 0, max = 10000) int n) {
        GapBuffer b = new GapBuffer();
        for (int i = 0; i < n; i++) {
            b.insert((char) (i % 100));
        }
        int actual = b.getSize();
        return actual == n;
    }
}

