import {
    GET_PROCEEDING_BY_DATE_REQUEST,
    GET_PROCEEDINGS_DATES_REQUEST
} from "./ActionType.js";


const initialValue= {
    dates: [],
    meet: null,
}

export const proceedingReducer=(store=initialValue, {type,payload})=>{
    if (type === GET_PROCEEDINGS_DATES_REQUEST) {
        return {...store, dates: payload}
    } else if (type === GET_PROCEEDING_BY_DATE_REQUEST) {
        return {...store, meet: payload}
    }

    return store
}