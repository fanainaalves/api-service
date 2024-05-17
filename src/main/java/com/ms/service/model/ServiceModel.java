package com.ms.service.model;

import com.ms.service.dto.ServiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "services")
public class ServiceModel implements Serializable {

    @Id
    private String id;
    private String name;
    private String email;
    private String idCategory;
    private boolean status;
    private String description;
    private BigDecimal price;
    private int runtime;
    private int term;
    private String created;
    private String updated;
    private String image;
    private String registryUser;

    public ServiceModel (ServiceDTO serviceDTO){
        BeanUtils.copyProperties(serviceDTO, this);
    }
    public ServiceModel(){
        super();
    }
}
