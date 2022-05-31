package nir.bh.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "failed to convert from json")
public class MappingException extends RuntimeException{
}
