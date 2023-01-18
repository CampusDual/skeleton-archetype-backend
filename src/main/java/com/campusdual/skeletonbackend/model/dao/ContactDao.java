package com.campusdual.skeletonbackend.model.dao;


import com.campusdual.skeletonbackend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDao extends JpaRepository<Contact, Integer> {
}
