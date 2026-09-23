/*
 * EduHistory 초기 데이터 적재를 위한 Data Initializer 클래스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.service.impl;

import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EduHistoryDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(EduHistoryDataInitializer.class);

    private final EduHistoryMapper eduHistoryMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (eduHistoryMapper.countAll() > 0) {

            log.info("DB에 데이터가 이미 존재합니다. 데이터 적재 작업을 건너뜁니다.");
            return;
        }

        List<EduHistoryVO> list = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("data/edu_history.csv");

        try (
            Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            CSVParser parser = CSVFormat.DEFAULT.parse(reader)
        ) {

            for (CSVRecord csvRecord : parser) {

                if (csvRecord.getRecordNumber() == 1) continue;
                EduHistoryVO vo = new EduHistoryVO();
                vo.setTraineeName(csvRecord.get(0).trim());
                vo.setEduStartDate(LocalDate.parse(csvRecord.get(1).trim()));
                vo.setEduEndDate(LocalDate.parse(csvRecord.get(2).trim()));
                vo.setCourseName(csvRecord.get(3).trim());
                vo.setInstitutionName(csvRecord.get(4).trim());
                vo.setCourseStatus(csvRecord.get(5).trim());
                list.add(vo);
            }
        }

        eduHistoryMapper.insertAll(list);
        log.info("데이터 {}건 적재 완료", list.size());
    }
}
