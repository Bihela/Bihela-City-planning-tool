package edu.curtin.app;

public enum Terrain {
    FLAT("flat"), SWAMPY("swampy"), ROCKY("rocky");

    private final String value;

    Terrain(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Terrain fromString(String value) {
        for (Terrain t : values()) { // Changed from Terrain.values()
            if (t.value.equalsIgnoreCase(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown terrain type: " + value);
    }
}