package com.base.meta.service;

import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.mapper.TestCaseUploadMapper;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestCaseUpload;
import com.base.meta.model.TestStep;
import com.base.meta.repository.AccountRepository;
import com.base.meta.repository.ProgramRepository;
import com.base.meta.repository.TestCaseRepository;
import com.base.meta.repository.TestStepRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;

@Service
@Slf4j
public class ExcelService {

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String[] TCUPHEADERS = {"Program ID", "Member ID", "Test Case Name", "Test Case Pre-condition", "Test Case Menu Path", "Test Step Action", "Test Step Data", "Test Step Expected Result"};
    private static final String[] PGUPHEADERS = {"Program ID", "Program Name", "Category", "Type", "Requirement ID", "Start Date", "End Date", "Description"};
    final ProgramRepository programRepository;
    final TestCaseRepository testCaseRepository;
    final TestStepRepository testStepRepository;
    final TestCaseUploadMapper testCaseUploadMapper;
    final AccountRepository accountRepository;

    public ExcelService(ProgramRepository programRepository, TestCaseRepository testCaseRepository, TestStepRepository testStepRepository, TestCaseUploadMapper testCaseUploadMapper, AccountRepository accountRepository) {
        this.programRepository = programRepository;
        this.testCaseRepository = testCaseRepository;
        this.testStepRepository = testStepRepository;
        this.testCaseUploadMapper = testCaseUploadMapper;
        this.accountRepository = accountRepository;
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    private boolean validateTCUPHeaders(Row row) {
        return row.getCell(0).getStringCellValue().equals(TCUPHEADERS[0])
                && row.getCell(1).getStringCellValue().equals(TCUPHEADERS[1])
                && row.getCell(2).getStringCellValue().equals(TCUPHEADERS[2])
                && row.getCell(3).getStringCellValue().equals(TCUPHEADERS[3])
                && row.getCell(4).getStringCellValue().equals(TCUPHEADERS[4])
                && row.getCell(5).getStringCellValue().equals(TCUPHEADERS[5])
                && row.getCell(6).getStringCellValue().equals(TCUPHEADERS[6])
                && row.getCell(7).getStringCellValue().equals(TCUPHEADERS[7]);
    }

    private Map<Integer, Consumer<Cell>> createCellMapping(TestCaseUpload testCaseUpload) {
        return Map.of(
                0, cell -> testCaseUpload.setProgram(programRepository.findFirstById(Long.parseLong(cell.getStringCellValue()))),
                1, cell -> testCaseUpload.setCreatedBy(accountRepository.findFirstById(Long.parseLong(cell.getStringCellValue())).getUsername()),
                2, cell -> testCaseUpload.setTestCaseName(cell.getStringCellValue()),
                3, cell -> testCaseUpload.setTestCasePrecondition(cell.getStringCellValue()),
                4, cell -> testCaseUpload.setTestCaseMenuPath(cell.getStringCellValue()),
                5, cell -> testCaseUpload.setTestStepsAction(cell.getStringCellValue()),
                6, cell -> testCaseUpload.setTestStepsData(cell.getStringCellValue()),
                7, cell -> testCaseUpload.setTestStepsExpectedResult(cell.getStringCellValue())
        );
    }

    private TestCaseUpload createTestCaseUpload(Row row) {
        TestCaseUpload testCaseUpload = new TestCaseUpload();

        Map<Integer, Consumer<Cell>> cellMapping = createCellMapping(testCaseUpload);

        for (Map.Entry<Integer, Consumer<Cell>> entry : cellMapping.entrySet()) {
            Cell cell = row.getCell(entry.getKey());
            if (cell != null && cell.getCellType() == CellType.STRING) {
                entry.getValue().accept(cell);
            }
        }
        return testCaseUpload;
    }

    public List<TestCaseUpload> mapExcelToData(InputStream inputStream) {
        List<TestCaseUpload> testCaseUploads = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        if (!validateTCUPHeaders(row)) {
                            throw new BadRequestException("Invalid Excel column format");
                        }
                        continue;
                    }

                    TestCaseUpload testCaseUpload = createTestCaseUpload(row);
                    testCaseUploads.add(testCaseUpload);
                }
            }
        } catch (IOException ex) {
            throw new BadRequestException("Failed to parse Excel file.\nError: " + ex.getMessage());
        }

        return testCaseUploads;
    }

    public void exportTestCaseDataToExcelFile(Long testCaseId, String excelFilePath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Test Case Data");

            int rowCount = 0;
            sheet.autoSizeColumn(0);
            Row row = sheet.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellValue("Test Case Name");
            cell = row.createCell(1);
            cell.setCellValue("Test Case Precondition");
            cell = row.createCell(2);
            cell.setCellValue("Test Case Menu Path");

            TestCase testcase = testCaseRepository.findById(testCaseId).orElseThrow(()
                    -> new NotFoundException("Test case not found"));

            row = sheet.createRow(rowCount++);
            cell = row.createCell(0);
            cell.setCellValue(testcase.getName());
            cell = row.createCell(1);
            cell.setCellValue(testcase.getPrecondition());
            cell = row.createCell(2);
            cell.setCellValue(testcase.getMenuPath());

            List<TestStep> testSteps = testStepRepository.findAllByTestCaseId(testCaseId);

            row = sheet.createRow(rowCount++);
            cell = row.createCell(0);
            cell.setCellValue("Test Step Action");
            cell = row.createCell(1);
            cell.setCellValue("Test Step Data");
            cell = row.createCell(2);
            cell.setCellValue("Test Step Expected Result");
            cell = row.createCell(3);
            cell.setCellValue("Test Step Actual Result");

            for (TestStep testStep : testSteps) {
                row = sheet.createRow(rowCount++);
                cell = row.createCell(0);
                cell.setCellValue(testStep.getAction());
                cell = row.createCell(1);
                cell.setCellValue(testStep.getData());
                cell = row.createCell(2);
                cell.setCellValue(testStep.getExpectedResult());
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to Excel file", e);
        }
    }
}

