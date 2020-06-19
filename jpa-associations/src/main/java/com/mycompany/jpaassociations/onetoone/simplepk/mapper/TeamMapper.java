package com.mycompany.jpaassociations.onetoone.simplepk.mapper;

import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.TeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TeamMapper {

    Team toTeam(CreateTeamDto createTeamDto);

    TeamDto toTeamDto(Team team);

    void updateTeamFromDto(UpdateTeamDto updateTeamDto, @MappingTarget Team team);

    TeamDetail toTeamDetail(CreateTeamDetailDto createTeamDetailDto);

    void updateTeamDetailFromDto(UpdateTeamDetailDto updateTeamDetailDto, @MappingTarget TeamDetail teamDetail);

}
