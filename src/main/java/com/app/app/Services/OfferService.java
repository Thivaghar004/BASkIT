package com.app.app.Services;

import com.app.app.Models.Offer;
import com.app.app.Repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Offer getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + id));
    }

    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer updateOffer(Long id, Offer offer) {
        Offer existingOffer = getOfferById(id);
        existingOffer.setMinimumOrderAmount(offer.getMinimumOrderAmount());
        existingOffer.setValidFrom(offer.getValidFrom());
        existingOffer.setValidTill(offer.getValidTill());
        existingOffer.setDiscountPercentage(offer.getDiscountPercentage());
        return offerRepository.save(existingOffer);
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}
