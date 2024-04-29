package com.sports.sportsPlayers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sports.sportsPlayers.dto.PlayerDto;
import com.sports.sportsPlayers.dto.SportDto;
import com.sports.sportsPlayers.exception.SportsPlayersException;
import com.sports.sportsPlayers.helper.CopyUtil;
import com.sports.sportsPlayers.model.Player;
import com.sports.sportsPlayers.model.Sport;
import com.sports.sportsPlayers.repository.PlayerRepository;
import com.sports.sportsPlayers.repository.SportRepository;

@Service
public class SportsPlayersService {

	PlayerRepository playerRepository;

	@Autowired
	public void setPlayerRepository(PlayerRepository repository) {
		this.playerRepository = repository;
	}

	SportRepository sportRepository;

	@Autowired
	public void setSportRepository(SportRepository repository) {
		this.sportRepository = repository;
	}

	/*
	 * (B-1) Service: Get Sports with Associated Players
	 */
	public List<SportDto> getSportsWithassociatedPlayers(List<String> names) throws SportsPlayersException {
		if (names == null) {
			throw new SportsPlayersException("Invalid Sports Names");
		}
		List<Sport> sportsList = sportRepository.findBySportsNameIn(names);
		return CopyUtil.convertToSportDto(sportsList);
	}

	/*
	 * (B-2) Service: return players with no associated sports.
	 */
	public List<PlayerDto> getPlayersWithNoSports() throws SportsPlayersException {
		List<Player> players = playerRepository.findBySportsIsEmpty();
		return CopyUtil.convertToPlayerDto(players);
	}

	/*
	 * (B-3) Service: Update a player's associated sports.
	 */
	public boolean updatePlayersSports(PlayerDto playerDto) throws SportsPlayersException {

		Player player = playerRepository.findById(playerDto.getPlayerId()).orElse(null);
		if (player == null) {
			throw new SportsPlayersException("Player not found");
		}

		List<Long> sportsIds = new ArrayList<>();
		if (playerDto.getSports() != null && playerDto.getSports().size() > 0) {
			sportsIds = playerDto.getSports().stream().map(SportDto::getSportsId).collect(Collectors.toList());
			// clear existing sports for the player;
			player.getSports().forEach(s -> {
				if (s.getPlayers() != null) {
					s.getPlayers().remove(player);
				}
			});
			sportRepository.saveAll(player.getSports());
		}

		// Add new sports;
		Set<Sport> sportsList = sportRepository.findBySportsIdIn(sportsIds);
		sportsList.forEach(s -> {
			s.getPlayers().add(player);
		});

		sportRepository.saveAll(sportsList);
		return true;
	}

	/*
	 * (B-4) Service: Delete Sports and their Associated Data
	 */
	public boolean deleteSports(Long sportId) throws SportsPlayersException {
		if (sportId == null) {
			throw new SportsPlayersException("Invalid ID");
		}
		sportRepository.deleteById(sportId);
		return true;
	}

	/*
	 * (B-5) Service: Paginated Player List with Sports Filter. Pagination should be
	 * by 10 players per page.
	 */

	public List<PlayerDto> paginatedPlayersBySports(List<String> names, int pageNumber, int pageSize) throws SportsPlayersException {

		if (names == null) {
			throw new SportsPlayersException("Invalid Sports Names");
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Player> results = playerRepository.findBySports_sportsNameIn(names, pageable);
		List<Player> allPlayers = results.stream().collect(Collectors.toList());
		return CopyUtil.convertToPlayerDto(allPlayers);
	}

}
