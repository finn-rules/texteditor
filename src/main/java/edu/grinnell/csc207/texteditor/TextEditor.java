package edu.grinnell.csc207.texteditor;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * The driver for the TextEditor Application.
 */
public class TextEditor {

    /**
     * drawBuffer : creates and updates the buffer, creating a 100*100 panel.
     *
     * @param buf = a string buffer
     * @param screen = the screen object
     * @throws IOException
     */
    public static void drawBuffer(GapBuffer buf, Screen screen) throws IOException {
        screen.clear(); // Added this to address a visual glitch with deleting.
        
        int row = 0;
        int col = 0;
        int panelSize = 75;
        for (int i = 0; i < buf.getSize(); i++) {
            char ch = buf.getChar(i);
            row = i % panelSize;
            col = i / panelSize;
            TextCharacter text = new TextCharacter(ch);
            screen.setCharacter(row, col, text);
            screen.setCursorPosition(new TerminalPosition(buf.gapStart % panelSize,
                    col));
        }
        screen.refresh();
    }

    /**
     * The main entry point for the TextEditor application.
     *
     * @param args command-line arguments.
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TextEditor <filename>");
            System.exit(1);
        }

        String pathString = args[0];
        System.out.format("Loading %s...\n", pathString);
        Path path = Paths.get(pathString); // !!

        GapBuffer b = new GapBuffer();
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        if (Files.exists(path) && Files.isRegularFile(path)) {
            String insideFile = Files.readString(path);
            char[] strChars = insideFile.toCharArray();
            for (int i = 0; i < strChars.length; i++) {
                b.insert(strChars[i]);
            }
        }

        boolean isRunning = true;
        while (isRunning) {
            drawBuffer(b, screen);
            KeyStroke keyStroke = screen.readInput();
            KeyType keyType = keyStroke.getKeyType();
            if (null != keyType) {
                switch (keyType) {
                    case Character -> {
                        char ch = keyStroke.getCharacter();
                        b.insert(ch);
                    }
                    case ArrowLeft ->
                        b.moveLeft();
                    case ArrowRight ->
                        b.moveRight();
                    case Backspace ->
                        b.delete();
                    case Escape ->
                        isRunning = false;
                    default -> {
                    }
                }
            }
        }
        screen.stopScreen();
        Files.writeString(path, b.toString());
        System.out.format("File %s saved. \nExiting...\n", pathString);
    }
}
