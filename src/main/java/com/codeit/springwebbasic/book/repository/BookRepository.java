package com.codeit.springwebbasic.book.repository;

import com.codeit.springwebbasic.book.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class BookRepository {
    // 웹 어플리케이션은 동시에 여러 오청이 한꺼번에 들어올 수 있기 때문에
    // 멀티 스레드에서도 안전하게 사용할 수 있는 Hashmap을 사용.
    // AtomicLong도 마찬가지로 Long 타입의 정수를 멀티 스레드에서 안전하게 공유 가능.
    private final Map<Long, Book> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    // 도서 저장
    public Book save(Book book) {
        if(book.getId() == null) {
            book.setId(sequence.getAndIncrement()); // 값을 얻고 나서 값을 하나 증가
        }
        store.put(book.getId(), book);
        return book;
    }

    // 도서 조회
    public List<Book> findAll() {
        // Map에서 value들만 전부 꺼낸 후 List로 반환.
        return new ArrayList<>(store.values());
    }

    // id에 따라 조회가 안될 수도 있잖아 -> 조회가 안되면 null 리턴 -> NPE 발생 가능성 높아짐.
    // Optional로 포장해서 호출한 쪽에서 충분한 검증 후에 Book 객체를 사용하도록 유도.
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Book> findByTitleContaining(String title) {
        return store.values()
                .stream()
                .filter(e -> e.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
