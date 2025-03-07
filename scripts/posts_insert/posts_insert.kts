#!/usr/bin/env kotlin


import java.sql.Connection
import java.sql.DriverManager
import java.io.File
import java.time.Instant
import kotlin.random.Random

fun main() {
    // Подключение к базе (замени параметры на свои)
    val url = "jdbc:postgresql://localhost:5445/social_net_otus"
    val user = "postgres"
    val password = "Password1"

    val connection: Connection = DriverManager.getConnection(url, user, password)

    // Получаем список пользователей
    val userIds = mutableListOf<Int>()
    connection.createStatement().use { stmt ->
        val rs = stmt.executeQuery("SELECT user_id FROM social_net_otus.sno_user_register LIMIT 500000")
        while (rs.next()) {
            userIds.add(rs.getInt("id"))
        }
    }

    // Загружаем посты из файла
    val posts = File("../../posts.txt").readLines().filter { it.isNotBlank() }
    if (posts.isEmpty() || userIds.isEmpty()) {
        println("Ошибка: нет данных для вставки!")
        return
    }

    // Вставляем посты
    val insertSQL = "INSERT INTO posts (login, text_post, time_modified, active) VALUES (?, ?, ?, ?)"
    connection.prepareStatement(insertSQL).use { stmt ->
        userIds.forEach {  userId ->
            val selectedPosts = posts.shuffled().take(10)  // 10 случайных постов
            selectedPosts.forEach { post ->
                stmt.setInt(1, userId)
                stmt.setString(2, post)
                stmt.setObject(3, Instant.now())
                stmt.setInt(4, Random.nextInt(0, 2))  // 0 или 1
                stmt.addBatch()
            }
        }
        stmt.executeBatch()
    }

    connection.close()
    println("✅ Таблица posts успешно заполнена!")
}
