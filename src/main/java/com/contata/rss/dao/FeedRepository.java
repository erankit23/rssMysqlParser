package com.contata.rss.dao;

import org.springframework.data.repository.CrudRepository;

import com.contata.rss.model.Feed;

public interface FeedRepository extends CrudRepository<Feed, Long>{

}
