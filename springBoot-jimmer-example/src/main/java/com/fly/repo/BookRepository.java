package com.fly.repo;

import com.fly.model.Book;
import com.fly.model.BookDraft;
import com.fly.model.BookTable;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.DeleteResult;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.babyfish.jimmer.sql.ast.query.ConfigurableRootQuery;
import org.babyfish.jimmer.sql.ast.tuple.Tuple3;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author zchengfeng
 * @Date 2024/1/18 15:56:27
 */
@Repository
@AllArgsConstructor
public class BookRepository {
    private final JSqlClient sqlClient;
    // 获取Book模型表实体,可以通过该对象获取Book模型任意字段
    private static final BookTable table = BookTable.$;

    /**
     * 根据模型添加数据
     *
     * @param book Book模型
     * @return 布尔值, 表示是否删除成功
     */
    public boolean save(Book book) {
        SimpleSaveResult<Book> result = sqlClient.save(book);
        return result.getTotalAffectedRowCount() > 0;
    }

    /**
     * 根据模型修改数据
     *
     * @param book Book模型
     * @return 布尔值, 表示是否删除成功
     */
    public boolean update(Book book) {
        SimpleSaveResult<Book> result = sqlClient.update(book);
        return result.getTotalAffectedRowCount() > 0;
    }

    /**
     * 根据id删除Book
     *
     * @param id Book id
     * @return 布尔值, 表示是否删除成功
     */
    public boolean del(long id) {
        DeleteResult result = sqlClient.deleteById(Book.class, id);
        return result.getTotalAffectedRowCount() > 0;
    }

    /**
     * 根据id数组删除Book
     *
     * @param ids Book id数组
     * @return 布尔值, 表示是否全部删除成功
     */
    public boolean batchDel(List<Long> ids) {
        DeleteResult result = sqlClient.deleteByIds(Book.class, ids);
        return result.getTotalAffectedRowCount() == ids.size();
    }

    /**
     * 根据name模糊查询书名,并根据书的版本(edition)降序排序,仅返回Book的id、name、edition三个字段,
     * 然后将执行结果通过Stream的collect()收集为 List<Book>
     *
     * @param name Book name
     * @return List<Book>
     */
    public List<Book> getList(String name) {
        /**
         * 返回一个List,泛型为一个Tuple3(元组,类似于List,但是元组中的元素可以是任何数据类型),
         * Tuple的泛型为 select() 中数据库列映射的Java类型
         */
        List<Tuple3<Long, String, Integer>> result = sqlClient.createQuery(table)
                .where(table.name().ilike(name))
                // 根据edition字段降序排序
                .orderBy(table.edition().desc())
                // 查询指定列
                .select(
                        table.id(),
                        table.name(),
                        table.edition()
                ).execute();

        return result.stream().map((item) -> {
            // 通过Book草稿类创建一个Book
            return BookDraft.$.produce(book -> {
                book.setId(item.get_1());
                book.setName(item.get_2());
                book.setEdition(item.get_3());
            });
        }).collect(Collectors.toList());
    }
}