package com.mycompany.jpaassociations.onetoone.simplepk.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.repository.TeamRepository;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDetailRequest;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamRequest;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.TeamResponse;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailRequest;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamRequest;
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

import static org.assertj.core.api.Assertions.assertThat;

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
        ResponseEntity<TeamResponse> responseEntity = testRestTemplate.getForEntity(url, TeamResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(team.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(team.getName());
        assertThat(responseEntity.getBody().getTeamDetail()).isNull();
    }

    @Test
    void testCreateTeam() {
        CreateTeamRequest createTeamRequest = new CreateTeamRequest("White Team");
        ResponseEntity<TeamResponse> responseEntity = testRestTemplate.postForEntity(
                API_TEAMS_URL, createTeamRequest, TeamResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(createTeamRequest.getName());
        assertThat(responseEntity.getBody().getTeamDetail()).isNull();

        Optional<Team> teamOptional = teamRepository.findById(responseEntity.getBody().getId());
        assertThat(teamOptional.isPresent()).isTrue();
        teamOptional.ifPresent(t -> assertThat(t.getName()).isEqualTo(createTeamRequest.getName()));
    }

    @Test
    void testUpdateTeam() {
        Team team = teamRepository.save(getDefaultTeam());
        UpdateTeamRequest updateTeamRequest = new UpdateTeamRequest("Black Team");

        HttpEntity<UpdateTeamRequest> requestUpdate = new HttpEntity<>(updateTeamRequest);
        String url = String.format(API_TEAMS_TEAM_ID_URL, team.getId());
        ResponseEntity<TeamResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, TeamResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(updateTeamRequest.getName());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertThat(teamOptional.isPresent()).isTrue();
        teamOptional.ifPresent(t -> assertThat(t.getName()).isEqualTo(updateTeamRequest.getName()));
    }

    @Test
    void testDeleteTeam() {
        Team team = teamRepository.save(getDefaultTeam());

        String url = String.format(API_TEAMS_TEAM_ID_URL, team.getId());
        ResponseEntity<TeamResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, TeamResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(team.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(team.getName());
        assertThat(responseEntity.getBody().getTeamDetail()).isNull();

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertThat(teamOptional.isPresent()).isFalse();
    }

    @Test
    void testAddTeamDetail() {
        Team team = teamRepository.save(getDefaultTeam());
        CreateTeamDetailRequest createTeamDetailRequest = new CreateTeamDetailRequest("This team is awesome");

        String url = String.format(API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL, team.getId());
        ResponseEntity<TeamResponse> responseEntity = testRestTemplate.postForEntity(
                url, createTeamDetailRequest, TeamResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getTeamDetail()).isNotNull();
        assertThat(responseEntity.getBody().getTeamDetail().getDescription())
                .isEqualTo(createTeamDetailRequest.getDescription());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertThat(teamOptional.isPresent()).isTrue();
        teamOptional.ifPresent(t -> {
            assertThat(t.getTeamDetail()).isNotNull();
            assertThat(t.getTeamDetail().getDescription()).isEqualTo(createTeamDetailRequest.getDescription());
        });
    }

    @Test
    void testUpdateTeamDetail() {
        Team team = getDefaultTeam();
        team.addTeamDetail(getDefaultTeamDetail());
        team = teamRepository.save(team);

        UpdateTeamDetailRequest updateTeamDetailRequest = new UpdateTeamDetailRequest("This team is excellent");

        HttpEntity<UpdateTeamDetailRequest> requestUpdate = new HttpEntity<>(updateTeamDetailRequest);
        String url = String.format(API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL, team.getId());
        ResponseEntity<TeamResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, TeamResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getTeamDetail()).isNotNull();
        assertThat(responseEntity.getBody().getTeamDetail().getDescription())
                .isEqualTo(updateTeamDetailRequest.getDescription());

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertThat(teamOptional.isPresent()).isTrue();
        teamOptional.ifPresent(t -> {
            assertThat(t.getTeamDetail()).isNotNull();
            assertThat(t.getTeamDetail().getDescription()).isEqualTo(updateTeamDetailRequest.getDescription());
        });
    }

    @Test
    void testDeleteTeamDetail() {
        Team team = getDefaultTeam();
        team.addTeamDetail(getDefaultTeamDetail());
        team = teamRepository.save(team);

        String url = String.format(API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL, team.getId());
        ResponseEntity<TeamResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, TeamResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getTeamDetail()).isNull();

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        assertThat(teamOptional.isPresent()).isTrue();
        teamOptional.ifPresent(t -> assertThat(t.getTeamDetail()).isNull());
    }

    private Team getDefaultTeam() {
        Team team = new Team();
        team.setName("White Team");
        return team;
    }

    private TeamDetail getDefaultTeamDetail() {
        TeamDetail teamDetail = new TeamDetail();
        teamDetail.setDescription("This team is awesome");
        return teamDetail;
    }

    private static final String API_TEAMS_URL = "/api/teams";
    private static final String API_TEAMS_TEAM_ID_URL = "/api/teams/%s";
    private static final String API_TEAMS_TEAM_ID_TEAMS_DETAILS_URL = "/api/teams/%s/team-details";

}