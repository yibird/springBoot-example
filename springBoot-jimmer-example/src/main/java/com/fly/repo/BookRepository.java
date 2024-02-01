package com.fly.repo;

import com.fly.model.Book;
import org.babyfish.jimmer.spring.repository.JRepository;

/**
 * @Description
 * @Author zchengfeng
 * @Date 2024/1/18 15:56:27
 */
public interface BookRepository extends JRepository<Book, Long> {}