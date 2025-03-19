package main.java.edu.curtin.app;

public class HeritageRule implements ZoningRule {
    private final String material;

    public HeritageRule(String material) {
        if (!material.equals("wood") && !material.equals("stone") && !material.equals("brick")) {
            throw new IllegalArgumentException("Invalid heritage material: " + material);
        }
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String getType() {
        return "heritage";
    }
}