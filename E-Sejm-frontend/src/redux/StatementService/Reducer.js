import {
    GET_STATEMENT_REQUEST, GET_STATEMENT_TEXT_REQUEST,
    GET_STATEMENTS_BY_DATE_REQUEST
} from "./ActionType.js";


const initialValue= {
    statements: [],
    data: null,
    text: null,
}

export const statementReducer=(store=initialValue, {type,payload})=>{
    if (type === GET_STATEMENTS_BY_DATE_REQUEST) {
        return {...store, statements: payload}
    } else if (type === GET_STATEMENT_REQUEST) {
        return {...store, data: payload}
    } else if (type === GET_STATEMENT_TEXT_REQUEST) {
        return {...store, text: payload}
    }

    return store
}