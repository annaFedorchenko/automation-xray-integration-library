## Overview
The project contains methods that help in generating and sending X-ray report json. 
https://confluence.xpand-it.com/display/public/XRAY/Import+Execution+Results+-+REST#ImportExecutionResults-REST-XrayJSONresults

## Instalation
1) package project using 'mvn package' command or downloading .jar file from confluence page
2) add .jar file(e.g.xray-integration-library-1.0-SNAPSHOT.jar) to libs folder of the project
3) update pom.xml with dependency
```java
 <dependency>
            <groupId>models.xray</groupId>
            <artifactId>xray-integration-library</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>system</scope>
            <systemPath>${basedir}/libs/xray-integration-library-1.0-SNAPSHOT.jar</systemPath>
        </dependency>
```
4) refresh dependencies on the project

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
