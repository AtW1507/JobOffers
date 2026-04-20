package com.junioroffer.domain.offer;

import java.util.List;
import java.util.Map;
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
    public Offer addOffer(Offer offer) {
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
    public Offer findOfferById(final Long id) {
        return inMemoryDataBase.get(id);
    }
}
