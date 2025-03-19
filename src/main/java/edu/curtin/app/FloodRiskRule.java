package edu.curtin.app;

public class FloodRiskRule implements ZoningRule {
    private final double risk;

    public FloodRiskRule(double risk) { this.risk = risk; }

    @Override
    public String checkBuildable(Structure structure) { // Renamed from canBuild
        return structure.getFloors() >= 2 ? null : "Flood risk requires at least 2 floors";
    }

    @Override
    public double applyCost(double baseCost) { return baseCost * (1 + risk / 50); }
}