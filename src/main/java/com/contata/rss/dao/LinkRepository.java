package com.contata.rss.dao;

import org.springframework.data.repository.CrudRepository;

import com.contata.rss.model.Link;


public interface LinkRepository extends CrudRepository<Link, Long> {

}
