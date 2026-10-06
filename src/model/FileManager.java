package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//класс для работы с текстовыми файлами: прочитать текст из файла и записать текст в файл
//ошибки (IOException) здесь не обрабатываются, а передаются дальше (throws) -
//их ловит меню, выводит сообщение и программа работает дальше
public class FileManager {

    //читает весь файл и возвращает его содержимое одной строкой (строки файла через пробел)
    public static String readText(String fileName) throws IOException {
        BufferedReader reader = null; //reader - поток для чтения файла
        String text = "";             //text - сюда собираем содержимое файла

        try {
            //FileReader читает символы из файла, BufferedReader над ним позволяет читать по строкам
            //если файла нет - здесь возникнет FileNotFoundException
            reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine(); //line - очередная строка файла (null - строки кончились)
            //читаем строки, пока они не закончатся
            while (line != null) {
                text = text + line + " ";
                line = reader.readLine();
            }
        } finally {
            //finally выполняется всегда, даже если была ошибка - тут закрываем файл
            if (reader != null)
                reader.close();
        }

        //пустой файл - сами возбуждаем исключение, чтобы меню сообщило об этом
        if (text.trim().isEmpty())
            throw new IOException("файл пустой");

        return text.trim();
    }

    //записывает текст в файл (если файл уже есть - он перезаписывается)
    public static void writeText(String fileName, String text) throws IOException {
        BufferedWriter writer = null; //writer - поток для записи в файл

        try {
            //FileWriter пишет символы в файл, BufferedWriter над ним копит их и пишет пачкой
            //если имя файла недопустимое - здесь возникнет исключение
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
        } finally {
            //закрываем файл в любом случае, при закрытии данные окончательно записываются
            if (writer != null)
                writer.close();
        }
    }
}
