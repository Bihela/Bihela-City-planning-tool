package edu.curtin.app;

import java.util.Scanner;

public class UniformStrategy implements CityBuildStrategy {
    private final Structure structure;

    public UniformStrategy(Scanner scanner) {
        System.out.print("Enter floors: ");
        int floors = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter foundation (slab/stilts): ");
        String foundation = scanner.nextLine();
        System.out.print("Enter material (wood/stone/brick/concrete): ");
        String material = scanner.nextLine();
        this.structure = new Structure(floors, foundation, material);
    }

    @Override
    public void buildCity(Grid grid) {
        int count = 0;
        double totalCost = 0;
        StringBuilder gridDisplay = new StringBuilder();
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                GridSquare square = grid.getSquare(row, col);
                if (square.checkBuildable(structure) == null) {
                    count++;
                    totalCost += square.calculateCost(structure);
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