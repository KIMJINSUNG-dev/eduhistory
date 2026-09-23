/*
 * EduHistory API 컨트롤러 클래스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.web;

import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryListResponse;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistorySearchVO;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryService;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/edu-histories")
@RequiredArgsConstructor
public class EduHistoryApiController {

    private final EduHistoryService eduHistoryService;

    @GetMapping
    public EduHistoryListResponse selectEduHistoryList(EduHistorySearchVO searchVO) {

        return eduHistoryService.selectEduHistoryList(searchVO);
    }

    @GetMapping("/download")
    public void downloadEduHistoryList(
            EduHistorySearchVO searchVO,
            HttpServletResponse response) throws IOException {

        List<EduHistoryVO> list = eduHistoryService.selectEduHistoryList(searchVO).getList();

        String fileName = "교육수강내역_"
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + ".csv";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replace("+", "%20");

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + encodedFileName + "\"; "
                            + "filename*=UTF-8''" + encodedFileName);

        // 엑셀에서 한글이 깨지지 않도록 BOM 기록
        response.getOutputStream().write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

        try (Writer writer = new OutputStreamWriter(
                response.getOutputStream(), StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            printer.printRecord("교육자", "교육시작일", "교육종료일", "과정명", "교육기관", "수강상태");

            for (EduHistoryVO vo : list) {
                printer.printRecord(
                        vo.getTraineeName(),
                        vo.getEduStartDate(),
                        vo.getEduEndDate(),
                        vo.getCourseName(),
                        vo.getInstitutionName(),
                        vo.getCourseStatus());
            }
            printer.flush();
        }
    }
}
