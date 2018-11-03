package com.mycompany.jpabatch.service;

import com.mycompany.jpabatch.model.Partner;

public interface PartnerService {

    Partner savePartner(Partner partner);

    Partner validateAndGetPartner(Long id);

    void deletePartner(Partner partner);
}
