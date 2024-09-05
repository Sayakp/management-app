package org.daniel.managementspringapp.service.interfaces;

import java.util.List;

public interface CrudService<DTO, IdType> {
    List<DTO> getAll();

    DTO get(IdType id);

    DTO add(DTO dto);

    void delete(IdType id);

    DTO update(DTO dto);
}
