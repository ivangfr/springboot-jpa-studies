package com.mycompany.jpaassociations.onetoone.simplepk.mapper;

import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDetailRequest;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamRequest;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.TeamResponse;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailRequest;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TeamMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamDetail", ignore = true)
    Team toTeam(CreateTeamRequest createTeamRequest);

    TeamResponse toTeamResponse(Team team);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamDetail", ignore = true)
    void updateTeamFromRequest(UpdateTeamRequest updateTeamRequest, @MappingTarget Team team);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    TeamDetail toTeamDetail(CreateTeamDetailRequest createTeamDetailRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    void updateTeamDetailFromRequest(UpdateTeamDetailRequest updateTeamDetailRequest,
                                     @MappingTarget TeamDetail teamDetail);
}
