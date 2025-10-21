package com.codeit.springwebbasic.book.controller;

import com.codeit.springwebbasic.book.dto.request.BookCreateRequestDto;
import com.codeit.springwebbasic.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // final 변수를 초기화 하는 매개값을 전달받는 생성자
public class BookController {
    private final BookService bookService;
    /*
    도서 등록
        {
            "title": string,
            "author": string,
            "isbn": string,
            "publisher": string,
            "publishedDate": date
        }
     */
    public void createBook(@Valid @RequestBody BookCreateRequestDto requestDto) {
        bookService.createBook(requestDto);
    }
}
