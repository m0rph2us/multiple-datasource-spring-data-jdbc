package com.example.demo.dao.sample.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("tb_user")
data class TbUser(
        @Id
        @Column("id")
        val id: Long?,

        @Column("user_id")
        val userId: String,

        @Column("name")
        val name: String,

        @Column("email")
        val email: String,

        @Column("status")
        val status: Int,

        @Column("delete_yn")
        val deleteYn: String,

        @Column("reg_dt")
        val regDt: LocalDateTime,

        @Column("chg_dt")
        val chgDt: LocalDateTime
)