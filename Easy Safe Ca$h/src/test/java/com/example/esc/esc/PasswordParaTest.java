package com.example.esc.esc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;

/**
 * Created by doanthanh on 2/4/18.
 */
@RunWith(Parameterized.class)
public class PasswordParaTest {
    private String password;
    private boolean valid;
    
    public PasswordParaTest(boolean valid, String password){
        this.valid = valid;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object [][] {
                // valid password format
                {true,"abc$E1"},
                {true, "ABcDEF2#"},
                {true, "a23qw%1qWE!()*yu"},
                // invalid passwords
                {false,"     "},
                {false, "abcdef"},
                {false, "ABCDEF"},
                {false, "123456"},
                {false, "#$%^&+"},
                {false, "cseQWER UIOPsdfgh"},
                {false, "myproject@gmail.com.12a"},
                {false, "thisisme..    sdfvfdc??"},
                {false, "12334$%^&"},
                {false, "thisisme..sdf1dc??"},
                {false, "thisiWE??"},
                {false, "this,is.WE??"},
                {false, "~`.,;'{}"},
                {false, "onelastime?????????????????"},

        });
    }

    @Test
    public void testPassword(){
        assertEquals(this.valid, Register.isPasswordValid(password));
    }

}
