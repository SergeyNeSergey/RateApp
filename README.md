 Android приложение из одного экрана, которое получает список валют с сервера цб рф 
 по ссылке: https://www.cbr-xml-daily.ru/daily_json.js при помощи ретрофита.
 В поле вводится количество средств пользователя, при вводе средств происходит расчёт 
 итоговой суммы, которую пользователь может получить в любой валюте из списка. Список
 сохраняется в базе данных SQL, для сохранения возможности пользоваться приложением
 в случае отсутствия подключения к интернету.
 
 Язык программирования Kotlin. Были использованы библиотеки: Retrofit2, Rxjava2, Room
 