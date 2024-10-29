package rocks.learnercouncil.lchat.proxy.bungee;

import rocks.learnercouncil.lchat.proxy.common.BasicLogger;

import java.util.logging.Logger;

public class BungeeLogger implements BasicLogger {
    private final Logger logger;

    public BungeeLogger(Logger logger) {
        this.logger = logger;
    }


    @Override
    public void debug(String message) {
        logger.fine(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warning(message);
    }

    @Override
    public void error(String message) {
        logger.severe(message);
    }
}
