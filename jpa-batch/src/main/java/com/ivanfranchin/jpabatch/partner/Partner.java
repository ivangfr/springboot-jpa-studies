package com.ivanfranchin.jpabatch.partner;

import com.ivanfranchin.jpabatch.voucher.VoucherCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "voucherCodes")
@EqualsAndHashCode(exclude = "voucherCodes")
@Entity
@Table(name = "partners")
public class Partner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<VoucherCode> voucherCodes = new LinkedHashSet<>();

  @Column(nullable = false)
  private String name;
}
