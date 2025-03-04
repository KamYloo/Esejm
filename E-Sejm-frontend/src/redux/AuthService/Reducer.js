import {LOGIN_REQUEST, LOGOUT_REQUEST, REGISTER_REQUEST} from "./ActionType.js";


const initialValue= {
    login:null,
    register:null,
    logout:null,
}

export const authReducer=(store=initialValue, {type,payload})=>{
    if (type === REGISTER_REQUEST) {
        return {...store, register: payload}
    }
    else if (type === LOGIN_REQUEST) {
        return {...store, login: payload}
    }
    else if (type === LOGOUT_REQUEST) {
        return {...store, logout: payload, login: null}
    }
    return store
}