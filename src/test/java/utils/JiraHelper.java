package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.xray.InfoObject;
import models.xray.TestObject;
import models.xray.XrayReportTemplate;
import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JiraHelper {
    private static XrayReportTemplate xrayReport;
    private static List<TestObject> tests = new ArrayList<>();

    public static void generateXrayReportJson(String description, String summary, String user, String[] environments, String xaryJsonReportFilePath) throws IOException {
        xrayReport = XrayReportTemplate.newBuilder()
                .setInfoObject(new InfoObject(summary, description, user, environments))
                .setTestObjects(tests.stream().toArray(TestObject[]::new))
                .build();
        new ObjectMapper().writeValue(
                new FileOutputStream(xaryJsonReportFilePath), xrayReport);
    }

    public static void sendXrayReportToJIRA(String username, String password, String jiraURL, String reportFilePath) {
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
    }

    public void afterScenario(String jiraTicketNumber, boolean isScenarioFailed) {
        TestObject existingTest = JiraHelper.tests.stream()
                .filter(i -> i.getTestKey().equals(jiraTicketNumber))
                .findAny()
                .orElse(null);
        if (existingTest == null) {
            TestObject test = TestObject.newBuilder()
                    .setComment("Successful execution")
                    .setStatus(getScenarioStatus(isScenarioFailed))
                    .setTestKey(jiraTicketNumber)
                    .build();
            JiraHelper.tests.add(test);
        } else {
            if (isScenarioFailed) {
                existingTest.setStatus("FAIL");
            }
        }
    }


    //Cucumber only
    public String getJiraTicketNumber(List<String> tags, String projectId) {
        return tags.stream()
                .filter((i) -> i.contains(projectId))
                .map((i) -> i.replace("@", ""))
                .findFirst()
                .orElse(null);
    }

    private String getScenarioStatus(boolean isScenarioFailed) {
        return isScenarioFailed ? "FAIL" : "PASS";
    }

    private static String encodeBase64String(String inputString) {
        return Base64.encodeBase64URLSafeString(inputString.getBytes());
    }


}
