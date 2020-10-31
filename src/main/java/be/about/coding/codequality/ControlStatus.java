package be.about.coding.codequality;

public enum ControlStatus {
    REGISTERING("Registering", "Listing all directories and files"),
    ANALYSING("Analysing", "Looping through all of the classes and make a code analysis"),
    DONE("Done", "Analysis of the codebase is done");

    private String name;
    private String description;

    ControlStatus(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
