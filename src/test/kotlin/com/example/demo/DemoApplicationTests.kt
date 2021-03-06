package com.example.demo

import com.example.demo.dao.sample.entity.TbUser
import com.example.demo.dao.sample.repository.main.TbUserMainRepository
import com.example.demo.dao.sample.repository.replica.TbUserReplicaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import java.time.LocalDateTime

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	lateinit var tbUserMainRepository: TbUserMainRepository

	@Autowired
	lateinit var tbUserReplicaRepository: TbUserReplicaRepository

	@Test
	fun contextLoads() {
	}

	@BeforeEach
	fun setUp() {
		tbUserMainRepository.deleteAll();
	}

	@Test
	fun writeToMainAndReadFromReplica() {
		tbUserMainRepository.save(
				TbUser(
						null,
						"morph's id",
						"morph's name",
						"morph@email",
						1,
						"N",
						LocalDateTime.now(),
						LocalDateTime.now()
				)
		)

		Thread.sleep(500)

		tbUserReplicaRepository.findAll().run {
			assertEquals(1, count())

			forEach {
				when (it.userId) {
					"morph's id" -> {
						assertNotNull(it.id)
						assertEquals("morph's id", it.userId)
						assertEquals("morph's name", it.name)
						assertEquals("morph@email", it.email)
						assertEquals(1, it.status)
						assertEquals("N", it.deleteYn)
					}
					else -> fail(it.toString())
				}
			}
		}
	}

	@Test
	fun writeToReplicaShouldFailed() {
		val result = try {
			tbUserReplicaRepository.save(
					TbUser(
							null,
							"morph's id",
							"morph's name",
							"morph@email",
							1,
							"N",
							LocalDateTime.now(),
							LocalDateTime.now()
					)
			)
			""
		} catch (e: DbActionExecutionException) {
			e.cause!!.cause!!.message
		}

		assertTrue(result!!.contains("--read-only"))
	}

}
