package ch.zkk0.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zkk0.football.model.Play;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {
}