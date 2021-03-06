package com.example.demo.dao.sample.repository.replica

import com.example.demo.dao.sample.entity.TbUser
import org.springframework.data.repository.CrudRepository

interface TbUserReplicaRepository : CrudRepository<TbUser, Long>