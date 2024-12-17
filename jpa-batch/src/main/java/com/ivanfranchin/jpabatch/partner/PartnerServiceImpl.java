package com.ivanfranchin.jpabatch.partner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    @Override
    public Partner savePartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public Partner validateAndGetPartner(Long id) {
        return partnerRepository.findById(id).orElseThrow(() -> new PartnerNotFoundException(id));
    }

    @Transactional
    @Override
    public void deletePartner(Partner partner) {
        partnerRepository.delete(partner);
    }
}
