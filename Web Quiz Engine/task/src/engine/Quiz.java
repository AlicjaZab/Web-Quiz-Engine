package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import javax.persistence.*;

@Entity
public class Quiz {

    private long id;
    private String title = "";
    private String text = "";
    private String[] options = new String[] {};

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer = new int[]{};

    @JsonIgnore
    private String author;

    public Quiz() {};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString(){
        String s = "[";
        for(String option : options) {
            s += "\"" + option + "\"" + ", ";
        }
        if(!(options.length == 0))
            s = s.substring(0, s.length()-2);
        s += "]";

        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"title\": \""+ title + "\",\n" +
                "  \"text\": \"" + text + "\",\n" +
                "  \"options\": " + s + "\n" +
                "}";
    }

    public boolean isCorrectAnswer(int[] answers) {
        if(this.answer.length == 0 && answers == null || this.answer.length == 0 && answers.length == 0){
            return true;
        }
        if(answers == null){
            return false;
        }
        if(this.answer.length == answers.length){

            for (int i = 0; i < answers.length; i++) {
                boolean contains = false;
                for(int j = 0; j < answers.length; j++) {
                    if(answers[j] == this.answer[i]){
                        contains = true;
                    }
                }
                if(!contains)
                    return false;
            }
            return true;
        }else{
            return false;
        }
    }


}
