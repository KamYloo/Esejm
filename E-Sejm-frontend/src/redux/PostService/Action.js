import { dispatchAction } from "../api";
import {
    ADD_COMMENT_ERROR,
    ADD_COMMENT_REQUEST,
    ADD_POST_ERROR,
    ADD_POST_REQUEST, FETCH_COMMENT_BY_THREAD_ID_ERROR, FETCH_COMMENT_BY_THREAD_ID_REQUEST,
    FETCH_THREAD_BY_ID_ERROR,
    UPVOTE_COMMENT_REQUEST,
    UPVOTE_COMMENT_ERROR,
    DOWNVOTE_COMMENT_REQUEST,
    DOWNVOTE_COMMENT_ERROR, DOWNVOTE_POST_REQUEST, DOWNVOTE_POST_ERROR, UPVOTE_POST_REQUEST, UPVOTE_POST_ERROR,
} from "./ActionType.jsx";
import { FETCH_THREADS_REQUEST, FETCH_THREADS_ERROR } from "./ActionType.jsx";
import { FETCH_THREAD_BY_ID_REQUEST } from "./ActionType.jsx";



export const addPostAction = (postRequest) => async (dispatch) => {
    await dispatchAction(dispatch, ADD_POST_REQUEST, ADD_POST_ERROR, "/api/posts/create", {
        method: "POST",
        body: JSON.stringify(postRequest),
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
    });
};

export const fetchThreadsAction = ({ sortOption, page, size }) => async (dispatch) => {
    const endpointMap = [
        "/api/posts/date-desc",
        "/api/posts/date-asc",
        "/api/posts/comments",
        "/api/posts/ratings",
    ];
    const endpoint = endpointMap[sortOption] || endpointMap[0];

    await dispatchAction(dispatch, FETCH_THREADS_REQUEST, FETCH_THREADS_ERROR, `${endpoint}?page=${page}&size=${size}`, {
        method: "GET",
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
    });
};

export const getThreadByIdAction = (threadId) => async (dispatch) => {
    await dispatchAction(dispatch, FETCH_THREAD_BY_ID_REQUEST, FETCH_THREAD_BY_ID_ERROR, `/api/posts/${threadId}`, {
        method: 'GET',
        credentials: 'include',
    });
}

export const addCommentAction = (postRequest) => async (dispatch) => {
    await dispatchAction(dispatch, ADD_COMMENT_REQUEST, ADD_COMMENT_ERROR, "/api/comments/create", {
        method: "POST",
        body: JSON.stringify(postRequest),
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
    });
};

export const fetchCommentsByPostIdAction = (postId) => async (dispatch) => {
    await dispatchAction(dispatch, FETCH_COMMENT_BY_THREAD_ID_REQUEST, FETCH_COMMENT_BY_THREAD_ID_ERROR, `/api/comments/by-post/${postId}`, {
        method: "GET",
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
    });


};

export const upvoteCommentAction = (commentId) => async (dispatch) => {
    try {
        const response = await dispatchAction(
            dispatch,
            UPVOTE_COMMENT_REQUEST,
            UPVOTE_COMMENT_ERROR,
            `/api/comments/${commentId}/upvote`,
            {
                method: "POST",
                body: JSON.stringify({}),
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
            }
        );
        const updatedScore = response.score;

        dispatch({
            type: UPVOTE_COMMENT_REQUEST,
            payload: { commentId, updatedScore, userReaction: "upvote" },
        });
    } catch (error) {
        console.error("Upvote error:", error);
    }
};


export const downvoteCommentAction = (commentId) => async (dispatch) => {
    try {
        const response = await dispatchAction(
            dispatch,
            DOWNVOTE_COMMENT_REQUEST,
            DOWNVOTE_COMMENT_ERROR,
            `/api/comments/${commentId}/downvote`,
            {
                method: "POST",
                body: JSON.stringify({}),
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
            }
        );
        const updatedScore = response.score;

        dispatch({
            type: DOWNVOTE_COMMENT_REQUEST,
            payload: { commentId, updatedScore, userReaction: "downvote" },
        });
    } catch (error) {
        console.error("Downvote error:", error);
    }
};

export const upvotePostAction = (postId) => async (dispatch) => {
    try {
        const response = await dispatchAction(
            dispatch,
            UPVOTE_POST_REQUEST,
            UPVOTE_POST_ERROR,
            `/api/posts/${postId}/upvote`,
            {
                method: "POST",
                body: JSON.stringify({}),
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
            }
        );
        const updatedScore = response.score;

        dispatch({
            type: UPVOTE_POST_REQUEST,
            payload: { postId, updatedScore, userReaction: "upvote" },
        });
    } catch (error) {
        console.error("Upvote error:", error);
    }
};

export const downvotePostAction = (postId) => async (dispatch) => {
    try {
        const response = await dispatchAction(
            dispatch,
            DOWNVOTE_POST_REQUEST,
            DOWNVOTE_POST_ERROR,
            `/api/posts/${postId}/downvote`,
            {
                method: "POST",
                body: JSON.stringify({}),
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
            }
        );
        const updatedScore = response.score;

        dispatch({
            type: DOWNVOTE_POST_REQUEST,
            payload: { postId, updatedScore, userReaction: "downvote" },
        });
    } catch (error) {
        console.error("Downvote error:", error);
    }
};
