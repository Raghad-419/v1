package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.AdminDTO;
import com.example.capston3.Model.Admin;
import com.example.capston3.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<AdminDTO> getAllAdmins(){
        List<Admin> admins = adminRepository.findAll();
        List<AdminDTO> adminDTOS = new ArrayList<>();

        for(Admin admin:admins){
            AdminDTO adminDTO = new AdminDTO(admin.getName(),admin.getEmail());
            adminDTOS.add(adminDTO);
        }
        return adminDTOS;
    }


    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id,Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin==null){
            throw new ApiException("Admin not found");
        }
        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setName(admin.getName());
        adminRepository.save(oldAdmin);
    }


    public void deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if(admin==null){
            throw new ApiException("Admin not found");
        }
        adminRepository.delete(admin);
    }

}
