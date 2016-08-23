package next.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import next.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserId(String userId);
	
}
