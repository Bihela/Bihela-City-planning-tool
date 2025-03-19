package edu.curtin.app;

public class Structure {
    private final int floors;
    private final String foundation; // "slab" or "stilts"
    private final String material;   // "wood", "stone", "brick", "concrete"

    public Structure(int floors, String foundation, String material) {
        this.floors = floors;
        this.foundation = foundation;
        this.material = material;
    }

    public int getFloors() { return floors; }
    public String getFoundation() { return foundation; }
    public String getMaterial() { return material; }
}