package com.ivanfranchin.jpaassociations.onetoone.sharedpk.rest.dto;

public record PersonResponse(Long id, String name, PersonDetailResponse personDetail) {
}
