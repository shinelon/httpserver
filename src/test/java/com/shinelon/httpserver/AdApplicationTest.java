package com.shinelon.httpserver;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shinelon.httpserver.HttpserverApplication;

/**
 * AdApplicationTests.java
 *
 * @author syq
 *
 *         2018年5月18日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpserverApplication.class)
public class AdApplicationTest {

    protected static final Logger logger = LoggerFactory.getLogger(AdApplicationTest.class);

}
