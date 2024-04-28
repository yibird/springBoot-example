package com.fly;

import com.fly.model.Book;
import com.fly.model.BookDraft;
import com.fly.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = {JimmerApplication.class})
public class JimmerTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSave() {
        // 通过生成的BookDraft(草稿)对象创建一个Book对象
        Book bookProduce = BookDraft.$.produce(book -> {
            book.setEdition(1);
            book.setName("深入Jimmer");
            book.setPrice(new BigDecimal(49));
        });
        System.out.println(bookRepository.save(bookProduce) ? "Save successfully" : "Save failed");
    }

    @Test
    public void testUpdate() {
        // 通过生成的BookDraft(草稿)对象创建一个Book对象
        Book bookProduce = BookDraft.$.produce(book -> {
            book.setId(1);
            book.setName("深入Java Jimmer框架");
            book.setPrice(new BigDecimal(108.00));
        });
        System.out.println(bookRepository.update(bookProduce) ? "Update successfully" : "Update failed");
    }

    @Test
    public void testDel() {
        System.out.println(bookRepository.del(1) ? "Delete successfully" : "Delete failed");
    }

    @Test
    public void testBatchDel() {
        System.out.println(bookRepository.batchDel(List.of(2L, 3L)) ? "Batch Delete successfully" : "Batch Delete failed");
    }

    @Test
    public void testGetList() {
        List<Book> bookList = bookRepository.getList("J");
        System.out.println("bookList:" + bookList);
    }
}
