package com.contata.rss.dao;

import org.springframework.data.repository.CrudRepository;

import com.contata.rss.model.Url;

public interface UrlRepository extends CrudRepository<Url, Long> {

}
