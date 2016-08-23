package next.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import next.CannotOperateException;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.repositories.AnswerRepository;
import next.repositories.QuestionRepository;

@Service
public class QnaService {
	private QuestionRepository questionRepository;
	private AnswerRepository answerRepository;

	@Autowired
	public QnaService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
	}

	public Question findById(long questionId) {
		return questionRepository.findByQuestionId(questionId);
	}

	public List<Answer> findAllByQuestion(long questionId) {
		Question question = questionRepository.findByQuestionId(questionId);
		return answerRepository.findByQuestion(question);
	}

	public void deleteQuestion(long questionId, User user) throws CannotOperateException {
		Question question = questionRepository.findByQuestionId(questionId);
		if (question == null) {
			throw new EmptyResultDataAccessException("존재하지 않는 질문입니다.", 1);
		}

		if (!question.isSameUser(user)) {
			throw new CannotOperateException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
		}

		List<Answer> answers = answerRepository.findByQuestion(question);
		if (answers.isEmpty()) {
			questionRepository.delete(question);
			return;
		}

		boolean canDelete = true;
		for (Answer answer : answers) {
			User writer = question.getWriter();
			if (!writer.equals(answer.getWriter())) {
				canDelete = false;
				break;
			}
		}

		if (!canDelete) {
			throw new CannotOperateException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
		}

		questionRepository.delete(question);
	}

	public void updateQuestion(long questionId, Question newQuestion, User user) throws CannotOperateException {
		Question question = questionRepository.findByQuestionId(questionId);
        if (question == null) {
        	throw new EmptyResultDataAccessException("존재하지 않는 질문입니다.", 1);
        }
        
        if (!question.isSameUser(user)) {
            throw new CannotOperateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        
        question.update(newQuestion);
        questionRepository.save(question);
	}
}
