package com.library.api.modules.authors.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateAuthorDTO {
	String name;
	String age;
}
