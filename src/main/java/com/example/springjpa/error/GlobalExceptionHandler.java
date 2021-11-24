package com.example.springjpa.error;

import com.example.springjpa.common.ResponseService;
import com.example.springjpa.common.model.CommonResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ResponseService responseService;

    //잘못된 request가 왔을 경우 발생하는 exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, "method에 잘못된 인자가 들어왔음");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //아이디, 비밀번호 검증에서 실패했을 경우 발생하는 exception -> spring security에서 내부적으로 발생시킴
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<CommonResult> handleBadCredentialsException(BadCredentialsException e) {
        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, "pw가 틀림");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //인가에서 실패했을 경우 밣생하는 exeption -> spring security 내부에서 발생시킴
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<CommonResult> handleAccessDeniedException(AccessDeniedException e) {

        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, "권한이 없음");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //인증에서 실패했을 경 발생하는 exception
    @ExceptionHandler(InsufficientAuthenticationException.class)
    protected ResponseEntity<CommonResult> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {

        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, "인증 실패");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<CommonResult> handleMalformedJwtException(MalformedJwtException e) {
        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, "jwt error");


        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<CommonResult> handleSignatureException(SignatureException e) {

        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, "jwt 에러");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //token 유효기간 만료시 발생
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<CommonResult> handleExpiredJwtException(ExpiredJwtException e) {

        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, "jwt 토큰 유효기간 만료");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<CommonResult> handleNotFoundException(NotFoundException e) {
        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<CommonResult> handleBusinessException(BusinessException e) {
        log.info(e.getMessage());

        CommonResult result = responseService.getFailResult(400, e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
