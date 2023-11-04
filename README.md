# :bank:Bank
Консольное приложение, которое обладает основными функциями банка с генерацией выписок PDF и TXT формата, а также имеет CRUD-операции для всех сущностей. 
Проект разработан на Java 17 с использованием Gradle для сборки, PostgreSQL для хранения данных, JDBC для взаимодействия с базой данных,
Lombok для упрощения работы с шаблонным кодом, а также включает в себя Docker.

## Инструкция по запуску
* Клонировать репозиторий.
* Собрать проект с помощью Gradle.
  ```bash
  gradle build
  ```
* Поднять БД с помощью docker-compose.
  ```bash
  docker build
  docker-compose up
  ```
* Запустить Main класс


## Работа приложения

- Начальное меню
```
----------------------------
1: CRUD-операции
2: Пользователь
----------------------------
```

## CRUD-операции

- Меню CRUD-операций
```
----------------------------
1: Операции со счетами
2: Операции с пользователями
3: Операции с транзакциями
4: Операции с банками
----------------------------
```

- Меню со счетами
```
----------------------------
1: Просмотреть все счета
2: Добавить счет
3: Изменить счет
4: Удалить счет
5: Вернуться назад
----------------------------
```

- Просмотр всех счетов
```
id: 17, number: 0000000002, balance: 0,00, user_id: 2, bank_id: 1, opening date: 2023-08-30
id: 19, number: 0000000004, balance: 0,00, user_id: 3, bank_id: 4, opening date: 2023-09-04
id: 20, number: 0000000005, balance: 0,00, user_id: 5, bank_id: 2, opening date: 2023-08-30
id: 21, number: 0000000006, balance: 0,00, user_id: 6, bank_id: 1, opening date: 2022-07-15
id: 22, number: 0000000007, balance: 0,00, user_id: 7, bank_id: 2, opening date: 2021-11-20
id: 23, number: 0000000008, balance: 0,00, user_id: 8, bank_id: 3, opening date: 2020-05-04
id: 24, number: 0000000009, balance: 0,00, user_id: 9, bank_id: 4, opening date: 2019-09-12
```

- Создание нового счета
```
Введите нужный вам баланс: 
1200

Введите id пользователя для счета: 
6

Введите id банка счета: 
2

Введите дату открытия счета: 
2023-11-04
```

- Изменить счет
```
Введите id счета, который нужно изменить: 
16
id: 16, number: 0000000001, balance: 406,00, user_id: 1, bank_id: 2, opening date: 2023-08-30

Введите нужный вам баланс: 
500

Введите id пользователя для счета: 
3

Введите id банка счета: 
4

Введите дату открытия счета: 
2023-11-04
```
- Удалить счет
```
Введите id счета, который хотите удалить: 
16
```

## Пользователь

- Определение пользователя и счета
```
Введите свое ФИО: 
Смирнов Иван Александрович

Выберите счет, которым хотите воспользоваться: 
1: 0000000005, 0,00, 2023-08-30
2: 0000000025, 750,00, 2018-11-11
2
```

- Меню пользователя
```
----------------------------
1: Проверить баланс
2: Пополнить баланс
3: Снять деньги
4: Перевод
5: Запросить выписку
6: Сменить счет
7: Выход
----------------------------
```

- Проверить баланс
```
Ваш баланс: 750.00 BYN
```

- Пополнить баланс
```
На какую сумму вы хотите пополнить баланс? 
200
Чек сформирован.
```

- Снять деньги
```
Какую сумму вы хотите снять? 
200
Чек сформирован.
```

- Перевод
```
Введите номер счета, на который хотите перевести деньги: 
0000000025

Введите сумму, которую хотите перевести: 
10
Чек сформирован.
```

- Запросить выписку
```
Выписка успешно сформирована.
```

- Сменить счет
```
Выберите счет, которым хотите воспользоваться: 
1: 0000000005, 0,00, 2023-08-30
2: 0000000025, 760,00, 2018-11-11
1
```

- Пример чека
```
----------------------------------------------
|              Банковский чек                |
| Чек:                                    31 |
| 16-10-2023                        20:49:33 |
| Тип транзакции:             Cнятие средств |
| Банк:                      Belagroprombank |
| Счет:                           0000000020 |
| Сумма:                           25,00 BYN |
----------------------------------------------
```

- Пример выписки
```
                            Выписка
                          Belarusbank
Клиент                      | Короткевич Алексей Геннадьевич
Счет                        | 0000000001
Валюта                      | BYN
Дата открытия               | 30.08.2023
Период                      | 30.08.2023 - 27.10.2023
Дата и время формирования   | 27.10.2023, 21:58
Остаток                     | 406.00 BYN
   Дата    |       Примечание             | Сумма
-----------------------------------------------------------
27.10.2023 | Cнятие средств                | -500.00 BYN
27.10.2023 | Перевод                       | -15.00 BYN
27.10.2023 | Перевод                       | -20.00 BYN
27.10.2023 | Перевод                       | -59.00 BYN
27.10.2023 | Пополнение                    | 1000.00 BYN
02.10.2023 | Перевод                       | -100.00 BYN

```

