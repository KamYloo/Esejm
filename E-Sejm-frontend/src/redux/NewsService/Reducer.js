import {
    CREATE_NEWS_REQUEST, DELETE_NEWS_REQUEST, EDIT_NEWS_REQUEST,
    GET_ALL_NEWS_REQUEST, GET_NEWS_REQUEST,
    UPLOAD_PHOTO_REQUEST
} from "./ActionType.js";

const initialValue= {
    article:null,
    newsList: [],
    photo:null,
}

export const newsReducer=(store=initialValue, {type,payload})=>{
    if (type === CREATE_NEWS_REQUEST) {
        return {...store, article: payload}
    } else if (type === GET_ALL_NEWS_REQUEST) {
        return {...store, newsList: payload}
    } else if (type === UPLOAD_PHOTO_REQUEST) {
        return {...store, photo: payload}
    }else if (type === EDIT_NEWS_REQUEST) {
        return {...store, article: payload}
    }else if (type === DELETE_NEWS_REQUEST) {
        return {...store, article: payload}
    } else if (type === GET_NEWS_REQUEST) {
        return {...store, article: payload}
    }
    return store
}