package br.com.gabriel.api.repository;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.RequestStage;
import br.com.gabriel.api.enums.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    public List<Request> findAllByOwnerId(Long id);

    @Query("UPDATE requests SET state = ?2 WHERE id = ?1")
    @Transactional
    public Request updateStatus(Long id, RequestState state);
}
