package com.ivanfranchin.jpabatch.service;

import com.ivanfranchin.jpabatch.model.Partner;

public interface PartnerService {

    Partner savePartner(Partner partner);

    Partner validateAndGetPartner(Long id);

    void deletePartner(Partner partner);
}
