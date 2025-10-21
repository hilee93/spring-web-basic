package com.codeit.springwebbasic.book.service;

import com.codeit.springwebbasic.book.dto.request.BookCreateRequestDto;
import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.entity.BookStatus;
import com.codeit.springwebbasic.book.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void createBook(BookCreateRequestDto requestDto) {
        // ISBN 중복 체크
        Optional<Book> byIsbn = bookRepository.findByIsbn(requestDto.getIsbn());
        if (byIsbn.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 ISBN입니다: " + requestDto.getIsbn());
        }

        // Builder 패턴을 활용하면 초기화 순서를 내 맘대로 지정해도 상관 없고,
        // 원하는 필드만 골라서 초기화 하는 것이 가능.
        Book book = Book.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .publisher(requestDto.getPublisher())
                .isbn(requestDto.getIsbn())
                .publishedDate(requestDto.getPublishedDate())
                .status(BookStatus.AVAILABLE)
                .build();

        bookRepository.save(book);
    }
}
