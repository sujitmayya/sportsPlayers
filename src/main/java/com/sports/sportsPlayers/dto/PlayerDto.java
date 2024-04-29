package com.sports.sportsPlayers.dto;

import java.util.Set;

import com.sports.sportsPlayers.model.Player.Gender;

public class PlayerDto {

	private Long playerId;

	private String email;

	private Integer level;

	private Integer age;

	private Gender gender;

	private Set<SportDto> sports;

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Set<SportDto> getSports() {
		return sports;
	}

	public void setSports(Set<SportDto> sports) {
		this.sports = sports;
	}

}
