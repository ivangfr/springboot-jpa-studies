package com.ivanfranchin.jpaassociations.onetoone.simplepk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@ToString(exclude = "teamDetail")
@EqualsAndHashCode(exclude = "teamDetail")
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "team")
    private TeamDetail teamDetail;

    @Column(nullable = false)
    private String name;

    public void addTeamDetail(TeamDetail teamDetail) {
        this.teamDetail = teamDetail;
        teamDetail.setTeam(this);
    }

    public void removeTeamDetail() {
        this.teamDetail.setTeam(null);
        this.teamDetail = null;
    }
}
