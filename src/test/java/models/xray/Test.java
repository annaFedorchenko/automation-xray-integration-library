package models.xray;

public class Test {
    private String testKey;
    private String comment;
    private String status;

    public Test(String testKey, String comment, String status) {
        this.testKey = testKey;
        this.comment = comment;
        this.status = status;
    }

    private Test() {
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
        return new Test().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setTestKey(String testKey) {
            Test.this.testKey = testKey;
            return this;
        }

        public Builder setComment(String comment) {
            Test.this.comment = comment;
            return this;
        }

        public Builder setStatus(String status) {
            Test.this.status = status;
            return this;
        }

        public Test build() {
            return Test.this;
        }

    }
}
