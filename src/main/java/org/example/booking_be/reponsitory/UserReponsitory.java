package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReponsitory extends MongoRepository<User,String> {
}
