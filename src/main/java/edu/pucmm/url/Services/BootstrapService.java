package edu.pucmm.url.Services;

import org.h2.tools.Server;

import java.sql.SQLException;

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
}
