package com.hzih.bsms.web.action.logrotate;

import com.hzih.bsms.dao.CaUserDao;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;

/**
 * Created by hhm on 2014/12/17.
 */
public class SquidLogRotateServlet  extends HttpServlet {
    private CaUserDao caUserDao;


    private Timer timer = null;
    /**
     * Destruction of the servlet.


     */
    public void destroy() {
        super.destroy();
        if(timer!=null){
            timer.cancel();
        }
    }

    /**
     * <p>
     * 在Servlet中注入对象的步骤:
     * 1.取得ServletContext
     * 2.利用Spring的工具类WebApplicationContextUtils得到WebApplicationContext
     * 3.WebApplicationContext就是一个BeanFactory,其中就有一个getBean方法
     * 4.有了这个方法就可像平常一样为所欲为了,哈哈!
     * </p>
     */
    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = this.getServletContext();

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        caUserDao = (CaUserDao) ctx.getBean("caUserDao");

        timer = new Timer(true);
        //设置任务计划，启动和间隔时间
        timer.schedule(new SquidLogRotateTask(caUserDao), 0, 1000 * 60*5);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {

    }

}
