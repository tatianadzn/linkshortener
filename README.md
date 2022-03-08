# Сервис работы с короткими ссылками
Система предоставляет API для создания и использования коротких ссылок, предоставления статистики переходов (общее число преходов и число уникальных переходов) по ссылкам, также поддерживается операция удаления зарегистрированной короткой ссылки.
Приложение поддерживает механизм аутентификации при помощи токена. 

Технологический стек: Java 11, Spring MVC, Spring Boot, Spring Security, Spring JDBC, db H2 (embedded), Maven, Flyway, Swagger, Swagger2Markup.

## Описание API
___
Описание API сгенерировано автоматически при помощи библиотеки ```Swagger2Markup```.

Описание API с примерами запросов расположено [здесь](/docs/api/paths.md).

Описание используемых объектов (entities, models) находится [здесь](/docs/api/definitions.md).

Указанные документы могут быть перегенерированы в любое время выполнением теста ```ApiDocsGenerationTest```.

## Инструкция по запуску
___
1. Скачать и распаковать архив с проектом
2. Открыть терминал/командную строку и перейти в папку проекта
3. Выполнить команду ```./mvnw spring-boot:run``` (или ```.\mvnw spring-boot:run``` на Windows os). На машине должна быть установлена JRE 

Приложение запустится на порту 8080 (можно проверить и посмотреть swagger ui). 

## Инструкция по использованию
___
Для упрощения тестового взаимодействия с системой приложен [экпортированный файл для Postman](/Link%20Shortener.postman_collection.json), который можно импортировать в Postman и получить готовую модель для тестировния.
### Аутентификация
Каждый запрос к API должен содержать в себе токен для аутентификации в заголовке Authorization либо в куки (тоже Authorization), в форме строки: префикс Bearer + пробел + токен. Также в параметрах запроса обязательно должен передаваться уникальный идентификатор пользователя (параметр id).


Токен (подпись) пользователь формирует самостоятельно на основании параметров запроса и секретного ключа. 

Алгоритм формирования подписи:
1. берутся все параметры запроса в виде ключ=значение, разделителем параметров является символ "&"
2. производится их сортировка в алфавитном порядке по ключу
3. в конец получившейся последовательности отсортированных параметров добавляется секретный ключ
4. итоговая последовательность хэшируется с помощью алгоритма SHA-1

В приложенном экспортированном файле Постмана есть готовые примеры.

### Базовые операции работы с ссылками
Система предоставляет пользователю интерфейс для выполнения базовых операций по работе с ссылками: 
1. Регистрация полной ссылки в системе с возможностью задать время жизни ссылки с получением уникальной короткой ссылки (```POST /link/register```)
2. Редирект по короткой ссылке (```GET /link/redirect/{link}```)
3. Удаление короткой ссылки из базы (```DELETE /link/delete/{link}```)

### Статистика переходов
Система предоставлет возможность получения статистики перехода по ссылкам в виде вывода некоторого количества самых популярных ссылок (топ-10, по дефолту). У пользователя есть возможность задать это количество, либо будет использовано дефолтное значение, которое конфигурируется в ```application.properties```.
1. Получение статистики общих переходов по ссылкам (```GET /link/stats/all```)
2. Получение статистики уникальных переходов по ссылкам (```GET /link/stats/unique```)
