package jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcHikariDemo {

    @Test
    public void hikariTest(){
        HikariConfig config = new HikariConfig("src/main/resources/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);
        try {
            Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from person");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("user{id:"+rs.getInt("id")+",name:"+rs.getString("name")+"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ds.close();
        }
    }


}
