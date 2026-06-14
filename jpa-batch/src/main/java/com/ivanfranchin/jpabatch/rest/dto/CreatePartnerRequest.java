package com.ivanfranchin.jpabatch.rest.dto;

import com.ivanfranchin.jpabatch.partner.Partner;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatePartnerRequest(@Schema(example = "partner1") @NotBlank String name) {

  public Partner toDomain() {
    Partner partner = new Partner();
    partner.setName(name);
    return partner;
  }
}
