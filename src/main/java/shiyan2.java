import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.txtutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
@RunWith(Parameterized.class)
public class shiyan2 {
    private String url;
    private String body;

    @Before
    public void initialize() {
        System.out.println(123);
    }

    public shiyan2(String url, String body,String host) {
        this.url = url;
        this.body = body;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
//        ArrayList<String[]> x=new ArrayList();
//        String[] x1=new String[]{ "2", "true", "true" };
//        x.add(x1);
        return txtutil.x("/Users/zlr/Desktop/zlr/shiyan2/src/main/resources/1");
    }

    @Test
    public void testPrimeNumberChecker() {
        System.out.println("Parameterized Number is : " + url);
        System.out.println("Parameterized Number is : " + url);
        System.out.println("Parameterized Number is : " + url);
//        assertEquals(expectedResult, primeNumberChecker.validate(inputNumber));
    }
}
