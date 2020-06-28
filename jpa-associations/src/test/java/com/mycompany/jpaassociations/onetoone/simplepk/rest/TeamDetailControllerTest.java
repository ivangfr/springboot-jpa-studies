package com.mycompany.jpaassociations.onetoone.simplepk.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.repository.TeamRepository;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.TeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TeamDetailControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void testGetTeam() {
        Team team = teamRepository.save(getDefaultTeam());

        String url = String.format(API_TEAMS_TEAM_ID_URL, team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.getForEntity(url, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(team.getId(), responseEntity.getBody().getId());
        assertEquals(team.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getTeamDetail());
    }

    @Test
    void testCreateTeam() {
        CreateTeamDto createTeamDto = getDefaultCreateTeamDto();
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.postForEntity(API_TEAMS_URL, createTeamDto, TeamDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createTeamDto.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getTeamDetail());

        Optional<Team> teamOptional = teamRepository.findById(responseEntity.getBody().getId());
        assertTrue(teamOptional.isPresent());
        teamOptional.ifPresent(t -> assertEquals(createTeamDto.getName(), t.getName()));
    }

    @Test
    void testUpdateTeam() {
        Team team = teamRepository.save(getDefaultTeam());
        UpdateTeamDto updateTeamDto = getDefaultUpdateTeamDto();

        HttpEntity<UpdateTeamDto> requestUpdate = new HttpEntity<>(updateTeamDto);
        String url = String.format(API_TEAMS_TEAM_ID_URL, team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateTeamDto.getName(), responseEntity.getBody().getName());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertTrue(teamOptional.isPresent());
        teamOptional.ifPresent(t -> assertEquals(updateTeamDto.getName(), t.getName()));
    }

    @Test
    void testDeleteTeam() {
        Team team = teamRepository.save(getDefaultTeam());

        String url = String.format(API_TEAMS_TEAM_ID_URL, team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(team.getId(), responseEntity.getBody().getId());
        assertEquals(team.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getTeamDetail());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertFalse(teamOptional.isPresent());
    }

    @Test
    void testAddTeamDetail() {
        Team team = teamRepository.save(getDefaultTeam());
        CreateTeamDetailDto createTeamDetailDto = getDefaultCreateTeamDetailDto();

        String url = String.format(API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL, team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.postForEntity(url, createTeamDetailDto, TeamDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getTeamDetail());
        assertEquals(createTeamDetailDto.getDescription(), responseEntity.getBody().getTeamDetail().getDescription());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertTrue(teamOptional.isPresent());
        teamOptional.ifPresent(t -> {
            assertNotNull(t.getTeamDetail());
            assertEquals(createTeamDetailDto.getDescription(), t.getTeamDetail().getDescription());
        });
    }

    @Test
    void testUpdateTeamDetail() {
        Team team = getDefaultTeam();
        team.addTeamDetail(getDefaultTeamDetail());
        team = teamRepository.save(team);

        UpdateTeamDetailDto updateTeamDetailDto = getDefaultUpdateTeamDetailDto();

        HttpEntity<UpdateTeamDetailDto> requestUpdate = new HttpEntity<>(updateTeamDetailDto);
        String url = String.format(API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL, team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getTeamDetail());
        assertEquals(updateTeamDetailDto.getDescription(), responseEntity.getBody().getTeamDetail().getDescription());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertTrue(teamOptional.isPresent());
        teamOptional.ifPresent(t -> {
            assertNotNull(t.getTeamDetail());
            assertEquals(updateTeamDetailDto.getDescription(), t.getTeamDetail().getDescription());
        });
    }

    @Test
    void testDeleteTeamDetail() {
        Team team = getDefaultTeam();
        team.addTeamDetail(getDefaultTeamDetail());
        team = teamRepository.save(team);

        String url = String.format(API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL, team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNull(responseEntity.getBody().getTeamDetail());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertTrue(teamOptional.isPresent());
        teamOptional.ifPresent(t -> assertNull(t.getTeamDetail()));
    }

    private Team getDefaultTeam() {
        Team team = new Team();
        team.setName("White Team");
        return team;
    }

    private CreateTeamDto getDefaultCreateTeamDto() {
        CreateTeamDto createTeamDto = new CreateTeamDto();
        createTeamDto.setName("White Team");
        return createTeamDto;
    }

    private UpdateTeamDto getDefaultUpdateTeamDto() {
        UpdateTeamDto updateTeamDto = new UpdateTeamDto();
        updateTeamDto.setName("Black Team");
        return updateTeamDto;
    }

    private TeamDetail getDefaultTeamDetail() {
        TeamDetail teamDetail = new TeamDetail();
        teamDetail.setDescription("This team is awesome");
        return teamDetail;
    }

    private CreateTeamDetailDto getDefaultCreateTeamDetailDto() {
        CreateTeamDetailDto createTeamDetailDto = new CreateTeamDetailDto();
        createTeamDetailDto.setDescription("This team is awesome");
        return createTeamDetailDto;
    }

    private UpdateTeamDetailDto getDefaultUpdateTeamDetailDto() {
        UpdateTeamDetailDto updateTeamDetailDto = new UpdateTeamDetailDto();
        updateTeamDetailDto.setDescription("This team is excellent");
        return updateTeamDetailDto;
    }

    private static final String API_TEAMS_URL = "/api/teams";
    private static final String API_TEAMS_TEAM_ID_URL = "/api/teams/%s";
    private static final String API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL = "/api/teams/%s/team-details";

}