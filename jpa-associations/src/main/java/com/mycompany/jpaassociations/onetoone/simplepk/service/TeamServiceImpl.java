package com.mycompany.jpaassociations.onetoone.simplepk.service;

import com.mycompany.jpaassociations.onetoone.simplepk.exception.TeamNotFoundException;
import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Team validateAndGetTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(String.format("Team with id '%s' not found", id)));
    }

    @Override
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }
}
