package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CompletedQuizService
{
    @Autowired
    CompletedQuizRepository repository;

    public Slice<CompletedQuiz> getAllCompletedQuizzes(Integer pageNo, Integer pageSize, long userId)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());

        Slice<CompletedQuiz> pagedResult = repository.findByUserId(userId, paging);

        return pagedResult;
    }
}