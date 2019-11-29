package models.xray;

public class InfoObject {

    private String summary;
    private String description;
    private String user;
    private String[] testEnvironments;

    public InfoObject(String summary, String description, String user, String[] testEnvironments) {
        this.summary = summary;
        this.description = description;
        this.user = user;
        this.testEnvironments = testEnvironments;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String[] getTestEnvironments() {
        return testEnvironments;
    }

    public void setTestEnvironments(String[] testEnvironments) {
        this.testEnvironments = testEnvironments;
    }

}
