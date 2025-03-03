import random
import datetime
import psycopg2 # Для PostgreSQL
import os

# === Параметры подключения ===
DB_SETTINGS = {
    "dbname": "social_net_otus",       # Имя БД
    "user": "postgres",      # Пользователь
    "password": "Password1",  # Пароль
    "host": "localhost",     # Хост (обычно localhost)
    "port": 5445             # Порт PostgreSQL (5432) / MySQL (3306)
}

# Подключение к базе
conn = psycopg2.connect(**DB_SETTINGS)
cursor = conn.cursor()

# Читаем пользователей
cursor.execute("SELECT user_id FROM social_net_otus.sno_user_register")
user_ids = [row[0] for row in cursor.fetchall()]

# Загружаем посты из файла
if os.path.exists("../../posts.txt"):
    with open("../../posts.txt", "r", encoding="utf-8") as f:
        all_posts = [line.strip() for line in f.readlines() if line.strip()]
else:
    print("Ошибка: Файл posts.txt не найден!")
    exit()

# Проверяем, есть ли данные
if not user_ids or not all_posts:
    print("Ошибка: Нет данных для вставки!")
    conn.close()
    exit()

# SQL-запрос для вставки данных
insert_query = "INSERT INTO social_net_otus.sno_posts (login, text_post, time_modified, active) VALUES (%s, %s, %s, %s)"

# Генерируем случайные записи
batches = []
a = 0
while a != 1000:
    batch_data = []
    for user_id in user_ids:
        selected_posts = random.sample(all_posts, min(10, len(all_posts)))  # 10 случайных постов
        for post in selected_posts:
            batch_data.append((
                user_id,                           # Случайный user_id
                post,                              # Текст поста
                datetime.datetime.now(),           # Текущая дата
                True             # Случайное значение (active)
            ))
            if len(batch_data) == 10000:
                cursor.executemany(insert_query, batch_data)
                conn.commit()
                a = a + 1
                break

# Выполняем вставку данных одним запросом (ускоряет вставку)
for batch in batches:
    cursor.executemany(insert_query, batch)

# Сохраняем изменения и закрываем соединение
conn.commit()
conn.close()

print("✅ Таблица 'posts' успешно заполнена!")
