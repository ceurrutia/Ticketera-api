package com.atCliente.ticketera.respository;

import com.atCliente.ticketera.entitys.Box;
import com.atCliente.ticketera.enums.BoxEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    Optional<Box> findFirstByEstado(BoxEstado estado);
}
