package com.ms.service.service;

import com.ms.service.dto.ServiceDTO;
import com.ms.service.exceptions.ServiceNotFoundException;
import com.ms.service.model.ServiceModel;
import com.ms.service.exceptions.ServiceException;
import com.ms.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class ServiceServices {

    @Autowired
    ServiceRepository serviceRepository;

    public List<ServiceDTO> findAll() throws ServiceException {
        List<ServiceModel> list = serviceRepository.findAll();
        return list.stream().map(ServiceDTO::new).toList();
    }

    @Transactional
    public ServiceDTO create(ServiceDTO serviceDTO) throws ServiceException {
        ServiceModel serviceModel = new ServiceModel(serviceDTO);
        serviceModel.setRegistryUser(serviceDTO.getRegistryUser());
        serviceModel.setCreated(LocalDateTime.now().toString());
        serviceRepository.save(serviceModel);
        return new ServiceDTO(serviceModel);
    }

    public ServiceDTO findById(String id) throws ServiceException {
        return serviceRepository.findById(id)
                .map(ServiceDTO::new)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID: " + id));
    }

    public ServiceDTO findByName(String name) throws ServiceException {
        return serviceRepository.findByName(name)
                .map(ServiceDTO::new)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with NAME: " + name));
    }

    public List<ServiceDTO> findByIdCategory(String idCategory) throws ServiceException {
        List<ServiceDTO> serviceDTOList = serviceRepository.findByIdCategory(idCategory)
                .stream().map(ServiceDTO::new)
                .collect(Collectors.toList());
        return serviceDTOList;
    }

    @Transactional
    public ServiceDTO update(String id, ServiceDTO serviceDTO) throws ServiceException {
        Optional<ServiceModel> optionalServiceModel = serviceRepository.findById(id);
        if (optionalServiceModel.isPresent()){
            ServiceModel serviceModel = optionalServiceModel.get();

            serviceModel.setName(serviceDTO.getName());
            serviceModel.setEmail(serviceDTO.getEmail());
            serviceModel.setIdCategory((serviceDTO.getIdCategory()));
            serviceModel.setStatus(serviceDTO.isStatus());
            serviceModel.setDescription(serviceDTO.getDescription());
            serviceModel.setPrice(serviceDTO.getPrice());
            serviceModel.setRuntime(serviceDTO.getRuntime());
            serviceModel.setTerm(serviceDTO.getTerm());
            serviceModel.setRegistryUser(serviceDTO.getRegistryUser());
            serviceModel.setUpdated(LocalDateTime.now().toString());

            serviceRepository.save(serviceModel);
            return new ServiceDTO(serviceModel);
        } else {
            throw new ServiceNotFoundException("Service not found with ID: " + id);
        }
    }

    public void delete(String id) throws ServiceException {
        if(!serviceRepository.existsById(id)){
            throw new ServiceNotFoundException("Service not found with ID:" + id);
        }
        serviceRepository.deleteById(id);
    }

}
