package edu.curtin.app;

public class CentralStrategy implements CityBuildStrategy {
    @Override
    public void buildCity(Grid grid) {
        int count = 0;
        double totalCost = 0;
        StringBuilder gridDisplay = new StringBuilder();
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                double distance = Math.sqrt(
                    Math.pow(row - (grid.getHeight() - 1) / 2.0, 2) +
                    Math.pow(col - (grid.getWidth() - 1) / 2.0, 2)
                );
                int floors = (int) Math.round(1 + 20 / (distance + 1));
                String material = distance <= 2 ? "concrete" :
                                 distance <= 4 ? "brick" :
                                 distance <= 6 ? "stone" : "wood";
                Structure s = new Structure(floors, "slab", material);
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