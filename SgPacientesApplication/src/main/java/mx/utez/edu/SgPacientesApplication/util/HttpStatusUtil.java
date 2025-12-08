package mx.utez.edu.SgPacientesApplication.util;

import org.springframework.http.HttpStatus;

/**
 * {@code HttpStatusUtil} es una clase que ayuda a mapear los
 * tipos de errores HTTP en base a su código, utilizando
 * {@code getStatus(int code)}.
 */
public class HttpStatusUtil {
    /**
     *
     * @param code Recibe el número del error HTTP
     * @return El tipo de error {@code HttpStatus}
     */
    public static HttpStatus getStatus(int code) {
        return switch (code) {
            case 200 -> HttpStatus.OK;
            case 201 -> HttpStatus.CREATED;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 404 -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
