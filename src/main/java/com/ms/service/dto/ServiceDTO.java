package com.ms.service.dto;

import com.ms.service.model.ServiceModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO implements Serializable {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Insira um e-mail válido (por exemplo, teste@teste.com)")
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

    public ServiceDTO (ServiceModel serviceModel){
        BeanUtils.copyProperties(serviceModel, this);
    }

}
