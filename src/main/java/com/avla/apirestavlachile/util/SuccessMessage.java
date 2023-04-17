package com.avla.apirestavlachile.util;

import io.swagger.v3.oas.annotations.media.Schema;

public class SuccessMessage {
    
    @Schema(description = "Mensaje devuelto en la respuesta de la API cuando un proceso se ha completado exitosamente")
    private String mensaje;

    public SuccessMessage(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
