package com.junioroffer.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



class InMemoryOfferRepositoryTestImpl implements OfferRepository{
    Map<String, Offer> inMemoryDataBase = new ConcurrentHashMap<>();


    @Override
    public List<Offer> findAllOffer() {
        return  inMemoryDataBase.values().stream().toList();
    }

    @Override
    public Offer save(Offer entity) {
        if(inMemoryDataBase.values().stream().anyMatch(offer -> offer.offerUrl().equals(entity.offerUrl()))){
            throw new OfferDuplicateException(entity.offerUrl());
        }
        UUID id = UUID.randomUUID();
        Offer offer = new Offer(
                id.toString(),
                entity.companyName(),
                entity.position(),
                entity.salary(),
                entity.offerUrl()
                );
        inMemoryDataBase.put(id.toString(), offer);

        return offer;
    }

    @Override
    public Optional<Offer> findOfferById(String id) {
        return Optional.ofNullable(inMemoryDataBase.get(id));
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
        }

    @Override
    public boolean existsByOfferUrl(final String offerUrl) {
        long count = inMemoryDataBase.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(offerUrl))
                .count();
        return count == 1;
    }

}

