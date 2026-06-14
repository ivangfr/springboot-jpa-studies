package com.ivanfranchin.jpabatch.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ivanfranchin.jpabatch.partner.Partner;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@Import(VoucherCodeService.class)
@TestPropertySource(properties = "spring.jpa.properties.hibernate.jdbc.batch_size=10")
class VoucherCodeServiceTest {

  @MockitoBean private VoucherCodeRepository voucherCodeRepository;

  @MockitoBean private VoucherCodeBatchProcessing voucherCodeBatchProcessing;

  @Autowired private VoucherCodeService voucherCodeService;

  @Test
  void getStreamOfVoucherCodesByPartnerShouldReturnStream() {
    Partner partner = new Partner();
    VoucherCode voucherCode = new VoucherCode(partner, "code1");
    when(voucherCodeRepository.streamByPartner(partner)).thenReturn(Stream.of(voucherCode));

    Stream<VoucherCode> result = voucherCodeService.getStreamOfVoucherCodesByPartner(partner);

    assertThat(result).containsExactly(voucherCode);
  }

  @Test
  void getListOfVoucherCodesByPartnerShouldReturnList() {
    Partner partner = new Partner();
    VoucherCode voucherCode = new VoucherCode(partner, "code1");
    when(voucherCodeRepository.findByPartner(partner)).thenReturn(List.of(voucherCode));

    List<VoucherCode> result = voucherCodeService.getListOfVoucherCodesByPartner(partner);

    assertThat(result).containsExactly(voucherCode);
  }

  @Test
  void saveVoucherCodesShouldDelegateToRepository() {
    Partner partner = new Partner();
    VoucherCode voucherCode = new VoucherCode(partner, "code1");
    when(voucherCodeRepository.saveAll(List.of(voucherCode))).thenReturn(List.of(voucherCode));

    List<VoucherCode> result = voucherCodeService.saveVoucherCodes(List.of(voucherCode));

    assertThat(result).containsExactly(voucherCode);
  }

  @Test
  void deleteVoucherCodesShouldDeleteInBatchesOfBatchSize() {
    ReflectionTestUtils.setField(voucherCodeService, "batchSize", 2);
    Partner partner = new Partner();
    VoucherCode c1 = new VoucherCode(partner, "c1");
    VoucherCode c2 = new VoucherCode(partner, "c2");
    VoucherCode c3 = new VoucherCode(partner, "c3");

    voucherCodeService.deleteVoucherCodes(List.of(c1, c2, c3));

    ArgumentCaptor<List<VoucherCode>> captor =
        ArgumentCaptor.forClass((Class<List<VoucherCode>>) (Class<?>) List.class);
    verify(voucherCodeRepository, times(2)).deleteAllInBatch(captor.capture());
    List<List<VoucherCode>> batches = captor.getAllValues();
    assertThat(batches.get(0)).containsExactly(c1, c2);
    assertThat(batches.get(1)).containsExactly(c3);
  }
}
