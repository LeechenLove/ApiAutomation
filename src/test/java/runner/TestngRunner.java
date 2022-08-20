package runner;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lulu
 * @Description: 可以通过获取命令行参数选择跑哪个用例集
 * @DateTime: 2022/8/19 22:55
 **/
public class TestngRunner {
    public static void main(String[] args) {
        String smokesuiteXml = Paths.get(System.getProperty("user.dir"),
                "smoketest.xml").toString();
        String releasesuiteXml = Paths.get(System.getProperty("user.dir"),
                "releasetest.xml").toString();
        String defaultsuiteXml = Paths.get(System.getProperty("user.dir"),
                "testng.xml").toString();
        List<String> files = new ArrayList<>();
        if("smoke".equalsIgnoreCase(System.getProperty("testset"))) {
            files.add(smokesuiteXml);
        } else if("release".equalsIgnoreCase(System.getProperty("testset"))) {
            files.add(releasesuiteXml);
        } else {
            files.add(defaultsuiteXml);
        }
        XmlSuite suite = new XmlSuite();
        suite.setSuiteFiles(files);
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();
    }
}
