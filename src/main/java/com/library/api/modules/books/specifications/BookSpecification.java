package com.library.api.modules.books.specifications;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.enums.BookState;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> filter(Long authorId, String title, String category, String state) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (authorId != null) {
                Join<Book, Author> authorJoin = root.join("authors");
                Predicate authorPredicate = criteriaBuilder.equal(authorJoin.get("id"), authorId);
                predicate = criteriaBuilder.and(predicate, authorPredicate);
            }

            if (title != null && !title.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (category != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category"), category));
            }

            if (state != null && !state.isEmpty()) {
                try {
                    BookState bookState = BookState.valueOf(state.toUpperCase());
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("state"), bookState));
                } catch (Exception e) {
                    throw new BadRequestException("Estado do livro inválido: " + state);
                }
            }

            return predicate;
        };
    }

}
