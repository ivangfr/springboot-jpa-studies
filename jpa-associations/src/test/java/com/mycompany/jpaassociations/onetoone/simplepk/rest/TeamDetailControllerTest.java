package com.mycompany.jpaassociations.onetoone.simplepk.rest;

import com.mycompany.jpaassociations.ContainersExtension;
import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.repository.TeamRepository;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.CreateTeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.TeamDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class, ContainersExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TeamDetailControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void testGetTeam() {
        Team team = getDefaultTeam();
        team = teamRepository.save(team);

        String url = String.format("/api/teams/%s", team.getId());
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
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.postForEntity("/api/teams", createTeamDto, TeamDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createTeamDto.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getTeamDetail());

        Optional<Team> optionalTeam = teamRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalTeam.isPresent());
        assertEquals(createTeamDto.getName(), optionalTeam.get().getName());
    }

    @Test
    void testUpdateTeam() {
        Team team = getDefaultTeam();
        team = teamRepository.save(team);

        UpdateTeamDto updateTeamDto = getDefaultUpdateTeamDto();

        HttpEntity<UpdateTeamDto> requestUpdate = new HttpEntity<>(updateTeamDto);
        String url = String.format("/api/teams/%s", team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateTeamDto.getName(), responseEntity.getBody().getName());

        Optional<Team> optionalTeam = teamRepository.findById(team.getId());
        assertTrue(optionalTeam.isPresent());
        assertEquals(updateTeamDto.getName(), optionalTeam.get().getName());
    }

    @Test
    void testDeleteTeam() {
        Team team = getDefaultTeam();
        team = teamRepository.save(team);

        String url = String.format("/api/teams/%s", team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(team.getId(), responseEntity.getBody().getId());
        assertEquals(team.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getTeamDetail());

        Optional<Team> optionalTeam = teamRepository.findById(team.getId());
        assertFalse(optionalTeam.isPresent());
    }

    @Test
    void testAddTeamDetail() {
        Team team = getDefaultTeam();
        team = teamRepository.save(team);

        CreateTeamDetailDto createTeamDetailDto = getDefaultCreateTeamDetailDto();

        String url = String.format("/api/teams/%s/team-details", team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.postForEntity(url, createTeamDetailDto, TeamDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getTeamDetail());
        assertEquals(createTeamDetailDto.getDescription(), responseEntity.getBody().getTeamDetail().getDescription());

        Optional<Team> optionalTeam = teamRepository.findById(team.getId());
        assertTrue(optionalTeam.isPresent());
        assertNotNull(optionalTeam.get().getTeamDetail());
        assertEquals(createTeamDetailDto.getDescription(), optionalTeam.get().getTeamDetail().getDescription());
    }

    @Test
    void testUpdateTeamDetail() {
        Team team = getDefaultTeam();
        TeamDetail teamDetail = getDefaultTeamDetail();
        team.addTeamDetail(teamDetail);
        team = teamRepository.save(team);

        UpdateTeamDetailDto updateTeamDetailDto = getDefaultUpdateTeamDetailDto();

        HttpEntity<UpdateTeamDetailDto> requestUpdate = new HttpEntity<>(updateTeamDetailDto);
        String url = String.format("/api/teams/%s/team-details", team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getTeamDetail());
        assertEquals(updateTeamDetailDto.getDescription(), responseEntity.getBody().getTeamDetail().getDescription());

        Optional<Team> optionalTeam = teamRepository.findById(team.getId());
        assertTrue(optionalTeam.isPresent());
        assertNotNull(optionalTeam.get().getTeamDetail());
        assertEquals(updateTeamDetailDto.getDescription(), optionalTeam.get().getTeamDetail().getDescription());
    }

    @Test
    void testDeleteTeamDetail() {
        Team team = getDefaultTeam();
        TeamDetail teamDetail = getDefaultTeamDetail();
        team.addTeamDetail(teamDetail);
        team = teamRepository.save(team);

        String url = String.format("/api/teams/%s/team-details", team.getId());
        ResponseEntity<TeamDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, TeamDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNull(responseEntity.getBody().getTeamDetail());

        Optional<Team> optionalTeam = teamRepository.findById(team.getId());
        assertTrue(optionalTeam.isPresent());
        assertNull(optionalTeam.get().getTeamDetail());
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

}