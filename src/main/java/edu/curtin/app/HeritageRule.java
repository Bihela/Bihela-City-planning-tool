package edu.curtin.app;

public class HeritageRule implements ZoningRule {
    private final String material;

    public HeritageRule(String material) { this.material = material; }

    @Override
    public String checkBuildable(Structure structure) { // Renamed
        return material.equals(structure.getMaterial()) ? null : "Material must match heritage: " + material;
    }

    @Override
    public double applyCost(double baseCost) { return baseCost; }
}