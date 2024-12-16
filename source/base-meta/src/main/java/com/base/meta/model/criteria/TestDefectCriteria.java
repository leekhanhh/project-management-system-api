package com.base.meta.model.criteria;

import com.base.meta.model.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestDefectCriteria implements Serializable {
    private String name;
    private String priority;
    private Long statusId;
    private String assignedDeveloperName;
    private Long assignedDeveloperId;
    private Long testStepExecutionId;
    private Long testCaseProjectId;
    private Long testCaseProgramId;
    private Long testExecutionId;
    private Long testCaseId;
    private Long testStepId;
    private int flag;

    public Specification<TestDefect> getSpecification() {
        return new Specification<TestDefect>() {
            public static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TestDefect> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (!StringUtils.isEmpty(getName())) {
                    predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getPriority())) {
                    predicateList.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("priority")), "%" + getPriority().toLowerCase() + "%"));
                }
                if (getStatusId() != null) {
                    Join<Category, TestDefect> join = root.join("status", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getStatusId()));
                }
                if (!StringUtils.isEmpty(getAssignedDeveloperName())) {
                    Join<Account, TestDefect> join = root.join("assignedDeveloper", JoinType.INNER);
                    predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(join.get("fullName")), "%" + getAssignedDeveloperName().toLowerCase() + "%"));
                }
                if (getTestStepExecutionId() != null) {
                    Join<TestStepExecution, TestDefect> join = root.join("testStepExecution", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getTestStepExecutionId()));
                }
                if (getTestCaseProjectId() != null) {
                    Join<TestStepExecution, TestDefect> join = root.join("testStepExecution", JoinType.INNER);
                    Join<TestCaseExecution, TestStepExecution> joinTestStep = join.join("testCaseExecution", JoinType.INNER);
                    Join<TestCase, TestCaseExecution> joinTestCase = joinTestStep.join("testCase", JoinType.INNER);
                    Join<Program, TestCase> joinProgram = joinTestCase.join("program", JoinType.INNER);
                    Join<Project, Program> joinProject = joinProgram.join("project", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(joinProject.get("id"), getTestCaseProjectId()));
                }
                if (getTestCaseProgramId() != null) {
                    Join<TestStepExecution, TestDefect> join = root.join("testStepExecution", JoinType.INNER);
                    Join<TestCaseExecution, TestStepExecution> joinTestStep = join.join("testCaseExecution", JoinType.INNER);
                    Join<TestCase, TestCaseExecution> joinTestCase = joinTestStep.join("testCase", JoinType.INNER);
                    Join<Program, TestCase> joinProgram = joinTestCase.join("program", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(joinProgram.get("id"), getTestCaseProgramId()));
                }
                if (getTestExecutionId() != null) {
                    Join<TestStepExecution, TestDefect> join = root.join("testStepExecution", JoinType.INNER);
                    Join<TestCaseExecution, TestStepExecution> joinTestStep = join.join("testCaseExecution", JoinType.INNER);
                    Join<TestExecution, TestCaseExecution> joinTestExecution = joinTestStep.join("testExecutionTurn", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(joinTestExecution.get("id"), getTestExecutionId()));
                }
                if (getTestCaseId() != null) {
                    Join<TestStepExecution, TestDefect> join = root.join("testStepExecution", JoinType.INNER);
                    Join<TestCaseExecution, TestStepExecution> joinTestStep = join.join("testCaseExecution", JoinType.INNER);
                    Join<TestCase, TestCaseExecution> joinTestCase = joinTestStep.join("testCase", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(joinTestCase.get("id"), getTestCaseId()));
                }
                if (getTestStepId() != null) {
                    Join<TestStepExecution, TestDefect> join = root.join("testStepExecution", JoinType.INNER);
                    Join<TestStep, TestStepExecution> joinTestStep = join.join("testStep", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(joinTestStep.get("id"), getTestStepId()));
                }
                if (getAssignedDeveloperId() != null) {
                    Join<Account, TestDefect> join = root.join("assignedDeveloper", JoinType.INNER);
                    predicateList.add(criteriaBuilder.equal(join.get("id"), getAssignedDeveloperId()));
                }
                if (getFlag() != 0) {
                    predicateList.add(criteriaBuilder.equal(root.get("flag"), getFlag()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
