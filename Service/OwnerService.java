package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.CourseDTO;
import com.example.capston3.DTO.MotorcycleDTO;
import com.example.capston3.DTO.OwnerDTO;
import com.example.capston3.Model.Owner;
import com.example.capston3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final MotorcycleService motorcycleService;
    private final CourseService courseService;


    public List<OwnerDTO> getAllOwners(){

        List<Owner> owners = ownerRepository.findAll();

        List<OwnerDTO> ownerDTOList = new ArrayList<>();
        List<MotorcycleDTO> motorcycleDTOS = motorcycleService.getAllMotorcycles();
        List<CourseDTO> courseDTOS = courseService.getAllCourses();

        for(Owner owner : owners){
            OwnerDTO ownerDTO = new OwnerDTO(owner.getName(), owner.getEmail(), owner.getPhoneNumber(), owner.getAddress(),motorcycleDTOS, courseDTOS );
            ownerDTOList.add(ownerDTO);
        }
        return ownerDTOList;
    }


    public void createOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public void updateOwner(Integer id, Owner owner){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        o.setName(owner.getName());
        o.setEmail(owner.getEmail());
        o.setPhoneNumber(owner.getPhoneNumber());
        o.setPassword(owner.getPassword());

        ownerRepository.save(o);
    }

    public void deleteOwner(Integer id){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        ownerRepository.delete(o);

    }













}
