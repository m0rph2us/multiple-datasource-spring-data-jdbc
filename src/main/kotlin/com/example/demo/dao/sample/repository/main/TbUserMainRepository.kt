package com.example.demo.dao.sample.repository.main

import com.example.demo.dao.sample.entity.TbUser
import org.springframework.data.repository.CrudRepository

interface TbUserMainRepository : CrudRepository<TbUser, Long>