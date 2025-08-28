package com.atCliente.ticketera.respository;

import com.atCliente.ticketera.entitys.Cliente;
import com.atCliente.ticketera.entitys.Consulta;
import com.atCliente.ticketera.enums.Tipo_consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Optional<Consulta> findByTipoConsulta(Tipo_consulta tipoConsulta);
}
