package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuizService
{
    @Autowired
    QuizRepository repository;

    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Quiz> pagedResult = repository.findAll(paging);

        return pagedResult;
    }
}