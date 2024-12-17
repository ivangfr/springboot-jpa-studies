package com.ivanfranchin.jpabatch.rest.dto;

import com.ivanfranchin.jpabatch.partner.Partner;

public record PartnerResponse(Long id, String name) {

    public static PartnerResponse from(Partner partner) {
        return new PartnerResponse(partner.getId(), partner.getName());
    }
}
