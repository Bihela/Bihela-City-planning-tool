package edu.curtin.app;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static String buildApproach = "random";

    public static void main(String[] args) {
        if (args.length != 1) {
            LOGGER.severe("Usage: java -jar cityplanner.jar <grid-file>");
            System.exit(1);
        }

        GridLoader loader = new GridLoader();
        try {
            Grid grid = loader.loadGrid(args[0]);
            System.out.println("Loaded grid: " + grid.getHeight() + "x" + grid.getWidth());

            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.println("\nMenu:");
                    System.out.println("1. Show Grid");
                    System.out.println("2. Build Structure");
                    System.out.println("3. Build City");
                    System.out.println("4. Configure");
                    System.out.println("5. Quit");
                    System.out.print("Enter choice: ");
                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "1":
                            System.out.println("Grid: " + grid.getHeight() + "x" + grid.getWidth());
                            break;
                        case "2":
                            buildStructure(grid, scanner);
                            break;
                        case "3":
                            buildCity(grid, scanner);
                            break;
                        case "4":
                            configure(scanner);
                            break;
                        case "5":
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.severe(() -> "Failed to load grid: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            LOGGER.severe(() -> "Failed to load grid: invalid numeric value - " + e.getMessage());
            System.exit(1);
        }
    }

    private static void buildStructure(Grid grid, Scanner scanner) {
        try {
            System.out.print("Enter row (0-" + (grid.getHeight() - 1) + "): ");
            int row = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter column (0-" + (grid.getWidth() - 1) + "): ");
            int col = Integer.parseInt(scanner.nextLine());
            if (row < 0 || row >= grid.getHeight() || col < 0 || col >= grid.getWidth()) {
                System.out.println("Coordinates out of bounds.");
                return;
            }
            System.out.print("Enter floors: ");
            int floors = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter foundation (slab/stilts): ");
            String foundation = scanner.nextLine();
            System.out.print("Enter material (wood/stone/brick/concrete): ");
            String material = scanner.nextLine();

            Structure structure = new Structure(floors, foundation, material);
            GridSquare square = grid.getSquare(row, col);
            String reason = square.checkBuildable(structure);
            if (reason != null) {
                System.out.println("Cannot build: " + reason);
            } else {
                double cost = square.calculateCost(structure);
                System.out.println("Can build! Cost: $" + cost);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: please enter numeric values for row, column, and floors.");
        }
    }

    private static void buildCity(Grid grid, Scanner scanner) {
        System.out.println("Building city with " + buildApproach + " approach...");
        CityBuildStrategy strategy;
        switch (buildApproach) {
            case "uniform":
                strategy = new UniformStrategy(scanner);
                break;
            case "random":
                strategy = new RandomStrategy();
                break;
            case "central":
                strategy = new CentralStrategy();
                break;
            default:
                strategy = new RandomStrategy(); // Default
        }
        strategy.buildCity(grid);
    }

    private static void configure(Scanner scanner) {
        System.out.print("Enter build approach (uniform/random/central): ");
        buildApproach = scanner.nextLine();
        System.out.println("Build approach set to: " + buildApproach);
    }
}