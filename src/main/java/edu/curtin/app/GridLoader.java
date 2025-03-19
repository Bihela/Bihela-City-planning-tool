package edu.curtin.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class GridLoader {
    private static final Logger LOGGER = Logger.getLogger(GridLoader.class.getName());

    public Grid loadGrid(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] dimensions = reader.readLine().split(",");
            if (dimensions.length != 2) {
                throw new IllegalArgumentException("Invalid grid dimensions line");
            }
            int height = Integer.parseInt(dimensions[0].trim());
            int width = Integer.parseInt(dimensions[1].trim());
            if (height <= 0 || width <= 0) {
                throw new IllegalArgumentException("Grid dimensions must be positive");
            }

            Grid grid = new Grid(height, width);
            int row = 0, col = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 0) {
                    throw new IllegalArgumentException("Empty grid square line at position " + (row * width + col));
                }

                GridSquare square = new GridSquare(Terrain.fromString(fields[0].trim()));
                for (int i = 1; i < fields.length; i++) {
                    square.addZoningRule(parseZoningRule(fields[i].trim()));
                }

                grid.setSquare(row, col, square);
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }

            if (row != height || col != 0) {
                throw new IllegalArgumentException("Grid file does not match specified dimensions");
            }

            LOGGER.info(() -> "Successfully loaded grid from " + filename);
            return grid;
        } catch (NumberFormatException e) {
            LOGGER.severe(() -> "Numeric parsing error in file " + filename + ": " + e.getMessage());
            throw new IllegalArgumentException("Invalid numeric value in grid file", e);
        } catch (IOException e) {
            LOGGER.severe(() -> "Error reading file " + filename + ": " + e.getMessage());
            throw e;
        }
    }

    private ZoningRule parseZoningRule(String ruleStr) {
        if (ruleStr.equals("contamination")) {
            return new ContaminationRule();
        }

        String[] parts = ruleStr.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid zoning rule format: " + ruleStr);
        }

        String type = parts[0];
        String value = parts[1];

        switch (type) {
            case "heritage":
                return new HeritageRule(value);
            case "height-limit":
                return new HeightLimitRule(Integer.parseInt(value));
            case "flood-risk":
                return new FloodRiskRule(Double.parseDouble(value));
            default:
                throw new IllegalArgumentException("Unknown zoning rule type: " + type);
        }
    }
}