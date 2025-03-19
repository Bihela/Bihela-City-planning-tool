package edu.curtin.app;

public class HeightLimitRule implements ZoningRule {
    private final int limit;

    public HeightLimitRule(int limit) { this.limit = limit; }

    @Override
    public String checkBuildable(Structure structure) { // Renamed from canBuild
        return structure.getFloors() <= limit ? null : "Exceeds height limit of " + limit;
    }

    @Override
    public double applyCost(double baseCost) { return baseCost; }
}