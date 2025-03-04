import {
    GET_ALL_MPS_REQUEST, GET_MP_REQUEST,
} from "./ActionType.js";


const initialValue= {
    mps: [],
    findMp: null,
}

export const mpReducer=(store=initialValue, {type,payload})=>{
    if (type === GET_ALL_MPS_REQUEST) {
        return {...store, mps: payload}
    } else if (type === GET_MP_REQUEST) {
        return {...store, findMp: payload}
    }

    return store
}