package edu.curtin.app;

public class ContaminationRule implements ZoningRule {
    @Override
    public String checkBuildable(Structure structure) { // Renamed from canBuild
        return null;
    }

    @Override
    public double applyCost(double baseCost) { return baseCost * 1.5; }
}