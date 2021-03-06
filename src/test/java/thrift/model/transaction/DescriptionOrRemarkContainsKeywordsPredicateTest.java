package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import thrift.testutil.ExpenseBuilder;

public class DescriptionOrRemarkContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DescriptionOrRemarkContainsKeywordsPredicate firstPredicate = new DescriptionOrRemarkContainsKeywordsPredicate(
                firstPredicateKeywordList);
        DescriptionOrRemarkContainsKeywordsPredicate secondPredicate = new DescriptionOrRemarkContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionOrRemarkContainsKeywordsPredicate firstPredicateCopy =
                new DescriptionOrRemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        DescriptionOrRemarkContainsKeywordsPredicate predicate = new DescriptionOrRemarkContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Alice Bob").build()));

        // Multiple keywords
        predicate = new DescriptionOrRemarkContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Alice Bob").build()));

        // Only one matching keyword
        predicate = new DescriptionOrRemarkContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new DescriptionOrRemarkContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Alice Bob").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DescriptionOrRemarkContainsKeywordsPredicate predicate = new DescriptionOrRemarkContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("Alice").build()));

        // Non-matching keyword
        predicate = new DescriptionOrRemarkContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("Alice Bob").build()));

        // Keywords match value, but does not match name
        predicate = new DescriptionOrRemarkContainsKeywordsPredicate(Arrays.asList("12345", "Hearing", "$999"));
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("Airpods").withValue("12345").build()));
    }
}
