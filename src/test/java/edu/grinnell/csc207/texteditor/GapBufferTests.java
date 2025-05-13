package edu.grinnell.csc207.texteditor;

import org.junit.jupiter.api.Test;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;

public class GapBufferTests {

    @Test
    public void simpleCreationTest() {
        GapBuffer b = new GapBuffer();
        b.insert('a');
        assertEquals('a', b.getChar(0));
        assertEquals("a", b.toString());
    }

    @Test
    public void leftmostDeletion() {
        GapBuffer b = new GapBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        assertEquals("abc", b.toString());
        b.moveLeft();
        b.moveLeft();
        assertEquals('b', b.getChar(1));
        b.moveLeft();
        b.delete();
        assertEquals(0, b.getCursorPosition());
        assertEquals(3, b.getSize());
        assertEquals("abc", b.toString());
    }

    @Test
    public void singleDeletion() {
        GapBuffer b = new GapBuffer();
        b.insert('a');
        b.insert('b');
        b.insert('c');
        assertEquals(b.toString(), "abc");
        assertEquals(b.getSize(), 3);
        b.moveLeft();
        b.delete();
        assertEquals(b.getSize(), 2);
        assertEquals(b.toString(), "ac");
    }

    @Test
    public void fullDeletion() {
        GapBuffer b = new GapBuffer();
        String s = "";
        for (int i = 97; i < 97 + 26; i++) {
            b.insert((char) i);
            s = s + (char) i;
        }
        assertEquals(b.toString(), s);
        for (int i = 0; i < 10; i++) {
            b.moveLeft();
        }
        assertEquals(b.toString(), s);
        for (int i = 97; i < 97 + 26; i++) {
            b.insert((char) i);
        }
        for (int i = 0; i < 100; i++) {
            b.moveRight();
        }
        for (int i = 0; i < 1000; i++) {
            b.delete();
        }
        assertEquals(b.toString(), "");
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
        assertEquals("a", b.toString());
        b.insert('b');
        assertEquals("ab", b.toString());
        b.insert('c');
        assertEquals("abc", b.toString());
        b.insert('d');
        assertEquals("abcd", b.toString());
        b.insert('e');
        assertEquals("abcde", b.toString());
        assertEquals(b.getCursorPosition(), 5);
        b.delete();
        assertEquals("abcd", b.toString());
        assertEquals(4, b.getCursorPosition());
        b.moveLeft();
        assertEquals("abcd", b.toString());
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
        assertEquals("XXXXXXXXXX", b.toString());
        b.moveLeft();
        b.moveLeft();
        b.moveLeft();
        assertEquals("XXXXXXXXXX", b.toString());
        b.insert('c');
        assertEquals("XXXXXXXcXXX", b.toString());
        b.insert('b');
        assertEquals("XXXXXXXcbXXX", b.toString());
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
        assertEquals(b.toString(), "xxxxxxxxxx");
        for (int i = 0; i < 10; i++) {
            b.moveLeft();
            b.moveRight();
        }
        assertEquals(b.toString(), "xxxxxxxxxx");
        for (int i = 0; i < 10; i++) {
            b.moveRight();
        }
        assertEquals(b.toString(), "xxxxxxxxxx");
        for (int i = 0; i < 20; i++) {
            b.moveLeft();
        }
        assertEquals(b.toString(), "xxxxxxxxxx");
        b.insert('f');
        assertEquals(b.toString(), "fxxxxxxxxxx");
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
    public boolean correctElementNumTest(@ForAll @IntRange(min = 0, max = 10000) int n) {
        GapBuffer b = new GapBuffer();
        for (int i = 0; i < n; i++) {
            b.insert((char) (i % 100));
        }
        int actual = b.getSize();
        return actual == n;
    }

    private GapBuffer makeBufferWith(String s) {
        GapBuffer buf = new GapBuffer();
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        return buf;
    }

    @Test
    @DisplayName("Gap: hello end-to-end")
    public void helloExampleTest() {
        GapBuffer buffer = new GapBuffer();
        buffer.insert('h');
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');
        buffer.insert(' ');
        buffer.insert('w');
        buffer.insert('o');
        buffer.insert('r');
        buffer.insert('l');
        buffer.insert('d');
    
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.insert('!');
    
        buffer.moveLeft();
        buffer.delete();
        buffer.delete();
        buffer.delete();
        buffer.delete();
        buffer.delete();
    
        buffer.insert('a');
        buffer.insert('b');
        buffer.insert('c');
        assertEquals("abc! world", buffer.toString());
    }

    @Test
    @DisplayName("Gap: empty")
    public void emptyBufTest() {
        GapBuffer buf = makeBufferWith("");
        assertEquals(0, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
    }

    @Test
    @DisplayName("Gap: cursor movement")
    public void cursorMovementTest() {
        GapBuffer buf = makeBufferWith("abc");
        assertEquals(3, buf.getCursorPosition(), "initial cursor");
        buf.moveLeft();
        assertEquals(2, buf.getCursorPosition(), "after L");
        buf.moveLeft();
        assertEquals(1, buf.getCursorPosition(), "after LL");
        buf.moveLeft();
        assertEquals(0, buf.getCursorPosition(), "after LLL");
        buf.moveLeft();
        assertEquals(0, buf.getCursorPosition(), "after LLLL");
        buf.moveRight();
        assertEquals(1, buf.getCursorPosition(), "after LLLLR");
        buf.moveRight();
        assertEquals(2, buf.getCursorPosition(), "after LLLLRR");
        buf.moveRight();
        assertEquals(3, buf.getCursorPosition(), "after LLLLRRR");
        buf.moveRight();
        assertEquals(3, buf.getCursorPosition(), "after LLLLRRRR");
    }

    @Test
    @DisplayName("Gap: insert middle")
    public void cursorInsertMiddleTest() {
        GapBuffer buf = makeBufferWith("abc");
        buf.moveLeft();
        buf.moveLeft();
        buf.insert('!');
        buf.insert('!');
        assertEquals(5, buf.getSize(), "size");
        assertEquals(3, buf.getCursorPosition(), "cursor");
        assertEquals("a!!bc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete middle")
    public void cursorDeleteMiddleTest() {
        GapBuffer buf = makeBufferWith("abc");
        buf.moveLeft();
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("c", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: insert front")
    public void cursorInsertFrontTest() {
        GapBuffer buf = makeBufferWith("abc");
        for (int i = 0; i < 3; i++) {
            buf.moveLeft();
        }
        buf.insert('!');
        buf.insert('!');
        assertEquals(5, buf.getSize(), "size");
        assertEquals(2, buf.getCursorPosition(), "cursor");
        assertEquals("!!abc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete front")
    public void cursorDeleteFrontTest() {
        GapBuffer buf = makeBufferWith("abc");
        for (int i = 0; i < 3; i++) {
            buf.moveLeft();
        }
        buf.delete();
        assertEquals(3, buf.getSize(), "size");
        assertEquals(0, buf.getCursorPosition(), "cursor");
        assertEquals("abc", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: delete end")
    public void cursorDeleteEndTest() {
        GapBuffer buf = makeBufferWith("abc");
        buf.delete();
        buf.delete();
        assertEquals(1, buf.getSize(), "size");
        assertEquals(1, buf.getCursorPosition(), "cursor");
        assertEquals("a", buf.toString(), "contents");
    }

    @Test
    @DisplayName("Gap: big buffer")
    public void bigBufferTest() {
        GapBuffer buf = new GapBuffer();
        for (int i = 0; i < 16384; i++) {
            buf.insert((char) (i % 10 + '0'));
        }
        assertEquals(16384, buf.getSize(), "size");
        assertEquals(16384, buf.getCursorPosition(), "cursor");

        for (int i = 0; i < 300; i++) {
            buf.moveLeft();
        }
        buf.insert('!');
        buf.insert('!');
        buf.delete();
        assertEquals(16385, buf.getSize(), "size");
        assertEquals(16085, buf.getCursorPosition(), "cursor");
    }
}
