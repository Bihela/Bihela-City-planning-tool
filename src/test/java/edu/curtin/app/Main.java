package edu.curtin.app;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (args.length != 1) {
            LOGGER.severe("Usage: java -jar cityplanner.jar <grid-file>");
            System.exit(1);
        }

        GridLoader loader = new GridLoader();
        try {
            Grid grid = loader.loadGrid(args[0]);
            System.out.println("Loaded grid: " + grid.getHeight() + "x" + grid.getWidth());
        } catch (Exception e) {
            LOGGER.severe("Failed to load grid: " + e.getMessage());
            System.exit(1);
        }
    }
}