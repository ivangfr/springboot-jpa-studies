package com.mycompany.jpabatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
