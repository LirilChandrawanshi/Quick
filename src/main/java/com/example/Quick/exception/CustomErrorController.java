package com.example.Quick.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // Default to 500

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            httpStatus = HttpStatus.valueOf(statusCode);
        }

        // Only return detailed error response for 500 errors
        if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                "Internal Server Error",
                message != null ? message.toString() : "An unexpected error occurred",
                path != null ? path.toString() : request.getRequestURI()
            );

            return new ResponseEntity<>(errorResponse, httpStatus);
        }

        // For other error types, use the default error response
        return new ResponseEntity<>(new ErrorResponse(
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            "Request could not be processed",
            request.getRequestURI()
        ), httpStatus);
    }
}
