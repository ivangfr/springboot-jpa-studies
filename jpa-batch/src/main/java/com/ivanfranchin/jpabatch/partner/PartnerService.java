package com.ivanfranchin.jpabatch.partner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PartnerService {

  private final PartnerRepository partnerRepository;

  public Partner savePartner(Partner partner) {
    return partnerRepository.save(partner);
  }

  public Partner validateAndGetPartner(Long id) {
    return partnerRepository.findById(id).orElseThrow(() -> new PartnerNotFoundException(id));
  }

  @Transactional
  public void deletePartner(Partner partner) {
    partnerRepository.delete(partner);
  }
}
