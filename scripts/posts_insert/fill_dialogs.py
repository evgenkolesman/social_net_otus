import random
import datetime
from fileinput import filename
from itertools import count

import psycopg2 # Для PostgreSQL
import os
#
#       POSTGRES_USER: dialog_service
#       POSTGRES_PASSWORD: ${DB_PASSWORD:-dialog_password}
# === Параметры подключения ===
DB_SETTINGS = {
    "dbname": "dialog",       # Имя БД
    "user": "dialog_service",      # Пользователь
    "password": "dialog_password",  # Пароль
    "host": "localhost",     # Хост (обычно localhost)
    "port": 5777             # Порт PostgreSQL (5432) / MySQL (3306)
}
DB_SETTINGS1 = {
    "dbname": "social_net_otus",       # Имя БД
    "user": "postgres",      # Пользователь
    "password": "Password1",  # Пароль
    "host": "localhost",     # Хост (обычно localhost)
    "port": 5445             # Порт PostgreSQL (5432) / MySQL (3306)
}
# Подключение к базе
conn = psycopg2.connect(**DB_SETTINGS)
conn1 = psycopg2.connect(**DB_SETTINGS1)
cursor = conn.cursor()
cursor1 = conn1.cursor()

# Читаем пользователей
cursor1.execute("SELECT user_id FROM social_net_otus.sno_user_register")
user_ids = [row[0] for row in cursor1.fetchall()]
conn1.close()
# Загружаем посты из файла

filename = "posts.txt"

if os.path.exists(filename):
    with open(filename, "r", encoding="utf-8") as f:
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
insert_query = "INSERT INTO dialog.dialogs.sno_dialogs as ss (\"from\", \"to\", \"text\") VALUES (%s, %s, %s)"

# Генерируем случайные записи
batches = []
a = 0
while a != 1000:
    batch_data = []
    count = 0
    while count != 10000:
        selected_posts = random.sample(all_posts, min(10, len(all_posts)))  # 10 случайных постов
        for post in selected_posts:
            fromA = random.choice(user_ids)
            toB = random.choice(user_ids)
            while fromA == toB:
                toB = random.choice(user_ids)

            batch_data.append((
                fromA,                           # Случайный user_id
                toB,                           # Случайный user_id
                post,                              # Текст поста
            ))
            if len(batch_data) == 10000:
                cursor.executemany(insert_query, batch_data)
                conn.commit()
                a = a + 1
                count = 0
                break
            count = count + 1

# Выполняем вставку данных одним запросом (ускоряет вставку)
for batch in batches:
    cursor.executemany(insert_query, batch)

# Сохраняем изменения и закрываем соединение
conn.commit()
conn.close()

print("✅ Таблица 'posts' успешно заполнена!")
