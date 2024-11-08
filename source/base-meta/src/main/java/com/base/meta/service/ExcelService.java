package com.base.meta.service;

import com.base.meta.mapper.TestCaseUploadMapper;
import com.base.meta.model.TestCaseUpload;
import com.base.meta.repository.ProgramRepository;
import com.base.meta.repository.TestCaseRepository;
import com.base.meta.repository.TestStepRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class ExcelService {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//    static String[] HEADERs = {"Program id", "Test case precondition", "Test case menu path", "Test step action", "Test step data", "Test step expected result", "Test step actual result"};

    @Autowired
    ProgramRepository programRepository;
    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    TestStepRepository testStepRepository;
    @Autowired
    TestCaseUploadMapper testCaseUploadMapper;

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<TestCaseUpload> excelToTestCaseUploadData(InputStream inputStream) {
        List<TestCaseUpload> testCaseUploads = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    if (row.getRowNum() < 1) {
                        continue;
                    }

                    TestCaseUpload testCaseUpload = new TestCaseUpload();
                    testCaseUpload.setTestCaseName(sheet.getSheetName());

                    Cell programIdCell = row.getCell(0);
//                    log.info("Program id cell: " + programIdCell.getCellType());
                    if (programIdCell != null && programIdCell.getCellType() == CellType.STRING) {
                        Long programId = Long.parseLong(programIdCell.getStringCellValue());
//                        log.info("Program id: " + programId);
                        testCaseUpload.setProgram(programRepository.findById(programId).get());
                    }

                    Cell preconditionCell = row.getCell(1);
                    if (preconditionCell != null) {
//                        log.info("Precondition: " + preconditionCell.getStringCellValue());
                        testCaseUpload.setTestCasePrecondition(preconditionCell.getStringCellValue());
                    }

                    Cell menuPathCell = row.getCell(2);
                    if (menuPathCell != null) {
                        testCaseUpload.setTestCaseMenuPath(menuPathCell.getStringCellValue());
                    }

                    Cell actionCell = row.getCell(3);
                    if (actionCell != null) {
                        testCaseUpload.setTestStepsAction(actionCell.getStringCellValue());
                    }

                    Cell dataCell = row.getCell(4);
                    if (dataCell != null) {
                        testCaseUpload.setTestStepsData(dataCell.getStringCellValue());
                    }

                    Cell expectedResultCell = row.getCell(5);
                    if (expectedResultCell != null) {
                        testCaseUpload.setTestStepsExpectedResult(expectedResultCell.getStringCellValue());
                    }

                    Cell actualResultCell = row.getCell(6);
//                    log.info("Actual result: " + actualResultCell);
                    if (actualResultCell != null) {
                        testCaseUpload.setTestStepsActualResult(actualResultCell.getStringCellValue());
                    }

                    testCaseUploads.add(testCaseUpload);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }
        return testCaseUploads;
    }
}

