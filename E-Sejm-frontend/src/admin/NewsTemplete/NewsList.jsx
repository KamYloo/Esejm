import React from 'react';
import { List, Datagrid, TextField, EditButton, DeleteButton} from 'react-admin';

export const NewsList = (props) => (
    <List {...props}>
        <Datagrid>
            <TextField source="id" label="ID" />
            <TextField source="title" label="Title" />
            <TextField source="content" label="Content" />
            <TextField source="author" label="Author" />
            <TextField source="categories" label="Category" />
            <TextField source="imagePath" label="Image Path" />
            <EditButton />
            <DeleteButton />
        </Datagrid>
    </List>
);