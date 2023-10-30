package com.powerreaderapi.powerreaderapi.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class ErrorLogFilter extends Filter<ILoggingEvent>{

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLevel() == Level.ERROR && event.getMessage().contains("Incorrect reading recorded")) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }

}
