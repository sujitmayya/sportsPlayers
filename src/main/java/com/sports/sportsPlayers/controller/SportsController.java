package com.sports.sportsPlayers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sports.sportsPlayers.dto.PlayerDto;
import com.sports.sportsPlayers.dto.SportDto;
import com.sports.sportsPlayers.exception.SportsPlayersException;
import com.sports.sportsPlayers.service.SportsPlayersService;

@RestController
public class SportsController {

	private static final int PAGE_SIZE = 10;
	private SportsPlayersService sportsPlayersService;

	@Autowired
	public void setPlayerService(SportsPlayersService sportsPlayersService) {
		this.sportsPlayersService = sportsPlayersService;
	}

	public SportsController(SportsPlayersService sportsPlayersService) {
		this.sportsPlayersService = sportsPlayersService;
	}

	@GetMapping("/")
	public String test() throws SportsPlayersException {
		return "Hello";
	}

	/*
	 * (B-1) API Endpoint: Get Sports with Associated Players
	 */
	@GetMapping("/api/sports")
	public List<SportDto> getSportsWithassociatedPlayers(@RequestParam List<String> names) throws SportsPlayersException {
		return sportsPlayersService.getSportsWithassociatedPlayers(names);
	}

	/*
	 * (B-2) API Endpoint: return players with no associated sports.
	 */
	@GetMapping("/api/players-with-no-sports")
	public List<PlayerDto> getPlayersWithNoSports() throws SportsPlayersException {
		return sportsPlayersService.getPlayersWithNoSports();
	}

	/*
	 * (B-3) API Endpoint: Update a player's associated sports.
	 * Note: Ignores changes to player, only updates the sports ID associated with the player.
	 */
	@PostMapping("/api/update-player-sports")
	public ResponseEntity<String> updatePlayersSports(@RequestBody PlayerDto player) throws SportsPlayersException {
		boolean update = sportsPlayersService.updatePlayersSports(player);
		return ResponseEntity.ok("Player's sports updated successfully");
	}

	/*
	 * (B-4) API Endpoint: Delete Sports and their Associated Data.
	 * Note: Deletes Sports entry in the row. Will not delete players. 
	 */
	@PostMapping("/api/delete-sports/{sportId}")
	public ResponseEntity<String> deleteSports(@PathVariable Long sportId) throws SportsPlayersException {
		boolean update = sportsPlayersService.deleteSports(sportId);
		return ResponseEntity.ok("Sport with ID:" + sportId + " deleted successfully");
	}

	/*
	 * (B-5) API Endpoint: Paginated Player List with Sports Filter. Pagination
	 * should be by 10 players per page.
	 * Note: 10 is hardcoded. Page numbers start from 0 not 1;
	 */
	@GetMapping("/api/paginated-players-by-sports")
	public List<PlayerDto> paginatedPlayersBySports(@RequestParam List<String> names,
			@RequestParam int page) throws SportsPlayersException {
		
		return sportsPlayersService.paginatedPlayersBySports(names, page, PAGE_SIZE);
	}
}
