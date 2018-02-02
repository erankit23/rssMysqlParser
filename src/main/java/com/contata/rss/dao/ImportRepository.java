package com.contata.rss.dao;

import org.springframework.data.repository.CrudRepository;

import com.contata.rss.model.Feed;
import com.contata.rss.model.Import;

public interface ImportRepository extends CrudRepository<Import, Long>{

}
