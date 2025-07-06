package db.api;

import db.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *  user API
 */
@WebServlet("/api/users")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();


    /**
     * 获取所有用户
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("he is here");

        String path = req.getPathInfo();

        // 返回 JSON 列表
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        if (path == null || "/".equals(path)) {
            out.print(userService.findAll());
        } else {
            int id = Integer.parseInt(path.substring(1));
            out.print(userService.findAll());
        }
        out.close();
    }
}
