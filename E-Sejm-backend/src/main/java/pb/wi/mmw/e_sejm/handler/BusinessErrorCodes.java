package pb.wi.mmw.e_sejm.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum BusinessErrorCodes{

    NO_CODE(0,"No code", HttpStatus.NOT_IMPLEMENTED),
    INCORRECT_CURRENT_PASSWORD(300, "Current password is incorrect", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH(301, "New password does not match", HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(302, "User account locked", HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED(303, "User account is disable", HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS(304, "Login and / or password is incorrect", HttpStatus.FORBIDDEN),
    BAD_COOKIE(305, "No jwt cookie found", HttpStatus.BAD_REQUEST),
    BAD_JWT_TOKEN(306, "Invalid JWT token", HttpStatus.BAD_REQUEST),
    EMAIL_IS_USED(307, "Email is used", HttpStatus.BAD_REQUEST),
    NICKNAME_IS_USED(308, "Nick name is used", HttpStatus.BAD_REQUEST),
    USER_IS_ENABLE(308, "User is already activated", HttpStatus.BAD_REQUEST),
    BAD_NEWS_ID(310, "There is no news of such id", HttpStatus.BAD_REQUEST),
    BAD_NEWS(311, "There is no such news", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(312, "Token expired", HttpStatus.BAD_REQUEST),
    INCORRECT_USER_ID(313, "User of such ID does not exist", HttpStatus.BAD_REQUEST),
    NO_ROLE(314, "This role does not exist", HttpStatus.BAD_REQUEST),
    INCORRECT_USER(315, "User does not exist", HttpStatus.BAD_REQUEST),
    NO_TOKEN(316,"This token does not exit", HttpStatus.BAD_REQUEST),
    NOT_FOUND_MP(317, "MP with the given ID not found", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND(318, "Image not found", HttpStatus.NOT_FOUND),
    IMAGE_FETCH_FAILED(319, "Failed to fetch image", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_CATEGORY(320,"There is no such category", HttpStatus.BAD_REQUEST),
    NO_POST_ID(321,"There is no such category", HttpStatus.BAD_REQUEST),
    NO_POST_TITLE(322,"There is no such category", HttpStatus.BAD_REQUEST),
    NO_COMMENT_ID(323,"There is no such comment", HttpStatus.BAD_REQUEST),
    INCORRECT_PERMISSIONS(324,"There is no such comment", HttpStatus.UNAUTHORIZED),
    NO_MEETING_ID(325,"There is no such meeting", HttpStatus.BAD_REQUEST),
    NO_VOTING_ID(326,"There is no such voting", HttpStatus.BAD_REQUEST),
    INCORRECT_MPLIST(327,"List of favorite mp's doesn't contain such mp.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_PARTY(328,"There is no such party", HttpStatus.BAD_REQUEST),
    PARTY_NOT_IN_FAVORITE(329,"This party isn't in user favorite's", HttpStatus.BAD_REQUEST),
    NO_SURVEY(330, "This survey doesn't exist", HttpStatus.BAD_REQUEST),
    NO_QUESTION(331, "This question doesn't exist", HttpStatus.BAD_REQUEST),
    NO_ANSWER(332, "There is no answer to this question by this user", HttpStatus.BAD_REQUEST),
    SURVEY_CLOSED(333, "This survey is already closed.", HttpStatus.BAD_REQUEST),
    PROCEEDING_NOT_FOUND(334, "Proceeding not found", HttpStatus.NOT_FOUND),
    PROCEEDING_DATE_NOT_FOUND(335, "Proceeding_Date not found", HttpStatus.NOT_FOUND),
    STATEMENT_NOT_FOUND(336, "Statement not found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(338,"Invalid token", HttpStatus.BAD_REQUEST)
    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;


}
