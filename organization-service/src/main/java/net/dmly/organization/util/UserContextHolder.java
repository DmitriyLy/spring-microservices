package net.dmly.organization.util;

import org.springframework.util.Assert;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();


    public static UserContext getContext() {
        UserContext context = userContext.get();

        if (context == null) {
            context = new UserContext();
            userContext.set(context);
        }

        return context;
    }

    public static void setUserContext(UserContext context) {
        Assert.notNull(context, "context must not be null");
        userContext.set(context);
    }
}
