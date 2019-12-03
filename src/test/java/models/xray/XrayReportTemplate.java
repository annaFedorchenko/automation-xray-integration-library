package models.xray;

public class XrayReportTemplate {
    private Info info;
    private Test[] tests;

    private XrayReportTemplate() {
    }

    public XrayReportTemplate(Info info, Test[] tests) {
        this.info = info;
        this.tests = tests;
    }

    public Info getInfo() {
        return info;
    }

    public Test[] getTests() {
        return tests;
    }
    public static Builder newBuilder() {
        return new XrayReportTemplate().new Builder();
    }

    public class Builder {

        private Info info;
        private Test[] tests;

        private Builder() {
        }

        public Builder setInfo(Info info) {
            XrayReportTemplate.this.info = info;
            return this;
        }

        public Builder setTests(Test[] tests) {
            XrayReportTemplate.this.tests = tests;
            return this;
        }

        public XrayReportTemplate build() {
            return XrayReportTemplate.this;
        }

    }
}
