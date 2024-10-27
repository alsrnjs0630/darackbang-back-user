package com.lab.darackbang.criteria;

import com.lab.darackbang.dto.event.EventSearchDTO;
import com.lab.darackbang.entity.Event;
import org.springframework.data.jpa.domain.Specification;

public class EventCriteria {
    public static Specification<Event> byCriteria(EventSearchDTO dto) {
        return (root, query, criteriaBuilder) -> {
            Specification<Event> spec = Specification.where(null);

            if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> cb.like(root1.get("title"), "%" + dto.getTitle() + "%"));
            }

            if (dto.getEventState() != null && !dto.getEventState().isEmpty()) {
                spec = spec.and((root1, query1, cb) -> cb.equal(root1.get("eventState"), dto.getEventState()));
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
