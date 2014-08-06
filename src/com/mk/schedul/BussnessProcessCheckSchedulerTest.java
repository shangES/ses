package com.mk.schedul;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: zhangyu
 * Date: 10-12-27
 * Time: 下午3:37
 */
@ContextConfiguration({"/spring-context.xml", "/spring-schedul.xml"})
public class BussnessProcessCheckSchedulerTest extends AbstractTestNGSpringContextTests {
    @Test
    public void testScheduler() throws Exception {
         System.in.read();
    }
}
