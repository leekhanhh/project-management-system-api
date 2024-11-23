package com.base.meta.service;

import com.base.meta.dto.ErrorCode;
import com.base.meta.exception.BadRequestException;
import com.base.meta.exception.NotFoundException;
import com.base.meta.mapper.TestCaseUploadMapper;
import com.base.meta.form.program.ProgramUploadForm;
import com.base.meta.model.TestCaseUpload;
import com.base.meta.repository.AccountRepository;
import com.base.meta.repository.ProgramRepository;
import com.base.meta.repository.TestCaseRepository;
import com.base.meta.repository.TestStepRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;

@Service
@Slf4j
public class ExcelService {

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String[] TCUPHEADERS = {"Program ID", "Member ID", "Test Case Name", "Test Case Pre-condition", "Test Case Menu Path", "Test Step Action", "Test Step Data", "Test Step Expected Result"};
    private static final String[] PGUPHEADERS = {"Project ID", "Program Name", "Category", "Type", "Requirement ID", "Start Date", "End Date", "Description"};
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

    private boolean validatePGUPHeaders(Row row) {
        return row.getCell(0).getStringCellValue().equals(PGUPHEADERS[0])
                && row.getCell(1).getStringCellValue().equals(PGUPHEADERS[1])
                && row.getCell(2).getStringCellValue().equals(PGUPHEADERS[2])
                && row.getCell(3).getStringCellValue().equals(PGUPHEADERS[3])
                && row.getCell(4).getStringCellValue().equals(PGUPHEADERS[4])
                && row.getCell(5).getStringCellValue().equals(PGUPHEADERS[5])
                && row.getCell(6).getStringCellValue().equals(PGUPHEADERS[6])
                && row.getCell(7).getStringCellValue().equals(PGUPHEADERS[7]);
    }

    private Map<Integer, Consumer<Cell>> createTCUPCellMapping(TestCaseUpload testCaseUpload){
        Map<Integer, Consumer<Cell>> cellMapping = new HashMap<>();
        cellMapping.put(0, cell -> testCaseUpload.setProgram(programRepository.findById(Long.valueOf(cell.getStringCellValue())).orElseThrow(()
                -> new NotFoundException("Program is not existed!", ErrorCode.PROGRAM_ERROR_NOT_EXIST))));
        cellMapping.put(1, cell -> testCaseUpload.setCreatedBy(accountRepository.findById(Long.valueOf(cell.getStringCellValue())).orElseThrow(()
                -> new NotFoundException("Account is not existed!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND)).getUsername()));
        cellMapping.put(2, cell -> testCaseUpload.setTestCaseName(cell.getStringCellValue()));
        cellMapping.put(3, cell -> testCaseUpload.setTestCasePrecondition(cell.getStringCellValue()));
        cellMapping.put(4, cell -> testCaseUpload.setTestCaseMenuPath(cell.getStringCellValue()));
        cellMapping.put(5, cell -> testCaseUpload.setTestStepAction(cell.getStringCellValue()));
        cellMapping.put(6, cell -> testCaseUpload.setTestStepData(cell.getStringCellValue()));
        cellMapping.put(7, cell -> testCaseUpload.setTestStepExpectedResult(cell.getStringCellValue()));
        return cellMapping;
    }

    private TestCaseUpload createTestCaseUpload(Row row) throws Exception {
        TestCaseUpload testCaseUpload = new TestCaseUpload();
        try{
            Map<Integer, Consumer<Cell>> cellMapping = createTCUPCellMapping(testCaseUpload);

            for (Map.Entry<Integer, Consumer<Cell>> entry : cellMapping.entrySet()) {
                Cell cell = row.getCell(entry.getKey());
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    entry.getValue().accept(cell);
                }
            }
        }catch (Exception e){
            throw new Exception("Failed to parse Excel file.\nError: " + e.getMessage() + "\nAt row num: " + row.getRowNum());
        }
        return testCaseUpload;
    }

    private ProgramUploadForm createProgramUploadForm(Row row) throws Exception{
        ProgramUploadForm programUploadForm = new ProgramUploadForm();
        try {
            programUploadForm.setProjectId(row.getCell(0).getStringCellValue());
            programUploadForm.setProgramName(row.getCell(1).getStringCellValue());
            programUploadForm.setProgramCategory(row.getCell(2).getStringCellValue());
            programUploadForm.setProgramType(row.getCell(3).getStringCellValue());
            programUploadForm.setRequirementId(row.getCell(4).getStringCellValue());
            programUploadForm.setStartDate(row.getCell(5).getDateCellValue());
            programUploadForm.setEndDate(row.getCell(6).getDateCellValue());
            programUploadForm.setDescription(row.getCell(7).getStringCellValue());
        }catch (Exception e){
            throw new Exception("Failed to parse Excel file.\nError: " + e.getMessage() + "\nAt row num: " + row.getRowNum());
        }
        return programUploadForm;
    }

    public List<ProgramUploadForm> mapExcelToProgramData(InputStream inputStream) throws Exception{
        List<ProgramUploadForm> programUploadForms = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        if (!validatePGUPHeaders(row)) {
                            throw new BadRequestException("Invalid Excel column format");
                        }
                        continue;
                    }

                    ProgramUploadForm programUploadForm = createProgramUploadForm(row);
                    programUploadForms.add(programUploadForm);
                }
                log.info("Program Upload Form: " + programUploadForms);
            }
        } catch (IOException ex) {
            throw new BadRequestException("Failed to parse Excel file.\nError: " + ex.getMessage());
        }
        return programUploadForms;
    }

    public List<TestCaseUpload> mapExcelToData(InputStream inputStream) throws Exception {
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
}

