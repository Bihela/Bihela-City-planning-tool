package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;

public class GridSquare implements CostCalculator {
    private final Terrain terrain;
    private final List<ZoningRule> zoningRules = new ArrayList<>();
    private CostCalculator costDecorator; // Initially null

    public GridSquare(Terrain terrain) {
        this.terrain = terrain;
        this.costDecorator = null;
    }

    public void addZoningRule(ZoningRule rule) {
        zoningRules.add(rule);
        // Wrap the previous decorator (or a base calculator) with a new decorator
        costDecorator = new ZoningCostDecorator(costDecorator == null ? new BaseCostCalculator(this) : costDecorator, rule);
    }

    public String checkBuildable(Structure structure) {
        if ("slab".equals(structure.getFoundation()) && terrain == Terrain.SWAMPY) {
            return "Cannot build slab foundation on swampy terrain";
        }
        if ("wood".equals(structure.getMaterial()) && terrain == Terrain.SWAMPY) {
            return "Cannot build wooden structure on swampy terrain";
        }
        for (ZoningRule rule : zoningRules) {
            String reason = rule.checkBuildable(structure);
            if (reason != null) {
                return reason;
            }
        }
        return null; // Can build
    }

    @Override
    public double calculateCost(Structure structure) {
        CostCalculator base = new BaseCostCalculator(this);
        if (costDecorator == null) {
            return base.calculateCost(structure); // No decorators, use base cost
        }
        return costDecorator.calculateCost(structure); // Delegate to decorator chain
    }

    // Expose base cost calculation for the BaseCostCalculator
    double baseCost(Structure structure) {
        double baseCost;
        switch (structure.getMaterial()) {
            case "wood": baseCost = 10000 * structure.getFloors(); break;
            case "stone": baseCost = 50000 * structure.getFloors(); break;
            case "brick": baseCost = 30000 * structure.getFloors(); break;
            case "concrete": baseCost = 20000 * structure.getFloors(); break;
            default: throw new IllegalArgumentException("Unknown material");
        }
        if (terrain == Terrain.SWAMPY) {
            baseCost += 20000 * structure.getFloors();
        }
        if (terrain == Terrain.ROCKY) {
            baseCost += 50000;
        }
        return baseCost;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}

// Inner class to handle base cost calculation without recursion
class BaseCostCalculator implements CostCalculator {
    private final GridSquare gridSquare;

    public BaseCostCalculator(GridSquare gridSquare) {
        this.gridSquare = gridSquare;
    }

    @Override
    public double calculateCost(Structure structure) {
        return gridSquare.baseCost(structure);
    }
}