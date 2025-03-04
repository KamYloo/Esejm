import {
    ADD_POST_REQUEST,
    FETCH_THREADS_REQUEST,
    FETCH_THREAD_BY_ID_REQUEST,
    ADD_COMMENT_REQUEST,
    FETCH_COMMENT_BY_THREAD_ID_REQUEST,
    DOWNVOTE_COMMENT_REQUEST,
    UPVOTE_COMMENT_REQUEST, DOWNVOTE_POST_REQUEST, UPVOTE_POST_REQUEST,
} from "./ActionType.jsx";

const initialState = {
    post: null,
    data: [],
    totalPages: 0,
    currentThread: null,
    currentComment: null,
    comments: [],
    currentScore: 0
};

export const threadReducer = (state = initialState, { type, payload }) => {
    switch (type) {
        case ADD_POST_REQUEST:
            return {
                ...state,
                post: payload,
            };

        case FETCH_THREADS_REQUEST:
            return {
                ...state,
                data: payload.content,
                totalPages: payload.totalPages,
            };

        case FETCH_THREAD_BY_ID_REQUEST:
            return {
                ...state,
                currentThread: payload,
            };

        case ADD_COMMENT_REQUEST:
            return {
                ...state,
                post: payload,
            };

        case FETCH_COMMENT_BY_THREAD_ID_REQUEST:
            return {
                ...state,
                comments: payload,
            };

        case UPVOTE_COMMENT_REQUEST: {
            const updatedComments = state.comments.map(comment =>
                comment.id === payload.commentId
                    ? {
                        ...comment,
                        score: payload.updatedScore,
                        userReaction: "upvote",
                    }
                    : comment
            );
            return { ...state, comments: updatedComments };
        }

        case DOWNVOTE_COMMENT_REQUEST: {
            const updatedComments = state.comments.map(comment =>
                comment.id === payload.commentId
                    ? {
                        ...comment,
                        score: payload.updatedScore,
                        userReaction: "downvote",
                    }
                    : comment
            );
            return { ...state, comments: updatedComments };
        }

        case UPVOTE_POST_REQUEST: {
            const updatedPosts = state.posts.map(post =>
                post.id === payload.postId
                    ? {
                        ...post,
                        score: payload.updatedScore,
                        userReaction: "upvote",
                    }
                    : post
            );
            return { ...state, posts: updatedPosts };
        }

        case DOWNVOTE_POST_REQUEST: {
            const updatedPosts = state.posts.map(post =>
                post.id === payload.postId
                    ? {
                        ...post,
                        score: payload.updatedScore,
                        userReaction: "downvote",
                    }
                    : post
            );
            return { ...state, posts: updatedPosts };
        }

        default:
            return state;
    }
};
