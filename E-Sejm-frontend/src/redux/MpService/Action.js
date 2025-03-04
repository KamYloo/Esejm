import {dispatchAction} from "../api.js";
import {
    GET_ALL_MPS_ERROR,
    GET_ALL_MPS_REQUEST,
    GET_MP_ERROR,
    GET_MP_REQUEST
} from "./ActionType.js";

export const getMpsAction = () => async (dispatch) => {
    await dispatchAction(dispatch, GET_ALL_MPS_REQUEST, GET_ALL_MPS_ERROR, '/api/public/mps', {
        method: 'GET',
    });
};

export const getMpByIdAction = (id) => async (dispatch) => {
    await dispatchAction(dispatch, GET_MP_REQUEST, GET_MP_ERROR, `/api/public/mpProfile/${id}`, {
        method: 'GET',
    });
}