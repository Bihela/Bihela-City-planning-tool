package main.java.edu.curtin.app;

public class FloodRiskRule implements ZoningRule {
    private final double risk;

    public FloodRiskRule(double risk) {
        if (risk < 0 || risk > 100) {
            throw new IllegalArgumentException("Flood risk must be between 0 and 100: " + risk);
        }
        this.risk = risk;
    }

    public double getRisk() {
        return risk;
    }

    @Override
    public String getType() {
        return "flood-risk";
    }
}