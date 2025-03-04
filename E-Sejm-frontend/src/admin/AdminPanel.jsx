import React from 'react';
import { Admin, Resource } from 'react-admin';
import {Dashboard} from "./Dashboard.jsx";
import {UserList} from "./UserTemplete/UserList.jsx";
import {UserEdit} from "./UserTemplete/UserEdit.jsx";
import {createTheme} from "@mui/material";
import NewsCreate from "./NewsTemplete/CreateNews.jsx";
import {NewsList} from "./NewsTemplete/NewsList.jsx";
import combinedDataProvider from "./combinedDataProvider.js";
import NewsEdit from "./NewsTemplete/NewsEdit.jsx";

const theme = createTheme({
    palette: {
        primary: {
            main: '#061D31',
        },
        secondary: {
            main: '#061D31',
        },
        background: {
            default: 'rgba(6,29,49,0.06)',
        },
    },
    typography: {
        fontFamily: 'Roboto, sans-serif',
    },
});

const AdminPanel = () => {
    return (
        <Admin basename="/admin" theme={theme} dashboard={Dashboard} dataProvider={combinedDataProvider}>
            <Resource name="users" list={UserList} edit={UserEdit}/>
            <Resource name="news" list={NewsList} create={NewsCreate} edit={NewsEdit}/>
        </Admin>
    );
};

export {AdminPanel};
