package edu.curtin.app;

public interface ZoningRule {
    String checkBuildable(Structure structure); // Renamed
    double applyCost(double baseCost);
}