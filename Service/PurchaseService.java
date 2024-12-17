package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.PurchaseMotorcycleOutDTO;
import com.example.capston3.DTO.PurchaseOutDTO;
import com.example.capston3.DTO.PurchaseUserOutDTO;
import com.example.capston3.InDTO.PurchaseDTO_In;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Model.Purchase;
import com.example.capston3.Model.User;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.PurchaseRepository;
import com.example.capston3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;

//    public List<PurchaseOutDTO> getAllPurchases() {
//        List<Purchase> purchases = purchaseRepository.findAll();
//        List<PurchaseOutDTO> purchaseOutDTOs = new ArrayList<>();
//        for (Purchase purchase : purchases) {
//            purchaseOutDTOs.add(new PurchaseOutDTO(purchase.getPurchaseDate(),new PurchaseUserOutDTO(purchase.getUser().getName(),purchase.getUser().getEmail(),purchase.getUser().getPhoneNumber(),purchase.getUser().getAge(),purchase.getUser().getAddress()),new PurchaseMotorcycleOutDTO(purchase.getMotorcycle().getBrand(),purchase.getMotorcycle().getModel(),purchase.getMotorcycle().getYear(),purchase.getMotorcycle().getPrice(),purchase.getMotorcycle().getColor(),purchase.getMotorcycle().getIsAvailable())));
//        }
//        return purchaseOutDTOs;
//    }

//    public void addPurchase(PurchaseDTO_In purchaseInDTO) {
//        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchaseInDTO.getMotorcycleId());
//        if (motorcycle == null) {
//            throw new ApiException("Motorcycle not found");
//        }
//        User user = userRepository.findUserById(purchaseInDTO.getUserId());
//        if (user == null) {
//            throw new ApiException("User not found");
//        }
//        Purchase purchase = new Purchase(user,motorcycle.getId(),);
//        purchaseRepository.save(purchase);
//    }


    public void updatePurchase(Integer purchase_id,PurchaseDTO_In purchaseInDTO) {
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchaseInDTO.getMotorcycleId());
        if (motorcycle == null) {
            throw new ApiException("Motorcycle not found");
        }
        Purchase purchase = purchaseRepository.findPurchaseById(purchase_id);
        if (purchase == null) {
            throw new ApiException("Purchase not found");
        }
        User user = userRepository.findUserById(purchaseInDTO.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }
        purchase.setMotorcycleId(motorcycle.getId());
        purchase.setUser(userRepository.findUserById(user.getId()));
        purchaseRepository.save(purchase);

    }

}