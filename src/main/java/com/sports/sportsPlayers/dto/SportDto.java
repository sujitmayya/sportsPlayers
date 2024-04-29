package com.sports.sportsPlayers.dto;

import java.util.Set;

import com.sports.sportsPlayers.model.Player;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

public class SportDto {
	private Long sportsId;

	private String sportsName;

	private Set<PlayerDto> players;
	
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

	public Set<PlayerDto> getPlayers() {
		return players;
	}

	public void setPlayers(Set<PlayerDto> players) {
		this.players = players;
	}
}
