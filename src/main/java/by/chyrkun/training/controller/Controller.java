package by.chyrkun.training.controller;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.exception.CommandNotFoundException;
import by.chyrkun.training.service.factory.CommandFactoryClient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        CommandResult result;
        Command command;
        try {
            command = CommandFactoryClient.getInstance().initCommand(req);
            RequestContent requestContent = new RequestContent();
            requestContent.extractValues(req);
            result = command.execute(requestContent);
            requestContent.insertRequestAttributes(req);
            requestContent.insertSessionAttributes(req);
            switch (result.getResponseType()) {
                case FORWARD: {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(result.getPage());
                    dispatcher.forward(req, resp);
                    break;
                }
                case REDIRECT: {
                    resp.sendRedirect("/training" + result.getPage());
                    break;
                }
                case SESSION_INVALIDATE: {
                    req.getSession().invalidate();
                    resp.sendRedirect("/training" + result.getPage());
                }
            }
        }catch (CommandNotFoundException ex) {
            resp.sendError(404, "Command not found");
        }
    }

    @Override
    public void destroy() {
        ConnectionPoolImpl.getInstance().shutdown();
        super.destroy();
    }
}
