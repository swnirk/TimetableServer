#   TimetableServer
# Перед запуском программы 
Oткройте файл Server.java с помощью vim или какой-либо среды разработки для java и в 16 строке укажите путь до Вашего .csv файла
# Запуск через среду разработки для java:
1. Откройте среду разработки
2. Откройте в ней склонированный проект
3. Запустите программу
# Запуск через консоль:
1. Предворительно установите JDK (Скачать: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
2. Откройте командную строку и перейдите в папку со склонированным репозиторием
3. Для компиляции введите команду javac -sourcepath Server.java
4. Для запуска введите команду java -classpath . Server.java
# После запуска программы
1. Откройте любой браузер
2. Введите в поисковой строке http://localhost:8080/flights/id , где id - id рейса, информацию о котором нужно узнать пользователю 
   (Пример запроса: http://localhost:8080/flights/1)
3. Наблюдайте результат :)
