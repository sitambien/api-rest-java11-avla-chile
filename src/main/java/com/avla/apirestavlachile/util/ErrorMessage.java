package com.avla.apirestavlachile.util;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorMessage {

    @Schema(description = "Mensaje devuelto en la respuesta de la API cuando un proceso no se ha completado")
    private String mensaje;

    public ErrorMessage(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
