package edu.grinnell.csc207.texteditor;

/**
 * A naive implementation of a text buffer using a <code>String</code>.
 */
public class SimpleStringBuffer {
    public int curPos;
    public String string;

    /**
     * Constructor for a SimpleStringBuffer
     */
    public SimpleStringBuffer() {
        this.curPos = 0;
        this.string = "";
    }

    /**
     * insert : to insert a character
     * 
     * @param ch : the character to insert.
     */
    public void insert(char ch) {
        if (curPos == string.length()) {
            string = string + ch;
        } else {
            string = string.substring(0, curPos) + ch + string.substring(curPos, string.length());
        }
        this.moveRight();
    }

    /**
     * delete: deletes a character, depending on cursor pos.
     */
    public void delete() {
        if (curPos == string.length() && string.length() != 0) {
            string = string.substring(0, curPos - 1);
            this.moveLeft();
        } else if (curPos != 0) {
            string = string.substring(0, curPos - 1) + string.substring(curPos, string.length());
            this.moveLeft();
        }
    }

    /**
     * getCursorPosition
     * 
     * @return int : the cursor's position
     */
    public int getCursorPosition() {
        return this.curPos;
    }

    /**
     * moveLeft() makes the cursor move left.
     */
    public void moveLeft() {
        if (curPos != 0 && string.length() != 0) {
            this.curPos--;
        }
    }

    /**
     * moveRight() makes the cursor move right.
     */
    public void moveRight() {
        if (curPos != string.length()) {
            this.curPos++;
        }
    }

    /**
     * getSize
     * 
     * @return int: the length of the buffer as a string.
     */
    public int getSize() {
        return this.string.length();
    }

    /**
     * getChar: gets the character at int i
     * 
     * @param i : index
     * @return char: the char at index i
     */
    public char getChar(int i) {
        if (i >= string.length()) {
            throw new IndexOutOfBoundsException("Error: i is an invalid index of the buffer.");
        } else {
            char arr[] = string.toCharArray();
            return arr[i];
        }
    }

    /**
     * toString: converts the buffer to a string.
     * 
     * @return String: the string.
     */
    @Override
    public String toString() {
        return this.string;
    }
}
