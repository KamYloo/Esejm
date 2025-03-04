package pb.wi.mmw.e_sejm.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.service.CookieService;

@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public Cookie getNewCookie(String arg, String value) {
        Cookie cookie = new Cookie(arg, value);
        cookie.setHttpOnly(true); // Ciasteczko HttpOnly
        cookie.setSecure(false);   // Ciasteczko Secure, wymaga HTTPS (w środowisku produkcyjnym)
        cookie.setPath("/");      // Zakres ciasteczka
        cookie.setMaxAge(24 * 60 * 60); // Ustaw czas ważności ciasteczka na 1 dzień
        return cookie;
    }

    @Override
    public Cookie deleteCookie(String arg) {
        Cookie cookie = new Cookie(arg, null);
        cookie.setHttpOnly(true); // Ciasteczko HttpOnly
        cookie.setSecure(false);   // Ciasteczko Secure, wymaga HTTPS (w środowisku produkcyjnym)
        cookie.setPath("/");      // Zakres ciasteczka
        cookie.setMaxAge(0); // Ustaw czas ważności ciasteczka na 1 dzień
        return cookie;
    }

    public String getJwtCookie(HttpServletRequest request){
        String jwt = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                    return jwt;
                }
            }
        }
//        return "";
        throw new CustomException(BusinessErrorCodes.BAD_COOKIE);
    }

}
