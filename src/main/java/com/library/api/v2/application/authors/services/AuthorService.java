package com.library.api.v2.application.authors.services;

import com.library.api.v2.application.authors.mappers.AuthorDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorService {
    AuthorDTOMapper dtoMapper = AuthorDTOMapper.INSTANCE;
}
