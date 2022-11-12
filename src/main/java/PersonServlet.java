import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "PersonServlet", value = "/persons")
public class PersonServlet extends HttpServlet {
    Connection connection;
    static final String URL = "jdbc:postgresql://postgres-app/person_database";
    static final String USER = "postgres";
    static final String PASSWORD = "password";
    private boolean isDbConnectionInit = false;

    public PersonServlet() {
        System.out.println("PersonServlet is created..."); //todo: just for testing
        initialiseDbConnection();
    }

    private void initialiseDbConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
            isDbConnectionInit = true;
        } catch (Exception e) {
            System.out.println("Error occurred while connecting to DB");
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Get is called...");

        if(!isDbConnectionInit) initialiseDbConnection();

        JSONObject responseObject = new JSONObject();

        String testQuery = "SELECT firstname, lastname FROM \"Person\"";
        try {
            JSONArray personsJSONArray = new JSONArray();

            PreparedStatement statement = connection.prepareStatement(testQuery);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                JSONObject personJSON = new JSONObject();
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
//                Date dob = resultSet.getDate("dob"); // todo: need to figure how to fetch date value
                personJSON.put("firstname", firstname);
                personJSON.put("lastname", lastname);
//                personJSON.put("dob", dob)

                personsJSONArray.put(personJSON);
            }

            responseObject.put("persons", personsJSONArray);
            sendSuccessResponse(responseObject, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void sendSuccessResponse(JSONObject jsonObject, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);

        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }
}
