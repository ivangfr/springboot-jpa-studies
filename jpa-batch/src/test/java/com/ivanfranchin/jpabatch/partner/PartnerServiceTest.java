package com.ivanfranchin.jpabatch.partner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(PartnerService.class)
class PartnerServiceTest {

    @MockitoBean
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerService partnerService;

    @Test
    void validateAndGetPartnerWhenFoundShouldReturnPartner() {
        Partner partner = new Partner();
        partner.setId(1L);
        partner.setName("test");
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));

        Partner result = partnerService.validateAndGetPartner(1L);

        assertThat(result).isEqualTo(partner);
    }

    @Test
    void validateAndGetPartnerWhenNotFoundShouldThrow() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> partnerService.validateAndGetPartner(1L))
                .isInstanceOf(PartnerNotFoundException.class)
                .hasMessage("Partner with id '1' not found");
    }

    @Test
    void savePartnerShouldDelegateToRepository() {
        Partner partner = new Partner();
        partner.setName("test");
        when(partnerRepository.save(partner)).thenReturn(partner);

        Partner result = partnerService.savePartner(partner);

        assertThat(result).isEqualTo(partner);
        verify(partnerRepository).save(partner);
    }

    @Test
    void deletePartnerShouldDelegateToRepository() {
        Partner partner = new Partner();
        partner.setId(1L);

        partnerService.deletePartner(partner);

        verify(partnerRepository).delete(partner);
    }
}
