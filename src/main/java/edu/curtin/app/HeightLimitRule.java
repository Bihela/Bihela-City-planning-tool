package main.java.edu.curtin.app;

public class HeightLimitRule implements ZoningRule {
    private final int limit;

    public HeightLimitRule(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Height limit must be positive: " + limit);
        }
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String getType() {
        return "height-limit";
    }
}