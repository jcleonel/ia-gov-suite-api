package br.com.iagovsuite.api.controller.project;

import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<byte[]> downloadReport(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User user) {

        byte[] pdfContents = reportService.generateReportPdf(projectId, user);

        String filename = "Relatorio_Analise_" + projectId.toString().substring(0, 8) + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentLength(pdfContents.length);

        return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
    }
}
