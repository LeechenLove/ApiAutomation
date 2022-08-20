package testcase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/19 19:53
 **/
public class Testsuite {
    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite() {
        System.out.println("Api test starting...");
    }

    @Test(groups = "smoke")
    public void test() {
        System.out.println("smoke test");
    }

    @Test(groups = "release")
    public void test1() {
        System.out.println("release test");
    }

    @AfterSuite(alwaysRun = true)
    public void afterTestSuite() {
        System.out.println("Api test end...");
    }
}
