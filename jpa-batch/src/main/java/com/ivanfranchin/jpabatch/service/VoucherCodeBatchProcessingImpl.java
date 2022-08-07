package com.ivanfranchin.jpabatch.service;

import com.ivanfranchin.jpabatch.model.VoucherCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VoucherCodeBatchProcessingImpl implements VoucherCodeBatchProcessing {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private final EntityManager entityManager;

    @Override
    public List<VoucherCode> saveInBatch(List<VoucherCode> voucherCodes) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO voucher_codes (code, deleted, partner_id) VALUES (?, ?, ?)")) {

                int counter = 0;
                for (VoucherCode voucherCode : voucherCodes) {
                    ps.setString(1, voucherCode.getCode());
                    ps.setBoolean(2, voucherCode.getDeleted());
                    ps.setLong(3, voucherCode.getPartner().getId());
                    ps.addBatch();

                    if (++counter % batchSize == 0) {
                        ps.executeBatch();
                    }
                }

                ps.executeBatch(); // insert remaining records
                connection.commit();

            } catch (BatchUpdateException e) {
                log.info("Batch insertion has managed to process {} entries", e.getUpdateCounts().length);
                throw e;
            }
        });
        return voucherCodes;
    }
}
