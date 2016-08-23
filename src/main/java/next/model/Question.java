package next.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long questionId;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;

	private String title;

	private String contents;

	private Date createdDate;

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
	@OrderBy("answerId ASC")
	private List<Answer> answers;
	
	private int countOfComment;

	public Question() {
	}

	public Question(String writer, String title, String contents) {
		this(0, writer, title, contents, new Date(), 0);
	}

	public Question(long questionId, String writer, String title, String contents, Date createdDate,
			int countOfComment) {
		this.questionId = questionId;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
	}
	
	public int getCountOfComment() {
		return countOfComment;
	}

	public void setCountOfComment(int countOfComment) {
		this.countOfComment = countOfComment;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public long getTimeFromCreateDate() {
		return this.createdDate.getTime();
	}

	public Question newQuestion(User user) {
		return new Question(user.getUserId(), title, contents);
	}
	
	public boolean isSameUser(User user) {
		return user.isSameUser(this.writer);
	}

	public void update(Question newQuestion) {
		this.title = newQuestion.title;
		this.contents = newQuestion.contents;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", writer=" + writer + ", title=" + title + ", contents="
				+ contents + ", createdDate=" + createdDate + ", countOfComment=" + answers.size() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (questionId != other.questionId)
			return false;
		return true;
	}
}
