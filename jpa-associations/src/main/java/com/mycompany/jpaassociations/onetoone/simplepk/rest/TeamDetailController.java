package com.mycompany.jpaassociations.onetoone.simplepk.rest;

import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.TeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.service.TeamService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/teams")
public class TeamDetailController {

    private final TeamService teamService;
    private final MapperFacade mapperFacade;

    public TeamDetailController(TeamService teamService, MapperFacade mapperFacade) {
        this.teamService = teamService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping("/{teamId}")
    public TeamDto getTeam(@PathVariable Long teamId) {
        Team team = teamService.validateAndGetTeam(teamId);
        return mapperFacade.map(team, TeamDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TeamDto createTeam(@Valid @RequestBody CreateTeamDto createTeamDto) {
        Team team = mapperFacade.map(createTeamDto, Team.class);
        team = teamService.saveTeam(team);
        return mapperFacade.map(team, TeamDto.class);
    }

    @PutMapping("/{teamId}")
    public TeamDto updateTeam(@PathVariable Long teamId, @Valid @RequestBody UpdateTeamDto updateTeamDto) {
        Team team = teamService.validateAndGetTeam(teamId);
        mapperFacade.map(updateTeamDto, team);
        teamService.saveTeam(team);
        return mapperFacade.map(team, TeamDto.class);
    }

    @DeleteMapping("/{teamId}")
    public TeamDto deleteTeam(@PathVariable Long teamId) {
        Team team = teamService.validateAndGetTeam(teamId);
        teamService.deleteTeam(team);
        return mapperFacade.map(team, TeamDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{teamId}/team-details")
    public TeamDto addTeamDetail(@PathVariable Long teamId, @Valid @RequestBody CreateTeamDetailDto createTeamDetailDto) {
        Team team = teamService.validateAndGetTeam(teamId);
        TeamDetail teamDetail = mapperFacade.map(createTeamDetailDto, TeamDetail.class);
        team.addTeamDetail(teamDetail);
        team = teamService.saveTeam(team);
        return mapperFacade.map(team, TeamDto.class);
    }

    @PutMapping("/{teamId}/team-details")
    public TeamDto updateTeamDetail(@PathVariable Long teamId, @Valid @RequestBody UpdateTeamDetailDto updateTeamDetailDto) {
        Team team = teamService.validateAndGetTeam(teamId);
        TeamDetail teamDetail = team.getTeamDetail();
        mapperFacade.map(updateTeamDetailDto, teamDetail);
        team = teamService.saveTeam(team);
        return mapperFacade.map(team, TeamDto.class);
    }

    @DeleteMapping("/{teamId}/team-details")
    public TeamDto deleteTeamDetail(@PathVariable Long teamId) {
        Team team = teamService.validateAndGetTeam(teamId);
        team.removeTeamDetail();
        team = teamService.saveTeam(team);
        return mapperFacade.map(team, TeamDto.class);
    }

}
