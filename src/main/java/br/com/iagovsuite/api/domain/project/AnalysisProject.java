package br.com.iagovsuite.api.domain.project;

import br.com.iagovsuite.api.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "analysis_project")
public class AnalysisProject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "client_name")
    private String clientName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "analysisProject", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DocumentFile documentFile;

    @OneToMany(mappedBy = "analysisProject", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Annotation> annotations = new ArrayList<>();

    public void setDocumentFile(DocumentFile documentFile) {
        if (documentFile == null) {
            if (this.documentFile != null) {
                this.documentFile.setAnalysisProject(null);
            }
        } else {
            documentFile.setAnalysisProject(this);
        }
        this.documentFile = documentFile;
    }

    public void addAnnotation(Annotation annotation) {
        annotations.add(annotation);
        annotation.setAnalysisProject(this);
    }

    public void removeAnnotation(Annotation annotation) {
        annotations.remove(annotation);
        annotation.setAnalysisProject(null);
    }

}