package models.xray;

public enum TestStatus {
    PASS("PASS"),
    FAIL("FAIL"),
    ABORTED("ABORTED");

    String value;

    TestStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
