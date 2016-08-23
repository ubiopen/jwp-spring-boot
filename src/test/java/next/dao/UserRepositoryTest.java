package next.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import core.test.IntegrationTest;
import next.repositories.UserRepository;

public class UserRepositoryTest extends IntegrationTest {
	@Autowired
	private UserRepository userRepository;

	@Test
	public void crud() {
		fail("Not yet implemented");
	}

}
