package com.mycompany.jpaassociations.onetoone.simplepk.rest;

import com.mycompany.jpaassociations.onetoone.simplepk.mapper.TeamMapper;
import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.TeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.service.TeamService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teams")
public class TeamDetailController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping("/{teamId}")
    public TeamDto getTeam(@PathVariable Long teamId) {
        Team team = teamService.validateAndGetTeam(teamId);
        return teamMapper.toTeamDto(team);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TeamDto createTeam(@Valid @RequestBody CreateTeamDto createTeamDto) {
        Team team = teamMapper.toTeam(createTeamDto);
        team = teamService.saveTeam(team);
        return teamMapper.toTeamDto(team);
    }

    @PutMapping("/{teamId}")
    public TeamDto updateTeam(@PathVariable Long teamId, @Valid @RequestBody UpdateTeamDto updateTeamDto) {
        Team team = teamService.validateAndGetTeam(teamId);
        teamMapper.updateTeamFromDto(updateTeamDto, team);
        teamService.saveTeam(team);
        return teamMapper.toTeamDto(team);
    }

    @DeleteMapping("/{teamId}")
    public TeamDto deleteTeam(@PathVariable Long teamId) {
        Team team = teamService.validateAndGetTeam(teamId);
        teamService.deleteTeam(team);
        return teamMapper.toTeamDto(team);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{teamId}/team-details")
    public TeamDto addTeamDetail(@PathVariable Long teamId, @Valid @RequestBody CreateTeamDetailDto createTeamDetailDto) {
        Team team = teamService.validateAndGetTeam(teamId);
        TeamDetail teamDetail = teamMapper.toTeamDetail(createTeamDetailDto);
        team.addTeamDetail(teamDetail);
        team = teamService.saveTeam(team);
        return teamMapper.toTeamDto(team);
    }

    @PutMapping("/{teamId}/team-details")
    public TeamDto updateTeamDetail(@PathVariable Long teamId, @Valid @RequestBody UpdateTeamDetailDto updateTeamDetailDto) {
        Team team = teamService.validateAndGetTeam(teamId);
        TeamDetail teamDetail = team.getTeamDetail();
        teamMapper.updateTeamDetailFromDto(updateTeamDetailDto, teamDetail);
        team = teamService.saveTeam(team);
        return teamMapper.toTeamDto(team);
    }

    @DeleteMapping("/{teamId}/team-details")
    public TeamDto deleteTeamDetail(@PathVariable Long teamId) {
        Team team = teamService.validateAndGetTeam(teamId);
        team.removeTeamDetail();
        team = teamService.saveTeam(team);
        return teamMapper.toTeamDto(team);
    }

}
