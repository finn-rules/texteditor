package edu.grinnell.csc207.texteditor;
import java.util.Arrays;
/**
 * A gap buffer-based implementation of a text buffer.
 */
public class GapBuffer {
    public int arrSize;
    public int gapStart;
    public int gapEnd;
    int numElements;
    public char[] arr;
    /**
     * Constructor for the gap buffer.
     */
    public GapBuffer() {
       this.arrSize = 1;
       this.numElements = 0;
       this.gapStart = 0;
       this.gapEnd = 1;
       this.arr = new char[arrSize];
    }
    
    /**
     * Insert : to insert a character
     * @param ch : the character to insert.
     */
    public void insert(char ch) {
        if (gapStart >= gapEnd) {
            System.out.println("Expanding!");
            arrSize *= 2;
            char[] temp = Arrays.copyOf(arr, arrSize);
            for (int i = gapEnd + arrSize / 2; i < arrSize; i++) {
                temp[i] = arr[i - arrSize / 2];
            }
            this.arr = temp;
            gapEnd = gapStart + (arrSize / 2);
            System.out.println("End index: " + gapEnd);
        }
        System.out.println("Attempting to place. Start index: " + gapStart);
        arr[gapStart] = ch;
        gapStart++;
        numElements++;
    }

    /**
     * delete: deletes the character at the start of the gap.
     */
    public void delete() {
        if(gapStart != 0 && arrSize != 0) {
            arrSize--;
            gapStart--;
            numElements--;
        }
    }

    /**
     * getCursorPosition
     * @return the position of the start of the gap
     */
    public int getCursorPosition() {
        return this.gapStart;
    }

    /**
     * moveLeft: moves the entire gap left
     */
    public void moveLeft() {
        if (gapStart > 0) {
            this.gapStart--;
            this.gapEnd--; 
            this.arr[gapEnd] = this.arr[gapStart];         
        }
    }
    
    /**
     * moveRight: moves the entire gap right
     */
    public void moveRight() {
        if (gapEnd < arr.length - 2) {
            this.gapStart++;
            this.gapEnd++; 
            this.arr[gapStart] = this.arr[gapEnd];         
        }
    }

    /**
     * getSize: returns the number of "occupied" elements in the buffer.
     * @return int
     */
    public int getSize() {
        return this.numElements;
    }
    
    /**
     * getChar gets the character in the buffer at index i
     * @param i for index
     * @return char: the character at index i
     * @throws IndexOutOfBoundsException 
     */
    public char getChar(int i) throws IndexOutOfBoundsException { 
    // note: may not get the correct character due to not considering buffer
        if(i > arrSize - 1) {
            throw new IndexOutOfBoundsException();
        }
        return this.arr[i];
    }
    
    /**
     * toString: converts the buffer (no gap) to a string.
     * @return the String. 
     */
    @Override
    public String toString() {
        String s = "";
        int totalStringSize = this.numElements;
        for (int i = 0; i < gapStart; i++) {
            s = s + this.arr[i];
            totalStringSize--;
        }
        if (totalStringSize > 0) {
            for (int i = 0; i < totalStringSize; i++) {
                s = s + this.arr[gapEnd + i];
            }
        }
        return s;
    }
}
