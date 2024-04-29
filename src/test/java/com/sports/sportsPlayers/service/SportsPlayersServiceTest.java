package com.sports.sportsPlayers.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sports.sportsPlayers.dto.PlayerDto;
import com.sports.sportsPlayers.dto.SportDto;
import com.sports.sportsPlayers.exception.SportsPlayersException;
import com.sports.sportsPlayers.model.Player;
import com.sports.sportsPlayers.model.Player.Gender;
import com.sports.sportsPlayers.model.Sport;
import com.sports.sportsPlayers.repository.PlayerRepository;
import com.sports.sportsPlayers.repository.SportRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
class SportsPlayersServiceTest {

	@InjectMocks
	private SportsPlayersService sportsPlayersService;

	@Mock
	private PlayerRepository playerRepository;

	@Mock
	private SportRepository sportRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		// Mock Data:
		List<String> sportNames = Arrays.asList("Soccer", "Tennis");
		List<String> invalidSportsName = Arrays.asList("sitting", "sleeping");
		List<Sport> sportsList = getSomeSampleSports();
		List<Player> playerList = getSomeSamplePlayers();
		Set<Sport> sportsSet = new HashSet<>();
		Set<Player> playerSet = new HashSet<>();
		playerSet.addAll(playerList);
		sportsSet.addAll(sportsList);
		List<Long> sportIds = Arrays.asList(1L, 2L, 3L);

		playerList.get(0).setSports(sportsSet);
		sportsList.get(0).setPlayers(playerSet);

		when(sportRepository.findBySportsNameIn(sportNames)).thenReturn(sportsList);
		when(sportRepository.findBySportsNameIn(invalidSportsName)).thenReturn(sportsList);
		when(sportRepository.findByPlayersIsNotEmpty()).thenReturn(sportsList);
		when(sportRepository.findBySportsIdIn(sportIds)).thenReturn(sportsSet);

		when(playerRepository.findBySportsIsEmpty()).thenReturn(playerList.subList(1, 2));
		when(playerRepository.findById(123L)).thenReturn(Optional.ofNullable(playerList.get(0)));
		Pageable pageable = PageRequest.of(0, 10);
		when(playerRepository.findBySports_sportsNameIn(sportNames, pageable)).thenReturn(Page.empty());

	}

	@Test
	void testGetSportsWithassociatedPlayers() throws SportsPlayersException {
		List<String> sportNames = Arrays.asList("Soccer", "Tennis");

		List<SportDto> result = sportsPlayersService.getSportsWithassociatedPlayers(sportNames);

		assertNotNull(result.get(0).getSportsId());
		assertEquals(result.size(), 2);
	}

	@Test
	void testGetPlayersWithNoSports() throws SportsPlayersException {
		List<PlayerDto> playerDtoList = sportsPlayersService.getPlayersWithNoSports();
		assertEquals(playerDtoList.size(), 1);
	}

	@Test
	void testUpdatePlayersSports() {
		PlayerDto playerDto = new PlayerDto();
		playerDto.setPlayerId(123L);
		playerDto.setSports(Collections.singleton(new SportDto()));

		try {
			boolean result = sportsPlayersService.updatePlayersSports(playerDto);
			assertTrue(result);
			assertEquals(playerDto.getPlayerId(), 123L);
			playerDto.setPlayerId(1L);
			sportsPlayersService.updatePlayersSports(playerDto);
		} catch (SportsPlayersException ex) {
			assertNotEquals(playerDto.getPlayerId(), 123L);
		}
	}

	@Test
	void testDeleteSports() throws SportsPlayersException {
		try {
			sportsPlayersService.deleteSports(null);
		} catch (SportsPlayersException e) {
			assertEquals(e.getMessage(), "Invalid ID");
		}
	}
	
	@Test
	void testPaginatedPlayersBySports() throws SportsPlayersException {
		try {
			List<PlayerDto> results = sportsPlayersService.paginatedPlayersBySports(Arrays.asList("Soccer", "Tennis"), 0, 10);
			assertEquals(results.size(), 0);
			sportsPlayersService.paginatedPlayersBySports(null, 0, 10);
			
		} catch (SportsPlayersException e) {
			assertEquals(e.getMessage(), "Invalid Sports Names");
		}
	}

	private List<Player> getSomeSamplePlayers() {
		Player p1 = new Player();
		p1.setAge(1);
		p1.setEmail("asdf@asdf.com");
		p1.setGender(Gender.FEMALE);
		p1.setLevel(2);
		p1.setSports(null);
		Player p2 = new Player();
		p2.setAge(1);
		p2.setEmail("ddd@asdf.com");
		p2.setGender(Gender.FEMALE);
		p2.setLevel(2);
		p2.setSports(null);
		return Arrays.asList(p1, p2);
	}

	private List<Sport> getSomeSampleSports() {
		Sport p1 = new Sport();
		p1.setSportsId(4L);
		p1.setSportsName("Tennis");

		p1.setPlayers(null);

		Sport p2 = new Sport();
		p2.setSportsId(8L);
		p2.setSportsName("Football");

		return Arrays.asList(p1, p2);
	}

}
