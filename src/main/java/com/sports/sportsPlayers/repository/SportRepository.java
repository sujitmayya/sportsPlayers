package com.sports.sportsPlayers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sports.sportsPlayers.model.Sport;

import java.util.List;
import java.util.Set;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
	List<Sport> findBySportsNameIn(List<String> sportNames);

	List<Sport> findByPlayersIsNotEmpty();
	
	Set<Sport> findBySportsIdIn(List<Long> sportIds);
	
	@Query("SELECT s FROM Sport s WHERE SIZE(s.players) >= 2")
    List<Sport> getSportsWithMoreThan2Players();
	
	@Query("SELECT s FROM Sport s WHERE s.players IS EMPTY")
    List<Sport> getSportsWithNoPlayers();
}
