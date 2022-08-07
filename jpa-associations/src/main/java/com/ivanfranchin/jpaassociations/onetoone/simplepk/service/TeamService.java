package com.ivanfranchin.jpaassociations.onetoone.simplepk.service;

import com.ivanfranchin.jpaassociations.onetoone.simplepk.model.Team;

public interface TeamService {

    Team validateAndGetTeam(Long id);

    Team saveTeam(Team team);

    void deleteTeam(Team team);
}
