package helpers;

import entities.Card;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardListGeneratorTest {

    @Nested
    class GetSubset {
        @Test
        void empty() {
            assertEquals(List.of(), CardListGenerator.getSubset(new String[]{}, new char[]{}));
        }

        @Test
        void single() {
            assertEquals(List.of(new Card("10", 'S')), CardListGenerator.getSubset(new String[]{"10"}, new char[]{'S'}));
        }
    }


}