package com.springboot;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @Author 刘宏飞
 * @Date 2020/11/24 10:08
 * @Version 1.0
 */
public class BCryptTest {

    @Test
    public void testEncrypt(){
        final String password = "123456";
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashpw);
    }
}
