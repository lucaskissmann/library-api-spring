package com.library.api.books.specification;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.enums.BookState;
import com.library.api.modules.books.specifications.BookSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSpecificationTest {

    @Test
    @DisplayName("Deve retornar um Predicate com todos os filtros")
    void shouldReturnPredicateWithAllFilters() {
        Long authorId = 1L;
        String title = "Book Title";
        String category = "Fiction";
        String state = "AVAILABLE";

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        Root<Book> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);

        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.conjunction()).thenReturn(predicate);

        Join<Book, Author> authorJoin = mock(Join.class);
        when(root.join("authors")).thenReturn((Join) authorJoin);

        Predicate authorPredicate = mock(Predicate.class);
        Predicate titlePredicate = mock(Predicate.class);
        Predicate categoryPredicate = mock(Predicate.class);
        Predicate statePredicate = mock(Predicate.class);

        when(criteriaBuilder.equal(authorJoin.get("id"), authorId)).thenReturn(authorPredicate);
        when(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%book title%")).thenReturn(titlePredicate);
        when(criteriaBuilder.equal(root.get("category"), category)).thenReturn(categoryPredicate);
        when(criteriaBuilder.equal(root.get("state"), BookState.AVAILABLE)).thenReturn(statePredicate);

        when(criteriaBuilder.and(predicate, authorPredicate)).thenReturn(predicate);
        when(criteriaBuilder.and(predicate, titlePredicate)).thenReturn(predicate);
        when(criteriaBuilder.and(predicate, categoryPredicate)).thenReturn(predicate);
        when(criteriaBuilder.and(predicate, statePredicate)).thenReturn(predicate);

        Specification<Book> specification = BookSpecification.filter(authorId, title, category, state);
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).conjunction();
        verify(root).join("authors");
        verify(criteriaBuilder).equal(authorJoin.get("id"), authorId);
        verify(criteriaBuilder).equal(root.get("category"), category);
        verify(criteriaBuilder).equal(root.get("state"), BookState.AVAILABLE);
    }

    @Test
    @DisplayName("Deve retornar um Predicate sem filtros")
    void shouldReturnPredicateWithoutAnyFilters() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        Root<Book> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);

        Predicate predicate = mock(Predicate.class);
        when(criteriaBuilder.conjunction()).thenReturn(predicate);

        Specification<Book> specification = BookSpecification.filter(null, null, null, null);
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).conjunction();
        verify(criteriaBuilder, never()).and(any(), any());
    }

    @Test
    @DisplayName("Deve lançar BadRequestException para estado inválido")
    void shouldThrowBadRequestExceptionForInvalidState() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        Root<Book> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);

        Specification<Book> specification = BookSpecification.filter(null, null, null, "STATUS_INVALIDO");

        Executable executable = () -> specification.toPredicate(root, query, criteriaBuilder);
        BadRequestException thrown = assertThrows(BadRequestException.class, executable);
        assertEquals("Estado do livro inválido: STATUS_INVALIDO", thrown.getMessage());
    }
}
