package com.furkanyesilyurt.couriertracking.common.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.hibernate.Session;

@Aspect
@Component
@RequiredArgsConstructor
public class FilterAspect {

    private final EntityManager entityManager;

    @Before("@annotation(com.furkanyesilyurt.couriertracking.common.annotation.EnableDeleteFilter)")
    public void enableActiveFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deleteFilter").setParameter("isDeleted", false);;
    }
}
