package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class CompletedQuiz {


    @JsonIgnore
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completed_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "quiz_id")
    private long id;

    private String completedAt;

    CompletedQuiz(long quizId, User user){
        this.id = quizId;
        this.user = user;
        Date date= new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

        completedAt = ts.toString();
    }

    CompletedQuiz() {};

    public Long getCompleted_id() {
        return completed_id;
    }

    public void setCompleted_id(Long completed_id) {
        this.completed_id = completed_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

}
