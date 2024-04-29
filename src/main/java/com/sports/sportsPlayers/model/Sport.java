package com.sports.sportsPlayers.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreUpdate;

@Entity
public class Sport {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sportsId;

    @Column(nullable = false)
    private String sportsName;

    @ManyToMany
    @JoinTable(
        name = "PLAYER_SPORT",
        joinColumns = @JoinColumn(name = "sports_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> players;
    
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @PreUpdate
    public void updateModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }
    
    public Long getSportsId() {
		return sportsId;
	}

	public void setSportsId(Long sportsId) {
		this.sportsId = sportsId;
	}

	public String getSportsName() {
		return sportsName;
	}

	public void setSportsName(String sportsName) {
		this.sportsName = sportsName;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
}