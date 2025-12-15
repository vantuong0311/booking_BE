package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentReponsitory extends MongoRepository<Payment, String> {
}
