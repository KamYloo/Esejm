import {dispatchAction} from "../api.js";
import {
    CREATE_NEWS_ERROR,
    CREATE_NEWS_REQUEST, DELETE_NEWS_ERROR, DELETE_NEWS_REQUEST, EDIT_NEWS_ERROR, EDIT_NEWS_REQUEST,
    GET_ALL_NEWS_ERROR,
    GET_ALL_NEWS_REQUEST, GET_NEWS_ERROR, GET_NEWS_REQUEST,
    UPLOAD_PHOTO_ERROR,
    UPLOAD_PHOTO_REQUEST
} from "./ActionType.js";

export const getNewsListAction = () => async (dispatch) => {
    await dispatchAction(dispatch, GET_ALL_NEWS_REQUEST, GET_ALL_NEWS_ERROR, '/api/public/getAllNews', {
        method: 'GET',
    });
};

export const getNewsByIdAction = (newsId) => async (dispatch) => {
    await dispatchAction(dispatch, GET_NEWS_REQUEST, GET_NEWS_ERROR, `/api/public/getNews/${newsId}`, {
        method: 'GET',
    });
}

export const createNewsAction = (data) => async (dispatch) => {
    await dispatchAction(dispatch, CREATE_NEWS_REQUEST, CREATE_NEWS_ERROR, `/api/admin/news_add`, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
    });
};

export const uploadNewsPhotoAction = (formData) => async (dispatch) => {
    const response = await dispatchAction(dispatch, UPLOAD_PHOTO_REQUEST, UPLOAD_PHOTO_ERROR, `/api/admin/news/upload`, {
        method: 'POST',
        body: formData,
        credentials: 'include',
    });

    return response;
}

export const updateNewsAction = (id, data) => async (dispatch) => {
    await dispatchAction(dispatch, EDIT_NEWS_REQUEST, EDIT_NEWS_ERROR, `/api/admin/putNews/${id}`, {
        method: 'PUT',
        body: JSON.stringify(data),
        credentials: 'include',
    });
};

export const deleteNewsAction = (newsId) => async (dispatch) => {
    await dispatchAction(dispatch, DELETE_NEWS_REQUEST, DELETE_NEWS_ERROR, `/api/admin/deleteNews/${newsId}`, {
        method: 'DELETE',
        credentials: 'include',
    });
};