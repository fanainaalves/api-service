package com.ms.service.dto;

import com.ms.service.model.Service;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ServiceDTO implements Serializable {

    private String id;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]+\s[A-Z][a-z]+$",
            message = "O nome completo deve conter: " +
                    "Nome e sobrenome com iniciais em Letra Maiúscula!")
    private String name;


    @NotBlank
    @Email
    private String email;


//    @NotBlank
//    @Pattern(regexp = "\\d{11}", message = "O telefone deve ser: " +
//            "No formato: XXXXXXXXXXX (Apenas números)" +
//            "Devem ser inseridos 11 números (DDD, 9 na frente e o número em si")
//    private String cel;
//
//    @NotBlank
//    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números!")
//    private String cpf;
//
// CONSTRUTOR DE SERVICEDTO = id, nome, email

    public ServiceDTO (Service service){
        BeanUtils.copyProperties(service, this);
    }
    public ServiceDTO(){
        super();
    }

}
