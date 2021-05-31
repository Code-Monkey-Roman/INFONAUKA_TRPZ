package com.barchuk.springbootinfoscience.patterns;

import lombok.var;
import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Singleton {

    private static final Logger logger = LoggerFactory.getLogger(Singleton.class);

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext(Singleton.class);

        var app = ctx.getBean(Singleton.class);

        var bean1 = ctx.getBean(Message.class);
        var bean2 = ctx.getBean(Message.class);

        app.run(bean1, bean2);

        ctx.close();
    }

    public void run(Message a, Message b) {
        logger.info("running Application");

        if (a.equals(b)) {
            logger.info("The beans are the same");
        } else {
            logger.info("The beans are not the same");
        }
    }
}
