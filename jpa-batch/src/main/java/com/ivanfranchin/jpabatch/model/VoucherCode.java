package com.ivanfranchin.jpabatch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "voucher_codes")
public class VoucherCode {

    @Id
    //-- If you want to use VoucherCodeBatchProcessing class instead of VoucherCodeRepository to save voucher codes in
    //-- batch, set the strategy to IDENTITY.
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", foreignKey = @ForeignKey(name = "FK_PARTNER"))
    private Partner partner;

    @Column(nullable = false)
    private String code;

    private Boolean deleted = false;

    public VoucherCode(Partner partner, String code) {
        this.partner = partner;
        this.code = code;
    }
}
