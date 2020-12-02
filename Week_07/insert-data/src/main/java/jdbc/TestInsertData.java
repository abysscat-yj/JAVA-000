package jdbc;

import lombok.extern.java.Log;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;



@Log
public class TestInsertData {


    // time: 3302502ms = 3302s = 55min
    @Test
    public void batchInsertAutoCommit() {
        int batchCount = 1_000_000;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            ps = conn.prepareStatement("insert into user(user_id, user_name, mobile, password) values(?, ?, ?, ?)");

            long start = System.currentTimeMillis();

            for(int i = 0; i < batchCount; i++){
                ps.setInt(1, i);
                ps.setString(2, "user_" + i);
                ps.setString(3,  String.valueOf(i));
                ps.setString(4,  String.valueOf(i));
                ps.addBatch();

                if (i % 1000 == 0) {
                    log.info("count：" + i);
                }
            }

            ps.executeBatch();
            ps.clearBatch();

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }


    // time: 195965ms = 196s = 3.26min
    @Test
    public void batchInsertSingleTx() {
        int batchCount = 1_000_000;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false);

            ps = conn.prepareStatement("insert into user(user_id, user_name, mobile, password) values(?, ?, ?, ?)");

            long start = System.currentTimeMillis();

            for(int i = 0; i < batchCount; i++) {
                ps.setInt(1, i);
                ps.setString(2, "user_" + i);
                ps.setString(3,  String.valueOf(i));
                ps.setString(4,  String.valueOf(i));
                ps.addBatch();

                if (i % 1000 == 0) {
                    log.info("count：" + i);
                }
            }

            ps.executeBatch();
            ps.clearBatch();

            conn.commit();

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }

    // time: 157209ms = 157s = 2.6min
    @Test
    public void batchInsertMultiTx() {
        int batchCount = 1_000_000;
        int txBatchCount = 5000;

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false);

            ps = conn.prepareStatement("insert into user(user_id, user_name, mobile, password) values(?, ?, ?, ?)");

            long start = System.currentTimeMillis();

            for(int i = 0; i < batchCount; i++) {
                ps.setInt(1, i);
                ps.setString(2, "user_" + i);
                ps.setString(3,  String.valueOf(i));
                ps.setString(4,  String.valueOf(i));
                ps.addBatch();

                if (i % txBatchCount == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                    conn.commit();
                }
            }

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }


    // time: 19033ms = 19s
    @Test
    public void batchInsertConcatValueString() {
        int batchCount = 1_000_000;

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false);

            String insertSql = "insert into user(user_id, user_name, mobile, password) values(?, ?, ?, ?)";

            StringBuilder stringBuilder = new StringBuilder(insertSql);

            long start = System.currentTimeMillis();

            for(int i = 1; i < batchCount; i++) {
                stringBuilder.append(",(?, ?, ?, ?)");
            }

            ps = conn.prepareStatement(stringBuilder.toString());

            for(int i = 1; i <= batchCount; i++) {
                ps.setInt((i - 1) * 4 + 1, i);
                ps.setString((i - 1) * 4 + 2, "user_" + i);
                ps.setString((i - 1) * 4 + 3,  String.valueOf(i));
                ps.setString((i - 1) * 4 + 4,  String.valueOf(i));
            }

            ps.execute();

            conn.commit();

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }





}
