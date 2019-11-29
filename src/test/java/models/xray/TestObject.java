package models.xray;

public class TestObject {
    private String testKey;
    private String comment;
    private String status;

    public TestObject(String testKey, String comment, String status) {
        this.testKey = testKey;
        this.comment = comment;
        this.status = status;
    }

    private TestObject() {
    }

    public String getTestKey() {
        return testKey;
    }

    public String getComment() {
        return comment;
    }

    public String getStatus() {
        return status;
    }

    public void setTestKey(String testKey) {
        this.testKey = testKey;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Builder newBuilder() {
        return new TestObject().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setTestKey(String testKey) {
            TestObject.this.testKey = testKey;
            return this;
        }

        public Builder setComment(String comment) {
            TestObject.this.comment = comment;
            return this;
        }

        public Builder setStatus(String status) {
            TestObject.this.status = status;
            return this;
        }

        public TestObject build() {
            return TestObject.this;
        }

    }
}
