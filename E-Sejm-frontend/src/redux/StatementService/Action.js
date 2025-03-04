import {dispatchAction} from "../api.js";
import {
    GET_STATEMENT_ERROR,
    GET_STATEMENT_REQUEST, GET_STATEMENT_TEXT_ERROR, GET_STATEMENT_TEXT_REQUEST,
    GET_STATEMENTS_BY_DATE_ERROR,
    GET_STATEMENTS_BY_DATE_REQUEST

} from "./ActionType.js";

export const getStatementsByDateAction = (date) => async (dispatch) => {
    await dispatchAction(dispatch, GET_STATEMENTS_BY_DATE_REQUEST, GET_STATEMENTS_BY_DATE_ERROR, `/api/public/statements/by-${date}`, {
        method: 'GET',
    });
};

export const getStatementByIdAction = (id) => async (dispatch) => {
    await dispatchAction(dispatch, GET_STATEMENT_REQUEST, GET_STATEMENT_ERROR, `/api/public/statements/${id}`, {
        method: 'GET',
    });
};

export const getStatementTextByIdAction = (proceedingId, transcriptId, date) => async (dispatch) => {
    try {
        dispatch({ type: GET_STATEMENT_REQUEST });
        const response = await fetch(`https://api.sejm.gov.pl/sejm/term10/proceedings/${proceedingId}/${date}/transcripts/${transcriptId}`, {
            method: "GET",
            headers: {
                Accept: "text/html",
            },
        });

        if (!response.ok) {
            throw new Error(`Błąd HTTP: ${response.status}`);
        }

        const html = await response.text();
        dispatch({ type: GET_STATEMENT_TEXT_REQUEST, payload: html });
    } catch (error) {
        dispatch({ type: GET_STATEMENT_TEXT_ERROR, payload: error.message });
    }
};