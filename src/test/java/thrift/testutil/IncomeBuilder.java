package thrift.testutil;

import java.util.HashSet;
import java.util.Set;

import thrift.commons.util.StringUtil;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Income;
import thrift.model.transaction.Remark;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * A utility class to help with building Transaction objects.
 */
public class IncomeBuilder {

    public static final String DEFAULT_DESCRIPTION = "Bursary";
    public static final String DEFAULT_COST = "500";
    public static final String DEFAULT_DATE = "13/03/1937";

    private Description description;
    private Value value;
    private Remark remark;
    private TransactionDate date;
    private Set<Tag> tags;

    public IncomeBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        value = new Value(DEFAULT_COST);
        remark = new Remark("");
        date = new TransactionDate(DEFAULT_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code transactionToCopy}.
     */
    public IncomeBuilder(Transaction transactionToCopy) {
        description = transactionToCopy.getDescription();
        value = transactionToCopy.getValue();
        remark = transactionToCopy.getRemark();
        date = transactionToCopy.getDate();
        tags = new HashSet<>(transactionToCopy.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code Income} that we are building.
     */
    public IncomeBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Income} that we are building.
     */
    public IncomeBuilder withTags(String ... tags) {
        this.tags = StringUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Value} of the {@code Income} that we are building.
     */
    public IncomeBuilder withValue(String value) {
        this.value = new Value(value);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Income} that we are building.
     * @param remark
     * @return
     */
    public IncomeBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code TransactionDate} of the {@code Income} that we are building.
     */
    public IncomeBuilder withDate(String date) {
        this.date = new TransactionDate(date);
        return this;
    }

    public Income build() {
        return new Income(description, value, remark, date, tags);
    }

}
