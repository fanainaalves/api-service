package com.ms.service.model;


import com.ms.service.dto.ServiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "services")
public class Service implements Serializable {

    @Id
    private String serviceID; // id
    private String serviceName; // nome
    private String serviceEmail; // email
    private String serviceStatus; // status
    private boolean serviceDescription; // descrição
    private float servicePrice; // preço
    private int serviceRuntime; // tempo de execução
    private int serviceTerm; // prazo

    public Service (ServiceDTO serviceDTO){
        BeanUtils.copyProperties(serviceDTO, this);
    }
    public Service(){
        super();
    }
}
