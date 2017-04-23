ДЗ4
Написать приложение, следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old)
и время которое ушло на сборки в минуту.
Добиться OutOfMemory в этом приложении через медленное подтекание по памяти
(например добавлять желементы в LIst и удалять только половину)
Настроить приложение (можно добавлять Thread.sleep()) так чтобы оно падало
с OOM примерно через 5 минут после начала работы.
Собрать статистику (количество сборок, время на сборрки) по разным типам GC.

Описание работы.
Задание выполнено в виде отдельного модуля, чтобы уменьшить и упростить jar файл.
Порядок запуска:
1. mvn package (по идее должен работать файл package.sh)
2. запуск starter-ов

ВНИМАНИЕ - время работы от 2,5 до 5 минут на каждый starter.

Выходные данные пишутся в файлы jvm....out

Получены следующие результаты:
    G1GC
        Size list: 18598760
        G1 Young Generation, sum duration : 4080 ms, sum count: 40, time: 2.9978993280333337 min
        G1 Old Generation, sum duration : 5258 ms, sum count: 5, time: 2.9979037756166664 min

    Standart (PS Scavenge
              PS MarkSweep) - не упал, зато подвесил процесс
        Size list: 15598960
        PS Scavenge, sum duration : 556 ms, sum count: 5, time: 1.3106358822500002 min
        PS MarkSweep, sum duration : 21525 ms, sum count: 8, time: 2.3539611457833334 min

    ConcurrentMarkSweep(ParNew
                        ConcurrentMarkSweep)
        Size list: 17848810
        ParNew, sum duration : 1344 ms, sum count: 26, time: 2.4114773955 min
        ConcurrentMarkSweep, sum duration : 44902 ms, sum count: 24, time: 2.5167393604833337 min


