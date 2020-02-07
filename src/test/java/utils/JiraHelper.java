package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.xray.Info;
import models.xray.Test;
import models.xray.TestStatus;
import models.xray.XrayReportTemplate;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JiraHelper {
    public static Info executionInfo = new Info();
    private static XrayReportTemplate xrayReport;
    private static List<Test> tests = new ArrayList<>();

    /**
     * This method generates xrayReport.json and
     * should be used after all tests are finished
     *
     * @param xrayJsonReportFilePath - location of xrayReport.json, that will be generated
     * @throws IOException
     */
    public static void generateXrayReportJson(String xrayJsonReportFilePath) throws IOException {
        xrayReport = XrayReportTemplate.newBuilder()
                .setInfo(executionInfo)
                .setTests(tests.stream().toArray(Test[]::new))
                .build();

        File file = new File(xrayJsonReportFilePath);
        file.getParentFile().mkdirs();
        new FileWriter(file);

        (new ObjectMapper()).writeValue(new FileOutputStream(xrayJsonReportFilePath), xrayReport);

    }

    /**
     * This method generates xrayReport.json and sends it to jira
     *
     * @param username       - jira username
     * @param password       - jira password
     * @param jiraURL        - project url in jira
     * @param reportFilePath - location of xrayReport.json, that will be generated
     * @throws IOException
     */
    public static String sendXrayReportToJIRA(String username, String password, String jiraURL, String reportFilePath) throws IOException {
        generateXrayReportJson(reportFilePath);
        String[] curlRequest = {"curl", "-X", "POST",
                "-H", "Content-Type: application/json",
                "-H", "Authorization: Basic " + encodeBase64String(username + ":" + password),
                "--data", "@" + reportFilePath,
                jiraURL + "/rest/raven/1.0/import/execution"
        };

        ProcessBuilder processBuilder = new ProcessBuilder(curlRequest);
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.toString(curlRequest);
    }

    /**
     * This method should be used after each test
     * It collects jiraTicketNumber and statuses and stores then in list of test objects
     *
     * @param jiraTicketNumber
     * @param testStatus
     */
    public static void afterScenario(String jiraTicketNumber, TestStatus testStatus) {
        Test existingTest = JiraHelper.tests.stream()
                .filter(i -> i.getTestKey().equals(jiraTicketNumber))
                .findAny()
                .orElse(null);
        if (existingTest == null) {
            Test test = Test.newBuilder()
                    .setComment("Successful execution")
                    .setStatus(testStatus.toString())
                    .setTestKey(jiraTicketNumber)
                    .build();
            JiraHelper.tests.add(test);
        } else {
            existingTest.setStatus(testStatus.toString());
        }
    }

    /**
     * This method should be used after each test
     * It collects jiraTicketNumber and statuses and stores then in list of test objects
     *
     * @param jiraTicketNumber
     * @param isScenarioFailed
     */
    public static void afterScenario(String jiraTicketNumber, boolean isScenarioFailed) {
        TestStatus testStatus =  isScenarioFailed ? TestStatus.FAIL : TestStatus.PASS;
        afterScenario(jiraTicketNumber, testStatus);
    }

    private static String getScenarioStatus(boolean isScenarioFailed) {
        return isScenarioFailed ? "FAIL" : "PASS";
    }

    private static String encodeBase64String(String inputString) {
        return Base64.encodeBase64URLSafeString(inputString.getBytes());
    }
}
