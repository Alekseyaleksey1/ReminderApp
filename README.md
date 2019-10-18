ReminderApp
========================================================================================================================
Architecture - MVP
========================================================================================================================
Для хранения данных использовалась библиотека Room;
Для асинхронной работы использовалась Rx;

Всё из ТЗ я успел сделать.

Что я бы изменил/добавил, если бы было больше времени:
- вынес бы все строки в ресурсы;
- улучшил бы внешний вид приложения;
- в данный момент все активити залочены в портретной ориентации, без лока всё работает, вращается и выглядит тоже хорошо, но я бы немного поменял внешний вид;
- как мне кажется, нужно предусмотреть в приложении механизм удаления заметок за прошлые даты. Так как по ТЗ приложение показывает заметки на неделю вперёд значит заметки предыдущих дней, которые не были удалены вручную, остаются в БД, не отображаются и к ним нельзя получить доступ для их удаления через UI. Можно, например, при старте главной активити проверять все данные в БД и удалять заметки за прошедшие даты. 
- возможно, стоит добавить функционал для просмотра всех, в том числе за прошедшие дни, заметок. Это решит в том числе проблему с невозможностью удаления из БД заметок на прошедшие дни.
