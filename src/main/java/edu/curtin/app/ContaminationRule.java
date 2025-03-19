package main.java.edu.curtin.app;

public class ContaminationRule implements ZoningRule {
    @Override
    public String getType() {
        return "contamination";
    }
}