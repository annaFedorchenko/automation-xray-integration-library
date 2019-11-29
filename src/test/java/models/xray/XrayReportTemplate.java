package models.xray;

public class XrayReportTemplate {
    private InfoObject infoObject;
    private TestObject[] testObjects;

    private XrayReportTemplate() {
    }

    public XrayReportTemplate(InfoObject infoObject, TestObject[] testObjects) {
        this.infoObject = infoObject;
        this.testObjects = testObjects;
    }

    public InfoObject getInfoObject() {
        return infoObject;
    }

    public TestObject[] getTestObjects() {
        return testObjects;
    }
    public static Builder newBuilder() {
        return new XrayReportTemplate().new Builder();
    }

    public class Builder {

        private InfoObject infoObject;
        private TestObject[] testObjects;

        private Builder() {
        }

        public Builder setInfoObject(InfoObject infoObject) {
            XrayReportTemplate.this.infoObject = infoObject;
            return this;
        }

        public Builder setTestObjects(TestObject[] testObjects) {
            XrayReportTemplate.this.testObjects = testObjects;
            return this;
        }

        public XrayReportTemplate build() {
            return XrayReportTemplate.this;
        }

    }
}
