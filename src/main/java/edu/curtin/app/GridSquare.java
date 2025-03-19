package main.java.edu.curtin.app;

import java.util.HashSet;
import java.util.Set;

public class GridSquare {
    private final Terrain terrain;
    private final Set<ZoningRule> zoningRules;

    public GridSquare(Terrain terrain) {
        this.terrain = terrain;
        this.zoningRules = new HashSet<>();
    }

    public void addZoningRule(ZoningRule rule) {
        zoningRules.add(rule);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Set<ZoningRule> getZoningRules() {
        return new HashSet<>(zoningRules); // Return a copy to prevent modification
    }
}