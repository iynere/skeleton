package dao;

import api.ReceiptResponse;
import generated.tables.Tags;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }


    public void insert(int receiptID, String tag) {
        dsl.insertInto(TAGS, TAGS.RECEIPT_ID, TAGS.TAG)
                .values(receiptID, tag)
                .execute();

        //checkState(TagsRecord != null && TagsRecord.getReceiptId() != null, "Insert failed");

    }

    public boolean tagExists(int receiptID, String tag) {

        int count = dsl.selectCount()
                       .from(TAGS)
                       .where(TAGS.RECEIPT_ID.eq(receiptID).and(TAGS.TAG.equal(tag)))
                       .fetchOne(0, int.class);

        return (count != 0);
    }

    public void deleteTag(int receiptID, String tag) {
        dsl.delete(TAGS).where(TAGS.RECEIPT_ID.eq(receiptID).and(TAGS.TAG.equal(tag))).execute();
    }


    public List<ReceiptsRecord> getAllReceiptsByTag(String tag) {
        System.out.println(tag);
        return dsl.select()
                  .from(RECEIPTS)
                  .join(TAGS)
                  .on(RECEIPTS.ID.eq(TAGS.RECEIPT_ID).and(TAGS.TAG.equal(tag)))
                  .fetchInto(RECEIPTS);
    }

    //Not used
    public List<TagsRecord> getAllTags() {
        return dsl.selectFrom(TAGS).fetch();
    }

}