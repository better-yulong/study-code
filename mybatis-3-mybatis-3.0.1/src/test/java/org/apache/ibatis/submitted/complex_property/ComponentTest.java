package org.apache.ibatis.submitted.complex_property;

import static junit.framework.Assert.assertNotNull;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

public class ComponentTest {
  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setup() throws Exception {
    setupSqlSessionFactory();
    runDBScript();
  }


  @Test
  public void shouldInsertNestedPasswordFieldOfComplexType() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      //Create User
      User user = new User();
      user.setId(500000L);
      user.setPassword(new EncryptedString("secret"));
      user.setUsername("johnny" + Calendar.getInstance().getTimeInMillis());//random
      user.setAdministrator(true);

      sqlSession.insert("User.insert", user);

      //Retrieve User
      user = (User) sqlSession.selectOne("User.find", user.getId());

      assertNotNull(user.getId());

      sqlSession.rollback();
    } finally {
      sqlSession.close();
    }
  }

  private static void runDBScript() throws SQLException, IOException {
    Connection conn = sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection();
    ScriptRunner runner = new ScriptRunner(conn);
    String resource = "org/apache/ibatis/submitted/complex_property/db/db.sql";
    Reader reader = Resources.getResourceAsReader(resource);
    runner.runScript(reader);
    conn.close();
  }

  private static void setupSqlSessionFactory() throws IOException {
    String resource = "org/apache/ibatis/submitted/complex_property/db/Configuration.xml";
    Reader reader = Resources.getResourceAsReader(resource);
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
  }

}
