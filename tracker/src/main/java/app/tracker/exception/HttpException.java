package app.tracker.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HttpException extends RuntimeException{
    private final HttpStatus status;

    public HttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    @Override
    public String toString() {
        return "HttpException{" +
                "status=" + status +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}