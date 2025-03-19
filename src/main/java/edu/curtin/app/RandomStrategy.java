package edu.curtin.app;

import java.util.Random;

public class RandomStrategy implements CityBuildStrategy {
    private final Random rand = new Random();
    private static final String[] MATERIALS = {"wood", "stone", "brick", "concrete"};

    @Override
    public void buildCity(Grid grid) {
        int count = 0;
        double totalCost = 0;
        StringBuilder gridDisplay = new StringBuilder();
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                Structure s = new Structure(rand.nextInt(5) + 1, rand.nextBoolean() ? "slab" : "stilts",
                        MATERIALS[rand.nextInt(4)]);
                GridSquare square = grid.getSquare(row, col);
                if (square.checkBuildable(s) == null) {
                    count++;
                    totalCost += square.calculateCost(s);
                    gridDisplay.append("X ");
                } else {
                    gridDisplay.append(". ");
                }
            }
            gridDisplay.append("\n");
        }
        System.out.println(gridDisplay.toString());
        System.out.println("Structures built: " + count + ", Total cost: $" + totalCost);
    }
}