package com.example.prince.cse;

/**
 * Created by doanthanh on 15/3/18.
 */
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.ListResourceBundle;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EmailParaTest {
    private String email;
    private boolean valid;

    private LoginActivity loginActivity;

    public EmailParaTest(boolean valid, String email){
        this.valid = valid;
        this.email = email;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object [][] {
                // valid emails
                {true,"abc@gmail.com"},
                {true, "this.is.email@gmail.com"},
                {true, "aaa23@gmail.com"},
                // invalid emails
                {false,""},
                {false,"mymail"},
                {false, "myname@"},
                {false, "123@."},
                {false, "123@#gmail.1"},
                {false, "helloworld@123@gmail.com"},
                {false, "myproject@gmail..12a"},
                {false, "thisisme..123@@gail.co"},
                {false, "onelastime@gmail.comco?.com"},
        });
    }

    @Test
    public void testEmail(){
        assertEquals(this.valid, loginActivity.isEmailValid(email));
    }

}
