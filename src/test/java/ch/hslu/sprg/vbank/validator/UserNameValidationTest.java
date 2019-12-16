package ch.hslu.sprg.vbank.validator;

import ch.hslu.sprg.vbank.model.domainprimitives.UserName;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class UserNameValidationTest {

    private static final Logger logger = Logger.getAnonymousLogger();

    BiFunction<String, Integer, String> repeatString = (str, count) -> {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i<count; i++) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    };


    @Test(expected = ValidationException.class)
    public void testZeroLength() {
        new UserName("");
    }

    @Test(expected = ValidationException.class)
    public void testBelowMinLength() {
        new UserName("xy");
    }

    @Test
    public void testOnMinLength() {
        new UserName("xyz");
    }

    @Test(expected = ValidationException.class)
    public void testAboveMaxLength() {
        new UserName("abcdefghij__________0123456789_");
    }

    @Test
    public void testOnMaxLength() {
        new UserName("abcdefghij__________0123456789");
    }

    //these should all succeed
    @Test
    public void testValidUserNames() {
        Arrays.asList("Hello","Hello_","Hello_01", "01Hello", "01_Hello", "_01Hello", "010203")
                .forEach(username -> {
                    try {
                        new UserName(username);
                    } catch (ValidationException ve) {
                        Assert.fail("This should be valid : " + username);
                    }
                });
    }

    //and these should all fail
    @Test
    public void testInValidUserNames() {
        Arrays.asList(null, "", "\t", "\n", "ab\t", "ab\n",
                "'1","hello--","hello #", "<script>", "Hello&", "javascript:hello()", "Hello!01",
                "'or%20select *","admin'--", "'%20or%20''='", "%20or%20'x'='x", "<>\"'%;)(&+", ")%20or%20('x'='x",
                "0 or 1=1", "' or 0=0 --", "\" or 0=0 --")
                .forEach(username -> {
                    try {
                        new UserName(username);
                        Assert.fail("This should be invalid : " + username);
                    } catch (ValidationException ve) {
                    }
                });
    }

    //timeout is in milliseconds
    @Test(timeout = 200)
    public void testExtremeLengths() {
        Stream.<Supplier<String>>of(
                () -> repeatString.apply("x", 10000),
                () -> repeatString.apply("y", 100000),
                () -> repeatString.apply("z", 1000000),
                () -> repeatString.apply("w4ry_!", 1000000)
        ).forEach(stringSupplier -> {
                    try {
                        String username = stringSupplier.get();
                        logger.info("username.length : " + username.length());
                        new UserName(username);
                        Assert.fail("This should be invalid : " + username);
                    } catch (ValidationException ve) {
                    }
                }
        );
    }

}
