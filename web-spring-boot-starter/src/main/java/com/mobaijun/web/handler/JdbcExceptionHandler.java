/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.web.handler;

import com.mobaijun.common.enums.http.HttpStatus;
import com.mobaijun.common.result.ErrorDataInfo;
import com.mobaijun.common.result.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLTransactionRollbackException;

/**
 * Description: JDBC 和数据库相关异常处理器，仅在 Spring JDBC 存在时生效
 * Author: [mobaijun]
 * Date: [2024/8/13 18:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(name = "org.springframework.jdbc.BadSqlGrammarException")
public class JdbcExceptionHandler extends BaseExceptionHandler {

    /**
     * 处理 SQL 异常，捕获数据库操作执行中的错误。
     * <p>
     * 当 SQL 执行异常（如查询、插入、更新操作等）发生时，本方法捕获 {@link SQLException} 异常，记录错误信息、
     * 请求路径和请求方法，并返回标准化的错误响应。通常此类异常会由于数据库连接问题、SQL 语法错误等原因触发。
     * </p>
     *
     * @param e       捕获到的 {@link SQLException} 异常对象，包含 SQL 执行错误的具体信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含 SQL 错误信息及相关细节
     */
    @ExceptionHandler(SQLException.class)
    public R<ErrorDataInfo> handleSQLException(SQLException e, HttpServletRequest request) {
        log.error("SQL 执行异常: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "SQL 执行异常！", null);
    }

    /**
     * 处理数据完整性异常，捕获违反数据库唯一约束或完整性约束的错误。
     * <p>
     * 当操作违反数据库约束（如插入重复数据）时，本方法捕获 {@link DataIntegrityViolationException} 异常，
     * 记录错误信息、请求路径和请求方法，并返回相应的错误响应。常见的情况如插入重复数据或违反其他约束。
     * </p>
     *
     * @param e       捕获到的 {@link DataIntegrityViolationException} 异常对象，包含违反数据完整性约束的具体信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含详细的错误信息
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<ErrorDataInfo> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        log.error("数据库操作失败，违反唯一性或完整性约束: {}, 请求路径: {}, 请求方法: {}", 
                e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.BAD_REQUEST, "操作失败，数据违反唯一性或完整性约束！", null);
    }

    /**
     * 处理空结果异常，捕获查询结果为空的情况。
     * <p>
     * 当执行查询操作时，如果没有找到符合条件的结果，本方法会捕获 {@link EmptyResultDataAccessException} 异常，
     * 记录相关错误信息、请求路径和请求方法，并返回资源未找到的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link EmptyResultDataAccessException} 异常对象，包含查询无结果的错误信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示资源未找到
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public R<ErrorDataInfo> handleEmptyResultDataAccessException(EmptyResultDataAccessException e, HttpServletRequest request) {
        log.error("查询的资源不存在: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.NOT_FOUND, "查询的资源不存在！", null);
    }

    /**
     * 处理数据访问资源失败异常，捕获数据库连接失败或资源不可用的错误。
     * <p>
     * 当数据库访问出现资源不可用或连接问题时，本方法捕获 {@link DataAccessResourceFailureException} 异常，
     * 记录详细错误信息，并返回数据库服务不可用的错误响应。常见情况如数据库服务器停机或网络连接问题。
     * </p>
     *
     * @param e       捕获到的 {@link DataAccessResourceFailureException} 异常对象，包含数据库访问失败的具体信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含数据库连接失败的提示信息
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public R<ErrorDataInfo> handleDataAccessResourceFailureException(DataAccessResourceFailureException e, HttpServletRequest request) {
        log.error("数据库访问资源失败: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "数据库访问资源失败！", null);
    }

    /**
     * 处理乐观锁异常，捕获数据冲突导致的乐观锁失败。
     * <p>
     * 当在并发环境下，多个请求试图修改相同数据时，可能会出现乐观锁失败的情况，
     * 本方法捕获 {@link OptimisticLockingFailureException} 异常，记录错误信息，并返回乐观锁失败的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link OptimisticLockingFailureException} 异常对象，包含乐观锁失败的详细信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示乐观锁失败
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public R<ErrorDataInfo> handleOptimisticLockingFailureException(OptimisticLockingFailureException e, HttpServletRequest request) {
        log.error("乐观锁失败: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "乐观锁失败！", null);
    }

    /**
     * 处理查询结果大小不匹配异常，捕获查询结果数量不符合预期的情况。
     * <p>
     * 当数据库查询结果的数量与预期不符时，触发该异常（例如，期望单条记录返回，却返回了多条或零条记录）。
     * 本方法会捕获 {@link IncorrectResultSizeDataAccessException} 异常，记录错误信息，并返回标准化的错误响应。
     * </p>
     *
     * @param e 捕获到的 {@link IncorrectResultSizeDataAccessException} 异常对象，包含查询结果数量不匹配的详细信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示查询结果数量不匹配
     */
    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public R<ErrorDataInfo> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e, HttpServletRequest request) {
        log.error("查询结果数量异常: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "查询结果数量异常！", null);
    }

    /**
     * 处理数据库约束违反的异常
     * <p>
     * 当数据库操作违反唯一约束（例如插入重复的唯一键值）时会抛出该异常。
     * 本方法会捕获 {@link SQLIntegrityConstraintViolationException} 异常，记录错误信息，
     * 并返回状态码 409（ALREADY_EXISTS）和提示信息，指示操作违反了数据库唯一约束。
     * </p>
     *
     * @param e       捕获到的 {@link SQLIntegrityConstraintViolationException} 异常对象，包含违反唯一约束的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含数据库约束错误信息
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<ErrorDataInfo> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
        log.error("数据库约束违反, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return handleException(e, request, HttpStatus.CONFLICT, "操作违反数据库唯一约束，请确认数据是否重复！", null);
    }

    /**
     * 处理数据库连接失败的异常
     * <p>
     * 当数据库连接无法建立或失效时会抛出该异常，例如数据库服务器不可达。
     * 本方法会捕获 {@link SQLNonTransientConnectionException} 异常，记录错误信息，
     * 并返回状态码 503（SERVICE_UNAVAILABLE）和提示信息，指示数据库连接失败。
     * </p>
     *
     * @param e       捕获到的 {@link SQLNonTransientConnectionException} 异常对象，包含数据库连接失败的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含数据库连接错误信息
     */
    @ExceptionHandler(SQLNonTransientConnectionException.class)
    public R<ErrorDataInfo> handleSQLNonTransientConnectionException(SQLNonTransientConnectionException e, HttpServletRequest request) {
        log.error("数据库连接失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "数据库连接失败，请稍后重试！", null);
    }

    /**
     * 处理数据库事务回滚的异常
     * <p>
     * 当数据库事务执行失败，导致回滚时会抛出该异常。
     * 本方法会捕获 {@link SQLTransactionRollbackException} 异常，记录错误信息，
     * 并返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息，指示事务回滚并请求重试。
     * </p>
     *
     * @param e       捕获到的 {@link SQLTransactionRollbackException} 异常对象，包含事务回滚的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含事务回滚错误信息
     */
    @ExceptionHandler(SQLTransactionRollbackException.class)
    public R<ErrorDataInfo> handleSQLTransactionRollbackException(SQLTransactionRollbackException e, HttpServletRequest request) {
        log.error("事务回滚, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "操作失败，事务已回滚，请重试！", null);
    }

    /**
     * 处理批量更新失败的异常
     * <p>
     * 当批量更新操作失败时（例如批量插入或更新数据时出现错误）会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link BatchUpdateException} 异常对象，包含批量更新失败的详细信息
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BatchUpdateException.class)
    public R<ErrorDataInfo> handleBatchUpdateException(BatchUpdateException e, HttpServletRequest request) {
        log.error("批量更新失败, 请求地址: {}, 错误信息: {} ", request.getRequestURI(), e.getMessage());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, 
                "批量更新操作失败，可能由于数据格式不符合要求或数据库连接问题！", null);
    }

    /**
     * 处理 SQL 语法错误的异常
     * <p>
     * 当执行 SQL 语法错误时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link BadSqlGrammarException} 异常对象，包含 SQL 语法错误的详细信息
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public R<ErrorDataInfo> handleBadSqlGrammarException(BadSqlGrammarException e, HttpServletRequest request) {
        log.error("SQL 语法错误, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, 
                "SQL 语法错误，可能由于 SQL 语句编写不规范，请检查输入的 SQL 语句！", null);
    }

    /**
     * 处理通用数据访问异常
     * <p>
     * 处理其他 MyBatis 或数据库访问操作失败时抛出的异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link DataAccessException} 异常对象，包含数据库访问失败的详细信息
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(DataAccessException.class)
    public R<ErrorDataInfo> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        log.error("数据库操作失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, 
                "数据库操作失败，可能由于数据库连接问题或查询语句问题，请稍后重试！", null);
    }
}