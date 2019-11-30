package edu.pucmm.url.Services;

import edu.pucmm.url.Entities.User;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.UUID;

public class BootstrapService {
    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort",
                "9092",
                "-tcpAllowOthers",
                "-tcpDaemon").start();
    }

    public static void stopDb() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

    public static void initDb() {
        if (UsersServices.getInstance().findByUsername("admin") == null) {
            UsersServices.getInstance().create(new User(UUID.randomUUID().toString(), "admin", "Admin", "123456", true));
        }
    }
}
