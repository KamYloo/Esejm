import { dispatchAction } from "../api.js";
import {
    GET_ALL_VOTINGS_ERROR,
    GET_ALL_VOTINGS_REQUEST,
    GET_VOTING_DETAILS_ERROR,
    GET_VOTING_DETAILS_REQUEST,
    SEARCH_VOTINGS_ERROR,
    SEARCH_VOTINGS_REQUEST,
    GET_VOTES_BY_VOTING_REQUEST,
    GET_VOTES_BY_VOTING_ERROR, GET_VOTINGS_BY_DATE_REQUEST, GET_VOTINGS_BY_DATE_ERROR
} from "./ActionType.js";

export const getAllVotingsAction = (page = 0, size = 20, filters = {}) => async (dispatch) => {
    const params = new URLSearchParams({ page, size, ...filters }).toString();

    await dispatchAction(
        dispatch,
        GET_ALL_VOTINGS_REQUEST,
        GET_ALL_VOTINGS_ERROR,
        `/api/public/votings/all?${params}`,
        {
            method: 'GET',
        }
    );
};

export const getVotesByVotingAction = (votingId) => async (dispatch) => {
    await dispatchAction(
        dispatch,
        GET_VOTES_BY_VOTING_REQUEST,
        GET_VOTES_BY_VOTING_ERROR,
        `/api/public/votes/by-voting/${votingId}`,
        {
            method: "GET",
        }
    );
};

export const getVotingDetailsAction = (votingId) => async (dispatch) => {
    await dispatchAction(dispatch, GET_VOTING_DETAILS_REQUEST, GET_VOTING_DETAILS_ERROR, `/api/public/votings/${votingId}`, {
        method: 'GET',
    });
};

export const searchVotingsAction = (params) => async (dispatch) => {
    // Usuwamy undefined z parametrów
    const filteredParams = Object.fromEntries(
        Object.entries(params).filter(([_, value]) => value !== undefined)
    );

    const queryParams = new URLSearchParams(filteredParams).toString();

    await dispatchAction(dispatch, SEARCH_VOTINGS_REQUEST, SEARCH_VOTINGS_ERROR, `/api/public/votings/search?${queryParams}`, {
        method: 'GET',
    });
};

export const getVotingsByDateAction = (date) => async (dispatch) => {
    await dispatchAction(dispatch, GET_VOTINGS_BY_DATE_REQUEST, GET_VOTINGS_BY_DATE_ERROR, `/api/public/votings/by-${date}`, {
        method: 'GET',
    });
};