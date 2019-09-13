
import com.vmm.JHTTPServer;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.UIManager.getString;

public class MyServer extends JHTTPServer {

    public MyServer(int port) throws IOException {
        super(port);
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        Response res = null;
        if (uri.contains("GetSource")) {
            uri = uri.substring(1);
            uri = uri.substring(uri.indexOf("/") + 1);
            System.out.println(uri);
            res = sendCompleteFile(uri);

        } else if (uri.contains("StreamMedia")) {
            uri = uri.substring(1);
            uri = uri.substring(uri.indexOf("/") + 1);
            System.out.println(uri);
            res = streamFile(uri, method, header);

        } else if (uri.contains("AdminLogin")) {
            String email = parms.getProperty("email");
            String passw = parms.getProperty("password");
            ResultSet rs = JdbcCommon.executeQuery("select * from Admin where email='" + email + "' and pass='" + passw + "'");

            try {

                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "success");

                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (uri.contains("AddCourse")) {

            try {

                String name = parms.getProperty("name");
                String description = parms.getProperty("description");
                String amount2 = parms.getProperty("amount");
                int amount = Integer.parseInt(amount2);
                String pre = parms.getProperty("pre");
                String category = parms.getProperty("category");
                String filename1 = saveFileOnServerWithRandomName(files, parms, "squarephoto", "src" + File.separator + "coursecontents");
                String filename2 = saveFileOnServerWithRandomName(files, parms, "widephoto", "src" + File.separator + "coursecontents");
                String filename3 = saveFileOnServerWithRandomName(files, parms, "samplevideo", "src" + File.separator + "coursecontents");
                ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename='" + name + "'");
                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                } else {
                    rs.moveToInsertRow();
                    rs.updateString("coursename", name);
                    rs.updateString("description", description);
                    rs.updateString("category", category);
                    rs.updateString("squarephoto", "src" + File.separator + "coursecontents" + File.separator + filename1);
                    rs.updateString("widephoto", "src" + File.separator + "coursecontents" + File.separator + filename2);
                    rs.updateString("samplevideo", "src" + File.separator + "coursecontents" + File.separator + filename3);
                    rs.updateString("pre", pre);
                    rs.updateInt("amount", amount);
                    rs.insertRow();

                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        } else if (uri.contains("viewcourses")) {
            String ans = "";
            ResultSet rs = JdbcCommon.executeQuery("select * from course");
            try {
                while (rs.next()) {
                    String name = rs.getString("coursename");
                    String description = rs.getString("description");
                    String category = rs.getString("category");
                    String amount = rs.getString("amount");

                    ans = ans + name + ";" + description + ";" + category + ";" + amount + "~";
                }
                res = new Response(HTTP_OK, "text/plain", ans);

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("deletecourse")) {
            String coursed = parms.getProperty("coursed");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename='" + coursed + "'");
            try {
                if (rs.next()) {
                    try {
                        rs.deleteRow();
                        res = new Response(HTTP_OK, "text/plain", "success");
                    } catch (SQLException ex) {
                        Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                }
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("/getcourse")) {
            String ans = "";
            String selection = parms.getProperty("selection");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where category='" + selection + "'");
            try {
                while (rs.next()) {

                    ans = ans + rs.getString("coursename") + ";";

                }
                res = new Response(HTTP_OK, "text/plain", ans);
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("/addvideos")) {

            int LectureNo = Integer.parseInt(parms.getProperty("LectureNo"));
            String VideoPath = parms.getProperty("VideoPath");
            String videoName = saveFileOnServerWithRandomName(files, parms, "VideoPath", "src" + File.separator + "VideoContents");
            String VideoThumbnail = parms.getProperty("VideoThumbnail");
            String videoThumbnailName = saveFileOnServerWithRandomName(files, parms, "VideoThumbnail", "src" + File.separator + "VideoContents");

            int Duration = Integer.parseInt(parms.getProperty("Duration"));
            String Title = parms.getProperty("Title");
            String CourseName = parms.getProperty("CourseName");
            ResultSet rs = JdbcCommon.executeQuery("select * from videos");
            try {

                rs.moveToInsertRow();
                rs.updateInt("Lecture_no", LectureNo);
                rs.updateString("Video_path", "src" + File.separator + "VideoContents" + File.separator + videoName);
                rs.updateString("Video_thumbnail", "src" + File.separator + "VideoContents" + File.separator + videoThumbnailName);
                rs.updateInt("Duration", Duration);
                rs.updateString("Title", Title);
                rs.updateString("Coursename", CourseName);

                rs.insertRow();
                res = new Response(HTTP_OK, "text/plain", "success");

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("fetchvideos")) {
            String ans = "";
            String course = parms.getProperty("Course");

            ResultSet rs = JdbcCommon.executeQuery("select * from videos where Coursename='" + course + "'");
            try {
                while (rs.next()) {
                    int videoid = rs.getInt("Video_Id");
                    int lectureno = rs.getInt("Lecture_no");
                    String title = rs.getString("Title");
                    int duration = rs.getInt("Duration");
                    ans = ans + videoid + ";" + lectureno + ";" + title + ";" + duration + "~";

                }
                res = new Response(HTTP_OK, "text/plain", ans);
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains(("deletevideo"))) {

            String videod = parms.getProperty("videoid");
            ResultSet rs = JdbcCommon.executeQuery("select * from videos where Video_id='" + videod + "'");
            try {
                if (rs.next()) {
                    rs.deleteRow();
                    res = new Response(HTTP_OK, "text/plain", "success");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.contains("signup")) {
            String email = parms.getProperty("email");
            String password = parms.getProperty("password");
            String name = parms.getProperty("name");
            String phone = parms.getProperty("phone");

            String f1 = saveFileOnServerWithRandomName(files, parms, "profilephoto", "src" + File.separator + "userphotos");
            ResultSet rs = JdbcCommon.executeQuery("select * from users where email='" + email + "'");
            try {
                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "fail");

                } else {
                    rs.moveToInsertRow();
                    rs.updateString("email", email);
                    rs.updateString("password", password);
                    rs.updateString("name", name);
                    rs.updateString("phone", phone);
                    rs.updateString("photo", "src" + File.separator + "userphotos" + File.separator + f1);
                    rs.insertRow();

                    res = new Response(HTTP_OK, "text/plain", "success");

                }

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("login")) {
            String email = parms.getProperty("email");
            String pass = parms.getProperty("password");
            ResultSet rs = JdbcCommon.executeQuery("select * from users where email='" + email + "' and password='" + pass + "'");

            System.out.println("hi");
            try {
                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "success");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (uri.contains("fetchcategory")) {
            String ans = "";
            String cname = parms.getProperty("category");
            ResultSet rs = JdbcCommon.executeQuery("select * from  course where category='" + cname + "'");
            try {
                while (rs.next()) {
                    ans = ans + rs.getString("squarephoto") + ";" + rs.getString("coursename") + ";" + rs.getInt("amount") + "~";

                }
                System.out.println(ans);
                res = new Response(HTTP_OK, "text/plain", ans);

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("fetchdata")) {
            String ans = "";
            String status = "";
            String coursename = parms.getProperty("coursename");
            String email = parms.getProperty("email");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename='" + coursename + "'");
            try {
                if (rs.next()) {
                    ans = ans + rs.getString("coursename") + ";"
                            + rs.getString("pre") + ";"
                            + rs.getString("category") + ";"
                            + rs.getString("squarephoto") + ";"
                            + rs.getInt("amount") + ";"
                            + rs.getString("widephoto") + ";"
                            + rs.getString("samplevideo");

                }
                ResultSet rs2 = JdbcCommon.executeQuery("select * from user_subscription where email='" + email + "' and coursename='" + coursename + "'");
                if (rs2.next()) {
                    status = "yes";
                    ans = ans + ";" + status;
                } else {
                    status = "no";
                    ans = ans + ";" + status;

                }
                res = new Response(HTTP_OK, "text/plain", ans);
                System.out.println(ans);
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("streamvideos")) {
            String ans = "";
            String cname = parms.getProperty("coursename");
            ResultSet rs = JdbcCommon.executeQuery("select * from videos where Coursename='" + cname + "'");
            try {
                while (rs.next()) {
                    ans = ans + rs.getInt("Lecture_no") + ";"
                            + rs.getString("Title") + ";"
                            + rs.getInt("Duration") + ";"
                            + rs.getString("Video_thumbnail") + ";"
                            + rs.getString("Video_path") + "~";

                }
                res = new Response(HTTP_OK, "text/plain", ans);
                System.out.println("response from server " + ans);
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("subscribe")) {
            String coursename = parms.getProperty("coursename");
            String email = parms.getProperty("email");
            ResultSet rs = JdbcCommon.executeQuery("select * from user_subscription");
            try {

                rs.moveToInsertRow();
                rs.updateString("coursename", coursename);
                rs.updateString("email", email);
                rs.insertRow();
                res = new Response(HTTP_OK, "text/plain", "success");

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("forgotpassword")) {
            try {
                String uans = "";
                String uemail = parms.getProperty("email");
                ResultSet rs = JdbcCommon.executeQuery("select * from users where email='" + uemail + "'");
                if (rs.next()) {
                    String contact = rs.getString("phone");
                    String pass = rs.getString("password");
                    uans = uans + contact + ";" + pass;
                    res = new Response(HTTP_OK, "test/plain", uans);
                    System.out.println(uans);
                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (uri.contains("changepass")) {
            String email = parms.getProperty("email");
            String oldpass = parms.getProperty("old pass");
            String newpass = parms.getProperty("new pass");
            ResultSet rs = JdbcCommon.executeQuery("select * from users where email='" + email + "'and password='" + oldpass + "'");
            try {
                if (rs.next()) {
                    rs.updateString("password", newpass);
                    rs.updateRow();
                    res = new Response(HTTP_OK, "text/plain", "success");

                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");
                }

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("fetchprofile")) {
            String ans = "";
            String email = parms.getProperty("email");
            ResultSet rs = JdbcCommon.executeQuery("select * from users where email='" + email + "'");
            try {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String email2 = rs.getString("email");
                    String photo = rs.getString("photo");
                    ans = ans + name + ";" + phone + ";" + email2+ ";" + photo;
                    res = new Response(HTTP_OK, "text/plain", ans);

                } else {
                    res = new Response(HTTP_OK, "text/plain", "fail");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (uri.contains("sub")) {
            String ans = "";
            String email = parms.getProperty("email");
            ResultSet rs = JdbcCommon.executeQuery("select * from course where coursename in(select coursename from user_subscription where email='" + email + "')");
            try {
                while (rs.next()) {
                    ans = ans + rs.getString("coursename") + ";" + rs.getString("squarephoto") + ";" + "~";

                }
                res = new Response(HTTP_OK, "text/plain", ans);

            } catch (SQLException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return res;
    }
}
