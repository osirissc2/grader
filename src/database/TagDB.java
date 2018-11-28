package database;

import DAO.TagDAO;
import grades.Gradable;
import grades.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TagDB extends DBconn implements TagDAO {
    @Override
    public List<Tag> findAllTagInOneGrade(String sid, int gradableid) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from grade_tag gt, tag t \n" +
                "where gt.gradableid=? and gt.studentid= ? and gt.tagid=t.tagid";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,gradableid);
        stmt.setString(2,sid);
        ResultSet rs=    stmt.executeQuery();
        List<Tag> TagList=new ArrayList<Tag>();
        while(rs.next()) {
            Tag t=new Tag(
                    rs.getInt("tagid"),
                    rs.getString("tname")
            );
            TagList.add(t);
        }
        DBconn.closeAll(conn, stmt, rs);
        return TagList;
    }
    @Override
    public List<Tag> findAllTag() throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="select * from tag";
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet rs=    stmt.executeQuery();
        List<Tag> TagList=new ArrayList<Tag>();
        while(rs.next()) {
            Tag t=new Tag(
                    rs.getInt("tagid"),
                    rs.getString("tname")
            );
            TagList.add(t);
        }
        DBconn.closeAll(conn, stmt, rs);
        return TagList;
    }
    @Override
    public int insertTag(Tag t) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="INSERT INTO tag(tanme) VALUES(?)";
        PreparedStatement stmt= conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1,t.getTname());
        stmt.executeUpdate();
        ResultSet tableKeys = stmt.getGeneratedKeys();
        tableKeys.next();
        int autoGeneratedID = tableKeys.getInt(1);
        DBconn.closeAll(conn,stmt,tableKeys);
        return autoGeneratedID;
    }
    @Override
    public void deleteTag(Tag t)throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="delete from tag where tagid=?";
        PreparedStatement stmt =conn.prepareStatement(sql);
        stmt.setInt(1,t.gettID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }
    @Override
    public void updateGradable(Tag t)throws Exception {
        Connection conn= DBconn.getConnection();
        String sql="update tag \n"+
                "set tname=? \n"+
                "where tagid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,t.getTname());
        stmt.setInt(2,t.gettID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }
}