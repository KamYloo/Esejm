import {
    DELETE_USER_REQUEST,
    EDIT_USER_REQUEST,
    FETCH_USERS_REQUEST,
    GET_PROFILE_USER_REQUEST
} from "./ActionType.js";


const initialValue= {
    getUser:null,
    users: [],
    deletedUser:null,
    editUser: null,
}

export const userReducer=(store=initialValue, {type,payload})=>{
    if (type === GET_PROFILE_USER_REQUEST) {
        return {...store, getUser: payload}
    } else if (type === FETCH_USERS_REQUEST) {
        return {...store, users: payload}
    } else if (type === DELETE_USER_REQUEST) {
        return {...store, deletedUser: payload}
    } else if (type === EDIT_USER_REQUEST) {
        return {...store, editUser: payload}
    }

    return store
}