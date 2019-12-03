## Overview
The project contains methods that help in generating and sending X-ray report json. 
https://confluence.xpand-it.com/display/public/XRAY/Import+Execution+Results+-+REST#ImportExecutionResults-REST-XrayJSONresults

## Tutorial
To generate xray-report json perform the next steps:
1) add JiraHelper.afterScenario() method exectution after each test
```java
 @After
    public void afterScenario(Scenario scenario) {
        if (SEND_CUSTOM_REPORT_TO_JIRA) {
            JiraHelper.afterScenario(getJiraTicketNumber(scenario), scenario.isFailed());
        }
    }
```
2) generate xray json file after all tests are finished
```java
 @AfterClass
    public static void after() throws IOException {
        String[] environments = {ENV.getPropertyName()};
        JiraHelper.executionInfo
                .setSummary("[Marketplace] Automation tests run from Travis CI:" + LocalDateTime.now())
                .setDescription("This execution is automatically created when importing execution results")
                .setUser(JIRA_USERNAME)
                .setTestEnvironments(environments);
        JiraHelper.generateXrayReportJson(XRAY_JSON_REPORT_FILEPATH);
    }
```
3) to send report use sendXrayReportToJIRA that already includes xray-report generation:
```java
@AfterClass
    public static void after() throws IOException {
        String[] environments = {ENV.getPropertyName()};
        JiraHelper.executionInfo
                .setSummary("[Marketplace] Automation tests run from Travis CI:" + LocalDateTime.now())
                .setDescription("This execution is automatically created when importing execution results")
                .setUser(JIRA_USERNAME)
                .setTestEnvironments(environments);
        JiraHelper.sendXrayReportToJIRA(JIRA_USERNAME, JIRA_PASSWORD, JIRA_ROOT_URL, XRAY_JSON_REPORT_FILEPATH);
    }
```
