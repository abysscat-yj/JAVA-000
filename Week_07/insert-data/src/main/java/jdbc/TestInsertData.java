package jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestInsertData {

    @Test
    public void batchInsert(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement("insert into goods_order(name) values(?)");
            for(int i = 0;i < 5;i++){
                ps.setString(1,"user_"+i);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.clearBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }





}
