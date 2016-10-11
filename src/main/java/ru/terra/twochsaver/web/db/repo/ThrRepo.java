package ru.terra.twochsaver.web.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.terra.twochsaver.web.db.entity.Thr;

/**
 * Created by terranz on 11.10.16.
 */
public interface ThrRepo extends CrudRepository<Thr, Integer> {
    Thr findByUrl(String url);
}
