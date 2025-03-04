import {dispatchAction} from "../api.js";
import {
    GET_PROCEEDING_BY_DATE_ERROR,
    GET_PROCEEDING_BY_DATE_REQUEST,
    GET_PROCEEDINGS_DATES_ERROR,
    GET_PROCEEDINGS_DATES_REQUEST

} from "./ActionType.js";

export const getProceedingsDatesAction = (year, month) => async (dispatch) => {
    await dispatchAction(dispatch, GET_PROCEEDINGS_DATES_REQUEST, GET_PROCEEDINGS_DATES_ERROR, `/api/public/proceedingsDate/by-month?year=${year}&month=${month}`, {
        method: 'GET',
    });
};

export const getProceedingByDateAction = (date) => async (dispatch) => {
    await dispatchAction(dispatch, GET_PROCEEDING_BY_DATE_REQUEST, GET_PROCEEDING_BY_DATE_ERROR, `/api/public/proceedingsDate/by-${date}`, {
        method: 'GET',
    });
};