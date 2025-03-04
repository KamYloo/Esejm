import {applyMiddleware, combineReducers, legacy_createStore} from "redux"
import {thunk} from "redux-thunk"
import {authReducer} from "./AuthService/Reducer.js";
import {userReducer} from "./UserService/Reducer.js";
import {newsReducer} from "./NewsService/Reducer.js";
import {threadReducer} from "./PostService/Reducer.js";
import {mpReducer} from "./MpService/Reducer.js";
import {proceedingReducer} from "./ProceedingService/Reducer.js";
import {statementReducer} from "./StatementService/Reducer.js";
import { votingReducer } from "./VotingService/Reducer.js";


const rootReducer = combineReducers({
    auth: authReducer,
    user: userReducer,
    news: newsReducer,
    thread: threadReducer,
    mp: mpReducer,
    proceeding: proceedingReducer,
    statement: statementReducer,
    voting: votingReducer,
})

export const store = legacy_createStore(rootReducer, applyMiddleware(thunk))