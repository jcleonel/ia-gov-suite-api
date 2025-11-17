package br.com.iagovsuite.api.domain.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnalysisProjectRepository extends JpaRepository<AnalysisProject, UUID> {

    List<AnalysisProject> findByUserId(UUID userId);

}