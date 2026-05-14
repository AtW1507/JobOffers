package com.junioroffer.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


class InMemoryOfferRepositoryTestImpl implements OfferRepository{
    Map<Long, Offer> inMemoryDataBase = new ConcurrentHashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public List<Offer> findAllOffer() {
        return  inMemoryDataBase.values().stream().toList();
    }

    @Override
    public Offer save(Offer offer) {
        long index = this.index.getAndIncrement();
        Offer offerWithId = Offer.builder()
                .id(index)
                .company(offer.company())
                .title(offer.title())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
        inMemoryDataBase.put(index, offerWithId);

        return offerWithId;
    }

    @Override
    public Optional<Offer> findOfferById(Long id) {
        return Optional.ofNullable(inMemoryDataBase.get(id));
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
        }

    }

