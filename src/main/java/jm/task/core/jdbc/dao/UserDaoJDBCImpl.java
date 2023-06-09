package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private static final Connection connection = getConnection();
    public UserDaoJDBCImpl() {}


// Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
    public void createUsersTable() throws SQLException {

        String sqlCommand = "CREATE TABLE IF NOT EXISTS users (Id bigint PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(200), LastName VARCHAR(200), Age tinyint)";
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();){
            statement.executeUpdate(sqlCommand);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    public void dropUsersTable() throws SQLException {
        String sqlCommand = "TRUNCATE firstBase.users";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица удалена");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица не удалена");
        }
    }

//   Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sqlCommand = "INSERT INTO users (Name, LastName, Age) VALUES(?, ?, ?)";

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Удаление User из таблицы ( по id )
    public void removeUserById(long id) throws SQLException {
        String sqlCommand = "DELETE FROM users WHERE Id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)){
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//получение всех User
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sqlCommand = "SELECT Id, Name, LastName, Age FROM users";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return userList;
    }


    // Очистка содержания таблицы
    public void cleanUsersTable() throws SQLException {
        String sqlCommand = "DELETE FROM firstBase.users";
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Очищено");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не очищена");
        }
    }
}
