package edu.curtin.app;

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

    public String displayTerrain() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                sb.append(squares[row][col].getTerrain().getValue().charAt(0)).append(" "); // Fixed 'grid' to 'squares'
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}