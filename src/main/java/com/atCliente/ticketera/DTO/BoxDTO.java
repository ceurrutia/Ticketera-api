package com.atCliente.ticketera.DTO;

import com.atCliente.ticketera.enums.BoxEstado;
import com.atCliente.ticketera.enums.BoxType;

public class BoxDTO {
        private Long id;
        private BoxType type;
        private BoxEstado estado;

        // Constructor vacío
        public BoxDTO() {
        }

        // Constructor con parámetros
        public BoxDTO(Long id, BoxType type, BoxEstado estado) {
            this.id = id;
            this.type = type;
            this.estado = estado;
        }

        // Getters y setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public BoxType getType() {
            return type;
        }

        public void setType(BoxType type) {
            this.type = type;
        }

        public BoxEstado getEstado() {
            return estado;
        }

        public void setEstado(BoxEstado estado) {
            this.estado = estado;
        }
}
