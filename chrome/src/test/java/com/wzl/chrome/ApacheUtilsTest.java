package com.wzl.chrome;

import com.wzl.chrome.utils.ApacheIOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: wzl
 * @create: 2020/7/8
 * @description: 测试apache工具
 */
@SpringBootTest
public class ApacheUtilsTest {

    @Test
    public void testIO(){
//        ApacheIOUtils.read();
        ApacheIOUtils.write();
    }
}
