import {dispatchAction} from "../api.js";
import {
    DELETE_USER_ERROR,
    DELETE_USER_REQUEST, EDIT_USER_ERROR, EDIT_USER_REQUEST,
    FETCH_USERS_ERROR,
    FETCH_USERS_REQUEST,
    GET_PROFILE_USER_ERROR,
    GET_PROFILE_USER_REQUEST, UPDATE_USER_ERROR, UPDATE_USER_REQUEST
} from "./ActionType.js";

export const getUserByIdAction = (nickName) => async (dispatch) => {
    await dispatchAction(dispatch, GET_PROFILE_USER_REQUEST, GET_PROFILE_USER_ERROR, `/api/public/user/profile/${nickName}`, {
        method: 'GET',
    });
}

export const getUsersAction = () => async (dispatch) => {
    await dispatchAction(dispatch, FETCH_USERS_REQUEST, FETCH_USERS_ERROR, '/api/admin/users', {
        method: 'GET',
        credentials: 'include',
    });
};

export const updateUserAction = (id, data) => async (dispatch) => {
    await dispatchAction(dispatch, UPDATE_USER_REQUEST, UPDATE_USER_ERROR, `/api/admin/user/${id}`, {
        method: 'PUT',
        body: JSON.stringify(data),
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
    });
};

export const deleteUserAction = (userId) => async (dispatch) => {
    await dispatchAction(dispatch, DELETE_USER_REQUEST, DELETE_USER_ERROR, `/api/admin/user/delete/${userId}`, {
        method: 'DELETE',
        credentials: 'include',
    });
};

export const editUserPictureAction = (formData ) => async (dispatch) => {
    return await dispatchAction(dispatch, EDIT_USER_REQUEST, EDIT_USER_ERROR, `/api/user/profile/image`, {
        method: 'PUT',
        body: formData,
        credentials: 'include',
    });
}