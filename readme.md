# Лабораторная работа №3
Выполнил: Абельдинов Рафаэль  
Группа: 6132-010402D  


## Задание 1  
JAX-RS и SpringREST схожи, их главное отличие в том, что JAX-RS интегрируется
в Jakarta EE (реализован в ЛР1), а SpringREST интегрируется в, собственно, Sping (ЛР2).
SpingREST показался удобнее, так как нет потребности использовать отдельный сервер в виде Glassfish.

## Задание 2
За основу была взята ЛР2. Был создан пакет rest, в нем реализованы классы `DroneRestController` 
и `FlightControllerRestController`, реализующие REST API.  

## Задание 3
В `pom.xml` был подключён `jackson-dataformat-xml`.  
В классах `DroneRestController` и `FlightControllerRestController` в аннотациях `@GetMapping`, `@PostMapping`, `@PutMapping` указаны соответствующие
`produces`/`consumes` (`application/json`, `application/xml`). Формат запроса и ответа
выбирается на основе заголовков `Content-Type` и `Accept`.

## Задание 4
Были реализованы XSL преобразования для отображения таблиц с классами `Drone` 
`FlightController`, аналогично ЛР2. Кроме этого, была добавлена навигация между XSL и JSON, а также
между двумя таблицами

## Задание 5
Были созданы отдельные методы в классах `DroneRestController` и
`FlightControllerRestController` для добавления XSL-преобразований в 
XML-ответы REST.

## Задание 6
После запуска приложений можно перейти по ссылке `http://localhost:8080/api/flight-controllers/xml`
открывается таблица:  
![img.png](img/img1.png)  
С данной страницы можно открыть формат JSON данной таблицы:  
![img.png](img/img2.png)  
Также можно открыть таблицу с дронами:  
![img.png](img/img3.png)  
А из нее, аналогично, JSON-вариант таблицы:
![img.png](img/img4.png)