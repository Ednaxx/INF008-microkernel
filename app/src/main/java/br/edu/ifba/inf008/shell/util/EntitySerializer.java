package br.edu.ifba.inf008.shell.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.models.UserModel;

public class EntitySerializer {
    private static final String DATA_DIR = "data";
    private static final String BOOKS_FILE = DATA_DIR + "/books.dat";
    private static final String USERS_FILE = DATA_DIR + "/users.dat";

    private static void ensureDataDirectoryExists() {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static void serializeBooks(List<BookModel> books) {
        ensureDataDirectoryExists();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(BOOKS_FILE))) {
            oos.writeObject(new ArrayList<>(books));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<BookModel> deserializeBooks() {
        List<BookModel> books = new ArrayList<>();
        ensureDataDirectoryExists();
        File file = new File(BOOKS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file))) {
                books = (List<BookModel>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    public static void serializeUsers(List<UserModel> users) {
        ensureDataDirectoryExists();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(USERS_FILE))) {
            oos.writeObject(new ArrayList<>(users));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<UserModel> deserializeUsers() {
        List<UserModel> users = new ArrayList<>();
        ensureDataDirectoryExists();
        File file = new File(USERS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file))) {
                users = (List<UserModel>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}