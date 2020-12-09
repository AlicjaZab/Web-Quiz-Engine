package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuizRepository extends PagingAndSortingRepository<CompletedQuiz, Long> {

    //@Query(value = "SELECT quiz_id, completed_at FROM completed_quiz WHERE userid=?1")
    public Slice<CompletedQuiz> findByUserId(long userId, Pageable pageable);

    //public Slice<EmployeeEntity> findByFirstName(String firstName, Pageable pageable);
}
