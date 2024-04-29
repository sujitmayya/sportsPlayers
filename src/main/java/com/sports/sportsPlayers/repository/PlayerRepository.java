package com.sports.sportsPlayers.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sports.sportsPlayers.model.Player;
import com.sports.sportsPlayers.model.Player.Gender;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	List<Player> findBySportsIsEmpty();

	Page<Player> findBySports_sportsNameIn(List<String> sportNames, Pageable pageable);
	List<Player> findBySports_sportsNameIn(List<String> sportNames);
	
	//ORM Query to fetch Players by gender and age:
	List<Player> findByGenderAndLevelAndAge(Gender gender, int level, int age);
	
	//Query format:
	@Query("SELECT p FROM Player p WHERE p.gender = :gender AND p.level = :level AND p.age = :age")
	List<Player> getPlayersByGenderLevelAge(Gender gender, int level, int age);

	//SQL Query: select * from PLAYER where gender = 'MALE' and level = 5 and age =30
}
