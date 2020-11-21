package jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcDemo2 {

    @Test
    public void updateOnPreparedStatement(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update person set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "yj");
            ps.setInt(2, 6);

            int num = ps.executeUpdate();
            if (num > 0) {
                System.out.println("更新成功！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps, rs);
        }
    }

    @Test
    public void tx(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);//设置不自动提交
            ps = conn.prepareStatement("insert into person(name) values(?)");
            ps.setString(1,"test");
            ps.execute();
            if (true) {
                throw new Exception("test tx rollback");//主动抛出异常
            }
            conn.commit(); //提交事务
        } catch (Exception e) {
            try {
                conn.rollback(); //捕获异常后回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }

    @Test
    public void batchInsert(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement("insert into person(name) values(?)");
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
