package main.java.edu.curtin.app;

public class Grid {
    private final int height;
    private final int width;
    private final GridSquare[][] squares;

    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        this.squares = new GridSquare[height][width];
    }

    public void setSquare(int row, int col, GridSquare square) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            throw new IllegalArgumentException("Invalid grid coordinates: (" + row + ", " + col + ")");
        }
        squares[row][col] = square;
    }

    public GridSquare getSquare(int row, int col) {
        return squares[row][col];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}