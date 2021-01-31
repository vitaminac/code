package servlet.auth;

import java.security.Principal;

public interface PrincipleResolver {
    Principal resolve(String username, String password);
}
