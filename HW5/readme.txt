Задание
Написать свой тестовый фреймворк. Поддержать свои аннотации @Test, @Before, @After.
Запускать вызовом статического метода с
(1) массивом классов с тестами.
(2) именем package в котором надо найти и запустить тесты.


Исполнение.
1. Для включения класса в тест необходимо пометить его как @Test. Класс должен иметь пустой публичный конструктор
2. Для включения метода в тесты необходимо пометить метод @Test, @Before (выполняется первыми), @After (выполняются последними)
Методы должны быть без параметров ии публичными.
3. Asserts - упрощенные.
4. Поиск классов в package реализовано через сторонню библиотеку Reflections, так как в лекции было сказано что это факультатив.
5. Порядок работы тестера:
    1.Создаем объект.
    2. Входим в цикл тестов:
        1. Before
        2. Test (по одному из списка методов)
        3. After

Сделаны тесты, проверяющие, что что-то работает))