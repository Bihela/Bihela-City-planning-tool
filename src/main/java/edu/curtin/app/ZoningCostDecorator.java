package edu.curtin.app;

public class ZoningCostDecorator implements CostCalculator {
    private final CostCalculator baseCalculator;
    private final ZoningRule rule;

    public ZoningCostDecorator(CostCalculator baseCalculator, ZoningRule rule) {
        this.baseCalculator = baseCalculator;
        this.rule = rule;
    }

    @Override
    public double calculateCost(Structure structure) {
        double cost = baseCalculator.calculateCost(structure); // Line 14
        return rule.applyCost(cost);
    }
}