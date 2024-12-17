package com.ivanfranchin.jpabatch.partner;

public interface PartnerService {

    Partner savePartner(Partner partner);

    Partner validateAndGetPartner(Long id);

    void deletePartner(Partner partner);
}
