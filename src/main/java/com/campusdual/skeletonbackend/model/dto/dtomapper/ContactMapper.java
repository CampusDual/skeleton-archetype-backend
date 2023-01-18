package com.campusdual.skeletonbackend.model.dto.dtomapper;

import com.campusdual.skeletonbackend.model.Contact;
import com.campusdual.skeletonbackend.model.dto.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactDTO toDTO(Contact contact);
    List<ContactDTO> toDTOList(List<Contact> contacts);
    Contact toEntity(ContactDTO contactdto);
}
