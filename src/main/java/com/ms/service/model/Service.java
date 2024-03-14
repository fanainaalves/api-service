package com.ms.service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "services")
public class Service {

    @Id
    private String serviceID; // id
    private String serviceName; // nome
    private String serviceEmail; // email
    private String serviceStatus; // status
    private boolean serviceDescription; // descrição
    private float servicePrice; // preço
    private int serviceRuntime; // tempo de execução
    private int serviceTerm; // prazo

}
