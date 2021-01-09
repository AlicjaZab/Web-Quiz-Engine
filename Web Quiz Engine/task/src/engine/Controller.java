package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class Controller {

    String correctAnswer = "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
    String wrongAnswer = "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletedQuizRepository completedQuizRepository;

    @Autowired
    private CompletedQuizService completedQuizService;

    @Autowired
    QuizService quizService;


    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public String addQuiz(@RequestBody Quiz quiz)
    {
        if(quiz.getText().isEmpty() || quiz.getTitle().isEmpty() || quiz.getOptions().length <2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid quiz parameters");
        quiz.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
        return quizRepository.save(quiz).toString();
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public String getQuizById(@PathVariable long id)
    {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz with provided id doesn't exist"));
        return quiz.toString();
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page)
    {
        Page<Quiz> page_ = quizService.getAllQuizzes(page, 10);
        return page_;
    }

    @PostMapping(path = "/api/quizzes/{id}/solve", consumes = "application/json")
    public String checkAnswer(@PathVariable long id, @RequestBody(required = false) Answer answer)
    {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"));
        if (quiz.isCorrectAnswer(answer.getAnswer())){
            CompletedQuiz completedQuiz = new CompletedQuiz(id, userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
            completedQuizRepository.save(completedQuiz);
            return correctAnswer;
        }else
            return wrongAnswer;
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Slice<CompletedQuiz> getCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page)
    {
        Slice<CompletedQuiz> page_ = completedQuizService.getAllCompletedQuizzes(page, 10, userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId());
        return page_;
    }

    @PostMapping(path = "/api/register", consumes = "application/json")
    public HttpStatus register(@RequestBody User user)
    {
        //check if email and password are not empty & check if email is correct (with @ and . )
           if(user.getEmail() == null || user.getPassword() == null || !user.getEmail().contains("@") || !user.getEmail().contains("."))
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid registration parameters");

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given email already exists in the system");

        if(user.getPassword().length() < 5)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password must have at least five characters");

        user.encryptPassword();

        //add to database
        userRepository.save(user);

        return HttpStatus.OK;
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable long id)
    {
        if(!quizRepository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz with specified id doesn't exist");

        if(!quizRepository.findById(id).get().getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete a quiz if you are not the author");
        }

        quizRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
}
