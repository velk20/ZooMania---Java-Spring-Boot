package com.zoomania.zoomania.service;

import com.zoomania.zoomania.model.dto.CreateOrUpdateOfferDTO;
import com.zoomania.zoomania.model.dto.OfferDetailsDTO;
import com.zoomania.zoomania.model.entity.CategoryEntity;
import com.zoomania.zoomania.model.entity.OfferEntity;
import com.zoomania.zoomania.model.entity.UserEntity;
import com.zoomania.zoomania.repository.CategoryRepository;
import com.zoomania.zoomania.repository.OfferRepository;
import com.zoomania.zoomania.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }


    public void addOffer(CreateOrUpdateOfferDTO addOfferDTO, UserDetails userDetails) {
        OfferEntity newOffer = mapper.map(addOfferDTO, OfferEntity.class);

        UserEntity seller = userRepository.findByUsername(userDetails.getUsername()).
                orElseThrow();

        CategoryEntity categoryEntity = categoryRepository.findByName(addOfferDTO.getCategory()).orElseThrow();

        newOffer
                .setCategory(categoryEntity)
                .setSeller(seller)
                .setCreatedOn(LocalDate.now());

        offerRepository.save(newOffer);
    }

    public List<OfferDetailsDTO> getRecentOffers(int limit) {
        List<OfferEntity> byOrderByCreatedOnDesc = this.offerRepository.findByOrderByCreatedOnDesc();
        if (byOrderByCreatedOnDesc == null) {
            return null;
        }

        return byOrderByCreatedOnDesc.subList(0, limit)
                .stream()
                .map(o -> mapper.map(o, OfferDetailsDTO.class))
                .collect(Collectors.toList());
    }

    public Page<OfferDetailsDTO> getAllOffers(Pageable pageable) {
        return this.offerRepository
                .findAll(pageable)
                .map(o -> mapper.map(o, OfferDetailsDTO.class));
    }

    public Page<OfferDetailsDTO> getAllUserOffers(String username,Pageable pageable) {

        return this.offerRepository.findAllBySellerUsername(username,pageable)
                .map(o -> mapper.map(o, OfferDetailsDTO.class));
    }
}
