package next.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import next.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	Question findByQuestionId(long questionId);
}
