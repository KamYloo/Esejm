import {BASE_API_URL, dispatchAction} from "../api.js";
import {
    LOGIN_ERROR,
    LOGIN_REQUEST,
    LOGOUT_ERROR,
    LOGOUT_REQUEST,
    REGISTER_ERROR,
    REGISTER_REQUEST
} from "./ActionType.js";

export const registerAction = (data) => async (dispatch) => {
    await dispatchAction(dispatch, REGISTER_REQUEST, REGISTER_ERROR, '/api/auth/register',
        {
            method: 'POST',
            body: JSON.stringify(data),
        }
    );
};

export const loginAction = (data) => async (dispatch) => {
   await dispatchAction(dispatch, LOGIN_REQUEST, LOGIN_ERROR, '/api/auth/login',
            {
                method: 'POST',
                body: JSON.stringify(data),
                credentials: 'include',
            }
        );
};

export const loginWithGoogleAction = () => async (dispatch) => {
    await dispatchAction(dispatch, LOGIN_REQUEST, LOGIN_ERROR, '/api/auth/googleUser',
        {
            method: 'GET',
            credentials: 'include',
        }
    );
};

export const logoutAction = () => async (dispatch) => {
    try {
        const res = await fetch(`${BASE_API_URL}/api/auth/logout`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: 'include'
        })

        const resData = await res.text()
        localStorage.removeItem("refreshToken")
        localStorage.removeItem("user")
        dispatch({ type: LOGOUT_REQUEST, payload: resData })

    } catch (error) {
        console.log("catch error ", error)
        dispatch({ type: LOGOUT_ERROR, payload: error.message });
    }
}
