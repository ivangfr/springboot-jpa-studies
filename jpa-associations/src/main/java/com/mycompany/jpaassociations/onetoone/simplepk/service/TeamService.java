package com.mycompany.jpaassociations.onetoone.simplepk.service;

import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;

public interface TeamService {

    Team validateAndGetTeam(Long id);

    Team saveTeam(Team team);

    void deleteTeam(Team team);

}
