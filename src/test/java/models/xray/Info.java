package models.xray;

public class Info {

    private String summary;
    private String description;
    private String user;
    private String[] testEnvironments;

    public Info(String summary, String description, String user, String[] testEnvironments) {
        this.summary = summary;
        this.description = description;
        this.user = user;
        this.testEnvironments = testEnvironments;
    }

    public Info() {
    }

    public String getSummary() {
        return summary;
    }

    public Info setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Info setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Info setUser(String user) {
        this.user = user;
        return this;
    }

    public String[] getTestEnvironments() {
        return testEnvironments;
    }

    public Info setTestEnvironments(String[] testEnvironments) {
        this.testEnvironments = testEnvironments;
        return this;
    }

}
