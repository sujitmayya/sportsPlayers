package com.sports.sportsPlayers.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.sports.sportsPlayers.dto.PlayerDto;
import com.sports.sportsPlayers.dto.SportDto;
import com.sports.sportsPlayers.model.Player;
import com.sports.sportsPlayers.model.Sport;

public class CopyUtil {
	public static List<PlayerDto> convertToPlayerDto(List<Player> players) {
		List<PlayerDto> playerDtoList = new ArrayList<>();
		for (Player player : players) {
			PlayerDto playerDto = new PlayerDto();
			BeanUtils.copyProperties(player, playerDto);
			if (player.getSports() != null && player.getSports().size() > 0) {
				Set<SportDto> sportDtoList = new HashSet<>();
				for (Sport sport : player.getSports()) {
					SportDto sportDto = new SportDto();
					BeanUtils.copyProperties(sport, sportDto);
					sportDtoList.add(sportDto);
				}
				playerDto.setSports(sportDtoList);
			}
			playerDtoList.add(playerDto);
		}
		return playerDtoList;
	}

	public static List<SportDto> convertToSportDto(List<Sport> sports) {
		List<SportDto> sportDtoList = new ArrayList<>();
		for (Sport sport : sports) {
			SportDto sportDto = new SportDto();
			BeanUtils.copyProperties(sport, sportDto);
			if (sport.getPlayers() != null && sport.getPlayers().size() > 0) {
				Set<PlayerDto> playerDtoList = new HashSet<>();
				for (Player player : sport.getPlayers()) {
					PlayerDto playerDto = new PlayerDto();
					BeanUtils.copyProperties(player, playerDto);
					playerDtoList.add(playerDto);
				}
				sportDto.setPlayers(playerDtoList);
			}
			sportDtoList.add(sportDto);
		}
		return sportDtoList;
	}
}
