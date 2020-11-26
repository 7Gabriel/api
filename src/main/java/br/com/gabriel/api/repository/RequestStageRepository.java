package br.com.gabriel.api.repository;

import br.com.gabriel.api.domain.RequestStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {

    public List<RequestStage> findAllByRequestId(Long id);
}
