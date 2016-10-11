package ru.terra.twochsaver.web.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.terra.twochsaver.web.db.entity.Message;
import ru.terra.twochsaver.web.db.entity.Thr;

import java.util.List;

/**
 * Created by terranz on 11.10.16.
 */
public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findByThread(Thr thr);
}
