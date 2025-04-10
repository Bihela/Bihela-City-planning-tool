package edu.curtin.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class GridLoader {
    private static final Logger LOGGER = Logger.getLogger(GridLoader.class.getName());

    public Grid loadGrid(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null) {
                throw new IllegalArgumentException("File is empty");
            }

            String[] dimensions = line.split(",");
            if (dimensions.length != 2) {
                throw new IllegalArgumentException("Invalid dimensions format, expected 'height,width': " + line);
            }
            int height, width;
            try {
                height = Integer.parseInt(dimensions[0].trim());
                width = Integer.parseInt(dimensions[1].trim());
                if (height <= 0 || width <= 0) {
                    throw new IllegalArgumentException("Grid dimensions must be positive, found: " + line);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Dimensions must be numbers, found: " + line);
            }

            Grid grid = new Grid(height, width);
            int row = 0, col = 0;
            int lineNum = 1;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                String[] fields = line.split(",");
                if (fields.length == 0) {
                    throw new IllegalArgumentException("Empty grid square line at position " + (row * width + col));
                }

                String terrain = fields[0].trim();
                if (!terrain.equals("flat") && !terrain.equals("swampy") && !terrain.equals("rocky")) {
                    throw new IllegalArgumentException("Invalid terrain at line " + lineNum + ": " + terrain);
                }
                GridSquare square = new GridSquare(Terrain.fromString(terrain));

                for (int i = 1; i < fields.length; i++) {
                    square.addZoningRule(parseZoningRule(fields[i].trim(), lineNum));
                }

                grid.setSquare(row, col, square);
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }

            if (row != height || col != 0) {
                throw new IllegalArgumentException("Grid file does not match specified dimensions, expected " + (height * width) + " squares, found " + (row * width + col));
            }

            LOGGER.info(() -> "Successfully loaded grid from " + filename + ": " + height + "x" + width);
            return grid;
        } catch (IllegalArgumentException e) {
            LOGGER.severe(() -> "Grid loading failed for " + filename + ": " + e.getMessage());
            throw e;
        } catch (IOException e) {
            LOGGER.severe(() -> "Error reading file " + filename + ": " + e.getMessage());
            throw e;
        }
    }

    private ZoningRule parseZoningRule(String ruleStr, int lineNum) {
        if (ruleStr.equals("contamination")) {
            return new ContaminationRule();
        }
    
        String[] parts = ruleStr.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid zoning rule format at line " + lineNum + ": " + ruleStr);
        }
    
        String type = parts[0].trim();
        String value = parts[1].trim();
    
        switch (type) {
            case "contamination":
                throw new IllegalArgumentException("Contamination takes no value at line " + lineNum + ": " + ruleStr);
            case "heritage":
                if (!value.equals("wood") && !value.equals("stone") && !value.equals("brick")) {
                    throw new IllegalArgumentException("Heritage must be 'wood', 'stone', or 'brick' at line " + lineNum + ": " + value);
                }
                return new HeritageRule(value);
            case "height-limit":
                try {
                    int limit = Integer.parseInt(value);
                    if (limit <= 0) {
                        throw new IllegalArgumentException("Height limit must be positive at line " + lineNum + ": " + limit);
                    }
                    return new HeightLimitRule(limit);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Height limit must be an integer at line " + lineNum + ": " + value);
                }
            case "flood-risk":
                try {
                    double risk = Double.parseDouble(value);
                    if (risk < 0 || risk > 100) {
                        throw new IllegalArgumentException("Flood risk must be between 0 and 100 at line " + lineNum + ": " + risk);
                    }
                    return new FloodRiskRule(risk);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Flood risk must be a number at line " + lineNum + ": " + value);
                }
            default:
                throw new IllegalArgumentException("Unknown zoning rule at line " + lineNum + ": " + type);
        }
    }
}