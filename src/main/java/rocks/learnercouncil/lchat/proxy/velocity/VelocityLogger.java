package rocks.learnercouncil.lchat.proxy.velocity;

import org.slf4j.Logger;
import rocks.learnercouncil.lchat.proxy.common.BasicLogger;

public class VelocityLogger implements BasicLogger {
    private final Logger logger;

    public VelocityLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }
}
