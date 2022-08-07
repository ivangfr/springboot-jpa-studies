package com.ivanfranchin.jpaassociations.onetoone.simplepk.rest.dto;

public record TeamResponse(Long id, String name, TeamDetailResponse teamDetail) {
}
