package br.com.iagovsuite.api.service;

import br.com.iagovsuite.api.domain.project.AnalysisProject;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

import com.lowagie.text.DocumentException;

@Service
public class ReportService {

    @Autowired
    private AnalysisProjectService analysisProjectService;

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generateReportPdf(UUID projectId, User user) {
        String html = processReportTemplate(projectId, user);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out);
            return out.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new FileStorageException("Erro ao gerar o relatório PDF", e);
        }
    }

    private String processReportTemplate(UUID projectId, User user) {
        AnalysisProject project = analysisProjectService.findProjectByIdAndVerifyAccess(projectId, user);

        Context context = new Context(new Locale("pt", "BR"));
        context.setVariable("project", project);
        context.setVariable("generationDate", getFormattedGenerationDate());

        return templateEngine.process("report-template", context);
    }

    private String getFormattedGenerationDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "d 'de' MMMM 'de' yyyy",
                new Locale("pt", "BR")
        );
        return now.format(formatter);
    }
}
