package se.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
	
	Optional<VerificationToken> findByToken(String token);
	void deleteByToken(String token);

}
