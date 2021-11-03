package helpers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UsernameCheckTest {

    @ParameterizedTest
    @ValueSource(strings = {"raymond", "e", "stevenofsevennnn", "oh34t3", "aoifug24", "9824tsdf", "asdflj139oerugt2", "asdkjfhg92", "o8uergk24", "09u24tihkg284g", "kjhasdf-sadf", "asdfajsdf_f", "asdfljiasdf_fai"})
    void checkUsernameValid(String username) {
        assertTrue(UsernameCheck.checkUsername(username));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A^$#", "asdf 24t erg adf", "ouh4-24 g24", "hasdf83 asdf", "_faksdf_fasdf_", "asdflj_", "_idf"})
    void checkUsernameInvalid(String username) {
        assertFalse(UsernameCheck.checkUsername(username));
    }
}