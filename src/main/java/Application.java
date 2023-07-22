import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
    // Создаем переменные с данными для подключения к базе
    final String user = "postgres";
    final String password = "27052705";
    final String url = "jdbc:postgresql://localhost:5432/skypro";

        try {
        Class.forName("org.postgresql.Driver");

        // Создаем соединение с базой с помощью Connection
        // Формируем запрос к базе с помощью PreparedStatement
        try (final Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT e.first_name AS \"Имя\", e.last_name AS \"Фамилия\", e.gender AS \"Пол\", e.age AS \"Возраст\", c.city_name AS \"Город\" " +
                     "FROM employee e " +
                     "JOIN city c ON e.city_id = c.city_id " +
                     "WHERE e.id = ?")) {

            // Подставляем значение вместо wildcard
            statement.setInt(1, 5);

            // Делаем запрос к базе и результат кладем в ResultSet
            final ResultSet resultSet = statement.executeQuery();

            // Методом next проверяем есть ли следующий элемент в resultSet
            // и одновременно переходим к нему, если таковой есть
            while (resultSet.next()) {

                // С помощью методов getInt и getString получаем данные из resultSet
                String first_name = "First_name: " + resultSet.getString("Имя");
                String last_name = "Last_name: " + resultSet.getString("Фамилия");
                String gender = "Gender: " + resultSet.getString("Пол");
                String city = "City: " + resultSet.getString("Город");
                int age = resultSet.getInt(4);

                // Выводим данные в консоль
                System.out.println(first_name);
                System.out.println(last_name);
                System.out.println(gender);
                System.out.println(city);
                System.out.println("Age: " + age);

            }
            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);

            Employee pavel = new Employee(6, "Pavel", "Konev", "Male", 43,3);

            // Вызываем метод добавления объекта
            employeeDAO.createEmployee(pavel);

            // Создаем список наполняя его объектами, которые получаем
            // путем вызова метода для получения всех элементов таблицы
            List<Employee> employees = new ArrayList<>(employeeDAO.getAllEmployees());

            // Выведем список в консоль
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        }
    }

}